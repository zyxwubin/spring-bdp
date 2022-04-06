package org.bdp.basic.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.bdp.basic.dao.OrgDao;
import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.vo.ComboBean;
import org.bdp.basic.vo.QueryBean;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.common.superclass.SuperBean;
import org.bdp.glodal.dao.HibernateGenericDao;
import org.bdp.glodal.util.HqlUtil;
import org.bdp.glodal.util.PageTools;
import org.bdp.glodal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrgDaoImpl extends HibernateGenericDao<BasicOrg, Long> implements OrgDao {

	public OrgDaoImpl(EntityManager em) {
		super(BasicOrg.class, em);
	}

	public int delete(String orgCode, String memberCode) {
		int i=executeUpdate("delete from BasicOrg a where a.orgCode=? and a.memberCode=?",orgCode,memberCode);
		if(i>0){
			i=1;
		}else{
			i=-1;
		}
		return i;
	}
	
	public BasicOrg getByCode(String orgCode) {
		Object obj = uniqueResult("from BasicOrg a where a.orgCode=?", orgCode);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}
	
	public BasicOrg getByName(String orgName,String memberCode){
		Object obj = uniqueResult("from BasicOrg a where a.orgName=? and a.memberCode=?", orgName,memberCode);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}
	
	public BasicOrg getByAbbreviate(String orgAbbreviate,String memberCode){
		Object obj = uniqueResult("from BasicOrg a where a.orgAbbreviate=? and a.memberCode=?", orgAbbreviate,memberCode);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}


	public BasicOrg getByAbbreviateAndLeaf(String orgAbbreviate,String memberCode,Long orgIsleaf){
		Object obj = uniqueResult("from BasicOrg a where a.orgAbbreviate=? and a.memberCode=? and a.orgIsleaf=?", orgAbbreviate,memberCode,orgIsleaf);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}

	@SuppressWarnings("unchecked")
	public List<ComboBean> queryCombo(UserSession user, String query,QueryBean queryBean) {
		String hql="select a.orgCode as orgCode,a.orgName as orgName,a.orgAbbreviate as orgAbbreviate,a.orgSignature as orgSignature,a.orgSignatureAbroad as orgSignatureAbroad "
				+ " from BasicOrg a where a.memberCode=? and orgIsleaf=?";
		List<Object> param = new ArrayList<Object>();
		param.add(user.getMemberCode());
		param.add(1L);//机构明细
		if(StringUtil.isNotBlank(queryBean.getOrgCode())){
			hql+=" and orgCode=?";
			param.add(queryBean.getOrgCode());	
		}
		if(StringUtil.isNotBlank(query)){
			hql+=" and (a.orgCode like ? or a.orgName like ? or a.orgAbbreviate like ?)";
			param.add("%"+query+"%");
			param.add("%"+query+"%");
			param.add("%"+query+"%");
		}
		//启用指标统计（0：不启用 1：启用）
		if(null!=queryBean.getOrgSignature()){
			hql+=" and "+HqlUtil.emptyToHql("a.orgSignature", "0")+"=?";
			param.add(queryBean.getOrgSignature());	
		}
		//启用境外指标统计（0：不启用 1：启用）
		if(null!=queryBean.getOrgSignatureAbroad()){
			hql+=" and "+HqlUtil.emptyToHql("a.orgSignatureAbroad", "0")+"=?";
			param.add(queryBean.getOrgSignatureAbroad());	
		}
		/*数据权限*/
		SuperBean superBean = new SuperBean();
		superBean.setUser(user);
		superBean.setOrgParam("a.orgCode");
		String str = superBean.makeOpDataPerReadHqlStr();
		if(StringUtil.isNotBlank(str)){
			hql+=" and "+str;
		}
		hql+=" order by a.orgName asc,a.orgCode asc";
		return list(hql, param.toArray());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ComboBean> querySuperiorCombo(UserSession user, String query, String orgCode){
		StringBuilder hql=new StringBuilder("select a.orgCode as orgCode,a.orgName as orgName,a.orgAbbreviate as orgAbbreviate from BasicOrg a where a.memberCode=? and orgIsleaf=? and a.orgCode <>?");
		List<Object> param = new ArrayList<Object>();
		param.add(user.getMemberCode());
		param.add(0L);//机构组
		param.add("0000");//排除第一级集团
		if(StringUtil.isNotBlank(orgCode)){
			hql.append(" and a.orgCode=?");
			param.add(orgCode);
		}
		if(StringUtil.isNotBlank(query)){
			hql.append(" and (a.orgCode like ? or a.orgName like ? or a.orgAbbreviate like ?)");
			param.add("%"+query+"%");
			param.add("%"+query+"%");
			param.add("%"+query+"%");
		}
		/*数据权限*/
		SuperBean superBean = new SuperBean();
		superBean.setUser(user);
		superBean.setOrgParam("b.orgCode");
		String str = superBean.makeOpDataPerReadHqlStr();
		//只查询拥有数据权限的机构
		hql.append(" and exists (select 1 from BasicOrg b where a.orgCode = b.orgParent and ").append(str).append(")");
		hql.append(" order by a.orgName asc,a.orgCode asc");
		List<ComboBean> list = list(hql.toString(), param.toArray());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComboBean> queryInnerOrgCombo(UserSession user, String query, String orgCode) {
		String hql="select a.orgCode as orgCode,a.orgName as orgName,a.orgAbbreviate as orgAbbreviate from BasicOrg a where a.memberCode=? and orgIsleaf=?";
		List<Object> param = new ArrayList<Object>();
		param.add(user.getMemberCode());
		param.add(1L);//机构明细
		if(StringUtil.isNotBlank(orgCode)){
			hql+=" and orgCode=?";
			param.add(orgCode);
		}
		if(StringUtil.isNotBlank(query)){
			hql+=" and (a.orgCode like ? or a.orgName like ? or a.orgAbbreviate like ?)";
			param.add("%"+query+"%");
			param.add("%"+query+"%");
			param.add("%"+query+"%");
		}
		hql+=" order by a.orgName asc,a.orgCode asc";
		List<ComboBean> list = list(hql, param.toArray());
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BasicOrg> query(QueryBean bean, PageTools page) {
		String hql="from BasicOrg a where a.memberCode=?";
		List<Object> params = new ArrayList<Object>();
		params.add(bean.getMemberCode());
		if(StringUtil.isNotBlank(bean.getOrgNodecode())){
			hql+=" and orgNodecode like ?";
			params.add(bean.getOrgNodecode()+".%");
		}else{
		    if(null!=bean.getOrgParent()) {
                hql += " and orgParent=?";
                params.add(bean.getOrgParent());
            }
            Map<String,String> notMap = new HashMap<String, String>();
            notMap.put("memberCode","memberCode");
            String queryHql = HqlUtil.getModleAllToWhereHql("a", bean, null, notMap, params, false, null, bean.queryLikeCol());
            if (StringUtil.isNotBlank(queryHql)) {
                hql+=" and "+queryHql;
            }
		}
		if(null!=bean.getOrgIsleaf()){
			hql+=" and "+HqlUtil.emptyToHql("a.orgIsleaf", "0")+"="+bean.getOrgIsleaf()+"";
		}
		if(StringUtil.isNotBlank(bean.getSort())){
			hql+=" order by "+bean.getSort()+",orgId asc";
		}
		List objList = queryForPageByHql(hql, page, params.toArray());
		List<BasicOrg> list = new ArrayList<BasicOrg>();
		if(null!=objList&&!objList.isEmpty()){
			list=(List<BasicOrg>)objList;
		}
		return list;
	}

	public String getMaxOrgCode(){
		return getMaxCode("orgCode", "", 4);
	}

	public int getCountByParentCode(String orgParent,Long orgIsleaf,String memberCode) {
		String hql="from BasicOrg a where a.orgParent=? and a.memberCode=?";
		if(null!=orgIsleaf){
			hql+=" and "+HqlUtil.emptyToHql("a.orgIsleaf", "0")+"="+orgIsleaf+"";
		}
		return getResultCount(hql, orgParent,memberCode);
	}

	public int deleteOrgByParent(String orgParent, Long orgIsleaf,String memberCode) {
		int i=executeUpdate("delete from BasicOrg a where a.orgParent=? and orgIsleaf=? and a.memberCode=?",orgParent,orgIsleaf,memberCode);
		if(i>0){
			i=1;
		}else{
			i=-1;
		}
		return i;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BasicOrg> queryByParent(String orgParent, Long orgIsleaf,String memberCode) {
		String hql="from BasicOrg a where a.orgParent=? and a.memberCode=?";
		if(null!=orgIsleaf){
			hql+=" and "+HqlUtil.emptyToHql("a.orgIsleaf", "0")+"="+orgIsleaf+"";
		}
		hql+=" order by orgName asc";
		List objList= list(hql,orgParent,memberCode);
		List<BasicOrg> list =null;
		if(null!=objList&&!objList.isEmpty()){
			list= (List<BasicOrg>)objList;
		}
		return list;
	}

	public int updateOrgParentCode(String orgCode,String orgNodecode, String orgParent,String memberCode) {
		int i=executeUpdate("update BasicOrg a set a.orgNodecode=?,a.orgParent=? where a.orgCode=? and a.memberCode=?",orgNodecode,orgParent,orgCode,memberCode);
		if(i>0){
			i=1;
		}else{
			i=-1;
		}
		return i;
	}

	public int updateOrgNodecode(String oldOrgNodecode,String newOrgNodecode, String memberCode) {
		int i=executeUpdate("update BasicOrg a set a.orgNodecode=?||substr(a.orgNodecode,length(?)+1) where a.orgNodecode like ? and a.memberCode=?",newOrgNodecode,oldOrgNodecode,oldOrgNodecode+".%",memberCode);
		if(i>0){
			i=1;
		}else{
			i=-1;
		}
		return i;
	}

	public BasicOrg getOrg(String companyCode, String memberCode) {
		Object obj = uniqueResult("from BasicOrg a where a.companyCode=? and a.memberCode=?", companyCode,memberCode);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}

	@Override
	public BasicOrg getOrgByShare(String shareCode, String memberCode){
		Object obj = uniqueResult("from BasicOrg a where a.shareOrgcode=? and a.memberCode=?", shareCode,memberCode);
		BasicOrg mod = null;
		if(null!=obj){
			mod= (BasicOrg)obj;
		}
		return mod;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BasicOrg> queryOrg(String accNo) {
		return list(" from BasicOrg a where a.accNo=?", accNo);
	}
}
