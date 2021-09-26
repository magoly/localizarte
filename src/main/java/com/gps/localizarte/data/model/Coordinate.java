package com.gps.localizarte.data.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "coordinate")
@Data
@Builder
public class Coordinate {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String imei;
	
	private String input;
	
	private String response;
	
	private Instant createDate;
	
	private Double latitude;
	
	private Double longitude;

}
