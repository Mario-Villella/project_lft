public class Esercizio_1_5{
	public static boolean scan(String s){
        int state = 0;
        int index = 0;
        while(state >= 0 && index < s.length()){
            char ch = s.charAt(index++);
            switch(state){
                case 0:
                    if(ch == '/')
                        state = 1;
                    else if(ch == 'a' || ch == '*')
                        state = 4;
                    else 
                        state = -1;
                    break;
                case 1:
                    if(ch == '*')
                        state = 2;
                    else if(ch == 'a' || ch == '/')
                        state = 4;
                    else 
                        state = -1;
                    break;
                case 2:
                    if(ch == '*')
                        state = 3;
                    else if(ch == 'a' || ch == '/')
                        state = 2;
                    else 
                        state = -1;
                    break;
                case 3:
                    if(ch == '*')
                        state = 3;
                    else if(ch == 'a')
                        state = 2;
                    else if(ch == '/')
                        state = 0;
                    else 
                        state = -1;
                    break;
                case 4:
                    if(ch == '*' || ch == '/' || ch == 'a')
                        state = 4;
                    else 
                        state = -1;
                    break;
            }
        }
        return state == 0;
	}
	public static void main (String[]args){
	   System.out.println("Strings accepted:");

       System.out.println(scan("/****/")? "ok" : "nope");   
       System.out.println(scan("/*a*a*/")? "ok" : "nope");
       System.out.println(scan("/*a/**/")? "ok" : "nope");
       System.out.println(scan("/**a///a/a**/")? "ok" : "nope");
       System.out.println(scan("/**/")? "ok" : "nope");
       System.out.println(scan("/*/*/")? "ok" : "nope");

       System.out.println("Strings not accepted");

       System.out.println(scan("/*/")? "ok" : "nope");
       System.out.println(scan("/**/***/")? "ok" : "nope");
       System.out.println(scan("aa/**/")? "ok" : "nope");
       System.out.println(scan("////**/")? "ok" : "nope");
       System.out.println(scan("/******//////")? "ok" : "nope");
       System.out.println(scan("/")? "ok" : "nope");   	
	}
}