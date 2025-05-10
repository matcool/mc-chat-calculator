package mat.chatcalc.parser;

public class Parser {
	private Lexer lexer;
	private VariableProvider variables;

	protected Parser(Lexer lexer, VariableProvider variables) {
		this.lexer = lexer;
		this.variables = variables;
	}

	public static double parse(String input, VariableProvider variables) {
		Parser parser = new Parser(new Lexer(input), variables);
		return parser.evalExpression();
	}

	protected double evalExpression() {
		return this.evalExpression(0);
	}

	protected double evalExpression(int precedence) {
		var left = this.evalExpressionPrefix(lexer.next());
		var result = left;
		while (precedence < this.getNextInfixPrec()) {
			result = this.evalExpressionInfix(lexer.next(), result);
		}
		return result;
	}

	protected double evalExpressionPrefix(Token token) {
		switch (token.getType()) {
			case NUMBER:
				return Double.parseDouble(token.getValue());
			case NAME:
				// TODO: check null
				return variables.valueForVariable(token.getValue());
			case LPAREN: {
				double value = this.evalExpression();
				// TODO: expect RPAREN afterwards
				lexer.next();
				return value;
			}
			case MINUS:
				return -this.evalExpression(Precedence.PREFIX);
			default:
				throw new IllegalStateException("Invalid token!");
		}
	}

	protected int getNextInfixPrec() {
		var token = lexer.peek();
		return token.getPrecedence();
	}

	protected double evalExpressionInfix(Token token, double left) {
		switch (token.getType()) {
			case PLUS:
			case MINUS:
			case MULTIPLY:
			case DIVIDE: {
				var prec = token.getPrecedence();
				if (token.isRightAssociative()) {
					prec -= 1;
				}
				var right = this.evalExpression(prec);
				switch (token.getType()) {
					case PLUS:
						return left + right;
					case MINUS:
						return left - right;
					case MULTIPLY:
						return left * right;
					case DIVIDE:
						return left / right;
					// unreachable
					default:
						return 0.0;
				}
			}
			default:
				return 0.0;
		}
	}
}
