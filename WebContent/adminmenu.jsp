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
        <title>Administrator</title>
    </head>
    <body>
        <div class="centerlogindiv">
            <div class="centercard small">
            <form action = "AdminMenuServlet" method = "post">
                <p><h1 class="label">Admin Menu</h1>
                <div id="adminmenu">
                    <div class="form-group">
                        <button type="submit" id = "vo" name = "vo" class="blackbtn abtn adminbtn" onClick = "clicked(this);" value = "Offices">View Offices</button>
                    </div>
                    <div class="form-group">
                        <button type="submit" id = "voh" name = "voh" class="blackbtn abtn adminbtn" onClick = "clicked(this);" value = "Office Heads">View Office Heads</button>
                    </div>
                    <div class="form-group">
                        <button type="submit" id = "vu" name = "vu" class="blackbtn abtn adminbtn" onClick = "clicked(this);" value = "Users">View Users</button>
                    </div>
                    <div class="form-group">
                        <button type="submit" id = "pl" name = "pl" class="blackbtn abtn adminbtn" onClick = "clicked(this);" value = "Sign Out">Sign Out</button>
                    </div>
                    <input type = "hidden" id = "click" name = "click">
                </div>
             </form>
            </div>
        </div>
            
        <script>
        	function clicked(element)
        	{
        		var pressedBtn = element.id;
         		document.getElementById("click").value = document.getElementById(pressedBtn).value;
        	}
        </script>
        
    </body>
</html>    