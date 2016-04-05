package com.lvasanadu.tracker;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lvasanadu.tracker.controller.ExpenseController;
import com.lvasanadu.tracker.helper.ExpenseDataSerializer;
import com.lvasanadu.tracker.helper.MyTimer;
import com.lvasanadu.tracker.model.ExpenseData;
import com.lvasanadu.tracker.view.ExpenseBubbleView;
import com.lvasanadu.tracker.view.ExpenseDataSummaryView;
import com.lvasanadu.tracker.view.ExpenseHealthView;

/*
 * Main class for Expense Tracker UI
 */
public class ExpenseTrackerDashboard extends JFrame {

	public ExpenseTrackerDashboard(ExpenseData data) {

		super("My Expense Tracker");

		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		// Add different views for the expense data
		JPanel innerPanel = new JPanel();
		innerPanel.add(new ExpenseDataSummaryView(data));
		innerPanel.add(Box.createRigidArea(new Dimension(50, 20)));
		innerPanel.add(new ExpenseHealthView(data));
		innerPanel.add(Box.createRigidArea(new Dimension(50, 20)));
		innerPanel.add(new ExpenseController(data));
		innerPanel.setBackground(Color.black);

		contentPane.add(innerPanel);
		contentPane.add(new ExpenseBubbleView(data));
		
		contentPane.setBackground(Color.black);

		setSize(1290, 905);
		setLocationRelativeTo(null);
	}

	public static void createAndShowGUI(ExpenseData data) {
		ExpenseTrackerDashboard tracker = new ExpenseTrackerDashboard(data);
		tracker.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tracker.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ExpenseData data = ExpenseDataSerializer.deSerialize();
				if (data == null) {
					data = new ExpenseData();
				}
				// Start a timer to clear data
				new MyTimer(data);
				
				//Display UI
				createAndShowGUI(data);
			}
		});
	}

}