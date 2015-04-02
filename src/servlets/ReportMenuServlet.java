package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Office;

@WebServlet("/ReportMenuServlet")
public class ReportMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
		String button = (String) request.getParameter("click");
		
		if(button.equals("comments"))
		{
			request.getSession().setAttribute("Services", con.getOfficeServices(((Office)request.getSession().getAttribute("Office")).getID()));
			response.sendRedirect("comments.jsp");
		}
	}

}
