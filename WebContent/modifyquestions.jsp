<%@page import="java.util.ArrayList"%>
<%@page import="Model.Question"%>
<%@page import="java.util.Iterator"%>
<%@page import="Model.Office"%>
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
        int number = 0;
        int qn = 0;
    	Cookie[] cookies = request.getCookies();
	    for(Cookie cookie:cookies)
	    {
	      if(cookie.getName().equals("Office"))
	      {
	          office = cookie.getValue();
	      }
	    }
	       
	    Office o = con.getOfficeByName(office);
	    int formID = con.getFormID(o.getID());
	    Form form = con.getForm(formID);
		session.setAttribute("currentform", form);
	    Iterator<Question> iterator = con.getAllQuestions();
	    ArrayList<Question> questionsList = new ArrayList<Question>();
	    while(iterator.hasNext())
	    {
	    	questionsList.add(iterator.next());
	    }
	    String questions[] = new String[questionsList.size()];
	    
	    for(int x = 0; x < questions.length; x++)
	    {
	    	questions[x] = questionsList.get(x).getQuestion();
	    }
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
                            <button type="button" class="blackbtn" onClick="setConfirmation(true);">Yes</button>
                            <button type="button" class="blackbtn" onClick="setConfirmation(false);" data-dismiss="modal">No</button>    
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
              
        <div class="centerdiv">
            <form action = "ModifyQuestionsServlet" method = "post" id = "modifyQues" onSubmit = "return checkVal();">
            <h1 class="headerlabel">Edit Evaluation Form for <%=office %></h1>
            <div class="actualcontent">
                <ol id="questionlist" name = "questionlist">
                	<%
                		for(int x = 0; x < form.getQuestions().size(); x++)
                		{
                			number++;
                			qn++;
                    		ArrayList<String> ques = new ArrayList<String>();
                			String currQuestion = form.getQuestions().get(x).getQuestion();
                	%>
                
                    <li id="<%=number%>">
                        <div class="form-group">
                        	<select class = "form-control" id = "q<%=qn%>" name = "q<%=qn%>">
		 					<option>  <%=form.getQuestions().get(x).getQuestion()%> </option>
		 					<%for(int y = 0; y < questionsList.size(); y++)
		 					{
		 						if(!questionsList.get(y).getQuestion().equals(currQuestion))
		 							 ques.add(questionsList.get(y).getQuestion());
		 					}
		 					%>
		 					<%
		 						for(int y = 0; y< ques.size(); y++)
		 						{
		 					%>
			 					<option><%=ques.get(y)%></option>
		 					<%	} %>
                        	</select>
                            <!-- <input type="text" class="form-control" id = "<%=form.getQuestions().get(x).getQuestion()%>" name = "<%=form.getQuestions().get(x).getQuestion()%>" value= "<%=form.getQuestions().get(x).getQuestion()%>"> -->
                            <button class="floatright deletequestionbtn" onClick="deleteQuestion(<%=number%>);"><img src="css/images/x-mark.png" height="20" width="20"></button>
                        </div>
                    </li>
                    <%} %>
                </ol>        
		        <input type = "hidden" name = "numbah" id = "numbah">
                <div class="form-group">
                    <div>
