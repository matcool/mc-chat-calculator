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

	public static void main(String[] args) {
		try {
			String input = "2 ^ 3 ^ 2";
			Lexer lexer = new Lexer(input);
			Token token;
			do {
				token = lexer.next();
				System.out.println(token);
			} while (token.getType() != Token.Type.EOF);
			System.out.println(Parser.parse(input, new Calculator()));
		} catch (ParseException e) {
			System.err.println("Error: " + e);
		}
	}
}
