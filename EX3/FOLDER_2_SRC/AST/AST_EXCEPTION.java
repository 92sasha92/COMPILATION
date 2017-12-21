package AST;


public class AST_EXCEPTION extends Exception {
	public int lineNum = 0;
	public AST_EXCEPTION(String message, int lineNum){
		super(message);
		this.lineNum = lineNum;
	}
}