/* The following code was generated by JFlex 1.4.3 on 12/31/17 6:12 PM */

/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/*************/
/* USER CODE */
/*************/
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/


class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\0\1\7"+
    "\5\0\1\15\1\16\1\11\1\12\1\26\1\13\1\27\1\10\1\4"+
    "\11\5\1\31\1\30\1\33\1\32\1\34\2\0\17\6\1\14\12\6"+
    "\1\17\1\0\1\20\3\0\1\35\1\6\1\40\1\45\1\42\1\51"+
    "\1\6\1\50\1\24\2\6\1\25\1\6\1\23\3\6\1\36\1\41"+
    "\1\44\1\46\1\6\1\47\1\43\1\37\1\6\1\21\1\0\1\22"+
    "\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\2\3\1\4\1\1\1\5\1\6"+
    "\1\7\1\10\1\4\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\2\4\1\17\1\20\1\21\1\1\1\22\1\23"+
    "\1\24\5\4\1\0\1\25\1\2\1\1\3\4\1\26"+
    "\1\27\5\4\2\0\1\6\1\30\1\31\12\4\1\32"+
    "\1\4\1\33\1\4\1\34\1\35\1\4\1\36";

  private static int [] zzUnpackAction() {
    int [] result = new int[70];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\52\0\124\0\52\0\52\0\176\0\250\0\322"+
    "\0\374\0\52\0\52\0\52\0\u0126\0\52\0\52\0\52"+
    "\0\52\0\52\0\52\0\u0150\0\u017a\0\52\0\52\0\52"+
    "\0\u01a4\0\52\0\52\0\52\0\u01ce\0\u01f8\0\u0222\0\u024c"+
    "\0\u0276\0\322\0\52\0\u02a0\0\u02ca\0\u02f4\0\u031e\0\u0348"+
    "\0\250\0\52\0\u0372\0\u039c\0\u03c6\0\u03f0\0\u041a\0\u0444"+
    "\0\u046e\0\250\0\250\0\250\0\u0498\0\u04c2\0\u04ec\0\u0516"+
    "\0\u0540\0\u056a\0\u0594\0\u05be\0\u05e8\0\u0612\0\250\0\u063c"+
    "\0\250\0\u0666\0\250\0\250\0\u0690\0\250";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[70];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\2\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21"+
    "\1\22\1\23\1\24\1\25\1\7\1\26\1\27\1\30"+
    "\1\31\1\32\1\33\1\34\1\35\1\36\1\7\1\37"+
    "\1\7\1\40\4\7\1\41\2\7\54\0\1\4\53\0"+
    "\2\6\50\0\3\7\5\0\1\7\6\0\3\7\7\0"+
    "\15\7\4\0\3\42\1\43\4\0\1\42\6\0\3\42"+
    "\7\0\15\42\10\0\1\44\1\45\44\0\3\7\5\0"+
    "\1\46\6\0\3\7\7\0\15\7\4\0\3\7\5\0"+
    "\1\7\6\0\1\7\1\47\1\7\7\0\5\7\1\50"+
    "\7\7\4\0\3\7\5\0\1\7\6\0\3\7\7\0"+
    "\14\7\1\51\32\0\1\52\23\0\3\7\5\0\1\7"+
    "\6\0\3\7\7\0\1\7\1\53\13\7\4\0\3\7"+
    "\5\0\1\7\6\0\3\7\7\0\5\7\1\54\7\7"+
    "\4\0\3\7\5\0\1\7\6\0\2\7\1\55\7\0"+
    "\15\7\4\0\3\7\5\0\1\7\6\0\3\7\7\0"+
    "\6\7\1\56\6\7\4\0\3\7\5\0\1\7\6\0"+
    "\3\7\7\0\13\7\1\57\1\7\2\44\1\0\47\44"+
    "\1\60\6\45\2\60\1\61\2\60\1\45\6\60\3\45"+
    "\7\60\15\45\4\0\3\7\5\0\1\62\6\0\3\7"+
    "\7\0\15\7\4\0\3\7\5\0\1\7\6\0\2\7"+
    "\1\63\7\0\15\7\4\0\3\7\5\0\1\7\6\0"+
    "\3\7\7\0\12\7\1\64\2\7\4\0\3\7\5\0"+
    "\1\7\6\0\3\7\7\0\1\7\1\65\13\7\4\0"+
    "\3\7\5\0\1\7\6\0\3\7\7\0\7\7\1\66"+
    "\5\7\4\0\3\7\5\0\1\7\6\0\3\7\7\0"+
    "\1\67\14\7\4\0\3\7\5\0\1\7\6\0\3\7"+
    "\7\0\7\7\1\70\5\7\4\0\3\7\5\0\1\7"+
    "\6\0\1\7\1\71\1\7\7\0\15\7\11\60\1\61"+
    "\50\60\1\4\1\61\40\60\4\0\3\7\5\0\1\7"+
    "\6\0\3\7\7\0\1\72\14\7\4\0\3\7\5\0"+
    "\1\7\6\0\3\7\7\0\11\7\1\73\3\7\4\0"+
    "\3\7\5\0\1\7\6\0\3\7\7\0\4\7\1\74"+
    "\10\7\4\0\3\7\5\0\1\7\6\0\3\7\7\0"+
    "\5\7\1\75\7\7\4\0\3\7\5\0\1\7\6\0"+
    "\2\7\1\76\7\0\15\7\4\0\3\7\5\0\1\7"+
    "\6\0\3\7\7\0\2\7\1\77\12\7\4\0\3\7"+
    "\5\0\1\7\6\0\3\7\7\0\1\7\1\100\13\7"+
    "\4\0\3\7\5\0\1\7\6\0\3\7\7\0\4\7"+
    "\1\101\10\7\4\0\3\7\5\0\1\7\6\0\1\102"+
    "\2\7\7\0\15\7\4\0\3\7\5\0\1\7\6\0"+
    "\3\7\7\0\5\7\1\103\7\7\4\0\3\7\5\0"+
    "\1\7\6\0\1\104\2\7\7\0\15\7\4\0\3\7"+
    "\5\0\1\7\6\0\3\7\7\0\10\7\1\105\4\7"+
    "\4\0\3\7\5\0\1\7\6\0\3\7\7\0\4\7"+
    "\1\106\10\7";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1722];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\2\11\4\1\3\11\1\1\6\11"+
    "\2\1\3\11\1\1\3\11\5\1\1\0\1\11\6\1"+
    "\1\11\5\1\2\0\25\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[70];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	/*********************************************************************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/*********************************************************************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/*******************************************/
	/* Enable line number extraction from main */
	/*******************************************/
	public int getLine() { return yyline + 1; } 

	/**********************************************/
	/* Enable token position extraction from main */
	/**********************************************/
	public int getTokenStartPosition() { return yycolumn + 1; } 


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 122) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 21: 
          { return symbol(TokenNames.STRING, new String( yytext()));
          }
        case 31: break;
        case 8: 
          { return symbol(TokenNames.MINUS);
          }
        case 32: break;
        case 13: 
          { return symbol(TokenNames.LBRACE);
          }
        case 33: break;
        case 6: 
          { return symbol(TokenNames.TIMES);
          }
        case 34: break;
        case 28: 
          { return symbol(TokenNames.WHILE);
          }
        case 35: break;
        case 19: 
          { return symbol(TokenNames.LT);
          }
        case 36: break;
        case 5: 
          { return symbol(TokenNames.DIVIDE);
          }
        case 37: break;
        case 26: 
          { return symbol(TokenNames.ARRAY);
          }
        case 38: break;
        case 9: 
          { return symbol(TokenNames.LPAREN);
          }
        case 39: break;
        case 17: 
          { return symbol(TokenNames.SEMICOLON);
          }
        case 40: break;
        case 25: 
          { return symbol(TokenNames.NEW);
          }
        case 41: break;
        case 7: 
          { return symbol(TokenNames.PLUS);
          }
        case 42: break;
        case 11: 
          { return symbol(TokenNames.LBRACK);
          }
        case 43: break;
        case 20: 
          { return symbol(TokenNames.GT);
          }
        case 44: break;
        case 1: 
          { return symbol(TokenNames.error);
          }
        case 45: break;
        case 18: 
          { return symbol(TokenNames.EQ);
          }
        case 46: break;
        case 2: 
          { /* just skip what was found, do nothing */
          }
        case 47: break;
        case 14: 
          { return symbol(TokenNames.RBRACE);
          }
        case 48: break;
        case 23: 
          { return symbol(TokenNames.ASSIGN);
          }
        case 49: break;
        case 30: 
          { return symbol(TokenNames.EXTENDS);
          }
        case 50: break;
        case 29: 
          { return symbol(TokenNames.RETURN);
          }
        case 51: break;
        case 15: 
          { return symbol(TokenNames.COMMA);
          }
        case 52: break;
        case 4: 
          { return symbol(TokenNames.ID, new String( yytext()));
          }
        case 53: break;
        case 27: 
          { return symbol(TokenNames.CLASS);
          }
        case 54: break;
        case 3: 
          { if(yytext().length() < 7 ){
		int num = new Integer(yytext());
		if(num <= Math.pow(2,15) && num >= 0){
			return symbol(TokenNames.INT, num);
		}
	}
	return symbol(TokenNames.error);
          }
        case 55: break;
        case 10: 
          { return symbol(TokenNames.RPAREN);
          }
        case 56: break;
        case 24: 
          { return symbol(TokenNames.NIL);
          }
        case 57: break;
        case 12: 
          { return symbol(TokenNames.RBRACK);
          }
        case 58: break;
        case 22: 
          { return symbol(TokenNames.IF);
          }
        case 59: break;
        case 16: 
          { return symbol(TokenNames.DOT);
          }
        case 60: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case YYINITIAL: {
              return symbol(TokenNames.EOF);
            }
            case 71: break;
            default:
              { return new java_cup.runtime.Symbol(TokenNames.EOF); }
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
