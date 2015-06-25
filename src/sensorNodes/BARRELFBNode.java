package sensorNodes;

import parserProgram.Robot;
import programNodes.EXPNode;

public class BARRELFBNode implements SENNode {

	private EXPNode exp;

	public BARRELFBNode() {
		//nothing
	}

	public BARRELFBNode(EXPNode exp) {
		this.exp = exp;

	}

	@Override
	public int evaluate(Robot robot) {
		if (exp != null) {
			return robot.getBarrelFB(exp.evaluate(robot));
		} else {
			return robot.getClosestBarrelFB();
		}
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
