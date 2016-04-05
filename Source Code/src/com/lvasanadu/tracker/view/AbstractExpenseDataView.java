package com.lvasanadu.tracker.view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.lvasanadu.tracker.model.ExpenseData;

/*
 * Abstract View to show expense Data
 */
public abstract class AbstractExpenseDataView extends JPanel implements Observer {

	private ExpenseData data;

	public AbstractExpenseDataView(ExpenseData observableData) throws NullPointerException {

		if (observableData == null)
			throw new NullPointerException();

		data = observableData;

		// Register as an Observer to receive expense data updates
		data.addObserver(this);

		setBackground(Color.LIGHT_GRAY);
		setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
	}

	// get Expense Data for which this view is an Observer
	public ExpenseData getExpenseData() {
		return data;
	}

	// update display
	protected abstract void updateDisplay();

	// receive updates from Observable Account
	public void update(Observable observable, Object object) {
		updateDisplay();
	}
}
