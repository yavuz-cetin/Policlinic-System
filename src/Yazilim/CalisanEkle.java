package Yazilim;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class CalisanEkle extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField passwordField;
	private static Connection conn;


	public CalisanEkle(Connection parent_conn) {
		conn = parent_conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setBounds(157, 45, 120, 30);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(157, 90, 120, 30);
		contentPane.add(passwordField);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Kullanıcı Tipini Seçiniz", "Randevu Görevlisi", "Hasta Kayıt Görevlisi", "Veznedar"}));
		comboBox.setBounds(116, 135, 200, 30);
		contentPane.add(comboBox);
		
		JButton addButton = new JButton("Ekle");
		addButton.setFocusable(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				String usertype = comboBox.getSelectedItem().toString();
				if(username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Boş Alan Bırakmayınız");
				}
				else if(usertype == "Kullanıcı Tipi Seçiniz") {
					JOptionPane.showMessageDialog(null, "Kullanıcı Tipi Seçiniz");
				}
				else {
					addWorker(username, password, usertype);
				}
			}
		});
		addButton.setBounds(304, 220, 120, 30);
		contentPane.add(addButton);
		
		JButton btnGeriDn = new JButton("Geri Dön");
		btnGeriDn.setFocusable(false);
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnGeriDn.setBounds(10, 220, 120, 30);
		contentPane.add(btnGeriDn);
		
		JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLabel.setBounds(27, 45, 120, 30);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Şifre:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setBounds(27, 90, 120, 30);
		contentPane.add(passwordLabel);
		
		
	}
	private void addWorker(String username, String password, String usertype) {
		String query = "INSERT into GOREVLI values (?, ?, ?);";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, usertype);
			statement.execute();
			JOptionPane.showMessageDialog(null, username +" isimli " + usertype +" başarıyla eklendi.");
			dispose();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Görevli Eklenemedi");
			//e1.printStackTrace();
		}
	}
}
