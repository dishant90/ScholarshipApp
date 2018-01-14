package com.tripleS.model;

import java.math.BigDecimal;
import java.time.Year;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tripleS.classLevelConstraints.ValidateResidenceDetails;
import com.tripleS.enums.CourseNameEnum;
import com.tripleS.enums.ResidenceOwnershipEnum;
import com.tripleS.enums.ResidenceTypeEnum;

@ValidateResidenceDetails
@Entity
@Table(name="student_curriculum_record")
public class StudentCurriculumRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="course_year")
    private String courseYear;
	
	@Column(name="passing_year")
    private String passingYear;
	
	@Column(name="percentage_marks")
	@NotNull(message="*Please specify score")
	@DecimalMin(value="0", message="*Please specify score")
	@Digits(integer=6, fraction=2, message="*Please specify valid score e.g. 85")
    private BigDecimal percentageMarks;
	
	@Column(name="grade")
    private String grade;

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="student_course_details_id")
	private StudentCourseDetails studentCourseDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(String courseDuration) {
		this.courseYear = courseDuration;
	}

	public String getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}

	public BigDecimal getPercentageMarks() {
		return percentageMarks;
	}

	public void setPercentageMarks(BigDecimal expectedTotalCourseFees) {
		this.percentageMarks = expectedTotalCourseFees;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String schoolCollegeInstituteName) {
		this.grade = schoolCollegeInstituteName;
	}

	public StudentCourseDetails getStudentCourseDetails() {
		return studentCourseDetails;
	}

	public void setStudentCourseDetails(StudentCourseDetails studentCourseDetails) {
		this.studentCourseDetails = studentCourseDetails;
	}

	@Override
	public String toString() {
		return "StudentCurriculumRecord [id=" + id + ", courseYear=" + courseYear + ", passingYear=" + passingYear
				+ ", percentageMarks=" + percentageMarks + ", grade=" + grade + ", studentCourseDetails="
				+ studentCourseDetails + "]";
	}

}
