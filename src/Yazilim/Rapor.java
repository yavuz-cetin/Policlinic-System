package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Rapor extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection dummy;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rapor frame = new Rapor(dummy,0);
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
	@SuppressWarnings("unchecked")
	public Rapor(Connection conn,int muayene_id) {
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
		setTitle("Rapor ver");
		setBounds(100, 100, 542, 613);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hastan\u0131n Ad\u0131:");
		lblNewLabel.setBounds(35, 57, 313, 28);
		getContentPane().add(lblNewLabel);
		
		JLabel lblCinsiyet = new JLabel("Cinsiyet:");
		lblCinsiyet.setBounds(57, 86, 49, 28);
		getContentPane().add(lblCinsiyet);
		
		JLabel lblYa = new JLabel("Ya\u015F:");
		lblYa.setBounds(57, 113, 36, 28);
		getContentPane().add(lblYa);
		
		@SuppressWarnings("rawtypes")
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboBox_2.setToolTipText("");
		comboBox_2.setFocusable(false);
		comboBox_2.setBounds(57, 192, 184, 35);
		getContentPane().add(comboBox_2);
		
		JLabel lblBalangTarihi = new JLabel("Ba\u015Flang\u0131\u00E7 Tarihi");
		lblBalangTarihi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBalangTarihi.setBounds(57, 152, 139, 30);
		getContentPane().add(lblBalangTarihi);
		
		@SuppressWarnings("rawtypes")
		JComboBox comboBox_2_1 = new JComboBox();
		comboBox_2_1.setToolTipText("");
		comboBox_2_1.setFocusable(false);
		comboBox_2_1.setBounds(57, 278, 184, 35);
		getContentPane().add(comboBox_2_1);
		populateComboBoxWithDates(comboBox_2);
		populateComboBoxWithDates(comboBox_2_1);
		JLabel lblBitiTarihi = new JLabel("Biti\u015F Tarihi");
		lblBitiTarihi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBitiTarihi.setBounds(57, 238, 139, 30);
		getContentPane().add(lblBitiTarihi);
		
		JLabel lblNewLabel_1 = new JLabel("Koyulan Te\u015Fhis");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(53, 334, 143, 19);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(50, 364, 191, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Te\u015Fhisin Detaylar\u0131");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(53, 395, 143, 19);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(50, 425, 191, 61);
		getContentPane().add(textField_1);
		
		JButton btnRaporVer = new JButton("Rapor ver");
		btnRaporVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String raporMetni = "Hasta Adı: "+hastaIsim+" "+ hastaSoyisim+"\nCinsiyet: "+hastaCinsiyet+"\nYaş: "+hastaYas+"\n";
				String raporMetni_2 = "Başlangıç Tarihi: "+ (String) comboBox_2.getSelectedItem() +"\nBitiş Tarihi: "+(String) comboBox_2_1.getSelectedItem();
				raporMetni = raporMetni + raporMetni_2;
				raporMetni = raporMetni + "\nKoyulan Teşhis: "+textField.getText()+"\nTeşhisin Detayları: "+textField_1.getText();
				raporVer(raporMetni,muayene_id,conn);
			}
		});
		btnRaporVer.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRaporVer.setFocusable(false);
		btnRaporVer.setBounds(53, 510, 184, 30);
		getContentPane().add(btnRaporVer);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(137, 57, 292, 28);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(137, 86, 129, 28);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(137, 113, 70, 28);
		getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_2.setText(hastaIsim +" "+ hastaSoyisim);
		lblNewLabel_3.setText(hastaCinsiyet);
		lblNewLabel_4.setText(""+hastaYas);
		
		
	}
	
	protected void raporVer(String raporMetni, int muayene_id,Connection conn) {
		// TODO Auto-generated method stub
        String sql = "UPDATE MUAYENE SET muayene_rapor = ? WHERE muayene_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, raporMetni);
            statement.setInt(2, muayene_id);

            // Execute the update statement
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            	JOptionPane.showMessageDialog(null, "Rapor başarıyla verildi.", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
            	JOptionPane.showMessageDialog(null, "Rapor verilemedi.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	

	private void populateComboBoxWithDates(JComboBox<String> comboBox) {
	    comboBox.removeAllItems(); // Clear previous items
	    
	    // Get today's date
	    LocalDate currentDate = LocalDate.now();
	    
	    // Format today's date
	    String formattedToday = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    
	    // Add today's date to the comboBox
	    comboBox.addItem(formattedToday);
	    
	    // Add dates for the next 7 days to the comboBox
	    for (int i = 1; i <= 45; i++) {
	        LocalDate nextDate = currentDate.plusDays(i);
	        String formattedNextDate = nextDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        comboBox.addItem(formattedNextDate);
	    }
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
}
