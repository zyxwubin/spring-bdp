package org.bdp.basic.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-4-10
 * Function: 
 * Change Log:
**************************************/	
public class DoubleUtil {	
	/**数量的精度*/
	public static final int PRECISION_NUM=0;
	/**重量的精度*/
	public static final int PRECISION_WEIGHT = 4;
	/**含税单价的精度*/
	public static final int PRECISION_PRICE = 6;
	/**金额的精度 */
	public static final int PRECISION_MONEY = 2;
	/**无税单价的精度 */
	public static final int PRECISION_EXPRICE = 6;
	/**系统double最大值（25,8） */
	public static final String MAX_VALUE = "100000000000000000";
	
	/** 数量的格式*/
	public static final String FORMAT_NUM = "###,##0";
	/** 重量的格式*/
	public static final String FORMAT_WEIGHT = "###,##0.0000";
	/** 含税单价的格式*/
	public static final String FORMAT_INPRICE = "###,##0.000000";
	/** 金额的格式 */
	public static final String FORMAT_MONEY = "###,##0.00";
	/** 无税单价的格式 */
	public static final String FORMAT_EXPRICE = "###,##0.000000";
	
	/**
	 * 转换成字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String convertToString(Double d) {
		if (null == d) {
			d = 0D;
		}
		String f = "";
		for (int i = 0; i < String.valueOf(d).length(); i++) {
			f = f + "#";
		}
		DecimalFormat df = new DecimalFormat(f);
		String s = df.format(d);
		return s;
	}
	/**
	 * 按指定格式转换成字符串
	 * @param d
	 * @param format 格式
	 * @return
	 */
	public static String convertToString(Double d, String format) {
		if (null == d) {
			d = 0D;
		}
		DecimalFormat df = new DecimalFormat(format);
		String s = df.format(d);
		return s;
	}

	public static String convertDoubleFormat(Double d, String f) {
		if (null == d) {
			d = 0D;
		}
		DecimalFormat df = new DecimalFormat(f);
		String s = df.format(d);
		return s;
	}
	
	public static String convertNumFormat(Double d, String f) {
		if(StringUtils.isBlank(f)){
			f="###,##0";
			int decimalPrecision=PRECISION_NUM;
			if(decimalPrecision>0){
				f=f+"."+StringUtils.repeat("0", decimalPrecision);
			}
		}
		return convertDoubleFormat(d,f);
	}
	
	
	public static String convertWeightFormat(Double d, String f) {
		if(StringUtils.isBlank(f)){
			f="###,##0";
			if(PRECISION_WEIGHT>0){
				f=f+"."+StringUtils.repeat("0", PRECISION_WEIGHT);
			}
		}
		return convertDoubleFormat(d,f);
	}
	
