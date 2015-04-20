package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.Controller;

@WebServlet("/BackToReportServlet")
public class BackToReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BackToReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller m = new Controller();
		int office = -1;
    	Cookie[] cookies = request.getCookies();
       	for(Cookie cookie:cookies){
           	if(cookie.getName().equals("Office")){
              	office = Integer.parseInt((cookie.getValue()));
           	}
       	}
		request.getSession().setAttribute("Questions", m.getOfficeQuestions(office));
		response.sendRedirect("report.jsp");
	}

}
