package com.lvasanadu.tracker.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.lvasanadu.tracker.model.ExpenseData;

public class ExpenseDataSummaryView extends AbstractExpenseDataView {

	private JLabel budgetValueLabel = new JLabel();
	private JLabel expenseValueLabel = new JLabel();
	private NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

	public ExpenseDataSummaryView(ExpenseData data) {
		super(data);
		preparePanel();
	}

	private void preparePanel() {

		TitledBorder border = new TitledBorder("FINANCIAL SUMMARY");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		setBorder(border);
		setBackground(Color.LIGHT_GRAY);
		
		add(Box.createRigidArea(new Dimension(100, 30)));
		add(getBudgetPanel());
		add(Box.createRigidArea(new Dimension(10, 20)));
		add(getExpensePanel());

	}

	private JPanel getBudgetPanel() {
		JPanel budgetInnerPanel = new JPanel();
		budgetInnerPanel.setLayout(new BoxLayout(budgetInnerPanel,
				BoxLayout.Y_AXIS));

		JLabel budgetLabel = new JLabel();
		budgetLabel.setText("BUDGET");
		budgetLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 14));

		budgetValueLabel.setText(moneyFormat
				.format(getExpenseData().getLimit()));

		budgetInnerPanel.add(budgetLabel);
		budgetInnerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		budgetInnerPanel.add(budgetValueLabel);
		budgetInnerPanel.setBackground(Color.LIGHT_GRAY);

		ImageIcon imageIcon = new ImageIcon("resources/budgetIcon.png");
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		JLabel budgetIconLabel = new JLabel(imageIcon);
		budgetIconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel budgetPanel = new JPanel();
		budgetPanel.add(budgetIconLabel);
		budgetPanel.add(budgetInnerPanel);
		budgetPanel.setAlignmentX(LEFT_ALIGNMENT);
		budgetPanel.setAlignmentY(CENTER_ALIGNMENT);
		budgetPanel.setBackground(Color.LIGHT_GRAY);
		// budgetPanel.setBorder(new TitledBorder("BudgetPanel"));

		return budgetPanel;

	}

	private JPanel getExpensePanel() {
		JPanel expenseInnerPanel = new JPanel();
		expenseInnerPanel.setLayout(new BoxLayout(expenseInnerPanel,
				BoxLayout.Y_AXIS));

		JLabel expenseLabel = new JLabel();
		expenseLabel.setText("EXPENSE");
		expenseLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 14));

		expenseValueLabel.setText(moneyFormat.format(getExpenseData()
				.getTotalExpense()));

		expenseInnerPanel.add(expenseLabel);
		expenseInnerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		expenseInnerPanel.add(expenseValueLabel);
		expenseInnerPanel.setBackground(Color.LIGHT_GRAY);

		ImageIcon imageIcon = new ImageIcon("resources/expenseIcon.png");
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(80, 80,
				java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		JLabel expenseIconLabel = new JLabel(imageIcon);
		expenseIconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel expensePanel = new JPanel();
		expensePanel.add(expenseIconLabel);
		expensePanel.add(Box.createRigidArea(new Dimension(10, 10)));
		expensePanel.add(expenseInnerPanel);
		expensePanel.setAlignmentX(RIGHT_ALIGNMENT);
		expensePanel.setBackground(Color.LIGHT_GRAY);
		return expensePanel;

	}

	@Override
	protected void updateDisplay() {
		budgetValueLabel.setText(moneyFormat
				.format(getExpenseData().getLimit()));
		expenseValueLabel.setText(moneyFormat.format(getExpenseData()
				.getTotalExpense()));

	}

	// get AccountBarGraphView's preferred size
	public Dimension getPreferredSize() {
		return new Dimension(250, 375);
	}

	// get AccountBarGraphView's minimum size
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	// get AccountBarGraphView's maximum size
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
