package org.bdp.basic.service;

import java.util.List;

import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.entity.BasicOrgJh;
import org.bdp.basic.vo.ComboBean;
import org.bdp.basic.vo.QueryBean;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.util.PageTools;

/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-5-8
 * Create Author: gp
 * File Name: 机构设置服务
 * Last version:  1.0
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public interface OrgService {
	/**
	 * 查询公司组织树
	 * @param orgParent 父节点代码
	 * @param memberCode 使用单位
	 * @return
	 */
//	public List<TreeBean> queryTree(String orgParent,Long orgIsleaf,String memberCode);
	/**
	 * 更新节点父节点代码
	 * @param orgCode 公司组织代码
	 * @param oldOrgNodecode 公司组织编号
	 * @param orgParent 父节点代码
	 * @param memberCode 使用单位
	 * @return
	 */
	public int updateTreeParent(String orgCode,String oldOrgNodecode,String orgParent,String memberCode);
	/**
	 * 通过公司组织代码删除公司组织(包含下级公司组织,不包括末级机构)
	 * @param orgCode 公司组织代码
	 * @param memberCode 使用单位
	 * @return
	 */
	public int deleteTree(String orgCode,String memberCode);
	/**
	 * 通过公司组织机构代码,使用单位查询角色记录
	 * @param orgCode  公司组织代码(机构代码)
	 * @param memberCode 使用单位 
	 * @return
	 */
	public BasicOrg getOrgByCode(String orgCode,String memberCode);

	/**
	 * 通过公司组织机构代码获取收条比例
	 * @param orgCode  公司组织代码(机构代码)
	 * @param memberCode 使用单位
	 * @return
	 */
//	public OrgSignBean getOrgSignByCode(String orgCode, String memberCode);
	/**
	 * 查询信息
	 * @param orgAbbreviate 机构简称
	 * @param memberCode 使用单位代码
	 * @return List<BasicOrg>
	 */
	public BasicOrg getOrgByAbbreviateName(String orgAbbreviate,String memberCode);

	/**
	 * 查询信息
	 * @param orgAbbreviate 机构简称
	 * @param memberCode 使用单位代码
	 * @param orgIsleaf 0 机构组  1 机构组底下的子机构
	 * @return
	 */
	public BasicOrg getOrgByAbbreviateNameAndLeaf(String orgAbbreviate,String memberCode,Long orgIsleaf);

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
	 * 查询机构树
	 * @param user 用户信息
	 * @param query 过滤机构代码或机构名称
	 * @param orgCode 公司组织代码(机构代码)
	 * @return
	 */
//	public List<TreeBean> queryCombotree(UserSession user, String query,String orgCode);
	/**
	 * 新增记录
	 * @param mod
	 * @return 1 成功 -1 失败
	 */
	public int save(BasicOrg mod);
	/**
	 * 更新记录
	 * @param mod
	 * @return 1 成功 -1 失败
	 */
	public int update(BasicOrg mod);
	/**
	 * 删除记录
	 * @param orgCode 机构代码
	 * @param memberCode 使用单位
	 * @return
	 */
	public int delete(String orgCode,String memberCode);

	/**
	 * 查询所有信息
	 * 
	 * @return List<BasicOrg>
	 */
	public List<BasicOrg> queryAll();

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
	/**
	 * 查询机构收款码
	 * @param orgCode
	 * @param memberCode
	 * @return
	 */
	public List<BasicOrgJh> queryOrgJh(String orgCode,String memberCode);
	/**
	 * 修改机构交行收款码
	 * @param orgJhList
	 * @param memberCode
	 * @return
	 */
	public int updateOrgJh(List<BasicOrgJh> orgJhList,String memberCode);
	/**
	 * 交行开收款识别码/注销收款识别码
	 * @param orgCode
	 * @param session
	 * @return
	 */
	public int updateIdIdNo(String idIdNo,String idNoNme,String orgCode,String updateType,UserSession session);
	/**
	 * 查询存在开通收款识别码的机构
	 * @param accNo
	 * @return
	 */
	public List<BasicOrgJh> queryOrgByIdIdNo();
	/**
	 * 根据收款识别码获取机构
	 * @param idIdNo
	 * @return
	 */
	public BasicOrgJh getOrgIdIdNo(String idIdNo);
	/**
	 * 获取机构收款码
	 * @param orgCode
	 * @param idIdNo
	 * @param memberCode
	 * @return
	 */
	public BasicOrgJh getOrgJh(String orgCode,String idIdNo,String memberCode);
	
}
