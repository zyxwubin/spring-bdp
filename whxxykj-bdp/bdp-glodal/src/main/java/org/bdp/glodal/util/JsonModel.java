package org.bdp.glodal.util;

import java.io.Serializable;
import java.util.Map;

import org.bdp.glodal.common.superclass.SuperConstants;


public class JsonModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private long totalCount;
    
    private Object data;

    private String status;

    private String msg;
    
    private Map<String,Object> pageSum; //本页小计
    
    private Map<String,Object> allSum; //总合计
    
    
    

    public JsonModel(Object data) {
        this.data = data;
        this.status = SuperConstants.RETURN_STATUS_OK;
    }
    
    public JsonModel(Object data, String msg) {
        this.data = data;
        this.msg = msg;
        this.status = SuperConstants.RETURN_STATUS_OK;
    }

    public JsonModel(long totalCount, Object rows) {
        this.totalCount = totalCount;
        this.data = rows;
        this.status = SuperConstants.RETURN_STATUS_OK;
    }
    
    public JsonModel(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    
    public JsonModel(long totalCount, Object rows,Map<String,Object> pageSum,Map<String,Object> allSum) {
        this.totalCount = totalCount;
        this.data = rows;
        this.setPageSum(pageSum);
        this.setAllSum(allSum);
        this.status = SuperConstants.RETURN_STATUS_OK;
    }

    public JsonModel(String status) {
        this.status = status;
    }
    
    public JsonModel() {
    	
    }

    /**
     * 单个结果返回
     * @param data
     * @return
     */
    public static JsonModel dataResult(Object data){
        return new JsonModel(data);
    }
    
    public static JsonModel dataResult(Object data, String msg){
        return new JsonModel(data, msg);
    }

    /**
              * 创建结果集返回
     * @param totalCount
     * @param rows
     * @return
     */
    public static JsonModel dataResult(long totalCount, Object rows){
        return new JsonModel(totalCount, rows);
    }
    
 
    public static JsonModel dataResult(long totalCount, Object rows,Map<String,Object> pageSum,Map<String,Object> allSum){
        return new JsonModel(totalCount, rows, pageSum, allSum);
    }
    

    /**
     * 创建失败消息
     * @param msg
     * @return
     */
    public static JsonModel mkFaile(String msg){
        return new JsonModel(SuperConstants.RETURN_STATUS_ERROR, msg);
    }

    /**
     * 创建失败消息
     * @param msg
     * @return
     */
    public static JsonModel mkFaile(String status, String msg){
        return new JsonModel(status, msg);
    }

    /**
     * 创建成功消息
     * @return
     */
    public static JsonModel mkSuccess(){
        return new JsonModel(SuperConstants.RETURN_STATUS_OK,"操作成功");
    }

    /**
     * 创建成功消息
     * @param msg
     * @return
     */
    public static JsonModel mkSuccess(String msg){
        return new JsonModel(SuperConstants.RETURN_STATUS_OK, msg);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public Object getPageSum() {
		return pageSum;
	}

	public void setPageSum(Map<String,Object> pageSum) {
		this.pageSum = pageSum;
	}

	public Object getAllSum() {
		return allSum;
	}

	public void setAllSum(Map<String,Object> allSum) {
		this.allSum = allSum;
	}

}
