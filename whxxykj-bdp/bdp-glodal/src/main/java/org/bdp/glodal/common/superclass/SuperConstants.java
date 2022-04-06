package org.bdp.glodal.common.superclass;

 
/************************************************
 * Copyright (c)  by goldensoft
 * All right reserved.
 * Create Date: 2013-3-22
 * Create Author: gp
 * File Name:   application 常量
 * Last version:  1.0
 * Last Update Date:
 * Change Log:
**************************************************/	
 
public class SuperConstants {
	/**单据批号生成长度 */
	public static final int BATCH_LENGTH = 4;
	/**File separator from System properties*/
	public static final String FILE_SEP = System.getProperty("file.separator");
	/** sessinID */
	public static final String USER_SESSION_KEY = "goldensoft.session.user";
	/** csrfToken */
	public static final String USER_SESSION_CSRF = "goldensoft.session.csrf";
	/** 上一个URL */
	public static final String GOING_TO_URL_KEY = "GOING_TO";
	/** 当前方法对应的菜单名称 */
	public static final String ACTION_TITLE = "ACTION_TITLE"; 
	/** 当前方法对应的菜单 */
	public static final String ACTION_MENU = "ACTION_MENU"; 
	/** 回跳URL */
	public static final String GOING_TO_LOGIN_URL_KEY = "GOING_TO_LOGIN";
	/** 当前action名称 */
	public static final String ACTIONNAME = "actionName";
	/** 当前method名称 */
	public static final String METHODNAME = "methodName";
	/** 当前action地址 */
	public static final String ACTIONURL = "actionUrl";
	/** 当前域名 */
	public static final String ACTION_HOST = "actionhost";
	/** 当前命名空间 */
	public static final String NAMESPACE = "namespace";
	/** 当前action全路径（包括参数） */
	public static final String ACTIONALLURL = "actionAllUrl";
	
	/**ajax返回时的状态表示*/
	public static final String AJAX_RETURN_STATE = "state";
	/**ajax返回时的状态值 0 未登录*/
	public static final String AJAX_RETURN_STATE_LOGIN = "0";
	/**ajax返回时的状态值 1 无权限 */
	public static final String AJAX_RETURN_STATE_AUTH = "1";
	/**ajax返回时的状态值 2 失败 */
	public static final String AJAX_RETURN_STATE_ERROR = "2";
	/**ajax返回时的状态值 3 成功*/
	public static final String AJAX_RETURN_STATE_OK = "3";
	/**ajax返回时的状态值 4 提示 */
	public static final String AJAX_RETURN_STATE_MSG = "4";
	/** ajax返回时的说明*/
	public static final String AJAX_RETURN_MSG = "msg";
	/** 查询数据库数值为空时用--代替 */
	public static final String EMPTY_STRING = "--";
	/**树第一级默认的父代码*/
	public static final String TREE_EMPTY = "-1";
	/**树的叶子结点*/
	public static final String TREE_LEAF = "1";
	
	/**不启用*/
	public static final String STATE_0 = "0";
	/**启用*/
	public static final String STATE_1 = "1";
	
	/**当前数据需要处理的状态,由页面返回的 0:新增 */
	public static final String DATAFLAG_0="0";
	/**当前数据需要处理的状态,由页面返回的  1:更新 */
	public static final String DATAFLAG_1="1";
	/**当前数据需要处理的状态,由页面返回的 2:删除*/
	public static final String DATAFLAG_2="2";
	
	/**明细是否有码单 0:无*/
	public static final String PRODUCT_DETAIL_CODEFLAG_0="0";
	/**明细是否有码单 1:有*/
	public static final String PRODUCT_DETAIL_CODEFLAG_1="1";

	/** 库存物资出入方向 0:入 */
	public static final String GOODS_LOG_DIRECTION0 = "0";
	/** 库存物资出入方向 1:出 */
	public static final String GOODS_LOG_DIRECTION1 = "1";
	
	/**扩展列设置 是否是汇总列 0 是**/
	public static final String COLUMN_SUMFLAG_0="0";
	/**扩展列设置 是否是汇总列 1 不是**/
	public static final String COLUMN_SUMFLAG_1="1";
	
