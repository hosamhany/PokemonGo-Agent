package gottaCatchEmAll;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import problemDataTypes.Node;
import problemDataTypes.SearchQueue;
import abstractDataTypes.SearchProblem;
import abstractDataTypes.State;

public class PokeSearchProblem extends SearchProblem {
	private Maze maze;
	private SearchQueue searchQueue;
	private String algorithm;
	private int heuristic;
	public static int nodes_expanded;

	public PokeSearchProblem(int rows, int columns) {
		this.maze = new Maze(rows, columns);
		int initialCell = maze.getInitialCell();
		int rCell = initialCell / rows;
		int cCell = initialCell % rows;
		this.setOperators(new String[] { "F", "R", "L" });
		int orien = (int) Math.floor(Math.random()*4);
		int steps = Math.min(maze.getRows(), maze.getColumns());
		this.setInitialState(new State(rCell, cCell, orien, steps, this.maze.getPokemonPos()));
	}
	
	public PokeSearchProblem(Maze maze) {
		this.maze = maze;
		int initialCell = maze.getInitialCell();
		int rCell = initialCell / maze.getRows();
		int cCell = initialCell % maze.getRows();
		this.setOperators(new String[] { "F", "R", "L" });
		int orien = (int) Math.floor(Math.random()*4);
		int steps = Math.min(maze.getRows(), maze.getColumns());
		this.setInitialState(new State(rCell, cCell, orien, steps, this.maze.getPokemonPos()));
	}

	public Node generalSearch(String algorithm) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		nodes_expanded = 0;
		this.algorithm = algorithm;
		int limit = 0;
		Scanner scanner = new Scanner(System.in);
		if (algorithm.equals("DepthLimited")) {
			System.out.println("Number of levels: ");
			limit = scanner.nextInt();
		}
		if(algorithm.equals("APlus") || algorithm.equals("Greedy")) {
			System.out.println("Which heuristic? (0, 1, 2) ");
			heuristic = scanner.nextInt();
		}
		Node initialNode = new Node(this.getInitialState());
		calculateHeuristic(initialNode);
		this.searchQueue = new SearchQueue(algorithm, initialNode, limit);
		while (!this.searchQueue.isEmpty()) {
			Node currNode = this.searchQueue.removeFront();
			nodes_expanded++;
//			if(currNode.toString().length() > 0) {
//				System.out.println(currNode);
//			}
			if (isGoal(currNode)) {
				System.out.println("Gaol reached!");
				maze.printMaze();
				return currNode;
			}

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
		if(this.algorithm.equals("UniformCost")) {
			node.setFn(node.getPathCost());
		}
		if(this.algorithm.equals("APlus") || this.algorithm.equals("Greedy")) {
			switch (this.heuristic) {
			case 0:
				heuristicOne(node);
				break;
			case 1:
				heuristicTwo(node);
				break;
			case 2:
				heuristicThree(node);
				break;
			}
		}
		if(this.algorithm.equals("APlus")) {
			node.setFn(node.getFn()+node.getPathCost());
		}
		return node.getFn();
	}
	public void heuristicOne(Node node) {
		int xDiff = Math.abs(node.getState().getCellRow() - maze.getGoalCell()/maze.getRows());
		int yDiff = Math.abs(node.getState().getCellColumn() - maze.getGoalCell() % maze.getRows());
		node.setFn(xDiff + yDiff);
	}
	public void heuristicTwo(Node node) {
		heuristicOne(node);
		node.setFn(Math.max(node.getFn(), node.getState().getStepsToHatch()));
	}	
	public void heuristicThree(Node node) {
		HashSet<Integer> pokePos = node.getState().getPokemonPos();
		int cnt = 0;
		int orientation = node.getState().getOrientation();
		int rows = node.getState().getCellRow();
		int cols = node.getState().getCellColumn();
		
		if (orientation == 0){
			for(Integer x : pokePos) {
				if (x/maze.getRows() < rows) {
					cnt++;
				}
			}
		} else if (orientation == 2) {
			for(Integer x : pokePos) {
				if (x/maze.getRows() > rows) {
					cnt++;
				}
			}
		} else if (orientation == 1) {
			for(Integer x : pokePos) {
				if (x%maze.getRows() > rows) {
					cnt++;
				}
			}
		} else if (orientation == 3) {
			for(Integer x : pokePos) {
				if (x%maze.getRows() < rows) {
					cnt++;
				}
			}
		}
		node.setFn(cnt);
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
		// newNode.setState(new State(node.getState().getCellRow(), node.getState().getCellColumn()));
		State state = new State(node.getState().getCellRow(), node.getState().getCellColumn());
		HashSet<Integer> pokePos = (HashSet<Integer>)node.getState().getPokemonPos().clone();
		state.setPokemonPos(pokePos);
		newNode.setState(state);
		state.setStepsToHatch(node.getState().getStepsToHatch());
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
				} else return null;
				break;
			case 1:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow(), node.getState().getCellColumn() + 1)) {
					state.setLocation(node.getState().getCellRow(), node.getState().getCellColumn() + 1);
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				} else return null;
				break;
			case 2:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow() + 1, node.getState().getCellColumn())) {
					state.setLocation(node.getState().getCellRow() + 1, node.getState().getCellColumn());
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				} else return null;
				break;
			case 3:
				if (maze.isValidMove(node.getState().getCellRow(), node.getState().getCellColumn(),
						node.getState().getCellRow(), node.getState().getCellColumn() - 1)) {
					state.setLocation(node.getState().getCellRow(), node.getState().getCellColumn() - 1);
					// state.setStepsToHatch(node.getState().getStepsToHatch() -
					// 1);
				} else return null;
				break;
			default:

			}
			state.setOrientation(node.getState().getOrientation());
			if (node.getState().getStepsToHatch() > 0) {
				state.setStepsToHatch(node.getState().getStepsToHatch() - 1);
			}
