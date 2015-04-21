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
        <title>Add User</title>
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
        
        <div id="successModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Success</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <h2>Successfully added user!</h2>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" data-dismiss="modal" onclick="success();">Okay</button>  
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="centerdiv">
            <h1 class="headerlabel">Add User
                <div class="floatright headermenu">
                    <a href = "adminmenu.jsp" type="button" class="blackbtn abtn headermenubtn" id = "Backbtn" name = "Backbtn" value = "Back">Back</a>
                </div>
            </h1>
            <div id="addoffice" class="contentdiv small">
                <form action="AddUserServlet" method="post" id="userSubmit" onsubmit="return checkEval();">
                    <div class="form-horizontal row" style="margin-top:40px;"> 
                        
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Email </label>
                            <input type="email" class="form-control" id = "uemail" name = "uemail">
                        </div>

                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Name </label>
                            <input type="text" class="form-control" id = "uname" name = "uname">
                        </div>

                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Password </label>
                            <input type="password" class="form-control" id = "upw" name = "upw">
                        </div>
                        
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Re-Enter Password </label>
                            <input type="password" class="form-control" id = "upw2" name = "upw2">
                        </div>
                        
                        <div class="form-group control-group">
                            <label class="col-xs-12 addlabel control-label">Type </label>
                            <select class="form-control my-select" id = "utype" name = "utype">
                                <option value="administrator">Administrator</option>
                                <option value="officehead">Office Head</option>
                                <option value="service personnel">Service Personnel</option>
                            </select>
                        </div>
                    
                    </div>
                    <div class="form-group floatright">
                        <button type="submit" class="blackbtn abtn formfooterbtn">Add</button>
  	                    <a href="adminmenu.jsp" type="submit" class="blackbtn abtn formfooterbtn">Cancel</a>
                    </div>
                    <div class="clearfloat"></div>
                </form>
            </div>
        </div>
        
        <script>
        var returnValue = false;
    	function validate()
    	{
    			if(document.getElementById("uemail").value.length<1 || document.getElementById("uname").value.length<1
    					|| document.getElementById("upw").value.length<1 || document.getElementById("upw2").value.length<1
    					|| document.getElementById("utype").value.length<1)
    			{
    				$('#message').text("Please fill up all fields");
    				return false;	
    			}
    			else if(document.getElementById("upw").value != document.getElementById("upw2").value)
    			{
    				$('#message').text("Passwords mismatch");
    				return false;
    			}
    			else
    				return true;
    	}
    	
    	function success()
    	{
    		returnValue = true;
    		$('#successModal').modal("hide");
    		var button = document.getElementById("userSubmit").ownerDocument.createElement('input');
            button.style.display = 'none';
            button.type = 'submit';
            document.getElementById("userSubmit").appendChild(button).click();
            document.getElementById("userSubmit").removeChild(button);
    	}
    	
    	function checkEval()
    	{
    		if(!validate())
    		{
    			$('#errorModal').modal('show');
    			return false;
    		}
    		else if(returnValue)
    		{
    			return true;
    		}
    		else
    		{
    			$('#successModal').modal('show');
    			return false;
    		}	
    	}
        </script>
        
    </body>
</html>