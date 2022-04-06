package org.bdp.basic.common;

import org.bdp.glodal.common.superclass.SuperConstants;

public class BasicConstants extends SuperConstants{
	
	/**单据类型编码 客商-新增审核 */
	public static final String SYSTEM_BILLTYPE_BASIC_COMPANY_NEWAUDIT= "BC001";
	/**单据类型编码 客商-基本信息审核 */
	public static final String SYSTEM_BILLTYPE_BASIC_COMPANY_MODIFYAUDIT= "BC002";
	/**单据类型编码 客商-等级调整审核 */
	public static final String SYSTEM_BILLTYPE_BASIC_COMPANY_LEVELAUDIT= "BC003";
	/**单据类型编码 客商-供应商额度审核 */
	public static final String SYSTEM_BILLTYPE_BASIC_COMPANY_SUPPLIERQUOTAAUDIT= "BC004";
	/**单据类型编码 客商-客户额度审核 */
	public static final String SYSTEM_BILLTYPE_BASIC_COMPANY_CUSTOMERQUOTAAUDIT= "BC005";
	/**单据类型编码 新签、产值额度填报单 */
	public static final String SYSTEM_BILLTYPE_BASIC_SIGNATURE_QUOTA= "BC006";

	/** 初始化 初始化状态 0:未完成**/
	public static final String ORG_INIFLAG_0="0";
	/** 初始化 初始化状态 1:已完成**/
	public static final String ORG_INIFLAG_1="1";
	
	/** 子系统说明 平台支撑*/
	public static final String APPLICATION_CODE_0="0001";
	/** 子系统说明 办公自动化*/
	public static final String APPLICATION_CODE_1="0002";
	/** 子系统说明 客户关系管理*/
	public static final String APPLICATION_CODE_2="0003";
	/** 子系统说明 商贸物流管理*/
	public static final String APPLICATION_CODE_3="0004";
	/** 子系统说明 仓储物流管理*/
	public static final String APPLICATION_CODE_4="0005";
	/** 子系统说明 商务智能分析*/
	public static final String APPLICATION_CODE_5="0006";

	
	/** 往来单位 */
	/** 往来单位数据权限控制方式 - 不控制*/
	public static final String PERMITMODE_0 = "0";
	/** 往来单位数据权限控制方式 - 角色*/
	public static final String PERMITMODE_1 = "1";
	/** 往来单位数据权限控制方式 - 机构*/
	public static final String PERMITMODE_2 = "2";
	/** 往来单位数据权限控制方式 - 部门*/
	public static final String PERMITMODE_3 = "3";
	/** 往来单位数据权限控制方式 - 操作员*/
	public static final String PERMITMODE_4 = "4";
	
	/** 往来单位状态 0 : 启用*/
	public static final String COMPANY_STATE_0 = "0";
	/** 往来单位状态 1 : 停用*/
	public static final String COMPANY_STATE_1 = "1";

	/** 往来单位 项目 类型 0 : 所有共用*/
	public static final String COMPANY_PROJECT_TYPE_0 = "0";
	/** 往来单位 项目 类型 1 : 按单位划分 */
	public static final String COMPANY_PROJECT_TYPE_1 = "1";
	
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_GYS = "GYS";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_KH = "KH";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_NBDW = "NBDW";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_HZ = "HZ";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_FYDW = "FYDW";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW) */
	public static final String COMPANY_ASSESS_TYPE_ZYDW = "ZYDW";
	/** 往来单位业务关系 ***业务关系(供应商GYS、客户KH、内部单位NBDW、货主HZ、费用单位FYDW、质押单位ZYDW、非贸易类FMY) */
	public static final String COMPANY_ASSESS_TYPE_FMY = "FMY";
	
	/** 菜单属性 0：产品 */
	public static final String MENU_PROPERTY_0 = "0";
	/** 菜单属性 1：项目 */
	public static final String MENU_PROPERTY_1 = "1";
	/** 菜单属性 2：实施 */
	public static final String MENU_PROPERTY_2 = "2";
	
	/** 菜单上的授权方式判断类别 0：判断操作员权限 */
	public static final String PERMISSION_SORT_0 = "0";
	/** 菜单上的授权方式判断类别 1：判断是否登陆*/
	public static final String PERMISSION_SORT_1 = "1";
	/** 菜单上的授权方式判断类别 2：管理员*/
	public static final String PERMISSION_SORT_2 = "2";
	/** 菜单上的授权方式判断类别 3:系统管理员 */
	public static final String PERMISSION_SORT_3 = "3";
	
	/** 按钮的授权方式判断类别 0：操作员权限 */
	public static final String BUTTON_SORT_0 = "0";
	/** 按钮的授权方式判断类别 1：登陆*/
	public static final String BUTTON_SORT_1 = "1";
	/** 按钮的授权方式判断类别 2：菜单权限*/
	public static final String BUTTON_SORT_2 = "2";
	
	/** 操作员的授权方式 0：操作员权限 */
	public static final String OPERATOR_IDENTITY_0 = "0";
	/** 操作员的授权方式 1：管理员权限 */
	public static final String OPERATOR_IDENTITY_1 = "1";
	/** 操作员的授权方式 2：系统管理员权限 */
	public static final String OPERATOR_IDENTITY_2 = "2";
	
	/** 工种系数 */
	public static final String COLUMNNAME_0 = "worktypeWorkcoe";
	public static final String COLUMNDIS_0 = "工种系数";
	/** 工种人数 */
	public static final String COLUMNNAME_1 = "workloadWnumber";
	public static final String COLUMNDIS_1 = "工种人数";
	/** 工资系数 */
