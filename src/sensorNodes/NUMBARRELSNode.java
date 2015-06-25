package sensorNodes;

import parserProgram.Robot;

public class NUMBARRELSNode implements SENNode{

	public NUMBARRELSNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int evaluate(Robot robot) {
		return robot.numBarrels();
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}
}
