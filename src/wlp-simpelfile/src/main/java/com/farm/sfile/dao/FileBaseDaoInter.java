package com.farm.sfile.dao;

import com.farm.sfile.domain.FileBase;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：文件数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface FileBaseDaoInter {
	/**
	 * 删除一个文件实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(FileBase filebase);

	/**
	 * 由文件id获得一个文件实体
	 * 
	 * @param id
	 * @return
	 */
	public FileBase getEntity(String filebaseid);

	/**
	 * 插入一条文件数据
	 * 
	 * @param entity
	 */
	public FileBase insertEntity(FileBase filebase);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个文件记录
	 * 
	 * @param entity
	 */
	public void editEntity(FileBase filebase);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条文件查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除文件实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询文件实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<FileBase> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改文件实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计文件:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 删除转换日志表,删除转换任务表,删除预览文件表
	 * 
	 * @param fileid
	 */
	public void delRelationsViewTables(String fileid);
}