//	public static final String COLUMNNAME_2 = "worktypeWagecoe";
//	public static final String COLUMNDIS_2 = "工资系数";
	/** 物资代码工作量系数（原先的品名系数） */
	public static final String COLUMNNAME_3 = "partsnameWorkcoe";
	//原先的品名系数
	public static final String COLUMNDIS_3 = "物资代码工作量系数";
	/** 工作量类别系数 */
	public static final String COLUMNNAME_4 = "billtypeWorkcoe";
	public static final String COLUMNDIS_4 = "类别系数";
	/** 业务量 */
	public static final String COLUMNNAME_5 = "workloadBweight";
	public static final String COLUMNDIS_5 = "业务量";
	/** 作业人数 */
	public static final String COLUMNNAME_6 = "workloadSnumber";
	public static final String COLUMNDIS_6 = "作业人数";
	
	/** 仓库接口 是否启用仓位 0:不启用**/
	public static final String WAREHOUSE_WAREPLACE_0="0";
	/** 仓库接口 是否启用仓位 1:启用**/
	public static final String WAREHOUSE_WAREPLACE_1="1";
	
	/** 员工状态 是否启用 0:不启用**/
	public static final String EMPLOYEE_STATE_0="0";
	/** 员工状态 是否启用 1:启用**/
	public static final String EMPLOYEE_STATE_1="1";
	
	
	/** 工作组类别 0:内部 */
	public static final String WORK_GROUP_CLASS_0 = "0";
	/** 工作组类别 1:外部*/
	public static final String WORK_GROUP_CLASS_1 = "1";
	
	/** 工作量计算方式 0:重量 */
	public static final String WORKTYPE_METERING_0 = "0";
	/** 工作量计算方式 1:数量*/
	public static final String WORKTYPE_METERING_1 = "1";
	/** 工作量计算方式 2:次数*/
	public static final String WORKTYPE_METERING_2 = "2";
	
	
	/**是否是内部单位 0 否*/
	public static final String DATA_INNER_FLAG_0="0";
	/**是否是内部单位 1 是*/
	public static final String DATA_INNER_FLAG_1="1";
	
	/**消息类型  0 ：短消息*/
	public static final String MASSAGE_TYPE_0="0";
	/**消息类型  1：公告*/
	public static final String MASSAGE_TYPE_1="1";
	
	/**消息 方式 0 发送 */
	public static final String MESSAGE_WAY_0 = "0";
	/**消息 方式 1 转发 */
	public static final String MESSAGE_WAY_1 = "1";
	/**消息 方式 2 回复 */
	public static final String MESSAGE_WAY_2 = "2";
	
	/** 方向  0：发送  */
	public static final String OA_AFFICHE_CLASS_0 = "0";
	/** 方向  1：接受  */
	public static final String OA_AFFICHE_CLASS_1 = "1";
	/** 方向  2：草稿*/
	public static final String OA_AFFICHE_CLASS_2 = "2";

	/**共享接口状态 0：未推送 1:已推送 2:已共享 3:共享驳回 */
	public static final String DATA_SHARE_STATE_0 = "0";
	/**共享接口状态 1:已推送*/
	public static final String DATA_SHARE_STATE_1 = "1";
	/**共享接口状态 2:已共享*/
	public static final String DATA_SHARE_STATE_2 = "2";
	/**共享接口状态 3:共享驳回*/
	public static final String DATA_SHARE_STATE_3 = "3";

	/**运输方式 0:海运*/
	public static final String DD_TYPETRANSPORT_0 = "海运";
	/**运输方式 1:汽运*/
	public static final String DD_TYPETRANSPORT_1 = "汽运";
	/**运输方式 2:铁运*/
	public static final String DD_TYPETRANSPORT_2 = "铁运";
	/**运输方式 3:火运*/
	public static final String DD_TYPETRANSPORT_3 = "火运";
	
}
