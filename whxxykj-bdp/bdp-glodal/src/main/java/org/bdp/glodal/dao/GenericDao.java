package org.bdp.glodal.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GenericDao<T, PK extends Serializable> extends JpaRepository<T, PK>{

	/**
	 * 获取当前对象里的所有记录
	 * @return List-T
	 */
	public List<T> queryAll();

	/**
	 * 检测ID是否已经存在
	 * @param id
	 * @return 存在：true  不存在：false
	 */
	public boolean exists(PK id);
	/**
	 * 通过更新锁定当表
	 * @param column 更新的列名
	 * @param billcodeName 单据名称
	 * @param billcode 单据号
	 * @param memberCode 使用单位值
	 * @return 1 成功 -1 失败
	 */
	public int updateLock(String column,String billcodeName,String billcode,String memberCode);
	/**
	 * 通过ID删除对象
	 * @param id
	 */
	public void delete(PK id);

	/**
	 * 获取不重新的记录
	 * @return List-T
	 */
	public List<T> queryAllDistinct();

	/**
	 * 获取数据库当前时间
	 * 
	 * @return
	 */
	public Date getSystemDate();
	
	/**
	 * 返回参数对应的是否有记录数
	 * @param whereHql
	 * @return true 有 false 无
	 */
	public boolean getCountBywhere(String whereHql);
	/**
	 * 通过ID获取对象
	 * @param id
	 * @return T
	 */
	T getOne(PK id);
}