	/** 凭证标记 0:默认 **/
	public static final String VOUCHER_FLAG_0="0";
	/** 凭证标记 1:导入中**/
	public static final String VOUCHER_FLAG_1="1";
	/** 凭证标记 2:导入完成**/
	public static final String VOUCHER_FLAG_2="2";
	/** 凭证标记 3:已生成凭证**/
	public static final String VOUCHER_FLAG_3="3";
	/** 货齐标记 0:新增**/
	public static final String GOODS_FLAG_0="0";
	/** 货齐标记 1:完成**/
	public static final String GOODS_FLAG_1="1";
	/** 票齐标记 0:新增**/
	public static final String INVOICE_FLAG_0="0";
	/** 票齐标记 1:完成**/
	public static final String INVOICE_FLAG_1="1";
	/** 款齐标记 0:新增**/
	public static final String FUND_FLAG_0="0";
	/** 款齐标记 1:完成**/
	public static final String FUND_FLAG_1="1";
    /** 提交状态 0:未提交*/
    public static final String DATA_SUBMIT_0="0";
    /** 提交状态 1:已提交*/
    public static final String DATA_SUBMIT_1="1";
	
	/**往来流水 方向 0：应付 */
	public static final String FUND_BALANCE_CLASS_0="0";
	/**往来流水 方向 1：实付 */
	public static final String FUND_BALANCE_CLASS_1="1";
	/**往来流水 方向 2：应收 */
	public static final String FUND_BALANCE_CLASS_2="2";
	/**往来流水 方向 3：实收 */
	public static final String FUND_BALANCE_CLASS_3="3";
	/**往来流水 方向 4: 已到 */
	public static final String FUND_BALANCE_CLASS_4="4";
	/**往来流水 方向 5: 已开 */
	public static final String FUND_BALANCE_CLASS_5="5";
	/**往来流水 方向 40: 已到+应付 */
	public static final String FUND_BALANCE_CLASS_40="40";
	/**往来流水 方向 40: 已开+应收 */
	public static final String FUND_BALANCE_CLASS_52="52";
	
	/** 明细状态 状态 0 新增*/
	public static final String DETAIL_STATE_0="0";
	/** 明细状态 状态 1 执行中*/
	public static final String DETAIL_STATE_1="1";
	/** 明细状态 状态 2 完成*/
	public static final String DETAIL_STATE_2="2";
	
	/** 单据类型 关账控制 0：启用关账*/
	public static final String BILLTYPE_CLOSE_0="0";
	/** 单据类型 关账控制 1：停用关账*/
	public static final String BILLTYPE_CLOSE_1="1";
	
	/** 单据状态 0:新增 **/
	public static final String MAIN_STATE_0="0";
	/** 单据状态 1:生效(审核)**/
	public static final String MAIN_STATE_1="1";
	/** 单据状态 2:执行中**/
	public static final String MAIN_STATE_2="2";
	/** 单据状态 3:完成**/
	public static final String MAIN_STATE_3="3";
	/** 单据状态 -1:作废**/
	public static final String MAIN_STATE_4="-1";
	
	/** 单据类型 审核 0：未审*/
	public static final String BILLTYPE_AUDIT_0="0";
	/** 单据类型 审核 1：已审*/
	public static final String BILLTYPE_AUDIT_1="1";
	/** 单据类型 审核 2：审核中*/
	public static final String BILLTYPE_AUDIT_2="2";
	/** 单据类型 审核 -1：弃审*/
	public static final String BILLTYPE_AUDIT_NO="-1";
	/** 单据类型 审核 -2：驳回 */
	public static final String BILLTYPE_AUDIT_NO2="-2";

	/** 费用类别 0 业务登记*/
	public static final String SETTLEMENT_FEE_TYPE_0="0";
	/** 费用类别 1 非业务登记*/
	public static final String SETTLEMENT_FEE_TYPE_1="1";
	/**费用 方向 0：应付 */
	public static final String FEE_DERECTION_0="0";
	/**费用 方向 1：应收 */
	public static final String FEE_DERECTION_1="1";
	/** 费用是否免费 0:收费**/
	public static final String FEE_CURRENT_0 = "0";
	/** 费用是否免费 1:免费**/
	public static final String FEE_CURRENT_1 = "1";
	/** 费用核销标志 0 未核销*/
	public static final String SETTLEMENT_FEE_VERIFY_0="0";
	/** 费用核销标志 1 核销*/
	public static final String SETTLEMENT_FEE_VERIFY_1="1";
	
	/** 单据类型 更新控制方式 0:生效后(未启用审核)未被下级引用可以修改 */
	public static final String BILLTYPE_UPDATETYPE_0="0";
	/** 单据类型 更新控制方式 1:生效后(未启用审核)被下级引用可以修改 */
	public static final String BILLTYPE_UPDATETYPE_1="1";
	
