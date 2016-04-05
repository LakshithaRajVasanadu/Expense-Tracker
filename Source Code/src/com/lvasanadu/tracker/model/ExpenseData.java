package com.lvasanadu.tracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import com.lvasanadu.tracker.helper.ExpenseDataSerializer;

/*
 * Model to maintain expense data
 */
public class ExpenseData extends Observable implements Serializable {

	private Double limit;
	private Calendar timeForNextClear;
	
	// Store total expenses for each category - faster lookup for visualization
	private HashMap<Category, Double> expenses;
	
	// Store each expense along with time
	private HashMap<Category, ArrayList<SingleExpense>> expensesByTime;

	public ExpenseData() {
		expenses = new HashMap<Category, Double>();
		expensesByTime = new HashMap<Category, ArrayList<SingleExpense>>();
		initializeExpenses();
		setLimit(Double.valueOf(1000));
		setTimeForNextClear();
	}

	public Calendar getTimeForNextClear() {
		return timeForNextClear;
	}

	// Set time to clear the data and save it - clears data at 12AM every day
	public void setTimeForNextClear() {
		timeForNextClear = Calendar.getInstance();
		timeForNextClear.set(Calendar.HOUR_OF_DAY, 24);
		timeForNextClear.set(Calendar.MINUTE, 0);
		ExpenseDataSerializer.serialize(this);
	}

	private void initializeExpenses() {
		for (Category category : Category.values()) {
			expenses.put(category, Double.valueOf(0));
			ArrayList<SingleExpense> catSingleExpenses = new ArrayList<SingleExpense>();
			expensesByTime.put(category, catSingleExpenses);
		}
	}

	public void setLimit(Double limit) {
		this.limit = limit;
		ExpenseDataSerializer.serialize(this);
		setChanged();
		notifyObservers();
	}

	public HashMap<Category, Double> getExpenses() {
		return expenses;
	}

	public Double getLimit() {
		return limit;
	}

	public void clear() {
		initializeExpenses();
		setTimeForNextClear();
		setChanged();
		notifyObservers();
		ExpenseDataSerializer.serialize(this);
	}

	public void addExpense(Category category, Double value) {
		expenses.put(category, expenses.get(category) + value);
		
		SingleExpense expense = new SingleExpense(Calendar.getInstance(), value);
		expensesByTime.get(category).add(expense);
		
		ExpenseDataSerializer.serialize(this);
		setChanged();
		notifyObservers();
	}

	public Double getTotalExpense() {
		Double total = 0d;
		
		for (Map.Entry<Category, Double> entry : expenses.entrySet()) {
			total += entry.getValue();
		}

		return total;
	}
	
	public double getTotalExpenseByCategory(Category category) {
		double value = 0;
		value = expenses.get(category);
		return value;
	}
	
	public double getTotalExpenseByCategoryTime(Category category, Calendar start, Calendar end) {
		double value = 0;
		ArrayList<SingleExpense> expenses = expensesByTime.get(category);
		for(SingleExpense expense : expenses) {
			if(start.getTimeInMillis() <= expense.getTime().getTimeInMillis() 
					&& expense.getTime().getTimeInMillis() <= end.getTimeInMillis()) {
				value += expense.getValue();
			}
		}
		return value;
	}
}