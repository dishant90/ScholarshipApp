package com.tripleS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotEmpty;

import com.tripleS.validation.groups.StudentValidations;

@Entity
@Table(name = "student_entity_details")
public class StudentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "mother_tongue")
	@NotEmpty(message = "*Please provide mother tongue", groups = { StudentValidations.class })
	private String motherTongue;

	@Column(name = "religion")
	@NotEmpty(message = "*Please provide religion", groups = { StudentValidations.class })
	private String religion;

	@Column(name = "describe_prize_details")
	private String describePrizeDetails;

	@Column(name = "aim_in_life")
	private String aimInLife;

	@Column(name = "describe_job_business_details")
	private String describeJobBusinessDetails;

	@Column(name = "describe_other_problems_in_family")
	private String describeOtherProblemsInFamily;

	@Column(name = "got_help_from_other_sources")
	private Boolean gotHelpFromOtherSources;

	@Column(name = "have_education_loan")
	private Boolean haveEducationLoan;

	@Column(name = "describe_if_other_family_members_got_help")
	private String describeIfOtherFamilyMembersGotHelp;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	// @DateTimeFormat(pattern = "dd/MM/yyyy")
	// @NotNull(message = "*Please provide date of birth")
	private Date dateOfBirth;

	@Column(name = "place_of_birth")
	@NotEmpty(message = "*Please provide place of birth", groups = { StudentValidations.class })
	private String placeOfBirth;

	@OneToOne(mappedBy = "studentDetails")
	private EntityDetails entityDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getDescribePrizeDetails() {
		return describePrizeDetails;
	}

	public void setDescribePrizeDetails(String describePrizeDetails) {
		this.describePrizeDetails = describePrizeDetails;
	}

	public String getAimInLife() {
		return aimInLife;
	}

	public void setAimInLife(String aimInLife) {
		this.aimInLife = aimInLife;
	}

	public String getDescribeJobBusinessDetails() {
		return describeJobBusinessDetails;
	}

	public void setDescribeJobBusinessDetails(String describeJobBusinessDetails) {
		this.describeJobBusinessDetails = describeJobBusinessDetails;
	}

	public String getDescribeOtherProblemsInFamily() {
		return describeOtherProblemsInFamily;
	}

	public void setDescribeOtherProblemsInFamily(String describeOtherProblemsInFamily) {
		this.describeOtherProblemsInFamily = describeOtherProblemsInFamily;
	}

	public Boolean getGotHelpFromOtherSources() {
		return gotHelpFromOtherSources;
	}

	public void setGotHelpFromOtherSources(Boolean gotHelpFromOtherSources) {
		this.gotHelpFromOtherSources = gotHelpFromOtherSources;
	}

	public Boolean getHaveEducationLoan() {
		return haveEducationLoan;
	}

	public void setHaveEducationLoan(Boolean haveEducationLoan) {
		this.haveEducationLoan = haveEducationLoan;
	}

	public String getDescribeIfOtherFamilyMembersGotHelp() {
		return describeIfOtherFamilyMembersGotHelp;
	}

	public void setDescribeIfOtherFamilyMembersGotHelp(String describeIfOtherFamilyMembersGotHelp) {
		this.describeIfOtherFamilyMembersGotHelp = describeIfOtherFamilyMembersGotHelp;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public EntityDetails getEntityDetails() {
		return entityDetails;
	}

	public void setEntityDetails(EntityDetails entityDetails) {
		this.entityDetails = entityDetails;
	}
}
