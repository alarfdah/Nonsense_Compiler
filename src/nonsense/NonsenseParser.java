/* Generated By:JavaCC: Do not edit this line. NonsenseParser.java */
package nonsense;

import java.util.HashMap;

public final class NonsenseParser implements NonsenseParserConstants {

        public static String register[] = { "%eax", "%ebx", "%ecx", "%edi", "%esi", "%edx" };
        public static int regInUse[] = { 0, 0, 0, 0, 0, 0 };
        public static int offset = 4;
        public static HashMap<String, Integer> map;

        public static void main(String args[]) {
            NonsenseParser parser;
            java.io.InputStream input;
            String factReg, copyReg, expoReg;

            if (args.length==1) {

              try {
                input = new java.io.FileInputStream(args[args.length-1]);
              } catch (java.io.FileNotFoundException e) {
                System.out.println("File not found.");
                return;
              }

            }
            else {
              System.out.println("Usage: nsc <inputfile>");
              return;
            }
            try {

                  factReg = getRegister(3);
              copyReg = getRegister(4);
              expoReg = getRegister(5);
              System.out.println("\u005ct.intel_syntax");
              System.out.println("\u005ct.section .rodata");
              System.out.println(".io_format:");
              System.out.println("\u005ct.string \u005c"%d\u005c\u005c12\u005c\u005c0\u005c"");
              System.out.println("\u005ct.text\u005cn\u005ct.global main;");
              System.out.println("\u005ct.type main, @function");
              // Power Function
              System.out.println("power:");
                  System.out.println("\u005ctcmp  " + expoReg + ", 0");
                  System.out.println("\u005ctje   zero");
              System.out.println("\u005ctjg   loop");
              System.out.println("\u005ctjmp  return");
              // Zero Function
              System.out.println("zero:");
                  System.out.println("\u005ctmov  " + factReg + ", 1");
                  System.out.println("\u005ctjmp  return");
              // Loop Function
              System.out.println("loop:");
                  System.out.println("\u005ctcmp  " + expoReg + ", 1");
                  System.out.println("\u005ctje   return");
                  System.out.println("\u005ctsub  " + expoReg + ", 1");
                  System.out.println("\u005ctimul " + factReg + ", " + copyReg);
                  System.out.println("\u005ctjg   power");
                  System.out.println("\u005ctjmp  return");
                  // Return Function
                  System.out.println("return:");
                  System.out.println("\u005ctret");
                  // Main Function
              System.out.println("main:");
              System.out.println("\u005ctpush %ebp");
              System.out.println("\u005ctmov  %ebp, %esp");
              System.out.println("\u005ctsub  %esp, 64");
              // Initialize Map
              map = new HashMap<String, Integer>();
              // Start Program
              parser = new NonsenseParser(input);
              parser.program();
              // Exit
              System.out.println("\u005ctleave");
              System.out.println("\u005ctret");
            } catch (ParseException e) {
              System.err.println("Syntax Error: " + e.getMessage());
            }
        }

        /*
	 * Fetches a register.
	 * If passed a number, it will retrieve the corresponding register.
	 * If passed -1, will pass a free register.
	 * Otherwise, returns "error".
	 */
        public static String getRegister(int reg) {
          if (reg == -1) {
            int i = 0;
            for (i = 1; i < 6; i++) {
              if (regInUse[i] == 0) {
                regInUse[i] = 1;
                return register[i];
                }
              }
          } else {
            regInUse[reg] = 1;
            return register[reg];
          }

          return "error";
        }


        public static void freeRegisters() {
          int i = 0;
          for (i = 1; i < 6; i++) {
            if (regInUse[i] == 1) {
              regInUse[i] = 0;
            }
          }
        }

        public static boolean isInt(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

  static final public void program() throws ParseException {
    jj_consume_token(BEGIN);
    jj_consume_token(SEMICOLON);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OUTPUT:
      case ID:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      stmt();
    }
    jj_consume_token(END);
    jj_consume_token(SEMICOLON);
  }

