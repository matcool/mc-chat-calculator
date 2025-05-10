package mat.chatcalc.parser;

public interface VariableProvider {
	public Double valueForVariable(String variable);

	public void assignVariable(String variable, double value);
}
