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