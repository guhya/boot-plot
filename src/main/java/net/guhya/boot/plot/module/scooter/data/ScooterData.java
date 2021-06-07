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
	
	private double distance;
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;
	
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScooterData [seq=");
		builder.append(seq);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", lon=");
		builder.append(lon);
		builder.append(", distance=");
		builder.append(distance);
		builder.append(", minLat=");
		builder.append(minLat);
		builder.append(", maxLat=");
		builder.append(maxLat);
		builder.append(", minLon=");
		builder.append(minLon);
		builder.append(", maxLon=");
		builder.append(maxLon);
		builder.append("]");
		return builder.toString();
	}
	
}