//			if (maze.hasPokemon(newNode.getState().getCellRow(), newNode.getState().getCellColumn())) {
//				state.setnOfPoke(node.getState().getnOfPoke() - 1);
//			} else {
//				state.setnOfPoke(node.getState().getnOfPoke());
//			}
			this.hasPokemon(state);
			break;

		case "R":
			state.setOrientation(node.getState().getOrientation() + 1);
			state.setCell(node.getState());
//			state.setStepsToHatch(node.getState().getStepsToHatch());
			break;
		case "L":
			state.setOrientation(node.getState().getOrientation() - 1);
			state.setCell(node.getState());
//			state.setStepsToHatch(node.getState().getStepsToHatch());
			break;
		default:
		}
//		newNode.setState(state);
		newNode.setParent(node);
		return newNode;
	}
	public boolean hasPokemon(State s) {
		int x = s.getCellRow();
		int y = s.getCellColumn();
		if (s.getPokemonPos().contains(x * maze.getRows() + y)) {
			s.removePokemon(x * maze.getRows() + y);
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		PokeSearchProblem problem = new PokeSearchProblem(2, 2);
		problem.maze.printMaze();
		Maze general_maze = problem.maze;
		try {
			System.out.println((problem.getInitialState().toString()));
			Node sol = problem.generalSearch("BFS");
			System.out.println("Number of nodes expanded with BFS: " + nodes_expanded);
//			if(sol == null) {
//				System.out.println("No solution found");
//			}
//			System.out.println("-----------");
//			while (sol != null) {
//				System.out.println(sol.toString());
//				sol = sol.getParent();
//			}
//			System.out.println("-----------");
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("DepthLimited");
			System.out.println("Number of nodes expanded with Depth limited: " + nodes_expanded);
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("IterativeDeepening");
			System.out.println("Number of nodes expanded with Iterative deepening: " + nodes_expanded);
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("UniformCost");
			System.out.println("Number of nodes expanded with Uniform Cost: " + nodes_expanded);
//			problem = new PokeSearchProblem(general_maze);
//			sol = problem.generalSearch("Greedy");
//			System.out.println("Number of nodes expanded with Greedy: " + nodes_expanded);
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("APlus");
			System.out.println("Number of nodes expanded with A star: " + nodes_expanded);
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("Greedy");
			System.out.println("Number of nodes expanded with Greedy: " + nodes_expanded);
			problem = new PokeSearchProblem(general_maze);
			sol = problem.generalSearch("DFS");
			System.out.println("Number of nodes expanded with DFS: " + nodes_expanded);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
