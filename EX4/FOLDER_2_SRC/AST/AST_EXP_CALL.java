package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;
import IR.*;
import java.util.LinkedList;

public class AST_EXP_CALL extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String funcName;
	public AST_EXP_LIST params;
        public String funcLabel;

    // AST_EXP_CALL is sometimes used from AST_STMT_METHOD, in that case we already have the TYPE_FUNCTION and we should use it
    public TYPE_FUNCTION stmtMethod;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_CALL(String funcName,AST_EXP_LIST params)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.funcName = funcName;
		this.params = params;
	}

    public void setStmtMethod(TYPE_FUNCTION stmtMethod) {
        this.stmtMethod = stmtMethod;
    }

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		System.out.format("CALL(%s)\nWITH:\n",funcName);

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (params != null) params.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CALL(%s)\nWITH",funcName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,params.SerialNumber);		
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
				
		TYPE funcType = null;
		TYPE_LIST typeList = null;
		TYPE paramType = null;
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
       if (stmtMethod == null) {
            funcType = SYMBOL_TABLE.getInstance().find(funcName);
            if (funcType == null)
            {
				throw new AST_EXCEPTION(String.format("Non existing function %s\n", funcName), this.lineNum);
            } else if(!(funcType instanceof TYPE_FUNCTION)) {
                throw new AST_EXCEPTION(String.format("'%s' is not a function type\n", funcType.name), this.lineNum);
            }
        }
        else {
			funcType = stmtMethod;
        }
		typeList = ((TYPE_FUNCTION)funcType).params;

                this.funcLabel = ((TYPE_FUNCTION)funcType).funcLabel;

		for (AST_EXP_LIST it = params; it  != null; it = it.tail){
			paramType = it.head.SemantMe();
			if(typeList == null){
				throw new AST_EXCEPTION(String.format("Too many arguments for function %s", funcName), this.lineNum);
			} 
			typesCheck(typeList.head, paramType);
			typeList = typeList.tail;
		}
		if(typeList != null){
			throw new AST_EXCEPTION(String.format("Not enouth arguments for function %s", funcName), this.lineNum);
		}
		
		return ((TYPE_FUNCTION)funcType).returnType;
	}
	
	public Temp IRme()
	{
                // push all arguments to the stack
                // jalr to the function
                // save $fp to the stack (inside function)
                // save $ra to the stack (inside function)
                // push all registers to the stack (inside function)
                // fp = sp - 4
                // sp = sp - total local var size
                // recover everything
                // return value in $a0

            switch  (funcName) {
                case "PrintInt":
                    IR.getInstance().Add_IRcommand(new IRcommandPrintInt(params.IRme()));
                    return null;
                case "PrintString":
                    IR.getInstance().Add_IRcommand(new IRcommandPrintString(params.IRme()));
                    return null;
                default:
                    LinkedList <Temp> reversedTemps = null;
                    if (params != null) {
                        AST_EXP_LIST currentParam;
                        reversedTemps = new LinkedList <Temp>();
                        Temp currentParamTemp;
                        for (currentParam = params; currentParam != null ; currentParam = currentParam.tail) {
                            currentParamTemp = params.IRme(); 
                            reversedTemps.addFirst(currentParamTemp);
                        }
                        for (Temp currentReversedParamTemp: reversedTemps) {
                            IR.getInstance().Add_IRcommand(new IRcommand_Push(currentReversedParamTemp));
                        }

                    }
                    IR.getInstance().Add_IRcommand(new IRcommand_jump_and_link(funcLabel));
                    if (params != null) {
                        IR.getInstance().Add_IRcommand(new IRcommand_Addi("$sp", "$sp", 4 * reversedTemps.size()));
                    }


                    return null;
            }
        }
}
