package programNodes;

import parserProgram.Robot;

public class WAITNode implements STMTNode {

	private EXPNode exp;

	public WAITNode() {
		exp = null;
	}

	public WAITNode(EXPNode exp) {
		this.exp = exp;
	}

	@Override
	public void execute(Robot robot) {
		if (exp != null) {
			for (int i = 0; i < exp.evaluate(robot); i++) {
				robot.idleWait();
			}
		} else {
			robot.idleWait();
		}

	}
}
