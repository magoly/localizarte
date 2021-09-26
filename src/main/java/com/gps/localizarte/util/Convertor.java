package com.gps.localizarte.util;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gps.localizarte.data.model.Coordinate;
import com.gps.localizarte.data.model.Coordinate.CoordinateBuilder;



@Component
public class Convertor {
	
	public Coordinate analyze(String input) {
		
		List<String> inputList= Arrays.asList(input.split(",")); 
		
		String response="LOAD";
		String imei=null;
		
		CoordinateBuilder coordinate =Coordinate.builder();
		
		switch (inputList.size()) {
			// 12345678999121;
			case 1:
				imei=inputList.get(0);
				imei=imei.substring(0,imei.length()-1);
				response= String.format("**,imei:%s,B;", imei) ;
				break;
				
			// ##,12345678999121,A
			case 3:
				imei=inputList.get(1);
				response="LOAD";
				break;
			// imei:12345678999121,tracker,161003171049,,F,091045.000,A,1017.6730,N,07845.7982,E,0.00,0;
			case 13:
				Double latitude = gpsDDMToDD(Float.parseFloat(inputList.get(7)),inputList.get(8).equals("N")? 1:-1 );
				Double longitude= gpsDDMToDD(Float.parseFloat(inputList.get(9)),inputList.get(10).equals("E")? 1:-1 );
				coordinate.latitude(latitude)
						  .longitude(longitude);
				
				response="ON";
				break;

			default:
				response="LOAD";
		}
		

		return coordinate
				.createDate(Instant.now())
				.input(input)
				.response(response)
				.imei(imei)
				.build();
	}

	
	public static double gpsDDMToDD(double gps,int multiple) {
		int degrees = (int) (gps / 100);
		double minutes = gps - 100 * degrees;
		return (degrees + minutes / 60) * multiple;
	}
}
