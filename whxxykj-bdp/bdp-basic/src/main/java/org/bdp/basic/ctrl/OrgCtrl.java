package org.bdp.basic.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bdp.basic.entity.BasicOrg;
import org.bdp.basic.entity.BasicOrgJh;
import org.bdp.basic.service.OrgService;
import org.bdp.basic.vo.ComboBean;
import org.bdp.basic.vo.QueryBean;
import org.bdp.glodal.action.UserSession;
import org.bdp.glodal.common.superclass.SuperConstants;
import org.bdp.glodal.exception.BaseException;
import org.bdp.glodal.util.JsonModel;
import org.bdp.glodal.util.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;

@RestController
@RequestMapping("/org")
public class OrgCtrl {

    @Autowired
    private OrgService orgService;
    
	static Logger logger = Logger.getLogger(OrgCtrl.class.getName());

    @GetMapping("/say")
    public String say(@RequestParam("name")String name) {
    	return "你好"+name;
	}

    @PostMapping("/findList")
    public JsonModel queryList(@RequestBody QueryBean queryBean){
    	List<BasicOrg> list = orgService.query(queryBean, getPage());
        return JsonModel.dataResult(getPage().getRecordCount(),list);
    }
	
    private UserSession getUserSession() {
    	UserSession session = new UserSession();
    	session.setMemberCode("0000");
    	return session;
	}
    
    public PageTools getPage() {
    	return new PageTools(0, 1, 20);
    }
    
    private String getMemberCode() {
    	return getUserSession().getMemberCode();
	}
    
