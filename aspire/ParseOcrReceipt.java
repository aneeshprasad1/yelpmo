import com.asprise.ocr.Ocr;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.Double;

/*
 *@author Varun Tolani
 */
/* Class for parsing the result text of ocr on a meal receipt. Call the static
 * method doRegex on your resulting OCR string. It will return a list of "item"
 * in the receipt*/
public class ParseOcrReceipt {
	private static final String endStrings = "(Sales Tax)|(SALES TAX)|(Tax)|(TOTAL)|(total)|(Thank you)|(THANK YOU)|(sub-total)|(Sub-Total)|(Sub-tot)|(Tot)|(tot)|(BALANCE)|(Balance)|(balance)";
	private static final String startStrings = "(server:?)|(Server:?)|(Host:?)|(host:?)|(Order:?)|(ORDER:?)|((G|g)uest(s)? ?(: )?\\d)|(Table)|(table)|(STORE)|(Store)";

	public static List<Item> doRegex(String results) {
		String temp = results;
		String choppedFront = chopFront(temp);
		if (choppedFront.equals("unreadable receipt")) {
			System.out.println("unreadable reciept");
			return null;
		}
		String choppedString = chopEnd(choppedFront);
		List<Item> items = parseItems(choppedString);
		return items;
	}

	/** This will replace all instances of "startStrings" with "CHOPHERE", then looping through
	 * the receipt chopping the string until all instances of "CHOPHERE" have been removed"*/
	private static String chopFront(String receipt) {
		String temp = receipt.replaceAll(ParseOcrReceipt.startStrings, "CHOPHERE");
		while (temp.indexOf("CHOPHERE") != -1){
			temp = temp.substring(temp.indexOf("CHOPHERE"));
			temp = temp.substring(temp.indexOf("\n"));
		}
		return temp;
	
	}

	/** If the receipt doesn't end with any of the "endStrings" then don't chop off the receipt.
	 * Otherwise Chop the receipt at the spot of the earliest "endString")*/
	private static String chopEnd(String receipt) {
		String replaced = receipt.replaceFirst(ParseOcrReceipt.endStrings, "endHere");
		if (replaced.equals(receipt)) {
			return replaced;
		}
		replaced = replaced.substring(0,replaced.indexOf("endHere"));
		return replaced;
	}

	/** Creates an ArrayList of "items" by parsing the OCR string. Splits OCR string into "item"
	 * segments by treating every newline as a new "item string", calling the createItem method
	 * on them to add the item to the master arraylist of "items" corresponding to a meal*/
	private static ArrayList<Item> parseItems(String receipt) {
		ArrayList<Item> items = new ArrayList();
		while(receipt.indexOf("\n") != -1){
			int newLine = receipt.indexOf("\n");
			if (newLine == 0) {
				receipt = receipt.substring(1, receipt.length());
				continue;
			}
			String tempItem = receipt.substring(0, newLine);
			receipt = receipt.substring(newLine + 1);
			createItem(tempItem, items);
		}
		return items;
	}

	/* Takes a "item" in String form (quantity, name, price). First runs a bunch of checks to see if 
	 * this "item" (represented as a one line string) is actually an "item" (aka has a price, etc.).
	 * If so runs helper methods to getNumItems, getName, and getPrice, finally constructing an "item"
	 * an adding it to the master arraylist*/
	private static void createItem(String tempItem, ArrayList<Item> items) {
		String temp = tempItem;
		boolean nonItem = false; // change to true if subtotal/total
		String skipFirstNum = tempItem.substring(1,tempItem.length());
		String saveSkipFirstNum = skipFirstNum;
		if (temp.replaceFirst("\\d+", "PRICE").equals(tempItem)||skipFirstNum.replaceFirst("\\d+", "PRICE").equals(saveSkipFirstNum)) {
			return;
		}
		if(temp.indexOf(" ") == -1) {
			return;
		}
		String subtotal = tempItem.replaceFirst("Sub-tot", "THISISSUBTOTAL");
		String total = tempItem.replaceFirst("(total)|(TOTAL)","THISISTOTAL");
		String balance = tempItem.replaceFirst("(balance)|(BALANCE)|(Ba([a-z]|[A-Z])+nce)","THISISBALANCE");
		if (! subtotal.equals(tempItem) || ! total.equals(tempItem) || ! total.equals(balance)) {
			nonItem = true;
		}
		
		if(!nonItem){
			boolean hasNumItems = checkIfNumbered(tempItem);
			int numItems = getNumItems(tempItem, hasNumItems);
			int numInString = Integer.toString(numItems).length();

			if (hasNumItems)
				tempItem = tempItem.substring(numInString + 1);

			String name = getItemName(tempItem);
			tempItem = tempItem.substring(tempItem.indexOf(name) + name.length());

			double price = getItemPrice(tempItem, numItems);

			for(int i = 0; i < numItems; i++){
				Item curItem = new Item (name, price);
				items.add(curItem);
			}
		}
	}

	private static boolean checkIfNumbered(String tempItem) {
		String firstLetter = tempItem.substring(0,1);
		String hasNumber = firstLetter.replaceFirst("\\d","HASNUMBER");
		if(hasNumber.equals(firstLetter)){
			return false;
		}
		return true;
	}

	private static int getNumItems(String tempItem, boolean hasNumbers) {
		// if this is a letter than there is no "num items". Return 1
		if (!hasNumbers) {
			return 1;
		}
		String num = tempItem.substring(0, tempItem.indexOf(" "));
		int numItems = Integer.parseInt(num);
		/** This is to deal with menu's that give menu number's etc. Fix this bug right now*/
		if (numItems > 5){
			return 1;
		}
		return numItems;

	}

	private static String getItemName(String tempItem) {
		String firstLetter = tempItem.substring(0,1);
		if(firstLetter.equals(" ")){
			tempItem = tempItem.substring(1);
		}
		String tempString = "";
		String patn = "([A-Z] ?|[a-z] ?)+";
		int i = 1;
		while(i < tempItem.length()) {
			tempString = tempItem.substring(0,i);
			Matcher mat = Pattern.compile(patn).matcher(tempString);
			if(! mat.matches()){
				break;
			}
			i++;
		}
		tempString = tempItem.substring(0, i-1);
		return tempString;
	}

	/** Method to compute Price of a "one line item string". Checks if there is a $
	 * sign in the string, and removes it if there is. Next Checks if there are leading
	 * spaces in the string, removes them if there are. Finally checks if the ocr dropped
	 * the period and left a space instad (i.e. 2 67 instead of 2.67). Fixes this and returns
	 * the ideal result, which is always price/numitems. */
	private static double getItemPrice(String tempItem, int numItems){
		int dollarSign = tempItem.indexOf("$");
		if (dollarSign != -1) {
			String money = tempItem.substring(dollarSign + 1);
			return Double.parseDouble(money) / numItems;
		}
		int space = tempItem.indexOf(" ");
		if (space == -1 || space == 0){ // there is no space "just the price" or its the first character
			return Double.parseDouble(tempItem) / numItems;
		}
		String firstNum = tempItem.substring(0,1);
		double price = Double.parseDouble(tempItem.substring(space + 1));

		// Fixes bug of ocr not putting period between dollar and cent amount (only if its a space instead)		
		if (!firstNum.equals(" ") && space > 0){
			tempItem = tempItem.substring(0, space) + "." + tempItem.substring(space + 1);
			price = Double.parseDouble(tempItem);
			return price / numItems;
		}
		return price / numItems;
	}

}