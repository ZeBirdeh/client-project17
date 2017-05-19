import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



import javax.swing.JFrame;

public class DataParse {
	private int studentNum;
	private ArrayList<String> students;
	private JFrame mainFrame;
	private JFrame parentFrame;
	
	public DataParse(File dataFile) {
		studentNum = 0;
		students = new ArrayList<String>();
		Scanner a = null;
		try {
			a = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while (a.hasNextLine()){
		//	System.out.println(a.nextLine());
		//}
	}
	
	public String formatName(String name, String house, boolean gender){
		//gender is true if male
		String outName = "";
		outName += (char)(33+studentNum);
		students.add(name);
		outName += 'A';
		outName += gender ? 'M' : 'F';
		outName += house.charAt(0);
		return outName;
	}
	
	public void setParent(JFrame par){
		parentFrame = par;
	}
	
	public static void main(String[] args){
		File file = new File("H:\\Li_Excel.xlsx");
		DataParse parser = new DataParse(file);
	}

}
