package com.journalbuddy.SwingUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class JournalBuddyFront extends JFrame {

	private JPanel contentPane;
	private JTable dirtable;
	private JTable table;

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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 123, 1362, 761);
		contentPane.add(tabbedPane);
		
		JPanel add_dir = new JPanel();
		tabbedPane.addTab("Add New Directories", null, add_dir, null);
		add_dir.setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel(); 
		model.addColumn("Directories"); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(103, 99, 1168, 588);
		add_dir.add(scrollPane);
		dirtable = new JTable(model);
		scrollPane.setViewportView(dirtable);
		
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
		tabbedPane.addTab("Review Journal Files and DOI", null, parse_files, null);
		parse_files.setLayout(null);
		
		table = new JTable();
		table.setBounds(26, 145, 1307, 550);
		parse_files.add(table);
		
		JButton retrievedata = new JButton("Retrieve Data");
		retrievedata.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		retrievedata.setBounds(277, 44, 199, 60);
		parse_files.add(retrievedata);
		
		JButton fetchtable = new JButton("Fetch Table");
		fetchtable.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		fetchtable.setBounds(716, 44, 199, 60);
		parse_files.add(fetchtable);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_4, null);
	}
}
