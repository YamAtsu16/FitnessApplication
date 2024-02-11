package com.fitness.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "bodyWeight")
public class BodyWeightModel {

	/** ID */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** 日付 */
	private LocalDate date;
	
	/** 体重 */
	private Double weight;
}
