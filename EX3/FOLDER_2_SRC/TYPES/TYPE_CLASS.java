package TYPES;

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
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members, TYPE_LIST method_List)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
		this.method_List = method_List;
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
		}
		return true;
	}
	
	public boolean isLegalFieldDec(TYPE_VAR_DEC var){
		TYPE_VAR_DEC currentVar = null;
		for (TYPE_CLASS classType = this; classType != null ; classType = classType.father) {
			for (TYPE_LIST varList = classType.data_members; varList  != null; varList = varList.tail){
                    currentVar = (TYPE_VAR_DEC)varList.head;
                    if (currentVar.name.equals(var.name)) {
						return false;
                    }
            }
		}
		return true;
	}
}
