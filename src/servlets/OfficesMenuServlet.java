package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

@WebServlet("/OfficesMenuServlet")
public class OfficesMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OfficesMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller controller = new Controller();
		String option = (String)request.getParameter("pindot");
		if(option.equals("Edit"))
		{
			response.sendRedirect("editoffices.jsp");
		}
		else if(option.equals("Back"))
		{
			response.sendRedirect("adminmenu.jsp");
		}
		else
		{
			request.getSession().setAttribute("Services", controller.getOffice(Integer.parseInt(option)).getServices());
			response.sendRedirect("viewservices.jsp");
		}
	
	}

}
