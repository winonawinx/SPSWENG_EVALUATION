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

/**
 * Servlet implementation class ModifyFormServlet
 */
@WebServlet("/ModifyFormServlet")
public class ModifyFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyFormServlet() {
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
		// TODO Auto-generated method stub
		Controller controller = new Controller();
		String clicked = (String) request.getParameter("mhidden");
		System.out.println(clicked);
		response.addCookie(new Cookie("Office", clicked));
		Office o = controller.getOfficeByName(clicked);
		int i = controller.getFormID(o.getID());
		request.getSession().setAttribute("Office", o);
		if(i == 0)
			response.sendRedirect("createform.jsp");
		else
			response.sendRedirect("modifyquestions.jsp");
	}

}
