package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

@WebServlet("/EditOfficesBackServlet")
public class EditOfficesBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditOfficesBackServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
		
		request.getSession().setAttribute("Offices", con.getAllOffices());
		
		response.sendRedirect("Offices.jsp");
	}

}
