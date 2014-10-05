import com.asprise.ocr.Ocr;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.lang.Double;

public class TestOCR {
	private String results;
	private static final String endStrings = "(Sales Tax)|(SALES TAX)|(Tax)|(TOTAL)|(total)|(Thank you)|(THANK YOU)|(sub-total)|(Sub-Total)|(Sub-tot)|(Tot)|(tot)";

	public static void main(String[] args){
		TestOCR test = new TestOCR();
		test.readFile();
		test.doRegex();
	}

	public void readFile() {
		try{
			Ocr.setUp();
			Ocr ocr = new Ocr();
			ocr.startEngine("eng", Ocr.SPEED_FASTEST);
			String s = ocr.recognize(new File[] {new File("images/thai.png")},
				Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
			this.results = s;
			ocr.stopEngine();
		} catch (Exception e){
			System.exit(1);
		}
	}


	private void doRegex() {
		String temp = results;
		System.out.println(temp);
		String choppedFront = chopFront(temp);
		if (choppedFront.equals("unreadable receipt")) {
			System.out.println("unreadable reciept");
			return;
		}
		String choppedString = chopEnd(choppedFront);
		System.out.println(choppedString);
		List<Item> items = parseItems(choppedString);
		System.out.println(items);

		
	}

	private String chopFront(String receipt) {
		String temp = receipt.replaceFirst("(G|g)uest(s)? ?(: )?\\d\\n", "startHere");
		if(temp.equals(receipt)) {
			temp = receipt.replaceFirst("(server:?)|(Server:?)", "SERVER:");
			temp = temp.substring(temp.indexOf("SERVER:"));
			temp = temp.substring(temp.indexOf("\n"));
			return temp;
		}
		temp = temp.substring(temp.indexOf("startHere") + 9);
		return temp;
	}

	private String chopEnd(String receipt) {
		String replaced = receipt.replaceFirst(TestOCR.endStrings, "endHere");
		if (replaced.equals(receipt)) {
			return new String("Doesn't match thank you");
		}
		replaced = replaced.substring(0,replaced.indexOf("endHere"));
		return replaced;
	}

	private ArrayList<Item> parseItems(String receipt) {
		ArrayList<Item> items = new ArrayList();
		while(receipt.indexOf("\n") != -1){
			int newLine = receipt.indexOf("\n");
			String tempItem = receipt.substring(0, newLine);
			receipt = receipt.substring(newLine + 1);
			this.createItem(tempItem, items);
		}
		return items;
	}

	private void createItem(String tempItem, ArrayList<Item> items) {
		String temp = tempItem;
		boolean nonItem = false; // change to true if subtotal/total
		if (temp.replaceFirst("\\d+", "PRICE").equals(tempItem)) {
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
		
		if(! nonItem){
			boolean hasNumbers = checkIfNumbered(tempItem);
			int numItems = getNumItems(tempItem, hasNumbers);
			int numInString = Integer.toString(numItems).length();

			if (hasNumbers)
				tempItem = tempItem.substring(numInString + 1);
			// System.out.println(tempItem);

			String name = getItemName(tempItem);
			tempItem = tempItem.substring(tempItem.indexOf(name) + name.length());

			double price = getItemPrice(tempItem, numItems);

			for(int i = 0; i < numItems; i++){
				Item curItem = new Item (name, price);
				items.add(curItem);
			}
		}
	}

	private boolean checkIfNumbered(String tempItem) {
		String firstLetter = tempItem.substring(0,1);
		String hasNumber = firstLetter.replaceFirst("\\d","HASNUMBER");
		if(hasNumber.equals(firstLetter)){
			return false;
		}
		return true;
	}

	private int getNumItems(String tempItem, boolean hasNumbers) {
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

	private String getItemName(String tempItem) {
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

	private double getItemPrice(String tempItem, int numItems){
		int dollarSign = tempItem.indexOf("$");
		if (dollarSign != -1) {
			String money = tempItem.substring(dollarSign + 1);
			return Double.parseDouble(money);
		}
		int space = tempItem.indexOf(" ");
		if (space == -1 || space == 0){ // there is no space "just the price" or its the first character
			return Double.parseDouble(tempItem);
		}
		double price = Double.parseDouble(tempItem.substring(space + 1));
		return price / numItems;
	}

}