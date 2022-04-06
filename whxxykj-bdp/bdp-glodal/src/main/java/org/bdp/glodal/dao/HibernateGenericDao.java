package org.bdp.glodal.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bdp.glodal.util.PageTools;
import org.bdp.glodal.util.StringUtil;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-3-7
 * Create Author: gp
 * Last version:  2.0
 * Function:dao超类实现
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class HibernateGenericDao<T, PK extends Serializable> extends SimpleJpaRepository<T, PK> implements GenericDao<T, PK> {

	private final EntityManager em;
	
	private final Class<T> entityInformation;
	
	public HibernateGenericDao(Class<T> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
        this.entityInformation = entityInformation;
	}

	@Override
	public T getOne(PK id) {
        Assert.notNull(id, "id 不能为空");
        T entity = (T) findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return entity;
    }

	@Override
	public List<T> queryAll() {
		return findAll();
	}

	@Override
	public boolean exists(PK id) {
		T entity = (T) getOne(id);
		return entity != null;
	}

	@Override
	public int updateLock(String column, String billcodeName, String billcode, String memberCode) {
		String hql = "update "+entityInformation.getName()+" a set a."+column+"=a."+column+" where a."+billcodeName+"=? and a.memberCode=?";
		int i = this.executeUpdate(hql, billcode, memberCode);
        if (i > 0) {
            i = 1;
        } else {
            i = -1;
        }
		return i;
	}

	
	
	@Override
	public void delete(PK id) {
		delete(id);
	}

	@Override
	public List<T> queryAllDistinct() {
		Collection result = new LinkedHashSet(queryAll());
		return new ArrayList(result);
	}

	@Override
	public Date getSystemDate() {
        return new Date();
	}

	@Override
	public boolean getCountBywhere(String whereHql) {
		int count = getResultCount("from "+entityInformation.getName()+" where "+whereHql); 
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询对象
	 * @param hql
	 * @param args
	 * @return Object
	 */
	public Object uniqueResult(String hql, Object... args) {
		Query q = em.createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		List list = q.getResultList();
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 查询对象
	 * @param sql
	 * @param args
	 * @return Object
	 */
	public Object uniqueResultSql(String sql, Object... args) {
		Query q = em.createNativeQuery(sql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		List list = q.getResultList();
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 查询对象集合
	 * @param hql
	 * @param args
	 * @return list
	 */
	public List list(String hql, Object... args) {
		Query q = em.createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		List list =q.getResultList();
		return list;
	}
	
	/**
	 * 查询对象集合
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List listSql(String sql, Object... args) {
		Query q = em.createNativeQuery(sql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		List list =q.getResultList();
		return list;
	}
	
	/**
	 * 更新语句
	 * @param sql
	 * @param args 参数
	 * @return 更新记录数
	 */
	public int executeUpdateSql(String sql, Object... args) {
		Query q = em.createNativeQuery(sql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		int i = q.executeUpdate();
		return i;
	}
	
	/**
	 * 分页查询对象集合
	 * @param hql hql语句
	 * @param page 分页
	 * @return list
	 */
	public List queryForPageByHql(String hql, PageTools page) {
		if (null == page) {
			return list(hql);
		}else{
			int count = getResultCount(hql);
			page.setRecordCount(count);
			List list = em.createQuery(hql).setFirstResult(page.getStartRow()).setMaxResults(page.getPageSize()).getResultList();
			return list;
		}
	}
	
	/**
	 * 分页查询对象集合
	 * @param hql hql语句
	 * @param page 分页
	 * @param args 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryForPageByHql(String hql, PageTools page, Object... args) {
		if (null == page) {
			return list(hql, args);
		}else if((page.isBeginTotal()||page.isPriorTotal())&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
			setResultPeriod(hql, page, args);
			return null;
		}else if(page.isNeedGroup()&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
			setResultGroupSum(hql, page, args);
			return null;
		}else{
			if(page.isNeedTotal()&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
				setResultCountAndSum(hql, page, args);
			}else{
				int count = getResultCount(hql, args);
				page.setRecordCount(count);
			}
			Query query = em.createQuery(hql).setFirstResult(page.getStartRow()).setMaxResults(page.getPageSize());
//			if(hql.trim().startsWith("select")){
//				query.setResultTransformer(Transformers.aliasToBean(this.entityInformation));
//			}
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			List list = query.getResultList();
			return list;
		}
	}
	
	/**
	 * 分页查询对象集合(可指定封装Class)
	 * @param hql hql语句
	 * @param page 分页
	 * @param args 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryForPageByHqlWithClass(Class otherClass, String hql, PageTools page, Object... args) {
		if (null == page) {
			return listWithClass(otherClass, hql, args);
		}else if((page.isBeginTotal()||page.isPriorTotal())&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
			setResultPeriod(hql, page, args);
			return null;
		}else if(page.isNeedGroup()&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
			setResultGroupSum(hql, page, args);
			return null;
		}else{
			if(page.isNeedTotal()&&null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()){
				setResultCountAndSum(hql, page, args);
			}else{
				int count = getResultCount(hql, args);
				page.setRecordCount(count);
			}
			Query query = em.createQuery(hql).setFirstResult(page.getStartRow()).setMaxResults(page.getPageSize());
//			if(hql.trim().startsWith("select")){
//				query.setResultTransformer(Transformers.aliasToBean(null==otherClass?this.persistentClass:otherClass));
//			}
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			List list = query.getResultList();
			return list;
		}
	}
	
	/**
	 * 查询对象集合(可指定封装Class)
	 * @param hql
	 * @param args
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List listWithClass(Class otherClass, String hql, Object... args) {
		Query q = em.createQuery(hql);
//		if(hql.trim().startsWith("select")){
//			q.setResultTransformer(Transformers.aliasToBean(null==otherClass?this.persistentClass:otherClass));
//		}
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		List list =q.getResultList();
		return list;
	}

	
	
	/**
	 * 把查询出来的结果付给page
	 * @param object 结果集
	 * @param page 分页
	 */
	private Map<String,String> getCountAndSumValueToPage(Object[] object, PageTools page) {
		Map<String, String> map = page.getTotalProperty();
		Map<String, String> s = new HashMap<String, String>();
		if (null != object && 1 < object.length && null != map && !map.isEmpty()) {
			int i = 1;
			for (String name : map.keySet()) {
				if (i == object.length) {
					break;
				}
				if (null != object[i]) {
					String value = object[i].toString();
					BigDecimal bd = new BigDecimal(value);
					s.put(name, bd.toString());
				} else {
					s.put(name, "0");
				}
				i++;
			}
		}
		return s;
	}
	
	/**
	 * 获取查询数据所有记录
	 * @param queryString 查询语句
	 * @param args 参数
	 * @return 记录数
	 */
	@SuppressWarnings("unchecked")
	public int getResultCount(String queryString, Object... args) {
		/*
		 * String countQueryString = " select count (*) " +
		 * removeSelect(removeOrders(queryString));
		 * List countlist = find(countQueryString, args); int count = 0; if
		 * (countlist.size() == 1) count = ((Long) countlist.get(0)).intValue();
		 * else count = countlist.size(); return count;
		 */
		String hqlstr = removeOrders(queryString);
		if (this.hasDistinctOrGroupBy(hqlstr)) {
			List lt = find(hqlstr, args);
			return lt == null ? 0 : lt.size();
		} else {
			String countQueryString = " select count (*) " + removeSelect(removeOrders(queryString));

			List countlist = find(countQueryString, args);
			int count = 0;
			if (countlist.size() == 1)
				count = ((Long) countlist.get(0)).intValue();
			else
				count = countlist.size();
			return count;
		}
	}
	
	/**
	 * 获取查询数据所有记录数和汇总属性值
	 * @param queryString 查询语句
	 * @param page 分页对象
	 * @param args 参数
	 * @return 记录数
	 */
	@SuppressWarnings("unchecked")
	private void setResultCountAndSum(String queryString, PageTools page, Object... args) {
		if (null != page.getTotalProperty() && !page.getTotalProperty().isEmpty()) {
			String hqlstr = removeOrders(queryString);
			if(hasGroupByHaving(hqlstr)){
				String hql = " select count (*) ";
				String s = mapToHql(page.getTotalProperty());
				if (StringUtil.isNotBlank(s)) {
					hql += ","+ s;
				}
				String countQueryString = "";
				if (page.isLast()) {
					countQueryString = hql + removeSelectLastFrom(hqlstr);
				}else{
					countQueryString = hql + removeSelect(hqlstr);
				}
				List<Object[]> objList = find(countQueryString, args);
				page.setRecordCount(0);
				if (null != objList && !objList.isEmpty()) {
					page.setRecordCount(null != objList && !objList.isEmpty() ? objList.size() : 0);
					page.setSummaryMap(getCountAndSumValueToPage(objList, page));
				}
			}else{
				hqlstr = removeGroupBy(hqlstr);
				String hql = " select count (*) ";
				String s = mapToHql(page.getTotalProperty());
				if (StringUtil.isNotBlank(s)) {
					hql += ","+ s;
				}
				String countQueryString = "";
				if (page.isLast()) {
					countQueryString = hql + removeSelectLastFrom(hqlstr);
				}else{
					countQueryString = hql + removeSelect(hqlstr);
				}
				List objList = find(countQueryString, args);
				if (null != objList && !objList.isEmpty()) {
					Object[] obj = null;
					try {
						obj = (Object[]) objList.get(0);
					} catch (Exception e) {
						obj = new Long[]{(Long)objList.get(0)};
					}
					if (this.hasDistinctOrGroupBy(queryString)) {
						String hqlstr1 = removeOrders(queryString);
						String countQueryString1 = "";
						if (page.isLast()) {
							countQueryString1 = " select count (*) " + removeSelectLastFrom(hqlstr1);
						}else{
							countQueryString1 = " select count (*) " + removeSelect(hqlstr1);
						}
						List objList1 = find(countQueryString1, args);
						obj[0] = null != objList1 && !objList1.isEmpty() ? objList1.size() : 0;
					}
					page.setRecordCount(null == obj || null == obj[0] ? 0 : Integer.valueOf(String.valueOf(obj[0])));
					page.setSummaryMap(getCountAndSumValueToPage(objList, page));
				}
			}
		}
	}
	
	/**
	 * 查询分组小计的汇总数
	 * @param queryString 查询语句
	 * @param page 分页对象
	 * @param args 参数
	 */
	@SuppressWarnings("unchecked")
	private void setResultGroupSum(String queryString, PageTools page, Object... args){
		String s="select "+page.getGroup()+","+mapToHql(page.getTotalProperty())+" "; 
		String hqlstr = removeGroupBy(removeOrders(queryString)) + page.getOtherGroupHql();
		Object[] newArgs = ArrayUtils.addAll(null==args?new Object[1]:args, page.getOtherGroupParams().toArray());
		String hql="";
		if (page.isLast()) {
			hql=s+removeSelectLastFrom(hqlstr)+" group by "+ page.getGroup();
		}else{
			hql=s+removeSelect(hqlstr)+" group by "+ page.getGroup();
		}
		List objList = find(hql, newArgs);
	    if (null != objList && !objList.isEmpty()) {
	    	 Map<String, Map<String, String>> subtotalMap=new HashMap<String, Map<String, String>>(); 
	    	 for(int i=0;i<objList.size();i++){
	    		Object[] obj = null;
	    		try {
					obj = (Object[]) objList.get(i);
				} catch (Exception e) {
				}
				if(null!=obj){
					String key= null == obj[0] ? "" : String.valueOf(obj[0]);
					subtotalMap.put(key,getCountAndSumValueToPage(objList, page));
				}
	    	}
	    	page.setSubtotalMap(subtotalMap);
	    }
	}
	
	public String getMaxCode(String columnName,String str,int len ){
		String wherehql=StringUtil.filRight(str, '_', len);
		Object obj =uniqueResult("select max(a."+columnName+") from "+entityInformation.getName()+" a  where a."+columnName+" like '"+wherehql+"'");
		String maxStr="";
		if(null==obj||!StringUtil.isNumeric(obj.toString())){
			if(null != obj){
				maxStr = String.valueOf(Long.valueOf(obj.toString().replaceAll(str, ""))+1);
			}
			len-=str.length();
		}else{
			Long strLong=Long.valueOf(obj.toString())+1;
			maxStr= strLong.toString();
		}
		maxStr= StringUtil.fillLeft(maxStr, '0', len);
		maxStr=str+maxStr;
		return maxStr;
	}
	
	public String getMaxCode(String columnName,String str,int len,String where){
		String wherehql=StringUtil.filRight(str, '_', len);
		String hql="select max(a."+columnName+") from "+entityInformation.getName()+" a  where a."+columnName+" like '"+wherehql+"'";
		if(StringUtil.isNotBlank(where)){
			hql+=" and "+where;
		}
		Object obj =uniqueResult(hql);
		String maxStr="";
		if(null==obj||!StringUtil.isNumeric(obj.toString())){
			if(null != obj){
				maxStr = String.valueOf(Long.valueOf(obj.toString().replaceAll(str, ""))+1);
			}
			len-=str.length();
		}else{
			Long strLong=Long.valueOf(obj.toString())+1;
			maxStr= strLong.toString();
		}
		maxStr= StringUtil.fillLeft(maxStr, '0', len);
		maxStr=str+maxStr;
		return maxStr;
	}
	
	/**
	 * 把查询出来的结果付给page
	 * @param objList 结果集
	 * @param page 分页
	 */
	private Map<String,String> getCountAndSumValueToPage(List<Object[]> objList, PageTools page) {
		Map<String, String> map = page.getTotalProperty();
		Map<String, String> s = new HashMap<String, String>();
		if (null != objList && !objList.isEmpty() && null != map && !map.isEmpty()) {
			for(Object[] object:objList){
				int i = 1;
				for (String name : map.keySet()) {
					if (i == object.length) {
						break;
					}
					String o=s.get(name);
					if(StringUtil.isBlank(o)){
						o="0";
					}
					if (null != object[i]) {
						String value = object[i].toString();
						BigDecimal bd = new BigDecimal(value);
						bd = bd.add(new BigDecimal(o));
						s.put(name, bd.toString());
					} else {
						s.put(name, o);
					}
					i++;
				}
			}
		}
		return s;
	}
	
	/**
	 * 查询期初或上页合计
	 * @param queryString 查询语句
	 * @param page 分页对象
	 * @param args 参数
	 */
	@SuppressWarnings("unchecked")
	private void setResultPeriod(String queryString, PageTools page, Object... args){
		String s="select 1,"+mapToHql(page.getTotalProperty())+" "; 
		if(page.isBeginTotal()){
			String hqlstr = removeGroupBy(removeOrders(queryString));
			String hql="";
			if (page.isLast()) {
				hql=s+removeSelectLastFrom(hqlstr);
			}else{
				hql=s+removeSelect(hqlstr);
			}
			List objList = find(hql, args);
			if (null != objList && !objList.isEmpty()) {
				Object[] obj = null;
				try {
					obj = (Object[]) objList.get(0);
				} catch (Exception e) {
					obj = new Double[]{(Double)objList.get(0)};
				}
				page.setBeginMap(getCountAndSumValueToPage(obj, page));
			}
		}else{
			String hqlstr="select 1,"+mapToColhql(page.getTotalProperty())+" "+removeSelect(queryString);
			String hql=s+" from ("+hqlstr+") where rownum<="+page.getPageSize();
			//hql转换sql
			java.util.regex.Pattern p= java.util.regex.Pattern.compile("[A-Z]");
			java.util.regex.Matcher m = p.matcher(hql.toString());
			String sql =hql.toString();
			while(m.find()){
				String lowChars=m.group().toLowerCase();
				String preChar=sql.substring(m.start()-1, m.start()).trim();
				if(m.start()>0&&!"".equals(preChar)&&!",".equals(preChar)&&!")".equals(preChar)){
					lowChars="_"+lowChars;
				}
				sql = m.replaceFirst(lowChars);
				m  = p.matcher(sql);
			}
			sql = sql.replaceAll("%_y%m%d %_h:%i:%s", "%Y%m%d %H:%i:%s");
			sql = sql.replaceAll("yyyy-_m_m-dd", "yyyy-mm-dd");
			Query query = em.createNativeQuery(sql);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			List objList = query.getResultList();
			if (null != objList && !objList.isEmpty()) {
				Object[] obj = null;
				try {
					obj = (Object[]) objList.get(0);
				} catch (Exception e) {
					obj = new Double[]{(Double)objList.get(0)};
				}
				page.setSummaryMap(getCountAndSumValueToPage(obj, page));
			}
		}
	}
	
	/**
	 * 把map组装成合计的hql语句
	 * @param map Map<key=列名, value=属性表达式>
	 * @return hql语句
	 */
	public String mapToHql(Map<String, String> map) {
		return this.mapToColumn(map, true);
	}
	
	/**
	 *  把map组装成hql语句
	 * @param map Map<key=列名, value=属性表达式>
	 * @return hql语句
	 */
	private String mapToColhql(Map<String, String> map){
		return this.mapToColumn(map, false);
	}
	
	/**
	 *  把map组装成语句
	 * @param map map Map<key=列名, value=属性表达式>
	 * @param isSum true转换为合计 false 不合计 
	 * @return
	 */
	private String mapToColumn(Map<String, String> map,boolean isSum){
		String s = "";
		if (null != map && !map.isEmpty()) {
			for (String name : map.keySet()) {
				if (StringUtil.isNotBlank(name)) {
					if (0 < s.length()) {
						s += ",";
					}
					String value = map.get(name);
					if (StringUtil.isNotBlank(value)) {
						if(isSum){
							s += " sum(" + value + ") as "+name+" ";
						}else{
							s += " " + value + " ";
						}
					} 
				}
			}
		}
		return s;
	}
	
	/**
	 * 更新语句
	 * @param hql
	 * @param args 参数
	 * @return 更新记录数
	 */
	public int executeUpdate(String hql, Object... args) {
		Query q = em.createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		int i = q.executeUpdate();
		return i;
	}
	
	/**
	 * 获取查询数据所有记录
	 * @param queryString 查询语句
	 * @return 记录数
	 */
	@SuppressWarnings("unchecked")
	public int getResultCount(String queryString) {
		/*
		 * String countQueryString = " select count (*) " +
		 * removeSelect(removeOrders(queryString)); List countlist =
		 * find(countQueryString); int count = 0; if (countlist.size() == 1)
		 * count = ((Long) countlist.get(0)).intValue(); else count =
		 * countlist.size(); return count;
		 */
		String hqlstr = removeOrders(queryString);
		if (this.hasDistinctOrGroupBy(hqlstr)) {
			List lt = find(hqlstr);
			return lt == null ? 0 : lt.size();
		} else {
			String countQueryString = " select count (*) " + removeSelect(removeOrders(queryString));
			List countlist = find(countQueryString);
			int count = 0;
			if (countlist.size() == 1) {
				count = ((Long) countlist.get(0)).intValue();
			} else {
				count = countlist.size();
			}
			return count;
		}
	}
	
	private List find(String hql) {
		Query query = em.createQuery(hql);
		return query.getResultList();
	}
	
	private List find(String hql, Object... args) {
		Query query = em.createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.getResultList();
	}
	
	private String removeSelectLastFrom(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().lastIndexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	private String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	private boolean hasDistinctOrGroupBy(String str) {
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		p = Pattern.compile("distinct ", Pattern.CASE_INSENSITIVE);
		m = p.matcher(str);
		return m.find();
	}
	
	private static String removeGroupBy(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private boolean hasGroupByHaving(String str) {
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]* having ", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
