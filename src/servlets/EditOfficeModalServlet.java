package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Office;
import Model.DB.OfficeManager;

@WebServlet("/EditOfficeModalServlet")
public class EditOfficeModalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditOfficeModalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OfficeManager om = new OfficeManager();
		int officeId = Integer.valueOf(request.getParameter("officeId"));
		String officeName = request.getParameter("officeName");
		om.editOffice(officeId, officeName);
	}
}
