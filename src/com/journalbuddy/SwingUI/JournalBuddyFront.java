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


import com.journalbuddy.DataFilter.ConcurrentSearch;
import com.journalbuddy.DataFilter.Filter;
import com.journalbuddy.DataFilter.FilterData;
import com.journalbuddy.DataFilter.JournalDataLoader;
import com.journalbuddy.JournalDatabase.JournalData;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

public class JournalBuddyFront extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9100063317687695082L;
	private JPanel contentPane;
	private JTable dirtable;
	private JTable doitable;
	private String TXTfilesLoc="E:\\Research Papers\\New folder";
	private String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
	Path CSVpath = Paths.get("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv");
	String vocabsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\VocabTFs.txt";
	String Keywordsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\KeyWords.txt";
	private JTextField wordfield;
	private JTable filterstable;
	
	List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
	private JTable finalfiltertable;
	private JTable allkeytable;
	private JTable allvoctable;
	private JTable selectkeytable;
	private JTable selectvocabtable;
	private JTable selectentertable;
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
		title.setBounds(457, 11, 296, 67);
		toppanel.add(title);
		
		JLabel subtitle = new JLabel("An extensive text analysis and management tool for Scientific Journals.");
		subtitle.setBounds(312, 68, 715, 44);
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
		scrollPane.setBounds(10, 99, 1332, 595);
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
		scrollPane_1.setBounds(10, 145, 1332, 549);
		parse_files.add(scrollPane_1);
		
		Object[] doitablemodel_colnames= {"Directory", "File Name", "Digital Object Identifier"};
		DefaultTableModel doitablemodel = new DefaultTableModel(null, doitablemodel_colnames);
