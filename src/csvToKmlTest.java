import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class csvToKmlTest {
	String sourcefile = ""; // CSV file full path.
	String source = ""; // Test folder full path.
	String kmlFilePath = ""; // KML file full path
	
	csvWriter newfile = new csvWriter(source, sourcefile);

	boolean checkFileExist(String filePath) { // Checking if file exists.
		File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) { 
			return true;
		}
		return false;
	}

	@Test
	void testWriteFileKML() { // Checks if KML file was created.
		csvToKml newkml = new csvToKml(source, sourcefile);
		newkml.writeFileKML();
		assertEquals(true, checkFileExist(kmlFilePath));
	}
	/* No need to test private method csvToArrayList() as 
	 * writeFileKml() functionality is completely based on it.
	 */
}
