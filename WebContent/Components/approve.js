/**
 * 
 */
$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateApproveForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidApproveIdSave").val() == "") ? "POST" : "PUT";
	var formData = new FormData($("#formApprove")[0]);
	 console.log(formData);
	$.ajax({
		url : "ApproveAPI",
		type : type,
		data : formData,
		dataType : "text",
		complete : function(response, status)
		{
			onApproveSaveComplete(response.responseText, status);
		},
		 processData : false,
		 contentType :false
	});
});

function onApproveSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divApproveGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") 
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidApproveIdSave").val("");
	$("#formApprove")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidApproveIdSave").val(
					$(this).closest("tr").find('#hidApproveIdUpdate').val());
			$("#UserID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#UserName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#Units").val($(this).closest("tr").find('td:eq(2)').text());
			$("#Month").val($(this).closest("tr").find('td:eq(3)').text());
			$("#Email").val($(this).closest("tr").find('td:eq(4)').text());
						
		});
//CLIENT-MODEL=========================================================================
function validateApproveForm() {
	
	if ($("#UserID").val().trim() == "") {
		return "Insert User ID.";
	}
	if ($("#UserName").val().trim() == "") {
		return "Insert Userr Name.";
	}
	if ($("#Units").val().trim() == "") {
		return "Insert Units.";
	}
	if ($("#Month").val() == "0"){
		return "Insert Month.";
	}
	if ($("#Email").val().trim() == "") {
		return "Insert Email.";
	}
	
	return true;
}

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "ApproveAPI",
		type : "DELETE",
		data : "AID=" + $(this).data("approveid"),
		dataType : "text",
		complete : function(response, status) {
			onApproveDeleteComplete(response.responseText, status);
		}
	});
});

function onApproveDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divApproveGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

