/**
 * 
 */
$(function() {

	$("#searchFileNo").focusout(function(e) {
		$("form#searchFileForm").submit();
		return false;
	});

	$(".selectBorder-placeholder select").focus(function() {
		$(this).parent().css("border-color", "#5cb3fd");
	});

	$(".selectBorder-placeholder select").focusout(function() {
		$(this).parent().css("border-color", "rgba(0,0,0,.15)");
	});

	$(".inputBorder-placeholder input").focus(function() {
		$(this).parent().css("border-color", "#5cb3fd");
	});

	$(".inputBorder-placeholder input").focusout(function() {
		$(this).parent().css("border-color", "rgba(0,0,0,.15)");
	});

	$(".inputBorder-placeholder textarea").focus(function() {
		$(this).parent().css("border-color", "#5cb3fd");
	});

	$(".inputBorder-placeholder textarea").focusout(function() {
		$(this).parent().css("border-color", "rgba(0,0,0,.15)");
	});

	$('#messages li').click(function() {
		$(this).fadeOut();
	});

	setTimeout(function() {
		$('#messages li.info').fadeOut();
	}, 3000);

	$(".dobMask").mask("99/99/9999", {
		placeholder : "dd/mm/yyyy"
	});

	$("#studentBasicForm").find("#btnSaveContinue").click(function() {
		var isValid = validateDate("Date of Birth", $(".dobMask").val(), true);
		return isValid;
	});
	$("#studentBasicForm").find("#btnSave").click(function() {
		var isValid = validateDate("Date of Birth", $(".dobMask").val(), true);
		return isValid;
	});
	
	$("#studentBasicForm").find("#btnClear").click(function() {
		$("#studentBasicForm").find(':input').not(':button, :submit, :reset, :hidden, :checkbox, :radio, select').val('');
		$("#studentBasicForm").find('select').val("NONE");
		$("#studentBasicForm").find(':checkbox, :radio').prop('checked', false);
	});

	$("#residenceDetailsForm").find("#btnClear").click(function() {
		$("#residenceDetailsForm").find(':input').not(':button, :submit, :reset, :hidden, :checkbox, :radio, select').val('');
		$("#residenceDetailsForm").find('select').val("NONE");
		$("#residenceDetailsForm").find(':checkbox, :radio').prop('checked', false);
	});
	
	$("#searchStudentFileForm").find("#btnSearch").click(function() {
		var isValid = validateDate("Created Date", $("#createdDate")
				.val(), false);
		if (isValid) {
			isValid = validateDate("Interviewed Date", $(
					"#interviewedDate").val(), false);
		}
		return isValid;
	});

	$("#searchStudentFileForm").find("#btnClear").click(function() {
		$("#searchStudentFileForm").find(':input').not(':button, :submit, :reset, :hidden, :checkbox, :radio, select').val('');
		$("#searchStudentFileForm").find('select').val("NONE");
		$("#searchStudentFileForm").find(':checkbox, :radio').prop('checked', false);
	});
	
	loadResidenceDetailsFields();
	highlightActiveCaseMenuItem();
	highlightActiveUserProfileMenuItem();

	$("#residenceOwnership").change(function() {
		loadResidenceOwnershipDependencies($(this));
	});
	
	$("#studentCourseDetails\\.courseName").change(function() {
		getCourseDetailsIfCourseAlreadyAdded($(this));
	});

	$("#haveVehicle1").change(function() {
		loadVehicleDependencies($(this));
	});
});

function countChar(obj, trackCharactersObjID) {
	var len = obj.value.length;
	$(trackCharactersObjID).text(
			(obj.maxLength - len) + " characters remaining");
}

function displayModal(message) {
	$("#messageModal").find("#messageError").text(message);
	$("#messageModal").modal('show');
}

