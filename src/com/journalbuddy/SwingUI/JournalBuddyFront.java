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
import com.journalbuddy.JournalDatabase.DOIParsing;
import com.journalbuddy.JournalDatabase.InsertJournal;
import com.journalbuddy.JournalDatabase.JournalData;
import com.journalbuddy.KeyWordExtract.GetKeyWords;
import com.journalbuddy.KeyWordExtract.Word;
import com.journalbuddy.MatchingVocab.BestMatchingCalc;
import com.journalbuddy.MatchingVocab.BestMatchingData;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

public class JournalBuddyFront extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9100063317687695082L;
	
//	private String TXTfilesLoc="E:\\Research Papers\\New folder";
//	private String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
//	private String CSVpathSTR="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv";
//	private Path CSVpath = Paths.get(CSVpathSTR);
//	private String vocabsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\VocabTFs.txt";
//	private String Keywordsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\KeyWords.txt";
//	private String Tempvocabsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\TempVocabTFs.txt";
//	private String TempKeywordsfile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\TempKeyWords.txt";
//	private String DefaultDirs="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\DefaultDirs.txt";
//	private String DatabaseDIR="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db";
//	private String DatabaseURL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";

	private String TXTfilesLoc=System.getProperty("user.dir")+"\\data\\TXTfilesLoc";
	private String InvertedIndexFile=System.getProperty("user.dir")+"\\data\\InvertedIndexFile.txt";
	private String CSVpathSTR=System.getProperty("user.dir")+"\\data\\JournalData.csv";
	private Path CSVpath = Paths.get(CSVpathSTR);
	private String vocabsfile=System.getProperty("user.dir")+"\\data\\VocabTFs.txt";
	private String Keywordsfile=System.getProperty("user.dir")+"\\data\\KeyWords.txt";
	private String Tempvocabsfile=System.getProperty("user.dir")+"\\data\\TempVocabTFs.txt";
	private String TempKeywordsfile=System.getProperty("user.dir")+"\\data\\TempKeyWords.txt";
	private String DefaultDirs=System.getProperty("user.dir")+"\\data\\DefaultDirs.txt";
	private String DatabaseDIR=System.getProperty("user.dir")+"\\data\\db";
	private String DatabaseURL="jdbc:derby:"+DatabaseDIR+";create=true";
	
	List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);
	
	private JPanel contentPane;
	private JTable dirtable;
	private JTable doitable;
	private JTextField wordfield;
	private JTable filterstable;
	private JTable finalfiltertable;
	private JTable allkeytable;
	private JTable allvoctable;
	private JTable selectkeytable;
	private JTable selectvocabtable;
	private JTable selectentertable;
	private JTextField selectvocabsearchtext;
	private JTextField selectkeysearchtext;
	private JTextField allkeysearchword;
	private JTextField allvocabsearchword;
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
		title.setBounds(493, 11, 296, 67);
		toppanel.add(title);
		
		JLabel subtitle = new JLabel("An extensive text analysis and management tool for Scientific Journals.");
		subtitle.setBounds(318, 68, 715, 44);
		toppanel.add(subtitle);
		subtitle.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		
		JTabbedPane maintabpane = new JTabbedPane(JTabbedPane.TOP);
		maintabpane.setBounds(0, 123, 1362, 761);
		contentPane.add(maintabpane);
		
	    File dir = new File(System.getProperty("user.dir")+"\\data");
	    if (!dir.exists()) dir.mkdirs();
	    
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
		
		File f= new File(DefaultDirs);
		try {
			f.createNewFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if (f.exists()) {
			try (Stream<String> stream = Files.lines(Paths.get(DefaultDirs))) {
		        stream.forEach(i -> model.addRow(new Object[]{i}));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
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
				
		        BufferedWriter bw;
				try {
					FileWriter fw = new FileWriter(DefaultDirs);
					bw = new BufferedWriter(fw);
					for(int i = 0;i<dirtable.getRowCount();i++) {
						bw.write((String) dirtable.getValueAt(i, 0).toString()+ System.lineSeparator());
					}
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        
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
				doitablemodel.setRowCount(0);
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
				System.out.println(directoryName.toString());
			}
		});
		retrievedata.setBounds(277, 44, 199, 60);
		parse_files.add(retrievedata);
		
				
		JButton refreshdatabase = new JButton("Refresh Database");
		refreshdatabase.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		refreshdatabase.setBounds(716, 44, 199, 60);
		refreshdatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> filenames=new ArrayList<String>();
				for(int i = 0;i<doitablemodel.getRowCount();i++) {
					filenames.add(doitablemodel.getValueAt(i, 1).toString()+".txt");
				}
				InsertJournal.RemoveFromTXTFolder(filenames, TXTfilesLoc);
				
				File f = new File(DatabaseDIR);
				if(!f.exists()) {
					try {
						InsertJournal.CreateTables(DatabaseURL);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				List<JournalData> sampledata=new ArrayList<JournalData>();
				 
				for(int i = 0;i<doitablemodel.getRowCount();i++) {
					try {
						String Filename=doitablemodel.getValueAt(i, 1).toString();
						String FileDOI=doitablemodel.getValueAt(i, 2).toString();
						if(!InsertJournal.CheckIfFileExists(DatabaseURL, Filename)) {
							sampledata.add(DOIParsing.getJournalData(Filename, FileDOI));
						} else {
							System.out.println("\nFile Exists in database: "+Filename+"\n");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
		        for (int i = 0; i < sampledata.size(); i++) {
		        	try {
						InsertJournal.InsertJournalFun(DatabaseURL, sampledata.get(i));
					} catch (SQLException | ParseException e1) {
						e1.printStackTrace();
					}
		        }
					
		        try {
					InsertJournal.RemoveDeletedEntries(DatabaseURL, TXTfilesLoc);
					InsertJournal.ExportToCSV(DatabaseURL, CSVpathSTR);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);
				List<String> results = new ArrayList<String>();
				File[] files = new File(TXTfilesLoc).listFiles();
				for (File file : files) {
				    if (file.isFile()) {
				    	results.add(file.getAbsolutePath());
				    }
				}
				System.out.println(results.toString());
				try {
					GetKeyWords.WriteToFile(results, vocabsfile, Keywordsfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		parse_files.add(refreshdatabase);
		
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
		vocaball.addTab("Top Keywords from All Journals", null, allkeywords, null);
		allkeywords.setLayout(null);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(10, 106, 1327, 560);
		allkeywords.add(scrollPane_5);
		
		Object[] allkeytable_colnames= {"Word", "Document Frequency", "Term Frequency"};
		DefaultTableModel allkeytablemodel = new DefaultTableModel(null, allkeytable_colnames);
		allkeytable = new JTable(allkeytablemodel);
		scrollPane_5.setViewportView(allkeytable);
		
		JLabel lblSearchInVocabulary_1_1 = new JLabel("Search in Keywords:");
		lblSearchInVocabulary_1_1.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		lblSearchInVocabulary_1_1.setBounds(10, 46, 211, 44);
		allkeywords.add(lblSearchInVocabulary_1_1);
		
		allkeysearchword = new JTextField();
		allkeysearchword.setColumns(10);
		allkeysearchword.setBounds(224, 40, 346, 60);
		allkeywords.add(allkeysearchword);
		
		JButton allkeysearch = new JButton("Search");
		allkeysearch.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allkeysearch.setBounds(580, 40, 99, 60);
		allkeysearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.KeySearchFun(allkeytable, allkeysearchword, vocabsfile, allkeytablemodel);
			}
		});
		allkeywords.add(allkeysearch);
		
		JButton allShowKeywordsFor = new JButton("Show Keywords for All Journals");
		allShowKeywordsFor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allShowKeywordsFor.setBounds(689, 40, 319, 60);
		allShowKeywordsFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.ShowKeywordFun(Keywordsfile, vocabsfile, allkeytablemodel, true);
			}
			
		});
		allkeywords.add(allShowKeywordsFor);
		
		JButton allRefreshKeywordsFor = new JButton("Refresh Keywords for All Journals");
		allRefreshKeywordsFor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allRefreshKeywordsFor.setBounds(1018, 40, 319, 60);
		allRefreshKeywordsFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.RefreshKeywordFun(TXTfilesLoc, vocabsfile, Keywordsfile, allkeytablemodel, true);
			}
		});
		allkeywords.add(allRefreshKeywordsFor);
		
		JPanel allvocab = new JPanel();
		vocaball.addTab("Vocabulary from All Journals", null, allvocab, null);
		allvocab.setLayout(null);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(10, 106, 1327, 560);
		allvocab.add(scrollPane_6);
		
		DefaultTableModel allvoctablemodel = new DefaultTableModel(null, allkeytable_colnames);
		allvoctable = new JTable(allvoctablemodel);
		scrollPane_6.setViewportView(allvoctable);
		
		JLabel lblSearchInVocabulary_1_1_1 = new JLabel("Search in Vocabulary:");
		lblSearchInVocabulary_1_1_1.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		lblSearchInVocabulary_1_1_1.setBounds(10, 46, 211, 44);
		allvocab.add(lblSearchInVocabulary_1_1_1);
		
		allvocabsearchword = new JTextField();
		allvocabsearchword.setColumns(10);
		allvocabsearchword.setBounds(224, 40, 346, 60);
		allvocab.add(allvocabsearchword);
		
		JButton allvocabsearch = new JButton("Search");
		allvocabsearch.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allvocabsearch.setBounds(580, 40, 99, 60);
		allvocabsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.KeySearchFun(allvoctable, allvocabsearchword, vocabsfile, allvoctablemodel);
			}
		});
		allvocab.add(allvocabsearch);
		
		JButton allShowVocabularyFor = new JButton("Show Vocabulary for All Journals");
		allShowVocabularyFor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allShowVocabularyFor.setBounds(689, 40, 319, 60);
		allShowVocabularyFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.ShowKeywordFun(Keywordsfile, vocabsfile, allvoctablemodel, false);
			}
			
		});
		allvocab.add(allShowVocabularyFor);
		
		JButton allRefreshVocabularyFor = new JButton("Refresh Vocabulary for All Journals");
		allRefreshVocabularyFor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		allRefreshVocabularyFor.setBounds(1018, 40, 319, 60);
		allRefreshVocabularyFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.RefreshKeywordFun(TXTfilesLoc, vocabsfile, Keywordsfile, allvoctablemodel, false);
			}
		});
		allvocab.add(allRefreshVocabularyFor);
		
		JTabbedPane vocabselected = new JTabbedPane(JTabbedPane.TOP);
		wordsearch.addTab("Analyze Vocabulary on Selected Journals", null, vocabselected, null);
		
		JPanel enterdocs = new JPanel();
		vocabselected.addTab("Enter Journal Information for Text Analysis", null, enterdocs, null);
		enterdocs.setLayout(null);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(10, 106, 1327, 560);
		enterdocs.add(scrollPane_7);
		
		Object[] selectentertable_columns= {"File Path"};
		DefaultTableModel selectentertable_model=new DefaultTableModel(null, selectentertable_columns);
		selectentertable = new JTable(selectentertable_model);
		scrollPane_7.setViewportView(selectentertable);
		
		//FOR DEMO
