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
public class Receipt extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(Receipt.class.getName());

    public Receipt() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter writer = res.getWriter();
		String receiptFileName = req.getParameter("receiptname");
		
		//CWE 73: Control of File Path
		File receiptFile = new File("/user/profile/receipts/" + receiptFileName + ".txt");
		receiptFile.delete();
		
		//Fix CWE 73 via Custom Cleanser
		String customCleansedReceiptName = Utilities.cleanFilePath(receiptFileName);
		File customCleansedReceiptFile = new File("/user/profile/receipts/" + customCleansedReceiptName + ".txt");
		if(!customCleansedReceiptName.equals("reserved")){
			customCleansedReceiptFile.delete();
			writer.println("Valid File Name!");
		} else writer.println("Invalid File Name!");
		

	}

}