	/**收支方向 0： 支 */
	public static final String FUND_DIRECTION_0="0";
	/** 收支方向  1：收 */
	public static final String FUND_DIRECTION_1="1";
	
	/**数据权限类别 类别 orgCode:机构*/
	public static final String DATAPER_CODE_ORG="orgCode";
	/**数据权限类别 类别 deptCode:部门*/
	public static final String DATAPER_CODE_DEPT="deptCode";
	/**数据权限类别 类别 warehouseCode:仓库*/
	public static final String DATAPER_CODE_WAREHOUSE="wareCode";
	
	/** 单据类型 审核控制 0：不启用审核*/
	public static final String BILLTYPE_CHECK_0="0";
	/** 单据类型 审核控制 1：启用审核*/
	public static final String BILLTYPE_CHECK_1="1";
	/** 单据类型 审核模式 0：逐级审核*/
	public static final String BILLTYPE_CHECKMODE_0="0";
	/** 单据类型 审核模式 1：并级审核*/
	public static final String BILLTYPE_CHECKMODE_1="1";
	/**单据类型类别 0:单据*/
	public static final String BILLTYPE_CLASS_0="0";
	/**单据类型类别 1:报表*/
	public static final String BILLTYPE_CLASS_1="1";
	/** 直属领导 0 不是 */
	public static final String BILLTYPE_AUDIT_LEADER_0 = "0";
	/** 直属领导 1 是 */
	public static final String BILLTYPE_AUDIT_LEADER_1 = "1";
	
	/** 页面编辑状态 1 新增*/
	public static final String PAGE_DEIT_STATE_1 = "new";
	/** 页面编辑状态 2 修改*/
	public static final String PAGE_DEIT_STATE_2 = "edit";
	/** 页面编辑状态 3 审核*/
	public static final String PAGE_DEIT_STATE_3 = "audit";
	/** 页面编辑状态 4 弃审*/
	public static final String PAGE_DEIT_STATE_4 = "unAudit";
	/** 页面编辑状态 5 查看*/
	public static final String PAGE_DEIT_STATE_5 = "view";

	/** 事务处理 状态 0:未处理**/
	public static final String TRANSACTION_FLAG_0="0";
	/** 事务处理 状态 1:处理失败**/
	public static final String TRANSACTION_FLAG_1="1";
	/** 事务处理 状态 2:处理成功**/
	public static final String TRANSACTION_FLAG_2="2";
	
	/** 贸易仓储上传标记 0：未上传 */
	public static final String INTERFACE_WMSSCM_FLAG_0 = "0";
	/** 贸易仓储上传标记 1：已上传 */
	public static final String INTERFACE_WMSSCM_FLAG_1 = "1";
	
	/**手机app返回时的状态表示*/
	public static final String MOBILE_RETURN_STATE = "code";
	/**手机app返回时的状态值 0 成功*/
	public static final String MOBILE_RETURN_STATE_OK = "0";
	/**手机app返回时的状态值 1 失败 */
	public static final String MOBILE_RETURN_STATE_ERROR = "1";
	/**手机app返回时的状态值 2 未登陆 */
	public static final String MOBILE_RETURN_STATE_LOGIN = "2";
	/**手机app返回时的状态值 3 无权限 */
	public static final String MOBILE_RETURN_STATE_AUTH = "3";
	/**手机app返回时的错误信息*/
	public static final String MOBILE_RETURN_MSG = "message";
	/**手机app返回时的数据*/
	public static final String MOBILE_RETURN_DATA = "data";
	/**手机app返回时的分页*/
	public static final String MOBILE_RETURN_PAGE = "page";
	/**手机app返回时的分页汇总*/
	public static final String MOBILE_RETURN_PAGE_SUMMARY = "pageSummary";
	
	
	/**
	 * ajax返回时的状态字段key
	 */
	public final static String RETURN_STATUS_KEY = "status";
	/**ajax返回时的状态值 0 未登录*/
	public static final String RETURN_STATUS_LOGIN = "0";
	/**ajax返回时的状态值 1 无权限 */
	public static final String RETURN_STATUS_AUTH = "1";
	/**ajax返回时的状态值 2 失败 */
	public static final String RETURN_STATUS_ERROR = "2";
	/**ajax返回时的状态值 3 成功*/
	public static final String RETURN_STATUS_OK = "3";
	/**ajax返回时的状态值 4 提示 */
	public static final String RETURN_STATUS_MSG = "4";
	/**ajax返回时的状态值 5 未完成注册*/
	public static final String RETURN_STATUS_REGISTER = "5";
	/**ajax返回时的状态值 6 已在异地登录*/
	public static final String RETURN_STATUS_LOGWARN = "6";
	/**未配置域名信息*/
	public static final String RETURN_STATUS_HOST = "7";
	/** ajax返回时的说明KEY */
	public static final String RETURN_MSG_KEY = "msg";

