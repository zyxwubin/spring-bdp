package org.bdp.basic.service.impl;

import java.util.List;

import org.bdp.basic.common.BasicConstants;
import org.bdp.basic.dao.OrgDao;
import org.bdp.basic.dao.OrgJhDao;
import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.entity.BasicOrgJh;
import org.bdp.basic.service.OrgService;
import org.bdp.basic.vo.ComboBean;
import org.bdp.basic.vo.QueryBean;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.common.superclass.SuperConstants;
import org.bdp.glodal.exception.BaseException;
import org.bdp.glodal.util.PageTools;
import org.bdp.glodal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private OrgJhDao orgJhDao;

	public int save(BasicOrg mod) {
		String msg = checkOrg(mod);
		if (StringUtil.isNotBlank(msg)) {
			throw new BaseException("0301005", "操作失败:"+msg, "新增失败,"+msg);
		}
		if (StringUtil.isBlank(mod.getOrgCode())) {
			mod.setOrgCode(orgDao.getMaxOrgCode());
		}
		String str="";
		if(0==mod.getOrgIsleaf().intValue()){
			str="组织";
		}else{
			str="机构";
		}
		BasicOrg oldMod=orgDao.getByCode(mod.getOrgCode());
		if(null!=oldMod){
			throw new BaseException("0301006","操作失败："+str+"代码已经存在","新增失败,信息["+mod.getOrgCode()+"]失败:已经存在");
		}
		oldMod=orgDao.getByName(mod.getOrgName(), mod.getMemberCode());
		if(null!=oldMod){
			throw new BaseException("0301007","操作失败："+str+"名称已经存在","新增失败,信息["+mod.getOrgName()+"/"+mod.getMemberCode()+"]失败:已经存在");
		}
		oldMod=orgDao.getByAbbreviate(mod.getOrgAbbreviate(), mod.getMemberCode());
		if(null!=oldMod){
			throw new BaseException("0301008","操作失败："+str+"简称已经存在","新增失败,信息["+mod.getOrgName()+"/"+mod.getMemberCode()+"]失败:已经存在");
		}
		//给树结点编码赋值
		mod.setOrgNodecode(getOrgNodecode(mod.getOrgParent(),mod.getOrgCode()));
		try {
			orgDao.save(mod);
		} catch (Exception e){
			throw new BaseException("0301009", "", "插入信息["+mod.getOrgCode()+"/"+mod.getOrgName()+"/"+mod.getMemberCode()+"]保存失败,异常");
		}
		return 1;
	}

	public int update(BasicOrg mod) {
		String msg = checkOrg(mod);
		if (StringUtil.isNotBlank(msg)) {
			throw new BaseException("03010010", "操作失败:" + msg, "更新失败," + msg);
		}
		BasicOrg oldMod=orgDao.getByCode(mod.getOrgCode());
		if(null==oldMod){
			throw new BaseException("03010011","操作失败：信息不存在","更新失败,信息["+mod.getOrgCode()+"]失败:不存在");
		}
		String str="";
		if(0==mod.getOrgIsleaf().intValue()){
			str="组织";
		}else{
			str="机构";
		}
		if(!oldMod.getOrgName().equals(mod.getOrgName())){
			BasicOrg checkoldMod=orgDao.getByName(mod.getOrgName(), mod.getMemberCode());
			if(null!=checkoldMod){
				throw new BaseException("0301012","操作失败："+str+"名称已经存在","更新失败,信息["+mod.getOrgName()+"/"+mod.getMemberCode()+"]失败:已经存在");
			}
		}
		if(!oldMod.getOrgAbbreviate().equals(mod.getOrgAbbreviate())){
			BasicOrg checkoldMod=orgDao.getByAbbreviate(mod.getOrgAbbreviate(), mod.getMemberCode());
			if(null!=checkoldMod){
				throw new BaseException("0301013","操作失败："+str+"简称已经存在","更新失败,信息["+mod.getOrgName()+"/"+mod.getMemberCode()+"]失败:已经存在");
			}
		}
		//当上级节点发生变化时重新给树结点编码赋值
		if(!oldMod.getOrgParent().equals(mod.getOrgParent())){
			mod.setOrgNodecode(getOrgNodecode(mod.getOrgParent(),mod.getOrgCode()));
		}else{//防止并发
			mod.setOrgNodecode(oldMod.getOrgNodecode());
		}
		try {
			mod.setOrgId(oldMod.getOrgId());
			orgDao.save(mod);
		} catch (Exception e){
			throw new BaseException("03010015", "", "更新信息["+mod.getOrgCode()+"/"+mod.getOrgName()+"/"+mod.getMemberCode()+"]保存失败,异常");
		}
		return 1;
	}
	
	public int delete(String orgCode,String memberCode){
		if (StringUtil.isBlankOne(orgCode, memberCode)){
			throw new BaseException("0301001", "", "删除["+orgCode+"/"+memberCode+"]失败:memberCode为空");
		}
		int i = 0;
		try {
			i = orgDao.delete(orgCode, memberCode);
		} catch (Exception e){
			throw new BaseException("0301003", "", "删除["+orgCode+"/"+memberCode+"]异常");
		}
		if (1 != i){
			throw new BaseException("0301004", "", "删除["+orgCode+"/"+memberCode+"]失败:"+i);
		}
		try {
			i = orgJhDao.deleteAll(orgCode, memberCode);
		} catch (Exception e){
			throw new BaseException("", "", "删除机构交行收款码["+orgCode+"/"+memberCode+"]异常");
		}
		if (1 != i){
			throw new BaseException("", "", "删除机构交行收款码["+orgCode+"/"+memberCode+"]失败:"+i);
		}
		return 1;
	}

	/**
	 * 获取节点编码
	 * @param orgParent 父节点代码
	 * @param orgCode 树节点代码
	 * @return
	 */
	private String getOrgNodecode(String orgParent,String orgCode){
		String orgNodecode="";
		if(!SuperConstants.TREE_EMPTY.equals(orgParent)){
			BasicOrg parentMod=orgDao.getByCode(orgParent);
			if(null==parentMod || StringUtil.isBlank(parentMod.getOrgNodecode())){
				throw new BaseException("0301008","操作失败：上级节点编码为空","新增失败,信息["+orgParent+"]失败:上级节点编码为空");
			}
			orgNodecode=parentMod.getOrgNodecode();
			if(StringUtil.isNotBlank(orgCode)){
				orgNodecode+='.'+orgCode;
			}
		}else{
			orgNodecode=orgCode;
		}
		return orgNodecode;
	}

	private String checkOrg(BasicOrg mod) {
		if(null==mod){
			return "数据为空";
		}
		String msg = "";
		msg += StringUtil.isBlankToMsg(mod.getMemberCode(), "使用单位为空");
		if(null==mod.getOrgIsleaf()){
			mod.setOrgIsleaf(0L);
		}
		if(null==mod.getOrgSign()){
			mod.setOrgSign(0L);
		}
		if(null==mod.getSalePriceflag()){
			mod.setSalePriceflag(0L);
		}
		String str="";
		if(0==mod.getOrgIsleaf().intValue()){
			str="组织";
		}else{
			str="机构";
		}
		msg += StringUtil.isBlankToMsg(mod.getOrgName(), str+"名称为空");
		if(0!=mod.getOrgIsleaf().intValue()){
			msg += StringUtil.isBlankToMsg(mod.getOrgAbbreviate(), str+"简称为空");
		}
		if(1==mod.getOrgIsleaf().intValue()&&StringUtil.isBlank(mod.getOrgParent())){
			msg +="上级组织为空";
		}

		if (StringUtil.isNotBlank(msg) && msg.trim().endsWith(",")) {
			msg = msg.trim().substring(0, msg.trim().length() - 1);
		}
		if(StringUtil.isBlank(mod.getOrgParent())){
			mod.setOrgParent(SuperConstants.TREE_EMPTY);
		}
		return msg;
	}

	

	public List<BasicOrg> query(QueryBean bean, PageTools page) {
		return orgDao.query(bean, page);
	}

	public List<ComboBean> queryCombo(UserSession user, String query,QueryBean queryBean) {
		return orgDao.queryCombo(user, query, queryBean);
	}

	@Override
	public List<ComboBean> querySuperiorCombo(UserSession user, String query, String orgCode){
		return orgDao.querySuperiorCombo(user, query, orgCode);
	}

	@Override
	public List<ComboBean> queryInnerOrgCombo(UserSession user, String query, String orgCode) {
		return orgDao.queryInnerOrgCombo(user,query,orgCode);
	}

	public int deleteTree(String orgCode, String memberCode) {
		if(StringUtil.isBlank(orgCode)){
			throw new BaseException("0301001","","删除失败:orgCode参数为空");
		}
		//检测是否有机构明细
		if(0<getCountByParentCode(orgCode,1L,memberCode)){
			throw new BaseException("0301006","公司组织已经有明细机构,请先删除明细机构","更新失败,公司组织已经有明细机构");
		}
		int i=0;
		try{
			i=orgDao.delete(orgCode, memberCode);
		}catch(Exception e){
			throw new BaseException("0301002","","删除["+orgCode+"]异常");
		}
		if(1!=i){
			throw new BaseException("0301002","","删除["+orgCode+"]失败:"+i);
		}
		i=deleteTreeByParent(orgCode, memberCode);
		if(1!=i){
			throw new BaseException("0301002","","删除["+orgCode+"]子节点信息失败:"+i);
		}
		return 1;
	}

	public int deleteTreeByParent(String orgParent,String memberCode) {
		if(StringUtil.isBlankOne(orgParent)){
			throw new BaseException("0301003","","删除失败:orgParent为空");
		}
		List<BasicOrg> list =orgDao.queryByParent(orgParent,0L,memberCode);
		if(null!=list&&!list.isEmpty()){
			int i =0;
			for (BasicOrg mod : list) {
				//检测是否有机构明细
				if(0<getCountByParentCode(mod.getOrgCode(),1L,memberCode)){
					throw new BaseException("0301006","公司组织已经有明细机构,请先删除明细机构","更新失败,公司组织已经有明细机构");
				}
				deleteTreeByParent(mod.getOrgCode(),memberCode);
			}
			try{
				i = orgDao.deleteOrgByParent(orgParent, 0L, memberCode);
			}catch(Exception e){
				throw new BaseException("0301004","","删除["+orgParent+"]异常");
			}
			if(1!=i){
				throw new BaseException("0301005","","删除["+orgParent+"]失败:"+i);
			}
		}
		return 1;
	}

	/**
	 * 通过父结点代码,机构属性查询记录数
	 * @param orgParent 父结点代码
	 * @param orgIsleaf 机构属性(0：机构组,1：机构明细,null:所有)
	 * @param memberCode 使用单位
	 * @return
	 */
	private int getCountByParentCode(String orgParent,Long orgIsleaf,String memberCode){
		return orgDao.getCountByParentCode(orgParent, orgIsleaf,memberCode);
	}

	public BasicOrg getOrgByCode(String orgCode, String memberCode) {
		return orgDao.getByCode(orgCode);
	}

