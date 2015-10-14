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

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.outlet.gui.MainPage;
import com.outlet.objects.Category;
import com.outlet.objects.Item;

public class ItemList extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTable table;
	private JPanel panel;
	private JButton button;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private DefaultTableModel tableModel;
	private ViewImage viewImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemList frame = new ItemList();
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
	public ItemList(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public ItemList() {
		setTitle("Item Listing");
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		String[] columns = Item.HEADER_FIELDS.split(",");
		tableModel = new DefaultTableModel(columns, 0) {
		    @Override
		    public Class<?> getColumnClass(int column) {
		        switch(column) {
		            //case 6: return ImageIcon.class;
		            default: return Object.class;
		        }
		    }
		};;
		List<Item> lists = new Item().getObjects("1=1 ORDER BY PKID");
		for(int i=0;i<lists.size();i++) {
			Item item = lists.get(i);
			Object[] obj = new Object[columns.length];
			obj[0] = i+1;
			obj[1] = item.getPkid();
			obj[2] = item.getItemCode();
			obj[3] = item.getItemName();
			obj[4] = item.getMaCost();
			Category cat = new Category().getObject(item.getCategoryId());
			obj[5] = cat.getCategoryName() + " (" + cat.getCategoryCode() + ")";
			if(item.getImage() == null || item.getImage().length<1) {
				obj[6] = "No Image";
			} else {
				obj[6] = "Image Link..."; 
			}
			//obj[6] = "Image Link...";
			/*if(item.getImage() != null) {
				obj[6] = new ImageIcon(item.getImage());;
				//tableMode
			} else {
				obj[6] = "Empty";
			}*/
			tableModel.addRow(obj);
			
			//table.setRowHeight(table.get, 500);
		}
		
				// Create a new table instance
				table = new JTable(tableModel) {
			        private static final long serialVersionUID = 1L;
		
			        public boolean isCellEditable(int row, int column) {                
			                return false;               
			        };
			        /*public boolean getScrollableTracksViewportWidth()
		            {
		                return getPreferredSize().width < getParent().getWidth();
		            }*/
			    };
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						int col = table.columnAtPoint(new Point(arg0.getX(), arg0.getY()));
						if (col == 6) {
							int row  = table.rowAtPoint(new Point(arg0.getX(), arg0.getY()));
							String val = "";
							try {
								val = table.getValueAt(row, col).toString();
							} catch(Exception ex) {
								val = "";
							}
							if("Image Link...".equalsIgnoreCase(val)) {
								table.setCursor(new Cursor(Cursor.HAND_CURSOR));
							}
			            }
					}
					@Override
					public void mouseExited(MouseEvent e) {
						int col = table.columnAtPoint(new Point(e.getX(), e.getY()));
						if (col != 6) {
			                table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			            } else {
			            	int row  = table.rowAtPoint(new Point(e.getX(), e.getY()));
			            	String val = "";
			            	try {
			            		val = table.getValueAt(row, col).toString();
			            	} catch (Exception ex) {
			            		val = "";
			            	}
			            	if(!"Image Link...".equalsIgnoreCase(val)) {
								table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
			            }
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						int row = table.rowAtPoint(e.getPoint());
				        int col = table.columnAtPoint(e.getPoint());
				        if (row >= 0 && col >= 0) {
						if(col == 6) {
							//tableModel.removeRow(row);
							String val = "";
							try {
								val = table.getValueAt(row, col).toString();
							} catch(Exception ex) {
								val = "";
							}
							if("Image Link...".equalsIgnoreCase(val) && !val.isEmpty()) {
								String itemCode = table.getValueAt(row, 2).toString();
								Item itm = new Item().getObject(itemCode);
								byte[] image = itm.getImage();
								viewImage = new ViewImage(ItemList.this,image);
								viewImage.show();
								//ItemList.this.setVisible(false);
								
							}
			        	}
				       }
					}
				});
				//table = new JTable( dataValues, columns );
				table.setShowGrid(true);
				table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				//table.setBounds(10, 101, 414, 85);
				table.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
				table.setAutoCreateRowSorter(true);
				int rowHeight = Math.max(table.getRowHeight(), table.getPreferredSize().height);

				//table.setRowHeight(3);
				JScrollPane scrollPane = new JScrollPane(table);
		
		panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
					.addGap(8))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
					.addGap(6))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("");
		panel_1.add(lblNewLabel);
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		button = new JButton("Close");
		panel_2.add(button);
		button.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 	ItemList.this.setVisible(false);
					backPage.setVisibility(true);
			}
		});
		
		panel_3 = new JPanel();
		panel.add(panel_3);
		contentPane.setLayout(gl_contentPane);
		

	}

}
