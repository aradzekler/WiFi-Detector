import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import com.opencsv.*;

public class Algorithms {

	private static String mac;


	public static void function1(String mac, String filePath) {
		Vector<String> v = new Vector<String>(2, 2);

		try (
				Reader reader = Files.newBufferedReader(Paths.get(filePath));
				CSVReader csvReader = new CSVReader(reader);
				) {
			// Read CSV Records One by One into a String array
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (int i = 0; i < 41 || nextRecord[i] != ""; i++) {
					if (nextRecord[i].equals(mac)) {
						v.addElement(nextRecord[i]);
						v.addElement(nextRecord[i+1]);
					}
				}
			}
			Enumeration<String> vEnum = v.elements();
			System.out.println("\nElements in vector:");

			while(vEnum.hasMoreElements())
				System.out.print(vEnum.nextElement() + " ");
			System.out.println();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
}
