import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
				for (int i = 0; i < 44 && nextRecord[i+1] != null; i++) { // problem here, cancels loop after meeting null.
					if (nextRecord[i].equals(mac)) {
						v.addElement(nextRecord[i]);
						v.addElement(nextRecord[i+2]);
					}
				}
			}
			Enumeration<String> vEnum = v.elements();
			while(vEnum.hasMoreElements())
				System.out.print(vEnum.nextElement() + " ");
			System.out.println();
		} catch (IOException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
	}
}
