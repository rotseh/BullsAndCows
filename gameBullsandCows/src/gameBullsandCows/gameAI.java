package gameBullsandCows;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author db476
 *
 */
public class gameAI {
	private static String guessNumber = "";
	static Scanner scanner = new Scanner(System.in);
	private static void printWelcomeAndRules() {
		System.out.println("Hi, today we're going to play a game of Bulls and Cows. The computer guesses and you a four-digit number with non-repeating digits, and you have to guess it. \r\n" + 
				"Each turn you enter a number and the computer tells you how many cows and how many bulls are in that number.  \r\n" + 
				"Then the computer tries to guess your word, you write it the number of cows and bulls. No cheating! \r\n" +
				"Number of cows - how many digits are guessed without matching their positions in the secret number.\r\n" + 
				"Number of bulls - how many digits are guessed up to the position in the secret number. \r\n" + 
				"For example, the guessed number is 5234, and the entered number is 8243. \r\n" + 
				"The number of bulls - 1 ( the digit 2, which stands on the same position in both numbers).\r\n" + 
				"Number of cows - 2 (digits 4 and 3, which are in both numbers, but stand on different positions). \r\n" + 
				"\n-----Game started-----" + 
				"\r\n" );
		
		System.out.println();
	}
	private static String createRandomNumber() {
		int randomNumber = 1000 + ((int) (Math.random() * 10000) % 9000);
		guessNumber = Integer.toString(randomNumber);
		while (hasRepeatingDigits(guessNumber)) {
			createRandomNumber();
		}
		return guessNumber;
	}

	private static boolean hasRepeatingDigits(String number) {
		for (int i = 0; i < 4 - 1; i++) {
			for (int j = i + 1; j < number.length(); j++) {
				if (number.charAt(i) == number.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int amountBulls(String enterNumber, String guessNumber){
		int bulls=0;
		for(int i=0;i<enterNumber.length();i++) {
			if(enterNumber.charAt(i)==guessNumber.charAt(i)) bulls+=1;
		}
		return bulls;
	}
	public static int amountCows(String enterNumber, String guessNumber){
		int cows=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(enterNumber.charAt(i)==guessNumber.charAt(j)&&i!=j) cows+=1;
			}
		}
		return cows;
	}	
	public static Set<Integer> removeWithCows(int c, Set<Integer> intSet,String number) {
		Set<Integer> newSet = new HashSet<>();
		for (int element : intSet) {
		    if(amountCows(Integer.toString(element),number)==c) {newSet.add(element);} 		    
		}
		return newSet;
	}
	public static Set<Integer> removeWithBulls(int b, Set<Integer> intSet,String number){
		Set<Integer> newSet = new HashSet<>();
		for (int element : intSet) {
			if(amountBulls(Integer.toString(element),number)==b) {newSet.add(element);}
		}
		return newSet;
	}
	public static Set<Integer> removeWithCowsAndBulls(int c, int b, Set<Integer> intSet,String number){
		intSet=removeWithCows(c,intSet,number);
		intSet=removeWithBulls(b,intSet,number);
		return intSet;
	}
	public static Set<Integer> computerTry(Set<Integer> intSet){
		System.out.println("Computer try");
		Iterator<Integer> iterator = intSet.iterator();
		String element = Integer.toString(iterator.next());
		int bulls=0;
		int cows=0;
		System.out.println("I guess the number is "+element );
		System.out.println("How much bulls?" );
		bulls = Integer.parseInt(scanner.nextLine());
		if(bulls!=4) {
			System.out.println("And cows?" );
			cows = Integer.parseInt(scanner.nextLine());
			intSet=removeWithCowsAndBulls(cows,bulls,intSet,element);
		}
		else {
			System.out.println("The computer gave a number!");
			intSet.clear();
			intSet.add(0);
			return intSet;
		}
		return intSet;
	}
	public static void main(String[] args) {
		String computerNumber=createRandomNumber();
		printWelcomeAndRules();
		int amountTry = 0;
		Set<Integer> intSet = new HashSet<>();
		for(int i=1023;i<=9876;i++) { 
			if(!hasRepeatingDigits(Integer.toString(i))) intSet.add(i);
		}
		boolean j = true;
		int win=0;
		//System.out.println("Computer number - "+computerNumber);
		while(j) {
			amountTry++;
			System.out.println("Round - "+amountTry);
			System.out.println("Your try");
			System.out.println("What number did I wish for?");
			String guessN= scanner.nextLine();
			if(amountBulls(guessN,computerNumber)==4) {
				win=1;
				j=false;
				System.out.println("You gave a number!");
			}
			else {
				System.out.println("In number "+guessN+" amount of Bulls "+amountBulls(guessN,computerNumber));
				System.out.println("In number "+guessN+" amount of Cows  "+amountCows(guessN,computerNumber));
				System.out.println("");
				
			}
			if(intSet.size()==1 && win!=1) {
				if(win==1) win=4;
				else win=2;
				j=false;
			}
			intSet=computerTry(intSet);
			if(intSet.size()==1 && intSet.contains(0)) { 
				win+=2;
				j=false;
			}
			if(intSet.size()==0) {
				win=5;
				j=false;
			}
		}
		if(win==1) System.out.println("You won! Number was - "+computerNumber);
		if(win==2) System.out.println("Computer won!Number was - "+computerNumber);
		if(win==4) System.out.println("I think you made a mistake, or cheated with the cow and bull inputs. Number was "+computerNumber);
		if(win==3) System.out.println("Draw!Computer Number was "+computerNumber);
		

	}
}
 