	/**手机验证码*/
	public static final String MOBILE_RANDOM = "MOBILE_RANDOM";
	
	public static final String SYS_FUND = "000000";
	
	/**资金操作业务类型**/
	public static class ExtType{
		/**会员线下入金**/
		public static final Integer RECHARGE = Integer.valueOf(1);	
		
		/**会员线下出金**/
		public static final Integer WITHDRAW = Integer.valueOf(2);
		
		/**手续费**/
		public static final Integer SXF = Integer.valueOf(3);
		
		/**保证金**/
		public static final Integer BOND = Integer.valueOf(4);

		/**违约金**/
		public static final Integer WYJ = Integer.valueOf(5);
		
		/**提现手续费**/
		public static final Integer EXTRACT_SXF = Integer.valueOf(6);
		
	}

	/**参数设置常量**/
	public static class Cssz {
		public static class Sys {
			/**启用前台登录帐号限制**/
			public static final String M_LOGIN_USER_ONLINE = "marketSingleOnline";
			/**前台密码错误次数**/
			public static final String M_LOGIN_PWD_ERR_TIMES = "marketPwdNum";
			/**启用后台登录帐号限制**/
			public static final String B_LOGIN_USER_ONLINE = "manageSingleOnline";
			/**后台密码错误次数**/
			public static final String B_LOGIN_PWD_ERR_TIMES = "managePwdNum";
			/**发料超发比例**/
			public static final String OVERSTEP_STOCK = "overstepStock";
		}
		public static class Fund {
			/**系统资金账户**/
			public static final String SYS_FUNDID = "sys_fundid";
		}
	}

	/**登录常量**/
	public static class LOGIN {
		/** 后台登录用户的对象*/
		public static final String MANAGER_LOGIN_USER_KEY="MANAGER_LOGIN_USER_KEY";
		public static final String MANAGER_LOGIN_USER_MOBILE_KEY="MANAGER_LOGIN_USER_MOBILE_KEY";
		/** 前台登录用户的对象*/
		public static final String MARKET_LOGIN_USER_KEY="MARKET_LOGIN_USER_KEY";
		/** 后台登录验证码**/
		public static final String MANAGER_LOGIN_RANDOM = "MANAGER_LOGIN_RANDOM";
		/** 前台登录验证码**/
		public static final String MARKET_LOGIN_RANDOM = "MARKET_LOGIN_RANDOM";
		/** 前台注册验证码**/
		public static final String MARKET_REG_RANDOM = "MARKET_REG_RANDOM";
		/** 后台用户访问权限*/
		public static final String MANAGER_OPT_AUTHD_URL = "MANAGER_OPT_AUTHD_URL";
		/** 前台户访问权限*/
		public static final String MARKET_OPT_AUTHD_URL = "MARKET_OPT_AUTHD_URL";
		/** 初始化登录密码错误次数*/
		public static final String INIT_PWD_ERROR = "9";
		
		public static final String MANAGER_DATA_PERM = "MANAGER_DATA_PERM";


	}

	/**系统类型常量**/
	public static class SysType {
		/**
		 * 当前子系统类型
		 **/
		public static final String RUN_TYPE = "runType";
		/**
		 * 当前子系统ID
		 **/
		public static final String SYSTEM_ID = "systemId";
		/**
		 * 前后台类型
		 **/
		public static final String SITE_TYPE = "siteType";
		/**
		 * 前后标识
		 */
		public static final Integer MARKET = 1;
		/**
		 * 后后标识
		 */
		public static final Integer MANAGER = 2;

		/**
		 * 基础系统
		 **/
		public static final String SYS = "sys";

		/**
		 * 竞价系统
		 **/
		public static final String BTS = "bts";

		/**
		 * 资金系统
		 **/
		public static final String FUND = "fund";
		/**
		 * AEP系统
		 **/
		public static final String AEP = "aep";
		/**
		 * APP平台
		 **/
		public static final String APP = "app";
		/**
		 * PC平台
		 **/
		public static final String PC = "pc";
	}
}