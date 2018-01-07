package TempReplacer;

import java.io.*;
import Assem.*;
import Temp.*;
import FlowGraph.*;
import RegAlloc.*;
//import Liveness.*;

public class TempReplacer {
    public static final String inputFilename = "FOLDER_5_OUTPUT/MIPS.txt";
    public static InstrList last = null, instList = null;
    public static FlowGraph flowGraph = null;

    public static void ReplaceTemps() throws IOException{
		
        BufferedReader file_reader;
        file_reader = new BufferedReader(new FileReader(inputFilename));
        String line;
        String tokens[] = null;
        String delims = "[ ,()]";
        String command = null;
        String labelName = null;
        TempList dstTemps = null, srcTemps = null;
        LabelList labelList = null;
        LABEL labelDefinition = null;
        while ((line = file_reader.readLine()) != null) {
            tokens = line.replace("\t","").split(delims);
            command = tokens[0];
            System.out.println(command);
            switch (command) {
                case "add":
                case "sub":
                case "mul":
                case "div":
                    dstTemps = new TempList(getTemp(tokens[1]),null);
                    srcTemps = new TempList(getTemp(tokens[2]),new TempList(getTemp(tokens[3]),null));
                    OPER op = new OPER(line, dstTemps, srcTemps);
                    addInstruction(op);
                    break;
                case "addi":
                    dstTemps = new TempList(getTemp(tokens[1]),null);
                    srcTemps = new TempList(getTemp(tokens[2]),null);
                    OPER addi = new OPER(line, dstTemps, srcTemps);
                    addInstruction(addi);
                   break;
                case "j":
                   labelName = tokens[1];
                   labelList = new LabelList(Label.getLabel(labelName),null);
                   OPER j = new OPER(line, null, null, labelList);
                   addInstruction(j);
                   break;
                case "jal":
                   OPER jal = new OPER(line, null, null);
                   addInstruction(jal);
                   break;
				case "move":
					MOVE move = new MOVE(line,getTemp(tokens[1]),getTemp(tokens[2]));
					addInstruction(move);
                   break;
				case "li":
					dstTemps = new TempList(getTemp(tokens[1]),null);
					OPER li = new OPER(line, dstTemps, null);
                    addInstruction(li);
					break;
				case "lb":
				case "lw":
					dstTemps = new TempList(getTemp(tokens[1]),null);
					srcTemps = new TempList(getTemp(tokens[3]),null);
					OPER lw = new OPER(line, dstTemps, srcTemps);
					addInstruction(lw);
					break;
				case "sw":
					dstTemps = new TempList(getTemp(tokens[3]),null);
					srcTemps = new TempList(getTemp(tokens[1]),null);
					OPER sw = new OPER(line, dstTemps, srcTemps);
					addInstruction(sw);
					break;
				case "blt":
				case "bge":
				case "beq":
				case "bne":
					srcTemps = new TempList(getTemp(tokens[1]),new TempList(getTemp(tokens[2]),null));
					labelName = tokens[3];
                    labelList = new LabelList(Label.getLabel(labelName),null);
					OPER bOp = new OPER(line, null, srcTemps, labelList);
					addInstruction(bOp);
					break;
                default:
                   if (command.startsWith("Label_")) {
                       labelName = command.substring(0,command.length()-1);
                       labelDefinition = new LABEL(command, Label.getLabel(labelName));
                       addInstruction(labelDefinition);
                       break;
                   }

            }
        }
        printInstrList();
        flowGraph = new AssemFlowGraph(instList);
        //InterferenceGraph interGraph = new Liveness(flowGraph);

    }

    public static Temp getTemp(String tempStr) {

        int tempNum;
        String delim = "_";
        if (tempStr.charAt(0) != '$') {
            tempNum =  Integer.parseInt(tempStr.split(delim)[1]);
            return new Temp(tempNum);
        }
        return new Temp(Temp_FACTORY.getInstance().getReservedTempNumber(tempStr.substring(1)));
    }

    public static void addInstruction(Instr inst) {
        if (last != null) {
            last.tail = new InstrList(inst, null);
            last = last.tail;
        } else {
            instList = new InstrList(inst, null);
            last = instList;
        }
    }
    public static void printInstrList() {
        InstrList kaki = instList;
        for (;kaki != null ;kaki = kaki.tail ) {
            System.out.println("@@@@@@"+kaki.head.assem);
        }

    }



}
