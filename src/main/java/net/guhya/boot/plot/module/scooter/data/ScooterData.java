package net.guhya.boot.plot.module.scooter.data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import net.guhya.boot.plot.common.data.AbstractData;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"seq","lat","lon"})
public class ScooterData extends AbstractData {
	
	private Long seq;
	
	@DecimalMin(value ="1.0", message = "Min latitude is 1.0")
	@DecimalMax(value ="2.0", message = "Max latitude is 2.0")
	private double lat;
	
	@DecimalMin(value ="101.0", message = "Min longitude is 101.0")
	@DecimalMax(value ="104.0", message = "Max longitude is 104.0")
	private double lon;
	
	
	public ScooterData() {
	}

	public ScooterData(Long seq) {
		this.seq = seq;
	}
	
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "ScooterData [seq=" + seq + ", lat=" + lat + ", lon=" + lon + "]";
	}
	
}
