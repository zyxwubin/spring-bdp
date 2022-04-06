package org.bdp.glodal.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bdp.glodal.common.superclass.SuperBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HqlUtil {
	/** 数据库类型 orcale  */
	public static String dataType = "orcale";
	public static final String DATA_ORCALE = "orcale";
	public static final String DATA_SQL = "sql_server";
	public static final String DATA_MYSQL = "mysql";
	/**等于*/
	public static final String EXP_EQUALES="=";   
	/**大于符号*/
	public static final String EXP_MORE = ">";
	/**大于等于符号*/
	public static final String EXP_GE = ">=";
	/**等于符号*/
	public static final String EXP_EE = "=";
	/**小于*/
	public static final String EXP_LESS = "<";
	/**小于等于*/
	public static final String EXP_GL = "<=";
	
	private static SessionFactory sessionFactory;
	
	public static Session getSession(){
        return getSessionFactory().getCurrentSession();
    }
	
	/**
     * SessionFactory是重量级的
     * 最好做成单例模式
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory(){
        //保证SessionFactory为单例
        if (sessionFactory == null ||sessionFactory.isClosed()) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
	
	/**
	 * 处理HQL里对空的默认值处理
	 * @param column 字段名称
	 * @param def 默认值 数字类型 "0"，字符串类型 " '0' "
	 * @return
	 */
	public static String emptyToHql(String column, String def) {
		String hql = "";
		if (DATA_ORCALE.equals(dataType)) {
			hql = " nvl(" + column + "," + def + ") ";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " isnull(" + column + "," + def + ") ";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " coalesce(" + column + "," + def + ") ";
		}
		return hql;
	}
	/**
	 * 是否是orcale数据库
	 * @return
	 */
	public static boolean checkOracle(){
		if (DATA_ORCALE.equals(dataType)) {
			return true;
		}
		return false;
	}
	
	public static String getFormatDateString(String column, String format){
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(format)) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = "to_char("+column+", '"+format+"')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = column;
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = "date_format("+column+", '"+format+"')";
		}
		return hql;
	}

	/**
	 * 查询日期的转换,直接根据字段查询
	 * @param column
	 * @param expression
	 * @param date
	 * @return
	 */
	public static String getQueryDateByField(String column, String expression, String date) {
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || StringUtils.isEmpty(date)) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = column + expression + " to_date('" + date + "','yyyy-mm-dd hh24:mi:ss')";
		} else{

		}
		return hql;
	}

	/**
	 * 查询日期的转换
	 * @param column
	 * @param expression
	 * @param date
	 * @return
	 */
	public static String getqueryDateString(String column, String expression, String date) {
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || StringUtils.isEmpty(date)) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymmdd hh24:mi:ss') " + expression + " to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT)
					+ "','yyyy-mm-dd hh24:mi:ss'),'yyyymmdd hh24:mi:ss')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = column +" " + expression + " "+date;
		}else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y%m%d %H:%i:%s') " + expression + " date_format('" + DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT)
			+ "','%Y%m%d %H:%i:%s')";
		}else{
			
		}
		return hql;
	}

	/**
	 * 查询日期的转换 %Y%m%d %h:%i:%s'
	 * @param column
	 * @param expression
	 * @param date
	 * @return
	 */
	public static String getqueryDateString(String column, String expression, Date date) {
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymmdd hh24:mi:ss') " + expression + " to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT)
					+ "','yyyy-mm-dd hh24:mi:ss'),'yyyymmdd hh24:mi:ss')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = column+" " + expression + " "+DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT);
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y%m%d %H:%i:%s') " + expression + " date_format('" + DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT)
			       + "','%Y%m%d %H:%i:%s')";
		}
		return hql;
	}
	
	/**
	 * 查询日期的转换
	 * @param column
	 * @param expression
	 * @param date
	 * @return
	 */
	public static String getqueryDateStringToDate(String column, String expression, String date) {
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymmdd') " + expression + " to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyymmdd')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = column+" " + expression + " "+DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT);
		}else if(DATA_MYSQL.equals(dataType)){
			hql = " date_format(" + column + ",'%Y%m%d') " + expression + " date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m%d')";
		}
		return hql;
	}
	
	/**
	 * 查询日期的转换
	 * @param column
	 * @param expression
	 * @param date
	 * @return
	 */
	public static String getqueryDateStringToDate(String column, String expression, Date date) {
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymmdd') " + expression + " to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyymmdd')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = column+" " + expression + " "+DateUtil.getDateString(date, DateUtil.DATETIMESHOWFORMAT);
		}else if(DATA_MYSQL.equals(dataType)){
			hql = " date_format(" + column + ",'%Y%m%d') " + expression + " date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m%d')";
		}
		return hql;
	}
	
	/**
	 * 获取查询本年的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryYear(String column, String date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyy') = to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyy')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " DATEDIFF(yyyy, "+column+", "+date+") = 0";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y') = date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y')";
		}
		return hql;
	}
	
	/**
	 * 获取查询本年的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryYear(String column, Date date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyy') = to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyy')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " DATEDIFF(yyyy, "+column+", "+date+") = 0";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y') = date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y')";
		}
		return hql;
	}
	
	/**
	 * 获取查询本周的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryWeek(String column, String date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyy-mm-dd') >= to_char(TRUNC(NEXT_DAY(to_date('" + date + "', 'yyyy-mm-dd')-8,1)+1),'yyyy-mm-dd')"
					+" and to_char(" + column + ",'yyyy-mm-dd') <= to_char(TRUNC(NEXT_DAY(to_date('" + date + "', 'yyyy-mm-dd')-8,1)+7),'yyyy-mm-dd')";
		} else if (DATA_SQL.equals(dataType)) {
			
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " YEARWEEK(date_format(" + column + ",'%Y%m%d')) = YEARWEEK(date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m%d'))";
	}
		return hql;
	}
	
	/**
	 * 获取查询本周的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryWeek(String column, Date date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyy-mm-dd') >= to_char(TRUNC(NEXT_DAY(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "', 'yyyy-mm-dd')-8,1)+1),'yyyy-mm-dd')"
				+" and to_char(" + column + ",'yyyy-mm-dd') <= to_char(TRUNC(NEXT_DAY(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "', 'yyyy-mm-dd')-8,1)+7),'yyyy-mm-dd')";
		} else if (DATA_SQL.equals(dataType)) {
			
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " YEARWEEK(date_format(" + column + ",'%Y%m%d')) = YEARWEEK(date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m%d'))";
		}
		return hql;
	}
	
	/**
	 * 获取查询本月的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryMonth(String column, String date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymm') = to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyymm')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " DATEDIFF(mm, "+column+", "+date+") = 0";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y%m') = date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m')";
		}
		return hql;
	}
	
	/**
	 * 获取查询本月的语句
	 * @param column 列名
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryMonth(String column, Date date){
		String hql = "";
		if (StringUtils.isEmpty(column) || null == date) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymm') = to_char(to_date('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','yyyy-mm-dd'),'yyyymm')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " DATEDIFF(mm, "+column+", "+date+") = 0";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y%m') = date_format('" + DateUtil.getDateString(date, DateUtil.DATESHOWFORMAT) + "','%Y%m')";
		}
		return hql;
	}
	
	/**
	 * 获取按月查询的语句
	 * @param column 列名
	 * @param expression 表达式
	 * @param date 日期
	 * @return 语句
	 */
	public static String getqueryDateByMonth(String column, String expression, String date){
		String hql = "";
		if (StringUtils.isEmpty(column) || StringUtils.isEmpty(expression) || StringUtils.isEmpty(date)) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyymm') " + expression + " to_char(to_date('" + date + "','yyyy-mm'),'yyyymm')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " convert(char(7), " + column + ",120)" + expression + date;
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y%m') " + expression + " date_format('" + date + "','%Y%m')";
		}
		return hql;
	}
	
	/**
	 * 获取按年月汇总的语句
	 * @param column 列名
	 * @return 语句
	 */
	public static String getGroupbyYearAndMonth(String column){
		String hql = "";
		if (StringUtils.isEmpty(column)) {
			return hql;
		}
		if (DATA_ORCALE.equals(dataType)) {
			hql = " to_char(" + column + ",'yyyy-mm')";
		} else if (DATA_SQL.equals(dataType)) {
			hql = " convert(char(7), " + column + ",120)";
		} else if (DATA_MYSQL.equals(dataType)) {
			hql = " date_format(" + column + ",'%Y-%m')";
		}
		return hql;
	}
	
	/**
	 * 保存日期的转换
	 * @param date
	 * @param patternString
	 * @return
	 */
	public static String getinsertDateString(Date date,String patternString) {
		String hql = "";
		if (DATA_ORCALE.equals(dataType)) {
			String format = DateUtil.DATESHOWFORMAT;
			if (patternString.trim().length() > 10) {
				format = DateUtil.DATETIMESHOWFORMAT;
			}
			hql = " to_date('" + DateUtil.getDateString(date, format) + "','"+patternString+"') " ;
		} else if (DATA_SQL.equals(dataType)) {
			hql = "'"+date+"'";
		}
		return hql;
	}
	public static String getDatePatternString(){
		String hql = "";
		if (DATA_ORCALE.equals(dataType)) {
			hql = "yyyy-mm-dd hh24:mi:ss" ;
		} else if (DATA_SQL.equals(dataType)) {
			hql = "yyyy-mm-dd hh24:mi:ss" ;
		}
		return hql;
	}
	/** hql大写语句
	 * @param column 
	 * @return
	 */
	public static String getUpper(String column) {
		String hql = "";
		if (DATA_ORCALE.equals(dataType)) {
			hql = "upper(" + column + ")";
		} else if (DATA_SQL.equals(dataType)) {
			hql = "upper(" + column + ")";
		}else if (DATA_MYSQL.equals(dataType)) {
			hql = "upper(" + column + ")";
		}
		return hql;
	}
	/**
	 * 把对象超类里的属性转成更新的hql语句(a=?,b=?,c=?)
	 * @param prefix 前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到updte的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return 更新的hql语句(set部分)
	 */
	public static String getModleSupperToUpdateHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjOpSupperProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field,prefix, obj, map, notMap, args,",",blank);
			}
		}
		return hql;
	}
	/**
	 * 把对象实现类里的属性转成更新的hql语句(a=?,b=?,c=?)
	 * @param prefix 前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到updte的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return 更新的hql语句(set部分)
	 */
	public static String getModleToUpdateHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field, prefix,obj, map, notMap, args,",",blank);
			}
		}
		return hql;
	}
	/**
	 * 把对象所有(包括祖先类和实现类)里的属性转成更新的hql语句(a=?,b=?,c=?)
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到updte的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return 更新的hql语句(set部分)
	 */
	public static String getModleAllToUpdateHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjAllOpProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field, prefix,obj, map, notMap, args,",",blank);
			}
		}
		return hql;
	}
	/**
	 * 把对象超类里的属性转成where的hql语句(a=? and b=? and c=?)
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return where的hql语句
	 */
	public static String getModleSupperToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		return getModleSupperToWhereHql(prefix, obj, map, notMap, args, blank, null,null);
	}
	/**
	 * 把对象超类里的属性转成where的hql语句(a=? and b=? and c=?)
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @param prefixMap 前缀配置(有多个前缀的时候.这里可以配置对应的列,没有配置的取第一个参数,key=查询bean里的属性名,value=对应的前缀)
	 * @return where的hql语句
	 */
	public static String getModleSupperToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank,Map<String,String> prefixMap,Map<String,String> likeColMap){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjOpSupperProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field,prefix, obj, map, notMap, args," and ",blank,prefixMap,likeColMap);
			}
		}
		return hql;
	}
	/**
	 * 把对象实现类里的属性转成where的hql语句(a=? and b=? and c=?) 
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return where的hql语句
	 */
	public static String getModleToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		return getModleToWhereHql(prefix, obj, map, notMap, args, blank, null,null);
	}
	/**
	 * 把对象实现类里的属性转成where的hql语句(a=? and b=? and c=?) 
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @param prefixMap 前缀配置(有多个前缀的时候.这里可以配置对应的列,没有配置的取第一个参数)
	 * @return where的hql语句
	 */
	public static String getModleToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank,Map<String,String> prefixMap,Map<String,String> likeColMap){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field,prefix, obj, map, notMap, args," and ",blank,prefixMap,likeColMap);
			}
		}
		return hql;
	}
	/**
	 * 把对象所有(包括祖先类和实现类)属性转成where的hql语句(a=? and b=? and c=?)
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @return where的hql语句
	 */
	public static String getModleAllToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank){
		return getModleAllToWhereHql(prefix, obj, map, notMap, args, blank, null,null);
	}
	/**
	 * 把对象所有(包括祖先类和实现类)属性转成where的hql语句(a=? and b=? and c=?)
	 * @param prefix 默认前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需处理的属性(不需要放到where的语句里)<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param blank 空或""需不需要转化 true 需要 false 不需要
	 * @param prefixMap 前缀配置(有多个前缀的时候.这里可以配置对应的列,没有配置的取第一个参数)
	 * @return where的hql语句
	 */
	public static String getModleAllToWhereHql(String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank,Map<String,String> prefixMap,Map<String,String> likeColMap){
		String hql="";
		if(null!=args){
			Field[] field=BeanUtil.getObjAllOpProperty(obj);
			if(null!=field){
				hql=getFieldToHql(field, prefix,obj, map, notMap, args," and ",blank,prefixMap,likeColMap);
			}
		}
		if(null!=obj){
			try {
				String menuQueryHql = BeanUtil.getProperty(obj, "menuQueryHql");
				if(StringUtil.isNotBlank(menuQueryHql)){
					if(StringUtil.isNotBlank(hql)){
						hql+=" and ";
					}
					hql+="("+menuQueryHql+")";
				}
			} catch (Exception e) {
			}
		}
		return hql;
	}
	/**
	 * 
	 * @param field
	 * @param prefix 前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需要转的属性<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param split 两个属性之间的链接符号(, and )
	 * @return
	 */
	private static String getFieldToHql(Field[] field,String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,String split,boolean blank){
		return getFieldToHql(field, prefix, obj, map, notMap, args, split, blank, null,null);
	}
	/**
	 * 
	 * @param field
	 * @param prefix 前缀
	 * @param obj 数据对象
	 * @param map 特殊属性的转化可以定义实现 <key='属性名称' value='属性名称的值用?代替(a=a+?)'> 
	 * @param notMap 不需要转的属性<key='属性名称' value='属性名称'>
	 * @param args 返回参数
	 * @param split 两个属性之间的链接符号(, and )
	 * @return
	 */
	private static String getFieldToHql(Field[] field,String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,String split,boolean blank,Map<String,String> prefixMap,Map<String,String> likeColMap){
		String hql="";
		if(null!=args&&null!=field){
			for (Field field2 : field) {
				if(!"serialVersionUID".equals(field2.getName())){
					String nameHql=getFieldNameToHql(field2,prefix, obj, map, notMap, args,blank,prefixMap,likeColMap);
					if(StringUtil.isNotBlank(nameHql)){
						if(StringUtil.isNotBlank(hql)){
							hql+=split;
						}
						hql+=nameHql;
					}
				}
			}
		}
		return hql;
	}
	/**
	 * @param field
	 * @param prefix
	 * @param obj
	 * @param map
	 * @param notMap
	 * @param args
	 * @param blank //是否要转换空的对象,true 需要 false 不需要
	 * @return 
	 */
	private static String getFieldNameToHql(Field field,String prefix,Object obj,Map<String,String> map,Map<String,String> notMap,List<Object> args,boolean blank,Map<String,String> prefixMap,Map<String,String> likeColMap){
		String hql="";
		String name=field.getName();
		String column=field.getName();
		String keyValue="";
		if(null!=notMap){
			keyValue=notMap.get(name);//先检测是否不需要转换
		}
		if(null!=prefixMap&&!prefixMap.isEmpty()){
			String mapp=prefixMap.get(name);//先获取有没有配置对应的前缀
			if(StringUtil.isNotBlank(mapp)){
				prefix=mapp;
			}
		}
		if(StringUtil.isNotBlank(prefix)){
			name=prefix+"."+name;
		}
		if(StringUtil.isBlank(keyValue)){//防止map里增加了前缀
			if(null!=notMap){
				keyValue=notMap.get(name);//先检测是否不需要转换
			}
		}
		if(StringUtil.isBlank(keyValue)){
			Object objValue=BeanUtil.getObjValue(obj, field.getName(), null);
			if(blank||(null!=objValue&&StringUtil.isNotBlank(objValue.toString()))){
				String confHql="";//配置信息
				if(null!=map){
					confHql=map.get(column);
				}
				String exp="=";
				if(null!=likeColMap&&!likeColMap.isEmpty()){
					if(StringUtil.isNotBlank(likeColMap.get(column))){
						objValue="%"+objValue+"%";
						exp=" like ";
					}
				}
				if(StringUtil.isNotBlank(confHql)){
					if((null==objValue||"".equals(objValue))){
						confHql.replaceAll("?", "null");
						hql+=confHql;
					}else{
						confHql=confHql.replaceAll("=", exp);
						hql+=confHql;
						int len =StringUtil.indexOfAll(confHql, "?");
						for (int i = 0; i < len; i++) {
							args.add(objValue);
						}
					}
				}else{
					if((null==objValue||"".equals(objValue))){
						hql+=name+"=null";
					}else{
						hql+=name+" "+exp+" ? ";
						args.add(objValue);
					}
				}
			}
		}
		return hql;
	}
	/**得到模糊的hql
	 *@param queryBean 查询bean
	 * @param likeMap 需要模糊的map Map<column, upper> column-模糊的列,upper 是否区分大小写（值为"true"不区分不区分alse"区分）区分	 
	 * @param likeMap 不需要查询的列
	 * @param perfix 前缀
	 * @param params 参数
	 * @return
	 */
	public static String getLikeHql(SuperBean queryBean, Map<String, String> likeMap, Map<String, String> notMap, String perfix, List<Object> params) {
		boolean upper = false;// 是否区分大小写 true不区分
		String hql = "";
		if (null != likeMap && !likeMap.isEmpty()) {
			for (String column : likeMap.keySet()) {
				if (StringUtil.isNotBlank(column)) {
					String value = (String) BeanUtil.getNameProperty(queryBean, column);
					if (StringUtil.isNotBlank(value)) {
						if (StringUtil.isNotBlank(hql)) {
							hql+= " and ";
						}
						try {
							upper = Boolean.valueOf(likeMap.get(column));
						} catch (Exception e) {
						}
						if (upper) {
							hql+= HqlUtil.getUpper("a." + column) + " like ? ";
						} else {
							hql+= "a." + column +  " like ? ";
						}
						if (null == params) {
							params = new ArrayList<Object>();
						}
						params.add("%" +value.trim().toUpperCase() +  "%");
						if (null == notMap) {
							notMap = new HashMap<String, String>();
						}
						notMap.put(column, "1=2");
					}
				}
			}
		}
		return hql;
	}
	
	/** 获取规格系数的语句
	 * @param prefix 前缀
	 * @param obj 查询对象
	 * @param queryMap 查询字段map
	 * @param columnMap 查询bean对应的数据库字段map
	 * @param notMap 不需要转的属性<key='属性名称' value='属性名称'>
	 * @param prefixMap 另外前缀处理的map
	 * @return String
	 */
	public static String getSpecRange(String prefix,Object obj, Map<String,String> queryMap, Map<String,String> columnMap ,Map<String,String> notMap, Map<String,String> prefixMap){
		String hql = "";
		if (null != queryMap && !queryMap.isEmpty()) {
			for (String key : queryMap.keySet()) {
				String queryColumn = queryMap.get(key);
				if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(queryColumn)) {
					// 查询的值
					Object value = BeanUtil.getNameProperty(obj, queryColumn);
					notMap.put(queryColumn, "1=2");
					if (null != value) {
						String column = columnMap.get(queryColumn);
						if (StringUtil.isNotBlank(column)) {
							if (StringUtil.isNotBlank(hql)) {
								hql += " and ";
							}
							hql +=  getRangeHql(value, key, column, prefix, prefixMap);
						}
					}
				}
			}
		}
		return hql;
	}
	/**  根据值前缀获取范围查询的hql
	 * @param key 查询字段 eg goodsSpec1min, goodsSpec1max
	 * @param value 属性值
	 * @param column 需要查询的字段
	 * @param prefix 前缀
	 * @param prefixMap 前缀map
	 * @return String
	 */
	private static String getRangeHql(Object value, String key, String column, String prefix, Map<String, String> prefixMap) {
		String hql = "";
		if (null != prefixMap && !prefixMap.isEmpty()) {
			String pre =  prefixMap.get(column);
			if(StringUtil.isNotBlank(pre)){
				prefix =pre;// 获取前缀
			}
		}
		hql = prefix + "." + column  + (key.endsWith("min")?HqlUtil.EXP_GE:HqlUtil.EXP_GL) + Double.valueOf(value.toString()); 
		return hql;
	}
	/** 截取字符串
	 * @param column 字段名称
	 * @param start 开始位置
	 * @param end 结束位置
	 * @return String
	 */
	public static String getSubstrHql(String column, long start, long end) {// 计算科目代码级别
		String hql = "";
		if (HqlUtil.DATA_ORCALE.equals(HqlUtil.dataType)) {
			hql = "substr("+column+","+start+","+end+")";
		} else if (HqlUtil.DATA_SQL.equals(HqlUtil.dataType)) {
			hql = "substring("+column+","+start+","+end+")";
		}else if (HqlUtil.DATA_MYSQL.equals(HqlUtil.dataType)) {
			hql = "substring("+column+","+start+","+end+")";
		}
		return hql;
	}
	/**
	 * 处理分组小计
	 * @param list
	 * @param page
	 * @param str
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkNeedGroup(List list, PageTools page, String prexStr){
		boolean flag = false;
		if(null!=list&&!list.isEmpty()&&null!=page&&StringUtil.isNotBlank(page.getGroup())){
			if(1<page.getPageCount()){
				String hql = "";
				List<Object> params = new ArrayList<Object>();
				String fieldName = page.getGroup();
				if(StringUtil.isNotBlank(prexStr)){
					fieldName = prexStr + "." + page.getGroup();
				}
				if(1 == page.getPageNo()){
					//第一页取最后条记录
					Object obj = list.get(list.size()-1);
					Object objValue=BeanUtil.forceGetProperty(obj, page.getGroup());
					hql = " and " + fieldName + " = ?";
					params.add(objValue);
				}else if(page.getPageCount() == page.getPageNo()){
					//最后一页取第一条记录
					Object obj = list.get(0);
					Object objValue=BeanUtil.forceGetProperty(obj, page.getGroup());
					hql = " and " +fieldName + " = ?";
					params.add(objValue);
				}else{
					//中间
					Object obj = list.get(0);
					Object objValue=BeanUtil.forceGetProperty(obj, page.getGroup());
					obj = list.get(list.size()-1);
					Object objValue2=BeanUtil.forceGetProperty(obj, page.getGroup());
					hql = " and (" + fieldName + " = ? or " + fieldName + " = ?)";
					params.add(objValue);
					params.add(objValue2);
				}
				flag=true;
				page.setNeedGroup(true);
				page.setOtherGroupHql(hql);
				page.setOtherGroupParams(params);
			}
		}
		return flag;
	}
	
	/** 检测查询语句是否符合规范
	 * @param hqlStr 查询语句  
	 * @return true 规范 false 不规范
	 */
	public static String checkHql(String hqlStr) {
		String msg = "";
		if (hqlStr.toLowerCase().contains("update") || hqlStr.toLowerCase().contains("insert") || hqlStr.toLowerCase().contains("delete")
				|| hqlStr.toLowerCase().contains("drop") || hqlStr.toLowerCase().contains("creat") || hqlStr.toLowerCase().contains("execute")
				|| hqlStr.toLowerCase().contains("alter") || hqlStr.toLowerCase().contains("truncate") ) {
			msg = "语句不正确，不能包含update,insert,delete,drop,creat,execute,truncate,alter";
		}
		return msg;
	}
}