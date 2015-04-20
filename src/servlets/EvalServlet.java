package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Comment;
import Model.ControlNumber;
import Model.Form;

@WebServlet("/EvalServlet")
public class EvalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EvalServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller m = new Controller();
		Form f = (Form) request.getSession().getAttribute("Form");
		ControlNumber controlnumber = m.getControlNumber((String) request.getSession().getAttribute("Control"));
	
		for(int x = 0; x < f.getQuestions().size(); x++)
		{
			System.out.println("a " + f.getQuestions().get(x).getID());
			System.out.println("b " + controlnumber.getId());
			System.out.println("c " + Integer.parseInt((String)request.getParameter("answer"+x)));
			m.addAnswer(f.getQuestions().get(x).getID(), controlnumber.getId(), Integer.parseInt((String)request.getParameter("answer"+x)), false);
		}
		
		m.insertComment(new Comment(controlnumber.getId(), (String)request.getParameter("Text1")));
//		m.updateControlNumber(new ControlNumber(controlnumber.getId(), controlnumber.getControlNumber(), f.getID(), controlnumber.getExpirationTime(), true));
	
		response.sendRedirect("evaluatorlogin.jsp");
	}

}
