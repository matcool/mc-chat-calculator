package mat.chatcalc.parser;

import java.util.Iterator;

public class Lexer implements Iterator<Token> {
	private final String input;
	private int index = 0;
	private Token peeked = null;

	public Lexer(String input) {
		this.input = input;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Token next() {
		if (peeked != null) {
			var token = peeked;
			peeked = null;
			return token;
		}
		while (index < input.length()) {
			char c = input.charAt(index);
			final int startIndex = index;
			++index;

			if (Character.isWhitespace(c)) {
				continue;
			}

			Token.Type type = null;
			switch (c) {
				case '+':
					type = Token.Type.ADD;
					break;
				case '-':
					type = Token.Type.SUB;
					break;
				case '*':
					type = Token.Type.MULT;
					break;
				case '/':
					type = Token.Type.DIV;
					break;
				case '(':
					type = Token.Type.LPAREN;
					break;
				case ')':
					type = Token.Type.RPAREN;
					break;
				case '=':
					type = Token.Type.EQUAL;
					break;
				case '^':
					type = Token.Type.EXP;
					break;
			}

			String value = "" + c;
			if (type == null) {
				// either a name or a number..
				if (c >= '0' && c <= '9') {
					type = Token.Type.NUMBER;
				} else {
					type = Token.Type.NAME;
				}
				while (index < input.length()) {
					c = input.charAt(index);
					if (!Lexer.isAlphaNum(c)) {
						break;
					}
					++index;
					value += c;
				}
			}
			return new Token(type, value, startIndex);
		}
		return new Token(Token.Type.EOF, "", index);
	}

	public Token peek() {
		if (peeked == null) {
			peeked = this.next();
		}
		return peeked;
	}

	private static boolean isAlphaNum(char c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_') || (c == '.');
	}
}
