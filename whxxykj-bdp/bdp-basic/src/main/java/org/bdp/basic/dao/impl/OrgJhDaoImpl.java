package org.bdp.basic.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.bdp.basic.dao.OrgJhDao;
import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.entity.BasicOrgJh;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.dao.HibernateGenericDao;
import org.bdp.glodal.util.HqlUtil;
import org.springframework.stereotype.Repository;

@Repository
public class OrgJhDaoImpl extends HibernateGenericDao<BasicOrgJh, Long> implements OrgJhDao {

	public OrgJhDaoImpl(EntityManager em) {
		super(BasicOrgJh.class, em);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BasicOrgJh> queryOrgJh(String orgCode, String memberCode) {
		return list(" from BasicOrgJh a where a.orgCode=? and a.memberCode=? order by a.orgJhId ", orgCode,memberCode);
	}

	@Override
	public BasicOrgJh getOrgJh(String orgCode, String idIdNo, String memberCode) {
		Object obj = uniqueResult(" from BasicOrgJh a where a.orgCode=? and a.idIdNo=? and a.memberCode=?", orgCode,idIdNo,memberCode);
		BasicOrgJh mod = null;
		if(null != obj){
			mod = (BasicOrgJh)obj;
		}
		return mod;
	}
	
	@Override
	public int updateCancelOrgJh(String orgCode, String idIdNo,UserSession session) {
		String hql = "update BasicOrgJh a set a.orgJhState=?,a.dataCancelDate=?,a.dataCancelPerson=? "
				+ " where a.orgCode=? and a.idIdNo=? and a.memberCode=?";
		List<Object> params = new ArrayList<Object>();
		params.add(1L);
		params.add(getSystemDate());
		params.add(session.getOperatorUser());
		params.add(orgCode);
		params.add(idIdNo);
		params.add(session.getMemberCode());
		int i = executeUpdate(hql, params.toArray());
		if( i >0 ){
			i = 1;
		}else{
			i = -1;
		}
		return i;
	}

	@Override
	public int deleteAll(String orgCode, String memberCode) {
		String hql = "delete from BasicOrgJh a where a.orgCode=? and a.memberCode=?";
		List<Object> params = new ArrayList<Object>();
		params.add(orgCode);
		params.add(memberCode);
		int i = executeUpdate(hql, params.toArray());
		if( i >=0 ){
			i = 1;
		}else{
			i = -1;
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BasicOrgJh> queryOrgByIdIdNo() {
		//收款识状态（0：启用   1：注销）
		String hql = " from BasicOrgJh a,BasicOrg b where a.orgCode=b.orgCode and a.memberCode=b.memberCode "
				+ " and "+HqlUtil.emptyToHql("a.orgJhState", "0")+"=0 ";
		List<Object> params = new ArrayList<Object>();
		List<Object[]> objList = (List<Object[]>)list(hql, params.toArray());
		List<BasicOrgJh> list = new ArrayList<BasicOrgJh>();
		if(null != objList){
			for (Object[] obj : objList) {
				BasicOrgJh mod = (BasicOrgJh)obj[0];
				BasicOrg org = (BasicOrg)obj[1];
				mod.setOrgIdJh(org.getOrgIdJh());
				mod.setThdId(org.getThdId());
				list.add(mod);
			}
		}
		return list;
	}

	@Override
	public BasicOrgJh getOrgIdIdNo(String idIdNo) {
		String hql = " from BasicOrgJh a,BasicOrg b where a.orgCode=b.orgCode and a.memberCode=b.memberCode and a.idIdNo=? ";
		Object[] obj = (Object[])uniqueResult(hql, idIdNo);
		BasicOrgJh mod = null;
		if(null != obj){
			BasicOrg org = (BasicOrg)obj[1];
			mod = (BasicOrgJh)obj[0];
			mod.setOrgIdJh(org.getOrgIdJh());
			mod.setThdId(org.getThdId());
		}
		return mod;
	}

}
