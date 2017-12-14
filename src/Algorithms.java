import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.*; // imported from library.

public class Algorithms extends csvWriter{

	public Algorithms() {
		super(sourceFolder, destinationFile);
	}


	// parameters.
	private static Vector<Vector<String>> vector = new Vector<Vector<String>>(10, 10);
	private final static int POWER = 2;
	private final static int NORM = 10000;
	private final static double SIGNALDIFF = 0.4;
	private final static int MINDIFF = 3;
	private final static int NOSIGNAL = -120;
	private final static int DIFFNOSIGNAL = 100;
	

	
	// nned to design a reader that reads all entries from csv and uses weightedcetner of each mac.
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
						weightedCenterPointGrade(nextRecord[i]);
					}
				}
			}
			printVectorOfVectors(vector); 
			reader.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return vector;
	}

	/** Find the weighted center point for maximum 3 networks from the same mac.
	 * @param mac		 desired mac address.
	 * @param filePath		 path of CSV file.
	 */
	private static void weightedCenterPointGrade(String mac) {
		double latWeightSum = 0;
		double sigWeightSum = 0;
		double lonWeightSum = 0;
		double altWeightSum = 0;

		Vector<Vector<String>> Vector = recordsToVectorByMac(mac);
		filterStrongest(Vector);
		System.out.println("");
		printVectorOfVectors(Vector);

		// Calculation.
		double temp = 0;
		if (!isMacInsideVector(mac, vector)) {
			for (int i = 0; i < Vector.size(); i++) {
				for (int j = 2; j < Vector.get(i).size(); j++) {
					switch(j) {
					case 2: 
						temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for lat.
						latWeightSum += (1/(temp * temp));
						break;
					case 3:
						temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for lon.
						lonWeightSum += (1/(temp * temp));
						break;
					case 4:
						temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for alt.
						altWeightSum += (1/(temp * temp));
						break;
					case 6:
						temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for signal.
						sigWeightSum += (1/(temp * temp));
						break;
					}
				}
			}
			Vector<String> vec = new Vector<String>();
			vec.add(mac);
			vec.add(Double.toString(latWeightSum));
			vec.add(Double.toString(lonWeightSum));
			vec.add(Double.toString(altWeightSum));
			vec.add(Double.toString(sigWeightSum));
			double[] array = {latWeightSum, lonWeightSum, altWeightSum, sigWeightSum};
			vector.add(vec);
		}
	}

	private static boolean isMacInsideVector(String mac, Vector<Vector<String>> vectorOfVecs) {
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
	
	

	private static Vector<Vector<String>> recordsToVectorByMac(String mac)  {
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
			printVectorOfVectors(vecOfVecs); 
			reader.close();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return vecOfVecs;
	}

	private static void printVectorOfVectors(Vector<Vector<String>> v) {
		String m = "";
		for (int i = 0; i < v.size(); i++){
			for (int j = 0; j < v.get(i).size(); j++) {
				m = v.get(i).get(j);
				System.out.print(m + " ");
			}
			System.out.println();
		}
	}

	private static void filterStrongest(Vector<Vector<String>> vectorOfVecs) {
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
			if (Integer.parseInt(vectorOfVecs.get(i).lastElement()) < max3) {
				vectorOfVecs.remove(i);
				--i;
			}
		}
	}

	public void algorithm2() {
		Vector<Vector<String>> strongestVector = recordsToVectorByMac();
		Vector<Vector<String>> vector = recordsToVectorByMac();
		filterStrongest(strongestVector);

		for (int i = 0; i < strongestVector.size(); i++) {
			for (int j = 0; j < vector.size(); j++) {
				int diff = 0;
				double weight = 0.0;

				if (Integer.parseInt(vector.get(j).lastElement()) > NOSIGNAL  
						&& Integer.parseInt(vector.get(j).lastElement()) < DIFFNOSIGNAL) {
					if (!vector.get(j).equals(strongestVector.get(i))) {
						diff = Math.max(Math.abs(Integer.parseInt(vector.get(j).lastElement()) - Integer.parseInt(strongestVector.get(i).lastElement())), MINDIFF);
						weight = NORM / (Math.pow((double)diff, SIGNALDIFF) * Math.pow(Double.parseDouble(strongestVector.get(i).lastElement()), POWER));
						vector.get(j).addElement(Double.toString(weight));
					}
					else {
						continue;
					}
				}
			}
		}
		double sumWeight = 0.0;
		for (int i = 0; i < strongestVector.size(); i++) {
			double totalWeight = 1.0;
			for (int j = 0; j < vector.size(); j++) {
				totalWeight *= Double.parseDouble(vector.get(j).lastElement());
				sumWeight += Double.parseDouble(vector.get(j).lastElement());
			}
			strongestVector.get(i).addElement(Double.toString(totalWeight));
		}
		for (int i = 0; i < strongestVector.size(); i++) {
			double weight = Double.parseDouble(strongestVector.get(i).lastElement());
			double latWeight = Double.parseDouble(strongestVector.get(i).get(0)) * weight;
			double lonWeight = Double.parseDouble(strongestVector.get(i).get(1)) * weight;
			double altWeight = Double.parseDouble(strongestVector.get(i).get(2)) * weight;

			strongestVector.get(i).addElement(Double.toString(latWeight)); //5
			strongestVector.get(i).addElement(Double.toString(lonWeight)); //6
			strongestVector.get(i).addElement(Double.toString(altWeight)); //7

		}
		double totalLatWeight = 0.0;
		double totalLonWeight = 0.0;
		double totalAltWeight = 0.0;
		for (int i = 0; i < strongestVector.size(); i++) {
			totalLatWeight += Double.parseDouble(vector.get(i).get(5));
			totalLonWeight += Double.parseDouble(vector.get(i).get(6));
			totalAltWeight += Double.parseDouble(vector.get(i).get(7));
		}
		totalLatWeight = totalLatWeight / sumWeight;
		totalLonWeight = totalLonWeight / sumWeight;
		totalAltWeight = totalAltWeight / sumWeight;
		printVectorOfVectors(vector);
	}
}

