package programNodes;

import parserProgram.Robot;

public class ASSIGNNode implements STMTNode {

	private String var;
	private EXPNode exp;

	public ASSIGNNode(String var, EXPNode exp) {
		this.var = var;
		this.exp = exp;
	}

	public String getVar() {
		return var;
	}

	public EXPNode getExp() {
		return exp;
	}

	@Override
	public void execute(Robot robot) {
		robot.varMap.put(var, exp.evaluate(robot));
	}

}
