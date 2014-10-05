import com.asprise.ocr.Ocr;
import java.io.File;
import java.util.List;
import java.lang.Double;

/*Test class for the ParseOcrReceipt. Run using the asprise ocr
 * api for java. This won't work for android, but it's just an
 * example of how to use the ParseOcrReceipt class*/
public class TestOcrParse{
	private static String result;

	public static void main(String[] args){
		TestOcrParse test = new TestOcrParse();
		test.readFile();
		List<Item> items = ParseOcrReceipt.doRegex(result);
		System.out.println(items);
		double sum = TestOcrParse.sumArray(items);
		System.out.println(Double.toString(sum));
	}

	private void readFile() {
		try{
			Ocr.setUp();
			Ocr ocr = new Ocr();
			ocr.startEngine("eng", Ocr.SPEED_FASTEST);
			String s = ocr.recognize(new File[] {new File("images/mcdonalds.jpg")},
				Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
			TestOcrParse.result = s;
			ocr.stopEngine();
		} catch (Exception e){
			System.exit(1);
		}
	}

	private static double sumArray(List<Item> items){
		double sum = 0;
		for (int i = 0; i < items.size(); i++) {
			sum += items.get(i).getPrice();
		}
		return sum;
	}
}