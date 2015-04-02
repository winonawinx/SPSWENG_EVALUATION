package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

/**
 * Servlet implementation class AdminMenuServlet
 */
@WebServlet("/AdminMenuServlet")
public class AdminMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMenuServlet() {
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

		String clicked = (String) request.getParameter("click");
		System.out.println("Clicked " + clicked);
		Controller m = new Controller();
		request.getSession().setAttribute("Offices", m.getAllOffices());
		if(clicked.equals("Offices"))
			response.sendRedirect("Offices.jsp");
		if(clicked.equals("Users"))
			response.sendRedirect("addUser.jsp");
	}

}
