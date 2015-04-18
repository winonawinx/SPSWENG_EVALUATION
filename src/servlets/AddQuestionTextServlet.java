package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Form;
import Model.Question;
@WebServlet("/AddQuestionTextServlet")
public class AddQuestionTextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddQuestionTextServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
			Controller c = new Controller();
			
		   int number = Integer.parseInt(request.getParameter("number"));
	   	   String respMess = "";
		   respMess = "<li id=\"" + number + "\">";
		   respMess += "<div class=\"form-group\">";
		   respMess +="<input type = \"text\" class = \"form-control\" id = \"q"+number+"\" name = \"q"+number+"\">";
		   respMess +="<button class=\"floatright deletequestionbtn\" onClick=\"deleteQuestion("+number+");\"><img src=\"css/images/x-mark.png\" height=\"20\" width=\"20\"></button>";
		   respMess += "</div></li>";
		   
	       response.setContentType("text/plain");
	       response.setCharacterEncoding("UTF-8");
	       response.getWriter().write(respMess);
		  
	}

}
