<%@page import="Model.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="Controller.Controller"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="jquery-2.1.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>View Users</title>
    </head>
    <body>
    	<%
    		Controller con = new Controller();
    		Iterator iterator = con.getAllUsers();
    	%>
        
        <!-- Modal HTML -->
        <form action = "editUsersServlet" method = "post">
        <div id="editUserModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Edit User</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                  		<input type = "hidden" name = "modalid" id = "modalid">
			            <input type = "hidden" name = "modaltitle" id = "modaltitle">
			            <input type = "hidden" name = "modalemail" id = "modalemail">
                    
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Name </label>
                            <input type="text" class="form-control" id = "modalusername" name = "modalusername">
                        </div>
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Password </label>
                            <input type="password" class="form-control" is = "modalpassword" name = "modalpassword">
                        </div>
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Type </label>
                            <select class="form-control my-select" name = "modaltype" id = "modaltype">
                                <option>Administrator</option>
                                <option>Office Head</option>
                                <option>Service Personnel</option>
                            </select>
                        </div>
   			            <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="submit" class="blackbtn">Save</button>
                            <button type="button" class="blackbtn" data-dismiss="modal">Cancel</button>
                            <button type="button" id = "removeofficebtn" class="blackbtn" onClick="">Remove</button>   
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>	
            </form>
        </div>
        
        <div class="centerdiv">
            <h1 class="headerlabel">View Users
                <div class="floatright headermenu">
                    <a href="adminmenu.jsp" type="button" class="blackbtn abtn headermenubtn">Back</a>
                </div>
            </h1>
            <div class="contentdiv">
               <form action = "" method = "post" onSubmit = "return ShowUserModal();">
                <div class="row">
                	<%
                	int number = 0;
                	while(iterator.hasNext())
                	{
                		User user = (User) iterator.next();
                	%>
                    <div class="col-xs-4">
                        <button type="submit" class="blackbtn viewabtn view" onclick = "press(<%=number%>);"><%=user.getUsername() %></button>
                    </div>
                    <input type = "hidden" name = "unh<%=number %>" id = "unh<%=number %>" value = "<%=user.getUsername()%>">
                    <input type = "hidden" name = "pwh<%=number %>" id = "pwh<%=number %>" value = "<%=user.getPassword()%>">
                    <input type = "hidden" name = "th<%=number %>" id = "th<%=number %>" value = "<%=user.getType()%>">
                    <input type = "hidden" name = "idh<%=number %>" id = "idh<%=number %>" value = "<%=user.getID() %>">
                    <input type = "hidden" name = "tih<%=number %>" id = "tih<%=number %>" value = "<%=user.getTitle() %>">
                    <input type = "hidden" name = "eh<%=number %>" id = "eh<%=number %>" value = "<%=user.getEmail() %>">
                    
                    <%
                    number++;
                	} %>
                    <div class="col-xs-4">
                        <a href="addUser.jsp" type="button" class="blackbtn viewabtn view"> + Add User </a>
                    </div>
                </div>
                	
                </form>
            </div>
        </div>
    </body>
    
    
    <script>
    	function ShowUserModal()
    	{
    		$('#editUserModal').modal('show');
    		return false;
    	}
    	function press(element)
     	{
				document.getElementById("modalusername").value = document.getElementById("unh"+element).value;
				document.getElementById("modalpassword").value = document.getElementById("pwh"+element).value;
				document.getElementById("modalid").value = document.getElementById("idh"+element).value;
				document.getElementById("modaltitle").value = document.getElementById("tih"+element).value;
				document.getElementById("modalemail").value = document.getElementById("eh"+element).value;
     	}
     	
    </script>
    
</html>