package com.lvasanadu.tracker.model;

import java.io.Serializable;
import java.util.Calendar;

// Model to store time and value of expense
public class SingleExpense implements Serializable {
	
	private Calendar time;
	private Double value;

	public Calendar getTime() {
		return time;
	}

	public Double getValue() {
		return value;
	}

	public SingleExpense(Calendar time, Double val) {
		this.time = time;
		this.value = val;
	}

}
