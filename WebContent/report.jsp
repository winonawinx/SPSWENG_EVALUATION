<%@page import="Model.Service"%>
<%@page import="java.util.Iterator"%>
<%@page import="Controller.Controller"%>
<%@page import="Model.Question"%>
<%@page import="java.util.ArrayList"%>
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
        <title>View Reports</title>
    </head>
    <body>
        <%
             	Controller m = new Controller();
        		Office o = null;
              	//Office o = (Office) session.getAttribute("Office");
              	Cookie[] cookies = request.getCookies();
                for(Cookie cookie:cookies){
                    if(cookie.getName().equals("Office")){
                       o = m.getOffice(Integer.parseInt(cookie.getValue()));
                    }
                }
             	//session.setAttribute("Office", o);
              	Iterator<Question> aQ = (Iterator<Question>) session.getAttribute("Questions");
        %>
        <div class = "centerdiv">
            <h1 class="headerlabel"><%=o.getName()%> Report</h1>
            <div class="actualcontent">
                <table class="table" id="report">
                    <thead>
                        <tr>
	                        <th>Items</th>
                        <%
                        	Iterator iterator = o.getServices();
                        	int size = 0;
                        	while(iterator.hasNext())
                        	{
                        		size++;
                      			Service s = (Service)iterator.next();
                        	}
                        	int serviceSize = size;
                        	int questionSize = 0;
                        	float[] total = new float[serviceSize];
                        	Iterator services = o.getServices();
                        	//for(int x = 0 ; x < serviceSize; x++)
                        	while(services.hasNext())
                        	{
                        		Service service = (Service) services.next();
                        %>                        
                            <th><%=service.getName()%></th>
                        <%
                        	}
                        %>
                        </tr>
                    </thead>
                        <tbody>
                        	<%
                            	while(aQ.hasNext())
                            	{
                            		questionSize++;
                            		Question question = aQ.next();
                            %>
                            <tr>
                                <td><%=question.getQuestion() %></td>
                                <%
                                	services = o.getServices();
                                	int y = 0;
                                	while(services.hasNext())
                                	{
                                		Service service = (Service) services.next();
                                		float average = m.getAVG(question.getID(), service.getID(), o.getID());
                                		total[y] += average;
                                		y++;
                              	%>
                                <td><%= average %></td>
                                <%
                                	}
                            %>
                            	
                            </tr>
                            	<%
                            	}
                                %>
                            <tr>
                                <td>Overall Satisfaction</td>
                                <%
                                	for(int y = 0; y < serviceSize; y++)
                                	{
                                %>
                                <td><%= total[y] %></td>
                                <%
                                	}
                            	
                                %>
                            </tr>
                            <tr>
                                <td>Average</td>
                                <%
                                	for(int y = 0; y < serviceSize; y++)
                                	{
                                %>
                                <td><%= total[y]/questionSize %></td>
                                <%
                                	}
                            	
                                %>
                            </tr>
                        </tbody>
                  </table>
            </div>
            <form action = "ReportMenuServlet" method = "post">
            <div id="reportbtns">
                <a href="#filterModal" class="blackbtn reportbtn" data-toggle="modal">Filter Results</a>
                <button class="blackbtn reportbtn" type = "submit" id = "comments" name = "comments" value = "comments" onClick = "clicked(this);">View Comments</button>
                <a href="" class="blackbtn reportbtn">Export to Spreadsheet</a>
                <input type = "hidden" name = "click" id = "click">
            </div>
            </form>
        </div>
        
        <script>
        	function clicked(element)
        	{
        		var pressedBtn = element.id;
         		document.getElementById("click").value = document.getElementById(pressedBtn).value;
        	}
        </script>
    </body>
</html>