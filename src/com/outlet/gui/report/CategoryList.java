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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.outlet.common.Utilities;
import com.outlet.gui.MainPage;
import com.outlet.objects.Category;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class CategoryList extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTable table;
	private JButton btnCloe;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryList frame = new CategoryList();
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
	public CategoryList(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public CategoryList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		String[] columns = Category.HEADER_FIELDS.split(",");
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		List<Category> lists = Category.getObjects("1=1 ORDER BY PKID");
		for(int i=0;i<lists.size();i++) {
			Category cat = lists.get(i);
			String[] data = Utilities.convertToCSV(cat,",","true");
			data[0] = String.valueOf(i+1) + "," + data[0];
			tableModel.addRow(data[0].split(","));
		}

		// Create a new table instance
		table = new JTable(tableModel) {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		//table = new JTable( dataValues, columns );
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//table.setBounds(10, 101, 414, 85);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		
		panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
		);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnCloe = new JButton("Close");
		panel_2.add(btnCloe);
		btnCloe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CategoryList.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		
		panel_3 = new JPanel();
		panel.add(panel_3);
		contentPane.setLayout(gl_contentPane);
	}

}
