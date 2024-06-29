package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Admin {
    private String kullanici;
	private static Connection conn;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					
					Admin window = new Admin();
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
	public Admin() {
		kullanici = "yavuz";
		initialize();
	}
	public Admin(Connection parent_conn, String kullanici_adi) {
		kullanici = kullanici_adi;
		conn = parent_conn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Admin");
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Klinik Ekle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KlinikEkle klinikekle = new KlinikEkle(conn);
				klinikekle.showFrame();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(201, 40, 197, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnDoktorEkle = new JButton("Doktor Ekle");
		btnDoktorEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DoktorEkle doktorekle = new DoktorEkle(conn);
                doktorekle.showFrame();
            }
		});
		btnDoktorEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDoktorEkle.setFocusable(false);
		btnDoktorEkle.setBounds(201, 96, 197, 35);
		frame.getContentPane().add(btnDoktorEkle);
		
		JButton btnalanEkle = new JButton("\u00C7al\u0131\u015Fan Ekle");
		btnalanEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalisanEkle cal = new CalisanEkle(conn);
				cal.setVisible(true);
			}
		});
		btnalanEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnalanEkle.setFocusable(false);
		btnalanEkle.setBounds(201, 151, 197, 35);
		frame.getContentPane().add(btnalanEkle);
		
		JButton btnGeriDn = new JButton("\u00C7\u0131k\u0131\u015F");
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage startpage = new StartPage(conn);
				startpage.showFrame();
				frame.dispose();
			}
		});
		btnGeriDn.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGeriDn.setFocusable(false);
		btnGeriDn.setBounds(20, 144, 142, 49);
		frame.getContentPane().add(btnGeriDn);
		
		JLabel lblNewLabel = new JLabel("Ho\u015Fgeldin, ");
		lblNewLabel.setText("Ho\u015Fgeldin, "+kullanici);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(20, 40, 142, 35);
		frame.getContentPane().add(lblNewLabel);
		frame.setBounds(100, 100, 442, 309);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void showFrame() {
		frame.setVisible(true);
	}
}
