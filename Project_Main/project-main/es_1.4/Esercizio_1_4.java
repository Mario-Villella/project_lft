import java.lang.Character;

public class Esercizio_1_4{
    public static boolean scan(String s){
        int state = 0;
        int index = 0;
        while(state >= 0 && index < s.length()){
            char ch = s.charAt(index++);
            switch(state){
                case 0:
                    if(Character.isDigit(ch))
                        state = 1;
                    else if(ch == '.')
                        state = 2;
                    else if(ch == '+' || ch == '-')
                        state = 10;
                    else if(ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
                case 1://
                    if(Character.isDigit(ch))
                        state = 1;
                    else if(ch == '.')
                        state = 2;
                    else if(ch == 'e')
                        state = 4;
                    else if(ch == '+' || ch == '-')
                        state = 13;               
                    else 
                        state = -1;
                    break;
                case 2:
                    if(Character.isDigit(ch))
                        state = 3;
                    else if(ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
                case 3://
                    if(Character.isDigit(ch))
                        state = 3;
                    else if(ch == '+' || ch == '-')
                        state = 9;
                    else if(ch == '.')
                        state = 13;
                    else if(ch == 'e')
                        state = 4;
                    else
                        state = -1;
                    break;
                case 4:
                    if(Character.isDigit(ch))
                        state = 5;
                    else if(ch == '+' || ch == '-')
                        state = 8;
                    else if(ch == 'e')
                        state = 13;
                    else if(ch == '.')
                        state = 11;
                    else
                        state = -1;
                    break;
                case 5://
                    if(Character.isDigit(ch))
                        state = 5;
                    else if(ch == '-' || ch == '+' || ch == 'e')
                        state = 13;
                    else if(ch == '.')
                        state = 6;
                    else 
                        state = -1;
                    break;
                case 6:
                    if(Character.isDigit(ch))
                        state = 12;
                    else if(ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13; 
                    else
                        state = -1;
                    break;
                case 7://
                    if(Character.isDigit(ch))
                        state = 7;
                    else if(ch == '.')
                        state = 11;
                    else if(ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else
                        state = -1;
                    break;
                case 8:
                    if(Character.isDigit(ch))
                        state = 7;
                    else if(ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else if(ch == '.')
                        state = 11;
                    else 
                        state = -1;
                    break;
                case 9://
                    if(Character.isDigit(ch))
                        state = 9;
                    else if(ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
                case 10:
                    if(Character.isDigit(ch))
                        state = 1;
                    else if(ch == '.')
                        state = 2;
                    else if(ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else
                        state = -1;
                    break;
                case 11:
                    if(Character.isDigit(ch))
                        state = 9;
                    else if(ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
                case 12://
                    if(Character.isDigit(ch))
                        state = 12;
                    else if(ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
                case 13:
                    if(Character.isDigit(ch) || ch == '.' || ch == '+' || ch == '-' || ch == 'e')
                        state = 13;
                    else 
                        state = -1;
                    break;
            } 
        }
        return state == 1 || state == 3 || state == 5 || state == 7 || state == 9 || state == 12;
    }
    public static void main(String[]args){

        System.out.println(" Strings accepted:");

        System.out.println(scan("123") ? "ok" : "nope");
        System.out.println(scan("123.5") ? "ok" : "nope");
        System.out.println(scan(".567") ? "ok" : "nope");
        System.out.println(scan("+7.5") ? "ok" : "nope");
        System.out.println(scan("-.7") ? "ok" : "nope");
        System.out.println(scan("67e10") ? "ok" : "nope");
        System.out.println(scan("1e-2") ? "ok" : "nope");
        System.out.println(scan("-.7e2") ? "ok" : "nope");
        System.out.println(scan("-2.2e-4.5") ? "ok" : "nope");
        System.out.println(scan("2.2e4.5") ? "ok" : "nope");
        System.out.println(scan("-4.7e7") ? "ok" : "nope");
        System.out.println(scan("1e-.1") ? "ok" : "nope"); 
        System.out.println(scan("1e.1") ? "ok" : "nope");

        System.out.println(" Strings not accepted:");

        
        
        System.out.println(scan(".") ? "ok" : "nope");
        System.out.println(scan("e3") ? "ok" : "nope");
        System.out.println(scan("123.") ? "ok" : "nope");
        System.out.println(scan("+e6") ? "ok" : "nope");
        System.out.println(scan("1.2.3") ? "ok" : "nope");
        System.out.println(scan("4e5e6") ? "ok" : "nope");
        System.out.println(scan("++3") ? "ok" : "nope");
        System.out.println(scan("") ? "ok" : "nope");
        System.out.println(scan("tftyfgvm") ? "ok" : "nope");
    } 
}