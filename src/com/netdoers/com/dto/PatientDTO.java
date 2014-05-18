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
 * 1000A      VIKALP PATEL    04/02/14           RELEASE         ADDED IMAGES TO SX, IPD & OPD
 * P3A02      VIKALP PATEL    21/03/14           PHASE-III       ADDED IS SHARED OBJECT
 * P3A03      VIKALP PATEL    08/05/14           PHASE-III       ADDED IS SHARED PRIVATE OBJECT
 * P3A04      VIKALP PATEL    08/05/14           PHASE-III       ADDED SHARE CONTACT OBJECT
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.dto;

import java.util.ArrayList;


public class PatientDTO {

	public String _id;
	public String _newId;
	public String name;
	public String age;
	public String totalCount;
	public String diagnosis;
	public String type;
	public String serviceType;
	public String ref;
	public String location;
	public String startTime;
	public String time_spent;
	public String date;
	public String ward;
	public String emergency;
	public String teamMember;
	public String procedure;
	public String level;
	public String note;
	public String sex;
	public String status;
	public String sx_watch;
//	SA P3A03
	public String is_shared_private;

	public PatientDTO(String id, String newId, String name, String age,
			String totalCount, String diagnosis, String type, String ref,
			String location, String startTime, String timeSpent, String date,
			String ward, String emergency, String teamMember, String procedure,
			String level, String note, String sex, String status, String serviceType, String sx_watch,String is_shared,String is_shared_private) {
		_id = id;
		_newId = newId;
		this.name = name;
		this.age = age;
		this.totalCount = totalCount;
		this.diagnosis = diagnosis;
		this.type = type;
		this.ref = ref;
		this.location = location;
		this.startTime = startTime;
		time_spent = timeSpent;
		this.date = date;
		this.ward = ward;
		this.emergency = emergency;
		this.teamMember = teamMember;
		this.procedure = procedure;
		this.level = level;
		this.note = note;
		this.sex = sex;
		this.status = status;
		this.serviceType = serviceType;
		this.sx_watch = sx_watch;
		this.is_shared = is_shared;
		this.is_shared_private = is_shared_private;
	}
	public String getIs_shared_private() {
		return is_shared_private;
	}


	public void setIs_shared_private(String is_shared_private) {
		this.is_shared_private = is_shared_private;
	}
//	EA P3A03
	//	SA P3A02
	public String is_shared;
	
	public PatientDTO(String id, String newId, String name, String age,
			String totalCount, String diagnosis, String type, String ref,
			String location, String startTime, String timeSpent, String date,
			String ward, String emergency, String teamMember, String procedure,
			String level, String note, String sex, String status, String serviceType, String sx_watch,String is_shared) {
		_id = id;
		_newId = newId;
		this.name = name;
		this.age = age;
		this.totalCount = totalCount;
		this.diagnosis = diagnosis;
		this.type = type;
		this.ref = ref;
		this.location = location;
		this.startTime = startTime;
		time_spent = timeSpent;
		this.date = date;
		this.ward = ward;
		this.emergency = emergency;
		this.teamMember = teamMember;
		this.procedure = procedure;
		this.level = level;
		this.note = note;
		this.sex = sex;
		this.status = status;
		this.serviceType = serviceType;
		this.sx_watch = sx_watch;
		this.is_shared = is_shared;
	}
	
	
	public String getIs_shared() {
		return is_shared;
	}
	public void setIs_shared(String is_shared) {
		this.is_shared = is_shared;
	}
//	EA P3A02
	
//	SA P3A04
	public ArrayList<PatientShareDetailsDTO> contactPaths;
	
	public ArrayList<PatientShareDetailsDTO> getContactPaths() {
		return contactPaths;
	}
	public void setContactPaths(ArrayList<PatientShareDetailsDTO> contactPaths) {
		this.contactPaths = contactPaths;
	}
	public PatientDTO(String id, String newId, String name, String age,
			String totalCount, String diagnosis, String type, String ref,
			String location, String startTime, String timeSpent, String date,
			String ward, String emergency, String teamMember, String procedure,
			String level, String note, String sex, String status, String serviceType, String sx_watch, String is_shared, String is_shared_private,ArrayList<PatientDetailsDTO> paths,ArrayList<PatientShareDetailsDTO> contactPaths) {
		_id = id;
		_newId = newId;
		this.name = name;
		this.age = age;
		this.totalCount = totalCount;
		this.diagnosis = diagnosis;
		this.type = type;
		this.ref = ref;
		this.location = location;
		this.startTime = startTime;
		time_spent = timeSpent;
		this.date = date;
		this.ward = ward;
		this.emergency = emergency;
		this.teamMember = teamMember;
		this.procedure = procedure;
		this.level = level;
		this.note = note;
		this.sex = sex;
		this.status = status;
		this.serviceType = serviceType;
		this.sx_watch = sx_watch;
		this.is_shared = is_shared;
		this.is_shared_private = is_shared_private;
		this.paths = paths;
		this.contactPaths = contactPaths;
	}
	
//	EA P3A04
	
	public ArrayList<PatientDetailsDTO> paths;// ADDED 1000A
	
	public PatientDTO() {
	}
	//SA 1000A
	public PatientDTO(String id, String newId, String name, String age,
			String totalCount, String diagnosis, String type, String ref,
			String location, String startTime, String timeSpent, String date,
			String ward, String emergency, String teamMember, String procedure,
			String level, String note, String sex, String status, String serviceType, String sx_watch,ArrayList<PatientDetailsDTO> paths) {
		_id = id;
		_newId = newId;
		this.name = name;
		this.age = age;
		this.totalCount = totalCount;
		this.diagnosis = diagnosis;
		this.type = type;
		this.ref = ref;
		this.location = location;
		this.startTime = startTime;
		time_spent = timeSpent;
		this.date = date;
		this.ward = ward;
		this.emergency = emergency;
		this.teamMember = teamMember;
		this.procedure = procedure;
		this.level = level;
		this.note = note;
		this.sex = sex;
		this.status = status;
		this.serviceType = serviceType;
		this.sx_watch = sx_watch;
		this.paths = paths;
	}
	public ArrayList<PatientDetailsDTO> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<PatientDetailsDTO> paths) {
		this.paths = paths;
	}
	//EA 1000A
	public PatientDTO(String id, String newId, String name, String age,
			String totalCount, String diagnosis, String type, String ref,
			String location, String startTime, String timeSpent, String date,
			String ward, String emergency, String teamMember, String procedure,
			String level, String note, String sex, String status, String serviceType, String sx_watch) {
		_id = id;
		_newId = newId;
		this.name = name;
		this.age = age;
		this.totalCount = totalCount;
		this.diagnosis = diagnosis;
		this.type = type;
		this.ref = ref;
		this.location = location;
		this.startTime = startTime;
		time_spent = timeSpent;
		this.date = date;
		this.ward = ward;
		this.emergency = emergency;
		this.teamMember = teamMember;
		this.procedure = procedure;
		this.level = level;
		this.note = note;
		this.sex = sex;
		this.status = status;
		this.serviceType = serviceType;
		this.sx_watch = sx_watch;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String id) {
		_id = id;
	}
	public String get_newId() {
		return _newId;
	}
	public void set_newId(String newId) {
		_newId = newId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getTime_spent() {
		return time_spent;
	}
	public void setTime_spent(String timeSpent) {
		time_spent = timeSpent;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	public String getTeamMember() {
		return teamMember;
	}
	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSx_watch() {
		return sx_watch;
	}
	public void setSx_watch(String sxWatch) {
		sx_watch = sxWatch;
	}
}
