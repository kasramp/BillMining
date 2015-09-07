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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.outlet.common.DateLabelFormatter;
import com.outlet.common.DocRow;
import com.outlet.common.Utilities;
import com.outlet.gui.MainPage;
import com.outlet.objects.BillIndex;
import com.outlet.objects.BillItem;
import com.outlet.objects.Company;
import com.outlet.objects.Item;
import com.outlet.objects.Outlet;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AddBill extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private MainPage backPage;
	private UtilDateModel model;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private ItemSearch itmSearch;
	private DocRow row;
	private DefaultTableModel tableModel;
	private ItemSearchDetails itmSearchDetails;
	private Border defaultBorder;
	private JButton btnSave;
	private JDatePickerImpl datePicker;
	public JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBill frame = new AddBill();
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
	public AddBill(MainPage backPage) {
		this();
		this.row = new DocRow();
		this.backPage = backPage;
	}
	public AddBill() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		Calendar calendar = Calendar.getInstance();	 
		java.util.Date now = calendar.getTime();
		model = new UtilDateModel(now);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		comboBox = new JComboBox();
		comboBox_1 = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Company oneComp = getCompany(comboBox);
				if(oneComp != null) {
					comboBox_1.removeAllItems();
					List<Outlet> lstOutlet = Outlet.getObjects("company_pkid = " + oneComp.getPkid());
					if(lstOutlet != null && !lstOutlet.isEmpty() && lstOutlet.size()>0) {
						for(int i=0;i<lstOutlet.size();i++) {
							Outlet oneOutlet = lstOutlet.get(i);
							comboBox_1.addItem(oneOutlet.getOutletName() + " (" + oneOutlet.getOutletCode() + ")");
						}
					}
				}
			}
		});
		//comboBox_1.setBounds(122, 62, 202, 20);
		//contentPane.add(comboBox_1);
		List<Company> comp = Company.getObjects("1=1 ORDER BY PKID");
		if(comp != null && !comp.isEmpty()) {
			for(int i=0;i<comp.size();i++) {
				Company oneComp = comp.get(i);
				if(Outlet.getObjects("company_pkid = " + oneComp.getPkid()).size() > 0) {
					comboBox.addItem(oneComp.getCompanyName() + " (" + oneComp.getCompanyCode() + ")");
			}
		}
		comboBox.setBounds(127, 62, 202, 20);
		contentPane.add(comboBox);
		
		JLabel lblOutlet = new JLabel("Outlet :");
		//lblOutlet.setBounds(10, 87, 46, 14);
		lblOutlet.setBounds(26, 87, 100, 14);
		contentPane.add(lblOutlet);
		
		Company oneComp = getCompany(comboBox);
		if(oneComp != null) {
			List<Outlet> lstOutlet = Outlet.getObjects("company_pkid = " + oneComp.getPkid());
			if(lstOutlet != null && !lstOutlet.isEmpty() && lstOutlet.size()>0) {
				for(int i=0;i<lstOutlet.size();i++) {
					Outlet oneOutlet = lstOutlet.get(i);
					comboBox_1.addItem(oneOutlet.getOutletName() + " (" + oneOutlet.getOutletCode() + ")");
				}
				}
			}
		}
		/*String columnNames[] = { "Column 1", "Column 2", "Column 3" };
		// Create some data
		String dataValues[][] =
		{
			{ "12", "234", "67" },
			{ "-123", "43", "853" },
			{ "93", "89.2", "109" },
			{ "279", "9033", "3092" }
		};

		// Create a new table instance
		table = new JTable( dataValues, columnNames );
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
		        int col = table.columnAtPoint(arg0.getPoint());
		        if (row >= 0 && col >= 0) {
		           System.out.println(table.getValueAt(row, col).toString());
		        }
			}
		});*/
		Vector vecTree = new Vector();
		//vecTree.add("s");
		tableModel = new DefaultTableModel(DocRow.HEADER_FIELDS_ARR, 0);
		
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
		table.setBounds(10, 162, 414, 89);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
		        int col = table.columnAtPoint(arg0.getPoint());
		        if (row >= 0 && col >= 0) {
		           //System.out.println(col);
		           //System.out.println(table.getValueAt(row, col).toString());
		        	if(col == 6) {
		        		tableModel.removeRow(row);
		        		recalculateBillAmount();
		        		lblNewLabel.setText("Item Removed Successfully!");
		        		updateSaveBtn();
		        	}
		        	else if(col == 5) {
		        		int cnt = 1;
		        		DocRow oneDocRow = new DocRow();
		        		String itemCode = table.getValueAt(row, 1).toString();
		        		String strQty = table.getValueAt(row, 3).toString();
		        		String strCost = table.getValueAt(row, 4).toString();
		        		BigDecimal qty = new BigDecimal(0);
		        		BigDecimal itemCost = new BigDecimal(0);
		        		try {
		        			qty = new BigDecimal(strQty);
		        		} catch(Exception ex) {
		        			qty = new BigDecimal(0);
		        		}
		        		try {
		        			itemCost = new BigDecimal(strCost);
		        		} catch(Exception ex) {
		        			itemCost = new BigDecimal(0);
		        		}
		        		
		        		Item itm = Item.getObject(itemCode);
		        		oneDocRow.setItemPKid(itm.getPkid());
		        		oneDocRow.setItemCode(itm.getItemCode());
		        		oneDocRow.setItemName(itm.getItemName());
		        		oneDocRow.setQty(qty);
		        		oneDocRow.setItemCost(itemCost);
		        		itmSearchDetails = new ItemSearchDetails(null, oneDocRow, AddBill.this);
		        				//new ItemSearch(AddBill.this);
		        		itmSearchDetails.show();
		    			AddBill.this.setVisible(false);
		        	}
		        }
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				int col = table.columnAtPoint(new Point(arg0.getX(), arg0.getY()));
				if (col == 6 || col == 5) {
	                table.setCursor(new Cursor(Cursor.HAND_CURSOR));
	            }
			}
			@Override
			public void mouseExited(MouseEvent e) {
				int col = table.columnAtPoint(new Point(e.getX(), e.getY()));
				if (col != 6 || col != 5) {
	                table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	            }
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(10, 111, 89, 23);
		panel.add(btnAddItem);
		
		JLabel lblCompany = new JLabel("Company :");
		lblCompany.setBounds(10, 56, 73, 14);
		panel.add(lblCompany);
		
		JLabel lblDate = new JLabel("Date :");
		lblDate.setBounds(10, 21, 46, 14);
		panel.add(lblDate);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setLayout(null);
		
		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setBounds(10, 70, 73, 14);
		panel_1.add(lblTotal);
		
		JLabel lblGstAmount = new JLabel("Gst Amount :");
		lblGstAmount.setBounds(10, 40, 89, 14);
		panel_1.add(lblGstAmount);
		JLabel lblBillAmount = new JLabel("Bill Amount :");
		lblBillAmount.setBounds(10, 10, 89, 14);
		panel_1.add(lblBillAmount);
		
		textField_2 = new JTextField();
		textField_2.setBounds(109, 70, 166, 20);
		panel_1.add(textField_2);
		textField_2.setEditable(false);
		textField_2.setText("0");
		textField_2.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(109, 40, 166, 20);
		panel_1.add(textField_1);
		defaultBorder = textField_1.getBorder();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(Character.isDigit(c) || c == KeyEvent.VK_KP_LEFT || c == KeyEvent.VK_KP_RIGHT || 
						c == KeyEvent.VK_TAB ||c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || 
						c == KeyEvent.VK_PERIOD)){
					e.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				checkCost(textField_1);
			}
		});
		textField_1.setText("0");
		textField_1.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(109, 10, 166, 20);
		panel_1.add(textField);
		textField.setEditable(false);
		textField.setText("0");
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BillIndex bill = new BillIndex();
					String strBillAmount = textField.getText();
					String strGstAmt = textField_1.getText();
					String strTotalAmt = textField_2.getText();
					BigDecimal billAmt = new BigDecimal(0);
					BigDecimal gstAmt = new BigDecimal(0);
					BigDecimal totalAmt = new BigDecimal(0);
					try {
						billAmt = new BigDecimal(strBillAmount);
					} catch(Exception ex) {
						billAmt = new BigDecimal(0);
					}
					bill.setBillAmount(billAmt);
					try {
						gstAmt = new BigDecimal(strGstAmt);
					} catch(Exception ex) {
						gstAmt = new BigDecimal(0);
					}
					bill.setGstAmount(gstAmt);
					try {
						totalAmt = new BigDecimal(strTotalAmt);
					} catch(Exception ex) {
						totalAmt = new BigDecimal(0);
					}
					bill.setTotalAmount(totalAmt);
					String value = comboBox_1.getSelectedItem().toString();
					String[] valArr = value.split("[(]");
					value = valArr[1].substring(0,valArr[1].length()-1);
					Outlet outlet = Outlet.getObject(value);
					bill.setCompanyPkid(outlet.getCompanyPkid());
					bill.setOutletPkid(outlet.getPkid());
					Date selectedDate = (Date) datePicker.getModel().getValue();
				    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				    String reportDate = df.format(selectedDate);
					bill.setDateCreated(Utilities.getTime(reportDate));
					Integer indexPkid = BillIndex.setObject(bill);
					//for loop JTable
					for(int i=0;i<tableModel.getRowCount();i++) {
						BillItem billItm = new BillItem();
						billItm.setBillIndexPkid(indexPkid);
						billItm.setGstAmount(new BigDecimal(0));
						String itemCode = table.getValueAt(i, 1).toString();
						Item itm = Item.getObject(itemCode);
						billItm.setItemId(itm.getPkid());
						String strPrice = table.getValueAt(i, 4).toString();
						BigDecimal price = new BigDecimal(0);
						try {
							price = new BigDecimal(strPrice);
						} catch(Exception ex) {
							price = new BigDecimal(0);
						}
						billItm.setPrice(price);
						String strQty = table.getValueAt(i, 3).toString();
						Integer qty = new Integer(0);
						try {
							qty = new Integer(strQty);
						} catch(Exception ex) {
							qty = new Integer(0);
						}
						billItm.setQty(qty);
						BillItem.setObject(billItm); 
					}
					lblNewLabel.setText("Bill Issued Successfully!");
					reset();
				} catch(Exception ex) {
					ex.printStackTrace();
					lblNewLabel.setText("Failed To Issue The Bill!");
				}
			}
		});
		panel_4.add(btnSave);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel_3.add(btnCancel);
		
		JSeparator separator = new JSeparator();
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_5.add(lblNewLabel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(122)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(122)
					.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
					.addGap(3))
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(78)
									.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(15)
									.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
							.addGap(56)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(7)
							.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
		);
		contentPane.setLayout(gl_contentPane);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBill.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openItemSearch();
			}
		});
		updateSaveBtn();
	}
	private Company getCompany(JComboBox comboBox) {
		String value = comboBox.getSelectedItem().toString();
		String[] valArr = value.split("[(]");
		value = valArr[1].substring(0,valArr[1].length()-1);
		/*List<Company> rtnResult = Company.getObjects("company_code = '" + value + "'");
		if(!rtnResult.isEmpty()) {
			Company comp = rtnResult.get(0);*/
		Company comp = Company.getObject(value);
		return comp;
	}
	private void openItemSearch(){
		if(itmSearch == null) {
			itmSearch = new ItemSearch(AddBill.this);
			itmSearch.show();
			AddBill.this.setVisible(false);
		} else {
			itmSearch.setVisible(true);
			AddBill.this.setVisible(false);
		}
	}
	public void setDocRow(DocRow row) {
		this.row = row;
	}
	public void updateTable() {
		if(this.row != null)
		{
			// Remove if item added again (Update instead of Add)
			for(int i=0;i<tableModel.getRowCount();i++)
			{
				//tableModel.removeRow(i);
				String itemCode = table.getValueAt(i, 1).toString();
				if(!Utilities.isNullOrEmpty(itemCode)) {
					if(itemCode.equals(this.row.getItemCode())) {
						tableModel.removeRow(i);
					}
				}
			}
			
			tableModel.setColumnCount(DocRow.HEADER_FIELDS_ARR.length);
			for(int cnt=0; cnt<DocRow.HEADER_FIELDS_ARR.length;cnt++) {
				table.getColumnModel().getColumn(cnt).setHeaderValue(DocRow.HEADER_FIELDS_ARR[cnt]);
			}	
			String[] data = Utilities.convertToCSV(row, ",","true");
			data[0] = String.valueOf(table.getRowCount()+1) + "," + data[0];
			tableModel.addRow(data[0].split(","));
		}
		System.out.println(tableModel.getRowCount());
		this.lblNewLabel.setText("Item Added-Edited Successfully!");
	}
	public void recalculateBillAmount() {
		BigDecimal qty = new BigDecimal(0);
		BigDecimal itemCost = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);
		for(int i=0;i<tableModel.getRowCount();i++)
		{
			String strCost = table.getValueAt(i, 4).toString();
			String strQty = table.getValueAt(i, 3).toString();
			try {
				qty = new BigDecimal(strQty);
			} catch(Exception ex) {
				qty = new BigDecimal(0);
			}
			try {
				itemCost = new BigDecimal(strCost);
			} catch(Exception ex) {
				itemCost = new BigDecimal(0);
			}
			totalAmount = totalAmount.add((itemCost.multiply(qty)));
		}
		textField.setText(totalAmount.toString());
		String strGstAmt = textField_1.getText();
		BigDecimal gstAmt = new BigDecimal(0);
		try {
			gstAmt = new BigDecimal(strGstAmt);
		} catch(Exception ex) {
			gstAmt = new BigDecimal(0);
		}
		textField_2.setText(totalAmount.add(gstAmt).toString());
	}
	private BigDecimal checkCost(JTextField cost) {
		try {
			if(cost.getText() != null && !cost.getText().isEmpty()) {
				cost.setBorder(defaultBorder);
				BigDecimal costVal  = new BigDecimal(cost.getText());
				if(tableModel.getRowCount()>0) {
					btnSave.setEnabled(true);
				}
				recalculateBillAmount();
				lblNewLabel.setText("");
				return costVal;
			} else {
				if(tableModel.getRowCount()>0) {
					btnSave.setEnabled(true);
				}
				lblNewLabel.setText("");
				cost.setBorder(defaultBorder);
				recalculateBillAmount();
				return null;
			}
		} catch(Exception ex) {
			cost.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
			btnSave.setEnabled(false);
			lblNewLabel.setText("Invalid Gst Amount!");
			return null;
		}
	}
	public void updateSaveBtn() {
		if(tableModel.getRowCount()<1) {
			btnSave.setEnabled(false);
		} else {
			checkCost(textField_1);
			//btnSave.setEnabled(true);
		}
	}
	private void reset() {
		comboBox_1.setSelectedIndex(0);
		comboBox.setSelectedIndex(0);
		textField.setText("0");
		textField_1.setText("0");
		textField_2.setText("0");
		//table.removeAll();
		int cnt = tableModel.getRowCount();
		for(int i=0;i<cnt;i++) {
			tableModel.removeRow(0);
		}
		this.updateSaveBtn();
		
	}
}
