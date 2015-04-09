package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.Controller;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
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
		Controller m = new Controller();
		String cn = request.getParameter("controlnumber");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		if(m.ControlNumberIsValid(cn))
		{
			request.getSession().setAttribute("OfficeService", m.getFormOfficeAndService(m.getControlNumberFormID(cn), m.getForm(m.getControlNumberFormID(cn)).getID()));
			request.getSession().setAttribute("Form", m.getForm(m.getControlNumberFormID(cn)));
			request.getSession().setAttribute("Control", cn);
			response.sendRedirect("evaluationform.jsp");
		}
		else  
		{
			if(m.controlNumIsExpired(cn))
			{
				session.setAttribute("popup", "Expired");
				
			}
			else
			{
				session.setAttribute("popup", "Does not Exist");
			}
			session.setAttribute("cn", cn);
			response.sendRedirect("evaluatorlogin.jsp");
		}
	}

}
