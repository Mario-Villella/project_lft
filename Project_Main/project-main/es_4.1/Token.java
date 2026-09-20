public class Token {
    public final int tag;
    public String lexeme = "";
    public Token(int t) { tag = t;  }
    public String toString() {return "< " + tag + " >";}
    public static final Token
	not = new Token('!'),
	lpt = new Token('('),
	rpt = new Token(')'),
	lpq = new Token('['),
	rpq = new Token(']'),
	lpg = new Token('{'),
	rpg = new Token('}'),
	plus = new Token('+'),
	minus = new Token('-'),
	mult = new Token('*'),
	div = new Token('/'),
	semicolon = new Token(';'),
	comma = new Token(','), 
	assign = new Word(Tag.ASSIGN, "assign"),
	to = new Word(Tag.TO, "to"),
	iftok = new Word(Tag.IF, "if"),
	elsetok = new Word(Tag.ELSE, "else"),
	dotok = new Word(Tag.DO, "do"),
	fortok = new Word(Tag.FOR, "for"),
	begin = new Word(Tag.BEGIN, "begin"),
	end = new Word(Tag.END, "end"),
	print = new Word(Tag.PRINT, "print"),
	read = new Word(Tag.READ, "read"),
	init = new Word(Tag.INIT, ":="),
	or = new Word(Tag.OR, "||"),
	and = new Word(Tag.AND, "&&"),
	lt = new Word(Tag.RELOP, "<"),
	gt = new Word(Tag.RELOP, ">"),
	eq = new Word(Tag.RELOP, "=="),
	le = new Word(Tag.RELOP, "<="),
	ne = new Word(Tag.RELOP, "<>"),
	ge = new Word(Tag.RELOP, ">="); 
}