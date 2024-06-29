package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class KayitGorevlisi {

	private JFrame frame;
	private JTextField idField;
	private JTextField nameField;
	private JTextField surnameField;
	private JLabel surnameLabel;
	private static Connection conn;
	private JTextField ageField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KayitGorevlisi window = new KayitGorevlisi();
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
	public KayitGorevlisi() {
		initialize();
	}
	public KayitGorevlisi(Connection parent_conn,String kullanici) {
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Kayıt Görevlisi");
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		idField = new JTextField();
		idField.setBounds(165, 60, 120, 30);
		frame.getContentPane().add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(165, 100, 120, 30);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setBounds(165, 140, 120, 30);
		frame.getContentPane().add(surnameField);
		surnameField.setColumns(10);
		
		JLabel idLabel = new JLabel("T.C. Kimlik No.:");
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setBounds(35, 60, 120, 30);
		frame.getContentPane().add(idLabel);
		
		JLabel nameLabel = new JLabel("Hasta İsim:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setBounds(35, 100, 120, 30);
		frame.getContentPane().add(nameLabel);
		
		surnameLabel = new JLabel("Hasta Soyisim:");
		surnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		surnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		surnameLabel.setBounds(35, 140, 120, 30);
		frame.getContentPane().add(surnameLabel);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Cinsiyet", "Erkek", "Kadın"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(165, 221, 120, 29);
		frame.getContentPane().add(comboBox);
		
		JButton addButton = new JButton("Kaydet");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringID = idField.getText();
				String name = nameField.getText();
				String surname = surnameField.getText();
				String sex = comboBox.getSelectedItem().toString();
				String stringAge = ageField.getText();
				if(stringID.isEmpty() || name.isEmpty() || surname.isEmpty() || sex.equals("Cinsiyet") || stringAge.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Boş alan bırakmayınız.");
				}
				else if(stringID.length() != 11) {
					JOptionPane.showMessageDialog(null, "Kimlik numarası 11 rakamdan oluşmalıdır.");
				}
				else {
					long id = Long.parseLong(stringID);
					int age = Integer.parseInt(stringAge);
					addPatient(id, name, surname, age, sex);
				}
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addButton.setBounds(304, 305, 120, 45);
		addButton.setFocusable(false);
		frame.getContentPane().add(addButton);
		
		JButton exitButton = new JButton("Çıkış");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startpage = new StartPage(conn);
				startpage.showFrame();
				frame.dispose();
			}
		});
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		exitButton.setBounds(10, 305, 120, 45);
		exitButton.setFocusable(false);
		frame.getContentPane().add(exitButton);
		
		JLabel headerLabel = new JLabel("Hasta Kayıt Sistemi");
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setBounds(10, 11, 414, 41);
		frame.getContentPane().add(headerLabel);
		
		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.setBounds(165, 180, 120, 30);
		frame.getContentPane().add(ageField);
		
		JLabel ageLabel = new JLabel("Yaş:");
		ageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ageLabel.setBounds(35, 180, 120, 30);
		frame.getContentPane().add(ageLabel);
		
		JLabel sexLabel = new JLabel("Cinsiyet:");
		sexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sexLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sexLabel.setBounds(35, 220, 120, 30);
		frame.getContentPane().add(sexLabel);
		
		

		
	}
	public void showFrame() {
		frame.setVisible(true);
	}
	private void addPatient(long id, String name, String surname, int age, String sex) {
		String query = "INSERT into HASTA values (?, ?, ?, ?, ?);";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setLong(1, id);
			statement.setString(2, name);
			statement.setString(3, surname);
			statement.setInt(4, age);
			statement.setString(5, sex);
			statement.execute();
			JOptionPane.showMessageDialog(null, name +" isimli hasta başarıyla eklendi.");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Hasta Eklenemedi");
			//e1.printStackTrace();
		}
	}
}
