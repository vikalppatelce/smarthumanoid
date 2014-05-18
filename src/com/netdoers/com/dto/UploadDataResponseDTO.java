/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 1000A      VIKALP PATEL    04/02/14         RELEASE          ADDED IMAGES IN SX, IPD & OPD
 * 1000B-3    VIKALP PATEL    11/02/14         RELEASE          ADDED PAYMENT API
 * 1000F      VIKALP PATEL    05/03/14         DEPOSITEDBANK    ADDED DEPOSITED BANK LOV
 * P3A01      VIKALP PATEL    10/05/14         PHASEIII         ADDED SERVICE PRIVATE CONTACTS SHARING
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.dto;

public class UploadDataResponseDTO {

	String servive;
	String expense;
	String expense_image;
	String service_audio;
	String location;
	String expense_category;
	String patient_type;
	String payment_mode;
	String procedure;
	String referred_by;
	String start_time;
	String surgery_level;
	String team_member;
	String ward;
	String depositedbank;
//	SA P3A03
	String service_contact;
	public String getService_contact() {
		return service_contact;
	}

	public void setService_contact(String service_contact) {
		this.service_contact = service_contact;
	}
	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward, String service_image,String payment,String passhash,String depositedBank,String service_contact) 
	{
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
		this.passhash = passhash;
		this.payment = payment;
		this.service_image = service_image;
		this.depositedbank = depositedBank;
		this.service_contact = service_contact;
	}
	//	EA P3A03
	//SA 1000F
	public String getDepositedbank() {
		return depositedbank;
	}

	public void setDepositedbank(String depositedbank) {
		this.depositedbank = depositedbank;
	}
	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward, String service_image,String payment,String passhash,String depositedBank) 
	{
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
		this.passhash = passhash;
		this.payment = payment;
		this.service_image = service_image;
		this.depositedbank = depositedBank;
	}
	
	//EA 1000F
	//SA 1000E
	String passhash;
	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward, String service_image,String payment,String passhash) 
	{
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
		this.passhash = passhash;
		this.payment = payment;
		this.service_image = service_image;
	}

	public String getPasshash() {
		return passhash;
	}

	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}
	//EA 1000E
	//SA 1000B-3
	String payment;
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward, String service_image,String payment) {
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
		this.service_image=service_image;
		this.payment=payment;
	}
	//EA 1000B-3
	//SA 1000A
	String service_image;
	
	public String getService_image() {
		return service_image;
	}

	public void setService_image(String service_image) {
		this.service_image = service_image;
	}

	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward, String service_image) {
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
		this.service_image=service_image;
	}
	//EA 1000A
	
	public UploadDataResponseDTO(String servive, String expense,
			String expenseImage, String serviceAudio, String location,
			String expenseCategory, String patientType, String paymentMode,
			String procedure, String referredBy, String startTime,
			String surgeryLevel, String teamMember, String ward) {
		this.servive = servive;
		this.expense = expense;
		expense_image = expenseImage;
		service_audio = serviceAudio;
		this.location = location;
		expense_category = expenseCategory;
		patient_type = patientType;
		payment_mode = paymentMode;
		this.procedure = procedure;
		referred_by = referredBy;
		start_time = startTime;
		surgery_level = surgeryLevel;
		team_member = teamMember;
		this.ward = ward;
	}
	public UploadDataResponseDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getServive() {
		return servive;
	}
	public void setServive(String servive) {
		this.servive = servive;
	}
	public String getExpense() {
		return expense;
	}
	public void setExpense(String expense) {
		this.expense = expense;
	}
	public String getExpense_image() {
		return expense_image;
	}
	public void setExpense_image(String expenseImage) {
		expense_image = expenseImage;
	}
	public String getService_audio() {
		return service_audio;
	}
	public void setService_audio(String serviceAudio) {
		service_audio = serviceAudio;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getExpense_category() {
		return expense_category;
	}
	public void setExpense_category(String expenseCategory) {
		expense_category = expenseCategory;
	}
	public String getPatient_type() {
		return patient_type;
	}
	public void setPatient_type(String patientType) {
		patient_type = patientType;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String paymentMode) {
		payment_mode = paymentMode;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public String getReferred_by() {
		return referred_by;
	}
	public void setReferred_by(String referredBy) {
		referred_by = referredBy;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getSurgery_level() {
		return surgery_level;
	}
	public void setSurgery_level(String surgeryLevel) {
		surgery_level = surgeryLevel;
	}
	public String getTeam_member() {
		return team_member;
	}
	public void setTeam_member(String teamMember) {
		team_member = teamMember;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
}
