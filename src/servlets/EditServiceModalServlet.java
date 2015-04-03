package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditServiceModalServlet")
public class EditServiceModalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditServiceModalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String clicked = (String) request.getParameter("mhidden");
		String service = (String) request.getParameter("click");
		
		if(clicked.equals("Edit Form"))
		{
			response.addCookie(new Cookie("Service", service));
			response.sendRedirect("modifyquestions.jsp");
		}	
		
	}

}
