package com.tripleS.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;

import com.tripleS.enums.FileStatusEnum;

@Entity
@Table(name="student_file")
public class StudentFile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="file_no")
    private String fileNo;
	
	@Column(name="file_status")
	@Enumerated(EnumType.STRING)
	//@NotEmpty(message="*File status cannot be empty")
    private FileStatusEnum fileStatus;
	
	@Column(name="created_by")
	//@NotEmpty(message="*Created By cannot be empty")
    private String createdBy;
	
	@Column(name="interviewed_by")
    private String interviewedBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
    private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="interviewed_date")
    private Date interviewedDate;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entity_id")
	@Valid
    private EntityDetails entityDetails;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public FileStatusEnum getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(FileStatusEnum fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getInterviewedBy() {
		return interviewedBy;
	}
	public void setInterviewedBy(String interviewedBy) {
		this.interviewedBy = interviewedBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getInterviewedDate() {
		return interviewedDate;
	}
	public void setInterviewedDate(Date interviewedDate) {
		this.interviewedDate = interviewedDate;
	}
	
	public EntityDetails getEntityDetails() {
		return entityDetails;
	}
	public void setEntityDetails(EntityDetails entityDetails) {
		this.entityDetails = entityDetails;
	}
	
	@Override
    public String toString() {
        return String.format(
                "File[ID=%d, Number=%s, Status=%s]",
                id, fileNo, fileStatus);
    }
}
