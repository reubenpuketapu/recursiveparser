package programNodes;

import parserProgram.Robot;

public class MOVENode implements STMTNode {

	private EXPNode exp;

	public MOVENode(EXPNode exp) {
		this.exp = exp;
	}

	public MOVENode() {
		this.exp = null;
	}

	@Override
	public void execute(Robot robot) {

		if (exp != null) {
			for (int i = 0; i < exp.evaluate(robot); i++) {
				robot.move();
			}
		} else {
			robot.move();
		}

	}

}
