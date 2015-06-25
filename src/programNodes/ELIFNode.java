package programNodes;

import conditionNodes.CONDNode;
import parserProgram.Robot;

public class ELIFNode implements STMTNode{
	
	private CONDNode cond;
	private BLOCKNode block;

	public ELIFNode(CONDNode cond, BLOCKNode block) {
		this.cond = cond;
		this.block = block;
	}
	
	public boolean evaluate(Robot robot){
		return cond.evaluate(robot);
	}

	@Override
	public void execute(Robot robot) {
		if (cond.evaluate(robot)) {
			block.execute(robot);
		}
		
	}

}
