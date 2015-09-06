package com.outlet.gui.report;

import java.awt.BorderLayout;
import java.awt.Cursor;
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
import com.outlet.objects.BillMining.RptRow;
import com.outlet.objects.Item;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BillMining extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillMining frame = new BillMining();
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
	public BillMining(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public BillMining() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton button = new JButton("Close");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillMining.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		panel_2.add(button);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		String[] columns = com.outlet.objects.BillMining.HEADER_FIELDS_ARR;
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		List<RptRow> list = com.outlet.objects.BillMining.getMining();
		for(int i=0;i<list.size();i++) {
			RptRow oneRow = list.get(i);
			// Add view item image support
			/*String[] data = Utilities.convertToCSV(oneRow);
			data[0] = String.valueOf(i+1) + "," + data[0];
			tableModel.addRow(data[0].split(","));*/
			
			Object[] obj = new Object[columns.length];
			obj[0] = i;
			obj[1] = oneRow.itemId;
			obj[2] = oneRow.itemCode;
			obj[3] = oneRow.itemName;
			obj[4] = oneRow.optimalPrice;
			obj[5] = oneRow.billNo;
			obj[6] = oneRow.companyNameCode;
			obj[7] = oneRow.outletNameCode;
			obj[8] = oneRow.outletAddress;
			obj[9] = oneRow.dateOfPurchase;
			obj[10] = oneRow.frequencyOfPurchase;
			obj[11] = oneRow.fopWithOptimalPrice;
			obj[12] = oneRow.lastAppearance;
			if(oneRow.image == null || oneRow.image.length<1) {
				obj[13] = "No Image";
			} else {
				obj[13] = "Image Link..."; 
			}
			tableModel.addRow(obj);
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
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());
		        if (row >= 0 && col >= 0) {
				if(col == 13) {
					//tableModel.removeRow(row);
					String val = "";
					try {
						val = table.getValueAt(row, col).toString();
					} catch(Exception ex) {
						val = "";
					}
					if("Image Link...".equalsIgnoreCase(val) && !val.isEmpty()) {
						String itemCode = table.getValueAt(row, 2).toString();
						Item itm = Item.getObject(itemCode);
						byte[] image = itm.getImage();
						ViewImage viewImage = new ViewImage(null,image);
						viewImage.show();
						//ItemList.this.setVisible(false);
						
					}
	        	}
		       }
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				int col = table.columnAtPoint(new Point(arg0.getX(), arg0.getY()));
				if (col == 13) {
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
				if (col != 13) {
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
		});
		//table = new JTable( dataValues, columns );
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//table.setBounds(10, 101, 414, 85);
		table.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE))
					.addGap(5))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(6))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
