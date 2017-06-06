import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.poi.xssf.usermodel.*;
class Student {
	public String name;
	public String encoded;
	public int[] groups;
	
	public Student(String n, String e){
		name = n;
		encoded = e;
	}
}
class NameComparator implements Comparator<String>{

	public int compare(String arg0, String arg1) {
		return arg0.compareTo(arg1);
	}
	
}
public class DataParse implements Runnable{
	private int studentNum;
	private ArrayList<String> students;
	private ArrayList<String> studentNames;
	private HashMap<String, int[]> studentResults;
	private HashMap<String, String> formatStudents;
	private JFrame mainFrame;
	private JFrame parentFrame;
	private JButton left;
	private JButton switchd;
	private JButton right;
	private JTextArea displayArea;
	private String[] result;
	private int position;
	private boolean alphabetize = false;
	
	public DataParse(String dataPath) {
		position = 0;
		studentNum = 0;
		students = new ArrayList<String>();
		studentNames = new ArrayList<String>();
		studentResults = new HashMap<String, int[]>();
		formatStudents = new HashMap<String, String>();
		try {
			readExcelData(dataPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		left = new JButton("<");
		left.setEnabled(false);
		left.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (position > 0) position--;
				displayArea.setText(changeDisplay());
			}
		});
		
		switchd = new JButton(">=[+]=<");
		switchd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				alphabetize ^= true;
				displayArea.setText(changeDisplay());
			}
		});
		
		right = new JButton(">");
		right.setEnabled(false);
		right.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (position < 4) position++;
				displayArea.setText(changeDisplay());
			}
		});
		
		displayArea = new JTextArea("Please wait");
		
		mainFrame = new JFrame("Sort");
		
		Container buttonholder = mainFrame.getContentPane();
	    GridBagConstraints c = new GridBagConstraints();
	    buttonholder.setLayout(new GridBagLayout());
	    c.gridwidth = 3;c.gridy = 0;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 1;
	    buttonholder.add(displayArea,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 0;c.fill = GridBagConstraints.NONE;c.weightx = .1;c.weighty = .1;
	    buttonholder.add(left,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 1;c.fill = GridBagConstraints.NONE;c.weightx = .2;c.weighty = .1;
	    buttonholder.add(switchd,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 2;c.fill = GridBagConstraints.NONE;c.weightx = .1;c.weighty = .1;
	    buttonholder.add(right,c);
		
		mainFrame.setVisible(true);
		mainFrame.setBounds(50, 50, 520, 900);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public DataParse(String[] students2) {
		position = 0;
		studentNum = 0;
		students = new ArrayList<String>();
		studentNames = new ArrayList<String>();
		formatStudents = new HashMap<String, String>();
		for (String a: students2){
			formatName(a.substring(2), a.substring(0,1), a.charAt(1)=='M', Math.random() < 0.3);
			studentNum++;
		}
		
		left = new JButton("<");
		left.setEnabled(false);
		left.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (position > 0) position--;
				displayArea.setText(result[position]);
			}
		});
		
		switchd = new JButton(">-[+]-<");
		switchd.setEnabled(false);
		switchd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				changeDisplay();
			}
		});
		
		right = new JButton(">");
		right.setEnabled(false);
		right.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (position < 4) position++;
				displayArea.setText(result[position]);
			}
		});
		
		displayArea = new JTextArea("Please wait");
		
		mainFrame = new JFrame("Sort");
		
		Container buttonholder = mainFrame.getContentPane();
	    GridBagConstraints c = new GridBagConstraints();
	    buttonholder.setLayout(new GridBagLayout());
	    c.gridwidth = 3;c.gridy = 0;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 1;
	    buttonholder.add(displayArea,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 0;c.fill = GridBagConstraints.NONE;c.weightx = .1;c.weighty = .1;
	    buttonholder.add(left,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 1;c.fill = GridBagConstraints.NONE;c.weightx = .2;c.weighty = .1;
	    buttonholder.add(switchd,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 2;c.fill = GridBagConstraints.NONE;c.weightx = .1;c.weighty = .1;
	    buttonholder.add(right,c);
		
		mainFrame.setVisible(true);
		mainFrame.setBounds(50, 50, 520, 900);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void readExcelData(String excelFilePath) throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
         
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.rowIterator();
        iterator.next();
        
        while (iterator.hasNext()) {
        	XSSFRow nextRow = (XSSFRow) iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            String[] data = new String[4];
            int counter = 0;
            XSSFCell cell = (XSSFCell) cellIterator.next();
            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && cell.getStringCellValue().equals("1st")){
            		while (cellIterator.hasNext()) {
                    	cell = (XSSFCell) cellIterator.next();
                        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        	System.out.println(cell.getStringCellValue());
                        	data[counter++] = cell.getStringCellValue();
                        }
                    }
            		formatName(data[0], data[2],  data[1].equals("Male"), data[3].equals("Clemente"));
                    studentNum++;
            }
           
        }
         
        workbook.close();
        inputStream.close();
	}
	
	public void formatName(String name, String house, boolean gender, boolean clemente){
		//gender is true if male
		String outName = "";
		outName += (char)(65+studentNum%26);
		outName += (char)(97+studentNum/26);
		outName += gender ? 'M' : 'F';
		outName += house.charAt(0);
		outName += clemente ? 'C' : 'O';
		formatStudents.put(outName, name);
		students.add(outName);
		studentNames.add(name);
		//return outName;
	}
	
	public void setParent(JFrame par){
		parentFrame = par;
	}
	
	public void doThingy(){
		GroupCreator grouper = new GroupCreator(this);
		ArrayList<String> results = grouper.groupify(students, 1);
		result = parseDataList(results).split("\n\n");
		displayArea.setText(result[0]);
		left.setEnabled(true);right.setEnabled(true);
		studentNames.sort(new NameComparator());
	}
	
	public void incrementProgress(int p){
		displayArea.setText(p+"% Complete");
	}
	
	public String changeDisplay(){
		if (alphabetize){
			String outstr = "";
			outstr += "Day "+(position+1)+"\n";
			for (String str: studentNames){
				outstr += str + "\t\t" + "Group " + studentResults.get(str)[position]+"\n";
			}
			return outstr;
		} else {
			return result[position];
		}
	}
	
	public String parseDataList(ArrayList<String> a){
		String outstr = "";
		int e=1;
		for (String str: a){
			outstr += "Day "+e++;
			outstr += "\n";
			String[] b = str.split(", ");
			for (int i=1; i<b.length; i++){
				outstr += "Group " + i + "\n";
				String[] c = b[i].trim().split(" ");
				for (String d: c){
					outstr += formatStudents.get(d)+"\t";
					int[] thingy = new int[5];
					if (e>2) {thingy = studentResults.get(formatStudents.get(d));}
					thingy[e-2] = i;
					studentResults.put(formatStudents.get(d), thingy);
				}
				outstr += "\n";
			}
			outstr += "\n";
		}
		return outstr;
	}
	
	public static void main(String[] args){
		DataParse parser = new DataParse(args[0]);
		parser.doThingy();
	}

	@Override
	public void run() {
		doThingy();
	}

}
