package com.lvasanadu.tracker.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.lvasanadu.tracker.model.ExpenseData;

/*
 * Timer to clear the data
 */
public class MyTimer implements ActionListener {
	private ExpenseData data;
	Timer t;

	public MyTimer(ExpenseData data) {
		this.data = data;
		t = new Timer(100, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		if (System.currentTimeMillis() >= data.getTimeForNextClear().getTimeInMillis()) {
			data.clear();
		}
	}

}
