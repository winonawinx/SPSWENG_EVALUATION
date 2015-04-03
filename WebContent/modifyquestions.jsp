<%@page import="Model.Form"%>
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
        <title>Evaluate a Service</title>
    </head>
    <body>
        <%
        Controller con = new Controller();
        String office = null;
        String service = null;
    	Cookie[] cookies = request.getCookies();
	    for(Cookie cookie:cookies)
	    {
	      if(cookie.getName().equals("Office"))
	      {
	          office = cookie.getValue();
	      }
	      if(cookie.getName().equals("Service"))
	      {
	    	  service = cookie.getValue();
	      }
	     }
	       
	    int formID = con.getFormID(Integer.parseInt(service));
	    Form form = con.getForm(formID);
	    
        %>
        
        
        <!-- Modal HTML -->
        <div id="confirmationModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Confirmation</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="col-xs-4">
                            <img src="images/check-mark.gif" height="140" width="160"/>
                        </div>
                        <div class="col-xs-8">
                            <p><h3>Are you sure you want to save the changes made?</h3></p>
                            <p><h5>You will no longer be able to recover your previous work.</h5></p>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" onClick="">Yes</button>
                            <button type="button" class="blackbtn" data-dismiss="modal">No</button>    
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="centerdiv">
            <h1 class="headerlabel">Edit Evaluation Form for <%=office %></h1>
            <div class="actualcontent">
                <ol id="questionlist">
                	<%
                		for(int x = 0; x < form.getQuestions().size(); x++)
                		{
                	%>
                
                    <li id="<%=form.getQuestions().get(x).getID()%>">
                        <div class="form-group">
                            <input type="text" class="form-control" value= "<%=form.getQuestions().get(x).getID()%>">
                            <button class="floatright deletequestionbtn" onClick="deleteQuestion('question0');"><img src="css/images/x-mark.png" height="20" width="20"></button>
                        </div>
                    </li>
                    <%} %>
                </ol>
                <div class="form-group">
                    <div>
                        <button type="button" id="addquestionbtn" class="blackbtn" onClick="addQuestion();">+</button>
                    </div>
                    <div class="clearfloat"></div>
                    <div class="checkbox" style="margin-left:45px;">
                        <label class="checkbox-label"><input type="checkbox">Enable Comments</label>
                    </div>
                </div>
                <div align="right">
                    <a href="#confirmationModal" class="blackbtn abtn" style ="font-size: 25px;" data-toggle="modal">Save</a>
                    <a href="editoffices.html" class="blackbtn abtn" style ="font-size: 25px;">Cancel</a>
                </div>
            </div>
        </div>

        <script>
            var nElemQuestion = 4;
            
            function addQuestion()
            {
                $("#questionlist").append('<li id="question+nElemQuestion"><div class="form-group"><input type="text" class="form-control"><button class="floatright deletequestionbtn2" onClick="deleteQuestion(question+nElemQuestion);"><img src="css/images/x-mark.png" height="20" width="20"></button></div></li>'); 
                nElemQuestion++;
            }
            
            function deleteQuestion(question)
            {
                $("#"+question).remove();
            }

        </script>
    </body>
</html>