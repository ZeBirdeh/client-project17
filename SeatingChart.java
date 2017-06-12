import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SeatingChart {
	private JPanel pan;
	private JFrame pan2;
	//private JButton fcBut;
	//private JButton sortBut;
	//private JButton export;
	private String filePath;
	private JMenuBar menuBar;
	private JPanel menuBar2;
	private JMenu menu;
	private JMenuItem menuItem;
	private MenuItemListener menuItemListener;
	public String wordExport;
	
	public SeatingChart(){
		pan2 = new JFrame("Seating Chart Creator");
		pan2.setBounds(200, 200, 520, 310);
		GridBagConstraints c = new GridBagConstraints();
	    pan2.setLayout(new GridBagLayout());

		pan = new JPanel();
		JTextArea ta = new JTextArea("Nothing Here Yet");
		ta.setBackground(new Color(238,238,238));
		pan.add(ta);
		
		menuBar2 = new JPanel();
		menuBar2.setLayout(null);

	    menuItemListener = new MenuItemListener(this);
	    
		ImageIcon imp = null;
		try {
			imp = new ImageIcon("img\\import.jpg");
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		JButton importbut = new JButton(imp);
		importbut.setActionCommand("Import");
		importbut.setBounds(0,0,16,16);
		importbut.addActionListener(menuItemListener);
		
		imp = null;
		try {
			imp = new ImageIcon("img\\run.jpg");
		} catch (Exception ex) {
		    System.out.println(ex);
		}
		JButton runbut = new JButton(imp);
		runbut.setActionCommand("Run");
		runbut.addActionListener(menuItemListener);
		runbut.setBounds(16,0,16,16);
		
		menuBar2.add(importbut);
		menuBar2.add(runbut);
		
	    //pan.setSize(pan2.getWidth(), pan2.getHeight()-50);
		
		/*fcBut = new JButton("Choose File");
		fcBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				showChooser();
			}
		});
		
		sortBut = new JButton("Sort List");
		sortBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
			}
		});*/	

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		JMenu fileMenu = new JMenu("File");
		JMenu chartMenu = new JMenu("Chart");
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
	     
	    //create menu items
	    JMenuItem newMenuItem = new JMenuItem("New");
	    newMenuItem.setMnemonic(KeyEvent.VK_N);
	    newMenuItem.setActionCommand("New");

	    JMenuItem openMenuItem = new JMenuItem("Import");
	    openMenuItem.setActionCommand("Import");
	    openMenuItem.setMnemonic(KeyEvent.VK_I);

	    JMenuItem saveMenuItem = new JMenuItem("Export");
	    saveMenuItem.setActionCommand("Export");
	    
	    JMenuItem exitMenuItem = new JMenuItem("Exit");
	    exitMenuItem.setActionCommand("Exit");
	    exitMenuItem.setMnemonic(KeyEvent.VK_X);

	    JMenuItem runMenuItem = new JMenuItem("Run");
	    runMenuItem.setActionCommand("Run");
	    
	    JMenuItem manMenuItem = new JMenuItem("Manual");
	    manMenuItem.setActionCommand("Manual");

	    newMenuItem.addActionListener(menuItemListener);
	    openMenuItem.addActionListener(menuItemListener);
	    saveMenuItem.addActionListener(menuItemListener);
	    runMenuItem.addActionListener(menuItemListener);
	    manMenuItem.addActionListener(menuItemListener);
	    exitMenuItem.addActionListener(menuItemListener);
	    
	    fileMenu.add(newMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(openMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(exitMenuItem);
	    
	    chartMenu.add(runMenuItem);
	    
	    helpMenu.add(manMenuItem);
	    
	    menuBar.add(fileMenu);
	    menuBar.add(chartMenu);
	    menuBar.add(helpMenu);
	    
		c.gridwidth = 1;c.gridy = 0;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 0.1;
	    pan2.add(menuBar2,c);
	    c.gridwidth = 1;c.gridy = 1;c.gridx = 0;c.fill = GridBagConstraints.HORIZONTAL;c.weightx = 1;c.weighty = 0;
	    pan2.add(menuItemListener,c);
	    c.gridwidth = 1;c.gridy = 2;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 0.9;
	    pan2.add(pan,c);
	    //menuBar.setSize(pan2.getWidth(), 50);  
	    pan2.setJMenuBar(menuBar);
		pan2.setVisible(true);
		pan2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pan2.add(menuItemListener);
		//showChooser();
	}
	
	public void showChooser(){
		ExcelFileSelector efs = new ExcelFileSelector(this);
		efs.parent = this;
	}
	
	public void getChart(JPanel p){
		pan2.remove(pan);
		pan = p;
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;c.gridy = 2;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 0.9;
	    pan2.add(pan,c);
	    pan2.revalidate();
	    setPath(filePath);
	}
	
	public void setPath(String path){
		filePath = path;
		menuItemListener.setText("File selected: " + filePath);
	}
	
	public String getPath(){
		return filePath;
	}
	
	public static void main(String[] args){
		SeatingChart sc = new SeatingChart();
	}
	
	class MenuItemListener extends JTextField implements ActionListener {
		SeatingChart parent;
		
		public MenuItemListener(SeatingChart sc){
			super("No file selected");
			this.setHorizontalAlignment(JTextField.CENTER);
			parent = sc;
			this.setEditable(false);
		}
		
	    public void actionPerformed(ActionEvent e) {            
	    	//this.setText(e.getActionCommand() + " JMenuItem clicked.");
	    	switch (e.getActionCommand()){
	    	case "Import":
	    		showChooser();
	    		break;
	    	case "Run":
	    		if (filePath != null && filePath.endsWith("xlsx")){
	    			this.setText("Running");
		    		DataParse dp = new DataParse(filePath, parent);
					Thread t = new Thread(dp);
					t.start();
				}
	    		break;
	    	case "Manual":
	    		try {
					URL url = new URL("https://docs.google.com/document/d/1XMkBt5jVRSBPxYx3LvOCK-zANIWJCSob3yJgFGD1kCA/edit?usp=sharing");
					openWebpage(url);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		break;
	    	case "Export":
	    		createWordDoc();
	    		break;
	    	case "Exit":
	    		System.exit(0);
	    		break;
	    	case "New":
	    	}
	    }
	    
	    private void createWordDoc(){
	    	XWPFDocument doc = new XWPFDocument();

	        XWPFParagraph p1 = doc.createParagraph();
	        String[] wordRuns = wordExport.split("NEWRUN");
	        
	        for (String paragraphs: wordRuns){
	        	XWPFRun run = p1.createRun();
	        	run.setFontFamily("Times New Roman");
	        	run.setFontSize(14);
	        	String[] lines = paragraphs.split("LINEBREAK");
	        	for (String line: lines){
	        		if (line.startsWith("Day")) {run.addBreak(BreakType.PAGE); run.setFontSize(16); run.setBold(true);} else {run.setFontSize(14); run.setBold(false);}
	        		String[] tabbo = line.split("\t");
	        		for (String tabb: tabbo){
		        		run.setText(tabb);
		        		run.addTab();
	        		}
	        		run.addCarriageReturn();
	        	}
	        }
	        
	        FileOutputStream out = null;
	        try {
				out = new FileOutputStream("Seating-Chart-" + (new Date().getMonth()) + "" + (new Date().getDate()) + ".docx");
				doc.write(out);
		        out.close();
		        doc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
	    }
	    
	    private void openWebpage(URI uri) {
	        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	            try {
	                desktop.browse(uri);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    private void openWebpage(URL url){
	    	try {
				openWebpage(url.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	    }
	}
}
