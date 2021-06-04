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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.guhya.boot.plot.common.service.GenericService;
import net.guhya.boot.plot.module.scooter.dao.ScooterDao;
import net.guhya.boot.plot.module.scooter.data.ScooterData;

@Service(value = "scooterService")
public class ScooterService extends GenericService<ScooterData> {

	private ScooterDao scooterDao;
	
	public ScooterService(@Qualifier("scooterDao") ScooterDao scooterDao) {
		super(scooterDao);
		this.scooterDao = scooterDao;
		setEntityName("scooter");
	}

	public List<ScooterData> searchRadius(Map<String, Object> parameterMap) {
		return scooterDao.searchRadius(parameterMap);
	}

}
