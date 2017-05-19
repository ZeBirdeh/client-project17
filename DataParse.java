import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

public class DataParse {
	private JFrame mainFrame;
	private JFrame parentFrame;
	
	public DataParse(File dataFile) {
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
	
	private void readFromExcel(){
		
	}
	
	
	public void setParent(JFrame par){
		parentFrame = par;
	}
	
	public static void main(String[] args){
		File file = new File("H:\\Li_Excel.xlsx");
		DataParse parser = new DataParse(file);
	}

}
