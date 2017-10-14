package com.tripleS.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="entity_details")
public class EntityDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="entity_type")
    private String type;
	
	@Column(name="entity_category")
    private String category;
	
	@Column(name="first_name")
	@NotEmpty(message = "*Please provide first name")
    private String firstName;
	
	@Column(name="middle_name")
    private String middleName;
	
	@Column(name="last_name")
    private String lastName;
	
	@Column(name="full_name")
    private String fullName;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="telephone_no")
    private String telephoneNo;
	
	@Column(name="email_id")
	@Email(message = "*Please provide a valid Email Address")
	@NotEmpty(message = "*Please provide an email address")
	private String emailID;
	
	@Column(name="mobile_no")
	@Pattern(regexp="(^$|[0-9]{10})", message = "*Mobile number must be exactly 10 digits")
	@NotEmpty(message = "*Please provide mobile number")
    private String mobileNo;
	
	@Column(name="alternate_contact_no")
	@Pattern(regexp="(^$|[0-9]{10})", message = "*Alternate contact number must be exactly 10 digits")
    private String alternateContactNo;
	
	@Column(name="qualification")
	private String qualification;
	
	@Column(name="salary_per_month")
    private BigDecimal salaryPerMonth;
	
	@Column(name="occupation_profession")
    private String occupationProfession;
	
	@Column(name="education_loan_amount")
    private BigDecimal educationLoanAmount;
	
	@Column(name="help_amount")
    private BigDecimal helpAmount;

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="reference_entity_id")
    private EntityDetails applicant;
	
	@OneToOne(mappedBy = "entityDetails")
	private StudentFile studentFile;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
	@Valid
    private EntityAddressDetails entityAddressDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
	@Valid
    private StudentDetails studentDetails;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="applicant")
	private List<EntityDetails> relatives;
	
	public StudentDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	public StudentFile getStudentFile() {
		return studentFile;
	}
	public void setStudentFile(StudentFile studentFile) {
		this.studentFile = studentFile;
	}
    
    public EntityDetails getApplicant() {
		return applicant;
	}
	public void setApplicant(EntityDetails applicant) {
		this.applicant = applicant;
	}
	
	public List<EntityDetails> getRelatives() {
		return relatives;
	}
	public void setRelatives(List<EntityDetails> relatives) {
		this.relatives = relatives;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAlternateContactNo() {
		return alternateContactNo;
	}
	public void setAlternateContactNo(String alternateContactNo) {
		this.alternateContactNo = alternateContactNo;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public BigDecimal getSalaryPerMonth() {
		return salaryPerMonth;
	}
	public void setSalaryPerMonth(BigDecimal salaryPerMonth) {
		this.salaryPerMonth = salaryPerMonth;
	}
	public String getOccupationProfession() {
		return occupationProfession;
	}
	public void setOccupationProfession(String occupationProfession) {
		this.occupationProfession = occupationProfession;
	}
	public BigDecimal getEducationLoanAmount() {
		return educationLoanAmount;
	}
	public void setEducationLoanAmount(BigDecimal educationLoanAmount) {
		this.educationLoanAmount = educationLoanAmount;
	}
	public BigDecimal getHelpAmount() {
		return helpAmount;
	}
	public void setHelpAmount(BigDecimal helpAmount) {
		this.helpAmount = helpAmount;
	}
	public EntityAddressDetails getEntityAddressDetails() {
		return entityAddressDetails;
	}
	public void setEntityAddressDetails(EntityAddressDetails entityAddressDetails) {
		this.entityAddressDetails = entityAddressDetails;
	}

	@Override
	public String toString() {
		return "EntityDetails [id=" + id + ", type=" + type + ", category=" + category + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
