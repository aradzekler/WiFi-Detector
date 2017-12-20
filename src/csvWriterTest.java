import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;


class csvWriterTest {
	String sourcefile = ""; // CSV file full path.
	String source = ""; // Test folder full path.
	String kmlFilePath = ""; // KML file full path
	
	csvWriter newfile = new csvWriter(source, sourcefile);

	@Test
	void testProgram() { 						
		csvWriter newfile = new csvWriter(source, sourcefile);	
		csvToKml newkml = new csvToKml(source, sourcefile);	
		newfile.csvWriteFile();
		newkml.writeFileKML();
		/* Asserts if the 2 files were created in paths.
		 * If the test passes all the main functions in
		 * the program are working properly.
		 */
		assertEquals(true, checkFileExist(sourcefile));
		assertEquals(true, checkFileExist(kmlFilePath));
	}

	boolean checkFileExist(String filePath) { // Checking if file exists.
		File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) { 
			return true;
		}
		return false;
	}

	@Test
	void testWriteFile() {
		csvWriter newfile = new csvWriter(source, sourcefile);	
		newfile.csvWriteFile();
		// Simple test to see if CSV file was created.
		assertEquals(true, checkFileExist(sourcefile));
	}
}
