package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;
import IR.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Math;

public class AST_EXP_CALL extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String funcName;
	public AST_EXP_LIST params;
        public String funcLabel;
        public Temp classInstanceAddress;
        public boolean isMethodFromClass;
        public TYPE_CLASS classType;

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

    public void setClassType (TYPE_CLASS classType) {
        this.classType = classType;
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
            TYPE_FOR_SCOPE_BOUNDARIES classBoundry = (TYPE_FOR_SCOPE_BOUNDARIES)(SYMBOL_TABLE.getInstance().findClassBoundry());
            funcType = SYMBOL_TABLE.getInstance().find(funcName);
            if (funcType == null)
            {
                if (classBoundry == null) {
		    throw new AST_EXCEPTION(String.format("Non existing function %s\n", funcName), this.lineNum);
                }
                else {
                    TYPE_CLASS classType = (TYPE_CLASS)(classBoundry.returnType);
                    funcType = classType.getFunc(this.funcName);
                    if (funcType == null) {
		        throw new AST_EXCEPTION(String.format("Non existing function %s\n", funcName), this.lineNum);
                    }
                    this.isMethodFromClass = true;
                }
            } else if(!(funcType instanceof TYPE_FUNCTION)) {
                throw new AST_EXCEPTION(String.format("'%s' is not a function type\n", funcType.name), this.lineNum);
            }
            else if (classBoundry != null && SYMBOL_TABLE.getInstance().findIndex(funcName) >= SYMBOL_TABLE.getInstance().findClassBoundryIndex()) { // method in class scope 
                    this.isMethodFromClass = true;
            }
        }
        else {
			funcType = stmtMethod;
        }
		typeList = ((TYPE_FUNCTION)funcType).params;

                this.funcLabel = ((TYPE_FUNCTION)funcType).funcLabel;

                TYPE_FOR_SCOPE_BOUNDARIES classBoundry = (TYPE_FOR_SCOPE_BOUNDARIES)(SYMBOL_TABLE.getInstance().findClassBoundry());
                if (classBoundry != null) {
                this.classType = (TYPE_CLASS)(classBoundry.returnType);
                }

                // this.isMethodFromClass = (classBoundry != null);

                int maxParamRegs = 0;
		for (AST_EXP_LIST it = params; it  != null; it = it.tail){
			paramType = it.head.SemantMe();
                        maxParamRegs = Math.max(it.head.regsNeeded , maxParamRegs);
			if(typeList == null){
				throw new AST_EXCEPTION(String.format("Too many arguments for function %s", funcName), this.lineNum);
			} 
			typesCheck(typeList.head, paramType);
			typeList = typeList.tail;
		}
		if(typeList != null){
			throw new AST_EXCEPTION(String.format("Not enouth arguments for function %s", funcName), this.lineNum);
		}
		
                regsNeeded = Math.max(maxParamRegs,2);
		return ((TYPE_FUNCTION)funcType).returnType;
	}
	
	public Temp IRme()
	{
                // push all arguments to the stack
                // if method, also push class instance address
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
                    LinkedList <Temp> reversedTemps = new LinkedList <Temp>();                    
                    int numOfParams = 0;
                    if (params != null) {
                        AST_EXP_LIST currentParam;
                       
                        Temp currentParamTemp;
                        for (currentParam = params; currentParam != null ; currentParam = currentParam.tail) {
                            numOfParams++;
                        }
                        IR.getInstance().Add_IRcommand(new IRcommand_Addi("$sp","$sp",-numOfParams * 4));
                        int i = 0;
                        for (currentParam = params; currentParam != null ; currentParam = currentParam.tail) {
                            currentParamTemp = currentParam.IRme(); 
                            IR.getInstance().Add_IRcommand(new IRcommand_Push_Offset(currentParamTemp, 4*i));
                            i++;
                        }

                    }
                    if (this.classType != null && this.isMethodFromClass) {
                        Temp instanceAddr = Temp_FACTORY.getInstance().getFreshTemp();
                            IR.getInstance().Add_IRcommand(new IRcommand_LoadParamToTemp(1,instanceAddr));
                            IR.getInstance().Add_IRcommand(new IRcommand_Load(instanceAddr,instanceAddr));
                            this.classInstanceAddress = instanceAddr;
                        }

                        if (this.classInstanceAddress != null) {
                            IR.getInstance().Add_IRcommand(new IRcommand_Push(this.classInstanceAddress));
                            numOfParams++;
                        }

                    if (this.classInstanceAddress == null) { // if this is not a method but a function, jump to the label
                        IR.getInstance().Add_IRcommand(new IRcommand_jump_and_link(funcLabel));
                    }
                    else if (this.classType != null && this.classInstanceAddress != null) {
                        ArrayList keyList = new ArrayList(this.classType.virtualMethodTable.keySet());
                        int indexOfFunc = keyList.indexOf(this.funcName);
                        if (indexOfFunc == -1) {
                            System.out.println("ERROR!!!!");
                            return null;
                        }
                        Temp funcAddr = Temp_FACTORY.getInstance().getFreshTemp();
                        IR.getInstance().Add_IRcommand(new IRcommand_Load(funcAddr,this.classInstanceAddress));
                        IR.getInstance().Add_IRcommand(new IRcommand_Addi(funcAddr, funcAddr,indexOfFunc * 4));
                        IR.getInstance().Add_IRcommand(new IRcommand_Load(funcAddr,funcAddr));
                        IR.getInstance().Add_IRcommand(new IRcommand_jump_and_link(funcAddr));

                    }
                    else {
                        System.out.println("ERROR!!!!!");
                        return null;
                    }
                    IR.getInstance().Add_IRcommand(new IRcommand_Addi("$sp", "$sp", 4 * numOfParams));

                    Temp returnTemp = Temp_FACTORY.getInstance().getFreshTemp();
                    IR.getInstance().Add_IRcommand(new IRcommand_mixedMove(returnTemp, "$v0"));
                    
                    return returnTemp;
            }
        }
}
