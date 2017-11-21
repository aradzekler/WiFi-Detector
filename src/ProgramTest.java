import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class ProgramTest {
	String sourcefile = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv";
	String source = "C:\\Users\\Arad Zekler\\Desktop\\test folder";
	

	@Test
	void testProgram() { // Asserts if the 2 files were created in paths.
		String kmlFilePath = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newKml.kml";
		Program newfile = new Program(sourcefile, sourcefile, source, "");
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
	void testCsvFolderReader() {
		fail("Not yet implemented");
	}

	@Test
	void testWriteFileKML() {
		fail("Not yet implemented");
	}

}
