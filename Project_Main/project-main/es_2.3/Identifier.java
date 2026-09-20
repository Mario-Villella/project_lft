public class Identifier extends Token {
    String lexeme;
    public Identifier(int tag, String s) { super(tag); lexeme=s; }
    public String toString() { return "< " + tag + ", " + lexeme + " >"; }
}