package com.outlet.gui.add;

import java.awt.BorderLayout;
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
import com.outlet.objects.Outlet;

import javax.swing.JLabel;
import javax.swing.JComboBox;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;

public class AddOutlet extends JFrame {

	private JPanel contentPane;
	private MainPage backPage;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox comboBox;
	private JLabel label;
	private JButton btnNewButton;
	private Border defaultBorder;
	private JDatePickerImpl datePicker;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddOutlet frame = new AddOutlet();
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
	public AddOutlet(MainPage backPage) {
		this();
		this.backPage = backPage;
	}
	public AddOutlet() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 317);
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
		
		JSeparator separator = new JSeparator();
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				checkDuplicate();
			}
			@Override
			public void focusLost(FocusEvent e) {
				checkDuplicate();
			}
		});
		defaultBorder = textField_1.getBorder();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				checkDuplicate();
			}
		});
		textField_1.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				checkDuplicate();
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		JLabel lblCompany = new JLabel("Company :");
		
		JLabel lblNewLabel = new JLabel("Outlet Name :");
		
		JLabel lblNewLabel_1 = new JLabel("Outlet Code :");
		
		JLabel lblNewLabel_2 = new JLabel("Outlet Address :");
		
		JLabel lblCreationDate = new JLabel("Date Created :");
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnNewButton = new JButton("Save");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Outlet outlet = new Outlet();
				Company comp = getCompany(comboBox);
				if(comp != null) {
					outlet.setCompanyPkid(comp.getPkid());
				}
				/*outlet.setDateCreated(dateCreated);*/
				Date selectedDate = (Date) datePicker.getModel().getValue();
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			    String reportDate = df.format(selectedDate);
				outlet.setDateCreated(Utilities.getTime(reportDate));
				outlet.setOutletAddress(textField_2.getText());
				outlet.setOutletCode(textField_1.getText());
				outlet.setOutletName(textField.getText());
				try {
					Outlet.setObject(outlet);
				} catch(Exception ex) {
					ex.printStackTrace();
					label.setText("Failed to add outlet!");
				}
				label.setText("Outlet successfully added!");
				reset();
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		panel_3.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddOutlet.this.setVisible(false);
				backPage.setVisibility(true);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
					.addGap(2))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
					.addGap(2))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCompany, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(comboBox, 0, 197, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCreationDate, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(datePicker, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
					.addGap(22))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCompany))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED, 3, Short.MAX_VALUE))
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.RELATED, 3, Short.MAX_VALUE))
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCreationDate)
							.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE))
						.addComponent(datePicker, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(4))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		List<Company> comp = Company.getObjects("1=1 ORDER BY PKID");
		if(comp != null && !comp.isEmpty()) {
			for(int i=0;i<comp.size();i++) {
				Company oneComp = comp.get(i);
				comboBox.addItem(oneComp.getCompanyName() + " (" + oneComp.getCompanyCode() + ")");
			}
		}
	}
	private void checkDuplicate() {
		
		String outletCode = textField_1.getText();
		if(Utilities.isNullOrEmpty(outletCode)) {
			outletCode = "-1";
		}
		Company comp = getCompany(comboBox);
		if(comp != null) {
			List<Outlet> outletResult = Outlet.getObjects("company_pkid = " + comp.getPkid()
			+ " AND outlet_code ='" + outletCode + "'");
			if(!outletResult.isEmpty()) {
				textField_1.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
				label.setText("Duplicate Outlet Code!");
				btnNewButton.setEnabled(false);
			} else {
				textField_1.setBorder(defaultBorder);
				label.setText("");
				btnNewButton.setEnabled(true);
			}
		}
		//}	
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
	private void reset() {
		comboBox.setSelectedIndex(0);
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
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