//		doitablemodel.addColumn("Directory");
//		doitablemodel.addColumn("File Name");
//		doitablemodel.addColumn();
		doitable = new JTable(doitablemodel);
		
		scrollPane_1.setViewportView(doitable);
		JButton retrievedata = new JButton("Retrieve Data");
		retrievedata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		retrievedata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	List<String> directoryName= new ArrayList<String>();
				for (int count = 0; count < model.getRowCount(); count++){

						try {
							directoryName.add(model.getValueAt(count, 0).toString());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
		
		JTabbedPane wordsearch = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.addTab("Text Mining and Analysis", null, wordsearch, null);
		
		JPanel deepsearchh = new JPanel();
		wordsearch.addTab("Deep Text Search", null, deepsearchh, null);
		deepsearchh.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("Enter a Word to search: ");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
		lblNewLabel.setBounds(113, 44, 207, 39);
		deepsearchh.add(lblNewLabel);
		
		wordfield = new JTextField();
		wordfield.setBounds(330, 51, 730, 34);
		deepsearchh.add(wordfield);
		wordfield.setColumns(10);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 150, 1337, 572);
		deepsearchh.add(scrollPane_4);
		
		JTree tree = new JTree();
		scrollPane_4.setViewportView(tree);
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
		deepsearchh.add(wordsearchbutton);
		
//		JPanel vocaball = new JPanel();
//		wordsearch.addTab("Analyze Vocabulary on All Journals", null, vocaball, null);
//		vocaball.setLayout(null);
		
		JTabbedPane vocaball = new JTabbedPane(JTabbedPane.TOP);
		wordsearch.addTab("Analyze Vocabulary on All Journals", null, vocaball, null);
		
		JPanel allkeywords = new JPanel();
		vocaball.addTab("Top Keywords from All Documents", null, allkeywords, null);
		allkeywords.setLayout(null);
		
		allkeytable = new JTable();
		allkeytable.setBounds(10, 106, 1327, 560);
		allkeywords.add(allkeytable);
		
		JButton allkeyrefresh = new JButton("Refresh Data");
		allkeyrefresh.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allkeyrefresh.setBounds(703, 11, 199, 60);
		allkeywords.add(allkeyrefresh);
		
		JButton allkeyshowdata = new JButton("Show Data");
		allkeyshowdata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allkeyshowdata.setBounds(321, 11, 199, 60);
		allkeywords.add(allkeyshowdata);
		
		JPanel allvocab = new JPanel();
		vocaball.addTab("Vocabulary from All Documents", null, allvocab, null);
		allvocab.setLayout(null);
		
		allvoctable = new JTable();
		allvoctable.setBounds(10, 106, 1327, 560);
		allvocab.add(allvoctable);
		
		JButton allvocshowdata = new JButton("Show Data");
		allvocshowdata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allvocshowdata.setBounds(321, 11, 199, 60);
		allvocab.add(allvocshowdata);
		
		JButton allvocrefreshdata = new JButton("Refresh Data");
		allvocrefreshdata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allvocrefreshdata.setBounds(703, 11, 199, 60);
		allvocab.add(allvocrefreshdata);
		
		JTabbedPane vocabselected = new JTabbedPane(JTabbedPane.TOP);
		wordsearch.addTab("Analyze Vocabulary on SelectedJournals", null, vocabselected, null);
		
		JPanel enterdocs = new JPanel();
		vocabselected.addTab("Enter Document for Text Analysis", null, enterdocs, null);
		enterdocs.setLayout(null);
		
		selectentertable = new JTable();
		selectentertable.setBounds(10, 106, 1327, 560);
		enterdocs.add(selectentertable);
		
		JButton selectaddrow = new JButton("Add a Row");
		selectaddrow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectaddrow.setBounds(321, 11, 199, 60);
		enterdocs.add(selectaddrow);
		
		JButton selectproceed = new JButton("Proceed to Analyze");
		selectproceed.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectproceed.setBounds(703, 11, 199, 60);
		enterdocs.add(selectproceed);
		
		JPanel selectkeywords = new JPanel();
		vocabselected.addTab("Top Keywords from Selected Documents", null, selectkeywords, null);
		selectkeywords.setLayout(null);
		
		selectkeytable = new JTable();
		selectkeytable.setBounds(10, 106, 1327, 560);
		selectkeywords.add(selectkeytable);
		
		JButton selectkeyshow = new JButton("Show Data");
		selectkeyshow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectkeyshow.setBounds(321, 11, 199, 60);
		selectkeywords.add(selectkeyshow);
		
		JButton selectkeyrefresh = new JButton("Refresh Data");
		selectkeyrefresh.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectkeyrefresh.setBounds(703, 11, 199, 60);
		selectkeywords.add(selectkeyrefresh);
		
		JPanel selectvocab = new JPanel();
		vocabselected.addTab("Vocabulary from Selected Documents", null, selectvocab, null);
		selectvocab.setLayout(null);
		
		selectvocabtable = new JTable();
		selectvocabtable.setBounds(10, 106, 1327, 560);
		selectvocab.add(selectvocabtable);
		
		JButton selectvocabshow = new JButton("Show Data");
		selectvocabshow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectvocabshow.setBounds(321, 11, 199, 60);
		selectvocab.add(selectvocabshow);
		
		JButton selectvocabrefresh = new JButton("Refresh Data");
		selectvocabrefresh.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectvocabrefresh.setBounds(703, 11, 199, 60);
		selectvocab.add(selectvocabrefresh);
		
		
		JTabbedPane datafilterr = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.addTab("Data Filter", null, datafilterr, null);
		
		JPanel inputfilters = new JPanel();
		datafilterr.addTab("Input Filters", null, inputfilters, null);
		inputfilters.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 1332, 594);
		inputfilters.add(scrollPane_2);
		
