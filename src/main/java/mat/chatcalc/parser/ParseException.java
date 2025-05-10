package mat.chatcalc.parser;

public class ParseException extends Exception {
	private final int offset;

	public ParseException(String message, int offset) {
		super(message);
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	@Override
	public String toString() {
		return "ParseException [offset=" + offset + ", getMessage()=" + this.getMessage() + "]";
	}
}
