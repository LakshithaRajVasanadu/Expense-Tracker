package com.lvasanadu.tracker.view;

import java.awt.*;
import javax.swing.border.TitledBorder;

import com.lvasanadu.tracker.model.ExpenseData;

/*
 * Display for Expense Vs Limit 
 */
public class ExpenseDataBarGraphView extends AbstractExpenseDataView {

	public ExpenseDataBarGraphView(ExpenseData data) {
		super(data);
		
		TitledBorder border = new TitledBorder("Expense Vs Limit");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 14));
		setBorder(border);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("TimesNewRoman", Font.BOLD, 14));

		double expenses = getExpenseData().getTotalExpense();
		double limit = getExpenseData().getLimit();

		if (limit > 0) {

			// Draw limit bar
			int limitPercentage = (int) Math.round(((limit * 150) / limit));
			g2.setColor(Color.black);
			g2.fillRect(10, 30, limitPercentage, 30);
			g2.drawString("LIMIT", limitPercentage + 20, 40);
			g2.drawString("$" + limit, limitPercentage + 20, 55);

			// Calculate expense Percentage for drawing
			int expensePercentage = (int) Math.round(((expenses * 150) / limit));
			
			// Set bar color to red if total expenses > limit else green
			if (expensePercentage > limitPercentage)
				g2.setColor(Color.red);
			else
				g2.setColor(Color.green.brighter());
			
			// Set max limit for expenses bar length = 225 even if it goes beyond 225
			if (expensePercentage > 225)
				expensePercentage = 225;
			
			// Draw total expenses bar
			g2.fillRect(10, 70, expensePercentage, 30);
			g2.drawString("EXPENSES", expensePercentage + 20, 80);
			g2.drawString("$" + expenses, expensePercentage + 20, 95);
		}
	} // end method paintComponent

	// repaint graph when display is updated
	public void updateDisplay() {
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(360, 120);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}
