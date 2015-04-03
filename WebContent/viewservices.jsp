<%@page import="Model.Service"%>
<%@page import="java.util.Iterator"%>
<%@page import="Model.Office"%>
<%@page import="Controller.Controller"%>
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
        <title>Services</title>
    </head>
    <body>
        <%
        Controller controller = new Controller();
		Office office = null;
    	Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
           if(cookie.getName().equals("Office")){
        	   System.out.println("Cookie is " + cookie.getValue());
              office = controller.getOfficeByName(cookie.getValue());
           }
       }
        
		Iterator<Service> services = controller.getOfficeServices(office.getID());
        
        %>
        <!-- Modal HTML -->
        <form action = "EditServiceModalServlet" method = "post">
        <div id="editServiceModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Edit Service</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Name </label>
                            <input type="text" class="form-control" id = "serviceName" name = "serviceName">
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" onClick="">Save</button>
                            <button type="submit" class="blackbtn" id = "EditForm" name = "EditForm" value = "Edit Form" onClick="pressed(this);">Edit Form</button>
                            <button type="button" class="blackbtn" data-dismiss="modal">Cancel</button>
                            <button type="button" id = "removeofficebtn" class="blackbtn" onClick="">Remove</button>   
                        	<input type = "hidden" id = "mhidden" name = "mhidden">
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        </form>

 		<form action = "" method = "" onSubmit = "return showServiceModal();">
        <div class="centerdiv">
            <h1 class="headerlabel">Services
                <div class="floatright headermenu">
                    <a href="viewoffices.html" type="button" class="blackbtn abtn headermenubtn">Back</a>
                </div>
            </h1>
            <div class="contentdiv">
                <div class="row">
                    <%
                    	while(services.hasNext())
                    	{
                    		Service service = services.next();
                    %>
                    <div class="col-xs-4">
                        <button type="submit" class="blackbtn viewabtn view" name = "<%=service.getID()%>" id = "<%=service.getID()%>" value = "<%=service.getName()%>" onclick = "press(this);"><%=service.getName() %></button>
                    </div>
                    <%} %>
                    <div class="col-xs-4">
                        <a href="addservice.html" type="button" class="blackbtn viewabtn view"> + Add Service </a>
                    </div>
                </div>
                
            </div>
        </div>
        
        <input type = "hidden" name = "click" id = "click">
        </form>
        
         <script>
    	function showServiceModal()
    	{
    		$('#editServiceModal').modal('show');
    		document.getElementById("serviceName").value = document.getElementById('click').value;
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
        
    </body>
</html>