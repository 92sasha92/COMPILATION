package TYPES;

public class TYPE_VAR_DEC extends TYPE
{
	public TYPE t;
	public String name;

        public int integerInitialValue;
        public String stringInitialValue;
	
	public TYPE_VAR_DEC(TYPE t,String name)
	{
		this.t = t;
		this.name = name;
	}

        public void setInitialValue(String initial) {
            this.stringInitialValue = initial;
        }
        public void setInitialValue(int initial) {
            this.integerInitialValue = initial;
        }
}
