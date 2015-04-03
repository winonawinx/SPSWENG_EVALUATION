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
		Controller controller = new Controller();
		String clicked = (String) request.getParameter("mhidden");
		String service = (String) request.getParameter("serviceName");
		System.out.println("In Edit Service Modal Servlet == " + service);
		
		if(clicked.equals("Edit Form"))
		{
			Office office = null;
	    	Cookie[] cookies = request.getCookies();
	        for(Cookie cookie:cookies){
	           if(cookie.getName().equals("Office")){
	        	   System.out.println("Cookie is " + cookie.getValue());
	              office = controller.getOfficeByName(cookie.getValue());
	           }
	       }
	        
			
			
			response.addCookie(new Cookie("Service", String.valueOf(controller.getServiceID(service, office.getID()))));
			response.sendRedirect("modifyquestions.jsp");
		}	
		
	}

}
