package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Form;
import Model.Office;
import Model.Question;

@WebServlet("/ModifyQuestionsServlet")
public class ModifyQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModifyQuestionsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
        String office = null;
    	Cookie[] cookies = request.getCookies();
	    for(Cookie cookie:cookies)
	    {
	      if(cookie.getName().equals("Office"))
	      {
	          office = cookie.getValue();
	      }
	    }

	    Office o = con.getOfficeByName(office);
	    int formID = con.getFormID(o.getID());
	    Form form = con.getForm(formID);
	    
	    ArrayList<String> questionStrings = new ArrayList<String>();
	    int number = Integer.parseInt((String)request.getParameter("numbah"));
	   
	    String s = "";
	    for(int x = 0; x < number; x++)
	    {
	    	s = (String)request.getParameter("q" + (x+1));
	    
	    	if(s != null)
	    	{
	    		questionStrings.add(s);
	    	}
	    }
	    
	    ArrayList<Question> questions = new ArrayList<Question>();
	    for(int x = 0; x < questionStrings.size(); x++)
	    {
	    	questions.add(con.getQuestion(questionStrings.get(x)));
	    }
	    String startdate = (String)request.getParameter("startdate");
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date parsed = null;
		try {
			parsed = format.parse(startdate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
	    
        String enddate = (String)request.getParameter("enddate");
	    java.util.Date parsed2 = null;
		try {
			parsed2 = (java.util.Date) format.parse(enddate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        java.sql.Date sql2 = new java.sql.Date(parsed2.getTime());
	    
        
        con.addForm(-1, o.getID(), sql, sql2, true);
        Iterator<Form> i = con.getAllForms();
        int fcnt = 0;
        Form frm = null;
        while(i.hasNext())
        {
        	frm = i.next();
        	fcnt++;
        }
	    con.addFormQuestions(questions,frm.getID());
	    
	    response.sendRedirect("adminmenu.jsp");
	}

}
