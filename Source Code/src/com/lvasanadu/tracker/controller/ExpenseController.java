package com.lvasanadu.tracker.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.lvasanadu.tracker.model.Category;
import com.lvasanadu.tracker.model.ExpenseData;
import com.lvasanadu.tracker.view.AbstractExpenseDataView;

// Expense Manager Panel to add/edit data

public class ExpenseController extends AbstractExpenseDataView {

	private JPanel newPanel;
	private JTextField limitTextField;
	JPanel categoryPanel;
	JComboBox<Object> categoryList;

	public ExpenseController(ExpenseData controlledData) {
		super(controlledData);

		TitledBorder border = new TitledBorder("EXPENSE MANAGER");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 18));
		setBorder(border);
		setLayout(new FlowLayout());

		prepareLimitPanel();

		newPanel = getAddExpensePanel();
		add(newPanel);

		// Disable adding of expenses when limit is 0
		if (getExpenseData().getLimit() == 0)
			setPanelEnabled(newPanel, false);

		add(viewPanel());

	}

	// Set Limit and Reset Panel
	private void prepareLimitPanel() {
		limitTextField = new JTextField(10);
		limitTextField.setText(getExpenseData().getLimit().toString());

		JButton setLimitButton = new JButton("Set Limit Per Day");
		setLimitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				try {
					double limit = Double.parseDouble(limitTextField.getText());

					if (limit <= 0) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid amount", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else

						getExpenseData().setLimit(limit);
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null,
							"Please enter a valid amount", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} // end method actionPerformed
		});

		JButton clearLimitButton = new JButton("Reset");
		clearLimitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				int response = JOptionPane
						.showConfirmDialog(null, "Do you want to clear?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					getExpenseData().clear();
				}
			}
		});

		add(new JLabel("Limit: "));
		add(limitTextField);
		add(setLimitButton);
		add(clearLimitButton);

		categoryPanel = new JPanel(new FlowLayout());
		categoryList = new JComboBox<Object>(Category.values());
		categoryList.insertItemAt("--Choose one--", 0);
		categoryList.setSelectedIndex(0);

		categoryPanel.add(new JLabel("Category: "));
		categoryPanel.add(categoryList);
		categoryPanel.setBackground(Color.LIGHT_GRAY);

		add(categoryPanel);

	}

	// Add Expenses Panel
	private JPanel getAddExpensePanel() {
		JPanel addExpensePanel = new JPanel();
		addExpensePanel.setLayout(new BoxLayout(addExpensePanel,
				BoxLayout.Y_AXIS));
		addExpensePanel.setBackground(Color.LIGHT_GRAY);

		JPanel amountPanel = new JPanel(new FlowLayout());
		JTextField amountTextField = new JTextField(12);
		amountPanel.add(new JLabel("Amount: "));
		amountPanel.add(amountTextField);
		amountPanel.setBackground(Color.LIGHT_GRAY);

		JButton addButton = new JButton("Add Expense");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Category category = Category.OTHERS;

					if (categoryList.getSelectedIndex() > 0) {
						category = (Category) categoryList
								.getItemAt(categoryList.getSelectedIndex());

						Double amount = Double.parseDouble(amountTextField
								.getText());
						if (amount > 0) {
							getExpenseData().addExpense(category, amount);
							categoryList.setSelectedIndex(0);
							amountTextField.setText("");
						} else {
							JOptionPane.showMessageDialog(null,
									"Please enter a valid amount", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null,
								"Please choose a category", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null,
							"Please enter a valid amount", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		addExpensePanel.add(amountPanel);
		addExpensePanel.add(Box.createRigidArea(new Dimension(100, 0)));
		addExpensePanel.add(addButton);

		TitledBorder border = new TitledBorder("Add Expense");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 14));
		addExpensePanel.setBorder(border);

		return addExpensePanel;
	}

	// View expenses by category chosen - for a day or specific time of the day
	private JPanel viewPanel() {
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBackground(Color.LIGHT_GRAY);

		TitledBorder border = new TitledBorder("View By Category");
		border.setTitleFont(new Font("TimesNewRoman", Font.BOLD, 14));
		viewPanel.setBorder(border);

		JPanel startTimePanel = new JPanel();
		JSpinner shours = new JSpinner(new SpinnerNumberModel(1, 1, 24, 1));
		JSpinner sminutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		startTimePanel.add(new JLabel("Start Time     "));
		startTimePanel.add(new JLabel("Hrs:"));
		startTimePanel.add(shours);
		startTimePanel.add(new JLabel("Mins:"));
		startTimePanel.add(sminutes);
		startTimePanel.setBackground(Color.LIGHT_GRAY);

		JPanel endTimePanel = new JPanel();
		JSpinner ehours = new JSpinner(new SpinnerNumberModel(1, 1, 24, 1));
		JSpinner eminutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

		endTimePanel.add(new JLabel(" End Time     "));
		endTimePanel.add(new JLabel("Hrs:"));
		endTimePanel.add(ehours);
		endTimePanel.add(new JLabel("Mins:"));
		endTimePanel.add(eminutes);
		endTimePanel.setBackground(Color.LIGHT_GRAY);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);

		JButton viewButton = new JButton("View");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Category category = Category.OTHERS;

				if (categoryList.getSelectedIndex() > 0) {
					category = (Category) categoryList.getItemAt(categoryList
							.getSelectedIndex());

					JOptionPane
							.showMessageDialog(
									null,
									"You have spent $"
											+ getExpenseData()
													.getTotalExpenseByCategory(
															category) + " for "
											+ category.toString(), "Info",
									JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null,
							"Please choose a category", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		JButton viewTimeButton = new JButton("View By Time");
		viewTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Category category = Category.OTHERS;

				if (categoryList.getSelectedIndex() > 0) {
					category = (Category) categoryList.getItemAt(categoryList
							.getSelectedIndex());

					Calendar startTime = Calendar.getInstance();
					startTime.set(Calendar.HOUR_OF_DAY,
							(Integer) shours.getValue());
					startTime.set(Calendar.MINUTE,
							(Integer) sminutes.getValue());
					startTime.set(Calendar.SECOND, 0);

					Calendar endTime = Calendar.getInstance();
					endTime.set(Calendar.HOUR_OF_DAY,
							(Integer) ehours.getValue());
					endTime.set(Calendar.MINUTE, (Integer) eminutes.getValue());
					endTime.set(Calendar.SECOND, 0);

					if (startTime.getTimeInMillis() > endTime.getTimeInMillis()) {
						JOptionPane.showMessageDialog(null,
								"Start Time can not be greater than End time",
								"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					JOptionPane.showMessageDialog(
							null,
							"You have spent $"
									+ getExpenseData()
											.getTotalExpenseByCategoryTime(
													category, startTime,
													endTime) + " for "
									+ category.toString(), "Info",
							JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null,
							"Please choose a category", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		buttonPanel.add(viewButton);
		buttonPanel.add(viewTimeButton);

		viewPanel.add(startTimePanel);
		viewPanel.add(endTimePanel);
		viewPanel.add(buttonPanel);

		return viewPanel;

	}

	// Enable/Disable a panel with its components
	private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelEnabled((JPanel) components[i], isEnabled);
			}

			components[i].setEnabled(isEnabled);
		}
	}

	@Override
	protected void updateDisplay() {
		if (getExpenseData().getLimit() == 0)
			setPanelEnabled(newPanel, false);
		else
			setPanelEnabled(newPanel, true);

	}

	public Dimension getPreferredSize() {
		return new Dimension(300, 375);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}
