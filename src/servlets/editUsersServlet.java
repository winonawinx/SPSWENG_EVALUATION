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
		int userId = Integer.parseInt(request.getParameter("userId"));
		String userName = request.getParameter("userName");
		String userTitle = request.getParameter("userTitle");
		String userEmail = request.getParameter("userEmail");
		String userPassword = request.getParameter("userPassword");
		String userType = request.getParameter("userType");
		System.out.println("ID = " + request.getParameter("userId"));
		System.out.println("un = " +  request.getParameter("userName")); 
		System.out.println("title = " +	request.getParameter("userTitle")); 
		System.out.println("email = " + request.getParameter("userEmail"));
		System.out.println("pw = " + request.getParameter("userPassword"));
		System.out.println("type = " + request.getParameter("userType"));
		
		con.updateUser(new User(userId, userName, userTitle, userEmail, userPassword, userType, false));		
	}

}
