package mat.chatcalc;

import java.util.HashMap;
import java.util.Map;
import mat.chatcalc.parser.Lexer;
import mat.chatcalc.parser.ParseException;
import mat.chatcalc.parser.Parser;
import mat.chatcalc.parser.Token;
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

	public void execute(String input) {

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
			String input = "2^1^3";
			Lexer lexer = new Lexer(input);
			Token token;
			do {
				token = lexer.next();
				System.out.println(token);
			} while (token.getType() != Token.Type.EOF);
			Calculator calc = new Calculator();
			System.out.println(Parser.parse(input, calc));
			System.out.println(Parser.parse("a = 2", calc));
			System.out.println(Parser.parse("a * 2", calc));
		} catch (ParseException e) {
			System.err.println("Error: " + e);
		}
	}
}
