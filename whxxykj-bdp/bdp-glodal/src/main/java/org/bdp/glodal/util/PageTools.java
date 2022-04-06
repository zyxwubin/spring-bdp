package org.bdp.glodal.util;
import java.util.List;
import java.util.Map;

import org.bdp.glodal.common.vo.SortData;

/**封装分页和排序参数及分页查询结果*/
public class PageTools {
	/**默认当前页显示多少条记录*/
	private static final int DEFAULT_PAGE_SIZE = 50;
    /**参数初始化标志*/
	private boolean init = false;
	/**当前页*/
	private int pageNo = 1;
	/**当前每页显示多少记录*/
	private int pageSize = DEFAULT_PAGE_SIZE;
	/**总共多少条记录*/
	private int recordCount = 0;
	/**当前几行*/
	private int recordCountNo = 0;
	/**总共多少页*/
	private int pageCount = 0;
	/**开始记录*/
	private int startRow = 0;
	/**最后一页*/
	private int lastPageRow = 0;
	/**当前第几页*/
	private int currentPageNo = 0;
	/**当前的连接*/
	private String url = "";
	/**上一页*/
	private int upPageNo;
	/**下一页*/
	private int downPageNo;
	/**是否期初 true是 false否*/
	private boolean beginTotal;
	/**上页合计 true是 false否*/
	private boolean priorTotal;
	/**是否合计 true是 false否*/
	private boolean needTotal;
	/**是否分组 true是 false否*/
	private boolean needGroup;
	/**汇总的map Map<key=列名, value=属性表达式>*/
	private Map<String, String> totalProperty;
	/**汇总的结果*/
	private Map<String, String> summaryMap;
	/**期初的结果*/
	private Map<String, String> beginMap;
	/** 小计的结果 Map<key=分组值 ,Map<key=属性, value=属性值>>*/
	private Map<String, Map<String, String>> subtotalMap;
	/**分组属性*/
	private String group;
	/** from的位置是否是最后一个 */
	private boolean last = false;
	/**分组查询小计的时候过滤条件*/
	private String otherGroupHql;
	private List<Object> otherGroupParams;
	
	public PageTools(){}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageTools(int recordCount, int pageNum, int pageSize) {
		this.pageNo = pageNum;
		this.currentPageNo=pageNo;
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
		this.recordCount = recordCount;
		this.initialize();
	}

	/**
	 * 根据int recordCount, int pageNum, int pageSize初始化相应的参数
	 */
	protected void initialize() {
		if (recordCount % pageSize == 0) {
			pageCount = recordCount / pageSize;
		} else {
			pageCount = recordCount / pageSize + 1;
		}
		if (pageNo > pageCount) {
			pageNo = pageCount;
		} else if (pageNo < 1) {
			pageNo = 1;
		}
		startRow = (pageNo - 1) * pageSize;
		lastPageRow = (pageCount - 1) * pageSize;
		init = true;
	}

	public int getStartRow() {
		if (!init) {
			initialize();
		}
		return startRow > 0 ? startRow : 0;
	}

	public int getLastPageRow() {
		return lastPageRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		init = false;
	}

	public void setPageSize(int pageSize) {
		if(0==pageSize){
			pageSize=10;
		}
		this.pageSize = pageSize;
		if(this.getRecordCountNo()>0){
			setRecordCountNo(this.getRecordCountNo());
		}
		init = false;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
		init = false;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setLastPageRow(int lastPageRow) {
		this.lastPageRow = lastPageRow;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void calc() {
		if (!init) {
			initialize();
		}
	}

	public int getDownPageNo() {
		setDownPageNo(pageNo < pageCount ? pageNo + 1 : 0);
		return downPageNo;
	}

	public void setDownPageNo(int downPageNo) {
		this.downPageNo = downPageNo;
	}

	public int getUpPageNo() {
		setUpPageNo(pageNo > 0 ? pageNo - 1 : 0);
		return upPageNo;
	}

	public void setUpPageNo(int upPageNo) {
		this.upPageNo = upPageNo;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRecordCountNo() {
		return recordCountNo;
	}

	public void setRecordCountNo(int recordCountNo) {
		this.recordCountNo = recordCountNo;
		if(this.getPageSize()>0&&recordCountNo>0){
			setPageNo(recordCountNo/this.getPageSize()+1);
		}
		init = false;
	}

	public boolean isNeedTotal() {
		return needTotal;
	}

	public void setNeedTotal(boolean needTotal) {
		this.needTotal = needTotal;
	}

	public boolean isNeedGroup() {
		return needGroup;
	}

	public void setNeedGroup(boolean needGroup) {
		this.needGroup = needGroup;
	}
	
	public Map<String, String> getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Map<String, String> totalProperty) {
		this.totalProperty = totalProperty;
	}
	
	public Map<String, String> getSummaryMap() {
		return summaryMap;
	}

	public void setSummaryMap(Map<String, String> summaryMap) {
		this.summaryMap = summaryMap;
	}

	public Map<String, Map<String, String>> getSubtotalMap() {
		return subtotalMap;
	}

	public void setSubtotalMap(Map<String, Map<String, String>> subtotalMap) {
		this.subtotalMap = subtotalMap;
	}
	
	public String getGroup() {
		return group;
	}

	@SuppressWarnings("unchecked")
//	public void setGroup(String group) {
//		if(StringUtil.isNotBlank(group)){
//			List<SortData> groupList = (List<SortData>) JsonUtil.jsonToList(group, SortData.class);
//		      String str="";
//		      if(null!=groupList&&!groupList.isEmpty()){
//		    	  str=groupList.get(0).getProperty();
//		      }
//		      group=str;
//		}
//		this.group = group;
//	}
	
	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public String getOtherGroupHql() {
		return otherGroupHql;
	}

	public void setOtherGroupHql(String otherGroupHql) {
		this.otherGroupHql = otherGroupHql;
	}

	public List<Object> getOtherGroupParams() {
		return otherGroupParams;
	}

	public void setOtherGroupParams(List<Object> otherGroupParams) {
		this.otherGroupParams = otherGroupParams;
	}
	public boolean isBeginTotal() {
		return beginTotal;
	}

	public void setBeginTotal(boolean beginTotal) {
		this.beginTotal = beginTotal;
	}

	public boolean isPriorTotal() {
		return priorTotal;
	}

	public void setPriorTotal(boolean priorTotal) {
		this.priorTotal = priorTotal;
	}
	
	public Map<String, String> getBeginMap() {
		return beginMap;
	}

	public void setBeginMap(Map<String, String> beginMap) {
		this.beginMap = beginMap;
	}
}
