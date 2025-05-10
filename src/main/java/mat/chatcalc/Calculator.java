package mat.chatcalc;

import java.util.HashMap;
import java.util.Map;
import mat.chatcalc.parser.ParseException;
import mat.chatcalc.parser.Parser;
import mat.chatcalc.parser.VariableProvider;

public class Calculator implements VariableProvider {
	private Map<String, Double> variables = new HashMap<>();

	public Calculator() {
		this.reset();
	}

	public void reset() {
		variables.clear();
		variables.put("_", 0.0);
	}

	public double execute(String input) throws ParseException {
		if (input == "clear()") {
			// hacky i know
			this.reset();
			return 0.0;
		} else {
			double value = Parser.parse(input, this);
			this.assignVariable("_", value);
			return value;
		}
	}

	@Override
	public Double valueForVariable(String variable) {
		return variables.get(variable);
	}

	@Override
	public void assignVariable(String variable, double value) {
		variables.put(variable, value);
	}

	public static void main(String[] args) {
		try {
			Calculator calc = new Calculator();
			System.out.println(calc.execute("a = 2"));
			System.out.println(calc.execute("a * 2"));
			System.out.println(calc.execute("_ + 1"));
			System.out.println(calc.execute("clear()"));
			System.out.println(calc.execute("a + 2"));
		} catch (ParseException e) {
			System.err.println("Error: " + e);
		}
	}
}
