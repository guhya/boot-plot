package net.guhya.boot.plot.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.guhya.boot.plot.common.data.AbstractData;

@Repository(value = "genericDao")
public class GenericDao<T extends AbstractData> {
	
	protected SqlSession sqlSession;
	protected String namespace = "";
	
	public GenericDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void setNamespace(String className) {
		this.namespace = "net.guhya.boot.plot.module."+className+".dao.Mapper.";
	}

	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<T> list(Map<String, Object> map) {
		return sqlSession.selectList(namespace + "list", map);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return sqlSession.selectOne(namespace + "countList", parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public T select(T dto) {
		return sqlSession.selectOne(namespace + "select", dto);
	}
	
	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public long insert(T dto) {
		int r = sqlSession.insert(namespace + "insert", dto);
		long seq = dto.getSeq() > 0 ? dto.getSeq() : r;
		return seq;
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(T dto) {
		return sqlSession.update(namespace + "update", dto);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(T dto) {
		return sqlSession.delete(namespace + "delete", dto);
	}
}