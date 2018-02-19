package AST;
import TYPES.*;
import SYMBOL_TABLE.*;


public class AST_EXP_NEW extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String type;
	public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_NEW(String type,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.type = type;
		this.exp = exp;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		if(exp == null) System.out.format("NEW(%s)\n",type);
		if(exp != null) System.out.format("NEW(%s)[]\n",type);
		

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (exp != null) exp.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		if(exp == null) AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NEW(%s)\n",type));
		if(exp != null) AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NEW(%s[])\n",type));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);		
	}
        public TYPE SemantMe() throws AST_EXCEPTION{
            // type is Son or int
            // exp is 8 or 3+3

            /**************************************/
            /* [1] if the brackets are not empty, check that have INT inside */
            /**************************************/
            if (exp != null) { 
                TYPE expType = exp.SemantMe();
                if (!(expType instanceof TYPE_INT)) {
	            throw new AST_EXCEPTION(String.format("Array size must be of integral size. got %s instead\n", expType.name), this.lineNum);
                }
            }
	
            /**************************************/
            /* [2] check that type is in the scope */
            /**************************************/

            TYPE varType = SYMBOL_TABLE.getInstance().find(type);
            if (varType == null)
            {
                throw new AST_EXCEPTION(String.format("Non existing type %s\n", type), this.lineNum);
            
                /**************************************/
                /* [3] if our type is class and the brackets are empty, we return TYPE_CLASS */
                /**************************************/
            } else if (varType instanceof TYPE_CLASS && exp == null) {
                return varType;
                /**************************************/
                /* [3] otherwise we have brackets so we return TYPE_ARRAY for valid types */
                /**************************************/
            } else if ((varType instanceof TYPE_INT) || (varType instanceof TYPE_ARRAY) || (varType instanceof TYPE_CLASS) || (varType instanceof TYPE_STRING)) {
                return new TYPE_ARRAY(varType.name ,varType);
                /**************************************/
                /* [3] otherwise we have brackets but invalid type */
                /**************************************/
            } else { 
                throw new AST_EXCEPTION(String.format("'%s' is not a variable type\n", type), this.lineNum);
            }
            
        }
        public Temp IRme() throws AST_EXCEPTION {
            int sizeToMalloc = 0;
            Temp sizeToMallocTemp = null;
            Temp mallocAddress = null;
            TYPE_CLASS classType = null;
            if (exp == null) { // class
                classType = (TYPE_CLASS)(SYMBOL_TABLE.getInstance().find(type));
		for (; classType != null ; classType = classType.father) {
		    for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                        sizeToMalloc += 4;
                    }
                }
                sizeToMalloc += 4; // for virtual methods table
                IR.getInstance().Add_IRcommand(new IRcommandConstInt(sizeToMallocTemp,sizeToMalloc));
            }
            else { // array
                sizeToMallocTemp = exp.IRme();
            }
            IR.getInstance().Add_IRcommand(new IRcommand_Malloc(sizeToMallocTemp, mallocAddress));
            if (exp == null) { // class
                TYPE_VAR_DEC currentVar = null;
                TYPE_CLASS currentClass,currentClassSon = null;
                for (currentClass = classType; currentClass.father != null ;currentClass = currentClass.father);
                // currentClass is now the root class

                int fieldOffset = 0;
                // we are about to go down in the inheritance tree in a stupid way: start from the root, then look for the class whose father is the root, and so on...
                    while (currentClass != null) {

                        for (TYPE_LIST varList = currentClass.data_members; varList  != null; varList = varList.tail){
                            currentVar = (TYPE_VAR_DEC)varList.head;
                            Temp initializedFieldTemp = null;
                            if (currentVar.initialValue != null) {
                                initializedFieldTemp = currentVar.IRme();
                            }
                            fieldOffset++;

                        }

                        // set the direct son of currentClass
                        if (currentClass.name.equals(classType.name)) {
                            currentClass = null;
                        }
                        else {
                            // set currentClassSon
                            for (currentClassSon = classType; !currentClass.father.name.equals(currentClass.name) != null ;currentClassSon = currentClassSon.father);
                            // next iteration
                            currentClass = currentClassSon;
                        }

            }

        }
        }
}
