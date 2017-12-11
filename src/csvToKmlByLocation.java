import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class csvToKmlByLocation extends csvToKml implements filterInterface { 

	private ArrayList<String[]> csvList;
	private double lat;
	private double lon;
	private double radius;

	/** Constructor.
	 * @param sourceFolder enter the source folder's full path.
	 * @param destinationFile enter the csv file full path.
	 * @param lat latitude
	 * @param lon lontitude
	 * @param radius radius from center point.
	 */
	public csvToKmlByLocation(String sourceFolder, String destinationFile, double lat, double lon, double radius) {
		super(sourceFolder, destinationFile);
		this.lat = lat;
		this.lon = lon;
		this.radius = radius;
	}

	@Override
	// writes KML file filtered by given point and radius.
	public void writeFileKML() { 
		try {
			csvToArrayList();
			Kml kml = KmlFactory.createKml(); // creating a new instance.
			Document document = kml.createAndSetDocument().withName("Placemarks");

			for (int i = 1; i < csvList.size(); i++) { // looping all elements in ArrayList.
				String[] s = csvList.get(i);

				double distance = calculateDistance(lat, lon, Double.parseDouble(s[2]), Double.parseDouble(s[3]));

				if (distance <= radius) {
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

	/** calculates distance between 2 points.
	 * @param centerX x coordinate of center point.
	 * @param center y coordinate of center point
	 * @param pointX x coordinate of given point.
	 * @param pointY y coordinate of given point.
	 */
	private double calculateDistance(double centerX, double centerY, double pointX, double pointY) {
		double x = Math.pow((pointX - centerX), 2);
		double y = Math.pow((pointY - centerY), 2);
		double distance = Math.sqrt(x - y);

		return distance;
	}
}
