<%@page import="Model.Office"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="jquery-2.1.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Offices</title>
    </head>
    
    <%
    	ArrayList<Office> ofcs = (ArrayList<Office>) session.getAttribute("Offices");
    %>
    
    <body>
    	<form action = "ReportServlet" method = "post">
        <div class="centerdiv">
            <h1 class="headerlabel">View Offices
                <div class="floatright headermenu">
                    <a href="editoffices.html" type="button" class="blackbtn abtn headermenubtn">Edit</a>
                    <a href="adminmenu.jsp" type="button" class="blackbtn abtn headermenubtn">Back</a>
                </div>
            </h1>
            <div class="contentdiv">
                <div class="row">
                   
                  <%
                  	for(int x = 0; x < ofcs.size(); x++)
                  	{
                  %>
                    <div class="col-xs-4">
                        <button type="submit" class="blackbtn view" id = "office<%=ofcs.get(x).getID()%>>" value = "<%=ofcs.get(x).getID()%>" onclick = "clicked(this)"><%= ofcs.get(x).getName() %></button>
                    </div>
				<% } %>
                </div>
                <input type = "hidden" id = "answer" name = "answer" value = "0">
            </div>
        </div>
        </form>
        
        <script>
        	function clicked(element)
        	{
        		var pressedBtn = element.id;
         		document.getElementById("answer").value = document.getElementById(pressedBtn).value;
        	}
        </script>
        
    </body>
</html>