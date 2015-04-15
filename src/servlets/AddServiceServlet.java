package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DB.ServiceManager;

@WebServlet("/AddServiceServlet")
public class AddServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddServiceServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceManager sm = new ServiceManager();
		int officeId = Integer.parseInt(request.getParameter("officeId"));
		String service = (String) request.getParameter("service");
		
		sm.addService(officeId, service);
		response.sendRedirect("addservice.jsp");
	}

}
