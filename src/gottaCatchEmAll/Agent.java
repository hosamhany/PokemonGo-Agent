package gottaCatchEmAll;

import abstractDataTypes.SearchProblem;

public class Agent {
	private SearchProblem sp;
	private Maze maze;

	public Agent(SearchProblem sp, int rows, int columns) {
		this.maze = new Maze(rows, columns);
	}

	public SearchProblem getSp() {
		return sp;
	}

	public void setSp(SearchProblem sp) {
		this.sp = sp;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
}
