package abstractDataTypes;

import java.util.ArrayList;

import problemDataTypes.Node;

public abstract class SearchProblem {
	private State initialState;
	private String[] operators;
	
	public State getInitialState() {
		return initialState;
	}
	
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	public String[] getOperators() {
		return operators;
	}
	
	public void setOperators(String[] operators) {
		this.operators = operators;
	}
	
	public abstract boolean isGoal(Node node);
	
	public abstract ArrayList<Node> expand(Node node);
	
	public abstract Node apply(String operator, Node node);
}
