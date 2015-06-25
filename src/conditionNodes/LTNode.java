package conditionNodes;

import parserProgram.Robot;
import programNodes.EXPNode;

public class LTNode implements CONDNode{

	private EXPNode e1;
	private EXPNode e2;
	
	public LTNode(EXPNode e1, EXPNode e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public boolean evaluate(Robot robot) {
		if(e1.evaluate(robot) < e2.evaluate(robot)){
			return true;
		}
		else{
			return false;
		}
	}

}
