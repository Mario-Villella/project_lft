import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            case '(':
                peek = ' ';
                return Token.lpt;    
            case ')':
                peek = ' ';
                return Token.rpt;
            case '[':
                peek = ' ';
                return Token.lpq;
            case ']':
                peek = ' ';
                return Token.rpq;  
            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;
            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;    
            case '*':
                peek = ' ';
                return Token.mult;    
            case '/':
                readch(br);
                if(peek == '*'){
                    boolean flag = false;
                    readch(br);
                    while((!flag || peek != '/') && (peek != (char)-1)){
                        flag = peek == '*';
                        readch(br);
                    }
                    if(peek == (char)-1){
                        System.err.println("Erroneous comment not closed ");
                        return null; 
                    }
                }
                else if(peek == '/'){
                    while(peek != '\n' && peek != (char)-1){
                        readch(br);
                    }
                }
                else{
                    peek = ' ';
                    return Token.div;    
                }
                readch(br);
                return lexical_scan(br);
            case ';':
                peek = ' ';
                return Token.semicolon;    
            case ',':
                peek = ' ';
                return Token.comma;
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
            case ':':
                readch(br);
                if(peek == '='){
                    peek = ' ';

                    readch(br);
                    return Word.init;
                }
                else{
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
            case '<':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.le;
                }
                else if(peek == '>'){
                    peek = ' ';
                    return Word.ne;
                }
                else{
                    peek = ' ';
                    return Word.lt;
                }
            case '>':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.ge;
                }
                else{
                    peek = ' ';
                    return Word.gt;
                }
            case '=':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.eq;
                }
                else{
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }                
            case (char)-1:
                return new Token(Tag.EOF);
            default:
            //case key word and identifier    
                String word_in = "";
                boolean flag = false;
                if (Character.isLetter(peek) || peek == '_') {
                    while (peek == '_' || Character.isLetter(peek) || Character.isDigit(peek)) {
                        word_in += peek;
                        flag = peek != '_'; 
                        readch(br);
                    }
                    if(!flag){
                        System.err.println("Erroneous all caracters equal to "
                                + " _ : "  + word_in);
                            return null;
                    }
                    switch(word_in){
                        case "assign":
                            return Word.assign;

                        case "to":
                            return Word.to;
                        case "if":
                            return Word.iftok;
                        case "else":
                            return Word.elsetok;
                        case "do":
                            return Word.dotok;
                        case "for":
                            return Word.fortok;
                        case "begin":
                            return Word.begin;
                        case "end":
                            return Word.end;
                        case "print":
                            return Word.print;
                        case "read":
                            return Word.read;
                        case "init":
                            return Word.init;
                        default: 
                                return new Identifier(Tag.ID, word_in);
                    }
                            
            //case number    
                } else if (Character.isDigit(peek) ) {
                    String number_in = ""; 
                    while (Character.isDigit(peek) ) {
                        number_in = number_in + peek;
                        readch(br);
                    }
                    if(peek != '_' && !Character.isLetter(peek) ){
                       return new NumberTok(Tag.NUM, number_in);
                    }  
                } 
                    System.err.println("Erroneous character: " + peek );
                    return null;
         }
    }
}