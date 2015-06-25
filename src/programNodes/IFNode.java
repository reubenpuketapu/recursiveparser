package programNodes;

import java.util.List;

import conditionNodes.CONDNode;
import parserProgram.Robot;

public class IFNode implements STMTNode {

	private CONDNode cond;
	private BLOCKNode block;
	private BLOCKNode elseBlock;

	private List<ELIFNode> elifNodes;

	public IFNode(CONDNode cond, BLOCKNode block, List<ELIFNode> elifNodes) {
		this.cond = cond;
		this.block = block;
		this.elifNodes = elifNodes;
	}

	public IFNode(CONDNode cond, BLOCKNode block, BLOCKNode elseBlock,
			List<ELIFNode> elifNodes) {
		this.cond = cond;
		this.block = block;
		this.elseBlock = elseBlock;
		this.elifNodes = elifNodes;
	}

	@Override
	public void execute(Robot robot) {
		
		if (cond.evaluate(robot)) {
			block.execute(robot);
			return;
		}

		if (!elifNodes.isEmpty()) {
			for (ELIFNode node : elifNodes) {
				if (node.evaluate(robot)) {
					node.execute(robot);
					return;
				}

			}
		}

		if (elseBlock != null) {
			elseBlock.execute(robot);
		}

	}
}
