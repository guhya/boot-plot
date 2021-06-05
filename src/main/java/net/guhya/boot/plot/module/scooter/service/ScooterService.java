/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guhya.boot.plot.module.scooter.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.guhya.boot.plot.common.service.GenericService;
import net.guhya.boot.plot.module.scooter.dao.ScooterDao;
import net.guhya.boot.plot.module.scooter.data.ScooterData;

@Service(value = "scooterService")
public class ScooterService extends GenericService<ScooterData> {

	private static final Logger logger = LoggerFactory.getLogger(ScooterService.class);
	
	private ScooterDao scooterDao;
	
	public ScooterService(@Qualifier("scooterDao") ScooterDao scooterDao) {
		super(scooterDao);
		this.scooterDao = scooterDao;
		setEntityName("scooter");
	}

	public List<ScooterData> searchRadius(Map<String, Object> parameterMap) {
		
		calculateBound(parameterMap);
		
		return scooterDao.searchRadius(parameterMap);
	}
	
	private void calculateBound(Map<String, Object> parameterMap) {
		double maxLat = 0, minLat = 0, maxLon = 0, minLon = 0;
		
		 double lat = Double.valueOf((String) parameterMap.get("lat"));
		 double lon = Double.valueOf((String) parameterMap.get("lon"));
		 double radius = Double.valueOf((String) parameterMap.get("radius"));
		 double half = radius / 2;
		 
		 /**
		  * - 111,111 meter is 1 degree latitude
		  * - 111,111 meter * Math.cos(latitude) is 1 degree longitude
		  */
		 
		final double d = 111111;
		double latMeter = half / d;
		double lonMeter = half / d * Math.cos(lat);
		
		maxLat = lat + latMeter;
		minLat = lat - latMeter;
		maxLon = lon + lonMeter;
		minLon = lon - lonMeter;
		
		logger.info("Lat [{}], Lon[{}]", lat, lon);
		logger.info("Max Lat [{}], Min Lat [{}], Max Lon [{}], Min Lon[{}]", 
				maxLat, minLat, maxLon, minLon);
		
		parameterMap.put("maxLat", maxLat);
		parameterMap.put("minLat", minLat);
		parameterMap.put("maxLon", maxLon);
		parameterMap.put("minLon", minLon);
	}

}
