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
import Model.Question;

@WebServlet("/AddNewQuestionsServlet")
public class AddNewQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddNewQuestionsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
   	    ArrayList<String> questionStrings = new ArrayList<String>();
	    int number = Integer.parseInt((String)request.getParameter("numbah"));
	    
	    ArrayList<String> existing = new ArrayList<String>();
	    Iterator iterator = con.getAllQuestions();
	    while(iterator.hasNext())
	    {
	    	existing.add(((Question)iterator.next()).getQuestion());
	    }
	    System.out.println("number " + number);
	    String s = "";
	    for(int x = 0; x < number; x++)
	    {
	    	s = (String)request.getParameter("q" + (x));
	    	System.out.println(s);
	    	if(s != null)
	    	{
	    		if(!existing.contains(s))
	    		{
		    		questionStrings.add(s);
		    	    con.addQuestion(1, s, false);
		    	    existing.add(s);
	    		}
	    	}
	    }
	    
	    response.sendRedirect("adminmenu.jsp");
	}

}
