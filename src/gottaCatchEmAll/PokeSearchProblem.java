package gottaCatchEmAll;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import problemDataTypes.Node;
import problemDataTypes.SearchQueue;
import abstractDataTypes.SearchProblem;
import abstractDataTypes.State;

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
		node.setFn(node.getPathCost());
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
		Node newNode = new Node();
		newNode.setDepth(node.getDepth() + 1);
		newNode.setPathCost(node.getPathCost() + 1);
		newNode.setState(new State(node.getState().getCellRow(), node.getState().getCellColumn()));
		switch (operator) {
		case "F":
			switch (node.getState().getOrientation()) {
			// TODO: maze.inBounds need to check whether there is a wall or not.
			case 0:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() - 1, node.getState().getCellColumn())) {
					newNode.getState().setCellRow(node.getState().getCellRow() - 1);
				} else {
					return null;
				}
				break;
			case 1:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow(), node.getState().getCellColumn() + 1)) {
					newNode.getState().setCellColumn(node.getState().getCellColumn() + 1);
				} else {
					return null;
				}
				break;
			case 2:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() + 1, node.getState().getCellColumn())) {
					newNode.getState().setCellRow(node.getState().getCellRow() + 1);
				} else {
					return null;
				}
				newNode.getState().setStepsToHatch(node.getState().getStepsToHatch() - 1);
				break;
			case 3:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() + 1, node.getState().getCellColumn() - 1)) {
					newNode.getState().setCellColumn(node.getState().getCellColumn() - 1);
				} else {
					return null;
				}
				break;
			default:
				return null;
			}
			newNode.getState().setStepsToHatch(node.getState().getStepsToHatch() - 1);
			if(maze.hasPokemon(newNode.getState().getCellRow(), newNode.getState().getCellColumn())) {
				newNode.getState().setnOfPoke(node.getState().getnOfPoke()-1);
			}

		case "R":
			newNode.getState().setOrientation(node.getState().getOrientation() + 1);
			newNode.setParent(node);
			newNode.getState().setCell(node.getState());
			break;
		case "L":
			newNode.getState().setOrientation(node.getState().getOrientation() - 1);
			newNode.setParent(node);
			newNode.getState().setCell(node.getState());
			break;
		default:
		}

		return newNode;
	}

	public static void main(String[] args) {
		PokeSearchProblem problem = new PokeSearchProblem(3, 3);
		try {
			problem.generalSearch("DFS");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
