package gameBullsandCows;
import java.util.Scanner;

public class game {
	private static String guessNumber = "";
	private static Scanner scanner;
	private static void printWelcomeAndRules() {
		System.out.println("Hi, today we're going to play a game of Bulls and Cows. The computer guesses a four-digit number with non-repeating digits, and you have to guess it. \r\n" + 
				"Each turn you enter a number and the computer tells you how many cows and how many bulls are in that number.  \r\n" + 
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
		for(int i=0;i<4;i++) {
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
	

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		printWelcomeAndRules();
		String guessNumber=createRandomNumber();
		int cows=0;
		int bulls=0;
		int amountTry = 1;
		//System.out.println("Guess number is " + guessNumber);
		while(bulls!=4) {
			System.out.println("-Try number "+amountTry+"-");
			String userNumber = scanner.nextLine();
			cows=amountCows(guessNumber, userNumber);
			bulls=amountBulls(guessNumber, userNumber);
			System.out.println("In Number - "+userNumber);
			System.out.println("cows  :"+cows);
			System.out.println("bulls :"+bulls);
			amountTry+=1;
			
		}
		if(bulls==4) System.out.println("Victory!!!");

	}
}
 