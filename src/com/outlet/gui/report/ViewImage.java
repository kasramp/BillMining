package com.outlet.gui.report;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ViewImage extends JFrame {

	private JPanel contentPane;
	private ItemList backPage;
	private byte[] image;
	private JLabel label;
	private int i = 0;
	private JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewImage frame = new ViewImage();
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
	public ViewImage(ItemList backPage, byte[] image){
		this();
		this.backPage = backPage;
		this.image = image;
		label.setIcon(resizeImage(image, label));
		//label.setIcon(new ImageIcon(image));
		
	}
	public ViewImage() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
			}
		});
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 332);
		contentPane = new JPanel();
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if(image != null) {
					label.setIcon(resizeImage(image,label));
					//label.setSize(panel.getHeight(), height);
				}
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if(image != null) {
					label.setIcon(resizeImage(image,label));
					//label.setSize(panel.getHeight(), height);
				}
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
				if(image != null) {
					label.setIcon(resizeImage(image,label));
					//label.setSize(panel.getHeight(), height);
				}
			}
		});
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		label = new JLabel("");
		panel.add(label);
		label.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
			}
		});
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(panel);
	}
	public ImageIcon resizeImage(byte[] imageBytes, JLabel lblNewLabel){ 
		ImageIcon MyImage = new ImageIcon(imageBytes); 
		Image img = MyImage.getImage(); 
		Image newImage = null;
		if(lblNewLabel.getWidth() == 0 && lblNewLabel.getHeight() == 0) {
			newImage = img.getScaledInstance(ViewImage.this.getWidth(), ViewImage.this.getHeight(),Image.SCALE_SMOOTH);
		} else {
			newImage = img.getScaledInstance(ViewImage.this.getWidth(), ViewImage.this.getHeight(),Image.SCALE_SMOOTH);
		}
		ImageIcon image = new ImageIcon(newImage); 
		return image; 
	}

}
