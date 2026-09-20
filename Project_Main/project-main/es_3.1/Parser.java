import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error");
    }
    public void start() {
        switch(look.tag){
            case '+':
            case '-':
            case '(':
            case Tag.NUM:
                expr();
                match(Tag.EOF);
                break;
            default:
                error("start syntax error");
                break;
        }
    }
    private void expr() {
        switch(look.tag){
            case '(':
            case Tag.NUM:
                term();
                exprp();
                break;
            default:
                error("expr syntax error");
                break;
        }
    }
    private void exprp() {
        switch (look.tag) {
            case '+':
                match('+');
                term();
                exprp();
                break;
            case '-':
                match('-');
                term();
                exprp();
                break;
            case ')':
            case Tag.EOF:
                break;
            default:
                error("exprp syntax error");
                break;
        }
    }
    private void term() {
        switch (look.tag) {
            case '(':
            case Tag.NUM:
                fact();
                termp();   
                break;
            default:
                error("term syntax error");
                break;
        }
    }
    private void termp() {
        switch(look.tag){
            case '*':
                match('*');
                fact();
                termp();
                break;
            case '/':
                match('/');
                fact();
                termp();
                break;
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                break;
            default:
                error("termp syntax error");
                break;
        }
    }
    private void fact() {
        switch(look.tag){
            case '(':
                match('(');
                expr();
                match(')');
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break; 
            default:
                error("fact syntax error");
                break;
        }
    }
        
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "input.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}