//	public OrgSignBean getOrgSignByCode(String orgCode, String memberCode) {
//		if(StringUtil.isBlankOne(orgCode, memberCode)){
//			throw new BaseException("0301004","获取机构信息参数为空","获取机构信息参数为空");
//		}
//		BasicOrg mod = BaseConstants.getOrg(orgCode, memberCode);
//		if(null==mod){
//			throw new BaseException("","获取机构["+ orgCode +"]信息失败,该机构不存在!","获取机构不存在");
//		}
//		return new OrgSignBean(mod.getOrgCode(), mod.getOrgSign(), mod.getOrgSignratio());
//	}

	public int updateTreeParent(String orgCode,String oldOrgNodecode, String orgParent,String memberCode) {
		int i =0;
		String newOrgNodecode=getOrgNodecode(orgParent, orgCode);
		try{
			i=orgDao.updateOrgParentCode(orgCode, newOrgNodecode, orgParent, memberCode);
		}catch(Exception e){
			throw new BaseException("0301004","","更新组织机构["+orgCode+"]层次关系失败:异常");
		}
		if(1!=i){
			throw new BaseException("0301005","","更新组织机构["+orgCode+"]层次关系失败:异常");
		}
		try{//更新下级编号
			i=orgDao.updateOrgNodecode(oldOrgNodecode, newOrgNodecode, memberCode);
		}catch(Exception e){
			throw new BaseException("0301006","","更新组织机构["+orgCode+"]层次关系失败:异常");
		}
		return 1;
	}

	public List<BasicOrg> queryAll() {
		return orgDao.queryAll();
	}

	@Override
	public BasicOrg getOrgByShare(String shareCode, String memberCode){
		return orgDao.getOrgByShare(shareCode, memberCode);
	}

	public BasicOrg getOrgByAbbreviateName(String orgAbbreviate, String memberCode) {
		return orgDao.getByAbbreviate(orgAbbreviate, memberCode);
	}

	@Override
	public BasicOrg getOrgByAbbreviateNameAndLeaf(String orgAbbreviate, String memberCode, Long orgIsleaf) {
		return orgDao.getByAbbreviateAndLeaf(orgAbbreviate, memberCode,orgIsleaf);
	}

	@Override
	public List<BasicOrg> queryOrg(String accNo) {
		return orgDao.queryOrg(accNo);
	}

	@Override
	public List<BasicOrgJh> queryOrgJh(String orgCode, String memberCode) {
		return orgJhDao.queryOrgJh(orgCode, memberCode);
	}

	@Override
	public int updateOrgJh(List<BasicOrgJh> orgJhList, String memberCode) {
		if(null != orgJhList && !orgJhList.isEmpty()){
			for(BasicOrgJh mod: orgJhList){
				if(BasicConstants.DATAFLAG_0.equals(String.valueOf(mod.getDataFlag()))){
					mod.setMemberCode(memberCode);
					String msg = checkOrgJh(mod);
					if(StringUtil.isNotBlank(msg)){
						throw new BaseException("",msg,"插入失败,"+msg);
					}
					try{
						mod.setDataSystemdate(orgJhDao.getSystemDate());
						orgJhDao.save(mod);
					}catch(Exception e){
						e.printStackTrace();
						throw new BaseException("","","增加机构交行收款码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]异常,"+e.getMessage());
					}
				} else if(BasicConstants.DATAFLAG_1.equals(String.valueOf(mod.getDataFlag()))){
					mod.setMemberCode(memberCode);
					String msg = checkOrgJh(mod);
					if(StringUtil.isNotBlank(msg)){
						throw new BaseException("",msg,"插入失败,"+msg);
					}
					BasicOrgJh oldMod = orgJhDao.getOrgJh(mod.getOrgCode(), mod.getIdIdNo(), memberCode);
					if(null == oldMod){
						throw new BaseException("","该记录已经不存在","更新机构交行收款码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]失败:该记录已经不存在");
					}
					try{
						oldMod.setOrgJhRemark(mod.getOrgJhRemark());
						orgJhDao.save(oldMod);
					}catch(Exception e){
						e.printStackTrace();
						throw new BaseException("","","更新机构交行收款码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]异常,"+e.getMessage());
					}
				} else if(BasicConstants.DATAFLAG_2.equals(String.valueOf(mod.getDataFlag()))){
					if(null != mod.getOrgJhId()){
						try{
							orgJhDao.delete(mod.getOrgJhId());
						}catch(Exception e){
							throw new BaseException("","","删除机构交行收款码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]失败,异常：");
						}
					}
				} else {
					throw new BaseException("","","修改机构交行收款码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]失败,dataFlag超出范围");
				}
			}
		}
		return 1;
	}

	/**
	 * 检测
	 * @param mod
	 * @return
	 */
	private String checkOrgJh(BasicOrgJh mod){
		if(null == mod){
			return "数据为空";
		}
		
		String msg = "";
		msg += StringUtil.isBlankToMsg(mod.getMemberCode(), "会员代码为空");
		msg += StringUtil.isBlankToMsg(mod.getOrgCode(), "机构代码为空");
		msg += StringUtil.isBlankToMsg(mod.getIdIdNo(), "交行转账收款识别码为空");
		
		if(null == mod.getOrgJhState()){//收款识状态（0：启用   1：注销）
			mod.setOrgJhState(0L);
		}
		if(StringUtil.isNotBlank(msg) && msg.endsWith(",")){
			msg = msg.substring(0,msg.length()-1);
		}
		return msg;
	}
	@Override
	public int updateIdIdNo(String idIdNo, String idNoNme, String orgCode,String updateType,UserSession session) {
		if(StringUtil.isBlankOne(idIdNo,orgCode,updateType) || null == session){
			throw new BaseException("","参数为空","交行开收款识别码/注销收款识别码失败：参数为空");
		}
		if("create".equals(updateType)){
			BasicOrgJh mod = new BasicOrgJh();
			mod.setMemberCode(session.getMemberCode());
			mod.setOrgCode(orgCode);
			mod.setIdIdNo(idIdNo);
			mod.setIdNoNme(idNoNme);
			mod.setOrgJhState(0L);//收款识状态（0：启用   1：注销）
			try{
				mod.setDataSystemdate(orgJhDao.getSystemDate());
				orgJhDao.save(mod);
			}catch(Exception e){
				e.printStackTrace();
				throw new BaseException("","","开通收款识别码["+mod.getIdIdNo()+"/"+mod.getOrgCode()+"/"+mod.getMemberCode()+"]异常,"+e.getMessage());
			}
		}else if("cancel".equals(updateType)){
			BasicOrgJh oldMod = orgJhDao.getOrgJh(orgCode, idIdNo, session.getMemberCode());
			if(null == oldMod){
				throw new BaseException("","该记录已经不存在","更新机构交行收款码["+idIdNo+"/"+orgCode+"/"+session.getMemberCode()+"]失败:该记录已经不存在");
			}
			int i = orgJhDao.updateCancelOrgJh(orgCode, idIdNo, session);
			if(1!=i){
				throw new BaseException("","","注销收款识别码失败："+i);
			}
		}
		return 1;
	}

	@Override
	public List<BasicOrgJh> queryOrgByIdIdNo() {
		return orgJhDao.queryOrgByIdIdNo();
	}

	@Override
	public BasicOrgJh getOrgIdIdNo(String idIdNo) {
		return orgJhDao.getOrgIdIdNo(idIdNo);
	}

	@Override
	public BasicOrgJh getOrgJh(String orgCode, String idIdNo, String memberCode) {
		return orgJhDao.getOrgJh(orgCode, idIdNo, memberCode);
	}

}
