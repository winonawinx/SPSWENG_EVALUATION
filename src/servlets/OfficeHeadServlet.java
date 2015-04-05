package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

@WebServlet("/OfficeHeadServlet")
public class OfficeHeadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OfficeHeadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller controller = new Controller();
		String choice = (String)request.getParameter("pindot");
		response.addCookie(new Cookie("Office", choice));
		
		response.sendRedirect("report.jsp");
	
	}

}
