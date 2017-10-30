package com.tripleS.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="entity_bank_details")
public class EntityBankDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="is_current")
    private Boolean isCurrent;
	
	@Column(name="bank_account_no")
	@NotEmpty(message = "*Please provide bank account number")
    private String bankAccountNo;
	
	@Column(name="bank_name")
	@NotEmpty(message = "*Please provide bank name")
	private String bankName;
	
	@Column(name="branch")
	private String branch;
	
	@Column(name="ifsc_code")
	@NotEmpty(message = "*Please provide IFSC Code")
	private String ifscCode;
	
	@Column(name="account_holder_name1")
	@NotEmpty(message = "*Please provide first account holder's name")
	private String accountHolderName1;
	
	@Column(name="account_holder_name2")
	private String accountHolderName2;
	
	@Column(name="account_holder_name3")
	private String accountHolderName3;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="entity_id")
	private EntityDetails entityDetails;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountHolderName1() {
		return accountHolderName1;
	}

	public void setAccountHolderName1(String accountHolderName1) {
		this.accountHolderName1 = accountHolderName1;
	}

	public String getAccountHolderName2() {
		return accountHolderName2;
	}

	public void setAccountHolderName2(String accountHolderName2) {
		this.accountHolderName2 = accountHolderName2;
	}

	public String getAccountHolderName3() {
		return accountHolderName3;
	}

	public void setAccountHolderName3(String accountHolderName3) {
		this.accountHolderName3 = accountHolderName3;
	}

	public EntityDetails getEntityDetails() {
		return entityDetails;
	}

	public void setEntityDetails(EntityDetails entityDetails) {
		this.entityDetails = entityDetails;
	}

	@Override
	public String toString() {
		return "EntityBankDetails [id=" + id + ", isCurrent=" + isCurrent + ", bankAccountNo=" + bankAccountNo
				+ ", bankName=" + bankName + ", branch=" + branch + ", ifscCode=" + ifscCode + ", accountHolderName1="
				+ accountHolderName1 + ", accountHolderName2=" + accountHolderName2 + ", accountHolderName3="
				+ accountHolderName3 + "]";
	}
}
