package parserProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

import javax.swing.JFileChooser;

import conditionNodes.*;
import programNodes.*;
import sensorNodes.*;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */

public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement
														// this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	private static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	private static Pattern OPENPAREN = Pattern.compile("\\(");
	private static Pattern CLOSEPAREN = Pattern.compile("\\)");
	private static Pattern OPENBRACE = Pattern.compile("\\{");
	private static Pattern CLOSEBRACE = Pattern.compile("\\}");
	private static Pattern VAR = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");

	public static Map<String, Integer> varMap = new HashMap<String, Integer>();

	/**
	 * PROG ::= STMT+
	 */
	static RobotProgramNode parseProgram(Scanner s) {
		// THE PARSER GOES HERE!

		List<STMTNode> list = new ArrayList<STMTNode>();

		while (s.hasNext()) {
			list.add(parseStatement(s));
		}
		return new PROGNode(list);
	}

	static private IFNode parseIf(Scanner s) {

		require(OPENPAREN, "expected '('", s);
		CONDNode cond = parseCond(s);
		require(CLOSEPAREN, "expected ')'", s);
		BLOCKNode block = parseBlock(s);

		List<ELIFNode> elifNodes = new ArrayList<ELIFNode>();

		if (s.hasNext("elif")) {

			while (s.hasNext("elif")) {

				require("elif", "expected 'elif'", s);
				require(OPENPAREN, "expected '('", s);
				CONDNode elifcond = parseCond(s);
				require(CLOSEPAREN, "expected ')'", s);
				BLOCKNode elifblock = parseBlock(s);

				ELIFNode node = new ELIFNode(elifcond, elifblock);
				elifNodes.add(node);

			}
		}

		if (s.hasNext("else")) {
			require("else", "expected 'else'", s);
			BLOCKNode elseBlock = parseBlock(s);
			return new IFNode(cond, block, elseBlock, elifNodes);
		} else {
			return new IFNode(cond, block, elifNodes);

		}

	}

	static private WHILENode parseWhile(Scanner s) {

		require(OPENPAREN, "expected '('", s);
		CONDNode cond = parseCond(s);
		require(CLOSEPAREN, "expected ')'", s);
		BLOCKNode block = parseBlock(s);

		return new WHILENode(cond, block);
	}

	static private CONDNode parseCond(Scanner s) {

		if (s.hasNext("lt")) {
			require("lt", "expected 'lt'", s);
			require(OPENPAREN, "expected '('", s);
			EXPNode e1 = parseExp(s);
			require(",", "expected ','", s);
			EXPNode e2 = parseExp(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new LTNode(e1, e2);

		} else if (s.hasNext("gt")) {
			require("gt", "expected 'gt'", s);
			require(OPENPAREN, "expected '('", s);
			EXPNode e1 = parseExp(s);
			require(",", "expected ','", s);
			EXPNode e2 = parseExp(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new GTNode(e1, e2);

		} else if (s.hasNext("eq")) {
			require("eq", "expected 'eq'", s);
			require(OPENPAREN, "expected '('", s);
			EXPNode e1 = parseExp(s);
			require(",", "expected ','", s);
			EXPNode e2 = parseExp(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new EQNode(e1, e2);

		} else if (s.hasNext("and")) {
			require("and", "expected 'and'", s);
			require(OPENPAREN, "expected '('", s);
			CONDNode c1 = parseCond(s);
			require(",", "expected ','", s);
			CONDNode c2 = parseCond(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new ANDNode(c1, c2);

		} else if (s.hasNext("or")) {
			require("or", "expected 'or'", s);
			require(OPENPAREN, "expected '('", s);
			CONDNode c1 = parseCond(s);
			require(",", "expected ','", s);
			CONDNode c2 = parseCond(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new ORNode(c1, c2);

		} else if (s.hasNext("not")) {
			require("not", "expected 'not'", s);
			require(OPENPAREN, "expected '('", s);
			CONDNode c1 = parseCond(s);
			require(CLOSEPAREN, "expected ')'", s);

			return new NOTNode(c1);

		} else {
			fail("expected a condition", s);
			return null;
		}

	}

	static private SENNode parseSensor(Scanner s) {
		if (s.hasNext("fuelLeft")) {
			require("fuelLeft", "expected fuelLeft", s);
			return new FUELLEFTNode();
		} else if (s.hasNext("oppLR")) {
			require("oppLR", "expected oppLR", s);
			return new OPPLRNode();
		} else if (s.hasNext("oppFB")) {
			require("oppFB", "expected oppFB", s);
			return new OPPFBNode();
		} else if (s.hasNext("numBarrels")) {
			require("numBarrels", "expected numBarrels", s);
			return new NUMBARRELSNode();
		} else if (s.hasNext("barrelLR")) {
			require("barrelLR", "expected barrelLR", s);
			if (s.hasNext(OPENPAREN)) {
				require(OPENPAREN, "expected '('", s);
				EXPNode exp = parseExp(s);
				require(CLOSEPAREN, "expected ')'", s);
				return new BARRELLRNode(exp);
			} else {
				return new BARRELLRNode();
			}
		} else if (s.hasNext("barrelFB")) {
			require("barrelFB", "expected barrelFB", s);
			if (s.hasNext(OPENPAREN)) {
				require(OPENPAREN, "expected '('", s);
				EXPNode exp = parseExp(s);
				require(CLOSEPAREN, "expected ')'", s);
				return new BARRELFBNode(exp);
			} else {
				return new BARRELFBNode();
			}
		} else if (s.hasNext("wallDist")) {
			require("wallDist", "expected wallDist", s);
			return new WALLDISTNode();
		} else {
			fail("expected a valid sensor type", s);
			return null;
		}
	}

	static private NUMNode parseNumber(Scanner s) {

		int number = s.nextInt();
		return new NUMNode(number);
	}

	static private LOOPNode parseLoop(Scanner s) {

		return new LOOPNode(parseBlock(s));
	}

	static private BLOCKNode parseBlock(Scanner s) {

		List<STMTNode> list = new ArrayList<STMTNode>();

		require(OPENBRACE, "expected '{'", s);
		while (!s.hasNext("}")) {
			list.add(parseStatement(s));
		}
		require(CLOSEBRACE, "expected '}'", s);

		if (list.isEmpty()) {
			fail("expected at least one statement inside the block", s);
		}

		return new BLOCKNode(list);
	}

	static private STMTNode parseStatement(Scanner s) {

		if (s.hasNext("loop")) {
			require("loop", "expecting loop", s);
			return parseLoop(s);
		} else if (s.hasNext("if")) {
			require("if", "expecting 'if'", s);
			return parseIf(s);
		} else if (s.hasNext("while")) {
			require("while", "expecting 'while'", s);
			return parseWhile(s);
		} else if (s.hasNext("move")) {
			require("move", "expecting move", s);
			return parseMove(s);
		} else if (s.hasNext("wait")) {
			require("wait", "expecting wait", s);
			return parseWait(s);
		} else if (s.hasNext(VAR)) {
			return parseAssign(s);
		} else {
			ACTNode node = parseAction(s);
			require(";", "expecting ';' ", s);
			return node;
		}
	}

	private static ASSIGNNode parseAssign(Scanner s) {
		if (s.hasNext(VAR)) {
			String variable = s.next();
			require("=", "expecting '='", s);
			EXPNode exp = parseExp(s);
			require(";", "expecting ';' ", s);

			return new ASSIGNNode(variable, exp);

		} else {
			fail("expected a valid assignment", s);
			return null;
		}
	}

	private static VARNode parseVar(Scanner s) {
		return new VARNode(s.next());
	}

	private static EXPNode parseExp(Scanner s) {

		if (s.hasNext(NUMPAT)) {
			return parseNumber(s);
		} else if (s.hasNext("add")) {
			return parseOp(s);
		} else if (s.hasNext("sub")) {
			return parseOp(s);
		} else if (s.hasNext("mul")) {
			return parseOp(s);
		} else if (s.hasNext("div")) {
			return parseOp(s);
		} else if (s.hasNext(VAR)) {
			return parseVar(s);
		} else {
			return parseSensor(s);
		}

	}

	private static OPNode parseOp(Scanner s) {

		String type = s.next();
		require(OPENPAREN, "expected '('", s);
		EXPNode e1 = parseExp(s);
		require(",", "expected ','", s);
		EXPNode e2 = parseExp(s);
		require(CLOSEPAREN, "expected ')'", s);

		return new OPNode(e1, e2, type);

	}

	private static STMTNode parseMove(Scanner s) {
		if (s.hasNext(OPENPAREN)) {
			require(OPENPAREN, "expecting '('", s);

			EXPNode exp = parseExp(s);

			require(CLOSEPAREN, "expecting ')'", s);
			require(";", "expecting ';' ", s);
			return new MOVENode(exp);
		} else {
			require(";", "expecting ';' ", s);
			return new MOVENode();
		}
	}

	private static STMTNode parseWait(Scanner s) {
		if (s.hasNext(OPENPAREN)) {
			require(OPENPAREN, "expecting '('", s);
			EXPNode exp = parseExp(s);
			require(CLOSEPAREN, "expecting ')'", s);
			require(";", "expecting ';' ", s);
			return new WAITNode(exp);
		} else {
			require(";", "expecting ';' ", s);
			return new WAITNode();
		}
	}

	static private ACTNode parseAction(Scanner s) {

		if (s.hasNext("move")) {
			require("move", "expected move", s);
			return new ACTNode("move");

		} else if (s.hasNext("turnL")) {
			require("turnL", "expected turnL", s);
			return new ACTNode("turnL");

		} else if (s.hasNext("turnR")) {
			require("turnR", "expected turnR", s);
			return new ACTNode("turnR");

		} else if (s.hasNext("takeFuel")) {
			require("takeFuel", "expected takeFuel", s);
			return new ACTNode("takeFuel");

		} else if (s.hasNext("wait")) {
			require("wait", "expected wait", s);
			return new ACTNode("wait");

		} else if (s.hasNext("turnAround")) {
			require("turnAround", "expected 'turnAround'", s);
			return new ACTNode("turnAround");

		} else if (s.hasNext("shieldOn")) {
			require("shieldOn", "expected 'shieldOn'", s);
			return new ACTNode("shieldOn");

		} else if (s.hasNext("shieldOff")) {
			require("shieldOff", "expected 'shieldOff'", s);
			return new ACTNode("shieldOff");
		}

		else {
			fail("expected a valid action", s);
			return null;
		}
	}

	// makes sure that the parser receives the correct string
	static public String require(String pat, String msg, Scanner s) {
		if (s.hasNext(pat)) {
			return s.next();
		} else {
			fail(msg, s);
			return null;
		}
	}

	// makes sure that the parser receives the correct pattern
	static public String require(Pattern pat, String msg, Scanner s) {
		if (s.hasNext(pat)) {
			return s.next();
		} else {
			fail(msg, s);
			return null;
		}
	}

	// utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	public static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

}
