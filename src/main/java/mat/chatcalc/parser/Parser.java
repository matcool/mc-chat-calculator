package mat.chatcalc.parser;

public class Parser {
	private Lexer lexer;
	private VariableProvider variables;

	protected Parser(Lexer lexer, VariableProvider variables) {
		this.lexer = lexer;
		this.variables = variables;
	}

	public static double parse(String input, VariableProvider variables) throws ParseException {
		Parser parser = new Parser(new Lexer(input), variables);

		double value = parser.parseExpr();
		var lastToken = parser.lexer.next();
		if (lastToken.getType() != Token.Type.EOF) {
			throw new ParseException("Unexpected token " + lastToken.getType(), lastToken.getOffset());
		}
		return value;
	}

	protected double parseExpr() throws ParseException {
		return this.parseExpr(0);
	}

	protected double parseExpr(int precedence) throws ParseException {
		var left = this.parseExprPrefix(lexer.next());
		var result = left;
		while (precedence < this.getNextInfixPrec()) {
			result = this.parseExprInfix(lexer.next(), result);
		}
		return result;
	}

	protected double parseExprPrefix(Token token) throws ParseException {
		switch (token.getType()) {
			case NUMBER:
				return Double.parseDouble(token.getValue());
			case NAME: {
				if (lexer.peek().getType() == Token.Type.LPAREN) {
					// function call!
					lexer.next(); // LPAREN
					var func = token.getValue();
					double value = this.parseExpr();
					var next = lexer.next();
					if (next.getType() != Token.Type.RPAREN) {
						throw new ParseException("Expected right paren", next.getOffset());
					}
					return this.handleFuncCall(func, value, token.getOffset());
				} else if (lexer.peek().getType() == Token.Type.EQUAL) {
					// assignment!
					lexer.next(); // EQUAL
					double value = this.parseExpr();
					variables.assignVariable(token.getValue(), value);
					return value;
				} else {
					var value = variables.valueForVariable(token.getValue());
					if (value == null) {
						throw new ParseException("Unknown variable " + token.getValue(), token.getOffset());
					}
					return value;
				}
			}
			case LPAREN: {
				double value = this.parseExpr();
				var next = lexer.next();
				if (next.getType() != Token.Type.RPAREN) {
					throw new ParseException("Expected right paren", next.getOffset());
				}
				return value;
			}
			case SUB:
				return -this.parseExpr(Precedence.PREFIX);
			default:
				throw new ParseException("Unexpected token " + token.getType(), token.getOffset());
		}
	}

	protected int getNextInfixPrec() {
		var token = lexer.peek();
		return token.getPrecedence();
	}

	protected double parseExprInfix(Token token, double left) throws ParseException {
		switch (token.getType()) {
			case ADD:
			case SUB:
			case MULT:
			case DIV:
			case MOD:
			case EXP: {
				var prec = token.getPrecedence();
				if (token.isRightAssociative()) {
					prec -= 1;
				}
				var right = this.parseExpr(prec);
				switch (token.getType()) {
					case ADD:
						return left + right;
					case SUB:
						return left - right;
					case MULT:
						return left * right;
					case DIV:
						if (right == 0.0) {
							throw new ParseException("Division by zero", token.getOffset());
						}
						return left / right;
					case MOD:
						if (right == 0.0) {
							throw new ParseException("Modulo by zero", token.getOffset());
						}
						return left % right;
					case EXP:
						return Math.pow(left, right);
					// unreachable
					default:
						return 0.0;
				}
			}
			default:
				throw new ParseException("Unexpected token " + token.getType(), token.getOffset());
		}
	}

	protected double handleFuncCall(String func, double value, int offset) throws ParseException {
		switch (func) {
			case "sqrt":
				return Math.sqrt(value);
			case "floor":
				return Math.floor(value);
			case "ceil":
				return Math.ceil(value);
			case "round":
				return Math.round(value);
			case "abs":
				return Math.abs(value);
			case "sin":
				return Math.sin(value);
			case "cos":
				return Math.cos(value);
			case "tan":
				return Math.tan(value);
			default:
				throw new ParseException("Unknown function " + func, offset);
		}
	}
}
