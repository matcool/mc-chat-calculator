package mat.chatcalc.parser;

public class Precedence {
	public static final int ASSIGNMENT = 1;
	public static final int SUM = 2;
	public static final int PRODUCT = 3;
	public static final int EXPONENT = 4;
	public static final int PREFIX = 5;
	public static final int POSTFIX = 6;
	public static final int CALL = 7;
}
