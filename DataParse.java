import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



import javax.swing.JFrame;

import org.apache.poi.xssf.usermodel.*;

public class DataParse {
	private int studentNum;
	private String[] students;
	private HashMap<String, String> formatStudents;
	private JFrame mainFrame;
	private JFrame parentFrame;
	
	public DataParse(File dataFile) {
		studentNum = 0;
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
		outName += (char)(65+studentNum%26);
		outName += (char)(97+studentNum/26);
		outName += gender ? 'M' : 'F';
		outName += house.charAt(0);
		formatStudents.put(outName, name);
		return outName;
	}
	
	public void setParent(JFrame par){
		parentFrame = par;
	}
	
	public static void main(String[] args){
		File file = new File("H:\\Li_Excel.xlsx");
	}

}
