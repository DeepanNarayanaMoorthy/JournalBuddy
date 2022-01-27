package com.journalbuddy.SwingUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.journalbuddy.PDFParser.PDFParserClass;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class JournalBuddyFront extends JFrame {

	private JPanel contentPane;
	private JTable dirtable;
	private JTable doitable;
	private String TXTfilesLoc="E:\\Research Papers\\New folder";
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
		
		JPanel filtering = new JPanel();
		maintabpane.addTab("Filter Journal Data", null, filtering, null);
		
	}
}
