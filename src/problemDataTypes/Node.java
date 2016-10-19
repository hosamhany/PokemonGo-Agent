package problemDataTypes;

import abstractDataTypes.State;

public class Node {
	private int depth;
	private int pathCost;
	private String operator;
	private Node parent;
	private State state;
	private int fn;

	public Node(State state) {
		this.state = state;
	}

	public Node() {

	}

	public Node(Node parent, State state, String operator, int pathCost, int fn) {
		this(state);
		this.parent = parent;
		this.operator = operator;
		this.pathCost = pathCost;
		this.fn = fn;
	}

	public Node(Node parent, State state, String operator, int pathCost) {
		this(parent, state, operator, pathCost, pathCost);
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
	}

	public String toString() {
		String s = "State: " + this.state + "\n" + "Depth: " + this.depth + "\n" + "Path Cost: " + this.pathCost + "\n"
				+ "Operator: " + this.operator;
		return s;
	}

	//
	// @Override
	// public int compareTo(Object o) {
	// Integer thisPathCost = new Integer(this.pathCost);
	// Integer otherPathCost = new Integer(((Node) o).pathCost);
	// return thisPathCost.compareTo(otherPathCost);
	// }
}
