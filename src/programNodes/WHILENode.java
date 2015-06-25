package programNodes;

import conditionNodes.CONDNode;
import parserProgram.Robot;

public class WHILENode implements STMTNode{
	
	private CONDNode cond;
	private BLOCKNode block;
	
	public WHILENode(CONDNode cond, BLOCKNode block) {
		this.cond = cond;
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {
		while(cond.evaluate(robot)){
			block.execute(robot);
		}
	}

}
