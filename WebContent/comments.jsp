<%@page import="Controller.Controller"%>
<%@page import="Model.Comment"%>
<%@page import="Model.Service"%>
<%@page import="java.util.Iterator"%>
<%@page import="Model.Office"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script
	src="js/botmonster-jquery-bootpag-c34948f/lib/jquery.bootpag.min.js"></script>

<title>View Comments</title>

</head>
<body>
	<%
		Controller m = new Controller();
	        	//Office office = (Office) session.getAttribute("Office");
		      	Office office = null;
	        	Cookie[] cookies = request.getCookies();
		       	for(Cookie cookie:cookies){
		           	if(cookie.getName().equals("Office")){
		              	office = m.getOffice(Integer.parseInt(cookie.getValue()));
		           	}
		       	}
		     	//session.setAttribute("Office", office);
	        	Iterator services = m.getOfficeServices(office.getID());
	        	session.setAttribute("Services", services);
	%>
	<div class="centerdiv">
		<h1 class="headerlabel">
			View Comments
			<div class="floatright headermenu">
				<a href="report.jsp" type="button"
					class="blackbtn abtn headermenubtn">Back</a>
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
									<button name="<%=service.getID()%>"
										id="<%=service.getID()%>" value="<%=service.getID()%>"
										onClick="clicked(this);"><%=service.getName()%></button>
								</li>
							<%
								}
							%>
						</ul>
					</div>
				<div class="col-xs-9">
					<div id="displayserviceslabel">
						<h5 id="header">Click a Service to view it's comments</h5>
					</div>
					<div id="commentsdiv">
						<h5 id="comments">
							Click a Service to view it's comments
						</h5>
					</div>
					<div id="page-selection"></div>
				</div>
			</div>
		</div>
		<div id="reportbtns">
			<a href="#filterModal" class="blackbtn reportbtn" data-toggle="modal">View
				Reports</a>
		</div>
	</div>
	<script>
	// init bootpag
	var comments = JSON.parse('{"Comments":[]}');
	var currServiceId;
	
	</script>
	<script>
	
	function clicked(element) {
		currServiceId = element.id;
		$.ajax({
			type: 'POST',
			url : 'CommentServlet',
			data : {"serviceId": currServiceId},
			dataType : 'json',
			error : function(data) {
				alert('error');
			},
			success : function(data) {
				comments = JSON.parse('{"Comments":[]}');
				comments = data;
				
				if(comments.Comments.length > 0)
				{
					if(comments.Comments.length > 4) {
						$("#comments").html(comments.Comments[0].comment);
						$("#header").html('Displaying 1-5 out of ' + comments.Comments.length + '  comments');
					}
					else
					{
						$("#comments").html(comments.Comments[0].comment);
						$("#header").html('Displaying 1-'+ comments.Comments.length +' out of ' + comments.Comments.length + '  comments');
					} 
					$('#page-selection').bootpag({
						total : comments.Comments.length,
						page : 1,
						maxVisible : 5
					}).on("page", function(event, /* page number here */num) {
							if(comments.Comments.length > 0)
								$("#comments").html(comments.Comments[num-1].comment); // some ajax content loading...

					});
				}
				
				else
				{
					$("#header").html('No comments on this service yet');	
					$('#page-selection').html('');
					$("#comments").html('No comments on this service yet');
				}
				
			}
		});
	}
</script>
</body>
</html>