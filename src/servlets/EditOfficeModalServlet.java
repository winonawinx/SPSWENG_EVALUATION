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
		String clicked = (String) request.getParameter("mhidden");
		response.addCookie(new Cookie("Office", (String) request.getParameter("ofcName")));
		
		if(clicked.equals("View Services"))
		{
			response.sendRedirect("viewservices.jsp");
		}	
		
	}

}
