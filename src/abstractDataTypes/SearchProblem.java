package abstractDataTypes;

import problemDataTypes.Node;

public abstract class SearchProblem {
	private State initialState;
	private Operator[] operators;
	
	public State getInitialState() {
		return initialState;
	}
	
	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}
	
	public Operator[] getOperators() {
		return operators;
	}
	
	public void setOperators(Operator[] operators) {
		this.operators = operators;
	}
	
	public abstract boolean isGoal(Node node);
	
	public abstract Node[] expand(Node node);
	
	public abstract Node apply(Operator operator, Node node);
}
