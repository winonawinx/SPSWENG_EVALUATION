<%@page import="java.util.Iterator"%>
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
    	Iterator<Office> offices = (Iterator<Office>) session.getAttribute("Offices");
    %>
    
    <body>
        <div class="centerdiv">
        <form action = "OfficesMenuServlet" method = "post">
            <h1 class="headerlabel">View Offices
                <div class="floatright headermenu">
                    <button type="submit" class="blackbtn abtn headermenubtn" id = "Editbtn" name = "Editbtn" value = "Edit" onClick = "menu(this);">Edit</button>
                    <button type="submit" class="blackbtn abtn headermenubtn" id = "Backbtn" name = "Backbtn" value = "Back" onClick = "menu(this);">Back</button>
                    <input type = "hidden" name = "pindot" id = "pindot">
                </div>
            </h1>
            <div class="contentdiv">
                <div class="row">
                   
                  <%
                  	while(offices.hasNext())
                  	{
                  		Office office = offices.next();
                  %>
                    <div class="col-xs-4">
                        <button type="submit" class="blackbtn view" id = "office<%=office.getID()%>>" value = "<%=office.getID()%>" onclick = "menu(this)"><%= office.getName() %></button>
                    </div>
				<% } %>
                </div>
            </div>
            </form>
        </div>
  
        
        <script>
        	function menu(element)
        	{
        		var pressedBtn = element.id;
         		document.getElementById("pindot").value = document.getElementById(pressedBtn).value;
        	}
        </script>
        
    </body>
</html>