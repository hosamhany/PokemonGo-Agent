package abstractDataTypes;

import java.util.HashSet;

import problemDataTypes.Node;

public class State {
	private int cellRow;
	private int cellColumn;
	private int orientation;
	private HashSet<Integer> pokemonPos;
	private int stepsToHatch;

	public State() {

	}
	public State(int row, int column) {
		this.cellRow = row;
		this.cellColumn = column;
	}
	public State(int cellRow, int cellColumn, int orientation, int stepsToHatch, HashSet<Integer> pokemonPos) {
		this(cellRow, cellColumn, orientation, stepsToHatch);
		this.pokemonPos = (HashSet<Integer>)pokemonPos.clone();
	}
	public State(int cellRow, int cellColumn, int orientation, int stepsToHatch) {
		this(cellRow, cellColumn);
		this.orientation = orientation;
		this.stepsToHatch = stepsToHatch;
	}
	public void setLocation(int x, int y)
	{
		this.cellRow = x;
		this.cellColumn = y;
	}
	public int getCellRow() {
		return cellRow;
	}

	public void setCellRow(int cellRow) {
		this.cellRow = cellRow;
	}

	public int getCellColumn() {
		return cellColumn;
	}

	public void setCellColumn(int cellColumn) {
		this.cellColumn = cellColumn;
	}

	public int getOrientation() {
		return orientation;
	}

	// public int validateOrientation(Node node, int orientation, String
	// direction) {
	// switch (direction) {
	// case "F":
	// if (node.getState().getOrientation() == 3) {
	// node.getState().setOrientation(orientation);
	// }
	// }
	//
	// }

	public void setOrientation(int orientation) {
//		if ((orientation > 0) && (orientation < 3)) {
//			this.orientation = orientation;
//		} else {
//			if (orientation > 3) {
//				this.orientation = 0;
//			}
//			if (orientation < 0) {
//				this.orientation = 3;
//			}
//		}
		this.orientation = (orientation + 4) % 4;

	}

	public int getnOfPoke() {
		return pokemonPos.size();
	}

	public int getStepsToHatch() {
		return stepsToHatch;
	}

	public void setStepsToHatch(int stepsToHatch) {
		this.stepsToHatch = stepsToHatch;
	}
	
	public void setCell(State other) {
		this.setCellRow(other.getCellRow());
		this.setCellColumn(other.getCellColumn());
	}
	@Override
	public String toString() {
		String s = "("+this.cellRow+","+this.cellColumn+")\n"+
					"Orientation: "+this.orientation+"\n"+
					"Pokemons left: "+this.getnOfPoke()+"\n"+
					"Steps to hatch: "+this.stepsToHatch+"\n";
		return s;
	}
	public HashSet<Integer> getPokemonPos() {
		return pokemonPos;
	}
	public void setPokemonPos(HashSet<Integer> pokemonPos) {
		this.pokemonPos = pokemonPos;
	}
	
	public void removePokemon(int cell) {
		this.pokemonPos.remove(cell);
	}

}
