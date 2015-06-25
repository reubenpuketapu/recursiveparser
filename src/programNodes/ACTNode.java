package programNodes;

import parserProgram.ParserFailureException;
import parserProgram.Robot;

public class ACTNode implements STMTNode {
	
	private String action;
	
	public ACTNode(String action){
		this.action = action;
	}

	@Override
	public void execute(Robot robot) {
		switch(action){
		
		
		case "turnL" :
			robot.turnLeft();
			break;
		case "turnR" :
			robot.turnRight();
			break;
		case "takeFuel" :
			robot.takeFuel();
			break;
		case "turnAround" :
			robot.turnAround();
			break;
		case "shieldOn" :
			robot.setShield(true);
			break;
		case "shieldOff" :
			robot.setShield(false);
			break;
		default :
			throw new ParserFailureException(action);
		}
		
	}

}
