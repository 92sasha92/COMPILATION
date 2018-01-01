/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class sir_MIPS_a_lot
{
	private int WORD_SIZE=4;
        private int asciizIndex=0;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
	}
	public void print_string(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
	}

        public TEMP malloc(int size) {
            TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
            int idx = t.getSerialNumber();

            fileWriter.format("\tli $a0,%d\n",size);
            fileWriter.format("\tli $v0,9\n");
	    fileWriter.format("\tsyscall\n");
            // address is now in $v0, moving to the Temp
	    fileWriter.format("\tmove Temp_%d,$v0\n",idx);
            return t;
            
        }

	public TEMP addressLocalVar(int serialLocalVarNum)
	{
		TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
		int idx = t.getSerialNumber();

		fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
		
		return t;
	}
        public void move(TEMP dst,TEMP src) {
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,Temp_%d\n",idxdst,idxsrc);		
        }
        public void load(TEMP dst,TEMP src)
	{
            load(dst,src,0);
	}
        public void load(TEMP dst,TEMP src, int offset)
	{
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%d(Temp_%d)\n",idxsrc,offset, idxdst);		
	}
	public void load_byte(TEMP dst,TEMP src)
	{
            load_byte(dst,src,0);
        }
	public void load_byte(TEMP dst,TEMP src, int offset)
        {
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tlb Temp_%d,%d(Temp_%d)\n",idxsrc,offset,idxdst);		
        }

	public void store(TEMP dst,TEMP src)
	{
            store(dst,src,0);
	}
        public void store(TEMP dst,TEMP src, int offset)
	{
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,%d(Temp_%d)\n",idxsrc,offset, idxdst);		
	}
	public void store_byte(TEMP dst,TEMP src)
	{
            store_byte(dst,src,0);
        }
	public void store_byte(TEMP dst,TEMP src, int offset)
        {
		int idxdst=dst.getSerialNumber();
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsb Temp_%d,%d(Temp_%d)\n",idxsrc,offset,idxdst);		
        }
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);
	}
        public void load_string(TEMP t, String value) 
        {
		int idx=t.getSerialNumber();
		fileWriter.format("\t.data\n");
		fileWriter.format("\tstr%d: .asciiz \"%s\"\n",asciizIndex, value);
		fileWriter.format("\t.text\n");
		fileWriter.format("\tla Temp_%d,str%d\n",idx,asciizIndex);
                asciizIndex++;
        }
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
        public void addi(TEMP dst,TEMP oprnd1,int imm)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\taddi Temp_%d,Temp_%d,%d\n",dstidx,i1,imm);
	}
        public TEMP initializeRegToZero() {
            TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
            int idx = t.getSerialNumber();
	    fileWriter.format("\tadd Temp_%d,$zero,$zero\n",idx);
            return t;

        }
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmult Temp_%d,Temp_%d\n",i1,i2);
		fileWriter.format("\tmflo Temp_%d\n",dstidx);
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d\n",i1,i2);
		fileWriter.format("\tmflo Temp_%d\n",dstidx);
	}
	public void label(String inlabel)
	{
		fileWriter.format("%s:\n",inlabel);
	}	
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void beq(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static sir_MIPS_a_lot instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected sir_MIPS_a_lot() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static sir_MIPS_a_lot getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new sir_MIPS_a_lot();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./FOLDER_5_OUTPUT/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
				
			/************************************************/
			/* [4] Print text section with entry point main */
			/************************************************/
			instance.fileWriter.print(".text\n");
			instance.fileWriter.print("main:\n");

			/******************************************/
			/* [5] Will work with <= 10 variables ... */
			/******************************************/
			instance.fileWriter.print("\taddi $fp,$sp,40\n");
		}
		return instance;
	}
}
