<%@page import="Controller.Controller"%>
<%@page import="Model.Form"%>
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
        String OfficeService = (String)session.getAttribute("OfficeService");
        Form f = (Form)session.getAttribute("Form");
        session.setAttribute("Form", f);
        String cn = (String)session.getAttribute("Control");
        session.setAttribute("Control", cn);
        int nQuestions = f.getQuestions().size();
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
                            <h2>Please fill up all fields.</h2>
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
                            <p><h3>Are you sure you want to submit this form?</h3></p>
                            <p><h5>You will no longer be able to change your answer after you submit.</h5></p>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" onclick="setConfirmation(true);">Yes</button>
                            <button type="button" class="blackbtn" onclick="setConfirmation(false);"data-dismiss="modal">No</button>    
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        
       <div class="centerdiv">
            <h1 class="headerlabel"><%=OfficeService %></h1>
            <div id="evaluationformdiv" class="contentdiv">
               <form action = "EvalServlet" method = "post" id="evalSubmit" onsubmit="return checkEval();">
               <input type = "hidden" name = "hiddenForm" value = <%=f.getID()%>>
               <input type = "hidden" name = "hiddenControl" value = <%=cn%>>
                <ol>     
					<%
					for(int i=0; i<nQuestions; i++){
					%>
						<li class="evalquestion"><h2><span class="no-answer">*</span><%= f.getQuestions().get(i).getQuestion() %></h2>
		                        <div class="evalbtnsdiv floatright">
		                        	<button type="button" class="evalquesbtn sadbtn" id="sad<%=i%>" onclick="press(this);" value="1"><img src="images/Frowny%20donut.png">Sad</button>
		                            <button type="button" class="evalquesbtn neutralbtn" id="neutral<%=i%>" onclick="press(this);" value="2"><img src="images/Neutral%20Donut.png">Neutral</button>
		                            <button type="button" class="evalquesbtn happybtn" id="happy<%=i%>" onclick="press(this);" value="3"><img src="images/Happy%20donut.png">Happy</button>
									<button type="button" class="evalquesbtn nabtn" id="na<%=i%>" onclick="press(this);" value="0"><img src="images/Dead%20Smiley.png">N/A</button>
									<input type="hidden" id="answer<%=i%>" name="answer<%=i %>">
		                        </div>
		                        <div class="clearfloat"></div>
		                    </li>
<%
					}
%>
							<div  id="commentbox">
								<!--<h2 class="evalquestion">Comments </h2> -->
								<textarea name="Text1" cols="63" rows="6" placeholder="Comments"  ></textarea>
								<div align="right" >
									</br></br>
									<input type="submit" id ="submit" value=" Submit " class="blackbtn" style ="font-size: 25px; margin-right: 130px;">
								</div>
							</div>
					</form>
                </ol>		
            </div>
             <script>       	
         	var retValue = false;
         	function press(element)
         	{
         		var pressedBtn = element.id;
         		var lastChar = pressedBtn.charAt(pressedBtn.length-1);
         		document.getElementById("sad"+lastChar).disabled = false;
         		document.getElementById("neutral"+lastChar).disabled = false;
         		document.getElementById("happy"+lastChar).disabled = false;
         		document.getElementById("na"+lastChar).disabled = false;
         		document.getElementById(pressedBtn).disabled = true;
         		document.getElementById("answer"+lastChar).value = document.getElementById(pressedBtn).value;
         	}
        	function validate()
        	{
        		for(var i=0; i<<%= nQuestions%>; i++)
        		{
        			if(document.getElementById("answer"+i).value.length<1)
        				return false;	
        		}
        		return true;
        	}
             
        	function setConfirmation(retValue)
        	{
        		this.retValue = retValue;
        		$('#confirmationModal').modal("hide");
        		if(this.retValue)
        		{
                    alert("Thank you for evaluating! Have a nice day.");
                    var button = document.getElementById("evalSubmit").ownerDocument.createElement('input');
                    button.style.display = 'none';
                    button.type = 'submit';
                    document.getElementById("evalSubmit").appendChild(button).click();
                    document.getElementById("evalSubmit").removeChild(button);
        		}
        	}
        	
        	function checkEval()
        	{
        		if(!validate())
        		{
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
        	function checkConfirm()
        	{
        		return this.retValue;
        	}
            </script>
        </div>
    </body>
</html>
