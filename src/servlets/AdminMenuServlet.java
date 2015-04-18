package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

@WebServlet("/AdminMenuServlet")
public class AdminMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminMenuServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String clicked = (String) request.getParameter("click");
		
		Controller m = new Controller();
		request.getSession().setAttribute("Offices", m.getAllOffices());
		if(clicked.equals("Offices"))
			response.sendRedirect("Offices.jsp");
		if(clicked.equals("Users"))
			response.sendRedirect("addUser.jsp");
		if(clicked.equals("Add Questions"))
			response.sendRedirect("addQuestions.jsp");
	}

}
