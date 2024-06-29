package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class KlinikEkle {
	private static Connection conn;

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlinikEkle window = new KlinikEkle();
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
	public KlinikEkle() {
		initialize();
	}
	public KlinikEkle(Connection parent_conn) {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Klinik Ekle");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Klinik Ad\u0131");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(236, 11, 125, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(236, 44, 125, 25);
		frame.getContentPane().add(textField);
		
		JButton btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String yeni_klinik_adi = textField.getText();
				if(yeni_klinik_adi.length()>=3) {
					klinikEkle(yeni_klinik_adi);
				}else {
					JOptionPane.showMessageDialog(null, "Klinik isimleri en az 3 harften oluşmalıdır.");
				}
				
			}
		});
		btnEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnEkle.setFocusable(false);
		btnEkle.setBounds(236, 98, 125, 35);
		frame.getContentPane().add(btnEkle);
		
		JButton btnGeriDn = new JButton("Geri D\u00F6n");
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnGeriDn.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGeriDn.setFocusable(false);
		btnGeriDn.setBounds(10, 36, 160, 63);
		frame.getContentPane().add(btnGeriDn);
		frame.setBounds(100, 100, 431, 251);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void showFrame() {
		frame.setVisible(true);
	}
	public void klinikEkle(String klinik) {
		String query = "insert into klinik(klinik_isim) values(?);";
		String query_2 = "select klinik_id from klinik where klinik_isim = ?;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			PreparedStatement statement_2 = conn.prepareStatement(query_2);
			statement.setString(1, klinik);
			statement.execute();
			statement_2.setString(1, klinik);
			statement_2.execute();
			ResultSet result_2 = statement_2.getResultSet();
			Integer klinik_id=0;
			while (result_2.next()) {
				klinik_id = result_2.getInt(1);
			}
			KlinikEkleDoktor ked = new KlinikEkleDoktor(conn,klinik_id);
			ked.showFrame();
			JOptionPane.showMessageDialog(null, klinik +" isimli klinik eklemek için bir adet doktor atayınız.");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Bu isimde bir klinik zaten var.");
			//e1.printStackTrace();
		}
	}
}
