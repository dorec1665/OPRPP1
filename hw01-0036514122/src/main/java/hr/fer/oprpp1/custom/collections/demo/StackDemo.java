package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Program prima samo 1 argument iz komandne linije.");
		}
		
		String expression = args[0];
		
		String[] splitExpression = expression.split("\\s+");
		
		ObjectStack stack = new ObjectStack(4);
		
		int number, operand1, operand2;
		char operator;
		for(String str : splitExpression) {
			if(isInteger(str)) {
				number = Integer.parseInt(str);
				stack.push(number);
			} else {
				if(str.equals("*")) {
					operand2 = (Integer) stack.pop();
					operand1 = (Integer) stack.pop();
					stack.push(operand1*operand2);
				} else if(str.equals("/")) {
					operand2 = (Integer) stack.pop();
					operand1 = (Integer) stack.pop();
					if(operand2 == 0) {
						System.out.println("Nije dozvoljeno dijelit s 0!");
						System.exit(0);
					}
					stack.push(operand1/operand2);
				} else if(str.equals("+")) {
					operand2 = (Integer) stack.pop();
					operand1 = (Integer) stack.pop();
					stack.push(operand1+operand2);
				} else if(str.equals("-")) {
					operand2 = (Integer) stack.pop();
					operand1 = (Integer) stack.pop();
					stack.push(operand1-operand2);
				} else if(str.equals("%")) {
					operand2 = (Integer) stack.pop();
					operand1 = (Integer) stack.pop();
					if(operand2 == 0) {
						System.out.println("Nije dozvoljeno dijelit s 0!");
						System.exit(0);
					}
					stack.push(operand1%operand2);
				} else {
					System.out.println("Unesena nepoznata oznaka.");
					System.exit(0);
				}
			}
		}
		
		if(stack.getArray().size() != 1) {
			System.out.println("Nije unesen valjan argument");
		} else {
			System.out.println("Rezultat evaulacije je:" + stack.pop());
		}
	}
	
	public static boolean isInteger(String str) {
		if(str == null) {
			return false;
		}
		
		if(str.length() == 0) {
			return false;
		}
		
		int i = 0;
	    if (str.charAt(0) == '-') {
	        if (str.length() == 1) {
	            return false;
	        }
	        i = 1;
	    }
		
		for(; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c < '0' || c > '9') {
				return false;
			}
		}
		
		return true;
	}
}
