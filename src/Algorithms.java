import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import com.opencsv.*; // imported from library.

public class Algorithms extends csvWriter{

	public Algorithms() {
		super(sourceFolder, destinationFile);
	}

	public Algorithms(String mac) {
		super(sourceFolder, destinationFile);
		this.mac = mac;
	}

	// parameters.
	private static String mac;
	private final static int POWER = 2;
	private final static int NORM = 10000;
	private final static double SIGNALDIFF = 0.4;
	private final static int MINDIFF = 3;
	private final static int NOSIGNAL = -120;
	private final static int DIFFNOSIGNAL = 100;

	/** Find the weighted center point for maximum 3 networks from the same mac.
	 * @param mac		 desired mac address.
	 * @param filePath		 path of CSV file.
	 */
	public static double[] weightedCenterPointGrade() {
		double latWeightSum = 0;
		double sigWeightSum = 0;
		double lonWeightSum = 0;
		double altWeightSum = 0;

		Vector<Vector<String>> Vector = recordsToVector();
		filterStrongest(Vector);
		System.out.println("");
		printVectorOfVectors(Vector);

		// Calculation.
		double temp = 0;

		for (int i = 0; i < Vector.size(); i++) {
			for (int j = 0; j < Vector.get(i).size(); j++) {
				switch(j) {
				case 0: 
					temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for lat.
					latWeightSum += (1/(temp * temp));
					break;
				case 1:
					temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for lon.
					lonWeightSum += (1/(temp * temp));
					break;
				case 2:
					temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for alt.
					altWeightSum += (1/(temp * temp));
					break;
				case 3:
					temp = Double.parseDouble(Vector.get(i).get(j)); // Calculating weight for signal.
					sigWeightSum += (1/(temp * temp));
					break;
				}
			}
		}
		double[] array = {latWeightSum, lonWeightSum, altWeightSum, sigWeightSum};
		return array;
	}

	private static Vector<Vector<String>> recordsToVector()  {
		Vector<Vector<String>> vecOfVecs = new Vector<Vector<String>>();

		try (
				Reader reader = Files.newBufferedReader(Paths.get(getDestinationFile()));
				CSVReader csvReader = new CSVReader(reader);
				) {
			String[] nextRecord; // Store lines of CSV file.
			while ((nextRecord = csvReader.readNext()) != null) {
				for (int i = 0; i < nextRecord.length; i++) {
					if (nextRecord[i].equals(mac)) { // Adding proper elements to vector.
						Vector<String> Vector = new Vector<String>(4, 4);
						Vector.addElement(nextRecord[2]);
						Vector.addElement(nextRecord[3]);
						Vector.addElement(nextRecord[4]);
						Vector.addElement(nextRecord[i+2]);
						vecOfVecs.add(Vector);
					}
				}
			}
			printVectorOfVectors(vecOfVecs); 
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

	private static void filterStrongest(Vector<Vector<String>> vector) {
		int max1 = NOSIGNAL, max2 = NOSIGNAL, max3 = NOSIGNAL;

		for (int i = 0; i < vector.size(); i++) {
			for (int j = 3; j < vector.get(i).size(); j+=4) {
				if (Integer.parseInt(vector.get(i).get(j)) > max1) {
					max3 = max2; max2 = max1; max1 = Integer.parseInt(vector.get(i).get(j));
				}
				else if (Integer.parseInt(vector.get(i).get(j)) > max2) {
					max3 = max2; max2 = Integer.parseInt(vector.get(i).get(j));
				}
				else if (Integer.parseInt(vector.get(i).get(j)) > max3) {
					max3 = Integer.parseInt(vector.get(i).get(j));
				}
				else if (Integer.parseInt(vector.get(i).get(j)) < max3) {
					vector.remove(i);
				}
			}
		}
		for (int i = 0; i < vector.size(); i++) {
			if (Integer.parseInt(vector.get(i).lastElement()) < max3) {
				vector.remove(i);
				--i;
			}
		}
	}

	private static void algorithm2() {
		Vector<Vector<String>> strongestVector = recordsToVector();
		Vector<Vector<String>> vector = recordsToVector();
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
			double sigWeight = Double.parseDouble(strongestVector.get(i).get(3)) * weight;
			
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
	}
}
