/*
* This file is part of BillMining.
* 
* BillMining is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License version 3
* as published by the Free Software Foundation.
*
* BillMining is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.  <http://www.gnu.org/licenses/>
*
* Author(s):
*
* © 2015 Kasra Madadipouya <kasra@madadipouya.com>
*/

package com.outlet.gui.report;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import com.outlet.common.Utilities;
import com.outlet.gui.MainPage;

import jsyntaxpane.DefaultSyntaxKit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

public class DeveloperQuery extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lblNewLabel;
	private JEditorPane codeEditor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeveloperQuery frame = new DeveloperQuery();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DeveloperQuery(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public DeveloperQuery() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("Execute Query");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = codeEditor.getText();
					ArrayList sqlResult = (ArrayList) Utilities.executeDirectSelectSql(query);
					LinkedHashMap tree = (LinkedHashMap) sqlResult.get(0);
					Vector vecTree = new Vector(tree.keySet());
					// Header
					/*for (int cnt = 0; cnt < arr.size(); cnt++)
					{			
						Object str = (Object) arr.get(cnt);
						//System.out.println(str);
						
					}*/	
					/*tableModel = new DefaultTableModel(vecTree, 0);
					*/
					while(tableModel.getRowCount()>0) {
						for(int i=0;i<tableModel.getRowCount();i++)
						{
							tableModel.removeRow(0);
						}
					}
					tableModel.setColumnCount(vecTree.size());
					for(int cnt=0; cnt<vecTree.size();cnt++) {
						table.getColumnModel().getColumn(cnt).setHeaderValue(vecTree.get(cnt));
					}	
					for (int i=0; i < sqlResult.size(); i++)
					{
						LinkedHashMap tree1 = (LinkedHashMap) sqlResult.get(i);
						Vector vec = new Vector(tree1.values());
						tableModel.addRow(vec);
						/*for (int j=0; j < vec.size(); j++)
						{			
							Object str = (Object) vec.get(j);
							if (str==null) str = "NULL";
			 			}*/
					}
					// Create a new table instance
					/*table = new JTable(tableModel) {
					        private static final long serialVersionUID = 1L;
					        public boolean isCellEditable(int row, int column) {                
					                return false;               
					        };
					};
					table.setShowGrid(true);
					table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
					//table.setBounds(10, 101, 414, 85);
					table.setAutoCreateRowSorter(true);
					JScrollPane scrollPane = new JScrollPane(table);
					contentPane.add(scrollPane);*/
					lblNewLabel.setText("Query has run successfully! [Rows : " + sqlResult.size() + ", Columns : " +vecTree.size() + "]");
				}catch(Exception ex) {
					ex.printStackTrace();
					lblNewLabel.setText(ex.getMessage());
				}
				
			}
		});
		panel_2.add(btnNewButton);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeveloperQuery.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		panel_3.add(btnCancel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		Vector vecTree = new Vector();
		//vecTree.add("s");
		tableModel = new DefaultTableModel(vecTree, 0);
		table = new JTable(tableModel) {
		        private static final long serialVersionUID = 1L;
		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		};
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//table.setBounds(10, 101, 414, 85);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.add(lblNewLabel);

		final Container c =  this.getContentPane();

			DefaultSyntaxKit.initKit();
	        contentPane.setLayout(null);
	        c.doLayout();
	        
	        	        codeEditor = new JEditorPane();
	        	        JScrollPane scrPane = new JScrollPane(codeEditor);
	        	        panel.add(scrPane);
	        	        scrPane.setBounds(0, 0, 100, 100);
	        	        codeEditor.setContentType("text/sql");
	        	        codeEditor.setText("select * from ");
	        GroupLayout gl_contentPane = new GroupLayout(contentPane);
	        gl_contentPane.setHorizontalGroup(
	        	gl_contentPane.createParallelGroup(Alignment.LEADING)
	        		.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
	        		.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
	        		.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
	        		.addComponent(panel, GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
	        );
	        gl_contentPane.setVerticalGroup(
	        	gl_contentPane.createParallelGroup(Alignment.LEADING)
	        		.addGroup(gl_contentPane.createSequentialGroup()
	        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
	        			.addPreferredGap(ComponentPlacement.RELATED)
	        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
	        			.addGap(4)
	        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
	        			.addGap(11)
	        			.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
	        );
	        contentPane.setLayout(gl_contentPane);
	}
}
