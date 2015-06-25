package sensorNodes;

import parserProgram.Robot;

public class OPPFBNode implements SENNode{

	public OPPFBNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentFB();
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
