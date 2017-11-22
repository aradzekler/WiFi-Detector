import java.io.*;
import java.util.ArrayList;
import de.micromata.opengis.kml.v_2_2_0.*; //imported from API.

/**
 * @authors Arad Zekler, Dolev Hindy, Naor Dahan.
 */
public class Program {
	final static String COMMA = ",";
	final static String NEW_LINE_SEPARATOR = "\n";

	private static String sourceFolder = "";
	private static String destinationFile = "";
	private static ArrayList<String[]> csvList;

	/**
	 * Constructor.
	 * @param sourceFolder source folder to read files from.
	 * @param destinationFile destination csv file.
	 */
	public Program(String sourceFolder, String destinationFile) {
		Program.destinationFile = destinationFile;
		Program.sourceFolder = sourceFolder;
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

	/**
	 * Writer function for CSV.
	 * @param temp       getting the parameter from csvFolderReader().
	 */
	private static void writeFile(String path, FileWriter fileWriter) {
		String line = "";

		try {
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
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
					// sort every row by signal.
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
		} 
	}
	// Not useable yet.
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

	/**
	 * Printing rows into CSV file.
	 * @param max       Info Object containing column information.
	 * @param count		Counter received from writeFile(String).
	 * @param fileWriter Writer object received from writeFile(String).
	 */
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

	/* Reader function for reading files from folder. Calling that function will read all files
	 * in folders and turn them into 1 csv file.
	 */
	private boolean csvFolderReader(FileWriter fileWriter) {
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
				String path = all_files[i].toString();
				writeFile(path, fileWriter);
			}
			else {
				System.out.println("Wrong extension file found. Didn't read.");
			}
			i++;
		}
		return false;
	}

	// New Writer function for csv file.
	public void csvWriteFile() {
		final String FILE_HEADER = "Time, ID, Lat, Lon, Alt, SSID1, MAC1, Frequncy1, Signal1,"
				+ " SSID2, MAC2, Frequncy2, Signal2," + " SSID3, MAC3, Frequncy3, Signal3,"
				+ " SSID4, MAC4, Frequncy4, Signal4," + " SSID5, MAC5, Frequncy5, Signal5,"
				+ " SSID6, MAC6, Frequncy6, Signal6," + " SSID7, MAC7, Frequncy7, Signal7,"
				+ " SSID8, MAC8, Frequncy8, Signal8," + " SSID9, MAC9, Frequncy9, Signal9,"
				+ " SSID10, MAC10, Frequncy10, Signal10";
		FileWriter fileWriter = null;
		String line = "";
		boolean flag = true;

			try {
				fileWriter = new FileWriter(destinationFile); 
				fileWriter.append(FILE_HEADER.toString()); // Write the CSV file header
				fileWriter.append(NEW_LINE_SEPARATOR);
				while(flag) {
					flag = csvFolderReader(fileWriter);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	// CSV to KML function using JAK API.
	public void writeFileKML() {
		try {
			csvToArrayList(destinationFile);
			Kml kml = KmlFactory.createKml(); // creating a new instance.
			Document document = kml.createAndSetDocument().withName("Placemarks");

			for (int i = 1; i < csvList.size(); i++) { // looping all elements in ArrayList.
				String[] s = csvList.get(i);
				String timeStampSimpleExtension = s[0];
				// create <Placemark> and set points and values.
				Placemark placemark = KmlFactory.createPlacemark();
				placemark.createAndSetTimeStamp().addToTimeStampSimpleExtension(timeStampSimpleExtension);
				document.createAndAddPlacemark().withName("Placemark" + i).withOpen(Boolean.TRUE)
				.withTimePrimitive(placemark.getTimePrimitive()).createAndSetPoint()
				.addToCoordinates(Double.parseDouble(s[3]), Double.parseDouble(s[2]), Double.parseDouble(s[4]));
			}
			kml.marshal(new File(sourceFolder + "\\newKml.kml")); // create file.
			System.out.println("KML file writing was successful.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Converting CSV file to an ArrayList. used by writeFileKML().
	private void csvToArrayList(String path) {
		// Reads CSV file from string input, than transfers all information to ArrayList.
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
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
}
