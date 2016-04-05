package com.lvasanadu.tracker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;

import com.lvasanadu.tracker.model.Category;
import com.lvasanadu.tracker.model.ExpenseData;

/*
 * Visualization to show each category of expenses Vs Limit
 */
public class ExpenseBubbleView extends AbstractExpenseDataView {

	// List to maintain colors for bubbles
	private ArrayList<Color> colors = new ArrayList<Color>();

	public ExpenseBubbleView(ExpenseData data) {
		super(data);
		TitledBorder border = new TitledBorder("STATISTICS BY CATEGORY");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		setBorder(border);
		
		populateColors();
	}
	
	private void populateColors() {
		colors.add(new Color(247, 30, 106));
		colors.add(new Color(162, 118, 237));
		colors.add(new Color(74, 175, 37));
		colors.add(new Color(32, 115, 130));
		colors.add(new Color(239, 235, 177));
		colors.add(new Color(172, 77, 17));
		colors.add(new Color(7, 121, 177));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double limit = getExpenseData().getLimit();
		if (limit == 0)
			return;

		int x = 50, y = 50;
		int radius = (int) Math.round(((limit * 100) / limit)) + 50;
		
		// Write limit details
		g.setColor(Color.black);
		g.setFont(new Font("SansSerif", Font.BOLD, 10));
		int newX = (x + x + radius) / 2;
		int newY = y;
		g.drawString("LIMIT", newX, newY);
		newY += 10;
		g.drawString("$" + limit, newX, newY);
		newY += 10;
		g.drawString(100 + "%", newX, newY);

		// Draw limit bubble
		g.fillOval(x, newY + 5, radius, radius);

		int i = 0;
		for (Category category : Category.values()) {

			Double value = getExpenseData().getExpenses().get(category);
			
			if (value > 0) {
				g.setColor(colors.get(i));
				x += radius + 50;
				
				// Wrap up within canvas layouts
				if (x + radius * 2 >= getPreferredSize().getWidth()) {
					x = 50;
					y = y + 210;
				}

			
				int percentage = (int) Math.round(((value * 100) / limit));
				radius = percentage + 50;
				
				// Max radius of bubble
				if (radius > 180)
					radius = 180;
				
				// Min radius of bubble
				if (radius < 5)
					radius = 5;
				
				// Write category details
				g.setFont(new Font("SansSerif", Font.BOLD, 10));
				newX = (x + x + radius) / 2;
				newY = y;
				g.drawString(category.toString(), newX, newY);
				newY += 10;
				g.drawString("$" + value.toString(), newX, newY);
				newY += 10;
				g.drawString(percentage + "%", newX, newY);

				// Draw category bubble
				g.fillOval(x, newY + 5, radius, radius);
				
				i++;
			}
			
		}

	} // end method paintComponent

	// repaint graph when display is updated
	public void updateDisplay() {
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(1270, 470);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
