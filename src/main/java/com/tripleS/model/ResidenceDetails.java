package com.tripleS.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="residence_details")
public class ResidenceDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="residence_ownership")
	@NotEmpty(message="*Residence ownership cannot be empty")
    private String residenceOwnership;
	
	@Column(name="residence_ownership_other_description")
    private String residenceOwnershipOtherDescription;
	
	@Column(name="residence_type")
	@NotEmpty(message="*Residence type cannot be empty")
    private String residenceType;
	
	@Column(name="vehicle_description")
    private String vehicleDescription;

	@Column(name="residence_size_in_sqft")
	@NotNull(message="*Please specify residence size")
	@DecimalMin(value="1", message="*Please specify residence size")
    private BigDecimal residenceSizeInSqft;
	
	@Column(name="residence_rent_amount")
    private BigDecimal residenceRentAmount;
	
	@Column(name="have_water_supply")
    private Boolean haveWaterSupply;
	
	@Column(name="have_bathroom")
    private Boolean haveBathroom;
	
	@Column(name="have_toilet")
    private Boolean haveToilet;
	
	@Column(name="have_cot_bed")
    private Boolean haveCotBed;
	
	@Column(name="have_cup_board")
    private Boolean haveCupBoard;
	
	@Column(name="have_cooking_gas")
    private Boolean haveCookingGas;
	
	@Column(name="have_television")
    private Boolean haveTelevision;
	
	@Column(name="have_refrigerator")
    private Boolean haveRefrigerator;
	
	@Column(name="have_vehicle")
    private Boolean haveVehicle;
	
	@Column(name="have_washing_machine")
    private Boolean haveWashingMachine;
	
	@Column(name="have_oven")
    private Boolean haveOven;
	
	@Column(name="have_computer")
    private Boolean haveComputer;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="entity_id")
	private EntityDetails entityDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResidenceOwnership() {
		return residenceOwnership;
	}

	public void setResidenceOwnership(String residenceOwnership) {
		this.residenceOwnership = residenceOwnership;
	}

	public String getResidenceOwnershipOtherDescription() {
		return residenceOwnershipOtherDescription;
	}

	public void setResidenceOwnershipOtherDescription(String residenceOwnershipOtherDescription) {
		this.residenceOwnershipOtherDescription = residenceOwnershipOtherDescription;
	}

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getVehicleDescription() {
		return vehicleDescription;
	}

	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}

	public BigDecimal getResidenceSizeInSqft() {
		return residenceSizeInSqft;
	}

	public void setResidenceSizeInSqft(BigDecimal residenceSizeInSqft) {
		this.residenceSizeInSqft = residenceSizeInSqft;
	}

	public BigDecimal getResidenceRentAmount() {
		return residenceRentAmount;
	}

	public void setResidenceRentAmount(BigDecimal residenceRentAmount) {
		this.residenceRentAmount = residenceRentAmount;
	}

	public Boolean getHaveWaterSupply() {
		return haveWaterSupply;
	}

	public void setHaveWaterSupply(Boolean haveWaterSupply) {
		this.haveWaterSupply = haveWaterSupply;
	}

	public Boolean getHaveBathroom() {
		return haveBathroom;
	}

	public void setHaveBathroom(Boolean haveBathroom) {
		this.haveBathroom = haveBathroom;
	}

	public Boolean getHaveToilet() {
		return haveToilet;
	}

	public void setHaveToilet(Boolean haveToilet) {
		this.haveToilet = haveToilet;
	}

	public Boolean getHaveCotBed() {
		return haveCotBed;
	}

	public void setHaveCotBed(Boolean haveCotBed) {
		this.haveCotBed = haveCotBed;
	}

	public Boolean getHaveCupBoard() {
		return haveCupBoard;
	}

	public void setHaveCupBoard(Boolean haveCupBoard) {
		this.haveCupBoard = haveCupBoard;
	}

	public Boolean getHaveCookingGas() {
		return haveCookingGas;
	}

	public void setHaveCookingGas(Boolean haveCookingGas) {
		this.haveCookingGas = haveCookingGas;
	}

	public Boolean getHaveTelevision() {
		return haveTelevision;
	}

	public void setHaveTelevision(Boolean haveTelevision) {
		this.haveTelevision = haveTelevision;
	}

	public Boolean getHaveRefrigerator() {
		return haveRefrigerator;
	}

	public void setHaveRefrigerator(Boolean haveRefrigerator) {
		this.haveRefrigerator = haveRefrigerator;
	}

	public Boolean getHaveVehicle() {
		return haveVehicle;
	}

	public void setHaveVehicle(Boolean haveVehicle) {
		this.haveVehicle = haveVehicle;
	}

	public Boolean getHaveWashingMachine() {
		return haveWashingMachine;
	}

	public void setHaveWashingMachine(Boolean haveWashingMachine) {
		this.haveWashingMachine = haveWashingMachine;
	}

	public Boolean getHaveOven() {
		return haveOven;
	}

	public void setHaveOven(Boolean haveOven) {
		this.haveOven = haveOven;
	}

	public Boolean getHaveComputer() {
		return haveComputer;
	}

	public void setHaveComputer(Boolean haveComputer) {
		this.haveComputer = haveComputer;
	}

	public EntityDetails getEntityDetails() {
		return entityDetails;
	}

	public void setEntityDetails(EntityDetails entityDetails) {
		this.entityDetails = entityDetails;
	}

	@Override
	public String toString() {
		return "ResidenceDetails [id=" + id + ", residenceOwnership=" + residenceOwnership
				+ ", residenceOwnershipOtherDescription=" + residenceOwnershipOtherDescription + ", residenceType="
				+ residenceType + ", residenceSizeInSqft=" + residenceSizeInSqft + "]";
	}
	
}
