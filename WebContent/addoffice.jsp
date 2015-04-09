<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="Model.DB.*" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Add Office</title>
<%
OfficeManager om = new OfficeManager();
UserManager um = new UserManager();
Iterator<Office> offices = om.getAllData();
Iterator<User> heads = um.getOfficeHead();
%>
</head>
<body>
	<div class="centerdiv">
		<h1 class="headerlabel">Add Office</h1>
		<div id="addoffice" class="contentdiv">
			<form action="AddOfficeServlet" method="post">
				<div class="form-horizontal" class="row">
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Name </label> <input
							type="text" name="officeName" class="form-control">
					</div>
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Acronym </label> <input
							type="text" class="form-control">
					</div>
					<div class="clearfloat"></div>
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Head </label> 
						<select class="form-control my-select" name="heads">
							<% 
							while(heads.hasNext())
							{
								User user = heads.next();
							%>
								<option value="<%= user.getID()%>"><%= user.getUsername()%></option>
							<% 	
							}
							%>
						</select>
					</div>
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Hierarchy
						</label>

						<div style="padding-left: 10px;">
							<%
							while(offices.hasNext())
							{
								Office office = offices.next();
							%>
								<div class="checkbox">
									<label class="checkbox-label">
									<input name="office<%= office.getID() %>" value="<%= office.getID() %>" type="checkbox">
									<%= office.getName() %>
									</label>
								</div>
							<%
							}
							%>
						</div>
					</div>
					<div class="clearfloat"></div>
					<div class="form-group floatright">
						<button type="submit" class="blackbtn formfooterbtn">Add</button>
						<a href="editoffices.html" type="submit"
							class="blackbtn abtn formfooterbtn">Cancel</a>
					</div>
					<div class="clearfloat"></div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>