<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="Model.DB.OfficeManager"%>
<%@ page import= "Controller.*" %>
<%@ page import= "Model.*" %>
<%@ page import= "java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="jquery-2.1.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Add Service</title>
        
    </head>
    <body>
    	<%	
        	OfficeManager of = new OfficeManager();
        	ArrayList<Office> offices = new ArrayList<Office>();
        	offices = of.getAllData();
        	
        %>
        <div class="centerdiv">
            <h1 class="headerlabel">Add Service</h1>
            <div id="addoffice" class="contentdiv small">
                <form action="AddServiceServlet" method="post">
                    <div class="form-horizontal row" style="margin-top:40px;">
                        <div class="form-group">
                            <label class="col-xs-12 addlabel control-label">Office </label>
                            <select name="officeId" class="form-control my-select">
								<%	for(int i = 0; i < offices.size(); i++) {
								%>
									<option value="<%=offices.get(i).getID()%>"> <%=offices.get(i).getName()%> </option>
								<% 	
								}
								%>
                            </select>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-12 addlabel control-label">Service Name </label>
                            <input type="text" name="service" class="form-control">
                        </div>
                    </div>
                    <div class="form-group floatright">
                        <button type="submit" class="blackbtn blackbtn-alignright">Add</button>
                    </div>
                    <div class="clearfloat"></div>
                </form>
            </div>
        </div>
    </body>
</html>