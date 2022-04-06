package org.bdp.basic.dao;

import java.util.List;

import org.bdp.basic.entity.BasicOrgJh;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.dao.GenericDao;

/************************************************
 * Create Date: 2021年12月7日
 * Create Author: wangcheng
 * Function: 机构加航收款码
**************************************************/
public interface OrgJhDao extends GenericDao<BasicOrgJh, Long>{
	/**
	 * 查询机构收款码
	 * @param orgCode
	 * @param memberCode
	 * @return
	 */
	public List<BasicOrgJh> queryOrgJh(String orgCode,String memberCode);
	/**
	 * 获取机构收款码
	 * @param orgCode
	 * @param idIdNo
	 * @param memberCode
	 * @return
	 */
	public BasicOrgJh getOrgJh(String orgCode,String idIdNo,String memberCode);
	/**
	 * 注销收款识状态
	 * @param orgCode
	 * @param idIdNo
	 * @param memberCode
	 * @return
	 */
	public int updateCancelOrgJh(String orgCode,String idIdNo,UserSession session);
	/**
	 * 删除机构
	 * @param orgCode
	 * @param memberCode
	 * @return
	 */
	public int deleteAll(String orgCode,String memberCode);
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
	
}
