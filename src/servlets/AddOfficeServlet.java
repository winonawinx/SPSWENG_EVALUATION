package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.DB.OfficeManager;

@WebServlet("/AddOfficeServlet")
public class AddOfficeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	public AddOfficeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller con = new Controller();
		OfficeManager om = new OfficeManager();
		int userId = Integer.valueOf((String)request.getParameter("heads"));
		String officeName = (String) request.getParameter("officeName");
		//int officeId = om.addOffice(officeName);
		//om.setOfficeHead(userId, officeId);
		//request.getSession().setAttribute("Office", con.getOffice(officeId));
		request.getSession().setAttribute("Office", con.getOffice(9));
		response.sendRedirect("createform.jsp");
	}

}
