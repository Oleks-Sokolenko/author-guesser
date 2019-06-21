import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;

public class WordCounter{
	private FrequencyHashTable EAPtable;
	private FrequencyHashTable MWStable;
	private FrequencyHashTable HPLtable;

	/**
	 * Create FrequencyHashTable's
	 * Collect words into tables
	 * Test the tables
	 */
	WordCounter(){
		EAPtable = new FrequencyHashTable();
		MWStable = new FrequencyHashTable();
		HPLtable = new FrequencyHashTable();


		count(EAPtable, record -> record.getAuthor()
		                                .equals("EAP"));
		count(MWStable, record -> record.getAuthor()
		                                .equals("MWS"));
		count(HPLtable, record -> record.getAuthor()
		                                .equals("HPL"));

		int correct = test();
		System.out.println("Got " + correct + "/100" + " or " + correct / 100.0 + "%");
	}

	public static void main(String[] args){
		WordCounter c = new WordCounter();
	}

	/**
	 * Adds words to FrequencyHashTable
	 * @param table to add words to
	 * @param pred to check which author word belongs to
	 */
	private void count(FrequencyHashTable table, Predicate<Record> pred){
		try{
			Files.lines(Paths.get("train.csv"))
			     .map(Record::new)
			     .filter(pred)
			     .map(Record::getText)
			     .map(str -> str.split(" "))
			     .flatMap(Arrays::stream)
			     .forEach(table::increment);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Checks which author sentence belongs to
	 * @return number of times author was guessed correctly
	 */
	private int test(){
		int countCorrect = 0;
		File file = new File("test.csv");
		try(Scanner scan = new Scanner(file)){
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				Record record = new Record(line);
				double EAPwordCount = 1;
				double MWSwordCount = 1;
				double HPLwordCount = 1;
				for(String word : record.getText()
				                        .split(" ")){
					EAPwordCount *= EAPtable.getFrequency(word);
					MWSwordCount *= MWStable.getFrequency(word);
					HPLwordCount *= HPLtable.getFrequency(word);
				}
				if(Double.compare(EAPwordCount, MWSwordCount) > 0
						&& Double.compare(EAPwordCount, HPLwordCount) > 0
						&& record.getAuthor()
						         .equals("EAP")){
					countCorrect++;
				}else if(Double.compare(MWSwordCount, EAPwordCount) > 0
						&& Double.compare(MWSwordCount, HPLwordCount) > 0
						&& record.getAuthor()
						         .equals("MWS")){
					countCorrect++;
				}else if(Double.compare(HPLwordCount, MWSwordCount) > 0
						&& Double.compare(HPLwordCount, EAPwordCount) > 0
						&& record.getAuthor()
						         .equals("HPL")){
					countCorrect++;
				}
			}
			return countCorrect;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return -1;
	}

}
