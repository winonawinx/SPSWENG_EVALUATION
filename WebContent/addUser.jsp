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
        <div class="centerdiv">
            <h1 class="headerlabel">Add User</h1>
            <div id="addoffice" class="contentdiv small">
                <form>
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
                            <select class="form-control my-select" id = "utype" name = "utypw">
                                <option>Administrator</option>
                                <option>Office Head</option>
                                <option>Service Personnel</option>
                            </select>
                        </div>
                    
                    </div>
                    <div class="form-group floatright">
                        <button type="submit" class="blackbtn blackbtn-alignright">Add</button>
  	                    <a href="adminmenu.jsp" type="submit" class="blackbtn abtn formfooterbtn">Cancel</a>
                    </div>
                    <div class="clearfloat"></div>
                </form>
            </div>
        </div>
        
        <script>
    	function validate()
    	{
    			if(document.getElementById("uemail").value.length<1 || document.getElementById("uname").value.length<1
    					|| document.getElementById("upw").value.length<1 || document.getElementById("utype").value.length<1)
    			{	alert("Please fill up all fields.");
    				return false;	
    			}
    		
    		alert("User has been added!");
    		return true;
    	}
        </script>
        
    </body>
</html>