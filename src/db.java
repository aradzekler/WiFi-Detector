package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class db {
	private static String dbSourceFolder = "";
	private static String csvDestinationFile = "";

	public db(String dbSourceFolder, String csvDestinationFile) {
		db.dbSourceFolder = dbSourceFolder;
		db.csvDestinationFile = csvDestinationFile;
	}

	public static void main(String[] args) throws IOException {
		db DB = new db("/home/arad/eclipse-workspace/OOP1/src/db/storage.db","/home/arad/eclipse-workspace/OOP1/testfolder1/csvfile.csv");
		db.createNewTableFromCsv();
	}


	public static void createNewTableFromCsv() {
		String line = "";
		String cvsSplitBy = ",";
		BufferedReader br = null;

		// SQLite connection string
		String url = "jdbc:sqlite:" + dbSourceFolder;

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS storage ("
				+ " Time text NOT NULL, ID text NOT NULL, Lat text, Lon text, Alt text,"
				+ " SSID1 text, MAC1 text, Frequncy1 text, Signal1 text,"
				+ " SSID2 text, MAC2 text, Frequncy2 text, Signal2 text,"
				+ " SSID3 text, MAC3 text, Frequncy3 text, Signal3 text,"
				+ " SSID4 text, MAC4 text, Frequncy4 text, Signal4 text,"
				+ " SSID5 text, MAC5 text, Frequncy5 text, Signal5 text,"
				+ " SSID6 text, MAC6 text, Frequncy6 text, Signal6 text,"
				+ " SSID7 text, MAC7 text, Frequncy7 text, Signal7 text,"
				+ " SSID8 text, MAC8 text, Frequncy8 text, Signal8 text,"
				+ " SSID9 text, MAC9 text, Frequncy9 text, Signal9 text,"
				+ " SSID10 text, MAC10 text, Frequncy10 text, Signal10 text"
				+ ");";

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			br = new BufferedReader(new FileReader(csvDestinationFile));
			br.readLine(); 
			br.readLine();// reading 2 lines to avoid headers.
			while ((line = br.readLine()) != null) {
				String[] table = line.split(cvsSplitBy);
				String s ="INSERT INTO storage (" + valuesToString(table.length) + ") VALUES ("; // SQL statement to insert rows.
				for (int i = 0; i < table.length; i++) {
					s += "'"+table[i]+"'" + ",";
				}
				s = s.substring(0, s.length() - 1);
				s+=");";
				stmt.execute(s);
				System.out.println("SUCCESS");
			}
		}
		catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
	// helper function to dynamically state how much columns to add.
	private static String valuesToString(int length) {
		String str = "Time, ID, Lat, Lon, Alt,";
		int count = 5;
		for (int i = 1; i <= 10 && count < length; i++) {
			str += "SSID"+i;
			count++;
			str+=", MAC"+i;
			count++;
			str+=", Frequncy"+i;
			count++;
			str+=", Signal"+i+",";
			count++;
		}

		str = str.substring(0, str.length() - 1);
		return str;
	}
}
