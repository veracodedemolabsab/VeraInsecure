package xyz.veracode.verainsecure;

import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;



//Shows File Path Injection
public class PathTraversal extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(PathTraversal.class.getName());

    public PathTraversal() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter writer = res.getWriter();
		String path = System.getProperty("catalina.home") + "/receipts/";
		
		//CWE 73: Control of File Path
		File receiptFile = new File(path + req.getParameter("receiptname") + ".txt");
		receiptFile.delete();
		//writer.println(path + req.getParameter("receiptname") + ".txt");
		
		//Fix CWE 73 via Custom Cleanser
		String customCleansedReceiptName = Utilities.cleanFilePath(req.getParameter("receiptname"));
		File customCleansedReceiptFile = new File(path + customCleansedReceiptName + ".txt");
		if(!customCleansedReceiptName.equals("reserved")){
			customCleansedReceiptFile.delete();
			writer.println("Valid File Name!");
		} else writer.println("Invalid File Name!");
		

	}

}
