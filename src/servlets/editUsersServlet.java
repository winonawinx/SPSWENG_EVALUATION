package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;
import Model.User;

@WebServlet("/editUsersServlet")
public class editUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public editUsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller con = new Controller();
		
		System.out.println("ID = " + Integer.parseInt(request.getParameter("modalid")));
		System.out.println("un = " +  request.getParameter("modalusername")); 
		System.out.println("title = " +	request.getParameter("modaltitle")); 
		System.out.println("email = " + request.getParameter("modalemail"));
		System.out.println("pw = " + request.getParameter("modalpassword"));
		System.out.println("type = " + request.getParameter("modaltype"));
		
		con.updateUser(new User(Integer.parseInt(request.getParameter("modalid")), request.getParameter("modalusername"), request.getParameter("modaltitle"), request.getParameter("modalemail"), request.getParameter("modalpassword"), request.getParameter("modaltype"), false));		
	
		response.sendRedirect("viewUsers.jsp");
	}

}
