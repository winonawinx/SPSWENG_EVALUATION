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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute("Error", "Invalid");
		request.getRequestDispatcher("personnellogin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller m = new Controller();
		System.out.println("UN " + request.getParameter("email"));
		System.out.println("PW " + request.getParameter("password"));
		User user = m.getUser(request.getParameter("email"), request.getParameter("password"));
		response.addCookie(new Cookie("user", user.getEmail()));
		if(user != null)
		{
			if(user.getType().equals("admin"))
				response.sendRedirect("adminmenu.jsp");
			if(user.getType().equals("service personnel"))
				response.sendRedirect("generate.jsp");
			if(user.getType().equals("officehead"))
			{
				request.getSession().setAttribute("Office", m.getOffice(m.getOfficeIDbyHead(user.getID())));
				request.getSession().setAttribute("Questions", m.getOfficeQuestions(m.getOfficeIDbyHead(user.getID())));
				response.sendRedirect("report.jsp");
			}
		}
			
		else
			doGet(request, response);
	}

}
