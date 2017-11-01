   
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				/************************/
				/* [6] Print to console */
				/************************/
				switch (s.sym) {
					case TokenNames.error: {
						System.out.print("ERROR");
						file_writer.print("ERROR");
					} break;
					case TokenNames.PLUS: {
						System.out.print("PLUS");
						file_writer.print("PLUS");
					} break;
					case TokenNames.MINUS: {
						System.out.print("MINUS");
						file_writer.print("MINUS");
					} break;
					case TokenNames.TIMES: {
						System.out.print("TIMES");
						file_writer.print("TIMES");
					} break;
					case TokenNames.DIVIDE: {
						System.out.print("DIVIDE");
						file_writer.print("DIVIDE");
					} break;
					case TokenNames.LPAREN: {
						System.out.print("LPAREN");
						file_writer.print("LPAREN");
					} break;
					case TokenNames.RPAREN: {
						System.out.print("RPAREN");
						file_writer.print("RPAREN");
					} break;
					case TokenNames.SEMICOLON: {
						System.out.print("SEMICOLON");
						file_writer.print("SEMICOLON");
					} break;
					case TokenNames.NUMBER: {
						System.out.print("INT("+s.value+")");
						file_writer.print("INT("+s.value+")");
					} break;
					case TokenNames.ID: {
						System.out.print("ID("+s.value+")");
						file_writer.print("ID("+s.value+")");
					} break;
					case TokenNames.COMMA: {
						System.out.print("COMMA");
						file_writer.print("COMMA");
					} break;
					case TokenNames.DOT: {
						System.out.print("DOT");
						file_writer.print("DOT");
					} break;
					case TokenNames.EQ: {
						System.out.print("EQ");
						file_writer.print("EQ");
					} break;
					case TokenNames.LT: {
						System.out.print("LT");
						file_writer.print("LT");
					} break;
					case TokenNames.LBRACK: {
						System.out.print("LBRACK");
						file_writer.print("LBRACK");
					} break;
					case TokenNames.RBRACK: {
						System.out.print("RBRACK");
						file_writer.print("RBRACK");
					} break;
					case TokenNames.LBRACE: {
						System.out.print("LBRACE");
						file_writer.print("LBRACE");
					} break;
					case TokenNames.RBRACE: {
						System.out.print("RBRACE");
						file_writer.print("RBRACE");
					} break;
					case TokenNames.GT: {
						System.out.print("GT");
						file_writer.print("GT");
					} break;
					case TokenNames.STRING: {
						System.out.print("STRING("+s.value+")");
						file_writer.print("STRING("+s.value+")");
					} break;
					case TokenNames.IF: {
						System.out.print("IF");
						file_writer.print("IF");
					} break;
					case TokenNames.ASSIGN: {
						System.out.print("ASSIGN");
						file_writer.print("ASSIGN");
					} break;
					case TokenNames.NIL: {
						System.out.print("NIL");
						file_writer.print("NIL");
					} break;
					case TokenNames.NEW: {
						System.out.print("NEW");
						file_writer.print("NEW");
					} break;
					case TokenNames.ARRAY: {
						System.out.print("ARRAY");
						file_writer.print("ARRAY");
					} break;
					case TokenNames.CLASS: {
						System.out.print("CLASS");
						file_writer.print("CLASS");
					} break;
					case TokenNames.WHILE: {
						System.out.print("WHILE");
						file_writer.print("WHILE");
					} break;
					case TokenNames.RETURN: {
						System.out.print("RETURN");
						file_writer.print("RETURN");
					} break;
					case TokenNames.EXTENDS: {
						System.out.print("EXTENDS");
						file_writer.print("EXTENDS");
					} break;
				}
				
				System.out.print("[");
				System.out.print(l.getLine());
				System.out.print(",");
				System.out.print(l.getTokenStartPosition());
				System.out.print("]");
				System.out.print("\r\n");
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				file_writer.print("[");
				file_writer.print(l.getLine());
				file_writer.print(",");
				file_writer.print(l.getTokenStartPosition());
				file_writer.print("]");
				file_writer.print("\r\n");
				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
			}
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


