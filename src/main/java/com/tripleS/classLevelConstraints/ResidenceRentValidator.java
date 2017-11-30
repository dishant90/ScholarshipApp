package com.tripleS.classLevelConstraints;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tripleS.enums.ResidenceOwnershipEnum;
import com.tripleS.model.ResidenceDetails;

public class ResidenceRentValidator implements ConstraintValidator<ValidateResidenceDetails, ResidenceDetails> {

	private static final Logger logger = LoggerFactory.getLogger(ResidenceRentValidator.class);

	@Override
	public void initialize(ValidateResidenceDetails arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(ResidenceDetails residenceDetails, ConstraintValidatorContext context) {

		if (residenceDetails != null) {
			if (ResidenceOwnershipEnum.RENTED.equals(residenceDetails.getResidenceOwnership())
					&& (residenceDetails.getResidenceRentAmount() == null
							|| residenceDetails.getResidenceRentAmount().doubleValue() <= 0)) {
				logger.error("Residence Ownership: " + residenceDetails.getResidenceOwnership());
				if (residenceDetails.getResidenceRentAmount() != null) {
					logger.error("Residence Rent Amount: " + residenceDetails.getResidenceRentAmount().doubleValue());
				}
				context.buildConstraintViolationWithTemplate("*Please enter residence rent amount")
						.addPropertyNode("residenceRentAmount").addConstraintViolation();
				return false;
			} else if (ResidenceOwnershipEnum.OTHER.equals(residenceDetails.getResidenceOwnership())
					&& (residenceDetails.getResidenceOwnershipOtherDescription() == null
							|| residenceDetails.getResidenceOwnershipOtherDescription().trim().isEmpty())) {
				context.buildConstraintViolationWithTemplate("*Please enter ownership description")
						.addPropertyNode("residenceOwnershipOtherDescription").addConstraintViolation();
				return false;
			} else if (residenceDetails.getHaveVehicle() != null) {
				if (residenceDetails.getHaveVehicle() && (residenceDetails.getVehicleDescription() == null
						|| residenceDetails.getVehicleDescription().trim().isEmpty())) {
					context.buildConstraintViolationWithTemplate("*Please enter vehicle details")
							.addPropertyNode("vehicleDescription").addConstraintViolation();
					return false;
				}
			}
		} else {
			// handle if the object is uninitialized
			return false;
		}

		return true;
	}

}
