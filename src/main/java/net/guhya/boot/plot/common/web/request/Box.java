package net.guhya.boot.plot.common.web.request;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.guhya.boot.plot.common.util.MapUtil;
import net.guhya.boot.plot.common.web.AbstractRestController;

/*
 * Object that hold all parameters from query string, form, and login session 
 */
public class Box {
	
	private static Logger log = LoggerFactory.getLogger(Box.class);
	
    protected Map<String, Object> map = new HashMap<String, Object>();
    
    public Box(){
		log.info("########## Initializing Box ");
		
		map.put(AbstractRestController.PARAM_PAGE_SIZE		, AbstractRestController.PAGE_SIZE_VAL);
		map.put(AbstractRestController.PARAM_START_ROW		, 0);

		map.put(AbstractRestController.PARAM_PAGE			, 1);
		map.put(AbstractRestController.PARAM_CONDITION		, "");
		map.put(AbstractRestController.PARAM_KEYWORD		, "");
		map.put(AbstractRestController.PARAM_SORT_COLUMN	, "");
		map.put(AbstractRestController.PARAM_SORT_ORDER		, "");
    }
    
    public Object get(String key){
        return map.get(key);
    }
     
    public void put(String key, Object value){
        map.put(key, value);
    }
     
    public Object remove(String key){
        return map.remove(key);
    }
     
    public boolean containsKey(String key){
        return map.containsKey(key);
    }
     
    public boolean containsValue(Object value){
        return map.containsValue(value);
    }
     
    public void clear(){
        map.clear();
    }
     
    public Set<Entry<String, Object>> entrySet(){
        return map.entrySet();
    }
     
    public Set<String> keySet(){
        return map.keySet();
    }
     
    public boolean isEmpty(){
        return map.isEmpty();
    }
     
    public void putAll(Map<? extends String, ?extends Object> m){
        map.putAll(m);
    }
     
    public Map<String,Object> getMap(){
        return map;
    }
       
	public String getString(String key) {
		return MapUtil.getString(map, key);
	}

	public int getInt(String key) {
		return MapUtil.getInt(map, key);
	}
	
	public long getLong(String key) {
		return MapUtil.getLong(map, key);
	}

	public double getDouble(String key) {
		return MapUtil.getDouble(map, key);
	}
	
	public BigDecimal getBigDecimal(String key) {
		return MapUtil.getBigDecimal(map, key);
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();

    }


}