//		selectentertable_model.addRow(new Object[] {"E:\\Research Papers\\New folder\\06076275.pdf.txt"});
//		selectentertable_model.addRow(new Object[] {"E:\\Research Papers\\New folder\\Edge-Guided_Dual-Modality_Image_Reconstruction.pdf"});
		//FOR DEMO
		JButton selecttextaddrow = new JButton("Add a row");
		selecttextaddrow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selecttextaddrow.setBounds(320, 35, 319, 60);
		selecttextaddrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JFileChooser f = new JFileChooser();
		        f.setCurrentDirectory(new java.io.File(TXTfilesLoc));
		        f.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		        f.showSaveDialog(null);
				selectentertable_model.addRow(new Object[]{f.getSelectedFile()});
			}
		});
		enterdocs.add(selecttextaddrow);
		
		JButton selecttextproceed = new JButton("Proceed for Analysis");
		selecttextproceed.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selecttextproceed.setBounds(668, 35, 319, 60);
		selecttextproceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> results = new ArrayList<String>();
				for(int i = 0;i<selectentertable.getRowCount();i++) {
					results.add(selectentertable_model.getValueAt(i, 0).toString());
				}
				try {
					GetKeyWords.WriteToFile(results, Tempvocabsfile, TempKeywordsfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				vocabselected.setSelectedIndex(1);
			}
		});
		enterdocs.add(selecttextproceed);
		
		JPanel selectkeywords = new JPanel();
		vocabselected.addTab("Top Keywords from Selected Journals", null, selectkeywords, null);
		selectkeywords.setLayout(null);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(10, 106, 1327, 560);
		selectkeywords.add(scrollPane_8);
		
		DefaultTableModel selectkeytable_model=new DefaultTableModel(null, allkeytable_colnames);
		selectkeytable = new JTable(selectkeytable_model);
		scrollPane_8.setViewportView(selectkeytable);
		
		JLabel lblSearchInVocabulary_1 = new JLabel("Search in Keywords:");
		lblSearchInVocabulary_1.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		lblSearchInVocabulary_1.setBounds(10, 41, 211, 44);
		selectkeywords.add(lblSearchInVocabulary_1);
		
		selectkeysearchtext = new JTextField();
		selectkeysearchtext.setColumns(10);
		selectkeysearchtext.setBounds(224, 35, 346, 60);
		selectkeywords.add(selectkeysearchtext);
		
		JButton selectkeysearch = new JButton("Search");
		selectkeysearch.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectkeysearch.setBounds(580, 35, 99, 60);
		selectkeysearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.KeySearchFun(selectkeytable, selectkeysearchtext, Tempvocabsfile, selectkeytable_model);
			}
			
		});
		selectkeywords.add(selectkeysearch);
		
		JButton btnShowTopKeywords = new JButton("Show Keywords for Selected Journals");
		btnShowTopKeywords.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnShowTopKeywords.setBounds(689, 35, 319, 60);
		btnShowTopKeywords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.ShowKeywordFun(TempKeywordsfile, Tempvocabsfile, selectkeytable_model, true );
			}
		});
		selectkeywords.add(btnShowTopKeywords);
		
		JButton btnRefreshTopKeywords = new JButton("Refresh Keywords for Selected Journals");
		btnRefreshTopKeywords.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnRefreshTopKeywords.setBounds(1018, 35, 319, 60);
		btnRefreshTopKeywords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> results = new ArrayList<String>();
				for(int i = 0;i<selectentertable.getRowCount();i++) {
					results.add((String) selectentertable_model.getValueAt(i, 0));
				}
				try {
					GetKeyWords.WriteToFile(results, Tempvocabsfile, TempKeywordsfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				vocabselected.setSelectedIndex(1);
				SupportFun.ShowKeywordFun(TempKeywordsfile, Tempvocabsfile, selectkeytable_model, true );
			}
		});
		selectkeywords.add(btnRefreshTopKeywords);
		
		JPanel selectvocab = new JPanel();
		vocabselected.addTab("Vocabulary from Selected Journals", null, selectvocab, null);
		selectvocab.setLayout(null);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(10, 106, 1327, 560);
		selectvocab.add(scrollPane_9);
		
		DefaultTableModel selectvocabtable_model=new DefaultTableModel(null, allkeytable_colnames);
		selectvocabtable = new JTable(selectvocabtable_model);
		scrollPane_9.setViewportView(selectvocabtable);
		
		selectvocabsearchtext = new JTextField();
		selectvocabsearchtext.setBounds(224, 35, 346, 60);
		selectvocab.add(selectvocabsearchtext);
		selectvocabsearchtext.setColumns(10);
		
		JButton selectvocabshow = new JButton("Show Vocabulary for Selected Journals");
		selectvocabshow.setBounds(689, 35, 319, 60);
		selectvocabshow.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectvocabshow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.ShowKeywordFun(TempKeywordsfile, Tempvocabsfile, selectvocabtable_model, false );
			}
		});
		selectvocab.add(selectvocabshow);
		
		JButton selectvocabrefresh = new JButton("Refresh Vocabulary for Selected Journals");
		selectvocabrefresh.setBounds(1018, 35, 319, 60);
		selectvocabrefresh.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectvocabrefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> results = new ArrayList<String>();
				for(int i = 0;i<selectentertable.getRowCount();i++) {
					results.add((String) selectentertable_model.getValueAt(i, 0));
				}
				try {
					GetKeyWords.WriteToFile(results, Tempvocabsfile, TempKeywordsfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				vocabselected.setSelectedIndex(1);
				SupportFun.ShowKeywordFun(TempKeywordsfile, Tempvocabsfile, selectvocabtable_model, false );
			}
		});
		selectvocab.add(selectvocabrefresh);
		
		JButton selectvocabsearch = new JButton("Search");
		selectvocabsearch.setBounds(580, 35, 99, 60);
		selectvocabsearch.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		selectvocabsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupportFun.KeySearchFun(selectvocabtable, selectvocabsearchtext, Tempvocabsfile, selectvocabtable_model);
			}
		});
		selectvocab.add(selectvocabsearch);
		
		JLabel lblSearchInVocabulary = new JLabel("Search in Vocabulary:");
		lblSearchInVocabulary.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 22));
		lblSearchInVocabulary.setBounds(10, 41, 211, 44);
		selectvocab.add(lblSearchInVocabulary);
		
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
						System.out.println("ADDED FILTER: "+filterstable.getValueAt(i, 0)+"##"+filterstable.getValueAt(i, 1));
					} catch (Exception e1) {
						//INVALID FILTER PARAMETER
						e1.printStackTrace();
					}				
				}
				List<JournalData> results = ConcurrentSearch.findAll(data, filters, SIZE);
				
				for(JournalData resultiter: results) {
					System.out.println(resultiter.toString());
//					System.out.println(resultiter.getAuthor_firstname());
					finaltablemodel.setRowCount(0);
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
