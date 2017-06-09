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

class ExcelFilter extends FileFilter{

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    
	public boolean accept(File f) {
		if (f.isDirectory()) {
	        return true;
	    }

	    String extension = getExtension(f);
	    		//Utils.getExtension(f);
	    if (extension != null) {
	    	if (extension.equals("xlsx")) {
                return true;
	    	} else {
	            return false;
	        }
	    }

	    return false;
	}

	public String getDescription() {
		return "Compatible Data Files (.xlsx)";
	}
	
}

public class ExcelFileSelector {
	private JPanel pan;
	private JFrame pan2;
	private SeatingChart sc;
	private JButton but;
	private JButton but2;
	private JTextField address;
	private JFileChooser fc;
	private File selected;
	public SeatingChart parent;
	
	public ExcelFileSelector(SeatingChart par){
		sc = par;
		pan2 = new JFrame("Choose File");
		but = new JButton("Browse");
		but.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = showChooser();
				if (selected == null){
					return;
				}
				address.setText(selected.getPath());
				but2.setEnabled(true);
			}
		});
		
		but2 = new JButton("Submit");
		but2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//DataParse parser = new DataParse(selected.getPath());
				parent.setPath(selected.getPath());
				//DataParse dp = new DataParse(selected.getPath(), parent);
				//Thread t = new Thread(dp);
				//t.start();
				//parser.setParent(pan2);
				//double time = System.currentTimeMillis();
				pan2.setVisible(false);
				pan2.dispose();
				
			}
		});
		but2.setEnabled(false);

		address = new JTextField();
		address.setEditable(false);
		
		JTextField myPanel = new JTextField("Drag Files Here");
		myPanel.setHorizontalAlignment(JTextField.CENTER);
		myPanel.setEditable(false);
		myPanel.setDropTarget(new DropTarget() {
		    public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>)
		                evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		            File file = droppedFiles.get(0);
		            
		            if (ExcelFilter.getExtension(file).equals("xlsx")){
		            	address.setText(file.getPath());
		            	selected = file;
		            }
		            but2.setEnabled(true);
		            //    // process files
		            //}
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		Container pane = pan2.getContentPane();
	    GridBagConstraints c = new GridBagConstraints();
	    pane.setLayout(new GridBagLayout());
	    
	    c.gridwidth = 1;c.gridy = 0;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 0.9;c.weighty = 0;
	    pane.add(address,c);
	    
	    c.gridwidth = 1;c.gridy = 0;c.gridx = 1;c.fill = GridBagConstraints.HORIZONTAL;c.weightx = 0.1;c.weighty = 0;
	    pane.add(but,c);

	    c.gridwidth = 2;c.gridy = 1;c.gridx = 0;c.fill = GridBagConstraints.BOTH;c.weightx = 1;c.weighty = 1;
	    pane.add(myPanel,c);
	    
	    c.gridwidth = 2;c.gridy = 2;c.gridx = 0;c.fill = GridBagConstraints.HORIZONTAL;c.weightx = 1;c.weighty = 0;
	    pane.add(but2,c);
		
		pan2.setVisible(true);
		pan2.setBounds(50, 50, 520, 310);
	    //pan2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private File showChooser(){
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new ExcelFilter());
		pan = new JPanel();
		fc.setAcceptAllFileFilterUsed(false);
		//pan.setTransferHandler();
		int returnVal = fc.showDialog(pan, "Attach");
		if (returnVal == 1){
			return null;
		}
		return fc.getSelectedFile();
	}
	
	public static void main(String[] args){
		//ExcelFileSelector sc = new ExcelFileSelector();
	}
}
