import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class ProgramTest {
	String sourcefile = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv";
	String source = "C:\\Users\\Arad Zekler\\Desktop\\test folder";
	Program newfile = new Program(sourcefile, source);


	@Test
	void testProgram() { // Asserts if the 2 files were created in paths.
		String kmlFilePath = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newKml.kml";
		Program newfile = new Program(sourcefile, source);
		newfile.csvFolderReader();
		newfile.writeFileKML();

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

	@Test
	void testCsvFolderReader() { // Checks if CSV file was created.
		newfile.csvFolderReader();
		assertEquals(true, checkFileExist(sourcefile));
	}

	@Test
	void testWriteFileKML() { // Checks if KML file was created.
		String kmlFilePath = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newKml.kml"; 
		Program newfile = new Program(sourcefile, source);
		newfile.csvFolderReader();
		newfile.writeFileKML();

		assertEquals(true, checkFileExist(kmlFilePath));
	}

}
