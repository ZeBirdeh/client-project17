import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;




import javax.swing.JFrame;
import javax.swing.JTextArea;

//import org.apache.poi.xssf.usermodel.*;

public class DataParse {
	private int studentNum;
	private ArrayList<String> students;
	private HashMap<String, String> formatStudents;
	private JFrame mainFrame;
	private JFrame parentFrame;
	private JTextArea displayArea;
	String result;
	
	public DataParse(File dataFile) {
		studentNum = 0;
		students = new ArrayList<String>();
		formatStudents = new HashMap<String, String>();
		Scanner a = null;
		try {
			a = new Scanner(dataFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainFrame = new JFrame("Sort");
		displayArea = new JTextArea();
		mainFrame.add(displayArea);
		mainFrame.setVisible(true);
	}
	
	public DataParse(String[] students2) {
		studentNum = 0;
		students = new ArrayList<String>();
		formatStudents = new HashMap<String, String>();
		for (String a: students2){
			formatName(a.substring(2), a.substring(0,1), a.charAt(1)=='M');
			studentNum++;
		}
		
		mainFrame = new JFrame("Sort");
		displayArea = new JTextArea("Nothing here yet");
		mainFrame.add(displayArea);
		mainFrame.setVisible(true);
		mainFrame.setBounds(50, 50, 520, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void formatName(String name, String house, boolean gender){
		//gender is true if male
		String outName = "";
		outName += (char)(65+studentNum%26);
		outName += (char)(97+studentNum/26);
		outName += gender ? 'M' : 'F';
		outName += house.charAt(0);
		formatStudents.put(outName, name);
		students.add(outName);
		//return outName;
	}
	
	public void setParent(JFrame par){
		parentFrame = par;
	}
	
	public void doThingy(){
		GroupCreator grouper = new GroupCreator();
		ArrayList<String> results = grouper.groupify(students, 1);
		result = parseDataList(results);
		System.out.println(result);
		displayArea.setText(result);
	}
	
	public String parseDataList(ArrayList<String> a){
		String outstr = "";
		int e=1;
		for (String str: a){
			outstr += "\nDay "+e++;
			String[] b = str.split(", ");
			for (int i=1; i<b.length; i++){
				outstr += "Group " + i + "\n";
				String[] c = b[i].trim().split(" ");
				for (String d: c){
					outstr += formatStudents.get(d)+"\t";
				}
				outstr += "\n";
			}
		}
		return outstr;
	}
	
	public static void main(String[] args){
		File file = new File("H:\\Li_Excel.xlsx");
		DataParse parser = new DataParse(new String[]{"SMBob","SMJimmy","SMDaniel","SMTimmy","SMSahil","HMDylan","SMJohn","GMKyle","SMBrian","GMLiam","GMBob2","GMBbbbb","HMdhisog","HMfjio"
				,"HFasjgio","GFjidfobd","HFsdfjosd","SFjbidob","SFbkoesw","SFboipfj","SFbiocxv",
				"SMBob22","SMJimmy2","SMDaniel2","SMTimmy2","SMSahil2","HMDylan2","SMJohn2","GMKyle2","SMBrian2","GMLiam2","GMBob222","GMBbbbb2","HMdhisog2","HMfjio2"
				,"HFasjgio2","GFjidfobd2","HFsdfjosd2","SFjbidob2","SFbkoesw2","SFboipfj2","SFbiocxv2"});
		double time = System.currentTimeMillis();
		parser.doThingy();
		double timenow = System.currentTimeMillis();
		System.out.println((timenow-time)/1000);
	}

}
