package org.bdp.glodal.action;

import java.util.List;
import java.util.Map;

public class UserSession {
	/** 使用代码代码*/
	private String memberCode;
	/** 会员或者是往来单位名称*/
	private String memberName;
	/** 机构代码*/
	private String orgCode;
	/** 机构名称*/
	private String orgName;
	/** 机构简称*/
	private String orgAbbreviate;
	/**部门代码*/
	private String deptCode;
	/**部门名称*/
	private String deptName;
	/**员工代码 */
	private String employeeCode;
	/**员工名称 */
	private String employeeName;
	/** 操作员登录账号*/
	private String operatorUserid;
	/** 操作员身份  */
	private Long operatorIdentity;
	/** 操作员代码*/
	private String operatorCode;
	/** 操作员名称*/
	private String operatorUser;
	/** 操作员呢称 */
	private String operatorNickname;
	/** 操作员手机号 */
	private String operatorMobile;
	/** 操作员类型 */
	private Long operatorType;
	/**集团财务帐套信息*/
	public String ledgerCode;
	private String ip;
	private String hostname;
	/** 服务器名称 */
	private String serverName;
	/** 登录时间 */
	private String loginTimeStr;
	/** sessionId */
	private String sessionId;
	/** mac地址 */
	private String macAddr;
	/** 操作员按钮权限 */
	private Map<String, Boolean> buttonPermissionMap;
	/** 操作员权限 */
	private Map<String, Boolean> permissionMap;
	/** 操作员数据权限 启用*/
	private Map<String, String> opDataMap;
	/** 操作员数据只读权限 map<map<key==数据权限类别,对应用户数据权限信息('1','2')>> */
	private Map<String,String> opDataReadMap;
	/** 操作员数据写权限 map<map<key==数据权限类别,对应用户数据权限信息('1','2')>> */
	private Map<String,String> opDataWriteMap;
	/** 行业代码 */
	private String industryCode;
	/** 操作员角色*/
	private String roleCodelist;
	
	private String permissionOrgCodeStr;
	private String permissionDeptCodeStr;
	/**微鑫查询菜单权限 操作员权限 */
//	private List<ConfigPermission> permissionList;
	/** 是否打开待办  1-打开待办*/
	private String openTodo;
	/** 股份一体化用户ID */
	private Long crccuserId;
	
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperatorNickname() {
		return operatorNickname;
	}

	public void setOperatorNickname(String operatorNickname) {
		if(null==operatorNickname){
			operatorNickname="";
		}
		this.operatorNickname = operatorNickname;
	}

	public String getOperatorMobile() {
		return operatorMobile;
	}

	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}

	public Long getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Long operatorType) {
		this.operatorType = operatorType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getOrgAbbreviate() {
		return orgAbbreviate;
	}

	public void setOrgAbbreviate(String orgAbbreviate) {
		this.orgAbbreviate = orgAbbreviate;
	}

	public String getLoginTimeStr() {
		return loginTimeStr;
	}

	public void setLoginTimeStr(String loginTimeStr) {
		this.loginTimeStr = loginTimeStr;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLedgerCode() {
		return ledgerCode;
	}

	public void setLedgerCode(String ledgerCode) {
		this.ledgerCode = ledgerCode;
	}
	public String getOperatorUserid() {
		return operatorUserid;
	}

	public void setOperatorUserid(String operatorUserid) {
		this.operatorUserid = operatorUserid;
	}

	public Long getOperatorIdentity() {
		return operatorIdentity;
	}

	public void setOperatorIdentity(Long operatorIdentity) {
		this.operatorIdentity = operatorIdentity;
	}

	public String getOperatorUser() {
		return operatorUser;
	}

	public void setOperatorUser(String operatorUser) {
		this.operatorUser = operatorUser;
	}

//	@JSON(serialize=false)
	public Map<String, Boolean> getButtonPermissionMap() {
		return buttonPermissionMap;
	}

//	@JSON(serialize=false)
	public Map<String, Boolean> getPermissionMap() {
		return permissionMap;
	}

	public void setButtonPermissionMap(Map<String, Boolean> buttonPermissionMap) {
		this.buttonPermissionMap = buttonPermissionMap;
	}

	public void setPermissionMap(Map<String, Boolean> permissionMap) {
		this.permissionMap = permissionMap;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

//	@JSON(serialize=false)
	public Map<String, String> getOpDataReadMap() {
		return opDataReadMap;
	}

	public void setOpDataReadMap(Map<String, String> opDataReadMap) {
		this.opDataReadMap = opDataReadMap;
	}

//	@JSON(serialize=false)
	public Map<String, String> getOpDataWriteMap() {
		return opDataWriteMap;
	}

	public void setOpDataWriteMap(Map<String, String> opDataWriteMap) {
		this.opDataWriteMap = opDataWriteMap;
	}

//	@JSON(serialize=false)
	public Map<String, String> getOpDataMap() {
		return opDataMap;
	}

	public void setOpDataMap(Map<String, String> opDataMap) {
		this.opDataMap = opDataMap;
	}

	public String getRoleCodelist() {
		if(null==roleCodelist){
			roleCodelist="";
		}
		return roleCodelist;
	}

	public void setRoleCodelist(String roleCodelist) {
		this.roleCodelist = roleCodelist;
	}
	public String getPermissionOrgCodeStr() {
		return permissionOrgCodeStr;
	}

	public void setPermissionOrgCodeStr(String permissionOrgCodeStr) {
		this.permissionOrgCodeStr = permissionOrgCodeStr;
	}

	public String getPermissionDeptCodeStr() {
		return permissionDeptCodeStr;
	}

	public void setPermissionDeptCodeStr(String permissionDeptCodeStr) {
		this.permissionDeptCodeStr = permissionDeptCodeStr;
	}

//	public List<ConfigPermission> getPermissionList() {
//		return permissionList;
//	}
//
//	public void setPermissionList(List<ConfigPermission> permissionList) {
//		this.permissionList = permissionList;
//	}
//	
//	@JSON(serialize=false)
	public String getOpenTodo() {
		return openTodo;
	}

	public void setOpenTodo(String openTodo) {
		this.openTodo = openTodo;
	}

	public Long getCrccuserId() {
		return crccuserId;
	}

	public void setCrccuserId(Long crccuserId) {
		this.crccuserId = crccuserId;
	}
}
