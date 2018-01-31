package AST;


public class AST_EXCEPTION extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int lineNum = 0;
	public AST_EXCEPTION(String message, int lineNum){
		super(message);
		this.lineNum = lineNum;
	}
}