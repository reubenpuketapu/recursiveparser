package programNodes;

import parserProgram.Robot;

public class NUMNode implements EXPNode{
	
	private int number;

	public NUMNode(int number) {
		this.number = number;
	}
	
	public int printNum(){
		return number;
	}

	@Override
	public int evaluate(Robot robot) {
		
		return this.number;
	}
	

}
