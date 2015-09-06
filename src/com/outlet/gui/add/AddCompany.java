package com.outlet.gui.add;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.outlet.common.DateLabelFormatter;
import com.outlet.common.Utilities;
import com.outlet.gui.MainPage;
import com.outlet.objects.Company;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;

public class AddCompany extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel label;
	private JButton btnNewButton;
	private Border defaultBorder;
	private JDatePickerImpl datePicker;
	private JDatePanelImpl datePanel;
	private UtilDateModel model;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCompany frame = new AddCompany();
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
	public AddCompany(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public AddCompany() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 392, 300);
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
		datePanel = new JDatePanelImpl(model, p);
		
		label = new JLabel("");
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSeparator separator = new JSeparator();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblCompanyName = new JLabel("Company Name :");
		
		textField_2 = new JTextField();
		defaultBorder = textField_2.getBorder();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkDuplicate();
			}
		});
		textField_3.setColumns(10);
		
		JLabel lblCompanyCode_1 = new JLabel("Company Code :");
		
		JLabel lblDateCreated_1 = new JLabel("Date Created :");
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addGap(10))
				.addComponent(label, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(separator)
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblCompanyName, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblCompanyCode_1, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
						.addComponent(lblDateCreated_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(132)
							.addComponent(datePicker, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
					.addGap(8))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCompanyName))
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCompanyCode_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(6)
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(1)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDateCreated_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(datePicker, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnNewButton = new JButton("Save");
		panel_3.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		panel_3.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCompany.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Company comp = new Company();
				comp.setCompanyCode(textField_3.getText());
				comp.setCompanyName(textField_2.getText());
				//comp.setDateCreated("");
				Date selectedDate = (Date) datePicker.getModel().getValue();
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			    String reportDate = df.format(selectedDate);
				comp.setDateCreated(Utilities.getTime(reportDate));
				
				try {
					Company.setObject(comp);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// Error occurs
					label.setText("Failed to add company!");
					
				}
				// If successful, reset the fields
				label.setText("Company successfully added!");
				reset();
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
	private void checkDuplicate() {
		//List<Company> rtnResult = Company.getObjects("company_code = '" + textField_3.getText() + "'");
		Company comp = Company.getObject(textField_3.getText());
		if(comp != null) {
			textField_3.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
			label.setText("Duplicate Company Code!");
			btnNewButton.setEnabled(false);
		} else {
			textField_3.setBorder(defaultBorder);
			btnNewButton.setEnabled(true);
			label.setText("");
		}	
	}
	private void reset() {
		textField_2.setText("");
		textField_3.setText("");
		Calendar calendar = Calendar.getInstance();	 
		java.util.Date now = calendar.getTime();
		model = new UtilDateModel(now);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
}
