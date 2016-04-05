package com.lvasanadu.tracker.helper;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.lvasanadu.tracker.model.ExpenseData;

/*
 * Write/Read Expense Data to a file
 */
public class ExpenseDataSerializer {

	private static String filename = "resources/expenseData.ser";

	// Write object to a file
	public static void serialize(ExpenseData expenseData) {
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		try {
			fout = new FileOutputStream(filename);
			out = new ObjectOutputStream(fout);
			out.writeObject(expenseData);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Read object from a file
	public static ExpenseData deSerialize() {
		ExpenseData expenseData = null;
		FileInputStream fis = null;
		ObjectInputStream fin = null;
		try {
			fis = new FileInputStream(filename);
			fin = new ObjectInputStream(fis);
			Object obj = fin.readObject();
			expenseData = (ExpenseData) obj;

		} catch (EOFException eofException) {
			return expenseData;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fin != null)
					fin.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		return expenseData;
	}

}
