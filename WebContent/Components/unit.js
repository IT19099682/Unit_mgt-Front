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
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidUnitIdSave").val() == "") ? "POST" : "PUT";
	var formData = new FormData($("#formUnit")[0]);
	 console.log(formData);
	$.ajax({
		url : "UnitAPI",
		type : type,
		data : formData,
		dataType : "text",
		complete : function(response, status)
		{
			onUnitSaveComplete(response.responseText, status);
		},
		 processData : false,
		 contentType :false
	});
});

function onUnitSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divPaymentGrid").html(resultSet.data);
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

	$("#hidUnitIdSave").val("");
	$("#formUnit")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidUnitIdSave").val(
					$(this).closest("tr").find('#hidUnitIdUpdate').val());
			$("#UserID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#UserName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#Units").val($(this).closest("tr").find('td:eq(2)').text());
			$("#Month").val($(this).closest("tr").find('td:eq(3)').text());
			$("#Email").val($(this).closest("tr").find('td:eq(4)').text());
						
		});
//CLIENT-MODEL=========================================================================
function validateUnitForm() {
	
	if ($("#UserID").val().trim() == "") {
		return "Insert User ID.";
	}
	if ($("#UserName").val().trim() == "") {
		return "Insert User Name.";
	}
	if ($("#Units").val().trim() == "") {
		return "Insert Units.";
	}
	if ($("#Month").val() == ""){
		return "Insert Month.";
	}
	if ($("#Email").val().trim() == "") {
		return "Insert Email.";
	
	return true;
}

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "UnitAPI",
		type : "DELETE",
		data : "UID=" + $(this).data("userid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onUnitDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divUnitGrid").html(resultSet.data);

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
}
