/* Generated By:JavaCC: Do not edit this line. NonsenseParser.java */
package nonsense;

import java.util.HashMap;

public final class NonsenseParser implements NonsenseParserConstants {

        public static String register[] = { "%eax", "%ebx", "%ecx", "%edx", "%esi", "%edi" };
        public static int regInUse[] = { 0, 0, 0, 0, 0, 0 };
        public static int offset = 4;
        public static HashMap<String, Integer> map;

        public static void main(String args[]) {
            NonsenseParser parser;
            java.io.InputStream input;

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
              parser = new NonsenseParser(input);
              System.out.println("\u005ct.intel_syntax\u005cn\u005ct.section .rodata");
              System.out.println(".io_format:\u005cn\u005ct.string \u005c"%d\u005c\u005c12\u005c\u005c0\u005c"");
              System.out.println("\u005ct.text\u005cn\u005ct.global main;\u005cn\u005ct.type main, @function");
              System.out.println("main:\u005cn\u005ctpush %ebp\u005cn\u005ctmov %ebp, %esp\u005cn\u005ctsub %esp, 64");
              System.out.println("\u005cnMY_CODE_START\u005cn");
              map = new HashMap<String, Integer>();
              parser.program();
              System.out.println("\u005cnMY_CODE_END\u005cn");
              System.out.println("leave\u005cnret");
            } catch (ParseException e) {
              System.err.println("Syntax Error: "+e.getMessage());
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
                      Token x,y,z; String ret; int i = 0;
    x = jj_consume_token(ID);
    jj_consume_token(EQ);
    expr();
      for (i = 1; i < 6; i++) {
                if (regInUse[i] == 1) {
                  System.out.println("mov dword ptr [%ebp-" + offset + "], " + register[i]);
                  map.put(x.image, offset);
                  regInUse[i] = 0;
                  offset += 4;
                  break;
                }
      }
  }

  static final public void output() throws ParseException {
                  Token x = null; String ret;
    jj_consume_token(OUTPUT);
    jj_consume_token(LPAREN);
    ret = expr();
    jj_consume_token(RPAREN);
          System.out.println("\u005cnpush dword ptr [%ebp-" + map.get(ret) + "]\u005cnpush offset flat:.io_format");
          System.out.println("call printf\u005cnadd %esp, 8\u005cn");
  }

  static final public String expr() throws ParseException {
                  String ret;
    ret = term();
      {if (true) return ret;}
    label_2:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_2;
      }
      ret = addOp();
      System.out.println("plus" + ret);
      term();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String term() throws ParseException {
                  String ret;
    ret = nterm();
       {if (true) return ret;}
    label_3:
    while (true) {
      if (jj_2_2(2)) {
        ;
      } else {
        break label_3;
      }
      mulOp();
      nterm();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String nterm() throws ParseException {
                   String ret;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    ret = eterm();
            {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  static final public String eterm() throws ParseException {
                   String ret;
    ret = factor();
            {if (true) return ret;}
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXP:
      jj_consume_token(EXP);
      eterm();
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    throw new Error("Missing return statement in function");
  }

  static final public String factor() throws ParseException {
                    Token x = null , y = null; int i = 0; String reg = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      x = jj_consume_token(INT);
      for (i = 1; i < 6; i++) {
                if (regInUse[i] == 0) {
                  regInUse[i] = 1;
                  reg = register[i];
                  break;
                }
      }
      System.out.println("mov " + reg + ", " + x);
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
            System.out.println("addOp() = " + x);
            {if (true) return x.image;}
      break;
    case MINUS:
      x = jj_consume_token(MINUS);
            {if (true) return x.image;}
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
            {if (true) return x.image;}
      break;
    case DIVIDE:
      x = jj_consume_token(DIVIDE);
            {if (true) return x.image;}
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

  static private boolean jj_3R_15() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_scan_token(DIVIDE)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_4()) return true;
    if (jj_3R_5()) return true;
    return false;
  }

  static private boolean jj_3R_7() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(13)) jj_scanpos = xsp;
    if (jj_3R_12()) return true;
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

  static private boolean jj_3_2() {
    if (jj_3R_6()) return true;
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_scan_token(LPAREN)) return true;
    return false;
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

  static private boolean jj_3R_12() {
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_5() {
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_9() {
    if (jj_scan_token(MINUS)) return true;
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
