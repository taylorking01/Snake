package controller;

import snake.Direction;

public class MoveEntry {
	private Direction.Dir direction;
	private int x;
	private int y;
	
	public MoveEntry(Direction.Dir direction, int x, int y) {
		this.direction = direction;
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Getters
	 */
	public Direction.Dir getDirection() {
		return direction;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
