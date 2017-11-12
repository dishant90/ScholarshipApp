/**
 * 
 */
$(function() {
	$(".selectBorder-placeholder select").focus(function() {
		$(this).parent().css("border-color","#5cb3fd");
	});
	
	$(".selectBorder-placeholder select").focusout(function() {
		$(this).parent().css("border-color","rgba(0,0,0,.15)");
	});
	
	$(".inputBorder-placeholder input").focus(function() {
		$(this).parent().css("border-color","#5cb3fd");
	});
	
	$(".inputBorder-placeholder input").focusout(function() {
		$(this).parent().css("border-color","rgba(0,0,0,.15)");
	});
	
	$(".inputBorder-placeholder textarea").focus(function() {
		$(this).parent().css("border-color","#5cb3fd");
	});
	
	$(".inputBorder-placeholder textarea").focusout(function() {
		$(this).parent().css("border-color","rgba(0,0,0,.15)");
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
		var isValid = validateDate("Date of Birth", $(".dobMask").val());
		return isValid;
	});
	$("#studentBasicForm").find("#btnSave").click(function() {
		var isValid = validateDate("Date of Birth", $(".dobMask").val());
		return isValid;
	});
});

function countChar(obj, trackCharactersObjID) {
    var len = obj.value.length;
    $(trackCharactersObjID).text((obj.maxLength - len) + " characters remaining");
}

function displayModal(message) {
	$("#messageModal").find("#messageError").text(message);
	$("#messageModal").modal('show');
}

function validateDate(propertyName, propertyValue) {
	var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
	var errorMessage = "*Please provide valid " + propertyName + " in dd/mm/yyyy format";
	// Match the date format through regular expression  
	if (propertyValue.match(dateformat)) {
		//Test which seperator is used '/' or '-'  
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

		// Create list of days of a month [assume there is no leap year by default]  
		var ListofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		if (mm == 1 || mm > 2) {
			if (dd > ListofDays[mm - 1]) {
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
				displayModal(errorMessage);
				return false;
			}
			if ((lyear == true) && (dd > 29)) {
				displayModal(errorMessage);
				return false;
			}
		}
	} else {
		displayModal(errorMessage);
		return false;
	}
}