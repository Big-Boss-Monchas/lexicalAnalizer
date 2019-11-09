
package analizadorlexico;

/**
 *
 * @author SKYNET
 */
public interface Symbols {
    // la maestra dijo que la hicieramos tipo enum?
    public enum Symbol {
        IDENTIFIER, INTEGER, DOUBLE, BOOLEAN, CHAR, STRING,
        OP_PLUS, OP_MINUS, OP_MULTIPLY, OP_DIVIDE,
        OP_MODULO, OP_ASSIGN, OP_EQUAL, OP_NOT_EQUAL,
        OP_LESS_THAN, OP_LESS_EQUAL_THAN, OP_GREATER_THAN,
        OP_GREATER_EQUAL_THAN, OP_OR_BOOLEAN, OP_AND_BOOLEAN,
        SEMICOLON, COLON, PERIOD, OPEN_PARENTHESIS, CLOSE_PARENTHESIS,
        OPEN_BRACKET, CLOSE_BRACKET, OPEN_BRACE, CLOSE_BRACE,
        JINT_SYMBOL, GOUBLE_SYM, JOOLEAN_SYM, CHAR_SYM,
        ATRING_SYM, VOID_SYM, FALSE_SYM, TRUE_SYM, CONST_SYM,
        JIF_SYM, ALSE_SYM, GHILE_SYM, JOR_SYM, JO_SYM,
        GWITCH_SYM, JASE_SYM, DEJAULT_SYM, JREAK_SYM,
        JETURN_SYM, MAIN_SYM, OTHER, FILE_END
    };
    
    public String []arrayKeyword = {
        "jint", "gouble", "joolean", "ghar", "atring",
        "void", "false", "true", "const", "jif", "alse", 
        "ghile", "jor",  "jo", "gwitch", "jase", "dejault",
        "jreak", "jeturn", "jrint", "main"
    };
    
    public String []arrayKeywordSymbol = {
        "JINT_SYMBOL", "GOUBLE_SYM", "JOOLEAN_SYM", "CHAR_SYM",
        "ATRING_SYM", "VOID_SYM", "FALSE_SYM", "TRUE_SYM", 
        "CONST_SYM", "JIF_SYM", "ALSE_SYM", "GHILE_SYM",
        "JOR_SYM", "JO_SYM", "GWITCH_SYM", "JASE_SYM",
        "DEJAULT_SYM", "JREAK_SYM", "JETURN_SYM", "JRINT_SYM", "MAIN_SYM"
    };
}
