package filters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import utils.csvWriter;

public class csvToKml extends csvWriter implements FilterInterface {
	final static String COMMA = ",";
	private ArrayList<String[]> csvList;

	//Constructor.
	public csvToKml(String sourceFolder, String destinationFile) {
		super(sourceFolder, destinationFile);
	}

	// CSV to KML function using JAK API.
	public void writeFileKML() {
		try {
			csvToArrayList();
			Kml kml = KmlFactory.createKml(); // creating a new instance.
			Document document = kml.createAndSetDocument().withName("Placemarks");

			for (int i = 1; i < csvList.size(); i++) { // looping all elements in ArrayList.
				try {
					String[] s = csvList.get(i);
					String timeStampSimpleExtension = s[0];
					// create <Placemark> and set points and values.
					Placemark placemark = KmlFactory.createPlacemark();
					placemark.createAndSetTimeStamp().addToTimeStampSimpleExtension(timeStampSimpleExtension);
					document.createAndAddPlacemark().withName("Placemark" + i).withOpen(Boolean.TRUE)
					.withTimePrimitive(placemark.getTimePrimitive()).createAndSetPoint()
					.addToCoordinates(Double.parseDouble(s[3]), Double.parseDouble(s[2]), Double.parseDouble(s[4]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			kml.marshal(new File(getSourceFolder() + "\\newKml.kml")); // create file.
			System.out.println("KML file writing was successful.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Converting CSV file to an ArrayList. used by writeFileKML().
	private void csvToArrayList() {
		// Reads CSV file from string input, than transfers all information to ArrayList.
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(getDestinationFile())));
			String line;
			while ((line = br.readLine()) != null) {
				boolean flag = true;
				String[] entries = line.split(COMMA);
				for (int i = 0; i < entries.length; i++) {
					if (entries[i] == null && i < 5) {
						flag = false;
					}
				}
				if (flag) {
					csvList.add(entries);
				}
			}
			br.close();
			System.out.println("CSV successfully converted to an ArrayList");
		} catch (IOException e) {
			System.out.println("CSV Reader Error.");
			e.printStackTrace();
		}
		this.csvList = csvList;
	}
}
