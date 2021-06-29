package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
	private String dbHostName = "jdbc:mysql://database-1.cbf9mjnqgnfr.us-east-2.rds.amazonaws.com:3306/stock_trading_tracker";
	private String username = "admin";
	private String password = "Password123!";
	private Connection connection;
	private Statement statement;
	private ResultSet resultset;
	private ResultSetMetaData rsmd;
//	while (resultset.next()) {
//	System.out.println("stock symbol: "+resultset.getString("symbol"));
//	System.out.println("stock entryPrice: "+resultset.getString("entry_price"));
//	System.out.println("stock exitPrice: "+resultset.getString("exit_price"));
//  }
	public List<String> selectARecord(String query){
		List<String> list = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			resultset = statement.executeQuery(query);
			rsmd = resultset.getMetaData();
			resultset.next();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				list.add(resultset.getString(i));
			}
		} catch (SQLException e) {
		System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
		return list;
	}
	public void insertRecord(String query) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
	}
	public void updateRecord(String query) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
	}
	public void deleteRecord(String query) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
	}
}
