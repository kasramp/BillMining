package com.outlet.gui.add;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.outlet.common.Utilities;
import com.outlet.gui.MainPage;
import com.outlet.objects.Category;
import com.outlet.objects.Item;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

public class AddItem extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JComboBox comboBox = new JComboBox();
	private JLabel label;
	private JButton btnSave;
	private Border defaultBorder;
	private JTextField textField;
	private JLabel lblNewLabel;
	private byte[] image = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddItem frame = new AddItem();
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
	public AddItem(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public AddItem() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		List<Category> cat = Category.getObjects("1=1");
		if(cat != null && !cat.isEmpty()) {
			for(int i=0;i<cat.size();i++) {
				Category oneCat = cat.get(i);
				comboBox.addItem(oneCat.getCategoryName() + " (" + oneCat.getCategoryCode() + ")");
				
			}
		}
		
		JSeparator separator = new JSeparator();
		
		label = new JLabel("");
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblItemName_1 = new JLabel("Item Name :");
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		defaultBorder = textField_4.getBorder();
		textField_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkDuplicate();
			}
		});
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.addKeyListener(new KeyAdapter() {
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
				checkCost(textField_5);
			}
		});
		textField_5.setColumns(10);
		
		JLabel lblItemCode_1 = new JLabel("Item Code :");
		
		JLabel lblItemCost_1 = new JLabel("Item Cost :");
		
		JLabel label_1 = new JLabel("Item Category :");
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
					.addGap(8))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
					.addGap(9))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel lblItemPicture = new JLabel("Item Picture :");
		lblItemPicture.setVerticalAlignment(SwingConstants.TOP);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(); 
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"))); 
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Picture(*.jpg, *.gif, *.png)", "jpg","gif","png"); 
				fileChooser.addChoosableFileFilter(filter); 
				int result = fileChooser.showSaveDialog(null); 
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile(); 
					String path = selectedFile.getAbsolutePath(); 
					textField.setText(path);
					lblNewLabel.setIcon(resizeImage(path,lblNewLabel));
					FileInputStream fileInputStream=null;
					image = new byte[(int) selectedFile.length()];
					try {
			            //convert file into array of bytes
						fileInputStream = new FileInputStream(selectedFile);
						fileInputStream.read(image);
						fileInputStream.close(); 
						label.setText("Item Picture (" + selectedFile.getName() + ") Loaded!");
			        }catch(Exception e){
			        	e.printStackTrace();
			        }
					//label.setIcon(ResizeImage(path)); 
					//s = path; 
				} /*else if(result == JFileChooser.CANCEL_OPTION) { 
					System.out.println("No Data"); 
				}*/
				
			}
		});
		
		JPanel panel_4 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblItemName_1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addGap(34)
									.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblItemCode_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblItemPicture)))
									.addGap(24)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
										.addComponent(comboBox, 0, 320, Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnBrowse, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(132)
									.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
								.addComponent(lblItemCost_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))))
					.addGap(4))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblItemName_1))
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblItemCode_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(label_1))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(34)
							.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(lblItemCost_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblItemPicture))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse, 0, 0, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("");
		panel_6.add(lblNewLabel);
		
		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7);
		panel.setLayout(gl_panel);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnSave = new JButton("Save");
		panel_2.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Item item = new Item();
				item.setItemName(textField_3.getText());
				item.setItemCode(textField_4.getText());
				item.setImage(image);
				String value = comboBox.getSelectedItem().toString();
				String[] valArr = value.split("[(]");
				value = valArr[1].substring(0,valArr[1].length()-1);
				Category cat = Category.getObject(value);
				/*List<Category> rtnResult = Category.getObjects("category_code = '" + value + "'");
				if(!rtnResult.isEmpty()) {
					Category cat = rtnResult.get(0);*/
				if(cat != null) {
					item.setCategoryId(cat.getPkid());
				}
				try {
					BigDecimal cost = checkCost(textField_5);
					if(cost != null) {
						item.setMaCost(cost);
					}
				} catch (Exception ex) {
					label.setText("Wrong item cost!");
				}
				try {
					Item.setObject(item);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// Error occurs
					label.setText("Failed to add item!");
					
				}
				// If successful, reset the fields
				label.setText("Item successfully added!");
				reset();
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("Cancel");
		panel_3.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddItem.this.setVisible(false);
				backPage.setVisibility(true);
				//AddItem.this.dispose();
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
	private void checkDuplicate() {
		//List<Item> rtnResult = Item.getObjects("item_code = '" + textField_4.getText() + "'");
		Item itm = Item.getObject(textField_4.getText());
		if(itm != null) {
			textField_4.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
			label.setText("Duplicate Item Code!");
			btnSave.setEnabled(false);
		} else {
			textField_4.setBorder(defaultBorder);
			btnSave.setEnabled(true);
			label.setText("");
		}	
	}
	private BigDecimal checkCost(JTextField cost) {
		try {
			if(cost.getText() != null && !cost.getText().isEmpty()) {
				cost.setBorder(defaultBorder);
				BigDecimal costVal  = new BigDecimal(cost.getText());
				btnSave.setEnabled(true);
				return costVal;
			} else {
				btnSave.setEnabled(false);
				cost.setBorder(defaultBorder);
				return null;
			}
		} catch(Exception ex) {
			cost.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
			btnSave.setEnabled(false);
			return null;
		}
	}
	private void reset() {
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
		comboBox.setSelectedIndex(0);
		textField.setText("");
		lblNewLabel.setIcon(null);
		
	}
	public ImageIcon resizeImage(String imgPath, JLabel lblNewLabel){ 
		ImageIcon MyImage = new ImageIcon(imgPath); 
		Image img = MyImage.getImage(); 
		Image newImage = img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(),Image.SCALE_SMOOTH); 
		ImageIcon image = new ImageIcon(newImage); 
		return image; 
	}

}
