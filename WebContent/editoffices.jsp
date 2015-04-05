<%@page import="java.util.ArrayList"%>
<%@page import="Model.Office"%>
<%@page import="java.util.Iterator"%>
<%@page import="Controller.Controller"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="jquery-2.1.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Edit Offices</title>
    </head>
    <body>
        <%
        	Controller con = new Controller();
        	Iterator<Office> offices = con.getAllOffices();
        %>
        <!-- Modal HTML -->
        <form action = "EditOfficeModalServlet" method = "post">
        <div id="editOfficeModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Edit Office</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Name </label>
                            <input type="text" class="form-control" id = "ofcName" name = "ofcName">
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" onClick="">Save</button>
                            <button type = "submit" id="editformbtn" name = "editformbtn" value = "Edit Form" class = "blackbtn abtn" onclick = "pressed(this);">Edit Form</button> 
                            <button type="button" class="blackbtn" data-dismiss = "modal">Cancel</button>
                            <button type="button" id = "removeofficebtn" class="blackbtn" onClick="">Remove</button>   
                        	<input type = "hidden" id = "mhidden" name = "mhidden">
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        </form>

        <form action = "" method = "" onSubmit = "return showOfficeModal();">
        <div class="centerdiv">
            <h1 class="headerlabel">Edit Offices
                <div class="floatright headermenu">
                    <button type="submit" class="blackbtn abtn headermenubtn">Back</button>
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
                        <button type = "submit" class="blackbtn viewabtn view" name = "<%=office.getName()%>" id = "<%=office.getName()%>" value = "<%=office.getName()%>" onclick = "press(this);"><%=office.getName() %></button>
                    </div>
 					<%} %>
                    <div class="col-xs-4">
                        <button type="submit" class="blackbtn viewabtn view"> + Add Office </button>
                    </div>
                </div>                
            </div>
        </div>
        <input type = "hidden" name = "click" id = "click">
        </form>
        
    </body>
    
    <script>
    	function showOfficeModal()
    	{
    		$('#editOfficeModal').modal('show');
    		document.getElementById("ofcName").value = document.getElementById('click').value;
    		return false;
    	}
    	function press(element)
     	{
     		var pressedBtn = element.id;
     		document.getElementById("click").value = document.getElementById(pressedBtn).value;
     	}
    	function pressed(element)
     	{
     		var pressedBtn = element.id;
     		document.getElementById("mhidden").value = document.getElementById(pressedBtn).value;
     	}
    </script>
    
</html>