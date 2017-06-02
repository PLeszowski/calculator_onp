package com.company;

import java.util.Scanner;

/**
 * Created by RENT on 2017-05-29.
 */
public class Calculator {

	private double result = 0;


	public Calculator() {
	}

	public double eval (double x, double y, String operator) throws DivisionByZeroException {


		switch (operator) {

			case "+":
				result = x + y;
				break;
			case "-":
				result = x - y;
				break;
			case "*":
				result = x * y;
				break;
			case "/":
				if (y != 0)
					result = x / y;
				else
					throw new DivisionByZeroException();
				break;

			default:
				break;


		}

		return result;
	}

}
