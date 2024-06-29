package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class KartOdeme {

	private JFrame frame;
	private static Connection conn;
	private JTextField cardField;
	private JTextField monthField;
	private JTextField cvvField;
	private long id;
	private JTextField yearField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KartOdeme window = new KartOdeme();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KartOdeme() {
		initialize();
	}
	public KartOdeme(Connection parent_conn, long id) {
		this.id = id;
		conn = parent_conn;
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton payButton = new JButton("\u00D6deme");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cardNumber = cardField.getText();
				String month = monthField.getText();
				String year = yearField.getText();
				String date = month + '/' + year;
				String cvv = cvvField.getText();
				if(cardNumber.isEmpty() || date.isEmpty() || cvv.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Boş alan bırakmayınız.");
				}
				else if(cardNumber.length() != 16) {
					JOptionPane.showMessageDialog(null, "Kart numarası 16 rakamdan oluşmalıdır.");
				}
				else if(cvv.length() != 3) {
					JOptionPane.showMessageDialog(null, "CVV 3 Rakamdan oluşmalıdır.");
				}
				else if(year.length() != 2 || month.length() != 2) {
					JOptionPane.showMessageDialog(null, "Ay ve Yıl 2 rakamdan oluşmalıdır.");
				}
				else {
					boolean check = authenticate(cardNumber, date, cvv, id);
					if(check) {
						payment(id);
						frame.dispose();
					}
				}
			}
		});
		payButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		payButton.setBounds(304, 120, 120, 30);
		frame.getContentPane().add(payButton);
		
		cardField = new JTextField();
		cardField.setBounds(140, 40, 200, 30);
		frame.getContentPane().add(cardField);
		cardField.setColumns(10);
		
		monthField = new JTextField();
		monthField.setColumns(10);
		monthField.setBounds(140, 81, 30, 30);
		frame.getContentPane().add(monthField);
		
		cvvField = new JTextField();
		cvvField.setColumns(10);
		cvvField.setBounds(280, 81, 60, 30);
		frame.getContentPane().add(cvvField);
		
		JLabel cardLabel = new JLabel("Kart Numaras\u0131:");
		cardLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cardLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardLabel.setBounds(10, 40, 120, 30);
		frame.getContentPane().add(cardLabel);
		
		JLabel dateLabel = new JLabel("Tarih:");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dateLabel.setBounds(33, 81, 97, 30);
		frame.getContentPane().add(dateLabel);
		
		JLabel cvvLabel = new JLabel("CVV:");
		cvvLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		cvvLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cvvLabel.setBounds(232, 81, 38, 30);
		frame.getContentPane().add(cvvLabel);
		
		yearField = new JTextField();
		yearField.setColumns(10);
		yearField.setBounds(180, 81, 30, 30);
		frame.getContentPane().add(yearField);
		
		JLabel lblNewLabel = new JLabel("/");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(150, 82, 5, 30);
		frame.getContentPane().add(lblNewLabel);
	}
	private void payment(long id) {
		String query = "UPDATE MUAYENE SET muayene_odeme = 1 WHERE muayene_hasta_tc = ? and muayene_odeme = 0 and muayene_durum = 1";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			int rowsAffected = statement.executeUpdate();
			if(rowsAffected == 1) {
				JOptionPane.showMessageDialog(null,"Ödeme Gerçekleştirildi.");
			}
			else {
				JOptionPane.showMessageDialog(null, "Ödeme Zaten Gerçekleştirilmiş.");
			}
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "SQL HATA");
		}
	}
	public boolean authenticate(String cardNumber, String date, String cvv, long id) {
		String query = "SELECT banka_kart_no, banka_kart_tarih, banka_kart_cvv FROM BANKA WHERE banka_tc = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			if(rs.next()) {
				if(!cardNumber.equals(rs.getString("banka_kart_no")) || !date.equals(rs.getString("banka_kart_tarih")) || !cvv.equals(rs.getString("banka_kart_cvv"))) {
					JOptionPane.showMessageDialog(null, "Hatalı Bilgi");
				}
				else {
					return true;
				}
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "SQL HATA");
		}
		return false;
	}
	public void showFrame() {
		frame.setVisible(true);
	}

}
