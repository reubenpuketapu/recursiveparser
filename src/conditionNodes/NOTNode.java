package conditionNodes;

import parserProgram.Robot;

public class NOTNode implements CONDNode{
	
	CONDNode c1;
	
	public NOTNode(CONDNode c1) {
		this.c1 = c1;
	}

	public boolean evaluate(Robot robot) {
		
		return !c1.evaluate(robot);
		
	}

}
