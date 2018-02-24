package AST;

import TYPES.*;
import Temp.*;
import SYMBOL_TABLE.*;
import IR.*;

public class AST_EXP_VAR_FIELD extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public String fieldName;
        public int fieldOffset;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_FIELD(AST_EXP_VAR var,String fieldName)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== var -> var DOT ID( %s )\n",fieldName);
		this.var = var;
		this.fieldName = fieldName;
                this.fieldOffset = 0;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.format("FIELD\nNAME\n(___.%s)\n",fieldName);

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n___.%s",fieldName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);		
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
            regsNeeded = 2;
		TYPE t = null;
		TYPE_CLASS tc = null;
		this.varDefType = SYMBOL_TABLE.getInstance().findDefType(fieldName);
                if (this.varDefType == null) {
                    this.varDefType = SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD;
                }
		/******************************/
		/* [1] Recursively semant var */
		/******************************/
		if (var != null) t = var.SemantMe();

		/*********************************/
		/* [2] Make sure type is a class */
		/*********************************/
		if (!(t instanceof TYPE_CLASS))
		{
			throw new AST_EXCEPTION(String.format("access %s field of a non-class variable\n", fieldName), this.lineNum);
		}
		else
		{
			tc = (TYPE_CLASS) t;
		}
		
                return getFieldVardecAndSetOffset(tc);
		
		
	}
        public Temp IRme(boolean shouldLoad)
        {
            Temp varAddress = var.IRme();
            IR.getInstance().Add_IRcommand(new IRcommand_Addi(varAddress,varAddress,fieldOffset));
            if (!shouldLoad) {
                return varAddress;
            }
            else {
                Temp t = Temp_FACTORY.getInstance().getFreshTemp();
                IR.getInstance().Add_IRcommand(new IRcommand_Load(t,varAddress));

                return t;
            }
        }

        public TYPE getFieldVardecAndSetOffset(TYPE_CLASS tc) throws AST_EXCEPTION{
            TYPE_VAR_DEC currentVar = null;
            TYPE_CLASS currentClass = null;
            TYPE_CLASS currentClassSon = null;
            for (currentClass = tc; currentClass.father != null ;currentClass = currentClass.father);
            // currentClass is now the root class

            fieldOffset = 4; // start from 4 and not 0 to skip virtual table
            // we are about to go down in the inheritance tree in a stupid way: start from the root, then look for the class whose father is the root, and so on...
            while (currentClass != null) {

                for (TYPE_LIST varList = currentClass.data_members; varList  != null; varList = varList.tail){
                    currentVar = (TYPE_VAR_DEC)varList.head;
                    if (fieldName.equals(currentVar.name)) {
                        return ((TYPE_VAR_DEC)currentVar).t;
                    }

                    fieldOffset+=4;

                }

                // set the direct son of currentClass
                if (currentClass.name.equals(tc.name)) {
                    currentClass = null;
                }
                else {
                    // set currentClassSon
                    for (currentClassSon = tc; !currentClassSon.father.name.equals(currentClass.name) ;currentClassSon = currentClassSon.father);
                    // next iteration
                    currentClass = currentClassSon;
                }

            }
            /*********************************************/
            /* [4] fieldName does not exist in class var */
            /*********************************************/
            throw new AST_EXCEPTION(String.format("field %s does not exist in class\n", fieldName), this.lineNum);
        }
}
