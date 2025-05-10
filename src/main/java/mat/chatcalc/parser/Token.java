package mat.chatcalc.parser;

public class Token {
	public enum Type {
		// @formatter:off
		EOF,
		NUMBER,
		NAME,
		ADD,
		SUB,
		MULT,
		DIV,
		MOD,
		EXP,
		LPAREN,
		RPAREN,
		EQUAL,
		// @formatter:on
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
			case ADD:
			case SUB:
				return Precedence.SUM;
			case MULT:
			case DIV:
			case MOD:
				return Precedence.PRODUCT;
			case EXP:
				return Precedence.EXPONENT;
			case EQUAL:
				return Precedence.ASSIGNMENT;
			default:
				return 0;
		}
	}

	public boolean isRightAssociative() {
		switch (this.getType()) {
			case EQUAL:
			case EXP:
				return true;
			default:
				return false;
		}
	}
}
