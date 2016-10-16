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

	public Node generalSearch(String algorithm) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int limit = 0;
		if (algorithm.equals("DepthLimited")) {
			Scanner scanner = new Scanner(System.in);
			limit = scanner.nextInt();
		}
		Node initialNode = new Node(this.getInitialState());
		calculateHeuristic(initialNode);
		this.searchQueue = new SearchQueue(algorithm, initialNode, limit);
		while (!this.searchQueue.isEmpty()) {
			Node currNode = this.searchQueue.removeFront();
			if (isGoal(currNode)) {
				return currNode;
			}
			Node[] neighbors = expand(currNode);
			calculateHeuristic(neighbors);
			this.searchQueue.insert(neighbors);
		}
		return null;
	}

	public void calculateHeuristic(Node[] nodes) {
		for (Node node : nodes) {
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
		for (String operator : this.getOperators()) {
			Node next = apply(operator, node);
			if (next != null)
				neighbors.add(next);
		}
		return (Node[]) neighbors.toArray();
	}

	@Override
	public Node apply(String operator, Node node) {
		// TODO Auto-generated method stub
		node.setDepth(node.getDepth() + 1);
		node.setPathCost(node.getPathCost() + 1);
		switch (operator) {
		case "F":
			switch (node.getState().getOrientation()) {
			// TODO: maze.inBounds need to check whether there is a wall or not.
			case 0:
				if (maze.inBounds(node.getState().getCellRow() - 1)) {
					node.getState().setCellRow(node.getState().getCellRow() - 1);
				} else {
					return null;
				}
			case 1:
				if (maze.inBounds(node.getState().getCellColumn() + 1)) {
					node.getState().setCellColumn(node.getState().getCellColumn() + 1);
				} else {
					return null;
				}
			case 2:
				if (maze.inBounds(node.getState().getCellRow() + 1)) {
					node.getState().setCellRow(node.getState().getCellRow() + 1);
				} else {
					return null;
				}
			case 3:
				if (maze.inBounds(node.getState().getCellColumn() - 1)) {
					node.getState().setCellColumn(node.getState().getCellColumn() - 1);
				} else {
					return null;
				}
			default:
				return null;
			}

		case "R":
			node.getState().setOrientation(node.getState().getOrientation() + 1);
			node.setParent(node);
		case "L":
			node.getState().setOrientation(node.getState().getOrientation() - 1);
			node.setParent(node);
		default:
		}
		return node;
	}
}