	/**
	 * 转换成字符串固定位数,不足的补在右边设置的字符
	 * 
	 * @param d
	 * @param n
	 * @return
	 */
	public static String convertRToString(Double d, String rchar, int n) {
		if (null == d) {
			d = 0D;
		}
		String f = "";
		for (int i = 0; i < n; i++) {
			f = f + rchar;
		}
		DecimalFormat df = new DecimalFormat(f);
		String s = df.format(d);
		return s;
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(Double v, int scale) {
		if (null == v) {
			v = 0D;
		}
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(String v, int scale) {
		if (null == v) {
			v = "0";
		}
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 浮点数 加 精确计算 a+b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double preciseAdd(Double a, Double b) {
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.add(new BigDecimal(Double.toString(b)));
		return r.doubleValue();
	}

	/**
	 * 浮点数 加 小数位四舍五入精确计算 a+b
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 *            精确的小数位
	 * @return
	 */
	public static double preciseAdd(Double a, Double b, int scale) {
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.add(new BigDecimal(Double.toString(b)));
		return DoubleUtil.round(r.doubleValue(), scale);
	}

	/**
	 * 浮点数 减 精确计算 a-b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double preciseSub(Double a, Double b) {
		int scale=8;
		return preciseSub(a, b, scale);
	}

	/**
	 * 浮点数 减 小数位四舍五入精确计算 a-b
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 *            精确的小数位
	 * @return
	 */
	public static double preciseSub(Double a, Double b, int scale) {
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.subtract(new BigDecimal(Double.toString(b)));
		return DoubleUtil.round(r.doubleValue(), scale);
	}

	/**
	 * 浮点数 乘 精确计算 a*b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double preciseMul(Double a, Double b) {
		if (null == b || null == a) {
			return 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.multiply(new BigDecimal(Double.toString(b)));
		return r.doubleValue();
	}

	/**
	 * 浮点数 乘 小数位四舍五入精确计算 a*b
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 *            精确的小数位
	 * @return
	 */
	public static double preciseMul(Double a, Double b, int scale) {
		if (null == b || null == a) {
			return 0D;
		}
		return DoubleUtil.round(new BigDecimal(Double.toString(a)).multiply(
				new BigDecimal(Double.toString(b))).doubleValue(), scale);
	}

	/**
	 * 浮点数 除 精确计算 a/b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double preciseDev(Double a, Double b) {
		if (null == b || b == 0D || null == a) {
			return 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.divide(new BigDecimal(Double.toString(b)), 6,
				BigDecimal.ROUND_HALF_UP);
		return r.doubleValue();
	}

	
	public static double getMax(Double a, Double b) {
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		Double re=a;
		if(a<b){
			re=b;
		}
		return re;
	}
	public static double getMin(Double a, Double b) {
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		Double re=a;
		if(a>b){
			re=b;
		}
		return re;
	}
	/**
	 * 比较两数大小（a是否大于b）
	 * @param a
	 * @param b
	 * @return true 大于 false 小于
	 */
	public static boolean expMore(Double a, Double b) {
		boolean is = false;
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		if(a>b){
			is = true;
		}
		return is;
	}
	/**
	 * 比较两数大小（a是否大于等于b）
	 * @param a
	 * @param b
	 * @return true 大于 false 小于
	 */
	public static boolean expGe(Double a, Double b) {
		boolean is = false;
		if (null == b ) {
			b= 0D;
		}
		if (null == a ) {
			a= 0D;
		}
		if(a.doubleValue()>=b){
			is = true;
		}
		return is;
	}
	/**
	 * 浮点数 除 小数位四舍五入精确计算 a/b
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 *            精确的小数位
	 * @return
	 */
	public static double preciseDev(Double a, Double b, int scale) {
		if (null == b || b == 0D || null == a) {
			return 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.divide(new BigDecimal(Double.toString(b)), scale,
				BigDecimal.ROUND_HALF_UP);
		return r.doubleValue();
	}

	/**
	 * 浮点数 除 a/b 正数:最后一位大于0,则向前进一位;负数:舍弃
	 * @param a
	 * @param b
	 * @param scale 精确的小数位
	 * @return
	 */
	public static double preciseDevCy(Double a, Double b, int scale) {
		if (null == b || b == 0D || null == a) {
			return 0D;
		}
		BigDecimal r = new BigDecimal(Double.toString(a));
		r = r.divide(new BigDecimal(Double.toString(b)), scale,
				BigDecimal.ROUND_CEILING);
		return r.doubleValue();
	}
	
	public static String addDouble(String d1, String d2, boolean t) {
		if (isNumber(d1) && isNumber(d2)) {
			d1 = new BigDecimal(d1).add(new BigDecimal(d2)).toString();
		} else if (isNumber(d2)) {
			d1 = d2;
		}
		if (t && d1.indexOf(".") > 0) {
			d1 = d1.substring(0, d1.indexOf("."));
		}
		return d1;
	}

	public static boolean isNumber(String obj) {
		if(null==obj){
			obj="";
		}
		return obj.matches("-?\\d+\\.?\\d*");
	}
	
	/**
	 * 数字小数位是否与length长度一致
	 * @param obj
	 * @param length
	 * @return
	 */
	public static String decimalLen(Double obj, int length, String msg){
		if(null==obj||obj.doubleValue()==0){
			return "";
		}
		String str = convertNumFormat(obj, "###,###.00000000");
	    String[] num = str.split("\\.");
	    int len=0;
	    if (num.length == 2) {
	        for (;;){
	            if (num[1].endsWith("0")) {
	                num[1] = num[1].substring(0, num[1].length() - 1);
	            }else {
	                break;
	            }
	        }
	        len=num[1].length();
	    }
		if(len<=length){
			return "";
		}
		String f="###,###."+StringUtil.filRight("", '0', len);
		return msg+"["+convertNumFormat(obj, f)+"]小数位不正确,";
	}
	
	/**
	 * 检测obj是否是大于0
	 * @param obj
	 * @return true 是大于 0 false 小于0
	 */
	public static boolean isNotBlank(Double obj){
		if(null==obj||obj.doubleValue()<=0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 检测obj是否是小于或等于0
	 * @param obj
	 * @return true 小于或等于 0 false 不小于或等于0
	 */
	public static boolean isBlank(Double obj){
		if(null==obj||obj.doubleValue()<=0){
			return true;
		}else{
			return false;
		}
	}
	public static String isBlankToMsg(Double obj,String msg){
		String returnstr="";
		if (DoubleUtil.isBlank(obj)) {
			returnstr=msg+",";
		}
		return returnstr;
	}
	/**
	 * 检测obj是否不为空或者不为0
	 * @param obj
	 * @return true 是  false 不是
	 */
	public static boolean isNotEmpty(Double obj){
		if(null==obj||obj.doubleValue()==0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检测obj是否为空或者为0
	 * @param obj
	 * @returntrue 是  false 不是
	 */
	public static boolean isEmpty(Double obj){
		if(null==obj||obj.doubleValue()==0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 比较两个数字是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(Double a,Double b){
		boolean flag=false;
		if(null==a){
			a=0D;
		}
		if(null==b){
			b=0D;
		}
		if(a.equals(b)){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 取绝对值
	 * @param a
	 * @return
	 */
	public static Double abs(Double a){
		if(null==a){
			a=0D;
		}
		return Math.abs(a);
	}
	
	public static void main(String[] args){
		
	}
}
