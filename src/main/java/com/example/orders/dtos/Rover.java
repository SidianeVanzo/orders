package com.example.orders.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rover {
	
	private Integer number;
	private String coordinatesPosition;
	private Integer linePosition;
	private Integer columnPosition;
	private String instructions;
}
