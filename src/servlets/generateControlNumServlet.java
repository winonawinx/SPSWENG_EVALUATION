package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.DB.ControlNumManager;
import Model.DB.FormManager;
import Model.DB.OfficeManager;
import Model.DB.ServiceManager;

/**
 * Servlet implementation class generateControlNumServlet
 */
@WebServlet("/generateControlNumServlet")
public class generateControlNumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public generateControlNumServlet() {
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

		FormManager fm = new FormManager();
		ControlNumManager cm = new ControlNumManager();
		Controller con = new Controller();
		PrintWriter out = response.getWriter();
		int serviceId = Integer.valueOf(request.getParameter("services"));
		int officeId = Integer.valueOf(request.getParameter("offices"));
		int formId = fm.getFormId(officeId);
		if(formId != 0)
		{
			String controlNum = cm.insertControlNum(formId, serviceId);
			request.getSession().setAttribute("Office", con.getOffice(officeId).getName());
			request.getSession().setAttribute("Service", con.getService(serviceId).getName());
			request.getSession().setAttribute("ControlNumber", controlNum);			
			response.sendRedirect("generated.jsp");
		} 
		else
		{
			out.println("Form " + serviceId + " does not exist!");
		}
	}

}
