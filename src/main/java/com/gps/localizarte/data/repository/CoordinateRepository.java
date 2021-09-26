package com.gps.localizarte.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gps.localizarte.data.model.Coordinate;

@Repository
public interface CoordinateRepository  extends JpaRepository<Coordinate, Integer> {
	

}
