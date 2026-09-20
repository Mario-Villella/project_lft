import java.lang.Character;
public class Esercizio_1_2{
    public static boolean scan(String s){
        int state = 0;
        int index = 0;
        while(state >= 0 && index < s.length()){
            char ch = s.charAt(index++);
            switch(state){
                case 0: 
                    if(Character.isLetter(ch))
                        state = 3;
                    else if(ch == '_')
                        state = 2;
                    else if(Character.isDigit(ch))
                        state = 1;
                    else
                        state = -1;
                    break;
                case 1:
                    if(Character.isLetter(ch) || Character.isDigit(ch) || (ch == '_'))
                        state = 1;
                    else 
                        state = -1;
                    break;
                case 2:
                    if(ch == '_')
                        state = 2;
                    else if(Character.isLetter(ch) || Character.isDigit(ch))
                        state = 3;
                    else
                        state = -1;
                    break;
                case 3:
                    if(Character.isLetter(ch) || Character.isDigit(ch) || (ch == '_'))
                        state = 3;
                    else 
                        state = -1;
                    break;
            }
        }
        return state == 3;
    }

    public static void main(String[]args){
        System.out.println(" stringhe accettate :");

        System.out.println(scan("x") ? "ok" : "nope");
        System.out.println(scan("flag1") ? "ok" : "nope");
        System.out.println(scan("x2y2") ? "ok" : "nope");
        System.out.println(scan("x_1") ? "ok" : "nope");
        System.out.println(scan("lft_lab") ? "ok" : "nope");
        System.out.println(scan("_temp") ? "ok" : "nope");
        System.out.println(scan("x_1_y_2") ? "ok" : "nope");
        System.out.println(scan("x___") ? "ok" : "nope");
        System.out.println(scan("__5") ? "ok" : "nope");

        System.out.println(" string non accettate:");

        System.out.println(scan("5") ? "ok" : "nope");
        System.out.println(scan("221B") ? "ok" : "nope");
        System.out.println(scan("123") ? "ok" : "nope");
        System.out.println(scan("9_to_5") ? "ok" : "nope");
        System.out.println(scan("___") ? "ok" : "nope");
        System.out.println(scan("") ? "ok" : "nope");
        System.out.println(scan("%&//(") ? "ok" : "nope");
        System.out.println(scan("ab££___22") ? "ok" : "nope");
    }
}