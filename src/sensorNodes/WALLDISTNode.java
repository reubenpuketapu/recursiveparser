package sensorNodes;

import parserProgram.Robot;

public class WALLDISTNode implements SENNode{

	public WALLDISTNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int evaluate(Robot robot) {
		return robot.getDistanceToWall();
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
