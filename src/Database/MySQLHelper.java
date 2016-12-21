package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLHelper {

	private Connection conn = null;
	private Statement stmt = null;
	private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private String DB_URL;
	private String USER;
	private String PASS;

	public MySQLHelper(String DB_URL, String USER, String PASS) {
		this.DB_URL = DB_URL;
		this.USER = USER;
		this.PASS = PASS;
	}

	public boolean Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean Close() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(MySQLHelper.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	public ResultSet Select(String sql) throws SQLException {
		Connect();
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	public boolean Update(String sql) {
		Connect();
		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return false;
	}

	public boolean Insert(String sql) {
		Connect();
		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return false;
	}

	public boolean Delete(String sql) {
		Connect();
		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		Close();
		return false;
	}
}
