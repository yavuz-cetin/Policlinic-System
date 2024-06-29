package Yazilim;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class DoktorEkle {
	private static Connection conn;

	private JFrame frame;
	private JPanel contentPane;
	private JTextField doctorNameField;
	private JTextField doctorSurnameField;
	private JTextField doctorClinicField;
	private JTextField doctorUsernameField;
	private JTextField doctorPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoktorEkle frame = new DoktorEkle();
					frame.showFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DoktorEkle() {
		initialize();
	}
	public DoktorEkle(Connection parent_conn) {
		conn = parent_conn;
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 542, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGeriDn = new JButton("Geri D\u00F6n");
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnGeriDn.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGeriDn.setFocusable(false);
		btnGeriDn.setBounds(391, 11, 125, 42);
		contentPane.add(btnGeriDn);
		
		JLabel doctorNameLbl = new JLabel("Ad");
		doctorNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		doctorNameLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		doctorNameLbl.setBounds(104, 139, 125, 25);
		contentPane.add(doctorNameLbl);
		
		doctorNameField = new JTextField();
		doctorNameField.setColumns(10);
		doctorNameField.setBounds(239, 139, 125, 25);
		contentPane.add(doctorNameField);

		JButton btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = doctorNameField.getText();
				String surname = doctorSurnameField.getText();
				String clinic = doctorClinicField.getText();
				String username = doctorUsernameField.getText();
				String password = doctorPasswordField.getText();
				if(name.isEmpty() || surname.isEmpty() || clinic.isEmpty() || username.isEmpty() || password.isEmpty()){
					JOptionPane.showMessageDialog(null, "Boş Alan bırakmayınız.");
				}
				else {
					int clinicID = getClinicID(clinic);
					if(clinicID == -1){
						JOptionPane.showMessageDialog(null, "Hata");
					}
					else {
						addDoctor(name, surname, clinicID, username, password);
					}
				}
			}
		});
		btnEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnEkle.setFocusable(false);
		btnEkle.setBounds(391, 401, 125, 35);
		contentPane.add(btnEkle);
		
		JLabel lblSoyad = new JLabel("Soyad");
		lblSoyad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoyad.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSoyad.setBounds(101, 175, 125, 25);
		contentPane.add(lblSoyad);
		
		JLabel lblKlinik = new JLabel("Klinik");
		lblKlinik.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKlinik.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKlinik.setBounds(101, 211, 125, 25);
		contentPane.add(lblKlinik);
		
		doctorSurnameField = new JTextField();
		doctorSurnameField.setColumns(10);
		doctorSurnameField.setBounds(239, 175, 125, 25);
		contentPane.add(doctorSurnameField);
		
		doctorClinicField = new JTextField();
		doctorClinicField.setColumns(10);
		doctorClinicField.setBounds(239, 211, 125, 25);
		contentPane.add(doctorClinicField);
		
		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblKullancAd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKullancAd.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKullancAd.setBounds(101, 247, 125, 25);
		contentPane.add(lblKullancAd);
		
		JLabel lblifre = new JLabel("\u015Eifre");
		lblifre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblifre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblifre.setBounds(101, 283, 125, 25);
		contentPane.add(lblifre);
		
		doctorUsernameField = new JTextField();
		doctorUsernameField.setColumns(10);
		doctorUsernameField.setBounds(239, 247, 125, 25);
		contentPane.add(doctorUsernameField);
		
		doctorPasswordField = new JTextField();
		doctorPasswordField.setColumns(10);
		doctorPasswordField.setBounds(239, 283, 125, 25);
		contentPane.add(doctorPasswordField);
	}
	public int getClinicID(String clinic){
		String query = "SELECT klinik_id FROM KLINIK WHERE klinik_isim = ?;";
		int id = 0;
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, clinic);
			statement.execute();
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				id = rs.getInt("klinik_id");
			}
			if(id > 0){
				return id;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Klinik bulunamadı.");
			//e1.printStackTrace();
		}
		return -1;
	}
	private void addDoctor(String name, String surname, int clinic, String username, String password) {
		String query = "INSERT into DOKTOR (doktor_isim, doktor_soyisim, doktor_klinik_id, doktor_kullanici_adi, doktor_sifre) values (?, ?, ?, ?, ?);";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setInt(3, clinic);
			statement.setString(4, username);
			statement.setString(5, password);
			statement.execute();
			JOptionPane.showMessageDialog(null, name +" isimli doktor başarıyla eklendi.");
			frame.dispose();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Doktor Eklenemedi");
			//e1.printStackTrace();
		}
	}
	public void showFrame() {
		frame.setVisible(true);
	}
}
