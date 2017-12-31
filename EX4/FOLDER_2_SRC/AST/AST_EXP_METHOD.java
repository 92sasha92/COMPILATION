package AST;
import TYPES.*;

public class AST_EXP_METHOD extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP_VAR var;
    public AST_EXP_CALL expCall;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_METHOD(AST_EXP_VAR var, String methodName,AST_EXP_LIST expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
        this.expCall = new AST_EXP_CALL(methodName, expList);
	}
	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST EXP METHOD */
		/********************************/
		System.out.format("EXP\nMETHOD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (var != null) var.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP\nMETHOD\n"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}

        public TYPE SemantMe() throws AST_EXCEPTION {
            TYPE varType = null;
            TYPE_CLASS classType = null;
            TYPE_FUNCTION currentMethod = null;
			
            /****************************/
            /* [1] Semant var */
            /****************************/
            varType = var.SemantMe();

            /****************************/
            /* [2] Check if var is of type class */
            /****************************/
            if (!(varType instanceof TYPE_CLASS))
            {
				throw new AST_EXCEPTION(String.format("'%s' is not of class type\n", varType.name), this.lineNum);
            }
            
            /****************************/
            /* [3] check if the method is in the class hierarchy, starting from the bottom and going up */
            /****************************/
            TYPE_CLASS firstClass = (TYPE_CLASS)varType;
            for (classType = firstClass; classType != null ; classType = classType.father) {
				for (TYPE_LIST methodList = classType.method_List; methodList  != null; methodList = methodList.tail){
                    if (!(methodList.head instanceof TYPE_FUNCTION)) {
                        throw new AST_EXCEPTION(String.format("Found a method with type %s instead of TYPE_FUNCTION\n", methodList.head.name), this.lineNum);
                    }
                    currentMethod = (TYPE_FUNCTION)methodList.head;
                    if (currentMethod.name.equals(expCall.funcName)) {
                        /****************************/
                        /* [4] Found the method, semant the method call */
                        /****************************/
                        // found the method
                        expCall.setStmtMethod(currentMethod);
                        return expCall.SemantMe();
                    }
                }
            }
            throw new AST_EXCEPTION(String.format("Couldn't find the method %s in the class %s\n", expCall.funcName, firstClass.name), this.lineNum);
        }
}
