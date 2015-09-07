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

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.outlet.common.DocRow;
import com.outlet.objects.Category;
import com.outlet.objects.Item;

public class ItemSearchDetails extends JFrame {

	private JPanel contentPane;
	private ItemSearch backPage;
	private AddBill savePage;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;
	private JSpinner spinner_1;
	private JSpinner spinner;
	private Integer itemId;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemSearchDetails frame = new ItemSearchDetails();
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
	public ItemSearchDetails(ItemSearch backPage, Integer pkid, AddBill savePage) {
		this();
		this.backPage = backPage;
		this.savePage = savePage;
		Item itm = Item.getObject(pkid);
		if(itm != null) {
			this.itemId = pkid;
			textField.setText(itm.getItemName());
			textField_2.setText(itm.getItemCode());
			Category cat = Category.getObject(itm.getCategoryId());
			textField_1.setText(cat.getCategoryName() + " (" + cat.getCategoryCode() + ")");
			spinner.setValue(itm.getMaCost());
			spinner_1.setValue(1);
			
		}
		
	}
	public ItemSearchDetails(ItemSearch backPage, DocRow doc, AddBill savePage) {
		this();
		this.backPage = backPage;
		this.savePage = savePage;
		//Item itm = Item.getObject(pkid);
		if(doc != null) {
			this.itemId = doc.getItemPKid();
			textField.setText(doc.getItemName());
			textField_2.setText(doc.getItemCode());
			Item itm = Item.getObject(doc.getItemPKid());
			Category cat = Category.getObject(itm.getCategoryId());
			textField_1.setText(cat.getCategoryName() + " (" + cat.getCategoryCode() + ")");
			spinner.setValue(doc.getItemCost());
			spinner_1.setValue(doc.getQty());
			
		}
		
	}
	public ItemSearchDetails() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel label = new JLabel("Item Name :");
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("Item Code :");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		JLabel lblItemQuantity = new JLabel("Item Quantity :");
		
		spinner_1 = new JSpinner();
		
		JLabel label_2 = new JLabel("Item Category :");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		
		JLabel label_3 = new JLabel("Item Cost :");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(43)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(132)
							.addComponent(spinner_1, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
							.addGap(191))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(132)
							.addComponent(spinner, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
							.addGap(191))
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblItemQuantity, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addGap(8))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(6)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(3)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_2))
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(40)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(9)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(31)
							.addComponent(lblItemQuantity, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))))
		);
		panel_4.setLayout(gl_panel_4);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DocRow row = new DocRow();
				row.setItemPKid(itemId);
				row.setItemCode(textField_2.getText());
				row.setItemName(textField.getText());
				BigDecimal cost = new BigDecimal(spinner.getValue().toString());
				row.setItemCost(cost);
				BigDecimal qty = new BigDecimal(spinner_1.getValue().toString());
				row.setQty(qty);
				savePage.setDocRow(row);
				savePage.updateTable();
				savePage.recalculateBillAmount();
				savePage.updateSaveBtn();
				savePage.lblNewLabel.setText("Item Added-Edited Successfully!");
				ItemSearchDetails.this.setVisible(false);
				savePage.show(true);
			}
		});
		panel_1.add(btnSave);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ItemSearchDetails.this.setVisible(false);
				if(backPage != null) {
					backPage.show(true);
				} else {
					savePage.show(true);
				}
			}
		});
		panel_3.add(btnNewButton);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
					.addGap(6))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
