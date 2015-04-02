package servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.Comment;
import Model.Service;

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
		Controller controller = new Controller();
		int serviceid = Integer.parseInt((String)request.getParameter("click"));
		Iterator<Comment> comments = controller.getServiceComments(serviceid);
		request.getSession().setAttribute("comments", comments);
		Iterator<Service> services = (Iterator<Service>) request.getSession().getAttribute("Services");
		while(services.hasNext())
		{
			System.out.println("In comment servlet " + services.next().getName());
		}
		request.getSession().setAttribute("Services", services);
		response.sendRedirect("comments.jsp");
	}

}
