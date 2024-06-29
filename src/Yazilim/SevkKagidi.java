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

public class SevkKagidi extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection dummy;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SevkKagidi frame = new SevkKagidi(dummy,0);
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
	public SevkKagidi(Connection conn,int muayene_id) {
		String patientInfo = "";
		try {
			patientInfo = findHastaInfo(conn,muayene_id);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] lines = patientInfo.split("\n");
        String hastaIsim = lines[0].substring(lines[0].indexOf(":") + 1).trim();
        String hastaSoyisim = lines[1].substring(lines[1].indexOf(":") + 1).trim();
        int hastaYas = Integer.parseInt(lines[2].substring(lines[2].indexOf(":") + 1).trim());
        String hastaCinsiyet = lines[3].substring(lines[3].indexOf(":") + 1).trim();
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Sevk Kağıdı ver");
		setBounds(100, 100, 542, 613);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hastan\u0131n Ad\u0131:");
		lblNewLabel.setBounds(57, 57, 184, 28);
		getContentPane().add(lblNewLabel);
		
		JLabel lblCinsiyet = new JLabel("Cinsiyet:");
		lblCinsiyet.setBounds(57, 86, 79, 28);
		getContentPane().add(lblCinsiyet);
		
		JLabel lblYa = new JLabel("Ya\u015F:");
		lblYa.setBounds(57, 113, 52, 28);
		getContentPane().add(lblYa);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sevk Edilecek Kurum");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(60, 152, 143, 19);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(57, 182, 190, 28);
		getContentPane().add(textField_1);
		
		JButton btnRaporVer = new JButton("Sevk Ka\u011F\u0131d\u0131 ver");
		btnRaporVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String raporMetni = "Hasta Adı: "+hastaIsim+" "+ hastaSoyisim+"\nCinsiyet: "+hastaCinsiyet+"\nYaş: "+hastaYas+"\n";
				raporMetni = raporMetni + "\nSevk Edilecek Kurum: "+textField_1.getText();
				sevkVer(raporMetni,muayene_id,conn);
			}
		});
		btnRaporVer.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRaporVer.setFocusable(false);
		btnRaporVer.setBounds(57, 234, 184, 30);
		getContentPane().add(btnRaporVer);
		
		JLabel lblNewLabel_2 = new JLabel(" <dynamic>");
		lblNewLabel_2.setBounds(139, 57, 292, 28);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("<dynamic>");
		lblNewLabel_3.setBounds(139, 86, 129, 28);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("0");
		lblNewLabel_4.setBounds(139, 113, 70, 28);
		getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_2.setText(hastaIsim +" "+ hastaSoyisim);
		lblNewLabel_3.setText(hastaCinsiyet);
		lblNewLabel_4.setText(""+hastaYas);

	}
	private String findHastaInfo(Connection conn,int muayeneId) throws SQLException {
        String sql = "SELECT HASTA.hasta_isim, HASTA.hasta_soyisim, HASTA.hasta_yas, HASTA.hasta_cinsiyet " +
                "FROM MUAYENE " +
                "INNER JOIN HASTA ON MUAYENE.muayene_hasta_tc = HASTA.hasta_tc " +
                "WHERE MUAYENE.muayene_id = ?";
        StringBuilder patientInfo = new StringBuilder();
   try (PreparedStatement statement = conn.prepareStatement(sql)) {
       statement.setInt(1, muayeneId);
       try (ResultSet resultSet = statement.executeQuery()) {
           if (resultSet.next()) {
               String hastaIsim = resultSet.getString("hasta_isim");
               String hastaSoyisim = resultSet.getString("hasta_soyisim");
               int hastaYas = resultSet.getInt("hasta_yas");
               String hastaCinsiyet = resultSet.getString("hasta_cinsiyet");
               
               // Do something with the retrieved patient information
               
               patientInfo.append("Hasta Isim: ").append(hastaIsim).append("\n");
               patientInfo.append("Hasta Soyisim: ").append(hastaSoyisim).append("\n");
               patientInfo.append("Hasta Yas: ").append(hastaYas).append("\n");
               patientInfo.append("Hasta Cinsiyet: ").append(hastaCinsiyet);

           } else {
        	   JOptionPane.showMessageDialog(null, "Hata.", "Error", JOptionPane.ERROR_MESSAGE);
           }
       }
   }
   return patientInfo.toString();
	}
	
	protected void sevkVer(String raporMetni, int muayene_id,Connection conn) {
		// TODO Auto-generated method stub
        String sql = "UPDATE MUAYENE SET muayene_sevk = ? WHERE muayene_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, raporMetni);
            statement.setInt(2, muayene_id);

            // Execute the update statement
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            	JOptionPane.showMessageDialog(null, "Sevk Kağıdı başarıyla verildi.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(null, "Sevk Kağıdı verilemedi.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
