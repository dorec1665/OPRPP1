package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;


/**
 * Implementacija razreda {@link CalcModel}.
 * @author dario
 *
 */
public class CalcModelImpl implements CalcModel {

	private boolean isEditable;
	private boolean isNegative;
	private String inputString;
	private double inputNumber;
	private String frozenValue;
	
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners;
	private boolean isActiveOperandActive;
	
	
	/**
	 * Konstruktor.
	 */
	public CalcModelImpl() {
		inputString = "";
		inputNumber = 0;
		frozenValue = null;
		isEditable = true;
		isNegative = false;
		listeners = new ArrayList<>();
	}
	
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.add(l);
		
	}
	
	
	/**
	 * Obvještava sve promatrače o promjeni stanja.
	 */
	private void notifyListeners() {
		for(CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public double getValue() {
		return isNegative ? -1*inputNumber : inputNumber;
	}

	@Override
	public void setValue(double value) {
		this.inputNumber = value;
		
		if(Double.isInfinite(value) && value < 0) {
			inputString = "-Infinity";
		} else if(Double.isInfinite(value) && value > 0) {
			inputString = "Infinity";
		} else if(Double.isNaN(value)) {
			inputString = "NaN";
		} else {
			inputString = String.valueOf(value);
		}
		
		isEditable = false;
		notifyListeners();
		
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		inputNumber = 0;
		inputString = "";
		isEditable = true;
		isNegative = false;
		notifyListeners();
		
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		pendingOperation = null;
		clear();
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException();
		}
		
		isNegative = !isNegative;
		frozenValue = null;
		notifyListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException();
		}
		if(inputString.isEmpty()) {
			throw new CalculatorInputException("Number is empty!");
		}
		if(inputString.contains(".")) {
			throw new CalculatorInputException("Number is already decimal!");
		}
		inputString += ".";
		inputNumber = Double.parseDouble(inputString);
		frozenValue = null;
		notifyListeners();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) {
			throw new CalculatorInputException("Calculator is not edible!");
		}
		if(inputNumber*10 + digit > Double.MAX_VALUE) {
			throw new CalculatorInputException("Number is too big!");
		}
		
		String tmp = inputString;
		tmp += digit;
		try {
			inputNumber = Double.parseDouble(tmp);
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}
		frozenValue = null;
		if(inputString.equals("0") && digit == 0) {
			inputString = "0";
		} else {
			if(inputString.equals("0")) {
				inputString = "";
			}
			inputString += digit;
		}
		notifyListeners();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandActive;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandActive = true;
		
	}

	@Override
	public void clearActiveOperand() {
		isActiveOperandActive = false;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
	}

	@Override
	public void freezeValue(String value) {
		this.frozenValue = value;
		
	}

	@Override
	public boolean hasFrozenValue() {
		return frozenValue == null ? false : true;
	}

	@Override
	public String toString() {
		if(frozenValue != null) {
			if(frozenValue.contains(".")) {
				String[] tmp = frozenValue.split("\\.");
				if(tmp[1].equals("0")) {
					return tmp[0];
				}
			}
			return frozenValue;
		} else if(inputString == "" && isNegative) {
			return "-0";
		} else if(inputString == "" && !isNegative) {
			return "0";
		} else if(isNegative && !inputString.contains("-")){
			return "-" + inputString;
		} else {
			return inputString;
		}
	}
}
