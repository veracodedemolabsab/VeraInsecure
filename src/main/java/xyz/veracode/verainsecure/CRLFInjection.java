package xyz.veracode.verainsecure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.net.URLEncoder;

//CWE 117 
import org.apache.log4j.*;



public class CRLFInjection extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static Logger logger = LogManager.getLogger(CRLFInjection.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRLFInjection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @return 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//CWE 117: Improper Output Neutralization of Logs
		String username = req.getParameter("crlfusername");
		logger.info(username);

		//Stop CWE 117 with Veracode Approved Library
		String sanitizedUsername = URLEncoder.encode(username, "UTF-8");
		logger.info(sanitizedUsername);
		
	}

}
