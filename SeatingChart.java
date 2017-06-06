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
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileFilter;

public class SeatingChart {
	private JPanel pan;
	private JFrame pan2;
	
	public SeatingChart(){
		showChooser();
	}
	
	private void showChooser(){
		ExcelFileSelector efs = new ExcelFileSelector();
	}
	
	public static void main(String[] args){
		SeatingChart sc = new SeatingChart();
	}
}
