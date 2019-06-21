import java.util.Scanner;

public class Record{

	private String mText;
	private String mAuthor;
	private String mId;

	public Record(String rawCSVLine){
		String[] splt = rawCSVLine.split("\",\"");
		String id = splt[0].replaceAll("\"", "");
		String text = splt[1].replaceAll("\"", "");
		String author = splt[2].replaceAll("\"", "");

		mId = id;

		StringBuilder sb = new StringBuilder();

		Scanner wordScan = new Scanner(splt[1]);
		while(wordScan.hasNext()){

			String word = wordScan.next();
			word = word.replaceAll("[^a-zA-Z0-9]", "");
			word = word.toLowerCase();
			sb.append(word + " ");
		}

		mText = sb.toString();
		mAuthor = author;

	}

	public String getId(){ return mId;}

	public String getText(){ return mText;}

	public String getAuthor(){ return mAuthor;}


	public String toString(){
		return mId + "\n" + mText + "\n" + mAuthor;
	}
}
