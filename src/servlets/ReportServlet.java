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
 * Servlet implementation class ReportServlet
 */
@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int office = 0;
    
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("personnellogin.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller m = new Controller();
		office = Integer.parseInt((String) request.getParameter("answer"));
		System.out.println(request.getSession().getAttribute("User"));
		request.getSession().setAttribute("User", (User) request.getSession().getAttribute("User"));
		request.getSession().setAttribute("Office", m.getOffice(office));
		Cookie ofc = new Cookie("Office", String.valueOf(office));
        response.addCookie(ofc);
		request.getSession().setAttribute("Forms", m.getAllFormsByOffice(office));
		response.sendRedirect("report.jsp");
	}

}
