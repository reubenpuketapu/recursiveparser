package sensorNodes;

import parserProgram.Robot;

public class OPPLRNode implements SENNode{

	public OPPLRNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentLR();
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
