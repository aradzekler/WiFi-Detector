import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.*; // imported from library.

public class ApproxLocationByMac extends csvWriter implements AlgorithmsInterface {

	public ApproxLocationByMac() {
		super(sourceFolder, destinationFile);
	}

	// parameters.
	private static Vector<Vector<String>> vector = new Vector<Vector<String>>(10, 10);
	private final static int NOSIGNAL = -120;

	// reads from CSV file and stores lines in vector of vectors.
	@Override
	public Vector<Vector<String>> recordsToVector()  {

		try (
				Reader reader = Files.newBufferedReader(Paths.get(getDestinationFile()));
				CSVReader csvReader = new CSVReader(reader);
				) {
			String[] nextRecord; // Store lines of CSV file.
			csvReader.readNext();
			while ((nextRecord = csvReader.readNext()) != null) {
				for (int i = 0; i < nextRecord.length; i++) {
					int count = StringUtils.countMatches(nextRecord[i], ':'); 
					if (count >= 4) {
						weightedCenterPointFirstAlgo(nextRecord[i]); // if the needed elements are existing,
					}											 // invoke algorithm on the record.
				}
			}
			printVectorOfVectors(vector); 
			reader.close(); // close reader.
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return vector;
	}

	/** for the strongest records from the same mac, act the algorithm on them and 
	 * return the calculated record.
	 * @param mac	desired mac address.
	 */
	private void weightedCenterPointFirstAlgo(String mac) {
		double latWeightSum = 0, sigWeightSum = 0, lonWeightSum = 0, altWeightSum = 0;
		Vector<Vector<String>> Vector = recordsToVectorByMac(mac); // build 2d vector filled with records from the same given mac.
		filterStrongest(Vector);	// filter the strongest records by signal.

		// calculation.
		double temp = 0;
		if (!isMacInsideVector(mac, vector)) { // checking if the mac isn't inside the vector already.
			for (int i = 0; i < Vector.size(); i++) {
				double tempSigWeightSum = 0, tempLonWeightSum = 0, tempLatWeightSum = 0,tempAltWeightSum = 0;
				try {
					temp = Double.parseDouble(Vector.get(i).get(6)); 
					sigWeightSum += (1/(temp * temp));	// adding weight to total weight.
					tempSigWeightSum = (1/(temp * temp));	// calculating weight for signal.

					temp = Double.parseDouble(Vector.get(i).get(2)); 
					tempLatWeightSum = temp * tempSigWeightSum;
					latWeightSum += tempLatWeightSum;

					temp = Double.parseDouble(Vector.get(i).get(3));
					tempLonWeightSum = temp * tempSigWeightSum;
					lonWeightSum += tempLonWeightSum;

					temp = Double.parseDouble(Vector.get(i).get(4));
					tempAltWeightSum = temp * tempSigWeightSum;
					altWeightSum += tempAltWeightSum;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			latWeightSum = latWeightSum / sigWeightSum;
			lonWeightSum = lonWeightSum / sigWeightSum;
			altWeightSum = altWeightSum / sigWeightSum;
			// end of calculation.
			Vector<String> vec = new Vector<String>();
			vec.add(mac); // adding results to returned vector
			vec.add(Double.toString(latWeightSum));
			vec.add(Double.toString(lonWeightSum));
			vec.add(Double.toString(altWeightSum));
			vec.add(Double.toString(sigWeightSum));
			vector.add(vec);
		}
	}

	/**
	 * simple function for checking if mac address is inside vector.
	 * @param mac passed mac address.
	 * @param vectorOfVecs 2d vector to check.
	 */
	private boolean isMacInsideVector(String mac, Vector<Vector<String>> vectorOfVecs) {
		if (vectorOfVecs == null) {
			return false;
		}
		for (int i = 0; i < vectorOfVecs.size(); i++) {
			for (int j = 0; j < vectorOfVecs.get(i).size(); j++)
				if (vectorOfVecs.get(i).get(j).equals(mac)) {
					return true;
				}
		}
		return false;
	}


	/**
	 * function reads from CSV file and putting desired elements inside 2d vector.
	 * @param mac passed mac address.
	 */
	private Vector<Vector<String>> recordsToVectorByMac(String mac)  {
		Vector<Vector<String>> vecOfVecs = new Vector<Vector<String>>();

		try (
				Reader reader = Files.newBufferedReader(Paths.get(getDestinationFile()));
				CSVReader csvReader = new CSVReader(reader);
				) {
			String[] nextRecord; // Store lines of CSV file.
			while ((nextRecord = csvReader.readNext()) != null) {
				for (int i = 0; i < nextRecord.length; i++) {
					if (nextRecord[i].equals(mac)) { // Adding proper elements to vector.
						Vector<String> Vector = new Vector<String>(10, 10);
						Vector.addElement(nextRecord[0]);
						Vector.addElement(nextRecord[1]);
						Vector.addElement(nextRecord[2]);
						Vector.addElement(nextRecord[3]);
						Vector.addElement(nextRecord[4]);
						Vector.addElement(nextRecord[i]);
						Vector.addElement(nextRecord[i+2]);
						vecOfVecs.add(Vector);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return vecOfVecs;
	}

	/**
	 * simple printing function.
	 * @param v 2d vector to print.
	 */
	private void printVectorOfVectors(Vector<Vector<String>> v) {
		String m = "";
		for (int i = 0; i < v.size(); i++){
			for (int j = 0; j < v.get(i).size(); j++) {
				m = v.get(i).get(j);
				System.out.print(m + " ");
			}
			System.out.println();
		}
	}

	/**
	 * function filters the 3 strongest mac records from given 2d vector.
	 * @param vectorOfVecs	2d vector to filter.
	 */
	private void filterStrongest(Vector<Vector<String>> vectorOfVecs) {
		int max1 = NOSIGNAL, max2 = NOSIGNAL, max3 = NOSIGNAL;

		for (int i = 0; i < vectorOfVecs.size(); i++) {

			if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) > max1) {
				max3 = max2; max2 = max1; max1 = Integer.parseInt(vectorOfVecs.get(i).lastElement());
			}
			else if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) > max2) {
				max3 = max2; max2 = Integer.parseInt(vectorOfVecs.get(i).lastElement());
			}
			else if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) > max3) {
				max3 = Integer.parseInt(vectorOfVecs.get(i).lastElement());
			}
			else if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) < max3) {
				vectorOfVecs.remove(i);
			}
		}
		for (int i = 0; i < vectorOfVecs.size(); i++) {
			if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) < max3) { // if smaller the number 3, delete.
				vectorOfVecs.remove(i);
				--i;
			}
		}
	}
}
