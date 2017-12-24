package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}
	
	public boolean isEqual(TYPE_FUNCTION func){
		TYPE_LIST funcParams = func.params;
		TYPE funcReturn = func.returnType;
		if(this.returnType != func.returnType){
			return false;
		} else if(this.returnType instanceof TYPE_CLASS){
			if(this.name != func.name){
				return false;
			}
		}
		for(TYPE_LIST current = this.params; current != null; current = current.tail){
			if(funcParams == null){
				return false;
			} else if(funcParams.head != current.head){
				return false;
			} else if(funcParams.head instanceof TYPE_CLASS){
				if(funcParams.head.name != current.head.name){
					return false;
				}
			}
			funcParams = funcParams.tail;
		}
		if(funcParams != null){
			return false;
		}
		return true;
	}
}
