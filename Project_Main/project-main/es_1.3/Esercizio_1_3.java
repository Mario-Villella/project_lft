import java.lang.Character;

public class Esercizio_1_3{
    public static boolean scan(String s){
        int state = 0;
        int index = 0;
        while(state >= 0 && index < s.length()){
            char ch = s.charAt(index++);
            switch(state){
                case 0:
                    if(Character.isDigit(ch) && ch % 2 == 0)
                        state = 1;
                    else if(Character.isDigit(ch) && ch % 2 != 0)
                        state = 2;
                    else if(Character.isLetter(ch)) 
                        state = 4;
                    else
                        state = -1;
                    break;
                case 1:                                                
                    if(Character.isDigit(ch) && ch % 2 == 0)
                        state = 1;
                    else if(Character.isDigit(ch) && ch % 2 != 0)
                        state = 2;
                    else if(ch >= 'A' && ch <= 'K')
                        state = 3;
                    else if(ch >= 'L' && ch <= 'Z')
                        state = 4;
                    else
                        state = -1;
                    break;
                case 2:
                    if(Character.isDigit(ch) && ch % 2 != 0)
                        state = 2;
                    else if(Character.isDigit(ch) && ch % 2 == 0)
                        state = 1;
                    else if(ch >= 'A' && ch <= 'K')
                        state = 4;
                    else if(ch >= 'L' && ch <= 'Z')
                        state = 3;
                    else
                        state = -1;
                    break;
                case 3: 
                    if( Character.isLowerCase(ch))
                        state = 3;
                    else if(Character.isDigit(ch))
                        state = 4;
                    else 
                        state = -1;
                    break;
                case 4:
                    if( Character.isLowerCase(ch) || Character.isDigit(ch))
                        state = 4;
                    else
                        state = -1;
            }
        }
        return state == 3; 
    }

    public static void main(String[]args){
        System.out.println(" stringhe accettate :");

        System.out.println(scan("123446Bianchi") ? "ok" : "nope");
        System.out.println(scan("644321Rossi") ? "ok" : "nope");
        System.out.println(scan("2Bianchi") ? "ok" : "nope");
        System.out.println(scan("122B") ? "ok" : "nope");
        System.out.println(scan("43273789Pippo") ? "ok" : "nope");
        System.out.println(scan("234324232Alberto") ? "ok" : "nope");
      
        System.out.println(" string non accettate:");

        System.out.println(scan("644321Bianchi") ? "ok" : "nope");
        System.out.println(scan("644321BiAnchi") ? "ok" : "nope");
        System.out.println(scan("234324232alberto") ? "ok" : "nope");
        System.out.println(scan("123446Rossi") ? "ok" : "nope");
        System.out.println(scan("644322") ? "ok" : "nope");
        System.out.println(scan("Rossi") ? "ok" : "nope");
        System.out.println(scan("1277889Abete") ? "ok" : "nope");
        System.out.println(scan("") ? "ok" : "nope");
        System.out.println(scan("%&//(") ? "ok" : "nope");
    }
}