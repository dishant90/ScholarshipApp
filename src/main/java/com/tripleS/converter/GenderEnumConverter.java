package com.tripleS.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.tripleS.enums.GenderEnum;

@Converter
public class GenderEnumConverter implements AttributeConverter<GenderEnum, Character> {

	@Override
	public Character convertToDatabaseColumn(GenderEnum genderEnum) {
		switch (genderEnum) {
		case MALE:
			return 'M';
		case FEMALE:
			return 'F';
		case OTHER:
			return 'O';
		default:
			return null;
		}
	}

	@Override
	public GenderEnum convertToEntityAttribute(Character genderChar) {
		switch (genderChar) {
		case 'M':
			return GenderEnum.MALE;
		case 'F':
			return GenderEnum.FEMALE;
		case 'O':
			return GenderEnum.OTHER;
		default:
			return null;
		}
	}

}
