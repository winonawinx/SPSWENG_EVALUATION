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
        <title>Generated Control Number</title>
    </head>
    <body>
        
        <div class="centerlogindiv">
            <div class="centercard">
                <p><h1 class="label">Generated</h1></p>
            
                <div class="form-group generatedcontrol">
                    <h3>Office: <%=session.getAttribute("Office") %></h3>
                    <h3>Service: <%=session.getAttribute("Service") %></h3>
                    <h3>Control Number: <%= session.getAttribute("ControlNumber") %></h2>
                </div>

				<form action = "generate.jsp">
                <div class="form-group floatright">
                    <button type="submit" class="blackbtn" href = "generate.jsp">Back</button>
                </div>
                </form>
            </div>
        </div>
    </body>
</html>