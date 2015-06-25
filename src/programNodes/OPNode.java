package programNodes;

import parserProgram.ParserFailureException;
import parserProgram.Robot;

public class OPNode implements EXPNode{
	
	private EXPNode e1;
	private EXPNode e2;
	private String type;
	
	public OPNode(EXPNode e1, EXPNode e2, String type){
		this.e1 = e1;
		this.e2 = e2;
		this.type = type;
	}
	
	public int printNum(){
		return 1;
	}

	@Override
	public int evaluate(Robot robot) {
		if(type.equalsIgnoreCase("add")){
			return e1.evaluate(robot) + e1.evaluate(robot);
		}
		else if(type.equalsIgnoreCase("sub")){
			return e1.evaluate(robot) - e2.evaluate(robot);
		}
		else if(type.equalsIgnoreCase("mul")){
			return e1.evaluate(robot) * e2.evaluate(robot);
		}
		else if(type.equalsIgnoreCase("div")){
			return e1.evaluate(robot) / e2.evaluate(robot);			
		}
		else {
			throw new ParserFailureException("incorrect operation");
		}
	}

}