  static final public void stmt() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      assignment();
      break;
    case OUTPUT:
      output();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(SEMICOLON);
  }

  static final public void assignment() throws ParseException {
                      Token x,y; String expr, reg; int i = 0;
    x = jj_consume_token(ID);
    jj_consume_token(EQ);
    expr = expr();
      // If negative expression
      if (!isInt(expr) && expr.charAt(0) == '-') {
        reg = getRegister(-1);
        System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(expr.substring(1)) + "]\u005cn");
        System.out.println("\u005ctneg  " + reg + "\u005cn");
        System.out.println("\u005ctmov  dword ptr [%ebp-" + offset + "], " + reg + "\u005cn");
      // If positive expression
      } else {
        System.out.println("\u005ctmov  dword ptr [%ebp-" + offset + "], " + expr + "\u005cn");
      }
      map.put(x.image, offset);
      offset += 4;
      freeRegisters();
  }

  static final public void output() throws ParseException {
                  String ret;
    jj_consume_token(OUTPUT);
    jj_consume_token(LPAREN);
    ret = expr();
    jj_consume_token(RPAREN);
          System.out.println("\u005ctpush dword ptr [%ebp-" + map.get(ret) + "]");
          System.out.println("\u005ctpush offset flat:.io_format");
          System.out.println("\u005ctcall printf");
          System.out.println("\u005ctadd  %esp, 8");
  }

  static final public String expr() throws ParseException {
                  String x, y, addSub, reg = null; int i = 0;
    x = term();
    label_2:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_2;
      }
      addSub = addOp();
      y = term();
      // Get free register
      reg = getRegister(-1);

      // Moving x into a register
      if (isInt(x)) {   // If constant
        System.out.println("\u005ctmov  " + reg + ", " + x);
      } else if (x.charAt(0) == '%') {  // If register
        System.out.println("\u005ctmov  " + reg + ", " + x);
      } else if (x.charAt(0) == '-') {  // If negative
        System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(x.substring(1)) + "]");
        System.out.println("\u005ctneg  " + reg);
      } else {  // If memory address
        System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(x) + "]");
      }

      // Adding y to register
      if (isInt(y)) { // If constant
        System.out.println("\u005ct" + addSub + "  " + reg + ", " + y);
      } else if (y.charAt(0) == '%') { // If register
        System.out.println("\u005ct" + addSub + "  " + reg + ", " + y);
      } else { // If memory address
        System.out.println("\u005ct" + addSub + "  " + reg + ", dword ptr [%ebp-" + map.get(y) + "]");
      }
      // Set x to be the register
      x = reg;
    }
    {if (true) return x;}
    throw new Error("Missing return statement in function");
  }

  static final public String term() throws ParseException {
                  String x, y, mulDiv, eax, reg, negReg; int i = 0;
    x = nterm();
    label_3:
    while (true) {
      if (jj_2_2(2)) {
        ;
      } else {
        break label_3;
      }
      mulDiv = mulOp();
      y = nterm();
      switch(mulDiv) {
        case "imul":
          // Get free register
          reg = getRegister(-1);

                  // Moving x int a register
              if (isInt(x)) {   // If constant
                System.out.println("\u005ctmov  " + reg + ", " + x);
              } else if (x.charAt(0) == '%') { // If register
                System.out.println("\u005ctmov  " + reg + ", " + x);
              } else if (x.charAt(0) == '-') {  // If negative
                System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(x.substring(1)) + "]");
                System.out.println("\u005ctneg  " + reg);
          } else { // If memory address
                System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(x) + "]");
              }

              // Multiplying register by y
              if (isInt(y)) { // If constant
                System.out.println("\u005ct" + mulDiv + " " + reg + ", " + y);
              } else if (y.charAt(0) == '%') { // If register
                System.out.println("\u005ct" + mulDiv + " " + reg + ", " + y);
              } else if (y.charAt(0) == '-') {  // If negative
                negReg = getRegister(-1);
                System.out.println("\u005ctmov  " + negReg + ", dword ptr [%ebp-" + map.get(y.substring(1)) + "]");
                System.out.println("\u005ctneg  " + negReg);
                System.out.println("\u005ct" + mulDiv + " " + reg + ", " + negReg);
          } else { // If memory address
                System.out.println("\u005ct" + mulDiv + " " + reg + ", dword ptr [%ebp-" + map.get(y) + "]");
              }
              x = reg;
        break;
        case "idiv":
          eax = getRegister(0); // %eax
          // Moving x in to eax
              if (isInt(x)) {   // If constant
                System.out.println("\u005ctmov  " + eax + ", " + x);
              } else if (x.charAt(0) == '%') { // If register
                System.out.println("\u005ctmov  " + eax + ", " + x);
              } else if (x.charAt(0) == '-') {  // If negative
                System.out.println("\u005ctmov  " + eax + ", dword ptr [%ebp-" + map.get(x.substring(1)) + "]");
                System.out.println("\u005ctneg  " + eax);
          } else { // If memory address
                System.out.println("\u005ctmov  " + eax + ", dword ptr [%ebp-" + map.get(x) + "]");
              }


              reg = getRegister(-1);
              // Dividing eax by y
              if (isInt(y)) { // If constant
                System.out.println("\u005ctmov  " + reg + ", " + y);
                System.out.println("\u005ctcdq");
                System.out.println("\u005ct" + mulDiv + " " + reg);
              } else if (y.charAt(0) == '%') { // If register
                System.out.println("\u005ctcdq");
                System.out.println("\u005ct" + mulDiv + " " + y);
              } else if (y.charAt(0) == '-') {  // If negative
                negReg = getRegister(-1);
                System.out.println("\u005ctmov  " + negReg + ", dword ptr [%ebp-" + map.get(y.substring(1)) + "]");
                System.out.println("\u005ctneg  " + negReg);
                System.out.println("\u005ctcdq");
                System.out.println("\u005ct" + mulDiv + " " + negReg);
          } else { // If memory address
                System.out.println("\u005ctmov  " + reg + ", dword ptr [%ebp-" + map.get(y) + "]");
                System.out.println("\u005ctcdq");
                System.out.println("\u005ct" + mulDiv + " " + reg);
              }
              x = eax;
        break;
      }
    }
     {if (true) return x;}
    throw new Error("Missing return statement in function");
  }

  static final public String nterm() throws ParseException {
                   Token minus = null; String ret;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      minus = jj_consume_token(MINUS);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    ret = eterm();
            if (minus != null) {
              {if (true) return "-" + ret;}
            }
            {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  static final public String eterm() throws ParseException {
  Token exponent = null;
  String ret, eterm, factReg, copyReg, expoReg;
  int exists = 0;
    ret = factor();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXP:
      exponent = jj_consume_token(EXP);
      eterm = eterm();
            // Registers
                factReg = getRegister(3);       // %edi
            copyReg = getRegister(4);   // %esi
            expoReg = getRegister(5);   // %edx

                // Move exponent into register
            if (isInt(eterm)) { // If constant
                  System.out.println("\u005ctmov  " + expoReg + ", " + eterm);
                } else if (eterm.charAt(0) == '%') { // If register
                  System.out.println("\u005ctmov  " + expoReg + ", " + eterm);
                } else { // If memory address
                  System.out.println("\u005ctmov  " + expoReg + ", dword ptr [%ebp-" + map.get(eterm) + "]");
                }

                // Move factor into register
            if (isInt(ret)) { // If constant
                  System.out.println("\u005ctmov  " + factReg + ", " + ret);
            } else if (ret.charAt(0) == '%') { // If register
                  System.out.println("\u005ctmov  " + factReg + ", " + ret);
                } else { // If memory address
              System.out.println("\u005ctmov  " + factReg + ", dword ptr [%ebp-" + map.get(ret) + "]");
            }

            // Make a copy of factor
                System.out.println("\u005ctmov  " + copyReg + ", " + factReg);

                // Call power
                System.out.println("\u005ctcall power");

                ret = factReg;
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
            {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  static final public String factor() throws ParseException {
                    Token x = null, y = null; int i = 0; String reg = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      x = jj_consume_token(INT);
      {if (true) return x.image;}
      break;
    case ID:
      y = jj_consume_token(ID);
      {if (true) return y.image;}
      break;
    case LPAREN:
      jj_consume_token(LPAREN);
      expr();
      jj_consume_token(RPAREN);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String addOp() throws ParseException {
                   Token x = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      x = jj_consume_token(PLUS);
            {if (true) return "add";}
      break;
    case MINUS:
      x = jj_consume_token(MINUS);
            {if (true) return "sub";}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String mulOp() throws ParseException {
                   Token x = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TIMES:
      x = jj_consume_token(TIMES);
            {if (true) return "imul";}
      break;
    case DIVIDE:
      x = jj_consume_token(DIVIDE);
            {if (true) return "idiv";}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_3R_14() {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) return true;
    }
    }
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_scan_token(DIVIDE)) return true;
    return false;
  }

  static private boolean jj_3R_4() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_8()) {
    jj_scanpos = xsp;
    if (jj_3R_9()) return true;
    }
    return false;
  }

  static private boolean jj_3R_8() {
    if (jj_scan_token(PLUS)) return true;
    return false;
  }

  static private boolean jj_3R_10() {
    if (jj_scan_token(TIMES)) return true;
    return false;
  }

  static private boolean jj_3R_6() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_10()) {
    jj_scanpos = xsp;
    if (jj_3R_11()) return true;
    }
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_4()) return true;
    if (jj_3R_5()) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_6()) return true;
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  static private boolean jj_3R_7() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(13)) jj_scanpos = xsp;
    if (jj_3R_12()) return true;
    return false;
  }

  static private boolean jj_3R_9() {
    if (jj_scan_token(MINUS)) return true;
    return false;
  }

  static private boolean jj_3R_5() {
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_12() {
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public NonsenseParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80080,0x80080,0x2000,0x10000,0x180400,0x3000,0xc000,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[2];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public NonsenseParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public NonsenseParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new NonsenseParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public NonsenseParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new NonsenseParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public NonsenseParser(NonsenseParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(NonsenseParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[21];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 21; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
