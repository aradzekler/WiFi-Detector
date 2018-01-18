import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;


import com.opencsv.CSVReader;

import algorithms.AlgorithmsInterface;

public class UserLocationByMac extends csvWriter implements AlgorithmsInterface {
	private static final int NoSignal =-120;
	private static final double sigDiff=0.4;
	private static final int norm =10000;
	private static final int minDiff =3;
	private static final int diffNoSignal =100;
	private static final int Power =2;

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


	private Vector<Vector<String>> isMacInsideVector(String mac1,String mac2,String mac3) {
		Vector<Vector<String>> MatOfMac=new Vector<Vector<String>> ();
		try {

			BufferedReader br= new BufferedReader(new FileReader(destinationFile));
			String st;
			
			while((st = br.readLine())!=null)
			{
				Vector <String> temp =new Vector <String>();
				String[] row = st.split(COMMA);
				for(int i=6;i<row.length;i+=4)
				{
					String macId = row[i];
					if(macId==null) {
						continue;
					}
					else if(macId==mac1)
					{
						temp.add(0, mac1);
						temp.add(1, row[i+2]);
					}
					else if(macId==mac2)
					{
						temp.add(2, mac2);
						temp.add(3, row[i+2]);
					}					
					else if(macId==mac3)
					{
						temp.add(4, mac3);
						temp.add(5, row[i+2]);
					}
					MatOfMac.add(temp);

				}

			}
			br.close();
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("File Not found");
		}
		return MatOfMac;
	}




public void CalculateUserPos(macInfo mac1,macInfo mac2,macInfo mac3)
{
	ApproxLocationByMac algo1= new ApproxLocationByMac();
	algo1.weightedCenterPointFirstAlgo(mac1.mac);
	algo1.weightedCenterPointFirstAlgo(mac2.mac);
	algo1.weightedCenterPointFirstAlgo(mac3.mac);
	Vector<Vector<String>> vector=ApproxLocationByMac.getVector();
	Vector<Vector<String>> FoundMacs =isMacInsideVector(mac1.mac, mac2.mac, mac3.mac);


	double pi[]=new double [FoundMacs.size()];
		int i=0;
	while(i<FoundMacs.size())
	{
		for (int j = 0; j < pi.length; j++) 
		{
			double diff=Math.abs(Double.parseDouble(FoundMacs.get(i).get(1))-mac1.Signal);
			
			
		}
		
		
	}
		int diff[]=new int[3];
		int signal,result;
		/*for(int i=1;i<3;i++)
		{
			result=Math.abs(mac1.Signal-vector.get())
			if(result<=3)
			{
				diff[i]=minDiff;
			}
			else if(vector.get(i)==NoSignal)
			{
				diff[i]=diffNoSignal;
			}
			else
			{
				diff[i]=result;
			}
		}


		w=norm/(Math.pow(diff, sigDiff)*Math.pow(signal, Power));*/





}


@Override
public Vector<Vector<String>> recordsToVector() {

	return null;
}



}
