package Yazilim;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class KlinikEkleDoktor {
    private int klinik_id;
    private Connection conn;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlinikEkleDoktor window = new KlinikEkleDoktor();
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
	public KlinikEkleDoktor() {
		initialize();
	}
	public KlinikEkleDoktor(Connection conn, int klinik_id) {
		this.klinik_id =klinik_id;
		this.conn = conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 524, 502);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(39, 125, 125, 25);
		frame.getContentPane().add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("Doktor Ad\u0131");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(39, 92, 125, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isim = textField.getText();
				String soyisim = textField_1.getText();
				String kullanici_adi = textField_2.getText();
				char[] passwordChars = passwordField.getPassword();
				String sifre = new String(passwordChars);
				doktorAta(isim,soyisim,kullanici_adi,sifre,klinik_id);
				
			}
		});
		btnEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnEkle.setFocusable(false);
		btnEkle.setBounds(39, 375, 125, 35);
		frame.getContentPane().add(btnEkle);
		
		JLabel lblNewLabel_1_1 = new JLabel("Doktor Soyad\u0131");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(39, 168, 125, 22);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(39, 201, 125, 25);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(39, 270, 125, 25);
		frame.getContentPane().add(textField_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(39, 237, 125, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("\u015Eifre");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1_2.setBounds(39, 306, 125, 22);
		frame.getContentPane().add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel = new JLabel("Her klinikte 1 doktor bulunmal\u0131d\u0131r. Bu nedenle bu klini\u011Fe bir doktor ekleyiniz.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(25, 16, 444, 65);
		frame.getContentPane().add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(39, 339, 125, 25);
		frame.getContentPane().add(passwordField);
	}

	public void doktorAta(String isim, String soyisim, String kullanici_adi, String sifre,int klinik_id) {
		// TODO Auto-generated method stub
		if(isim.length()<3) {
			JOptionPane.showMessageDialog(null, "Ad en az 3 harf olmalıdır.");
		}else if(soyisim.length()<3) {
			JOptionPane.showMessageDialog(null, "Soyad en az 3 harften oluşmalıdır.");
		}else if(kullanici_adi.length()<3) {
			JOptionPane.showMessageDialog(null, "Kullanıcı Adı en az 3 harften oluşmalıdır.");
		}else if(sifre.length()<3) {
			JOptionPane.showMessageDialog(null, "Şifre en az 3 harften oluşmalıdır.");
		}else {
			String query = "insert into doktor(doktor_isim,doktor_soyisim,doktor_kullanici_adi,doktor_sifre,doktor_klinik_id) values(?,?,?,?,?);";
			try {
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setString(1, isim);
				statement.setString(2, soyisim);
				statement.setString(3, kullanici_adi);
				statement.setString(4, sifre);
				statement.setInt(5, klinik_id);
				statement.execute();
				JOptionPane.showMessageDialog(null,"Doktor başarıyla atandı.");
				frame.dispose();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Bu doktor eklenemedi. Tekrar deneyiniz.");
				//e1.printStackTrace();
			}
		}
	}
	public void showFrame() {
		frame.setVisible(true);
	}
}
