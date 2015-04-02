<%@page import="Model.Service"%>
<%@page import="java.util.Iterator"%>
<%@page import="Model.Office"%>
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
        <script src="js/bootstrap.min.js"></script>
        <script src="js/botmonster-jquery-bootpag-c34948f/lib/jquery.bootpag.min.js"></script>
        
        <title>View Comments</title>
    </head>
    <body>
        <%
        	Office office = (Office) session.getAttribute("Office");
        	Iterator services = (Iterator) session.getAttribute("Services");
        %>
        <div class="centerdiv">
            <h1 class="headerlabel">View Comments
                <div class="floatright headermenu">
                    <a href="report.jsp" type="button" class="blackbtn abtn headermenubtn">Back</a>
                </div>
            </h1>
            <div class="actualcontent">
                <div class="row">
                    <div id="serviceslistdiv" class="col-xs-3">
                        <ul id="serviceslist">
                            <%
                        		while(services.hasNext())
                        		{
                        			Service service = (Service)services.next();
                        	%>
                            <li>
                                <button type = "submit" name = "<%=service.getID()%>" id = "<%=service.getID()%>" value = "<%=service.getID()%>"><%=service.getName() %></button>
                            </li>
                            <%
                            }
                            %>
                        	<input type = "hidden" name = "click" id = "click">
                        </ul>
                    </div>
                    <div class="col-xs-9">
                        <div id="displayserviceslabel">
                            <h5>Displaying 1-5 out of 69 comments</h5>
                        </div>
                        <div id="commentsdiv">
                            <ul id="comments">
                                <li>Stupid Service</li>
                                <li>Stupid Service</li>
                                <li>Stupid Service</li>
                                <li>Stupid Service</li>
                                <li>Stupid Service</li>
                                <li>Stupid Service</li>
                            </ul>
                        </div>
                        <div id="page-selection"></div>
                    </div>
                </div>
            </div>
            <div id="reportbtns">
                <a href="#filterModal" class="blackbtn reportbtn" data-toggle="modal">View Reports</a>
            </div>
        </div>
    </body>
    <script>
        // init bootpag
        $('#page-selection').bootpag({
            total: 69,
            page: 1,
            maxVisible: 5
        }).on("page", function(event, /* page number here */ num){
             $("#comments").html("Insert content"); // some ajax content loading...
        });
    </script>
</html>