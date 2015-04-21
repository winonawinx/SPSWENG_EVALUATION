<%@page import="Model.Service"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Controller.Controller"%>
<%@page import="Model.Question"%>
<%@page import="Model.Form"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Office"%>
<%@page import="Model.User"%>
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
              	Cookie[] cookies = request.getCookies();
                for(Cookie cookie:cookies){
                    if(cookie.getName().equals("Office")){
                       o = m.getOffice(Integer.parseInt(cookie.getValue()));
                       request.getSession().setAttribute("Office", o);
                    }
                }
                Iterator tempForms = (Iterator) session.getAttribute("Forms");
                request.getSession().setAttribute("Forms", tempForms);
                User user = (User) session.getAttribute("User");
            	request.getSession().setAttribute("User", user);
				ArrayList<Form> forms = new ArrayList<Form>();
				while(tempForms.hasNext())
					forms.add((Form) tempForms.next());
              	Iterator<Question> aQ;
        %>
        
        <div class = "centerdiv">
            <form action = "OfficeHeadBackServlet" method = "post">
            <h1 class="headerlabel"><%=o.getName()%> Report
             	<div class = "floatright headermenu">
                   <button type = "submit" class="blackbtn abtn headermenubtn" value = "Back">Back</button>
                </div>
            </h1>
            </form>
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
                        <tbody id="questions">
                        </tbody>
                  </table>
            </div>
            <form action = "ReportMenuServlet" method = "post">
            <div id="reportbtns">
                <select class="form-control" style="width:220px; margin: 0px 20px; display: inline; text-decoration: none !important;" id="filterOptions" onChange="toggleOptions(this);">
	            </select>
	            <button class="blackbtn reportbtn" type = "submit" id = "comments" name = "comments" value = "comments" onClick = "clicked(this);">View Comments</button>
                <a href="" class="blackbtn reportbtn">Export to Spreadsheet</a>
                <input type = "hidden" name = "click" id = "click">
            </div>
            </form>
        </div>
        
        <script>
        var formId = 0;
    	var start = true;
    	var stringHTML;
    	var total;
   		<%
	        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			int formSize = forms.size();
			int id = 0;
			for(int i = 0; i < formSize; i++)
			{
				Form curForm = forms.get(i);
				String date = df.format(curForm.getStartDate()) + " - ";
				if(curForm.getEndDate() == null)
					date = date.concat("Present");
				else
					date = date.concat(df.format(curForm.getEndDate()));
		%>
			var option = document.createElement("option");
			option.text = "<%=date%>";
			option.value = "<%=curForm.getID()%>";
			document.getElementById("filterOptions").add(option);
		<%
			}
		%>
		$("#filterOptions").change();
		
		function clicked(element)
		{
			var pressedBtn = element.id;
	 		document.getElementById("click").value = document.getElementById(pressedBtn).value;
		}
      	
        function toggleOptions(element)
    	{
        	formId = element.value;
    		changeReports();
    	}
        
        function changeReports()
        {
        	stringHTML = "";
        	var dataLength;
        	var averageLength;
			total = new Array(<%=serviceSize%>);
			$("#questions").html("");
			for(var i = 0; i < <%=serviceSize%>; i++)
			{
				total[i] = new Number();
			}
			var questions = JSON.parse('{"Questions":[{"average":[""],"question":""}]}');
			$.ajax({
        		type: "POST",
				url : "ReportMenuServlet",
				data : {"formId" : formId, "officeId" : <%= o.getID() %>, "click" : "average"},
				method : "POST",
				success : function(average)
				{
					questions = JSON.parse('{"Questions":[{"average":[],"question":""}]}');
					questions = average;
	           		dataLength = questions.Questions.length;
					for(var i = 0; i < dataLength; i++)
					{
						stringHTML += "<tr><td>";
		           		stringHTML += questions.Questions[i].question;
		           		stringHTML += "</td>";
		           		averageLength = questions.Questions[i].average.length;
						for(var x = 0; x < averageLength; x++)
						{
							total[x] += questions.Questions[i].average[x];
							stringHTML += "<td>" + parseFloat(Math.round(questions.Questions[i].average[x] * 100) / 100).toFixed(2) + "</td>";
						}
		                stringHTML += "</tr>";
					}
					
					stringHTML += "<tr><td>Overall Satisfaction</td>";
	               	for(var y = 0; y < averageLength; y++)
	                {
	                	stringHTML += "<td>";
	   					stringHTML += parseFloat(Math.round(total[y] * 100) / 100).toFixed(2);
	   					stringHTML += "</td>";
	                }
	                stringHTML += "</tr><tr><td>Average</td>";
	                for(var y = 0; y < averageLength; y++)
	                {
		                	stringHTML += "<td>";
	       					stringHTML += parseFloat(Math.round(total[y]/dataLength * 100) / 100).toFixed(2);
	       					stringHTML += "</td>";
	                }      
	                stringHTML += "</tr>";
	                $("#questions").html(stringHTML);
	            }
            });
		}
        
        function getAVG(i, dataLength, question, questionId)
        {
        	$.ajax({
        		type: "GET",
				url : "ReportMenuServlet",
				data : {"questionId" : questionId, "officeId" : <%= o.getID() %>, "click" : "average"},
				method : "GET",
				async: "false",
				success : function(average)
				{
					var counter = this.ajaxcounter;
					stringHTML += "<tr><td>";
	           		stringHTML += question;
	           		stringHTML += "</td>";
	           		averageLength = average.length;
					for(var x = 0; x < averageLength; x++)
					{
						total[x] += average[x];
						stringHTML += "<td>" + parseFloat(Math.round(average[x] * 100) / 100).toFixed(2) + "</td>";
					}
	                stringHTML += "</tr>";
	                alert(counter + " " + question);
	                if(counter == dataLength-1)
	                {
	                	console.log(stringHTML);
						stringHTML += "<tr><td>Overall Satisfaction</td>";
		               	for(var y = 0; y < averageLength; y++)
		                {
		                	stringHTML += "<td>";
		   					stringHTML += parseFloat(Math.round(total[y] * 100) / 100).toFixed(2);
		   					stringHTML += "</td>";
		                }
		                stringHTML += "</tr><tr><td>Average</td>";
		                for(var y = 0; y < averageLength; y++)
		                {
			                	stringHTML += "<td>";
		       					stringHTML += parseFloat(Math.round(total[y]/dataLength * 100) / 100).toFixed(2);
		       					stringHTML += "</td>";
		                }      
		                stringHTML += "</tr>";

						console.log(stringHTML);
		                $("#questions").html(stringHTML);
	                }
	            }
            });
        }
        </script>
    </body>
</html>