package org.bdp.glodal.exception;

import org.apache.commons.lang.StringUtils;
import org.bdp.glodal.util.StringUtil;

public class BaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private String exceptionFLag;
	private String msg;
	private String msgError;
//	private OperationLogImpl operationLog =new OperationLogImpl();
	public String getExceptionFLag() {
		return exceptionFLag;
	}

	public void setExceptionFLag(String exceptionFLag) {
		this.exceptionFLag = exceptionFLag;
	}


	public BaseException(String flag) {
		super("");
		this.setExceptionFLag(flag);
	}
	/**
	 * 异常构造方法
	 * @param flag 错误编码
	 * @param msg  提示给用户信息（可以为空，为空默认操作失败）
	 * @param msgError 写到后台的错误信息
	 */
	public BaseException(String flag,String msg,String msgError) {
		super("");
		this.setExceptionFLag(flag);
		this.setMsg(msg);
		this.setMsgError(msgError);
	}
	public BaseException(String flag,String mess) {
		super(mess);
		this.setMsg(mess);
	}
	public BaseException(String mess,Exception ex) {
		super(mess,ex);
		this.setMsg(mess);
	}
	public String getMsgError(){
		return msgError;
	}
	public String getExcepMess() {
		if (StringUtils.isNotEmpty(msg)){
			return getMsg();
		}else{
			return "操作失败";
		}
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		if(StringUtil.isBlank(msg)){
			msg="操作失败";
		}
		this.msg = msg;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
		//记录错误信息
		//operationLog.servicesLog(this.getExceptionFLag(), this.getExcepMess(), msgError);
	}
}
