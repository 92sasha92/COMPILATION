/***********/
/* PACKAGE */
/***********/
package Temp;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class Temp_FACTORY
{
        private enum reservedTemps 
        {
            zero(0), fp(1), sp(2), ra(3);
            private final int value;
            private reservedTemps(int value) {
            this.value = value;
        }
        }

	private int counter = 4; // should be the last value in the enum + 1
	
	public Temp getFreshTemp()
	{
		return new Temp(counter++);
	}
	
        public int getReservedTempNumber(String registerName) {
            return reservedTemps.valueOf(registerName).value;
        }
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static Temp_FACTORY instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected Temp_FACTORY() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static Temp_FACTORY getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new Temp_FACTORY();
		}
		return instance;
	}
}
