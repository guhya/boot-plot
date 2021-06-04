package net.guhya.boot.plot.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.guhya.boot.plot.common.dao.GenericDao;
import net.guhya.boot.plot.common.data.AbstractData;

@Service(value = "genericService")
public class GenericService<T extends AbstractData> {

	private GenericDao<T> dao;

	public GenericService(@Qualifier("genericDao") GenericDao<T> dao) {
		this.dao = dao;
	}

	public void setEntityName(String entityName) {
		this.dao.setNamespace(entityName);
	}
	
	/**
	 * Select 
	 * @param paramMap
	 * @return
	 */
	public T select(T dto){
		return dao.select(dto);
	}
	
	/**
	 * List 
	 * @param map
	 * @return
	 */
	public List<T> list(Map<String, Object> parameterMap) {
		return dao.list(parameterMap);
	}

	/**
	 * Select 
	 * @param parameterMap
	 * @return
	 */
	public int countList(Map<String, Object> parameterMap) {
		return dao.countList(parameterMap);
	}

	/**
	 * Insert 
	 * @param parameterMap
	 * @return
	 */
	public long insert(T dto) {
		return dao.insert(dto);
	}
	
	/**
	 * Update 
	 * @param parameterMap
	 * @return
	 */
	public int update(T dto) {
		return dao.update(dto);
	}

	/**
	 * Delete 
	 * @param parameterMap
	 * @return
	 */
	public int delete(T dto) {
		return dao.delete(dto);
	}	
}
