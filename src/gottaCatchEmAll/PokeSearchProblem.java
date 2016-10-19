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
		int initialCell = maze.getInitialCell();
		int rCell = initialCell / rows;
		int cCell = initialCell % rows;
		this.setOperators(new String[] { "F", "R", "L" });
		this.setInitialState(new State(rCell, cCell, 0, maze.getNumberOfPokes(), 6));
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
				System.out.println("Gaol reached!");
				return currNode;
			}
			System.out.println(currNode.getDepth());
			System.out.println(currNode.toString());
			System.out.println("_____________");
			ArrayList<Node> neighbors = expand(currNode);
			calculateHeuristic(neighbors);
			this.searchQueue.insert(neighbors);
		}
		return null;
	}

	public void calculateHeuristic(ArrayList<Node> nodes) {
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
		State s = node.getState();
		int row = s.getCellRow();
		int column = s.getCellColumn();

		return (maze.isGoalCell(row, column) && (s.getnOfPoke() == 0) && (s.getStepsToHatch() == 0));
	}

	@Override
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for (String operator : this.getOperators()) {
			Node next = apply(operator, node);
			if (next != null)
				neighbors.add(next);
		}
		return neighbors;
	}

	@Override
	public Node apply(String operator, Node node) {
		// TODO Auto-generated method stub
		Node newNode = new Node();
		newNode.setOperator(operator);
		newNode.setDepth(node.getDepth() + 1);
		newNode.setPathCost(node.getPathCost() + 1);
		newNode.setState(new State(node.getState().getCellRow(), node.getState().getCellColumn()));
		State state = newNode.getState();
		switch (operator) {
		case "F":
			switch (node.getState().getOrientation()) {
			// TODO: maze.inBounds need to check whether there is a wall or not.
			case 0:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() - 1, node.getState().getCellColumn())) {
					state.setLocation(node.getState().getCellRow() - 1, node.getState().getCellColumn());
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				}
				break;
			case 1:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow(), node.getState().getCellColumn() + 1)) {
					state.setLocation(node.getState().getCellRow(), node.getState().getCellColumn() + 1);
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				}
				break;
			case 2:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() + 1, node.getState().getCellColumn())) {
					state.setLocation(node.getState().getCellRow() + 1, node.getState().getCellColumn());
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				}
				break;
			case 3:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() + 1, node.getState().getCellColumn() - 1)) {
					state.setLocation(node.getState().getCellRow(), node.getState().getCellColumn() - 1);
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				}
				break;
			default:

			}
			if (node.getState().getStepsToHatch() > 0)
				state.setStepsToHatch(node.getState().getStepsToHatch() - 1);
			if (maze.hasPokemon(newNode.getState().getCellRow(), newNode.getState().getCellColumn())) {
				state.setnOfPoke(node.getState().getnOfPoke() - 1);
			}
			break;

		case "R":
			state.setOrientation(node.getState().getOrientation() + 1);
			state.setCell(node.getState());
			break;
		case "L":
			state.setOrientation(node.getState().getOrientation() - 1);
			state.setCell(node.getState());
			break;
		default:
		}
		newNode.setState(state);
		newNode.setParent(node);
		return newNode;
	}

	public static void main(String[] args) {
		PokeSearchProblem problem = new PokeSearchProblem(3, 3);
		try {
			problem.generalSearch("BFS");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
