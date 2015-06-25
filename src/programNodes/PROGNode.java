package programNodes;

import java.util.List;

import parserProgram.Robot;

public class PROGNode implements RobotProgramNode{
	
	private List<STMTNode> statementList;

	public PROGNode(List<STMTNode> statementList) {
		this.statementList = statementList;
	}
	
	public void addSTMT(STMTNode node){
		this.statementList.add(node);
	}

	@Override
	public void execute(Robot robot) {
		for(STMTNode node : statementList){
			node.execute(robot);
		}
		
	}

}
