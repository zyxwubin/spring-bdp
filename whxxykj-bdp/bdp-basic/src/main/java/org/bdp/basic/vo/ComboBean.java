package org.bdp.basic.vo;

public class ComboBean {
	private String orgCode;
	private String orgName;
	private String orgAbbreviate;
	private Long orgSignature;
    private Long orgSignatureAbroad;
    
	public ComboBean(){
	}
	
	public ComboBean(String orgCode, String orgName,String orgAbbreviate) {
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.orgAbbreviate = orgAbbreviate;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgAbbreviate() {
		return orgAbbreviate;
	}
	public void setOrgAbbreviate(String orgAbbreviate) {
		this.orgAbbreviate = orgAbbreviate;
	}

	public Long getOrgSignature() {
		return orgSignature;
	}

	public void setOrgSignature(Long orgSignature) {
		this.orgSignature = orgSignature;
	}

	public Long getOrgSignatureAbroad() {
		return orgSignatureAbroad;
	}

	public void setOrgSignatureAbroad(Long orgSignatureAbroad) {
		this.orgSignatureAbroad = orgSignatureAbroad;
	}
	
}
