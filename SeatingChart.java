import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileFilter;

public class SeatingChart {
	private JPanel pan;
	private JFrame pan2;
	private JButton fcBut;
	private JButton sortBut;
	private JButton export;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	
	public SeatingChart(){
		pan2 = new JFrame("Seating Chart Creator");
		pan = new JPanel();
		
		fcBut = new JButton("Choose File");
		fcBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				showChooser();
			}
		});
		
		sortBut = new JButton("Sort List");
		sortBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
			}
		});
		
		export = new JButton("Export coming soon");
		export.setEnabled(false);
		
		

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("A text-only menu item",
		                         KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "This doesn't really do anything");
		menu.add(menuItem);
	    
		pan2.setVisible(true);
		pan2.setBounds(50, 50, 520, 310);
		
		//showChooser();
	}
	
	public void showChooser(){
		ExcelFileSelector efs = new ExcelFileSelector(this);
	}
	
	public static void main(String[] args){
		SeatingChart sc = new SeatingChart();
	}
}
