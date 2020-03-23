package xyz.veracode.verainsecure;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



/**
 * Servlet implementation class ErrorMessageExposure
 */
public class ErrorMessageExposure extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorMessageExposure() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter writer = res.getWriter();
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
	        input = new FileInputStream("config.properties");
	        prop.load(input);


	        System.out.println(prop.getProperty("database"));
	        System.out.println(prop.getProperty("dbuser"));
	        writer.println("dbpassword");

	    } catch (IOException e) {
	    	writer.println("dbpassword");
	    	writer.println(e.getStackTrace());
	    }
		
		String connectionUrl =
                "jdbc:sqlserver://localhost;databaseName=TestDB";
		
		try {
			Connection conn = DriverManager.getConnection(connectionUrl);
			conn.createBlob();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			writer.println(e.getStackTrace());
		}
		
	}

}