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
        <title>Evaluation Form</title>
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
                            <h2 id="message"></h2>
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
    		String popup = (String) session.getAttribute("popup"); 
    		String cn = (String) session.getAttribute("cn");
    		session.setAttribute("popup", null);
    		session.setAttribute("cn", null);
    	%>
    	<script>
    	var popup = '<%= popup%>';
    	var cn = '<%= cn%>';
    	if(popup == 'Expired')
    	{
    		$("#message").text(cn + " has expired!");
    		$('#errorModal').modal('show');
    	}
    	else if(popup == 'Does not Exist')
    	{
    		$("#message").text(cn + " does not exist!");
    		console.log("DNE");
    		$('#errorModal').modal('show');
    	}
    	else
    	{
    		message = "";
    		$('#errorModal').modal('hide');
    	}
    	</script>
        <div class="centerlogindiv">
            <div class="centercard">
                    <img src="images/dlsulogo.png" width=150 height=150 class="centeredimage"/>
                    <p><h1 class="label">Evaluator Login</h1></p>
                    <form action = "InitServlet" method = "post">
                    <input type="text" class = "form-control" id="controlnum" name = "controlnumber" placeholder = "Input control number">
                    <button type="submit" class="blackbtn" value = "login" onClick = "">Submit</button>
                    <button type="button" class="blackbtn">Clear</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
