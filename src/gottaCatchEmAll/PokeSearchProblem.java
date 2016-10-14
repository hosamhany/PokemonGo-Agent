package gottaCatchEmAll;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import problemDataTypes.Node;
import problemDataTypes.SearchQueue;
import abstractDataTypes.Operator;
import abstractDataTypes.SearchProblem;

public class PokeSearchProblem extends SearchProblem {
	private Maze maze;
	private SearchQueue searchQueue;
	
	public PokeSearchProblem(int rows, int columns) {
		this.maze = new Maze(rows, columns);
	}
	
	public Node generalSearch(String algorithm) throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int limit = 0;
		if(algorithm.equals("DepthLimited")) {
			Scanner scanner = new Scanner(System.in);
			limit = scanner.nextInt();
		}
		Node initialNode = new Node(this.getInitialState());
		calculateHeuristic(initialNode);
		this.searchQueue = new SearchQueue(algorithm, initialNode, limit);
		while(!this.searchQueue.isEmpty()) {
			Node currNode = this.searchQueue.removeFront();
			if(isGoal(currNode)) {
				return currNode;
			}
			Node[] neighbors = expand(currNode);
			calculateHeuristic(neighbors);
			this.searchQueue.insert(neighbors);
		}
		return null;
	}
	public void calculateHeuristic(Node[] nodes) {
		for(Node node : nodes) {
			node.setFn(calculateHeuristic(node));
		}
	}
	public int calculateHeuristic(Node node) {
		// TODO calculate heuristic
		return node.getFn();
	}
	public boolean isGoal(Node node) {
		// TODO goal test
		return false;
	}

	@Override
	public Node[] expand(Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for(Operator operator : this.getOperators()) {
			Node next = apply(operator, node);
			if(next != null)
				neighbors.add(next);
		}
		return (Node[]) neighbors.toArray();
	}
	
	public Node apply(Operator operator, Node node) {
		Node nextNode = null;
		// TODO apply operator
		return nextNode;
	}
}
