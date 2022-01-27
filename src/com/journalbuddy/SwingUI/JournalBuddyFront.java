package com.journalbuddy.SwingUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.journalbuddy.MatchingVocab.MatchingIndex;
import com.journalbuddy.PDFParser.PDFParserClass;
import com.journalbuddy.invertedindexing.InvertedIndexParser;
import com.journalbuddy.invertedindexing.InvertedIndexingMain;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

public class JournalBuddyFront extends JFrame {

	private JPanel contentPane;
	private JTable dirtable;
	private JTable doitable;
	private String TXTfilesLoc="E:\\Research Papers\\New folder";
	private String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
	private JTextField wordfield;
	private JTable filterstable;
	
	List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JournalBuddyFront frame = new JournalBuddyFront();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public JournalBuddyFront() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1376, 921);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel toppanel = new JPanel();
		toppanel.setBackground(Color.YELLOW);
		toppanel.setBounds(0, 0, 1362, 123);
		contentPane.add(toppanel);
		toppanel.setLayout(null);
		
		JLabel title = new JLabel("Journal Buddy");
		title.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 45));
		title.setBounds(551, 11, 296, 67);
		toppanel.add(title);
		
		JLabel subtitle = new JLabel("An extensive text analysis and management tool for Scientific Journals.");
		subtitle.setBounds(406, 68, 715, 44);
		toppanel.add(subtitle);
		subtitle.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		
		JTabbedPane maintabpane = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.setBounds(0, 123, 1362, 761);
		contentPane.add(maintabpane);
		
		JTabbedPane preprocessing = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.addTab("Pre-Processing Steps", null, preprocessing, null);
		
		JPanel add_dir = new JPanel();
		preprocessing.addTab("Add New Directories", null, add_dir, null);
		add_dir.setLayout(null);
		
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Directories");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(103, 99, 1168, 588);
		add_dir.add(scrollPane);
		dirtable = new JTable(model);
		scrollPane.setViewportView(dirtable);
		//FOR DEMO
		model.addRow(new Object[]{"E:\\Research Papers\\Integrated Circuit"});
		model.addRow(new Object[]{"E:\\Research Papers\\Image Processing"});
		//FOR DEMO
		JButton browsedir = new JButton("Browse Directory");
		browsedir.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		browsedir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JFileChooser f = new JFileChooser();
		        f.setCurrentDirectory(new java.io.File("../"));
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);
				DefaultTableModel model=(DefaultTableModel) dirtable.getModel();
				model.addRow(new Object[]{f.getSelectedFile()});
			}
		});
		browsedir.setBounds(583, 28, 199, 60);
		add_dir.add(browsedir);
		
		JPanel parse_files = new JPanel();
		preprocessing.addTab("Review Journal Files and DOI", null, parse_files, null);
		parse_files.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(26, 145, 1307, 550);
		parse_files.add(scrollPane_1);
		
		DefaultTableModel doitablemodel = new DefaultTableModel();
		doitablemodel.addColumn("Directory");
		doitablemodel.addColumn("File Name");
		doitablemodel.addColumn("Digital Object Identifier");
		doitable = new JTable(doitablemodel);
		
		scrollPane_1.setViewportView(doitable);
		JButton retrievedata = new JButton("Retrieve Data");
		retrievedata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		retrievedata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	List<String> directoryName= new ArrayList<String>();
				for (int count = 0; count < model.getRowCount(); count++){
					if(model.getValueAt(count, 0).toString()!="") {
						directoryName.add(model.getValueAt(count, 0).toString());
					}
				}
		    	List<File> filess = new ArrayList<File>();
		    	directoryName.parallelStream().forEach((directoryNameIter) -> {
		    		PDFParserClass.listf(directoryNameIter, filess);
		    	});
				try {
					Hashtable<String, String> doi_dict = PDFParserClass.PDFtoTXTMain(directoryName, TXTfilesLoc);
					for (File filedir : filess) {
						try {
							doitablemodel.addRow(new Object[]{filedir.getParent(), filedir.getName(), doi_dict.get(filedir.getName())});
						} catch (Exception e1) {
							doitablemodel.addRow(new Object[]{filedir.getParent(), filedir.getName(), "ENTER_DOI_HERE"});
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		retrievedata.setBounds(277, 44, 199, 60);
		parse_files.add(retrievedata);
		
				
		JButton fetchtable = new JButton("Fetch Table");
		fetchtable.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		fetchtable.setBounds(716, 44, 199, 60);
		parse_files.add(fetchtable);
		
		JPanel wordsearch = new JPanel();
		maintabpane.addTab("Deep Search for Journals", null, wordsearch, null);
		wordsearch.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter a Word to search: ");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
		lblNewLabel.setBounds(113, 44, 207, 39);
		wordsearch.add(lblNewLabel);
		
		wordfield = new JTextField();
		wordfield.setBounds(330, 51, 730, 34);
		wordsearch.add(wordfield);
		wordfield.setColumns(10);
		
		JTree tree = new JTree();
		tree.setBounds(113, 150, 1144, 503);
		wordsearch.add(tree);
    	DefaultTreeModel treemodel = (DefaultTreeModel)tree.getModel();
    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)treemodel.getRoot();
	    root.removeAllChildren();
	    treemodel.reload();
	    
		JButton wordsearchbutton = new JButton("Search");
		wordsearchbutton.setBounds(1093, 44, 164, 39);
		wordsearchbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		    	DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			    root.removeAllChildren();
			    model.reload();
		        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);

		    	ConcurrentHashMap<String, String[]> IndexLines=InvertedIndexParser.ReadIndexLines(InvertedIndexFile);
		    	System.out.print(IndexLines.size());
		    	
		    	ConcurrentHashMap<String, String> MatchWords =MatchingIndex.GetMatchingWordsFromII(InvertedIndexFile, wordfield.getText());
		    	System.out.println("Count of Matched Words: "+Integer.toString(MatchWords.size()));
		    	System.out.println("The set is: " + MatchWords.toString());
		    	
		    	DefaultMutableTreeNode mainword=new DefaultMutableTreeNode("Search Word: "+wordfield.getText());
		    	Set<String> setOfKeys = MatchWords.keySet();
		    	for (String key : setOfKeys) {
		    		DefaultMutableTreeNode foundword=new DefaultMutableTreeNode("Found Similar Word: "+key);
		    		mainword.add(foundword);
		    		String[] foundfiles=MatchWords.get(key).split(";");
		    		for(int k = 0;k<foundfiles.length;k++) {
		    			DefaultMutableTreeNode foundfile=new DefaultMutableTreeNode("Found in File: "+foundfiles[k]);
		    			foundword.add(foundfile);
		    		}
		    		
		    	}
		    	
		    	root.add(mainword);
		    	model.reload(root);
		    	
			}
			
		});
		wordsearch.add(wordsearchbutton);
		
		JTabbedPane datafilterr = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.addTab("Data Filter", null, datafilterr, null);
		
		JPanel inputfilters = new JPanel();
		datafilterr.addTab("Input Filters", null, inputfilters, null);
		inputfilters.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 1332, 594);
		inputfilters.add(scrollPane_2);
		
		
        String[] attrs = { "", "File_Name", "DOI", "Title", "Container_Name", "Author_FirstName",
        		"Author_LastName", "Single_Subject", "Single_Funder", "Single_Award", "Author_Sequence",
        		"Volume", "Issue", "Is_Referenced_By_Count", "Reference_count", "Published_Date", "Issued_Date"};
        JComboBox<String> comboBox1 = new JComboBox<String>( attrs );
        DefaultCellEditor dce1 = new DefaultCellEditor( comboBox1 );
        editors.add( dce1 );

        String[] items2 = { "", "equals", "greater_than", "lesser_than" };
        JComboBox<String> comboBox2 = new JComboBox<String>( items2 );
        DefaultCellEditor dce2 = new DefaultCellEditor( comboBox2 );
        editors.add( dce2 );
        
		DefaultTableModel filterstablemodel = new DefaultTableModel();
		filterstablemodel.addColumn("Attribute");
		filterstablemodel.addColumn("Value");
		filterstablemodel.addColumn("Filter Parameter");
		filterstablemodel.addColumn("Max. Word Difference");
		
		filterstable = new JTable(filterstablemodel) {
            public TableCellEditor getCellEditor(int row, int column)
            {
                int modelColumn = convertColumnIndexToModel( column );

                if (column==0)
                    return editors.get(0);
                else if (column==2)
                    return editors.get(1);
                else
                    return super.getCellEditor(row, column);
            }
        };
		scrollPane_2.setViewportView(filterstable);
		
		JButton filteraddrow = new JButton("Add a Row");
		filteraddrow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		filteraddrow.setBounds(264, 616, 199, 60);
		filteraddrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterstablemodel.addRow(new Object[] {"", "", "", ""});
			}
			
		});
		inputfilters.add(filteraddrow);
		
		JButton btnApplyFilters = new JButton("Apply Filters");
		btnApplyFilters.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnApplyFilters.setBounds(892, 616, 199, 60);
		inputfilters.add(btnApplyFilters);
		add_dir.setLayout(null);
		
		JPanel filteroutput = new JPanel();
		datafilterr.addTab("View Filter Output", null, filteroutput, null);
		add_dir.setLayout(null);
		
	}
}
