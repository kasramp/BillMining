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

package com.outlet.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import com.outlet.db.DbInit;
import com.outlet.gui.add.AddBill;
import com.outlet.gui.add.AddCategory;
import com.outlet.gui.add.AddCompany;
import com.outlet.gui.add.AddItem;
import com.outlet.gui.add.AddOutlet;
import com.outlet.gui.report.BillMining;
import com.outlet.gui.report.CategoryList;
import com.outlet.gui.report.CompanyList;
import com.outlet.gui.report.DeveloperQuery;
import com.outlet.gui.report.ItemList;
import com.outlet.gui.report.OutletList;

public class MainPage {

	private JFrame frmBillminingmainPage;
	private AddItem item;
	private AddCategory category;
	private AddOutlet outlet;
	private AddCompany company;
	private AddBill bill;
	private ItemList itemLst;
	private CategoryList catLst;
	private OutletList outletLst;
	private CompanyList companyLst;
	private DeveloperQuery developerQuery;
	private BillMining billMining;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						DbInit newDb = new DbInit();
					} catch(Exception ex){
						ex.printStackTrace();
				        JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				        System.exit(-1);
					}
					//newDb.DbInit();
					MainPage window = new MainPage();
					window.frmBillminingmainPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception ex){
			ex.printStackTrace();
		}
		frmBillminingmainPage = new JFrame();
		frmBillminingmainPage.setTitle("BillMining");
		frmBillminingmainPage.setBounds(100, 100, 450, 300);
		frmBillminingmainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBillminingmainPage.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_5 = new JPanel();
		frmBillminingmainPage.getContentPane().add(panel_5);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnNewButton_2 = new JButton("Add Outlet");
		
		JButton btnOutletList = new JButton("Outlet Listing");
		panel_5.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnNewButton = new JButton("Add Item");
		
		JButton btnItemList = new JButton("Item Listing");
		panel_5.add(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
				.addComponent(btnItemList, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnItemList)
					.addGap(31))
		);
		panel.setLayout(gl_panel);
		btnItemList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openItemListing();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAddItem();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnNewButton_1 = new JButton("Add Category");
		
		JButton btnCategoryList = new JButton("Category Listing");
		panel_5.add(panel_1);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
						.addComponent(btnCategoryList, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCategoryList)
					.addGap(31))
		);
		panel_1.setLayout(gl_panel_1);
		btnCategoryList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCategoryListing();
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddCategory();
			}
		});
		panel_5.add(panel_2);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(btnOutletList, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
				.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(32)
					.addComponent(btnNewButton_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnOutletList)
					.addGap(31))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnAddCompany = new JButton("Add Company");
		
		JButton btnCompanyList = new JButton("Company Listing");
		panel_5.add(panel_3);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(btnAddCompany, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
				.addComponent(btnCompanyList, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(31)
					.addComponent(btnAddCompany)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCompanyList)
					.addGap(32))
		);
		panel_3.setLayout(gl_panel_3);
		btnCompanyList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCompanyList();
			}
		});
		btnAddCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddCompany();
			}
		});
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnAddBill = new JButton("Add Bill");
		panel_5.add(panel_4);
		
		JButton button = new JButton("Bill Mining");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openBillMining();
			}
		});
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(btnAddBill, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
				.addComponent(button, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(31)
					.addComponent(btnAddBill)
					.addGap(12)
					.addComponent(button))
		);
		panel_4.setLayout(gl_panel_4);
		btnAddBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddBill();
			}
		});
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.add(panel_6);
		
		JButton btnNewButton_3 = new JButton("Run Query");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openDeveloperQuery();
			}
		});
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGap(31)
					.addComponent(btnNewButton_3)
					.addContainerGap(66, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		btnOutletList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openOutletList();
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddOutlet();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frmBillminingmainPage.setJMenuBar(menuBar);
		
		JMenu mnNewMenu_5 = new JMenu("Bill");
		menuBar.add(mnNewMenu_5);
		
		JMenuItem mntmAddBill = new JMenuItem("Add Bill");
		mntmAddBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openAddBill();
			}
		});
		mnNewMenu_5.add(mntmAddBill);
		
		JSeparator separator_4 = new JSeparator();
		mnNewMenu_5.add(separator_4);
		
		JMenuItem mntmBillMining = new JMenuItem("Bill Mining");
		mntmBillMining.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openBillMining();
			}
		});
		mnNewMenu_5.add(mntmBillMining);
		
		JMenu mnNewMenu = new JMenu("Item");
		mnNewMenu.setMnemonic('I');
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Add Item");
		
		
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openAddItem();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Item Listing");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openItemListing();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Category");
		mnNewMenu_1.setMnemonic('A');
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Add Category");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openAddCategory();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JSeparator separator_1 = new JSeparator();
		mnNewMenu_1.add(separator_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Category Listing");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openCategoryListing();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_2 = new JMenu("Outlet");
		mnNewMenu_2.setMnemonic('O');
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Add Outlet");
		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openAddOutlet();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
		JSeparator separator_2 = new JSeparator();
		mnNewMenu_2.add(separator_2);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Outlet Listing");
		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openOutletList();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_3 = new JMenu("Company");
		mnNewMenu_3.setMnemonic('C');
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Add Company");
		mntmNewMenuItem_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openAddCompany();
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);
		
		JSeparator separator_3 = new JSeparator();
		mnNewMenu_3.add(separator_3);
		
		JMenuItem mntmCompanyListing = new JMenuItem("Company Listing");
		mntmCompanyListing.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openCompanyList();
			}
		});
		mnNewMenu_3.add(mntmCompanyListing);
		
		JMenu mnNewMenu_4 = new JMenu("Developer");
		mnNewMenu_4.setMnemonic('D');
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Run Query");
		mntmNewMenuItem_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				openDeveloperQuery();
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_7);
	}
	public void setVisibility(boolean val) {
		MainPage.this.frmBillminingmainPage.setVisible(val);
	}
	private void openAddItem(){
		if(item == null) {
			item = new  AddItem(MainPage.this);
			item.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			item.addRefreshCategory();
			item.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
	private void openItemListing() {
		//if(itemLst == null) {
			itemLst = new  ItemList(MainPage.this);
			itemLst.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		/*} else {
			itemLst.setVisible(true);
			MainPage.this.frame.setVisible(false);
		}*/
	}
	private void openAddCategory() {
		if(category == null) {
			category = new AddCategory(MainPage.this);
			category.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			category.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
	private void openCategoryListing() {
		//if(catLst == null) {
			catLst = new  CategoryList(MainPage.this);
			catLst.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		/*} else {
			catLst.setVisible(true);
			MainPage.this.frame.setVisible(false);
		}*/
	}
	private void openAddOutlet() {
		if(outlet == null) {
			outlet = new AddOutlet(MainPage.this);
			outlet.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			outlet.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
	private void openOutletList() {
		//if(outletLst == null) {
			outletLst = new OutletList(MainPage.this);
			outletLst.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		/*} else {
			outletLst.setVisible(true);
			MainPage.this.frame.setVisible(false);
		}*/
	}
	private void openAddCompany() {
		if(company == null) {
			company = new AddCompany(MainPage.this);
			company.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			company.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}		
	}
	private void openCompanyList() {
		//if(outletLst == null) {
		companyLst = new CompanyList(MainPage.this);
		companyLst.show();
		MainPage.this.frmBillminingmainPage.setVisible(false);
		/*} else {
			outletLst.setVisible(true);
			MainPage.this.frame.setVisible(false);
		}*/
	}
	private void openDeveloperQuery() {
		if(developerQuery == null) {
			developerQuery = new DeveloperQuery(MainPage.this);
			developerQuery.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			developerQuery.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
	private void openAddBill() {
		if(bill == null) {
			bill = new AddBill(MainPage.this);
			bill.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			bill.loadCompany();
			bill.loadOutlet();
			bill.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
	private void openBillMining() {
		if(billMining == null) {
			billMining = new BillMining(MainPage.this);
			billMining.show();
			MainPage.this.frmBillminingmainPage.setVisible(false);
		} else {
			billMining.setVisible(true);
			MainPage.this.frmBillminingmainPage.setVisible(false);
		}
	}
}
