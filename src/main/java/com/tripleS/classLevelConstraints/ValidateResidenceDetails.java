package com.tripleS.classLevelConstraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ResidenceRentValidator.class })
@Documented
public @interface ValidateResidenceDetails {
	String message () default "Please enter valid residence rent amount";
	Class<?>[] groups () default {};
	Class<? extends Payload>[] payload () default {};
}
