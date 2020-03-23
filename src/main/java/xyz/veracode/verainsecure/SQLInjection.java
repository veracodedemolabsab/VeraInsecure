package xyz.veracode.verainsecure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;


public class SQLInjection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SQLInjection() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Stuff to get
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String username = req.getParameter("sqlusername");
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream("config.properties")) {
		    props.load(resourceStream);
		}
		
		PrintWriter writer = res.getWriter();

		//sqlite3 connection
		Connection sqlite3Conn = null;
        try {
        	//CWE 245: J2EE Bad Practices: Direct Management of Connections
        	Class.forName("org.sqlite.JDBC");
            String sqliteUrl = "jdbc:sqlite:" + props.getProperty("sqlitedbpath");
            sqlite3Conn = DriverManager.getConnection(sqliteUrl);
		} catch (Exception e) {
            e.printStackTrace();
        }
		
        //postgres connection
//        Connection postgresConn = null;
//		try{
//          //Fix for CWE 245: J2EE Bad Practices: Direct Management of Connections
//			Class.forName("org.postgresql.Driver");
//			InitialContext ctx = new InitialContext();
//			String DB_DATASRC_REF = "jdbc:postgresql://localhost:5432/" + props.getProperty("postgresdb") + 
//			props.getProperty("postgresuser") + "/" + props.getProperty("postgrespassword");
//			DataSource datasource = (DataSource) ctx.lookup(DB_DATASRC_REF);
//			postgresConn = datasource.getConnection();
//			System.out.println("Connected to postgres");
//		 } catch (SQLException e) {
//			System.err.format(e.getMessage());
//		 } catch (Exception e) {
//            e.printStackTrace();
//       }		

		
		//CWE 89: SQL Injection for String for SQLite3
        //If username in database is tom try entering tom" OR "1"="1 into the user field
		String badSqlQuery = "SELECT username, balance, accounttype FROM users WHERE username=\"" + username + "\"";
		try {
			Statement badStmt = sqlite3Conn.createStatement();
			ResultSet rs = badStmt.executeQuery(badSqlQuery);
			while (rs.next()) {
				writer.println("Username: " + rs.getString("username") + "   Balance: "+ rs.getInt("balance") + "  AccountType: " + rs.getString("accounttype"));
			}
			sqlite3Conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		
		//Stop CWE 89 With Prepared Statements for SQLite3
        //If username in database is tom try entering tom" OR "1"="1 into the user field
//		String goodSqlQuery = "SELECT username, balance, accounttype FROM users WHERE username=?";
//		try {
//			PreparedStatement goodStmt = sqlite3Conn.prepareStatement(goodSqlQuery);
//			goodStmt.setString(1, username);
//			ResultSet rs = goodStmt.executeQuery();
//			if(rs.next() == false) {
//				writer.println("Invalid Username");
//			} else {
//				do {writer.println(("Username: " + rs.getString("username") + 
//						"   Balance: "+ rs.getInt("balance") + "  AccountType: " + rs.getString("accounttype")));
//				}
//				while (rs.next());
//			}
//			sqlite3Conn.close();
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
