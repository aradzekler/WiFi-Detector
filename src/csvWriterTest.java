import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class csvWriterTest {
	String sourcefile = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv";
	String source = "C:\\Users\\Arad Zekler\\Desktop\\test folder";
	csvWriter newfile = new csvWriter(source, sourcefile);
	String kmlFilePath = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newKml.kml";

	@Test
	void testProgram() { // Asserts if the 2 files were created in paths.
		csvWriter newfile = new csvWriter(source, sourcefile);
		csvToKml newkml = new csvToKml(source, sourcefile);
		newfile.csvWriteFile();
		newkml.writeFileKML();

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
		fail("Not yet implemented");
	}
}