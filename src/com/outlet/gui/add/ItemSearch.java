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

package com.outlet.gui.add;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.outlet.common.Utilities;
import com.outlet.objects.Item;

public class ItemSearch extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private AddBill backPage;
	private JButton btnNewButton;
	private Border defaultBorder;
	private DefaultTableModel tableModel;
	private JTable table;
	private ItemSearchDetails itemSearchDetails;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemSearch frame = new ItemSearch();
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
	public ItemSearch(AddBill backPage) {
		this();
		this.backPage = backPage;
	}
	public ItemSearch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkSearchCriteria(textField);
			}
		});
		defaultBorder = textField.getBorder();
		textField.setColumns(10);
		
		btnNewButton = new JButton("Search");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()) {
					return;
				} else {
					String keyword = textField.getText();
					String condition = "item_name LIKE '%" + keyword + "%' OR item_code LIKE '%" 
					+ keyword + "%' ORDER BY PKID";
					List<Item> lstItem = Item.getObjects(condition);
					if(lstItem == null || lstItem.isEmpty()){
						;// update the status bar 
					} else {
						
						for(int i=0;i<tableModel.getRowCount();i++)
						{
							tableModel.removeRow(i);
						}
						String[] columns = Item.HEADER_FIELDS.split(",");
						tableModel.setColumnCount(columns.length);
						for(int cnt=0; cnt<columns.length;cnt++) {
							table.getColumnModel().getColumn(cnt).setHeaderValue(columns[cnt]);
						}	
						for(int i=0;i<lstItem.size();i++) {
							Item item = lstItem.get(i);
							String[] data = Utilities.convertToCSV(item, ",","true","convertCategory");
							data[0] = String.valueOf(i+1) + "," + data[0];
							tableModel.addRow(data[0].split(","));
						}
					}
				}
			}
		});
		
		JLabel lblKeyword = new JLabel("Keyword :");
		
		JPanel panel_1 = new JPanel();
		
		
		Vector vecTree = new Vector();
		//vecTree.add("s");
		tableModel = new DefaultTableModel(vecTree, 0);
		table = new JTable(tableModel) {
		        private static final long serialVersionUID = 1L;
		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
		        int col = table.columnAtPoint(arg0.getPoint());
		        if (row >= 0 && col >= 0) {
		           //System.out.println(table.getValueAt(row, 1).toString());
		        	OpenItemDetails(Integer.valueOf(table.getValueAt(row, 1).toString()));
		        }
			}
		});
		table.setShowGrid(true);
		table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//table.setBounds(10, 101, 414, 85);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 182, 641, 287);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JButton btnBack = new JButton("Back");
		panel_1.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeItemSearch();
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
					.addGap(6)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblKeyword)
					.addGap(2)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(4))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKeyword)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	private int closeItemSearch() {
		if(backPage == null) {
			return 0;
		}
		ItemSearch.this.setVisible(false);
		backPage.show(true);
		return 0;
	}
	private void checkSearchCriteria(JTextField textField) {
		try {
			if(textField.getText() != null && !textField.getText().isEmpty()
					&& textField.getText().length()>2) {
				textField.setBorder(defaultBorder);
				btnNewButton.setEnabled(true);
			} else {
				btnNewButton.setEnabled(false);
				textField.setBorder(defaultBorder);
				textField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private void OpenItemDetails(Integer pkid) {
		//if(itemSearchDetails == null) {
			itemSearchDetails = new ItemSearchDetails(ItemSearch.this, pkid,backPage);
			itemSearchDetails.show();
			ItemSearch.this.setVisible(false);
		/*} else {
			itemSearchDetails.setVisible(true);
			ItemSearch.this.setVisible(false);
		}*/
		
	}
}
