package programNodes;

import java.util.List;

import parserProgram.Robot;

public class BLOCKNode implements RobotProgramNode {

	private List<STMTNode> statementList;

	public BLOCKNode(List<STMTNode> list) {
		this.statementList = list;
	}

	@Override
	public void execute(Robot robot) {
		for (STMTNode node : statementList) {
			node.execute(robot);
		}

	}

}
