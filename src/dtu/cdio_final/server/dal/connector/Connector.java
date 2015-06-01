package dtu.cdio_final.server.dal.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

	private static Connector instance;

	private Connection conn;
	private Statement stm;

	private Connector(String server, int port, String database, String username, String password) throws SQLException {
		try {
			// call the driver class' no argument constructor
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// get Connection-object via DriverManager
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, username,
					password);

			stm = conn.createStatement();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new SQLException(e);
		}
	}

	private Connector() throws SQLException {
		this(Constant.server, Constant.port, Constant.database, Constant.username, Constant.password);
	}

	private static Connector getInstance() throws SQLException {
		if (instance == null)
			instance = new Connector();
		return instance;
	}

	public static void connect(String server, int port, String database, String username, String password) throws SQLException {
		if (instance == null)
			instance = new Connector(server, port, database, username, password);
	}

	public static void close() {
		try {
			getInstance().conn.close();
		} catch (SQLException e) {}
		instance = null;
	}
	
	// methods for SQL
	public static ResultSet doQuery(String cmd) throws SQLException {
		return getInstance().stm.executeQuery(cmd);
	}

	public static int doUpdate(String cmd) throws SQLException {
		return getInstance().stm.executeUpdate(cmd);
	}

	public static PreparedStatement prepare(String sql) throws SQLException {
		return getInstance().conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	public static int getLastInsert(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
}
