package org.bdp.basic.entity.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

// default package

/**
 * AbstractBasicOrgJh entity provides the base persistence definition of the
 * BasicOrgJh entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBasicOrgJh implements java.io.Serializable {

	// Fields

	/** */
	private static final long serialVersionUID = 489305414362129070L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Basic_Org_Jh_Seq")
    @SequenceGenerator(sequenceName = "Basic_Org_Jh_Seq", allocationSize = 1, name = "Basic_Org_Jh_Seq")
    private Long orgJhId;

    @Column(name = "member_Code")
	private String memberCode;

    @Column(name = "org_Code")
	private String orgCode;

    @Column(name = "id_Id_No")
	private String idIdNo;

    @Column(name = "id_No_Nme")
	private String idNoNme;

    @Column(name = "org_Jh_Remark")
	private String orgJhRemark;

    @Column(name = "org_Jh_State")
	private Long orgJhState;

    @Column(name = "data_Systemdate")
	private Date dataSystemdate;

    @Column(name = "data_Cancel_Date")
	private Date dataCancelDate;

    @Column(name = "data_Cancel_Person")
	private String dataCancelPerson;

	// Constructors

	/** default constructor */
	public AbstractBasicOrgJh() {
	}

	/** minimal constructor */
	public AbstractBasicOrgJh(String memberCode, String orgCode) {
		this.memberCode = memberCode;
		this.orgCode = orgCode;
	}

	/** full constructor */
	public AbstractBasicOrgJh(String memberCode, String orgCode, String idIdNo,
			String idNoNme, String orgJhRemark, Long orgJhState) {
		this.memberCode = memberCode;
		this.orgCode = orgCode;
		this.idIdNo = idIdNo;
		this.idNoNme = idNoNme;
		this.orgJhRemark = orgJhRemark;
		this.orgJhState = orgJhState;
	}

	// Property accessors

	public Long getOrgJhId() {
		return this.orgJhId;
	}

	public void setOrgJhId(Long orgJhId) {
		this.orgJhId = orgJhId;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIdIdNo() {
		return this.idIdNo;
	}

	public void setIdIdNo(String idIdNo) {
		this.idIdNo = idIdNo;
	}

	public String getIdNoNme() {
		return this.idNoNme;
	}

	public void setIdNoNme(String idNoNme) {
		this.idNoNme = idNoNme;
	}

	public String getOrgJhRemark() {
		return this.orgJhRemark;
	}

	public void setOrgJhRemark(String orgJhRemark) {
		this.orgJhRemark = orgJhRemark;
	}

	public Long getOrgJhState() {
		return this.orgJhState;
	}

	public void setOrgJhState(Long orgJhState) {
		this.orgJhState = orgJhState;
	}

	public Date getDataSystemdate() {
		return dataSystemdate;
	}

	public void setDataSystemdate(Date dataSystemdate) {
		this.dataSystemdate = dataSystemdate;
	}

	public Date getDataCancelDate() {
		return dataCancelDate;
	}

	public void setDataCancelDate(Date dataCancelDate) {
		this.dataCancelDate = dataCancelDate;
	}

	public String getDataCancelPerson() {
		return dataCancelPerson;
	}

	public void setDataCancelPerson(String dataCancelPerson) {
		this.dataCancelPerson = dataCancelPerson;
	}

}