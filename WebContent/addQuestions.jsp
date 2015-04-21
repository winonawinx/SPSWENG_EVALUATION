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
        <title>Add questions</title>
    </head>
    <body>    
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
                            <p><h3>Are you sure you want to add the following questions?</h3></p>
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
            <form action = "AddNewQuestionsServlet" method = "post" id = "modifyQues" onSubmit = "return checkVal();">
            <h1 class="headerlabel">Add Questions
                <div class="floatright headermenu">
                    <a href = "adminmenu.jsp" type="button" class="blackbtn abtn headermenubtn" id = "Backbtn" name = "Backbtn" value = "Back">Back</a>
                </div>           
            </h1>
            <div class="actualcontent">
                <ol id="questionlist" name = "questionlist">
                    <li id="0">
                        <div class="form-group">
                        	<input type = "text" class = "form-control" id = "q0" name = "q0">
                            <button class="floatright deletequestionbtn" onClick="deleteQuestion(0);"><img src="css/images/x-mark.png" height="20" width="20"></button>
                        </div>
                    </li>
                </ol>
		        <input type = "hidden" name = "numbah" id = "numbah">
		        <div class="clearfloat"></div>
                <div class="form-group">
                    <div>
                        <button type="button" id="addquestionbtn" class="blackbtn">+</button>
                    </div>
                </div>
                <div class="row form-group" style="padding-left:40px">
                </div>
                <div align="right">
                    <button type = "submit" class="blackbtn abtn" style ="font-size: 25px;" data-toggle="modal" onClick = "saveNum();">Save</button>
                    <a href="adminmenu.jsp" class="blackbtn abtn" style ="font-size: 25px;">Cancel</a>
                </div>
        </div>
        </form>
     </div>

	<script type = "text/javascript">
	var number = 1;
	$(document).ready(function()
      		{
      			$("#addquestionbtn").click(function()
      			{
      				$.ajax({
  							type: "POST",
  							url: "AddQuestionTextServlet",
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

	
	</script>

    </body>
</html>