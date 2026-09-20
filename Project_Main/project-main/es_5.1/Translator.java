import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    int next_for;
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;
    int operandType=0;
    int operandCount=0;
    int num_value;
    public Translator(Lexer l, BufferedReader br) {
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
        } else {
            error("syntax error");
        }
    }

    public void prog() {
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                int lnext_prog = code.newLabel();
                statlist(lnext_prog);
                code.emitLabel(lnext_prog);
                match(Tag.EOF);
                try {
                    code.toJasmin();
                } catch (java.io.IOException e) {
                    System.out.println("IO error\n");
                }
                break;
            default:
                error("prog syntax error");
                break;
        }
    }

    private void statlist(int lnext) {
        switch(look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                int lnext_statlist = code.newLabel();
                stat(lnext_statlist);
                lnext_statlist++;
                statlistp(lnext);
                //if (lnext_statlist <= 2)
                    //code.emit(OpCode.GOto, lnext);
                break;
            case '}':
                break;
            default:
                error("statlist syntax error");
                break;
        }
    }

    private void statlistp(int lnext) {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                int lnext_statlist = code.newLabel();
                stat(lnext_statlist);
                lnext_statlist++;
                statlistp(lnext);
                if (lnext_statlist <= 2)
                    code.emit(OpCode.GOto, lnext);
                break;
            case ';':
                match(';');
                int lnext_statlistp = code.newLabel();
                statlist(lnext_statlistp);
                lnext_statlistp++;
                statlistp(lnext);
                break;
            case Tag.EOF:
            case '}':
                break;
            default:
                error("statlistp syntax error");
                break;
        }
    }

    public void stat(int lnext) {
        switch(look.tag) {
            case Tag.ASSIGN:

                match(Tag.ASSIGN);
                assignlist(lnext);
                lnext++;
                break;
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                IdList(1);
                match(')');
                break;
            case Tag.READ:

                match(Tag.READ);
                match('(');
                IdList(0);
                match(')');
                break;

            case Tag.FOR:
                match(Tag.FOR);
                match('(');
                statfirst(lnext);

                break;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                int lifTrue = code.newLabel();
                int lifFalse = code.newLabel();
                int lifFalse2 = code.newLabel();
                bexpr(lifTrue, lifFalse);
                match(')');
                code.emitLabel(lifTrue);
                stat(lifTrue);
                code.emit(OpCode.GOto,lifFalse2);
                code.emitLabel(lifFalse);
                statsecond(lifFalse);

                code.emit(OpCode.GOto,lifFalse2);
                code.emitLabel(lifFalse2);

                break;
            case '{':
                match('{');
                statlist(lnext);
                match('}');

                break;
            default:
                error("stat syntax error");
                break;
        }
    }
    private void statfirst(int lnext) {
        switch (look.tag) {
            case Tag.ID:

                int lfornext=code.newLabel();
                int lforTrue = code.newLabel();
                int lforFalse = code.newLabel();
                code.emitLabel(lnext);
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr == -1) {
                    st.insert(((Word)look).lexeme, count);
                    id_addr = st.lookupAddress(((Word)look).lexeme);
                    id_addr=count;
                    count++;

                }
                match(Tag.ID);
                match(Tag.INIT);
                expr();
                code.emit(OpCode.istore, id_addr);

                match(';');
                code.emitLabel(lfornext);
                bexpr(lforTrue, lforFalse);
                code.emitLabel(lforTrue);
                match(')');
                match(Tag.DO);

                stat(lforFalse);

                code.emit(OpCode.GOto,lfornext);
                code.emitLabel(lforFalse);
                break;
            case Tag.RELOP:
                int lfornext1=code.newLabel();
                int ltrue = code.newLabel();
                int lfalse = code.newLabel();
                code.emitLabel(lfornext1);
                bexpr(ltrue, lfalse);
                code.emitLabel(ltrue);
                match(')');
                match(Tag.DO);
                stat(lfalse);
                code.emit(OpCode.GOto,lfornext1);
                code.emitLabel(lfalse);
                break;
            default:
                error("statfirst syntax error");
                break;
        }
    }
    private void statsecond(int lnext) {
        switch (look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat(lnext);
                match(Tag.END);
                break;
            case Tag.END:
                match(Tag.END);
                break;
            default:
                error("statsecond syntax error");
                break;
        }
    }
    private void assignlist(int lnext) {
        switch(look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist();
                match(']');
                assignlistp(lnext);
                break;
            default:
                error("assignlist syntax error");
                break;
        }
    }

    private void assignlistp(int lnext) {
        switch(look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist();
                match(']');
                assignlistp(lnext);
                break;
            case ';':
            case Tag.EOF:
            case '}':
            case Tag.ELSE:
            case Tag.END:
                break;
            default:
                error("assignlistp syntax error");
                break;
        }
    }

    private void idlist() {
        switch(look.tag) {
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr == -1) {
                    st.insert(((Word)look).lexeme, count);
                    id_addr = st.lookupAddress(((Word)look).lexeme);
                    count++;
                }
                match(Tag.ID);// Emit ldc before istore
                code.emit(OpCode.istore, id_addr);
                idlistp();
                break;
            default:
                error("idlist syntax error");
                break;
        }
    }

    private void idlistp() {
        switch(look.tag) {
            case ',':
                match(',');
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr == -1) {
                    st.insert(((Word)look).lexeme, count);
                    id_addr = count;
                    count++;
                }
                match(Tag.ID);
                code.emit(OpCode.ldc, num_value); // Emit ldc before istore
                code.emit(OpCode.istore, id_addr);
                idlistp();
                break;
            case ')':
            case ']':
                break;
            default:
                error("idlistp syntax error");
                break;
        }
    }

    private void IdList(int caseType) {
        switch (caseType) {
            case 0: // read
                switch (look.tag) {
                    case Tag.ID:
                        int id_addr = st.lookupAddress(((Word)look).lexeme);
                        if (id_addr == -1) {
                            st.insert(((Word)look).lexeme, count);
                            id_addr = count;
                            count++;
                        }
                        match(Tag.ID);
                        code.emit(OpCode.invokestatic, 0);
                        code.emit(OpCode.istore, id_addr);
                        IdListp(0);
                        break;
                    default:
                        error("readIdList syntax error");
                        break;
                }
                break;
            case 1: // print
                        expr();
                        code.emit(OpCode.invokestatic, 1);
                        IdListp(1);
                        break;
            default:
                error("IdList syntax error");
                break;
        }
    }

    private void IdListp(int caseType) {
        switch (caseType) {
            case 0: // read
                switch (look.tag) {
                    case ',':
                        match(',');
                        int id_addr = st.lookupAddress(((Word)look).lexeme);
                        if (id_addr == -1) {
                            st.insert(((Word)look).lexeme, count);
                            id_addr = count;
                            count++;
                        }
                        match(Tag.ID);
                        code.emit(OpCode.invokestatic, 0);
                        code.emit(OpCode.istore, id_addr);
                        IdListp(0);
                        break;
                    case ')':
                    case ']':
                        break;
                    default:
                        error("readIdListp syntax error");
                        break;
                }
                break;
            case 1: // print
                switch (look.tag) {
                    case ',':
                        match(',');
                        expr();
                        code.emit(OpCode.invokestatic, 1);
                        IdListp(1);
                        break;
                    case ')':
                    case ']':
                        break;
                    default:
                        error("printIdListp syntax error");
                        break;
                }
                break;
            default:
                error("IdListp syntax error");
                break;
        }
    }



    private void bexpr(int ltrue, int lfalse) {
        if (look.tag == Tag.RELOP) {

            String word = ((Word)look).lexeme;
            match(Tag.RELOP);
            expr();
            expr();

            switch (word) {
                case "<":
                    code.emit(OpCode.if_icmplt, ltrue);
                    break;
                case ">":
                    code.emit(OpCode.if_icmpgt, ltrue);
                    break;
                case "<=":
                    code.emit(OpCode.if_icmple, ltrue);
                    break;
                case ">=":
                    code.emit(OpCode.if_icmpge, ltrue);
                    break;
                case "<>":
                    code.emit(OpCode.if_icmpne, ltrue);
                    break;
                case "==":
                    code.emit(OpCode.if_icmpeq, ltrue);
                    break;
                default:
                    error("error bexpr: " + look);
                    break;
            }
            code.emit(OpCode.GOto, lfalse);
        } else {
            error("bexpr syntax error");
        }
    }

    private void expr() {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(0);
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '*':
                match('*');
                match('(');
                exprlist(2);
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
            case Tag.NUM:
                num_value = Integer.valueOf(((Word)look).lexeme);
                match(Tag.NUM);
                code.emit(OpCode.ldc,num_value) ;
                break;
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr == -1) {
                    st.insert(((Word)look).lexeme, count);
                    id_addr = st.lookupAddress(((Word)look).lexeme);
                }
                match(Tag.ID);
                code.emit(OpCode.iload, id_addr);
                break;
            default:
                error("expr syntax error");
                break;
        }
    }

    private void exprlist(int operandType) {
        switch(look.tag){
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistp(operandType);
                break;
            default:
                error("exprlist syntax error");
                break;
        }
    }
    private void exprlistp(int operandType) {
        switch(look.tag){
            case ',':
                match(',');
                expr();
                switch(operandType){
                    case 0:
                        code.emit(OpCode.iadd);
                        break;
                    case 1:
                        code.emit(OpCode.isub);
                        break;
                    case 2:
                        code.emit(OpCode.imul);
                        break;
                }
                exprlistp(operandType);
                break;
            case ')':
            case ']':
                break;
            default:
                error("exprlistp syntax error");
                break;
        }
    }


    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "input1.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
