package com.tripleS.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tripleS.enums.BranchNameEnum;
import com.tripleS.enums.CourseNameEnum;

@Entity
@Table(name="student_course_details")
public class StudentCourseDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="course_name")
	@Enumerated(EnumType.STRING)
	@NotNull(message="*Please select a course")
    private CourseNameEnum courseName;
	
	@Column(name="branch")
	@Enumerated(EnumType.STRING)
	@NotNull(message="*Please select a branch")
    private BranchNameEnum branch;
	
	@Column(name="course_duration")
    private String courseDuration;
	
	@Column(name="expected_total_course_fees")
	//@NotNull(message="*Please specify expected total course fees")
	//@DecimalMin(value="1", message="*Please specify expected total course fees")
	//@Digits(integer=10, fraction=2, message="*Please specify valid expected total course fees e.g. 65000")
    private BigDecimal expectedTotalCourseFees;
	
	@Column(name="school_college_institute_name")
	@NotEmpty(message="*Please provide school/college name")
    private String schoolCollegeInstituteName;

	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="entity_id")
	private EntityDetails entityDetails;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "studentCourseDetails")
	private List<StudentCurriculumRecord> studentCurriculumRecord;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CourseNameEnum getCourseName() {
		return courseName;
	}

	public void setCourseName(CourseNameEnum courseName) {
		this.courseName = courseName;
	}

	public BranchNameEnum getBranch() {
		return branch;
	}

	public void setBranch(BranchNameEnum branch) {
		this.branch = branch;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public BigDecimal getExpectedTotalCourseFees() {
		return expectedTotalCourseFees;
	}

	public void setExpectedTotalCourseFees(BigDecimal expectedTotalCourseFees) {
		this.expectedTotalCourseFees = expectedTotalCourseFees;
	}

	public String getSchoolCollegeInstituteName() {
		return schoolCollegeInstituteName;
	}

	public void setSchoolCollegeInstituteName(String schoolCollegeInstituteName) {
		this.schoolCollegeInstituteName = schoolCollegeInstituteName;
	}

	public EntityDetails getEntityDetails() {
		return entityDetails;
	}

	public void setEntityDetails(EntityDetails entityDetails) {
		this.entityDetails = entityDetails;
	}

	public List<StudentCurriculumRecord> getStudentCurriculumRecord() {
		return studentCurriculumRecord;
	}

	public void setStudentCurriculumRecord(List<StudentCurriculumRecord> studentCurriculumRecord) {
		this.studentCurriculumRecord = studentCurriculumRecord;
	}

	@Override
	public String toString() {
		return "StudentCourseDetails [id=" + id + ", courseName=" + courseName + ", courseDuration=" + courseDuration
				+ ", expectedTotalCourseFees=" + expectedTotalCourseFees + ", schoolCollegeInstituteName="
				+ schoolCollegeInstituteName + ", entityDetails=" + entityDetails + "]";
	}

}
