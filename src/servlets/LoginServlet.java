package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("Error", "Invalid");
		request.getRequestDispatcher("personnellogin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller m = new Controller();
		User user = m.getUser(request.getParameter("email"), request.getParameter("password"));
		
		if(user != null)
		{
			response.addCookie(new Cookie("user", user.getEmail()));
			if(user.getType().equals("administrator"))
				response.sendRedirect("adminmenu.jsp");
			if(user.getType().equals("service personnel"))
				response.sendRedirect("generate.jsp");
			if(user.getType().equals("officehead"))
			{
				request.getSession().setAttribute("Offices", m.getOfficeHeadOffices(user.getID()));
				request.getSession().setAttribute("User", user);
				response.sendRedirect("officeheadmenu.jsp");
			}
		}
			
		else
			doGet(request, response);
	}

}
