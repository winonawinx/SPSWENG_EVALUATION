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
import Model.ControlNumber;
import Model.Form;

@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InitServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller m = new Controller();
		String cn = request.getParameter("controlnumber");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if(m.ControlNumberIsValid(cn))
		{
			request.getSession().setAttribute("OfficeService", m.getFormOfficeAndService(m.getControlNumberID(cn), m.getForm(m.getControlNumberFormID(cn)).getID()));
			request.getSession().setAttribute("Form", m.getForm(m.getControlNumberFormID(cn)));
			request.getSession().setAttribute("Control", cn);
			ControlNumber controlnumber = m.getControlNumber(cn);
			Form f =  m.getForm(m.getControlNumberFormID(cn));
			m.updateControlNumber(new ControlNumber(controlnumber.getId(), controlnumber.getControlNumber(), f.getID(), controlnumber.getExpirationTime(), true));
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
