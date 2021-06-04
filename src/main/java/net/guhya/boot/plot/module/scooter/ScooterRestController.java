package net.guhya.boot.plot.module.scooter;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.guhya.boot.plot.common.service.GenericService;
import net.guhya.boot.plot.common.web.AbstractRestController;
import net.guhya.boot.plot.common.web.request.Box;
import net.guhya.boot.plot.common.web.response.JsonListResult;
import net.guhya.boot.plot.common.web.response.JsonResult;
import net.guhya.boot.plot.module.scooter.data.ScooterData;

@RestController
@RequestMapping(value = "/v1/scooters", 
	produces = {"application/json", "text/json"}, 
	consumes = MediaType.ALL_VALUE)
public class ScooterRestController extends AbstractRestController {

	private static Logger log = LoggerFactory.getLogger(ScooterRestController.class);
	
	private GenericService<ScooterData> scooterService;
	
	public ScooterRestController(@Qualifier("genericService") GenericService<ScooterData> scooterService) {
		this.scooterService = scooterService;
		this.scooterService.setEntityName("scooter");
		
		log.info("ScooterData rest controller initialized");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonListResult<ScooterData> list(Box paramBox) {
		int count = scooterService.countList(paramBox.getMap());
		listPaging(paramBox, count);
		List<ScooterData> list = scooterService.list(paramBox.getMap());
		return new JsonListResult<ScooterData>(getPagingData(paramBox), list);
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public JsonResult<ScooterData> select(@PathVariable String id) {
		ScooterData item = scooterService.select(new ScooterData(Long.valueOf(id)));
		return new JsonResult<ScooterData>(item);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JsonResult<ScooterData> insert(@Valid @RequestBody ScooterData dto) {
		scooterService.insert(dto);
		ScooterData item = scooterService.select(dto);
		return new JsonResult<ScooterData>(item);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult<ScooterData> update(@Valid @RequestBody ScooterData dto) {
		scooterService.update(dto);
		ScooterData item = scooterService.select(dto);
		return new JsonResult<ScooterData>(item);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonResult<ScooterData> delete(@RequestBody ScooterData dto) {
		scooterService.delete(dto);
		return new JsonResult<ScooterData>(dto);
	}
	
}
