import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

class csvToKmlTest {
	String sourcefile = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newfile.csv";
	String source = "C:\\Users\\Arad Zekler\\Desktop\\test folder";
	csvWriter newfile = new csvWriter(source, sourcefile);
	String kmlFilePath = "C:\\Users\\Arad Zekler\\Desktop\\test folder\\newKml.kml";

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

}