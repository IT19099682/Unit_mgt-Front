<%@page import="com.Approve"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Approve Unit</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/approve.js"></script>

</head>
<body>

<div class="container">
 <div class="row">
  <div class="col-md-8 mx-auto">

 
 <h2 class="m-3">Approve units</h2>
 <form id="formUnit" name="formUnit" method="post" enctype="multipart/form-data" action="Approve.jsp">
 
	
	<br> UserID:
	<input id="UserID" name="UserID" type="text"  class="form-control form-control-sm">
	<br> UserName:
	<input id="UserName" name="UserName" type="text" class="form-control form-control-sm">
	<br>Email :
	<input id="Email" name="Email" type="text" class="form-control form-control-sm">
	<br>Month:
	<select id="Month" name="Month" class="form-control form-control-sm">
						 <option value="0">--Select Month--</option>
						 <option value="January"> January</option>
						 <option value="February">February</option>
						 <option value="March">March</option>
						 <option value="April">April</option>
						 <option value="May">May</option>
						 <option value="June">June</option>
						 <option value="July">July</option>
						 <option value="August">August</option>
						 <option value="September">September</option>
						 <option value="October">October</option>
						 <option value="November">November</option>
						 <option value="December">December</option>
						 
	</select>
	<br>Units:
	<input id="Units" name="Units" type="text" class="form-control form-control-sm">
	<br> 
	
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
	<input type="hidden" id="hidApproveIdSave" name="hidApproveIdSave" value="">
</form>
<br>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<h4>All Approved Unit Details</h4>
				<div id="divApproveGrid">
					<%
						Approve approveObj = new Approve();
						out.print(approveObj.readApprove());
					%>
				</div>


</div>
</div>
</div>



</body>
</html>