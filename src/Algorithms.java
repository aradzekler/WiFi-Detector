import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Vector;

import com.opencsv.*; // imported from library.

public class Algorithms {

	/** Find the weighted center point for maximum 3 networks from the same mac.
	 * @param mac		 desired mac address.
	 * @param filePath		 path of CSV file.
	 */
	public static double[] weightedCenterPointGrade(String mac, String filePath) {
		double latWeightSum = 0;
		double sigWeightSum = 0;
		double lonWeightSum = 0;
		double altWeightSum = 0;

		Vector<String> Vector = new Vector<String>(2, 2); // Create a new Vector.

		try (
				Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader);
				) {
			String[] nextRecord; // Store lines of CSV file.
			while ((nextRecord = csvReader.readNext()) != null) {
				for (int i = 0; i < nextRecord.length; i++) {
					int count = 0;
					if (nextRecord[i].equals(mac) && count < 4) { // Adding proper elements to vector.
						Vector.addElement(nextRecord[2]);
						Vector.addElement(nextRecord[3]);
						Vector.addElement(nextRecord[4]);
						Vector.addElement(nextRecord[i+2]);
						count++;
					}
				}
			} // End of reading.
			// Calculation.
			for (int i = 0; i < Vector.size(); i += 4) {
				double temp = 0;
				temp = Double.parseDouble(Vector.get(i)); // Calculating weight for lat.
				latWeightSum += (1/(temp * temp));
			}
			for (int i = 1; i < Vector.size(); i += 4) {
				double temp = 0;
				temp = Double.parseDouble(Vector.get(i)); // Calculating weight for lon.
				lonWeightSum += (1/(temp * temp));
			}
			for (int i = 2; i < Vector.size(); i += 4) {
				double temp = 0;
				temp = Double.parseDouble(Vector.get(i)); // Calculating weight for alt.
				altWeightSum += (1/(temp * temp));
			}
			for (int i = 3; i < Vector.size(); i += 4) { 
				double temp = 0;
				temp = Double.parseDouble(Vector.get(i)); // Calculating weight for signal.
				sigWeightSum += (1/(temp * temp));
			}

			Enumeration<String> vEnum = Vector.elements(); // Printing the vector properly.
			while(vEnum.hasMoreElements())
				System.out.print(vEnum.nextElement() + " ");
			System.out.println();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		double[] array = {latWeightSum, lonWeightSum, altWeightSum, sigWeightSum};
		return array;
	}
}
