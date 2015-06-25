package programNodes;

import parserProgram.Parser;
import parserProgram.ParserFailureException;
import parserProgram.Robot;

public class VARNode implements EXPNode {

	private String node;

	public VARNode(String node) {
		this.node = node;
	}

	public String getNode() {
		return node;
	}

	@Override
	public int evaluate(Robot robot) {
		if (robot.varMap.containsKey(node)) {
			return robot.varMap.get(node);
		} else{
			return 0;
		}
	}

	@Override
	public int printNum() {
		return 0;
	}

}
