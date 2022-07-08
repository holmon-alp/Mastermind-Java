import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
 
 
 public class Mastermind {
 
     public static String secretCode;
     public static String inputCode;
     public static int rounds;
 
     public static  final String RESET = "\u001B[0m";
     public static  final String RED = "\u001B[31m";
     public static  final String GREEN = "\u001B[32m";
     public static  final String YELLOW = "\u001B[33m";
     public static  final String BLUE = "\u001B[34m";
     public static  final String PURPLE = "\u001B[35m";
     public static  final String CYAN = "\u001B[36m";
     public static  final String WHITE = "\u001B[37m";
 
     static String getSecretCode() { // Generate random code 
         String code = "";
         while(code.length() < 4) {
             int rand = (int)(Math.random() * 7) + 1;
             if(!(code.contains(""+rand))) {
                 code += rand;
             }
         }
         // System.out.println(code);
         return code;
     }
     
     static void missPlaced(String code, String input) { // count and print miss Placed pieces
         int mp = 0;
         for(int i=0; i < code.length(); i++) {
             if(code.contains("" + input.charAt(i)) && code.charAt(i) != input.charAt(i))
                 mp++;
         }
         System.out.printf(YELLOW + " 🤞 Misplaced pieces   № %d - 🧐\n" + RESET, mp);
     }
 
     static void wellPlaced(String code, String input) { // count and print well Placed pieces
         int wp = 0;
         for(int i=0; i < code.length(); i++) {
             if(code.charAt(i) == input.charAt(i))
                     wp++;
         }
         System.out.printf(GREEN + " 👍 Well placed pieces № %d - OK\n" + RESET, wp);
     }
 
     static boolean isDuplicated(char arg[]) { // checking char dublicated in array;
         int count = 0;
         for(int i = 0; i <arg.length - 1; i++) {
             for(int j = i+1; j <arg.length; j++) {  
                 if(arg[i] == arg[j]) {  
                     count++;
                     // arg[j] = '0';  //Set arr[j] to 0 to avoid printing visited character  
                 }
             }  
         }
         return count > 0;
     }
 
     static boolean wrongInput(String text) { // checking wrong  input 
         int len = text.length();
         String block = "01234567";
         if(len != 4) 
             return true;
         char arr[] = text.toCharArray();  
         for(int i=0; i < arr.length; i++) {
 	    char c = arr[i];	
             if(!(block.contains(""+c)) || isDuplicated(arr)) {
                 return true;
 	        }
         }
 	return false;
     }
 
     public static void setOptions(String[] args) {
         if (args.length > 1 ){
             ArrayList<String> option_list = new ArrayList<String>();
             option_list.addAll(Arrays.asList(args));
             Iterator<String> itr = null;
             itr = option_list.listIterator();    
             while (itr.hasNext()) {      // rotation across values;
                 String option = itr.next();
                 if (option.equals("-t")) {
                     try {                // checking  number of rounds is valid ?
                         rounds = Integer.parseInt(itr.next());
                         if(rounds < 0 && -30 < rounds) {
                             rounds *= -1;
                             System.out.printf(YELLOW + " 🙄  🙄  Rounds number set: %d (not -%d) ✖︎ ✖︎ ✖︎\n",rounds, rounds);
                         } else if(rounds > 30 || rounds < 1){
                                 System.out.println(RED + " ✖︎ ✖︎ ✖︎ Rounds cannot be more then 30 or less then 1 (30>rounds>1)");
                                 rounds = 10;
                         }
                     } catch (Exception e) {
                         System.out.println(RED+" ✖︎ ✖︎ ✖︎ You should type number of rounds! (-t) ✖︎ ✖︎ ✖︎\n"+RESET);
                     };
                 }
                 
                 if (option.equals("-c")) {
                     String code = itr.next();
                     if(wrongInput(code)) {
                         System.out.printf(RED+" ✖︎ ✖︎ ✖︎ Error in setting secret code: ( -c %s ) ✖︎ ✖︎ ✖︎\n" + WHITE +
                             " Secret code set automatic by 'generator' ✓ ✓ ✓\n", code);
                         secretCode = getSecretCode();
                     } else {
                         secretCode = code;
                     }
                 }
             }
         }
         if (secretCode == null) {     // if secret code is empty set generator
             secretCode = getSecretCode();
         }
         if (rounds == 0) {              // if rounds is 0 set default 10
             rounds = 10;
         }
     }
 
     public static void run() {
         int round = 1;
         System.out.println(BLUE + "\n 🤑 🤑 🤑 Will you find the secret code? 🤑 🤑 🤑");
         Scanner input = new Scanner(System.in);
         while (round <= rounds) {
             System.out.printf(PURPLE + "  -----\n  Round %d/%d\n 👉 > ", round, rounds);
             inputCode = input.nextLine();
             if(wrongInput(inputCode)) {
                 System.out.println(RED + "*** 🤮 🤮 Wrong input ! 🤮 🤮 ***");
                 continue;
             } else if (secretCode.equals(inputCode)) {
                 System.out.println(BLUE + " 👏  👏  👏  Congrats! You did it!  👏  👏  👏");
                 break;
             } else {
                 wellPlaced(secretCode, inputCode);
                 missPlaced(secretCode, inputCode);
             }
             if (round == rounds)
                 System.out.printf( RED+"☠️  ☠️  ☠️    Game Over   ☠️  ☠️  ☠️\n\n"+CYAN+"\tSecret code: %s\n", secretCode);
             round++;
             
         }
         
     }
     
 // Start  game
     public static void main(String[] args) {
         setOptions(args);
         run();
     }
 }
