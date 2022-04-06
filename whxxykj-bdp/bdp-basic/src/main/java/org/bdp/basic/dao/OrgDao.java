package org.bdp.basic.dao;

import java.util.List;

import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.vo.ComboBean;
import org.bdp.basic.vo.QueryBean;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.dao.GenericDao;
import org.bdp.glodal.util.PageTools;

/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-5-8
 * Create Author: gp
 * File Name: 机构设置dao
 * Last version:  1.0
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public interface OrgDao extends GenericDao<BasicOrg, Long>{
	/**
	 * 查询信息
	 * @param bean 查询条件bean
	 * @param page 分页信息
	 * @return
	 */
	public List<BasicOrg> query(QueryBean bean, PageTools page);
	/**
	 * 查询信息
	 * @param user 用户信息
	 * @param query 过滤机构代码或机构名称
	 * @param orgCode 公司组织代码(机构代码)
	 * @return
	 */
	public List<ComboBean> queryCombo(UserSession user, String query,QueryBean queryBean);
	/**
	 * 查询二级机构列表
	 * @param user 用户信息
	 * @param query 过滤机构代码或机构名称
	 * @param orgCode 公司组织代码(机构代码)
	 * @return
	 */
	public List<ComboBean> querySuperiorCombo(UserSession user, String query,String orgCode);

	/**
	 * 查询客商页面内部机构信息
	 * @param user 用户信息
	 * @param query 过滤机构代码或机构名称
	 * @param orgCode 公司组织代码(机构代码)
	 * @return
	 */
	public List<ComboBean> queryInnerOrgCombo(UserSession user, String query,String orgCode);

	/**
	 * 删除记录
	 * @param orgCode 机构代码
	 * @param memberCode 使用单位
	 * @return
	 */
	public int delete(String orgCode,String memberCode);

	/**
	 * 通过机构代码查询机构记录
	 * @param orgCode 机构代码
	 * @return
	 */
	public BasicOrg getByCode(String orgCode);
	
	/**
	 * 通过机构名称,使用单位查询机构记录
	 * @param orgName 机构名称
	 * @param memberCode 使用单位
	 * @return
	 */
	public BasicOrg getByName(String orgName,String memberCode);
	
	/**
	 * 通过机构简称,使用单位查询机构记录
	 * @param orgAbbreviate 机构简称
	 * @param memberCode 使用单位
	 * @return
	 */
	public BasicOrg getByAbbreviate(String orgAbbreviate,String memberCode);

	/**
	 * 通过机构简称,使用单位查询机构记录
	 * @param orgAbbreviate 机构简称
	 * @param memberCode 使用单位
	 * @return
	 */
	public BasicOrg getByAbbreviateAndLeaf(String orgAbbreviate,String memberCode,Long orgIsleaf);
	
	/**
	 * 获取当前表里orgCode最大的值
	 * @return 最大的值
	 */
	public String getMaxOrgCode();
	
	/**
	 * 通过父结点代码,机构属性查询记录数
	 * @param orgParent 父结点代码
	 * @param orgIsleaf 机构属性(0：机构组,1：机构明细,null:所有)
	 * @param memberCode 使用单位
	 * @return
	 */
	public int getCountByParentCode(String orgParent,Long orgIsleaf,String memberCode);
	
	/**
	 * 通过父结点代码,机构属性查询数据
	 * @param orgParent 父结点代码
	 * @param orgIsleaf 机构属性(0：机构组,1：机构明细,null:所有)
	 * @param memberCode 使用单位
	 * @return
	 */
	public List<BasicOrg> queryByParent(String orgParent,Long orgIsleaf,String memberCode);
	
	/**
	 * 通过父结点代码,机构属性、使用单位删除机构组
	 * @param orgParent 父结点代码
	 * @param orgIsleaf 机构属性(0：机构组,1：机构明细,null:所有)
	 * @param memberCode 使用单位
	 * @return
	 */
	public int deleteOrgByParent(String orgParent,Long orgIsleaf,String memberCode);
	/**
	 * 更新节点的父节点代码,公司组织编号
	 * @param orgCode 公司组织代码
	 * @param orgNodecode 公司组织编码
	 * @param orgParent 父节点代码
	 * @param memberCode 使用单位
	 * @return
	 */
	public int updateOrgParentCode(String orgCode,String orgNodecode,String orgParent,String memberCode);	
	/**
	 * 更新所有下级节点的公司组织编号
	 * @param oldOrgNodecode 原公司组织编号
	 * @param newOrgNodecode 新公司组织编号
	 * @param memberCode 使用单位
	 * @return
	 */
	public int updateOrgNodecode(String oldOrgNodecode,String newOrgNodecode,String memberCode);
	
	/**获取机构
	 * @param companyCode 货主代码
	 * @param memberCode 会员代码
	 * @return BasicOrg
	 */
	public BasicOrg getOrg(String companyCode,String memberCode);

	/**
	 * 根据共享代码
	 * @param shareCode
	 * @param memberCode
	 * @return
	 */
	public BasicOrg getOrgByShare(String shareCode, String memberCode);
	/**
	 * 根据现货收款银行账号查询机构
	 * @param accNo
	 * @return
	 */
	public List<BasicOrg> queryOrg(String accNo);

}