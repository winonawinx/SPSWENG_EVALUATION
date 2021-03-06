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
        <title>Create Office Form</title>
    </head>
    <body>
        <%
        Controller con = new Controller();
        String office = null;
        int number = 0;
        int qn = 0;
 
	    Office o = (Office)session.getAttribute("Office");
	    Iterator<Question> iterator = con.getAllQuestions();
	    ArrayList<Question> questionsList = new ArrayList<Question>();
	    int questionSize = 0;
	    while(iterator.hasNext())
	    {
	    	questionSize++;
	    	questionsList.add((Question)iterator.next());
	    }
	    office = o.getName();
	    session.setAttribute("Office", o);
        %>
        
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
        
        <div id="successModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Success</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <h2>Success in making evaluation form!</h2>
                            <h2>for <%=office %>!</h2>
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
            <form action = "CreateFormServlet" method = "post" id = "modifyQues" onSubmit = "return checkVal();">
            <h1 class="headerlabel">Edit Evaluation Form for <%=office %></h1>
            <div class="actualcontent">
                <ol id="questionlist" name = "questionlist">
              	    <li id="0">
                        <div class="form-group">
                        	<select class = "form-control" id = "q0" name = "q0">
		 					<%for(int y = 0; y < questionsList.size(); y++)
		 					{
		 					%>
		 						<option><%=questionsList.get(y).getQuestion()%></option>
		 					<%}%>
                        	</select>
                            <button class="floatright deletequestionbtn" onClick="deleteQuestion(0);"><img src="css/images/x-mark.png" height="20" width="20"></button>
                        </div>
                    </li>  
                </ol>        
		        <input type = "hidden" name = "numbah" id = "numbah">
                <div class="form-group">
                    <div>
                        <button type="button" id="addquestionbtn" class="blackbtn">+</button>
                    </div>
                    <div class="clearfloat"></div>
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
            </form>
        </div>

	<script type = "text/javascript">
	var number = 1;
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	if(dd<10)
	    dd='0'+dd

	if(mm<10)
	    mm='0'+mm

	today = yyyy + "-" + mm + "-" + dd;
	
	$(document).ready(function()
      		{
      			$("#addquestionbtn").click(function()
      			{
      				if(number < <%=questionSize%>)
      				{
      					$.ajax(
      					{
  							type: "POST",
  							url: "AddQuestionServlet",
  							data: {"number": number,},
  								error: function(data)
  								{
  									alert("ERROR: " + data);
  								},
  							success: function(data){
  								$("#questionlist").append(data);
  							}
  						});
						number++;
	      		    	document.getElementById("numbah").value = number;
      				}
      			});          
      		});  
    function saveNum()
    {
    	document.getElementById("numbah").value = number;
    }
    
    function deleteQuestion(question)
    {
        $("#"+question).remove();
        number--;
    	document.getElementById("numbah").value = number;
    }
    
    var retValue = false;
    function checkConfirm()
	{
		return this.retValue;
	}
    
    function startDateValid()
    {
    	if(document.getElementById("startdate").value >= today)
    		return true;
    	else
    		return false;
    }
    
    function endDateValid()
    {
    	if(document.getElementById("enddate").value == "" || (document.getElementById("enddate").value >= today 
    			&& document.getElementById("enddate").value >= document.getElementById("startdate").value))
    		return true;
    	else
    		return false;
    }
    
    function questionsDuplicate()
    {
    	for(var i = 0; i < number; i++)
    		for(var j = i+1; j < number; j++)
    		if($("#q"+i+" option:selected").text() == $("#q"+j+" option:selected").text())
    			return true;
    	return false;
    }
    function checkVal()
	{
    	if(!startDateValid())
        {
    		$("#message").text("Please select a valid start date");
    		$('#errorModal').modal('show');
			return false;
        }
    	else if(!endDateValid())
    	{
    		$("#message").text("Please select a valid end date");
    		$('#errorModal').modal('show');
			return false;
    	}
    	else if(questionsDuplicate())
    	{
    		$("#message").text("Please do not duplicate questions");
    		$('#errorModal').modal('show');
			return false;
    	}
    	else if(!checkConfirm())
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
            $('#successModal').modal("show");
		}
	}

    function success()
    {
    	var button = document.getElementById("modifyQues").ownerDocument.createElement('input');
        button.style.display = 'none';
        button.type = 'submit';
        document.getElementById("modifyQues").appendChild(button).click();
        document.getElementById("modifyQues").removeChild(button);
    }
	
	</script>

    </body>
</html>