import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import com.opencsv.CSVReader;

public class UserLocationByMac extends csvWriter implements AlgorithmsInterface {
	private static final int NoSignal =-120;
	private static final double sigDiff=0.4;
	private static final int norm =10000;
	private static final int minDiff =3;
	private static final int diffNoSignal =100;
	private static final int Power =2;

	private static Vector<Vector<String>> vector=ApproxLocationByMac.getVector();
	
	public class macInfo
	{
		String mac;
		double Alt;
		double Lat;
		double Lon;
		int Signal=-120;
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

	public double CalculateUserPos(macInfo mac1,macInfo mac2,macInfo mac3)
	{
		ApproxLocationByMac algo1 =new ApproxLocationByMac();
		
		double w1,w2,w3,Pi;
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
		w=norm/(Math.pow(diff, sigDiff)*Math.pow(signal, Power));
		return w;
		




	}


	@Override
	public Vector<Vector<String>> recordsToVector() {

		return null;
	}



}
