package sensorNodes;

import parserProgram.Robot;
import programNodes.EXPNode;

public class BARRELLRNode implements SENNode {

	private EXPNode exp;

	public BARRELLRNode() {
		// nothing
	}

	public BARRELLRNode(EXPNode exp) {
		this.exp = exp;
	}

	@Override
	public int evaluate(Robot robot) {
		if (exp != null) {
			return robot.getBarrelLR(exp.evaluate(robot));
		} else {
			return robot.getClosestBarrelLR();
		}
	}

	@Override
	public int printNum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
