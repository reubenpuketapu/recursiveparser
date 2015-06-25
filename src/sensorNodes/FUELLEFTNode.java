package sensorNodes;

import parserProgram.Robot;

public class FUELLEFTNode implements SENNode{

	public FUELLEFTNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int evaluate(Robot robot) {
		return robot.getFuel();
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
