package xyz.veracode.verainsecure;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//CWE 80 Sanitizer
import org.owasp.encoder.*;


public class XSS extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XSS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @return 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter writer = res.getWriter();
		
		//CWE 80: Script Tags
		
		String username = req.getParameter("xssusername");
		String htmlResponse = "<html>";
		htmlResponse += "<h2>Your xss vulnerable username is: " + username + "<br/>";
		htmlResponse += "</html>";
		writer.println(htmlResponse);
		
        	 
		//Protect against CWE 80

		String sanitizedUsername = Encode.forJavaScript(req.getParameter("xssusername")); 
		String cleanHTMLResponse = "<html>";
		cleanHTMLResponse += "<h2>Your non xss vulnerable username is: " + sanitizedUsername + "<br/>";
		cleanHTMLResponse += "</html>";
		writer.println(cleanHTMLResponse);

	}

}