<%--                         <button type="button" id="addquestionbtn" class="blackbtn" onClick="addQuestion(<%=number %>);">+</button> --%>
                        <button type="button" id="addquestionbtn" class="blackbtn">+</button>
                    </div>
                    <div class="clearfloat"></div>
                    <div class="checkbox" style="margin-left:45px;">
                        <div class="row">
                            <div class="col-xs-6">
                                <label class="checkbox-label"><input type="checkbox" id = "commentscb" name = "commentscb">Comments</label>
                                <input type = "hidden" name = "checkedComments" id = "checkedComments" value = "false">
                            </div>
                            <div class="col-xs-6">
                                <label class="checkbox-label"><input type="checkbox" id = "enddatecb" name = "enddatecb" onclick = "toggleCheckedEndDate(this);">End Date</label>
                                <input type = "hidden" name = "checkedEndDate" id = "checkedEndDate" value = "false">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row form-group" style="padding-left:40px">
                    <div class="col-xs-6">
                        <label class="col-xs-12 addlabel control-label">Start Date </label>
                        <input type="date" class="datepicker" id = "startdate" name = "startdate">
                    </div>
                    <div class="col-xs-6">
                        <label class="col-xs-12 addlabel control-label">End Date </label>
                        <input type="date" class="datepicker" id = "enddate" name = "enddate">
                    </div>
                </div>
                <div align="right">
                    <button type = "submit" class="blackbtn abtn" style ="font-size: 25px;" data-toggle="modal" onClick = "saveNum();">Save</button>
                    <a href="editoffices.jsp" class="blackbtn abtn" style ="font-size: 25px;">Cancel</a>
                </div>
            </div>
        </div>
        </form>

        <script type = "text/javascript">
        	//var nElemQuestion = <%=number%>;
        	
        	/*function addQuestion(number)
            {
            	alert("pumasok sa addq " + number);
        		//nElemQuestion = <%=number%>;
				<%number++;%>
            	//$("#questionlist").append('<li id="number"><div class="form-group"><input type="text" class="form-control"><button class="floatright deletequestionbtn2" onClick="deleteQuestion(question+nElemQuestion);"><img src="css/images/x-mark.png" height="20" width="20"></button></div></li>'); 
                
               	$("#questionlist").append('<li id="'+ number + '">');
               	$("#questionlist").append('<div class="form-group">');
               	$("#questionlist").append('<select class = "form-control" id = "'+number+'" name = "'+number+'">');
               	<%for(int x = 0; x < questionsList.size(); x++)
               	{
               	%>
                 		$("#questionlist").append('<option>' + <%=questionsList.get(x).getQuestion()%> + '</option>');               		
               	<%}%>
               	$("#questionlist").append('</select>');
               	$("#questionlist").append('<button class="floatright deletequestionbtn" onClick="deleteQuestion('+number+');"><img src="css/images/x-mark.png" height="20" width="20"></button>');
               	$("#questionlist").append('</div></li>');
               	
                //nElemQuestion++;
            }*/
            
            
            
            $(document).ready(function()
            {
              			$("#addquestionbtn").click(function()
              			{
              				$.ajax({
              							type: "POST",
              							url: "AddQuestionServlet",
              							data: {"number": <%=number%>,
              									"formforservlet": <%=form.getID()%>},
              								error: function(data)
              								{
              									alert("ERROR: " + data);
              								},
              							success: function(data){
              								$("#questionlist").append(data);
              							}
              				  });
              				alert("num is " + <%=number%>);
              				<%number++;%>
              			});          
              });
            
            
            
            
            
            
            
            
            function deleteQuestion(question)
            {
                $("#"+question).remove();
                <%number-=1;
                System.out.println();
                %>
            	document.getElementById("numbah").value = <%=number%>;
            }
            
            var retValue = false;
            function checkConfirm()
        	{
        		return this.retValue;
        	}
            function checkVal()
        	{
        		if(!checkConfirm())
        		{
        			$('#confirmationModal').modal('show');
        			return false;
        		}
        		else
	        		return true;
        	}
            function setConfirmation(retValue)
        	{
        		this.retValue = retValue;
        		$('#confirmationModal').modal("hide");
        		if(this.retValue)
        		{
                    alert("Your changes have been saved.");
                    var button = document.getElementById("modifyQues").ownerDocument.createElement('input');
                    button.style.display = 'none';
                    button.type = 'submit';
                    document.getElementById("modifyQues").appendChild(button).click();
                    document.getElementById("modifyQues").removeChild(button);
        		}
        	}
            
            function saveNum()
            {
            	document.getElementById("numbah").value = <%=number%>;
            }
            
            function toggleCheckedEndDate(element)
            {
	            	document.getElementById("checkedEndDate").value = element.checked;
            }
            
            function toggleCheckedComments(element)
            {
	            	document.getElementById("checkedComments").value = element.checked;
            }
            
        </script>
    </body>
</html>