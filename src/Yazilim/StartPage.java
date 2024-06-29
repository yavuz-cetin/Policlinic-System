package Yazilim;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class StartPage {
	private JFrame frame;
	private Connection conn;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection dummyConn = null;
				try {
					StartPage window = new StartPage(dummyConn);
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
	public StartPage(Connection parent_conn) {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Start Page");
		frame.setBounds(100, 100, 539, 577);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnLogOut = new JButton("Çıkış");
		btnLogOut.setFocusable(false);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogOut.setFocusable(false);
		btnLogOut.setBounds(168, 382, 156, 35);
		frame.getContentPane().add(btnLogOut);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(184, 149, 125, 25);
		frame.getContentPane().add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(184, 116, 125, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("\u015Eifre");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(184, 185, 47, 22);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(184, 218, 125, 25);
		frame.getContentPane().add(passwordField);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Göster");
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnNewRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 if(rdbtnNewRadioButton.isSelected()) {
                     passwordField.setEchoChar((char) 0);
                 }
                 else {
                     passwordField.setEchoChar('*');
                 }
            }
        });
		rdbtnNewRadioButton.setBounds(310, 219, 84, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setFocusable(false);
			}
		});
		JButton btnNewButton = new JButton("Giriş Yap");

		btnNewButton.setFocusable(false);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(184, 286, 125, 35);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				if(comboBox.getSelectedItem().toString()=="Veznedar") {
					String kullanici = textField.getText();
					gorevliGiris(kullanici,password,"Veznedar");
				}else if(comboBox.getSelectedItem().toString()=="Admin") {

					//Veri taban�ndan textField'daki ki�iyi bul ve passwordField'la �ifreyi kontrol et.
					String kullanici = textField.getText();
					adminGiris(kullanici);

					
				}else if(comboBox.getSelectedItem().toString()=="Doktor") {
					String kullanici = textField.getText();
					doktorGiris(kullanici);
					
				}else if(comboBox.getSelectedItem().toString()=="Randevu Görevlisi") {
					String kullanici = textField.getText();
					gorevliGiris(kullanici,password,"Randevu Görevlisi");
				}else if(comboBox.getSelectedItem().toString()=="Kayıt Görevlisi") {
					String kullanici = textField.getText();
					gorevliGiris(kullanici,password,"Hasta Kayıt Görevlisi");
				}else {//Hata
					JOptionPane.showMessageDialog(null, "Lütfen Kullanıcı tipi seçiniz.");
				}
			}

		});
		comboBox.setToolTipText("");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Kullan\u0131c\u0131 Tipi Se\u00E7iniz", "Admin", "Doktor", "Kay\u0131t G\u00F6revlisi", "Randevu G\u00F6revlisi", "Veznedar"}));
		comboBox.setBounds(148, 53, 195, 35);
		frame.getContentPane().add(comboBox);
	}

	public int adminGiris(String kullanici) {
		String query = "SELECT admin_sifre FROM Admin WHERE admin_kullanici_adi = ?;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, kullanici);
			statement.execute();
			ResultSet result = statement.getResultSet();
			String sifre = "";
			char[] passwordChars = passwordField.getPassword();
			String password = new String(passwordChars);
			while (result.next()) {
				sifre = result.getString(1);
			}
			if(sifre.length() < 1 ) {
				JOptionPane.showMessageDialog(null, "Yanlış kullanıcı adı veya şifre.");
			}else if(!password.equals(sifre)) {
				JOptionPane.showMessageDialog(null, "Yanlış kullanıcı adı veya şifre.");
			}else {
				Admin admin = new Admin(conn,kullanici);
				admin.showFrame();
				frame.dispose();
				return 1;
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}
	public int doktorGiris(String kullanici) {
	    String query = "SELECT doktor_id, doktor_sifre FROM DOKTOR WHERE doktor_kullanici_adi = ?;";
	    int doktorId = -1; // Default value if ID is not found
	    try {
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, kullanici);
	        statement.execute();
	        ResultSet result = statement.getResultSet();
	        String sifre = "";
	        char[] passwordChars = passwordField.getPassword();
	        String password = new String(passwordChars);
	        while (result.next()) {
	            doktorId = result.getInt("doktor_id"); // Retrieve the ID
	            sifre = result.getString("doktor_sifre");
	        }
	        if (sifre.length() < 1) {
	            JOptionPane.showMessageDialog(null, "Yanlış kullanıcı adı veya şifre.");
	            return -1; // Return -1 if credentials are incorrect
	        } else if (!password.equals(sifre)) {
	            JOptionPane.showMessageDialog(null, "Yanlış kullanıcı adı veya şifre.");
	            return -1; // Return -1 if credentials are incorrect
	        } else {
	            Doktor doktor = new Doktor(conn, kullanici,doktorId);
	            doktor.setVisible(true);
	            frame.dispose();
	            return 0; // Return the ID if credentials are correct
	        }
	    } catch (SQLException e1) {
	        // Handle SQLException
	        e1.printStackTrace();
	        return -1; // Return -1 in case of an exception
	    }
	}

	
	public int gorevliGiris(String kullanici, String sifre, String gorevliTipi) {
	    String query = "SELECT gorevli_sifre FROM GOREVLI WHERE gorevli_kullanici_adi = ? AND gorevli_tipi = ?;";
	    try {
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, kullanici);
	        statement.setString(2, gorevliTipi);
	        statement.execute();
	        ResultSet result = statement.getResultSet();
	        String correctSifre = "";
	        while (result.next()) {
	            correctSifre = result.getString("gorevli_sifre");
	        }
	        if (correctSifre.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Kullanıcı adı veya görevli tipi bulunamadı.");
	        } else if (!correctSifre.equals(sifre)) {
	            JOptionPane.showMessageDialog(null, "Yanlış şifre.");
	        } else {
	            switch (gorevliTipi) {
	                case "Hasta Kayıt Görevlisi":
	                    KayitGorevlisi kayitGorevlisi = new KayitGorevlisi(conn, kullanici);
	                    kayitGorevlisi.showFrame();
	                    frame.dispose();
	                    return 1;
	                case "Veznedar":
	                    Veznedar veznedar = new Veznedar(conn, kullanici);
	                    veznedar.showFrame();
	                    frame.dispose();
	                    return 1;
	                case "Randevu Görevlisi":
	                    RandevuGorevlisi randevuGorevlisi = new RandevuGorevlisi(conn, kullanici);
	                    randevuGorevlisi.setVisible(true);
	                    frame.dispose();
	                    return 1;
	                default:
	                    JOptionPane.showMessageDialog(null, "Bilinmeyen görevli tipi.");
	                    return 1;
	            }
	        }
	    } catch (SQLException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	    return 1;
	}


	public void showFrame() {
		frame.setVisible(true);
	}
}