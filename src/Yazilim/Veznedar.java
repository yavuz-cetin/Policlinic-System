package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Veznedar {

	private JFrame frame;
	private JTextField idField;
	private static Connection conn;
	private boolean insurance;
	private int price;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Veznedar window = new Veznedar();
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
	public Veznedar(Connection parent_conn,String kullanici) {
		conn = parent_conn;
		initialize();
	}
	public Veznedar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Veznedar");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		idField = new JTextField();
		idField.setBounds(157, 11, 120, 30);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		JLabel idLabel = new JLabel("T.C. Kimlik No.:");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idLabel.setBounds(27, 9, 120, 30);
		frame.getContentPane().add(idLabel);
		
		JButton exitButton = new JButton("Geri Dön");
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startpage = new StartPage(conn);
				startpage.showFrame();
				frame.dispose();
			}
		});
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		exitButton.setBounds(10, 220, 120, 30);
		frame.getContentPane().add(exitButton);
		
		JLabel priceLabel = new JLabel("Ücret:");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceLabel.setBounds(157, 52, 120, 28);
		frame.getContentPane().add(priceLabel);
		
		JButton priceButton = new JButton("Ücret Sorgula");
		priceButton.setFocusable(false);
		priceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringID = idField.getText();
				if(stringID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alanı Boş Bırakmayınız.");
				}
				else if(stringID.length() != 11) {
					JOptionPane.showMessageDialog(null, "Kimlik Numarası 11 Rakamdan Oluşmalıdır.");
				}
				else {
					long id = Long.parseLong(stringID);
					price = getPrice(id);
					priceLabel.setText("Ücret: "+ price);
				}
			}
		});
		priceButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceButton.setBounds(7, 50, 140, 30);
		frame.getContentPane().add(priceButton);
		
		JLabel insuranceLabel = new JLabel("Sigorta:");
		insuranceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		insuranceLabel.setBounds(157, 91, 120, 30);
		frame.getContentPane().add(insuranceLabel);
		
		JButton insuranceButton = new JButton("Sigorta Sorgula");
		insuranceButton.setFocusable(false);
		insuranceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringID = idField.getText();
				if(stringID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alanı boş bırakmayınız.");
				}
				else if(stringID.length() != 11) {
					JOptionPane.showMessageDialog(null, "Kimlik numarası 11 rakamdan oluşmalıdır.");
				}
				else {
					long id = Long.parseLong(stringID);
					insurance = getInsurance(id);
					if(insurance)
						insuranceLabel.setText("Sigorta: Var");
					else
						insuranceLabel.setText("Sigorta: Yok");
				}
			}
		});
		insuranceButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		insuranceButton.setBounds(7, 91, 140, 30);
		frame.getContentPane().add(insuranceButton);
		
		JLabel sumLabel = new JLabel("Toplam:");
		sumLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sumLabel.setBounds(157, 132, 120, 30);
		frame.getContentPane().add(sumLabel);
		
		JButton sumButton = new JButton("Toplam:");
		sumButton.setFocusable(false);
		sumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(insurance) {
					sumLabel.setText("Toplam: " + price/2);
				}
				else {
					sumLabel.setText("Toplam: " + price);
				}
			}
		});
		sumButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sumButton.setBounds(7, 132, 140, 30);
		frame.getContentPane().add(sumButton);
		
		JButton cashButton = new JButton("Nakit Ödeme");
		cashButton.setFocusable(false);
		cashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringID = idField.getText();
				if(stringID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alanı boş bırakmayınız.");
				}
				else if(stringID.length() != 11) {
					JOptionPane.showMessageDialog(null, "Kimlik Numarası 11 rakamdan oluşmalıdır.");
				}
				else {
					long id = Long.parseLong(stringID);
					cashPay(id);
				}
			}
		});
		cashButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cashButton.setBounds(174, 220, 120, 30);
		frame.getContentPane().add(cashButton);
		
		JButton cardButton = new JButton("Kartla Ödeme");
		cardButton.setFocusable(false);
		cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringID = idField.getText();
				if(stringID.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alanı Boş bırakmayınız.");
				}
				else if(stringID.length() != 11) {
					JOptionPane.showMessageDialog(null, "Kimlik numarası 11 rakamdan oluşmalıdır.");
				}
				else {
					long id = Long.parseLong(stringID);
					KartOdeme kartodeme = new KartOdeme(conn, id);
					kartodeme.showFrame();
				}
				
			}
		});
		cardButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardButton.setBounds(304, 220, 120, 30);
		frame.getContentPane().add(cardButton);
	}
	public void showFrame() {
		frame.setVisible(true);
	}
	public int getPrice(long id) {
		String query = "SELECT muayene_fiyati FROM MUAYENE WHERE muayene_hasta_tc = ? and muayene_odeme = 0 and muayene_durum = 1";
		int priceLocal = 0;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();			
			while (rs.next()) {
				priceLocal = rs.getInt("muayene_fiyati") + priceLocal;
			}
			return priceLocal;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL Hatası");
			//e1.printStackTrace();
		}
		return -1;
	}
	public boolean getInsurance(long id) {
		String query = "SELECT sigorta_durum FROM SIGORTA WHERE sigorta_tc = ?";
		boolean insuranceLocal = false;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			if (rs.next()) {
				insuranceLocal = rs.getBoolean("sigorta_durum");
			}
			else {
				JOptionPane.showMessageDialog(null, "Sigorta Bilgisi Bulunamadı");
			}
			return insuranceLocal;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL Hatası");
			//e1.printStackTrace();
		}
		return false;
	}
	public void cashPay(long id) {
		String query = "UPDATE MUAYENE SET muayene_odeme = 1 WHERE muayene_hasta_tc = ? and muayene_odeme = 0 and muayene_durum = 1";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			int rowsAffected = statement.executeUpdate();
			if(rowsAffected != 0) {
				JOptionPane.showMessageDialog(null,"Ödeme Gerçekleştirildi");
			}
			else {
				JOptionPane.showMessageDialog(null, "Ödeme Gerçekleştirilemedi.");
			}
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "SQL HATA");
		}
	}
	
}