	/**
	 * 保存组织
	 * @return
	 */
    @PostMapping("/saveTree")
	public JsonModel saveTree(@RequestBody BasicOrg basicOrg) {
		Map<String, String> map= new HashMap<String, String>();
		try{
			if (null!=basicOrg) {
				basicOrg.setMemberCode(getMemberCode());
				basicOrg.setOrgAbbreviate(basicOrg.getOrgName());//组织名称
				int i = orgService.save(basicOrg);
				if(1!=i){
					logger.warn("公司组织:新增组织失败");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
				}else{
					logger.info("公司组织:新增组织成功");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_OK);
				}
			} else {
				logger.warn("公司组织:新增组织失败,无数据");
				map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			}
		}catch(BaseException e){
			logger.error("公司组织:新增组织错误,"+e.getMsgError());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
		}catch(Exception e){
			logger.error("公司组织:新增组织错误,"+e.getMessage());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG,e.getMessage());
		}
		return JsonModel.dataResult(map);
	}

	/**
	 * 查询组织记录
	 * @return
	 */
    @PostMapping("/editTree")
	public JsonModel editTree(@RequestParam("orgParent") String orgParent){
		Map<String, Object> map= new HashMap<String, Object>();
		BasicOrg basicOrg = orgService.getOrgByCode(orgParent,getMemberCode());
		logger.info("公司组织:查询组织["+basicOrg.getOrgParent()+"]记录");
		map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_OK);
		map.put("data", basicOrg);
		return JsonModel.dataResult(map);
	}
	
	/**
	 * 修改组织
	 * @return
	 */
    @PostMapping("/updateTree")
	public Map<String, Object> updateTree(@RequestBody BasicOrg basicOrg) {
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(null!=basicOrg){
				basicOrg.setOrgAbbreviate(basicOrg.getOrgName());//组织名称
				int i =orgService.update(basicOrg);
				if(1!=i){
					logger.warn("公司组织:更新组织["+basicOrg.getOrgAbbreviate()+"]失败");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
				}else{
					logger.info("公司组织:更新组织["+basicOrg.getOrgAbbreviate()+"]成功");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_OK);
				}
			}else{
				logger.warn("公司组织:更新组织失败,无数据");
				map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, "无数据");
			}
		}catch(BaseException e){
			logger.error("公司组织:更新组织["+basicOrg.getOrgAbbreviate()+"]错误,"+e.getMsgError());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
		}catch(Exception e){
			logger.error("公司组织:更新组织["+basicOrg.getOrgAbbreviate()+"]错误,"+e.getMessage());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getMessage());
		}
		return map;
	}
	
	/**
	 * 删除组织
	 * @return
	 */
    @PostMapping("/deleteTree")
	public Map<String, Object> deleteTree(@RequestBody BasicOrg basicOrg) {
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			if(null!=basicOrg){
				int i =orgService.deleteTree(basicOrg.getOrgParent(),basicOrg.getMemberCode());
				if(1!=i){
					logger.warn("公司组织:删除组织["+basicOrg.getOrgParent()+"]失败");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
				}else{
					logger.info("公司组织:删除组织["+basicOrg.getOrgParent()+"]成功");
					map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_OK);
				}
			}else{
				logger.warn("公司组织:删除组织失败,无数据");
				map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, "数据为空");
			}
		}catch(BaseException e){
			logger.error("公司组织:删除组织["+basicOrg.getOrgParent()+"]错误,"+e.getMsgError());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
		}catch(Exception e){
			logger.error("公司组织:删除组织["+basicOrg.getOrgParent()+"]错误,"+e.getMessage());
			map.put(SuperConstants.AJAX_RETURN_STATE, SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getMessage());
		}
		return map;
	}
	
	/**
	 * 新增机构
	 * @return
	 */
    @PostMapping("/save")
	public Map<String, Object> save(@RequestBody BasicOrg basicOrg) {
		Map<String, Object> map= new HashMap<String, Object>();
		if (null != basicOrg) {
			try {
				basicOrg.setMemberCode(getMemberCode());
				basicOrg.setOrgIsleaf(1L);//明细机构
				int i = orgService.save(basicOrg);
				if (1 != i) {
					logger.warn("机构设置:新增失败");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				} else {
					logger.info("机构设置:新增成功");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_OK);
				}
			} catch (BaseException e) {
				logger.error("机构设置:新增错误," + e.getMsgError());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
			} catch (Exception e) {
				logger.error("机构设置:新增错误," + e.getMessage());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getMessage());
			}
		} else {
			logger.warn("机构设置:新增失败,无数据");
			map.put(SuperConstants.AJAX_RETURN_MSG, "无数据");
		}
		return map;
	}

	/**
	 * 修改机构
	 * @return
	 */
    @PostMapping("/update")
	public Map<String, Object> update(@RequestBody BasicOrg basicOrg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != basicOrg) {
			try {
				int i = orgService.update(basicOrg);
				if (1 != i) {
					logger.warn("机构设置:修改失败");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				} else {
					logger.info("机构设置:修改成功");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_OK);
				}
			} catch (BaseException e) {
				logger.error("机构设置:修改错误," + e.getMsgError());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
			} catch (Exception e) {
				logger.error("机构设置:修改错误," + e.getMessage());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getMessage());
			}
		} else {
			logger.warn("机构设置:修改失败,无数据");
			map.put(SuperConstants.AJAX_RETURN_MSG, "无数据");
		}
		return map;
	}

    @PostMapping("/delete")
	public Map<String, Object> delete(@RequestBody BasicOrg basicOrg) {
    	Map<String, Object> map = new HashMap<String, Object>();
		if (null != basicOrg && null != basicOrg.getOrgCode()) {
			try {
				int i = orgService.delete(basicOrg.getOrgCode(),getMemberCode());
				if (1 != i) {
					logger.warn("机构设置:删除失败");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				} else {
					logger.info("机构设置:删除成功");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_OK);
				}
			} catch (BaseException e) {
				logger.error("机构设置:删除错误," + e.getMsgError());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
			} catch (Exception e) {
				logger.error("机构设置:删除错误," + e.getMessage());
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, e.getMessage());
			}
		} else {
			logger.warn("机构设置:删除失败,无数据");
			map.put(SuperConstants.AJAX_RETURN_MSG, "无数据");
		}
		return map;
	}

    @PostMapping("/queryCombo")
	public JsonModel queryCombo(@RequestBody QueryBean queryBean) {
		if(null==queryBean){
			queryBean=new QueryBean();
		}
		List<ComboBean> orgComboList = orgService.queryCombo(getUserSession(), queryBean.getQuery(),queryBean);
		return JsonModel.dataResult(orgComboList);
	}

    @PostMapping("/querySuperiorCombo")
	public JsonModel querySuperiorCombo(@RequestParam String query, @RequestParam String orgCode) {
		List<ComboBean> orgComboList = orgService.querySuperiorCombo(getUserSession(), query,orgCode);
		return JsonModel.dataResult(orgComboList);
	}

    @PostMapping("/queryInnerOrgCombo")
	public JsonModel queryInnerOrgCombo(@RequestParam String query, @RequestParam String orgCode) {
		List<ComboBean> orgComboList = orgService.queryInnerOrgCombo(getUserSession(), query,orgCode);
		return JsonModel.dataResult(orgComboList);
	}
	
	/**
	 * 查询机构
	 * * @return
	 */
    @PostMapping("/edit")
	public JsonModel edit(@RequestParam String orgCode) {
    	BasicOrg basicOrg = orgService.getOrgByCode(orgCode, getMemberCode());
		logger.info("查询["+orgCode+"]的机构交行收款码:查询机构");
		return JsonModel.dataResult(basicOrg);
	}
	/**
	 * 查询机构交行收款码
	 * * @return
	 */
    @PostMapping("/queryOrgJh")
	public JsonModel queryOrgJh(@RequestParam String orgCode) {
		List<BasicOrgJh> orgJhList = orgService.queryOrgJh(orgCode,getMemberCode());
		logger.info("查询["+orgCode+"]的机构交行收款码：查询机构交行收款码");
		return JsonModel.dataResult(orgJhList);
	}
	/**
	 * 机构交行收款码设置
	 * @return
	 */
    @PostMapping("/updateOrgJh")
	public Map<String, Object> updateOrgJh(@RequestParam String orgJhStr) {
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<BasicOrgJh> orgJhList = (List<BasicOrgJh>) JSONArray.parse(orgJhStr);
			if (null != orgJhList) {
				int i = orgService.updateOrgJh(orgJhList, getMemberCode());
				if (1 == i) {
					logger.info("机构设置:更新机构交行收款码成功");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_OK);
				} else {
					logger.warn("机构设置:更新机构交行收款码失败");
					map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				}
			}else {
				logger.warn("机构设置:更新机构交行收款码失败,无数据");
				map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
				map.put(SuperConstants.AJAX_RETURN_MSG, "无数据");
			}
		} catch (BaseException e) {
			e.printStackTrace();
			logger.error("机构设置:更新机构交行收款码错误," + e.getMsgError());
			map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, e.getExcepMess());
		} catch (Exception e) {
			logger.error("机构设置:更新机构交行收款码错误," + e.getMessage());
			map.put(SuperConstants.AJAX_RETURN_STATE,SuperConstants.AJAX_RETURN_STATE_ERROR);
			map.put(SuperConstants.AJAX_RETURN_MSG, "");
		}
		return map;
	}

}