//		   comboBoxTraceModeSelection = new JComboBox<TraceMode>();
//		   comboBoxTraceModeSelection.setModel(new DefaultComboBoxModel<TraceMode>
//		(TraceMode.values()));
		   
        String[] attrs = { "File_Name", "DOI", "Title", "Container_Name", "Author_FirstName",
        		"Author_LastName", "Author_Sequence", "Single_Subject", "Single_Funder", "Single_Award",
        		"Volume", "Issue", "Is_Referenced_By_Count", "Reference_count", "Published_Date", "Issued_Date"};
        JComboBox<String> comboBox1 = new JComboBox<String>();
        comboBox1.setModel(new DefaultComboBoxModel<String>(attrs));
        DefaultCellEditor dce1 = new DefaultCellEditor( comboBox1 );
        editors.add( dce1 );

        String[] items2 = { "equals", "greater_than", "lesser_than" };
        JComboBox<String> comboBox2 = new JComboBox<String>();
        comboBox2.setModel(new DefaultComboBoxModel<String>(items2));
        DefaultCellEditor dce2 = new DefaultCellEditor( comboBox2 );
        editors.add( dce2 );
        //FOR DEMO
        Object[][] data = {
        		{"Author_FirstName", "equals", "Alessandr", "2"}, 
        		{"Single_Subject", "equals", "Electrical and Electronic Engineeri", "3"}
        };
      //FOR DEMO
        Object[] ColumnNamess= {"Attribute", "Filter Parameter", "Value", "Max. Word Difference"};
        
		DefaultTableModel filterstablemodel = new DefaultTableModel(data, ColumnNamess);

		
		filterstable = new JTable(filterstablemodel) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 100661786991169710L;

			public TableCellEditor getCellEditor(int row, int column)
            {
                if (column==0)
                    return editors.get(0);
                else if (column==1)
                    return editors.get(1);
                else
                    return super.getCellEditor(row, column);
            }
        };
		scrollPane_2.setViewportView(filterstable);
		filterstable.setRowHeight(20);
		JButton filteraddrow = new JButton("Add a Row");
		filteraddrow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		filteraddrow.setBounds(264, 616, 199, 60);
		filteraddrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterstablemodel.addRow(new Object[] {null, null, null, null});
			}
			
		});
		inputfilters.add(filteraddrow);
		
		JPanel filteroutput = new JPanel();
		datafilterr.addTab("View Filter Output", null, filteroutput, null);
		filteroutput.setLayout(null);
		
		
		DefaultTableModel finaltablemodel = new DefaultTableModel(null, attrs);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 11, 1332, 683);
		filteroutput.add(scrollPane_3);
		finalfiltertable = new JTable(finaltablemodel);
		scrollPane_3.setViewportView(finalfiltertable);
		add_dir.setLayout(null);
//		
//        String[] attrs = { "File_Name", "DOI", "Title", "Container_Name", "Author_FirstName",
//        		"Author_LastName", "Author_Sequence", "Single_Subject", "Single_Funder", "Single_Award",
//        		"Volume", "Issue", "Is_Referenced_By_Count", "Reference_count", "Published_Date", "Issued_Date"};
		
		JButton btnApplyFilters = new JButton("Apply Filters");
		btnApplyFilters.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnApplyFilters.setBounds(892, 616, 199, 60);
		btnApplyFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JournalData data[] = null;
				try {
					data = JournalDataLoader.load(CSVpath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				int SIZE=data.length;
				HashMap<String, Integer> filtercriterias=Filter.GenerateFilterCriteria();
				List<FilterData> filters = new ArrayList<FilterData>();
				FilterData filter = new FilterData();
				filters = new ArrayList<FilterData>();
				
				for(int i = 0;i<filterstable.getRowCount();i++) {
					try {
						filter = new FilterData();
						filter.setIdField(filtercriterias.get(filterstable.getValueAt(i, 0)+"##"+filterstable.getValueAt(i, 1)));
						filter.setLeven_dis(Integer.parseInt(filterstable.getValueAt(i, 3).toString()));
						filter.setValue((String) filterstable.getValueAt(i, 2).toString());
						filters.add(filter);
					} catch (Exception e1) {
						//INVALID FILTER PARAMETER
						e1.printStackTrace();
					}				
				}
				List<JournalData> results = ConcurrentSearch.findAll(data, filters, SIZE);
				for(JournalData resultiter: results) {
//					System.out.println(resultiter.getTitle());
//					System.out.println(resultiter.getAuthor_firstname());
					finalfiltertable.repaint();
					finaltablemodel.addRow(new Object[] {
							resultiter.getFilename(),
							resultiter.getDoi(),
							resultiter.getTitle(),
							resultiter.getContainer_name(),
							resultiter.getAuthor_firstname(),
							resultiter.getAuthor_lastname(),
							resultiter.getAuth_seq(),
							resultiter.getSingle_subject(),
							resultiter.getSingle_funder(),
							resultiter.getSingle_award(),
							resultiter.getVolume(),
							resultiter.getIssue(),
							resultiter.getIs_referenced_by_count(),
							resultiter.getReference_count(),
							resultiter.getPub_date(),
							resultiter.getIssue_date()
					});
					
				}
				datafilterr.setSelectedIndex(1);
			}
			
		});
		inputfilters.add(btnApplyFilters);
		add_dir.setLayout(null);
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
