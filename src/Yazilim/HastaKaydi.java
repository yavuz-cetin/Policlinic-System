package Yazilim;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HastaKaydi extends JFrame {
private static Connection dummy;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaKaydi frame = new HastaKaydi(dummy,1);
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
	public HastaKaydi(Connection conn,int muayene_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Hasta Kaydı");
		setBounds(100, 100, 542, 613);
		getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(26, 96, 468, 344);
		getContentPane().add(textPane);
		
		JButton btnRapor = new JButton("Rapor");
		btnRapor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            // Assuming conn is your Connection object
		            String query = "SELECT muayene_rapor FROM muayene WHERE muayene_id = ?";
		            PreparedStatement stt = conn.prepareStatement(query);
		            // Set the parameter muayene_id, assuming muayene_idValue is its value
		            stt.setInt(1, muayene_id);
		            // Execute the query
		            ResultSet rs = stt.executeQuery();
		            
		            if(rs.next()) {
		            	String Rapor = rs.getString("muayene_rapor");
		            	textPane.setText(Rapor);
		            }
		            // Process the ResultSet if needed
		            // Close resources when done
		            rs.close();
		            stt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            // Handle any SQL exceptions
		        }
			}
		});
		btnRapor.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRapor.setFocusable(false);
		btnRapor.setBounds(26, 37, 135, 30);
		getContentPane().add(btnRapor);
		
		JButton btnSevkKad = new JButton("Sevk Ka\u011F\u0131d\u0131");
		btnSevkKad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            // Assuming conn is your Connection object
		            String query = "SELECT muayene_sevk FROM muayene WHERE muayene_id = ?";
		            PreparedStatement stt = conn.prepareStatement(query);
		            // Set the parameter muayene_id, assuming muayene_idValue is its value
		            stt.setInt(1, muayene_id);
		            // Execute the query
		            ResultSet rs = stt.executeQuery();
		            
		            if(rs.next()) {
		            	String Rapor = rs.getString("muayene_sevk");
		            	textPane.setText(Rapor);
		            }
		            // Process the ResultSet if needed
		            // Close resources when done
		            rs.close();
		            stt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            // Handle any SQL exceptions
		        }
			}
		});
		btnSevkKad.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSevkKad.setFocusable(false);
		btnSevkKad.setBounds(332, 37, 160, 30);
		getContentPane().add(btnSevkKad);
		
		JButton btnReete = new JButton("Re\u00E7ete");
		btnReete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		            // Assuming conn is your Connection object
		            String query = "SELECT muayene_recete FROM muayene WHERE muayene_id = ?";
		            PreparedStatement stt = conn.prepareStatement(query);
		            // Set the parameter muayene_id, assuming muayene_idValue is its value
		            stt.setInt(1, muayene_id);
		            // Execute the query
		            ResultSet rs = stt.executeQuery();
		            
		            if(rs.next()) {
		            	String Rapor = rs.getString("muayene_recete");
		            	textPane.setText(Rapor);
		            }
		            // Process the ResultSet if needed
		            // Close resources when done
		            rs.close();
		            stt.close();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            // Handle any SQL exceptions
		        }
			}
		});
		btnReete.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReete.setFocusable(false);
		btnReete.setBounds(187, 37, 135, 30);
		getContentPane().add(btnReete);

	}
}
