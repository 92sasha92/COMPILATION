package TYPES;
import java.util.LinkedHashMap;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
	
	public TYPE_LIST method_List;

        public LinkedHashMap<String, String> virtualMethodTable;
	
        public int localVariablesCounter;
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members, TYPE_LIST method_List)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
		this.method_List = method_List;
                this.setVirtualMethodTable();
	}
	
        public void setVirtualMethodTable() {
            if (this.father != null) {
                this.virtualMethodTable = (LinkedHashMap)this.father.virtualMethodTable.clone();
            }
            else {
                this.virtualMethodTable = new LinkedHashMap<String,String>();
            }
			
            TYPE_FUNCTION currentMethod = null;
            for (TYPE_LIST methodList = this.method_List; methodList  != null; methodList = methodList.tail){
                    currentMethod = (TYPE_FUNCTION)methodList.head;
                    this.virtualMethodTable.put(currentMethod.name, this.name);
            }
        }

	public boolean isSonOf(String name){
		TYPE_CLASS classType;
		for (classType = this; classType != null ; classType = classType.father) {
			if(classType.name.equals(name)){
				return true;
			}
		}
		return false;
	}

	public boolean isLegalMethodDec(String funcName, TYPE_FUNCTION func){
		TYPE_FUNCTION currentMethod = null;
		TYPE_VAR_DEC currentVar = null;
		for (TYPE_CLASS classType = this; classType != null ; classType = classType.father) {
			for (TYPE_LIST methodList = classType.method_List; methodList  != null; methodList = methodList.tail){
                    currentMethod = (TYPE_FUNCTION)methodList.head;
                    if (currentMethod.name.equals(funcName)) {
						if(!currentMethod.isEqual(func)){
							return false;
						} else {
							return true;
						}
                    }
            }
			for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                currentVar = (TYPE_VAR_DEC)varList.head;
                if (currentVar.name.equals(funcName)) {
					return false;
                }
            }
		}
		return true;
	}
	
	public boolean isLegalFieldDec(TYPE_VAR_DEC var){
		TYPE_FUNCTION currentMethod = null;
		TYPE_VAR_DEC currentVar = null;
		for (TYPE_CLASS classType = this; classType != null ; classType = classType.father) {
			for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                    currentVar = (TYPE_VAR_DEC)varList.head;
                    if (currentVar.name.equals(var.name)) {
						return false;
                    }
            }
			for (TYPE_LIST methodList = classType.method_List; methodList  != null; methodList = methodList.tail){
                    currentMethod = (TYPE_FUNCTION)methodList.head;
                    if (currentMethod.name.equals(var.name)) {
						return false;
                    }
            }
		}
		return true;
	}
	
	public TYPE getField(String name){
		TYPE_VAR_DEC currentVar = null;
		for (TYPE_CLASS classType = this; classType != null ; classType = classType.father) {
			for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                    currentVar = (TYPE_VAR_DEC)varList.head;
                    if (currentVar.name.equals(name)) {
						return varList.head;
                    }
            }
		}
		return null;
	}
        public TYPE getFunc(String name){
		TYPE_FUNCTION currentFunc = null;
		for (TYPE_CLASS classType = this; classType != null ; classType = classType.father) {
			for (TYPE_LIST funcList = classType.method_List; funcList  != null; funcList = funcList.tail){
                    currentFunc = (TYPE_FUNCTION)funcList.head;
                    if (currentFunc.name.equals(name)) {
			    return funcList.head;
                    }
            }
		}
		return null;
	}
        public int getFieldIndex(String name){
            int counter = this.localVariablesCounter;
            int fieldCounter =0;
            int foundIndex = -1;
            


            TYPE_VAR_DEC currentVar = null;
            for (TYPE_CLASS classType = this.father; classType != null ; classType = classType.father) {
                for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                    currentVar = (TYPE_VAR_DEC)varList.head;
                    if (currentVar.name.equals(name)) {
                        foundIndex = fieldCounter;
                    }
                    fieldCounter++;
                }
                counter -= fieldCounter;
                if (foundIndex > -1) {
                    counter += foundIndex;
                    return counter+1;
                }
                fieldCounter = 0;
            }
            return -1;
	}
        // public void getLocalVariablesCounter(int localVariablesCounter) {
        //     this.localVariablesCounter = localVariablesCounter;
        // }
}
