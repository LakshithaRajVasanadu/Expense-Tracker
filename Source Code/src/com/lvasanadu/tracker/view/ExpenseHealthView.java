package com.lvasanadu.tracker.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;

import com.lvasanadu.tracker.model.ExpenseData;

/*
 * View which gives alert based on expenses
 */
public class ExpenseHealthView extends AbstractExpenseDataView {

	private ImageIcon happyImageIcon;
	private ImageIcon sadImageIcon;
	private JLabel personLabel;
	private JLabel msgLabel;

	public ExpenseHealthView(ExpenseData observableData)
			throws NullPointerException {
		super(observableData);

		TitledBorder border = new TitledBorder("FINANCIAL HEALTH");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		setBorder(border);
		setBackground(Color.LIGHT_GRAY);

		ImageIcon imageIcon = new ImageIcon("resources/happyIcon.png");
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(200, 300,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		happyImageIcon = new ImageIcon(newimg); // transform it back

		ImageIcon imageIcon1 = new ImageIcon("resources/sadIcon.png");
		Image image1 = imageIcon1.getImage(); // transform it
		Image newimg1 = image1.getScaledInstance(200, 300,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		sadImageIcon = new ImageIcon(newimg1); // transform it back

		// Label for sad / happy person.
		personLabel = new JLabel();
		if (getExpenseData().getTotalExpense() > getExpenseData().getLimit())
			personLabel.setIcon(sadImageIcon);
		else
			personLabel.setIcon(happyImageIcon);
		personLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

		// Label for alert msg
		msgLabel = new JLabel();
		TitledBorder msgborder = new TitledBorder("ADVISE");
		msgborder.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 14));
		msgLabel.setBorder(msgborder);
		msgLabel.setOpaque(true);

		// Set msg based on expense %
		Double value = getExpenseData().getTotalExpense() * 100
				/ getExpenseData().getLimit();

		if (value > 100) {
			msgLabel.setText("You are in debt!!");
			msgLabel.setBackground(Color.red);

		} else if (value <= 50) {
			msgLabel.setText("You are doing great!!");
			msgLabel.setBackground(Color.green);

		}
		else {
			msgLabel.setText("Watch out your expenses!!");
			msgLabel.setBackground(Color.orange);

		}

		JPanel inner2 = new JPanel();
		inner2.add(msgLabel);
		inner2.setBackground(Color.LIGHT_GRAY);
		inner2.setSize(50, 50);

		innerPanel.add(inner2, LEFT_ALIGNMENT);
		innerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		innerPanel.add(new ExpenseDataBarGraphView(observableData),
				CENTER_ALIGNMENT);
		innerPanel.setBackground(Color.LIGHT_GRAY);

		add(personLabel);
		add(innerPanel);

	}

	@Override
	protected void updateDisplay() {
		// Set image
		if (getExpenseData().getTotalExpense() > getExpenseData().getLimit())
			personLabel.setIcon(sadImageIcon);
		else
			personLabel.setIcon(happyImageIcon);

		// Set alert message
		if (getExpenseData().getLimit() > 0) {
			int value = getExpenseData().getTotalExpense().intValue() * 100
					/ getExpenseData().getLimit().intValue();
			if (value > 100) {
				msgLabel.setText("You are in debt!!");
				msgLabel.setBackground(Color.red);
			} else if (value <= 50) {
				msgLabel.setText("You are doing great!!");
				msgLabel.setBackground(Color.green);
			} else {
				msgLabel.setText("Watch out your expenses!!");
				msgLabel.setBackground(Color.orange);
			}
		}

	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 375);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
