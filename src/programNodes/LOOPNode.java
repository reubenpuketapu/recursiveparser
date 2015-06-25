package programNodes;

import parserProgram.Robot;

public class LOOPNode implements STMTNode {

	private BLOCKNode block;

	public LOOPNode(BLOCKNode block) {
		this.block = block;
	}

	@Override
	public void execute(Robot robot) {
		while (true) {
			block.execute(robot);
		}
	}

}
