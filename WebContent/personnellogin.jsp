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
        <title>Staff Login</title>
    </head>
    <body>
    
    	<div id="errorModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Error</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <h2>You have entered an invalid username and/or password. Please try again.</h2>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" data-dismiss="modal">Okay</button>  
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <% 
    		String error = (String) session.getAttribute("Error");
    		session.setAttribute("Error", "");
    	%>
    	
    	<script>
    	var error = '<%= error%>';
    	console.log(error);
    	if(error == 'Invalid')
    		$('#errorModal').modal('show');
    	else
    		$('#errorModal').modal('hide');
    	<%
			session.setAttribute("Error", "");
		%>
    	</script>
        <div class="centerlogindiv">
            <div class="centercard">
                    <img src="images/dlsulogo.png" width=150 height=150 class="centeredimage"/>
                    <p><h1 class="label">Staff Login</h1></p>
                    <form action="LoginServlet" method="post">
					<p><input type="text" class="form-control" id="email" name="email" placeholder="Email"></p>
                    <p><input type="password" class="form-control" id="password" name="password" placeholder="Password"></p>
                    <div class="form-group floatright">
                        <input type="submit" class="blackbtn" value="Log-in"/>
                    </div>
					</form>
                </div>
            </div>
        </div>
    </body>
</html>
