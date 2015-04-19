package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Controller.Controller;
import Model.Comment;
import Model.Office;
import Model.Service;
import Model.DB.CommentManager;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Pumasok sa commentservlet");
		CommentManager cm = new CommentManager();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONArray  jsonComments = new JSONArray();
		JSONObject comment;
		int serviceid = Integer.parseInt((String)request.getParameter("serviceId"));
		Iterator<Comment> comments =cm.getServiceComments(((Office)request.getSession().getAttribute("Office")).getID(), serviceid);

		while(comments.hasNext()) {
			comment = new JSONObject();
			comment.put("comment", comments.next().getComment());
			jsonComments.add(comment);
		}
		json.put("Comments", jsonComments);
		
		response.setContentType("application/json");
		out.print(json.toString());
	}

}
