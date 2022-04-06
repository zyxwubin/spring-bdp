package org.bdp.glodal.util;

import java.util.HashMap;
import java.util.Map;

/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-3-7
 * Create Author: goldensoft
 * Last version:  1.0
 * Function:把对象转成json格式
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class JsonUtil {
	/**
	 * 把对象转成json格式
	 * @param obj 需要转的对象
	 * @param args 暂时不用
	 * @return String
	 */
//	public static String jsonObject(Object obj,String...args){
//		JsonConfig jc = new JsonConfig();
//		jc.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
//		if (null != args) {
//			for (String string : args) {
//				jc.registerJsonValueProcessor(string, new JsonValueProcessorImpl());
//			}
//		}
//		return JSONArray.fromObject(obj,jc).toString();
//	}	
	/**
	 * 把json格式的数据 转成java bean 对象
	 * @param obj
	 * @param arg1 对象class
	 * @param map 字段类型的说明 <key=字段名,value=对象class(是自定义对象或是list对象里面的自定义对象)>
	 * @return object
	 */
	@SuppressWarnings("unchecked")
//	public static Object jsonToBean(Object obj,Class arg1,Map<String,Class> map){
//		Object retObj=null;
//		if(null!=map&&!map.isEmpty()){
//			try{
//				JSONObject jsonPerson = JSONObject.fromObject(obj);
//				setJsonToBeanDateFormat();
//				retObj = JSONObject.toBean(jsonPerson,arg1,map); 
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}else{
//			retObj=jsonToBean(obj, arg1);
//		}
//		return retObj;
//	}
	
	/**
	 * 把json格式的数据 转成java bean 对象
	 * @param obj 对象class
	 * @param arg1
	 * @return object
	 */
//	@SuppressWarnings("unchecked")
//	public static Object jsonToBean(Object obj,Class arg1){
//		Object retObj = null;
//		try{
//			JSONObject jsonPerson = JSONObject.fromObject(obj);
//			setJsonToBeanDateFormat();
//			retObj = JSONObject.toBean(jsonPerson,arg1); 
//		}catch(Exception e){
//			
//		}
//		return retObj;
//	}
	
	/**
	 * 把json格式的数据 转成java list 对象
	 * @param obj
	 * @param arg1 对象class
	 * @param map 字段类型的说明 <key=字段名,value=对象class(是自定义对象或是list对象里面的自定义对象)>
	 * @return object
	 */
//	@SuppressWarnings("unchecked")
//	public static List jsonToList(Object obj,Class arg1,Map<String,Class> map){
//		List list = new ArrayList();
//		if(null!=map&&!map.isEmpty()){
//			try{
//				JSONArray jsonPerson = JSONArray.fromObject(obj);
//				setJsonToBeanDateFormat();
//				list = JSONArray.toList(jsonPerson, arg1, map); 
//			}catch(Exception e){
//				
//			}
//		}else{
//			list=jsonToList(obj, arg1);
//		}
//		
//		return list;
//	}
	
	/**
	 * 把json格式的数据 转成java list 对象
	 * @param obj
	 * @param arg1 对象class
	 * @return object
	 */
//	@SuppressWarnings("unchecked")
//	public static List jsonToList(Object obj,Class arg1){
//		List list = new ArrayList();
//		try{
//			JSONArray jsonPerson = JSONArray.fromObject(obj);
//			setJsonToBeanDateFormat();
//			list = JSONArray.toList(jsonPerson, arg1); 
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	/**
	 * jsonTobean的是否设置日期的格式
	 */
//	private static void setJsonToBeanDateFormat(){
//		String[] dateFormats = new String[] {DateUtil.DATEHOURSSHOWFORMAT, DateUtil.DATESHOWFORMAT};   
//        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
//	}
	/**
	 * 把map转成combo数据个格式的json格式
	 * @return String (json)
	 */
//	public static String getMapToJson(Map<String,String> map) {
//		List<String[]> list =new ArrayList<String[]>();
//		if (null != map && !map.isEmpty()) {
//			for (String key : map.keySet()) {
//				String[] strS = new String[2];
//				strS[0] = key;
//				strS[1] = map.get(key);
//				list.add(strS);
//			}
//		}
//		return JsonUtil.jsonObject(list);
//	}
	/**
	 * 把TreeMap转成combo数据个格式的json格式
	 * @return String (json)
	 */
//	public static String getMapToJson(TreeMap<String,String> map) {
//		List<String[]> list =new ArrayList<String[]>();
//		if (null != map && !map.isEmpty()) {
//			for (String key : map.keySet()) {
//				String[] strS = new String[2];
//				strS[0] = key;
//				strS[1] = map.get(key);
//				list.add(strS);
//			}
//		}
//		return JsonUtil.jsonObject(list);
//	}
	/**
	 * 把json格式数据装成map
	 * @param str
	 * @return
	 */
	public static Map<String,String> getJsonToMap(String str){
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtil.isNotBlank(str)){
			String[] s=str.split(",");
			if(s.length>0){
				for (int i = 0; i < s.length; i++) {
					String con=s[i];
					int s1=con.indexOf(":");
					if(s1>0){
						map.put(con.substring(0,s1).trim().replace("\"", "").replace("{", ""), con.substring(s1+1).replace("\"", "").replace("}", ""));
					}else{
						map.put(con.trim().replace("\"", ""), "");
					}
				}
			}
		}
		return map;
	}
	/**
	 * 把两个json格式数据合并,合并用个or
	 * @param oldCon
	 * @param con
	 * @return
	 */
	public static String uniteJsonStr(String oldCon,String con){
		String str="";
		if(StringUtil.isBlank(oldCon)){
			oldCon="";
		}
		if(StringUtil.isBlank(con)){
			con="";
		}
		Map<String,String> oldMap=JsonUtil.getJsonToMap(oldCon);
		Map<String,String> map=JsonUtil.getJsonToMap(con);
		if(null!=oldMap&&!oldMap.isEmpty()){
			for (String key : oldMap.keySet()) {
				if(StringUtil.isNotBlank(str)){
					str+=",";
				}
				String value=oldMap.get(key);
				if(null!=map&&!map.isEmpty()){
					String newValue=map.get(key);
					if(StringUtil.isNotBlank(newValue)){
						if(StringUtil.isNotBlank(value)){
							value="("+value+")";
							value+=" or ("+newValue+")";
						}else{
							value=newValue;
						}
					}
					map.remove(key);
				}
				str+=key+":"+value;
			}
		}
		if(null!=map&&!map.isEmpty()){
			for (String key : map.keySet()) {
				if(StringUtil.isNotBlank(str)){
					str+=",";
				}
				String value=map.get(key);
				str+=key+":"+value;
			}
		}
		return str;
	}
	public static void  main(String[] args){
		String con="\"m\":\"0\"";
		System.out.println(getJsonToMap(con).get("m")); 
	}
}
