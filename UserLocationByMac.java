import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import com.opencsv.CSVReader;

public class UserLocationByMac extends csvWriter implements AlgorithmsInterface {
	private static int NoSignal =-120;
	private static double sigDiff=0.4;
	private static int norm =10000;
	private static int minDiff =3;
	private static int diffNoSignal =100;
	private static int Power =2;

	public class macInfo
	{
		String mac;
		double macAlt;
		double macLat;
		double macLon;
		int macSignal;
	}

	public UserLocationByMac() {
		super(sourceFolder, destinationFile);
	}

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


	private String isMacInsideVector(String mac, Vector<Vector<String>> vectorOfVecs) {
		if (vectorOfVecs == null) {
			return null;
		}
		for (int i = 0; i < vectorOfVecs.size(); i++) {
			for (int j = 0; j < vectorOfVecs.get(i).size(); j++)
				if (vectorOfVecs.get(i).get(j).equals(mac)) {
					return mac;
				}
		}
		return null;
	}





	private double CalculateMac(macInfo mac1,Vector <macInfo> macs)
	{

		double w;
		int diff,signal,result;
		result=Math.abs(signal-mac1.macSignal);
		if(result<=3)
		{
			diff=minDiff;
		}
		else if(signal==NoSignal)
		{
			diff=diffNoSignal;
		}
		else
		{
			diff=result;
		}




	}


	@Override
	public Vector<Vector<String>> recordsToVector() {

		return null;
	}



}
