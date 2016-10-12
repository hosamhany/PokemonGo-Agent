package problemDataTypes;

public class Cell {
	private int rowCoordinate;
	private int columnCooridanate;
	private boolean pokemon;
	private boolean startingCell;
	private boolean finishingCell;

	public Cell(int rowCoordinate, int columnCoordinate) {
		this.rowCoordinate = rowCoordinate;
		this.columnCooridanate = columnCoordinate;
	}

	public int getRowCoordinate() {
		return rowCoordinate;
	}

	public void setRowCoordinate(int rowCoordinate) {
		this.rowCoordinate = rowCoordinate;
	}

	public int getColumnCooridanate() {
		return columnCooridanate;
	}

	public void setColumnCooridanate(int columnCooridanate) {
		this.columnCooridanate = columnCooridanate;
	}

	public boolean isPokemon() {
		return pokemon;
	}

	public void setHasPokemon(boolean hasPokemon) {
		this.pokemon = hasPokemon;
	}

	public boolean isStartingCell() {
		return startingCell;
	}

	public void setStartingCell(boolean startingCell) {
		this.startingCell = startingCell;
	}

	public boolean isFinishingCell() {
		return finishingCell;
	}

	public void setFinishingCell(boolean finishingCell) {
		this.finishingCell = finishingCell;
	}
}
