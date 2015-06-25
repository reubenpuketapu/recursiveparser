package conditionNodes;

import parserProgram.Robot;

public class ORNode implements CONDNode{
	
	CONDNode c1;
	CONDNode c2;
	
	public ORNode(CONDNode c1, CONDNode c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public boolean evaluate(Robot robot) {
		
		return c1.evaluate(robot) || c2.evaluate(robot);
		
	}

}
