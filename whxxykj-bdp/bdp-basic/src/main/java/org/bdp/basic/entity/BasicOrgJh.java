package org.bdp.basic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.bdp.basic.entity.mapper.AbstractBasicOrgJh;
import org.bdp.glodal.common.superclass.SuperConstants;

/**
 * BasicOrgJh entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Basic_Org_Jh")
public class BasicOrgJh extends AbstractBasicOrgJh implements
		java.io.Serializable {

	// Constructors

	/** */
	private static final long serialVersionUID = -5432753729507798130L;
	/**新增、修改、删除标志（新增：0 修改：1 删除：2） */
	private int dataFlag = Integer.valueOf(SuperConstants.DATAFLAG_1);
	/** 机构名称*/
    @Transient
	private String orgName;
	/** 收款识状态（0：开通   1：注销）*/
    @Transient
	private String orgJhStateStr;
	/** 交行签约的机构编号*/
    @Transient
	private String orgIdJh;
	/** 交行第三方编号*/
    @Transient
	private String thdId;
	
	/** default constructor */
	public BasicOrgJh() {
	}

	/** minimal constructor */
	public BasicOrgJh(String memberCode, String orgCode) {
		super(memberCode, orgCode);
	}

	/** full constructor */
	public BasicOrgJh(String memberCode, String orgCode, String idIdNo,
			String idNoNme, String orgJhRemark, Long orgJhState) {
		super(memberCode, orgCode, idIdNo, idNoNme, orgJhRemark, orgJhState);
	}
	
	public int getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(int dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getOrgName() {
//		setOrgName(BaseConstants.getOrgAbbreviate(getOrgCode(), getMemberCode()));
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgJhStateStr() {
		setOrgJhStateStr("启用");
		if(null != getOrgJhState()){
			if(Long.valueOf(1L).equals(getOrgJhState())){
				setOrgJhStateStr("注销");
			}
		}
		return orgJhStateStr;
	}

	public void setOrgJhStateStr(String orgJhStateStr) {
		this.orgJhStateStr = orgJhStateStr;
	}

	public String getOrgIdJh() {
		return orgIdJh;
	}

	public void setOrgIdJh(String orgIdJh) {
		this.orgIdJh = orgIdJh;
	}

	public String getThdId() {
		return thdId;
	}

	public void setThdId(String thdId) {
		this.thdId = thdId;
	}

}
