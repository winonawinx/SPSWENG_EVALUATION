package servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Question;

@WebServlet("/DeleteQuestionsServlet")
public class DeleteQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteQuestionsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
		Iterator questions = con.getAllQuestions();
		while(questions.hasNext())
		{
			Question question = (Question) questions.next();
			String value = request.getParameter("h" + question.getID());
			System.out.println("value is " + value);
			if(value.equals("true"))
				con.removeQuestion(question, true);
		}
		
		response.sendRedirect("adminmenu.jsp");
	}

}
