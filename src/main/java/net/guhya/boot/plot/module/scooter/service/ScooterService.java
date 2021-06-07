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

import java.util.Iterator;
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

		/* Narrow down the search with bound so we won't calculate all distances */
		calculateBound(parameterMap);

		List<ScooterData> scooters = scooterDao.searchRadius(parameterMap);

		/* 
		After we got all scooters data within a distance, we calculate the
        exact scooter distance with the center point and remove scooters
		outside radius  
		*/
		calculateDistance(scooters, parameterMap);

		return scooters;
	}

	private void calculateDistance(List<ScooterData> scooters, Map<String, Object> parameterMap) {
		double lat = Double.valueOf((String) parameterMap.get("lat"));
		double lon = Double.valueOf((String) parameterMap.get("lon"));
		double radius = Double.valueOf((String) parameterMap.get("radius"));
		
		Iterator<ScooterData> it = scooters.iterator();
		while(it.hasNext()) {
			ScooterData sc = it.next();
			double distance = distance(lat, lon, sc.getLat(), sc.getLon());
				logger.info("Lat [{}], Lon [{}], Sc Lat [{}], Sc Lon [{}], Dist [{}]", 
					lat, lon, sc.getLat(), sc.getLon(), distance);
					
			if (distance > radius) {
				it.remove();
			} else {
				sc.setDistance(distance);
	
				sc.setMaxLat((Double) parameterMap.get("maxLat"));
				sc.setMinLat((Double) parameterMap.get("minLat"));
				sc.setMaxLon((Double) parameterMap.get("maxLon"));
				sc.setMinLon((Double) parameterMap.get("minLon"));
			}
		}
	}
	
	private void calculateBound(Map<String, Object> parameterMap) {
		double maxLat = 0, minLat = 0, maxLon = 0, minLon = 0;

		double lat = Double.valueOf((String) parameterMap.get("lat"));
		double lon = Double.valueOf((String) parameterMap.get("lon"));
		double radius = Double.valueOf((String) parameterMap.get("radius"));

		/*
        number of km per degree = ~111km (111.32 in google maps, but range varies
		between 110.567km at the equator and 111.699km at the poles)
		1 km in degree = 1 / 111.32km = 0.0089
		1 m in degree = 0.0089 / 1000 = 0.0000089
		*/
		double coef = radius * 0.0000089;
		
		maxLat = lat + coef;
		minLat = lat - coef;
		
		/* pi / 180 = 0.018 */
		maxLon = lon + coef / Math.cos(lat * 0.018);
		minLon = lon - coef / Math.cos(lat * 0.018);

		logger.info("Lat [{}], Lon[{}]", lat, lon);
		logger.info("Max Lat [{}], Min Lat [{}], Max Lon [{}], Min Lon [{}]", 
			maxLat, minLat, maxLon, minLon);

		parameterMap.put("maxLat", maxLat);
		parameterMap.put("minLat", minLat);
		parameterMap.put("maxLon", maxLon);
		parameterMap.put("minLon", minLon);
	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) 
				* Math.cos(deg2rad(theta));
				
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344 * 1000;
		
		return dist;
	}

	
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}
