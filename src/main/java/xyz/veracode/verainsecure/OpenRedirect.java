package xyz.veracode.verainsecure;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//CWE 113 Sanitizer
import java.net.URLEncoder;

/**
 * Servlet implementation class OpenRedirect
 */
public class OpenRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenRedirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getQueryString();
		
		//CWE-113: Improper Neutralization of CRLF Sequences in HTTP Headers ('HTTP Response Splitting')
		//
		//CWE 601: Open Redirects
		//For example http://localhost:8085/exampleflaws/openredirect?unvalidatedurl=http://www.google.com
		if (query.contains("unvalidatedurl")) {
			String url = request.getParameter("unvalidatedurl");
			response.sendRedirect(url);
		}
		
		//Stop CWE 113 and create Best Practice with Veracode Approved Library
		//Stop CWE 601 with Veracode Approved Library
		if (query.contains("validatedurl")) {
			String validatedurl = request.getParameter("validatedurl");
			response.sendRedirect(URLEncoder.encode(validatedurl, "UTF-8"));
		}
	}
}
