package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DB.OfficeManager;

/**
 * Servlet implementation class AddOfficeServlet
 */
@WebServlet("/AddOfficeServlet")
public class AddOfficeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOfficeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OfficeManager om = new OfficeManager();
		int userId = Integer.valueOf((String)request.getParameter("heads"));
		String officeName = (String) request.getParameter("officeName");
		int officeId = om.addOffice(officeName);
		om.setOfficeHead(userId, officeId);
		response.sendRedirect("addoffice.jsp");
	}

}
