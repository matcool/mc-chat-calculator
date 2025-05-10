package mat.chatcalc.parser;

public class Token {
	public enum Type {
		EOF, NUMBER, NAME, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN,
	}

	private final Type type;
	private final String value;
	private final int offset;

	public Token(Type type, String value, int offset) {
		this.type = type;
		this.value = value;
		this.offset = offset;
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		return "Token[" + type + ", \"" + value + "\", @" + offset + "]";
	}

	public int getPrecedence() {
		switch (this.getType()) {
			case LPAREN:
				return Precedence.POSTFIX;
			case PLUS:
			case MINUS:
				return Precedence.SUM;
			case MULTIPLY:
			case DIVIDE:
				return Precedence.PRODUCT;
			default:
				return 0;
		}
	}

	public boolean isRightAssociative() {
		return false;
	}
}
