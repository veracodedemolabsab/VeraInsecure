package xyz.veracode.verainsecure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

//CWE 113 Sanitizer

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
		//CWE 601: Open Redirects
		//For example http://localhost:7001/exampleflaws/openredirect?badurl=http%3A%2F%2Fwww.google.com%0A
		if (query.contains("badurl")) {
			String url = request.getParameter("badurl");
			response.sendRedirect(url);
		}
		
		//Stop CWE 113 and create Best Practice with Veracode Approved Library
		//Stop CWE 601 with Veracode Approved Library
		if (query.contains("goodurl")) {
			String url = request.getParameter("goodurl");
			response.sendRedirect(URLEncoder.encode(url, "UTF-8"));
		}
	}
}
