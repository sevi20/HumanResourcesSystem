import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.swing.JComboBox;

public class DBHelper {
	
	public static Connection conn = null;
	public static MyModel model = null;
	public static PreparedStatement state = null;
	public static ResultSet result = null;
    private static String dbPath = "";
    private static String dbDriver = "";
    private static String dbUsername = "";
    private static String dbPassword = "";

 	public static void init() {
 		Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/config/config.properties"); //TODO това за сега върши рабора, обаче като е jar?
            properties.load(inputStream);
            dbPath = properties.getProperty("db_path");
            dbDriver = properties.getProperty("db_driver");
            dbUsername = properties.getProperty("db_username");
            dbPassword = properties.getProperty("db_password");

        } catch (FileNotFoundException e) { //
            try {
                File file = new File("src/config/config.properties");
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                properties.setProperty("db_path", "");
                properties.setProperty("db_driver", "");
                properties.setProperty("db_username", "");
                properties.setProperty("db_password", "");
                properties.store(fileOutputStream, "properties");

            } catch (IOException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace(); //TODO create config or show error|create default config
        } catch (IOException e) {
            e.printStackTrace();
        }
 	}
	
 	public static Connection getConnection() {
	
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbPath,dbUsername, dbPassword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}//end getConnection();
	
 	
	static void fillPositionCombo(JComboBox<String> combo, List<Long> idPosition) {
		
		conn = getConnection();
		String sql = "SELECT POSITION_ID, POSITION_NAME FROM POSITIONS";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			while(result.next()) { 
				idPosition.add(result.getLong(1));
				combo.addItem(result.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end PositionCombo
	
	static void fillContractCombo(JComboBox<String> combo, List<Long> idContract) {
		conn = getConnection();
		String sql = "SELECT CONTRACT_ID, CONTRACT_TYPE FROM CONTRACT";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			
			while(result.next()) {
				idContract.add(result.getLong(1));
				combo.addItem(result.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end ContractCombo
	
	static void fillEmployeeCombo(JComboBox<String> combo, List<Long> idEmployee) {
		conn = getConnection();
		String sql = "SELECT EMPLOYEE_ID, NAME FROM EMPLOYEES";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			
			while(result.next()) {
				String item = result.getObject(1).toString() + " " + result.getObject(2).toString();
				combo.addItem(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end ContractCombo

	static void fillReferenceComboPos(JComboBox<String> combo) {
		conn = getConnection();
		String sql = "SELECT POSITION_NAME FROM POSITIONS";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			
			while(result.next()) {
				String item = result.getObject(1).toString();
				combo.addItem(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end ContractCombo
	
	static void fillReferenceComboCon(JComboBox<String> combo) {
		conn = getConnection();
		String sql = "SELECT CONTRACT_TYPE FROM CONTRACT";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			
			while(result.next()) {
				String item = result.getObject(1).toString();
				combo.addItem(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end ContractCombo
	
	
	
	public static MyModel getAllData(String tableName) {
		
		conn = getConnection();
		try {
			state = conn.prepareStatement("SELECT * FROM " + tableName);
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}//end getAllData
	
	public static MyModel getEmployeeData(){
		
		conn = getConnection();
		try {
			state = conn.prepareStatement("SELECT EMPLOYEE_ID, NAME, DOB, EMAIL, CITY, POSITIONS.POSITION_NAME, CONTRACT.CONTRACT_TYPE \r\n" + 
					"FROM EMPLOYEES\r\n" + 
					"JOIN POSITIONS ON POSITIONS.POSITION_ID = EMPLOYEES.POSITION_ID\r\n" + 
					"JOIN CONTRACT ON CONTRACT.CONTRACT_ID = EMPLOYEES.CONTRACT_ID");
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}//end getAllData
	
}//end class 