function validateDate(propertyName, propertyValue, isMandatory) {
	console.log(propertyName + " is mandatory? - " + isMandatory);
	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	var errorMessage = "*Please provide valid " + propertyName
			+ " in dd/mm/yyyy format";
	// Match the date format through regular expression
	if (propertyValue.match(dateformat)) {
		// Test which seperator is used '/' or '-'
		var opera1 = propertyValue.split('/');
		var opera2 = propertyValue.split('-');
		lopera1 = opera1.length;
		lopera2 = opera2.length;
		// Extract the string into month, date and year
		if (lopera1 > 1) {
			var pdate = propertyValue.split('/');
		} else if (lopera2 > 1) {
			var pdate = propertyValue.split('-');
		}
		var dd = parseInt(pdate[0]);
		var mm = parseInt(pdate[1]);
		var yy = parseInt(pdate[2]);

		// Create list of days of a month [assume there is no leap year by
		// default]
		var ListofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		if (mm == 1 || mm > 2) {
			if (dd > ListofDays[mm - 1]) {
				console.log(propertyName + " format is invalid");
				displayModal(errorMessage);
				return false;
			}
		}
		if (mm == 2) {
			var lyear = false;
			if ((!(yy % 4) && yy % 100) || !(yy % 400)) {
				lyear = true;
			}
			if ((lyear == false) && (dd >= 29)) {
				console.log(propertyName + " format is invalid");
				displayModal(errorMessage);
				return false;
			}
			if ((lyear == true) && (dd > 29)) {
				console.log(propertyName + " format is invalid");
				displayModal(errorMessage);
				return false;
			}
		}
	} else {
		if (!isMandatory) {
			if (propertyValue == "") {
				console.log(propertyName + " is not mandatory");
				return true;
			}
		}
		console.log(propertyName + " format is invalid");
		displayModal(errorMessage);
		return false;
	}
	return true;
}

function showRentAmountDiv() {
	$("#residenceRentAmountDiv").show();
	$("#residenceRentAmountErrorDiv").show();
}

function hideRentAmountDiv() {
	$("#residenceRentAmountDiv").hide();
	$("#residenceRentAmountErrorDiv").hide();
	$("#residenceRentAmount").val(null);
}

function showResidenceOtherDescription() {
	$("#residenceOwnershipOtherDescriptionDiv").show();
	$("#residenceOwnershipOtherDescriptionErrorDiv").show();
}

function hideResidenceOtherDescription() {
	$("#residenceOwnershipOtherDescriptionDiv").hide();
	$("#residenceOwnershipOtherDescriptionErrorDiv").hide();
	$("#residenceOwnershipOtherDescription").val(null);
}

function showVehicleDescription() {
	$("#vehicleDescriptionDiv").show();
	$("#vehicleDescriptionErrorDiv").show();
}

function hideVehicleDescription() {
	$("#vehicleDescriptionDiv").hide();
	$("#vehicleDescriptionErrorDiv").hide();
	$("#vehicleDescription").val(null);
}

function loadResidenceDetailsFields() {
	var residenceOwnershipObject = $("#residenceOwnership");
	if (residenceOwnershipObject.length) {
		loadResidenceOwnershipDependencies(residenceOwnershipObject);
	}

	if ($('[name="haveVehicle"]').length) {
		loadVehicleDependencies($("#haveVehicle1"));
	}
}

function loadResidenceOwnershipDependencies(residenceOwnershipObject) {
	if (residenceOwnershipObject.val() == "RENTED") {
		showRentAmountDiv();
		hideResidenceOtherDescription();
	} else if (residenceOwnershipObject.val() == "OTHER") {
		showResidenceOtherDescription();
		hideRentAmountDiv();
	} else {
		hideRentAmountDiv();
		hideResidenceOtherDescription();
	}
}

function loadVehicleDependencies(haveVehicleObject) {
	if (haveVehicleObject.is(":checked")) {
		showVehicleDescription();
	} else {
		hideVehicleDescription();
	}
}

function highlightActiveCaseMenuItem() {
	if ($("#basicDetailsHeader").length > 0) {
		$("#basicDetailsLink").addClass("active");
	} else if ($("#familyDetailsHeader").length > 0) {
		$("#familyDetailsLink").addClass("active");
	} else if ($("#bankAccountDetailsHeader").length > 0) {
		$("#bankAccountDetailsLink").addClass("active");
	} else if ($("#residenceDetailsHeader").length > 0) {
		$("#residenceDetailsLink").addClass("active");
	} else if ($("#curriculumRecordHeader").length > 0) {
		$("#curriculumRecordLink").addClass("active");
	}
}

function highlightActiveUserProfileMenuItem() {
	if ($("#userProfileHeader").length > 0) {
		$("#userProfileLink").addClass("active");
	} else if ($("#changePasswordHeader").length > 0) {
		$("#changePasswordLink").addClass("active");
	}
}

function getCourseDetailsIfCourseAlreadyAdded(courseNameObject) {
	if(courseNameObject != null && courseNameObject.val() != "" && courseNameObject.val() != "NONE"){
		$("#btnCourseChange").click();
	} else {
		$("#studentCourseDetails\\.schoolCollegeInstituteName").val(null);
		$("#studentCourseDetails\\.branch").val("NONE");
		$("#studentCourseDetails\\.id").val(null);
	}
}