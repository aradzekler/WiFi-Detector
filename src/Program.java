import java.io.*;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.*; //imported from API.

public class Program {
	final static String COMMA = ",";
	final static String NEW_LINE_SEPARATOR = "\n";

	private static String sourceFile = "";
	private static String sourceFolder = "";
	private static String destinationFile = "";
	private static String destinationFolder = "";

	private static ArrayList<String[]> csvList;


	// Constructor.
	public Program(String sourceFile, String destinationFile, String sourceFolder, String destinationFolder) {
		Program.sourceFile = sourceFile;
		Program.destinationFile = destinationFile;
		Program.sourceFolder = sourceFolder;
		Program.destinationFolder = destinationFolder;
	}

	// helping class to store information.
	private static class Info {
		private final String time;
		private final int frq;
		private final String mod;
		private final String lat;
		private final String lon;
		private final String alt;
		private final String wifi;
		private final String mac;
		private final String signal;

		// Constructor
		public Info(String[] column) {
			time = column[3];
			mod = column[2];
			lat = column[6];
			lon = column[7];
			alt = column[8];
			wifi = column[1];
			mac = column[0];
			frq = Integer.parseInt(column[4]);
			signal = column[5];
		}
	}

	// Writer function for CSV.
	public static void writeFile(String temp) {
		final String FILE_HEADER = "Time, ID, Lat, Lon, Alt, SSID1, MAC1, Frequncy1, Signal1,"
				+ " SSID2, MAC2, Frequncy2, Signal2," + " SSID3, MAC3, Frequncy3, Signal3,"
				+ " SSID4, MAC4, Frequncy4, Signal4," + " SSID5, MAC5, Frequncy5, Signal5,"
				+ " SSID6, MAC6, Frequncy6, Signal6," + " SSID7, MAC7, Frequncy7, Signal7,"
				+ " SSID8, MAC8, Frequncy8, Signal8," + " SSID9, MAC9, Frequncy9, Signal9,"
				+ " SSID10, MAC10, Frequncy10, Signal10";
		FileWriter fileWriter = null;
		String line = "";

		try {
			fileWriter = new FileWriter(destinationFile); 
			fileWriter.append(FILE_HEADER.toString()); // Write the CSV file header
			fileWriter.append(NEW_LINE_SEPARATOR);
			try (BufferedReader br = new BufferedReader(new FileReader(temp))) {
				br.readLine(); 
				br.readLine();// reading 2 lines to avoid headers.
				Info[] max = new Info[10];
				int count = 0;
				while ((line = br.readLine()) != null) {
					String[] column = line.split(COMMA);
					Info info = new Info(column);
					// sort by time first
					if (count > 0 && !max[0].time.equals(info.time)) {
						print(max, count, fileWriter);
						count = 0;
					}
					int i = 0;
					// sort by signal every row.
					while (i < count && Integer.parseInt(max[i].signal) > Integer.parseInt(info.signal)) {
						++i;
					}
					while (i < count) {
						Info pred = max[i];
						max[i] = info;
						info = pred;
						++i;
					}
					if (count < max.length) {
						max[count++] = info;
					}
				}
				if (count > 0) {
					print(max, count, fileWriter);
				}
			}
			System.out.println("CSV file read was successful.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Flushing Error");
				e.printStackTrace();
			}
		}
	}

	// get Model function. need to improve.
	private static String getMod(String path) {
		// returning model, input path csv file;
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			String[] column = line.split(COMMA);
			br.close();
			return column[2];
		} catch (IOException e) {
			e.printStackTrace();
			return "Reading Error";
		}
	}

	// printing rows into CSV file.
	private static void print(Info[] max, int count, Writer fileWriter) {
		// Writing to new CSV file.
		try {
			fileWriter.append(max[0].time);
			fileWriter.append(COMMA);
			fileWriter.append(max[0].mod);
			fileWriter.append(COMMA);
			fileWriter.append(max[0].lat);
			fileWriter.append(COMMA);
			fileWriter.append(max[0].lon);
			fileWriter.append(COMMA);
			fileWriter.append(max[0].alt);
			for (int i = 0; i < count; ++i) {
				fileWriter.append(COMMA);
				fileWriter.append(max[i].wifi);
				fileWriter.append(COMMA);
				fileWriter.append(max[i].mac);
				fileWriter.append(COMMA);
				fileWriter.append(Integer.toString(max[i].frq));
				fileWriter.append(COMMA);
				fileWriter.append(max[i].signal);
			}
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* reader function for reading files from folder. Calling that function will read all files
	 * in folders and turn them into 1 csv file.
	 */
	public void csvFolderReader() {
		File Location = new File(sourceFolder); //folder path name
		File[] all_files = Location.listFiles();
		int i = 0;

		while (i < all_files.length) {
			String extension = all_files[i].toString();
			int a = extension.lastIndexOf('.');
			if (a > 0) {
				extension = extension.substring(a + 1);
			}
			if (extension.compareToIgnoreCase("txt") == 0 || extension.compareToIgnoreCase("csv") == 0) {
				System.out.println(all_files[i]);
				String temp = all_files[i].toString();
				writeFile(temp);
			}
			i++;
		}
	}

	// CSV to KML function using JAK API.
	public void writeFileKML() {
		try {
			csvToArrayList();
			Kml kml = KmlFactory.createKml(); // creating a new instance.

			for (int i = 1; i < csvList.size(); i++) { // looping all elements in ArrayList.
				String[] s = csvList.get(i);
				// create <Placemark> and set values.
				Placemark placemark = KmlFactory.createPlacemark();
				placemark.setName(s[1]);
				placemark.setVisibility(true);
				placemark.setOpen(false);
				placemark.setDescription(s[0]);
				placemark.setStyleUrl("styles.kml#jugh_style");

				// create <Point> and set values. <Point> is children class of <Placemark>.
				Point point = KmlFactory.createPoint();
				point.setExtrude(false);
				point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
				point.addToCoordinates(Double.parseDouble(s[3]), Double.parseDouble(s[2]), Double.parseDouble(s[4])); // set
				// coordinates

				placemark.setGeometry(point); // binding <Point> to <Placemark>.
				kml.setFeature(placemark); // setting <Placemark> in KML.
			}
			kml.marshal(new File("newKml.kml")); // create file.
			System.out.println("KML file writing was successful.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Converting CSV file to an ArrayList. used by writeFileKML().
	private void csvToArrayList() {
		// Reads CSV file from string input, than transfers all information to
		// ArrayList.
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(sourceFile)));
			String line;
			while ((line = br.readLine()) != null) {
				String[] entries = line.split(COMMA);
				csvList.add(entries);
			}
			br.close();
			System.out.println("CSV successfully converted to an ArrayList");
		} catch (IOException e) {
			System.out.println("CSV Reader Error.");
			e.printStackTrace();
		}

		Program.csvList = csvList;
	}

	public static void main(String[] args) {
		String sourcefile = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv";
		String source = "C:\\Users\\Arad Zekler\\Desktop\\test folder";
		//csvFolderReader(source, destination);
		//writeFileKML(csvToArrayList("C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv"));

		Program newfile = new Program(sourcefile, sourcefile, source, "");
		newfile.csvFolderReader();
		//newfile.writeFileKML();
		/*
		 * TODO 
		 * -check if reader can take wrong inputs.
		 *  -model parsing. -כל נתב )לפי ה
		 * MAC שלו( יוצג לפי המיקום הכי "חזק שלו". meaning?? 
		 * -junit testing. 
		 * -upload project to github. 
		 * -testing folder with working "walk around the campus" testing.
		 *  -detailed readme file. -explanation file about the project.
		 * -javadoc. 
		 * -adding Timeline to kml.
		 */
	}
}