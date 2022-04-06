package org.bdp.glodal.common.superclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.common.vo.SortData;
import org.bdp.glodal.util.BeanUtil;
import org.bdp.glodal.util.JsonUtil;
import org.bdp.glodal.util.StringUtil;
 
/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-3-7
 * Create Author: gp
 * File Name:  文件名称说明
 * Last version:  1.0
 * Function:这里写注释
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class SuperBean {
	/**用户信息*/
	private UserSession user;
	/** 排序属性*/
	private String sort;
	/** 模糊匹配列信息*/
	private String[] likeCol;
	/** grid可见列*/
	private String visibleColumns;
	/**机构数据权限(业务机构,货权机构)*/
	private String orgParam;
	/**机构数据参数值 用来检测单条记录 */
	private String orgParamValue;
	/**第二个机构设置的数据权限设置(和第一个机构关系用or)   机构参数名 a.orgCode  */
	private String orgTwoParam;
	/**第三个机构设置的数据权限设置(和第一个机构关系用or)   机构参数名 a.orgCode  */
	private String orgThParam;
	/**第二个机构设置的数据权限设置(和第一个机构关系用or)  机构参数值 用来检测单条记录  */
	private String orgTwoParamValue;
	/**第三个机构设置的数据权限设置(和第一个机构关系用or)  机构参数值 用来检测单条记录  */
	private String orgThParamValue;
	/**部门数据权限(业务部门,货权部门)*/
	private String deptParam;
	/**部门数据参数值 用来检测单条记录 */
	private String deptParamValue;
	/**仓库设置的数据权限设置  仓库参数名  */
	private String warhouseParam;
	/**仓库数据参数值 用来检测单条记录 */
	private String warhouseParamValue;
	/**货齐使用*/
	private String completeCodeStrs;
	private String completeBatchStrs;
	private String completeTypeCodeStrs;
	/** 右键过滤sql语句 */
	private String menuQueryHql;
	
	public String[] getLikeCol() {
		return likeCol;
	}

	public void setLikeCol(String[] likeCol) {
		this.likeCol = likeCol;
	}

	public String getVisibleColumns() {
		return visibleColumns;
	}

	public void setVisibleColumns(String visibleColumns) {
		this.visibleColumns = visibleColumns;
	}
	
	public SuperBean(){
		super();
	}
	
	public SuperBean(UserSession user) {
		super();
		this.user = user;
	}

//	@JSON(serialize=false)
	public UserSession getUser() {
		return user;
	}
	
	public void setUser(UserSession user) {
		this.user = user;
	}
	public String getWarhouseParam() {
		return warhouseParam;
	}

	public void setWarhouseParam(String warhouseParam) {
		this.warhouseParam = warhouseParam;
	}
	
	public String getOrgParam() {
		return orgParam;
	}

	public void setOrgParam(String orgParam) {
		this.orgParam = orgParam;
	}

	public String getDeptParam() {
		return deptParam;
	}

	public void setDeptParam(String deptParam) {
		this.deptParam = deptParam;
	}
	
	public String getSort() {
		return sort;
	}
	
	public String getOrgParamValue() {
		return orgParamValue;
	}

	public void setOrgParamValue(String orgParamValue) {
		this.orgParamValue = orgParamValue;
	}

	public String getDeptParamValue() {
		return deptParamValue;
	}

	public void setDeptParamValue(String deptParamValue) {
		this.deptParamValue = deptParamValue;
	}

	public String getWarhouseParamValue() {
		return warhouseParamValue;
	}

	public void setWarhouseParamValue(String warhouseParamValue) {
		this.warhouseParamValue = warhouseParamValue;
	}
	
	/**读数据权限的hql语句*/
	public String makeOpDataPerReadHqlStr() {
		String opDataPerReadHqlStr="";
		if(null!=user){
			//TODO
			opDataPerReadHqlStr=makeHql(opDataPerReadHqlStr, makeOrgReadHqlStr());
			opDataPerReadHqlStr=makeHql(opDataPerReadHqlStr, makeDeptReadHqlStr());
			opDataPerReadHqlStr=makeHql(opDataPerReadHqlStr, makeWarhouseReadHqlStr());//仓库
			if(StringUtil.isNotBlank(opDataPerReadHqlStr)){
				opDataPerReadHqlStr=" ( " + opDataPerReadHqlStr+" ) ";
			}
		}else{
			opDataPerReadHqlStr = " (1=2) ";
		}
		return opDataPerReadHqlStr;
	}
	
	public String makeMultipleOrgPerReadHqlStr() {
		String opDataPerReadHqlStr="";
		if(null!=user){
			String returnStr="";
			if(StringUtil.isNotBlank(orgParam) && null!=user.getOpDataMap()){
				String dataperCode=SuperConstants.DATAPER_CODE_ORG, deual=user.getOrgCode();
				
				if( StringUtil.isNotBlank(user.getOpDataMap().get(dataperCode))){//是否启用
					Map<String,String> opDateReadMap=user.getOpDataReadMap();
					if(null!=opDateReadMap){
						returnStr=opDateReadMap.get(dataperCode);//str=('1','2')
						if(StringUtil.isBlank(returnStr)){
							returnStr=" 1=2 ";
						}else{
							returnStr=makeeMultipleWhereHql(deual, returnStr, orgParam);
						}
					}
				}
			}
			opDataPerReadHqlStr=makeHql(opDataPerReadHqlStr, returnStr);
		}else{
			opDataPerReadHqlStr = " (1=2) ";
		}
		return opDataPerReadHqlStr;
	}
	
	private String makeHql(String str1,String st2){
		if(StringUtil.isNotBlank(st2)){
			if(StringUtil.isNotBlank(str1)){
				str1+=" and ";
			}
			str1+=st2;
		}
		return str1;
	}
	public String makeOrgReadHqlStr(){
		String returnStr="";
		if(StringUtil.isNotBlank(orgParam)||StringUtil.isNotBlank(orgTwoParam)||StringUtil.isNotBlank(orgThParam)){
			returnStr=makeReadValueStr(SuperConstants.DATAPER_CODE_ORG,user.getOrgCode(),orgParam,orgTwoParam,orgThParam);
		}
		return returnStr;
	}
	
	public String makeDeptReadHqlStr(){
		String returnStr="";
		if(StringUtil.isNotBlank(deptParam)){
			returnStr=makeReadValueStr(SuperConstants.DATAPER_CODE_DEPT,user.getDeptCode(),deptParam);
		}
		return returnStr;
	}
	public String makeWarhouseReadHqlStr(){
		String returnStr="";
		if(StringUtil.isNotBlank(warhouseParam)){
			returnStr=makeReadValueStr(SuperConstants.DATAPER_CODE_WAREHOUSE,"",warhouseParam);
		}
		return returnStr;
	}
	private String makeReadValueStr(String dataperCode ,String deual,String paramsName,String... paramsNameTwo){
		String str="";
		if(null!=user&&StringUtil.isNotBlank(dataperCode)){
			if(null!=user.getOpDataMap() && StringUtil.isNotBlank(user.getOpDataMap().get(dataperCode))){//是否启用
				Map<String,String> opDateReadMap=user.getOpDataReadMap();
				if(null!=opDateReadMap){
					str=opDateReadMap.get(dataperCode);//str=('1','2')
					if(StringUtil.isBlank(str)){
						str=" 1=2 ";
					}else{
						str=makeWhereHql(deual, str,paramsName, paramsNameTwo);
					}
				}
			}
		}
		return str;
	}
	private String makeWhereHql(String deual,String str,String paramsName,String... paramsNameTwo ){
		String hql="";
		if(null!=str){//为空表示没有启用这个类别
			if("".equals(str)){
				if(StringUtil.isNotBlank(deual)){
					if(StringUtil.isNotBlank(paramsName)){
						hql=paramsName+" = '"+deual+"'";
					}
					if(null!=paramsNameTwo){
						for (int i = 0; i < paramsNameTwo.length; i++) {
							if (null != paramsNameTwo[i]) {
								if(StringUtil.isNotBlank(hql)){
									hql+=" or ";
								}
								hql+=paramsNameTwo[i]+" = '"+deual+"'";
							}
						}
					}
					hql=" ( "+hql+" ) ";
				}else{
					hql=" 1=2 ";
				}
			}else{
				if(StringUtil.isNotBlank(paramsName)){
					hql=paramsName+" in ("+str+")";
				}
				if(null!=paramsNameTwo){
					for (int i = 0; i < paramsNameTwo.length; i++) {
						if (null != paramsNameTwo[i]) {
							if(StringUtil.isNotBlank(hql)){
								hql+=" or ";
							}
							hql+=paramsNameTwo[i]+" in ("+str+")";
						}
					}
				}
				hql=" ( "+hql+" ) ";
			}
		}
		return hql;
	}
	private String makeeMultipleWhereHql(String deual,String str,String paramsName){
		String hql="";
		if(null!=str && StringUtil.isNotBlank(paramsName)){//为空表示没有启用这个类别
			if("".equals(str)){
				if(StringUtil.isNotBlank(deual)){
					hql=" ("+paramsName+" = '"+deual+"') ";
				}else{
					hql=" 1=2 ";
				}
			}else{
				String[] strS=str.split(",");
				for(int i=0;i<strS.length;i++){
					String v=strS[i];
					if(StringUtil.isNotBlank(v)){
						if(StringUtil.isNotBlank(hql)){
							hql+=" or ";
						}
						v=v.replaceAll("'", "");
						hql+="instr(','||"+paramsName+"||',' ,',"+v+",')>0";
					}
				}
				hql=" ( "+hql+" ) ";
			}
		}
		return hql;
	}
	/**
	 * 检测数据权限
	 * @param write true检测写的权限 false检测读的权限
	 * @return
	 */
	public boolean checkOpDataPermission(boolean write){
		boolean flag=true;
		if(null==user){
			return false;
		}
		//TODO
		if(write){
			flag = checkWrite();
		}else{
			flag = checkReadOnly();
		}
		return flag;
	}
	
	/**
	 * 检测单条记录是否有读的权限,把单条里的相关信息写到对应的参数值里和用户信息,就可以判断
	 * @return true 有 false 无
	 */
	public boolean checkReadOnly(){
		boolean flag=true;
		if(null==user){
			return false;
		}
		if(StringUtil.isNotBlank(orgParamValue)||StringUtil.isNotBlank(orgTwoParamValue)||StringUtil.isNotBlank(orgThParamValue)){//机构
			String str=makeReadValueStr(SuperConstants.DATAPER_CODE_ORG,"","",user.getOrgCode());
			if(StringUtil.isNotBlank(str)){
				flag=checkString(orgParamValue,str);
				if(!flag){
					flag=checkString(orgTwoParamValue,str);
					if(!flag){
						flag=checkString(orgThParamValue,str);
					}
				}
			}
		}
		if(flag){
			if(StringUtil.isNotBlank(deptParamValue)){//部门
				String str=makeReadValueStr(SuperConstants.DATAPER_CODE_DEPT,"","",user.getDeptCode());
				if(StringUtil.isNotBlank(str)){
					flag=checkString(deptParamValue,str);
				}
			}
		}
		if(flag){
			if(StringUtil.isNotBlank(warhouseParamValue)){//仓库
				String str=makeReadValueStr(SuperConstants.DATAPER_CODE_WAREHOUSE,"","","");
				if(StringUtil.isNotBlank(str)){
					flag=checkString(warhouseParamValue,str);
				}
			}
		}
		return flag;
	}
	/**
	 * 检测单条记录是否有写的权限,把单条里的相关信息写到对应的参数值里和用户信息,就可以判断
	 * @return true 有 false 无
	 */
	public boolean checkWrite(){
		boolean flag=true;
		if(null==user){
			return false;
		}
		if(StringUtil.isNotBlank(orgParamValue)){//机构
			String str=makeWriteValueStr(SuperConstants.DATAPER_CODE_ORG,"","",user.getOrgCode());
			if(StringUtil.isNotBlank(str)){
				flag=checkString(orgParamValue,str);
			}
		}
		if(flag){
			if(StringUtil.isNotBlank(deptParamValue)){//部门
				String str=makeWriteValueStr(SuperConstants.DATAPER_CODE_DEPT,"","",user.getDeptCode());
				if(StringUtil.isNotBlank(str)){
					flag=checkString(deptParamValue,str);
				}
			}
		}
		if(flag){
			if(StringUtil.isNotBlank(warhouseParamValue)){//仓库
				String str=makeWriteValueStr(SuperConstants.DATAPER_CODE_WAREHOUSE,"","","");
				if(StringUtil.isNotBlank(str)){
					flag=checkString(warhouseParamValue,str);
				}
			}
		}
		return flag;
	}
	
	private String makeWriteValueStr(String dataperCode,String deual,String paramsName,String... paramsNameTwo){
		String str="";
		if(null!=user&&StringUtil.isNotBlank(dataperCode)){
			if(null!=user.getOpDataMap() && StringUtil.isNotBlank(user.getOpDataMap().get(dataperCode))){//是否启用
				Map<String,String> opDateWriteMap=user.getOpDataWriteMap();
				if(null!=opDateWriteMap){
					str=opDateWriteMap.get(dataperCode);
					if(StringUtil.isBlank(str)){
						str=" 1=2 ";
					}else{
						str=makeWhereHql( deual, str,paramsName, paramsNameTwo);
					}
				}
			}
		}
		return str;
	}
	
	private boolean checkString(String paramValue,String str){
		boolean flag=true;
		if(StringUtil.isNotBlank(str)){
			if(str.indexOf("'"+paramValue+"'")<0){
				flag=false;
			}
		}
		return flag;
	}
	
	/**
	 * 获取当前查询条件的模糊匹配列
	 * @return map<key=column,value=column>
	 */
	public Map<String,String> queryLikeCol(){
		Map<String,String> map = new HashMap<String, String>();
		if(null!=likeCol&&likeCol.length>0){
			for (int i = 0; i < likeCol.length; i++) {
				if(StringUtil.isNotBlank(likeCol[i])){
					map.put(likeCol[i], likeCol[i]);
				}
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
//	public void setSort(String sort) {
//		if(StringUtil.isNotBlank(sort)){
//			List<SortData> sortList = (List<SortData>) JsonUtil.jsonToList(sort, SortData.class);
//			String str="";
//			if(null!=sortList&&!sortList.isEmpty()){
//				int i=1;
//				for(SortData mod : sortList){
//					if(i>1){
//						str+=",";
//					}
//					str+=mod.getProperty()+" "+mod.getDirection().toLowerCase();
//					i++;
//				}
//			}
//			sort=str;
//		}
//		this.sort = sort;
//	}
	
	public void setFieldValue(Object obj, String[] names, String[] values, String[] likeCols){
		if(null!=names){
			List<String> newLikes = new ArrayList<String>();
			for(int i=0;i<names.length;i++){
				if(StringUtil.isNotBlank(names[i])&&StringUtil.isNotBlank(values[i])){
					BeanUtil.forceSetProperty(obj, names[i], values[i]);
					if("1".equals(likeCols[i]) && BeanUtil.getObjType(obj, names[i]).indexOf("String")!=-1){//只有string类型的可以模糊
						newLikes.add(names[i]);
					}
				}
			}
			if(!newLikes.isEmpty()){
				setLikeCol((String[]) ArrayUtils.addAll(null==getLikeCol()?new String[1]:getLikeCol(), newLikes.toArray()));
			}
		}
	}
	
	/**
	 * 动态获取分组字段
	 * @param isGroup true:查询列 false:分组列
	 * @return
	 */
	public String getGroupbyColumns(boolean isGroup){
		String groupColumns="memberCode";//分组字段
		if(StringUtil.isNotBlank(visibleColumns)){
			groupColumns+=","+visibleColumns;
		}
		if(isGroup){
			String hql="";
			String[] values = groupColumns.split(",");
			for(String value: values){
				if(StringUtil.isNotBlank(hql)){
					hql+=",";
				}
				hql +=value+" as "+value;
			}
			groupColumns=hql;
		}
		return groupColumns;
	}

	/**动态获取分组字段
	 * @param map 特殊分组字段
	 * @param isGroup true:查询列 false:分组列
	 * @return
	 */
	public String getOtherGroupbyColumns(Map<String,String> map,boolean isGroup){
		String groupColumns="memberCode";//分组字段
		String otherHql="";
		if(StringUtil.isNotBlank(getVisibleColumns())){
			groupColumns+=","+getVisibleColumns();
		}
		if(null!=map&&!map.isEmpty()){
			for (String key : map.keySet()) {
				if(StringUtil.isNotBlank(otherHql)){
					otherHql+=",";
				}
				if(isGroup){
					otherHql +=map.get(key)+" as "+key;
				}else{
					otherHql +=map.get(key);
				}
			}
		}
		if(isGroup){
			String hql="";
			String[] values = groupColumns.split(",");
			for(String value: values){
				if(StringUtil.isNotBlank(hql)){
					hql+=",";
				}
				hql +=value+" as "+value;
			}
			groupColumns=hql;
		}
		if(StringUtil.isNotBlank(otherHql)){
			groupColumns+=","+otherHql;
		}
		return groupColumns;
	}

	public String getOrgThParam() {
		return orgThParam;
	}

	public void setOrgThParam(String orgThParam) {
		this.orgThParam = orgThParam;
	}

	public String getOrgThParamValue() {
		return orgThParamValue;
	}

	public void setOrgThParamValue(String orgThParamValue) {
		this.orgThParamValue = orgThParamValue;
	}

	public String getOrgTwoParam() {
		return orgTwoParam;
	}

	public void setOrgTwoParam(String orgTwoParam) {
		this.orgTwoParam = orgTwoParam;
	}

	public String getOrgTwoParamValue() {
		return orgTwoParamValue;
	}

	public void setOrgTwoParamValue(String orgTwoParamValue) {
		this.orgTwoParamValue = orgTwoParamValue;
	}

	public String getCompleteBatchStrs() {
		return completeBatchStrs;
	}

	public void setCompleteBatchStrs(String completeBatchStrs) {
		this.completeBatchStrs = completeBatchStrs;
	}

	public String getCompleteCodeStrs() {
		return completeCodeStrs;
	}

	public void setCompleteCodeStrs(String completeCodeStrs) {
		this.completeCodeStrs = completeCodeStrs;
	}

	public String getCompleteTypeCodeStrs() {
		return completeTypeCodeStrs;
	}

	public void setCompleteTypeCodeStrs(String completeTypeCodeStrs) {
		this.completeTypeCodeStrs = completeTypeCodeStrs;
	}

	public String getMenuQueryHql() {
		return menuQueryHql;
	}

	public void setMenuQueryHql(String menuQueryHql) {
		this.menuQueryHql = menuQueryHql;
	}
}
