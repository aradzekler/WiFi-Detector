import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class csvToKmlByTime extends csvToKml implements filterInterface { 

	private ArrayList<String[]> csvList;
	private String time;

	// constructer.
	public csvToKmlByTime(String sourceFolder, String destinationFile, String time) {// enter time in yyyy-mm-dd  hh:mm:ss format.
		super(sourceFolder, destinationFile);
		this.time = time;
	}
	
	// writes KML file filtered by given time. can take missing format arguments like yyyy or hh:mm:ss.
	@Override
	public void writeFileKML() { 
		try {
			csvToArrayList();
			Kml kml = KmlFactory.createKml(); // creating a new instance.
			Document document = kml.createAndSetDocument().withName("Placemarks");

			for (int i = 1; i < csvList.size(); i++) { // looping all elements in ArrayList.
				String[] s = csvList.get(i);
				if (s[0].contains(time)) {
					String timeStampSimpleExtension = s[0];
					// create <Placemark> and set points and values.
					Placemark placemark = KmlFactory.createPlacemark();
					placemark.createAndSetTimeStamp().addToTimeStampSimpleExtension(timeStampSimpleExtension);
					document.createAndAddPlacemark().withName("Placemark" + i).withOpen(Boolean.TRUE)
					.withTimePrimitive(placemark.getTimePrimitive()).createAndSetPoint()
					.addToCoordinates(Double.parseDouble(s[3]), Double.parseDouble(s[2]), Double.parseDouble(s[4]));
				}
				else {
					continue;
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
				String[] entries = line.split(COMMA);
				csvList.add(entries);
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