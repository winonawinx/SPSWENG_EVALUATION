package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Controller.Controller;
import Model.Form;
import Model.Office;
import Model.Question;
import Model.Service;
import Model.User;

@WebServlet("/ReportMenuServlet")
public class ReportMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = Controller.getInstance();
		String button = (String) request.getParameter("click");
		if(button.equals("comments"))
		{
			Office o = (Office) request.getSession().getAttribute("Office");
			request.getSession().setAttribute("Office", o);
	        Iterator tempForms = (Iterator) request.getSession().getAttribute("Forms");
	        request.getSession().setAttribute("Forms", tempForms);
	        System.out.println(((Form) tempForms.next()).getID() + " lol");
	        User user = (User) request.getSession().getAttribute("User");
	    	request.getSession().setAttribute("User", user);
			request.getSession().setAttribute("Services", con.getOfficeServices(((Office)request.getSession().getAttribute("Office")).getID()));
			request.getSession().setAttribute("comments", con.getServiceComments(((Office)request.getSession().getAttribute("Office")).getID(), 1));
			response.sendRedirect("comments.jsp");
		}
/*		else if(button.equals("filters"))
		{
			PrintWriter pw = response.getWriter();
			Gson g = new Gson();
			int formId = Integer.parseInt(request.getParameter("formId"));
			Iterator<Question> temp = con.getAllQuestions(formId);
			ArrayList<Question> questions = new ArrayList<Question>();
			while(temp.hasNext())
				questions.add(temp.next());
			response.setContentType("application/json");
			pw.write(g.toJson(questions));
		}*/
		else if(button.equals("average"))
		{
			PrintWriter pw = response.getWriter();
			int formId = Integer.parseInt(request.getParameter("formId"));
			int officeId = Integer.parseInt(request.getParameter("officeId"));
			Iterator tempQuestions = con.getAllQuestions(formId);
			Iterator tempServices = con.getAllServices(officeId);
			ArrayList<ArrayList<Float>> averages = new ArrayList<ArrayList<Float>>();
			ArrayList<Service> services = new ArrayList<Service>();
			ArrayList<Question> questions = new ArrayList<Question>();
			while(tempServices.hasNext())
			{
				services.add((Service) tempServices.next());
			}
			
			while(tempQuestions.hasNext())
			{
				questions.add((Question) tempQuestions.next());
			}

			JSONObject json = new JSONObject();
			JSONArray  jsonElements = new JSONArray();
			JSONObject questionJSON;
			for(Question question: questions)
			{
				int questionId = question.getID();
				ArrayList<Float> innerAverages = new ArrayList<Float>();
				for(Service service: services)
				{
					innerAverages.add(con.getAVG(questionId, formId, service.getID(), officeId));
				}
				averages.add(innerAverages);
				String s = "";
				
				
				questionJSON = new JSONObject();
				questionJSON.put("question", question.getQuestion());
				questionJSON.put("average", innerAverages);
				jsonElements.add(questionJSON);
			}
			json.put("Questions", jsonElements);
			System.out.println(json.toString());
			response.setContentType("application/json");
			pw.write(json.toString());
		}
	}

}
