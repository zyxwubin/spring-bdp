package org.bdp.basic.vo;

import org.bdp.basic.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

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

	private Integer page;

	private Integer pageSize;
	
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

	private String makeHql(String str1,String st2){
		if(StringUtil.isNotBlank(st2)){
			if(StringUtil.isNotBlank(str1)){
				str1+=" and ";
			}
			str1+=st2;
		}
		return str1;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
