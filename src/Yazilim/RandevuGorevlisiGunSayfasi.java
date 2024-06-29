package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class RandevuGorevlisiGunSayfasi extends JFrame {
    @SuppressWarnings("unused")
	private Connection conn;
	private static Connection dummy;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RandevuGorevlisiGunSayfasi frame = new RandevuGorevlisiGunSayfasi(dummy,"");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RandevuGorevlisiGunSayfasi(Connection conn,String date) {
		this.conn= conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 588);
		getContentPane().setLayout(null);
		
		
		String dateString = date;
		SimpleDateFormat inputFormat = new SimpleDateFormat("M/dd/yy, hh:mm:ss a zzz");
		java.util.Date date_2 = null;
		try {
			date_2 = inputFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Format the date to match PostgreSQL format
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = outputFormat.format(date_2);

		//System.out.println("Formatted Date: " + formattedDate);
		JLabel lblNewLabel = new JLabel("Seçilen tarih: "+formattedDate);
		setTitle(formattedDate + " tarihli randevu kayıt formu");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(31, 35, 255, 30);
		getContentPane().add(lblNewLabel);
		JComboBox comboBox_2 = new JComboBox();
		JComboBox comboBox_1 = new JComboBox();
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"}));
		comboBox.setToolTipText("");
		comboBox.setFocusable(false);
		comboBox.setBounds(31, 389, 184, 35);
		getContentPane().add(comboBox);
		
		JLabel lblSaatSeiniz = new JLabel("Saat Se\u00E7iniz.");
		lblSaatSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSaatSeiniz.setBounds(31, 348, 255, 30);
		getContentPane().add(lblSaatSeiniz);
		
		JButton btnRandevuEkle = new JButton("Randevu Kayd\u0131");
		btnRandevuEkle.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	int selectedDoktorId = getSelectedDoktorId(comboBox_1);
		        //comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"}));
		        removeExistingAppointmentTimes(selectedDoktorId, comboBox, formattedDate);
		        String hastaTC = textField.getText().trim();
		        long result = checkHastaTC(hastaTC, conn);
		        if (result == -1) {
		            JOptionPane.showMessageDialog(RandevuGorevlisiGunSayfasi.this, "TC veri tabanında bulunamadı. Yanlış girilmediyse kayıt görevlisine yönlendiriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
		        } else if (result == 0) {
		        	String in = (String) comboBox.getSelectedItem();
		        	//System.out.print(in);
		        	if(in == "") {
		        		JOptionPane.showMessageDialog(RandevuGorevlisiGunSayfasi.this, "Saat seçmelisiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
		        	}else {
		        	selectedDoktorId = getSelectedDoktorId(comboBox_1);
		        	int selectedKlinikId = getKlinikId(comboBox_2,conn);
		        	String saati = (String) comboBox.getSelectedItem();
		        	randevuOlustur(conn,hastaTC,formattedDate,saati,selectedDoktorId,selectedKlinikId);
			        comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"}));
			        removeExistingAppointmentTimes(selectedDoktorId, comboBox,formattedDate);
		        	}} else if (result == -2) {
		            JOptionPane.showMessageDialog(RandevuGorevlisiGunSayfasi.this, "Veritabanı hatası oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
		        }

		    }
		});
		btnRandevuEkle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRandevuEkle.setFocusable(false);
		btnRandevuEkle.setBounds(31, 445, 184, 46);
		getContentPane().add(btnRandevuEkle);
		
		JLabel lblHastaTcGiriniz = new JLabel("Hasta TC Giriniz.");
		lblHastaTcGiriniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHastaTcGiriniz.setBounds(31, 273, 149, 30);
		getContentPane().add(lblHastaTcGiriniz);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(31, 312, 184, 25);
		getContentPane().add(textField);
		
		JButton btnGeriDn = new JButton("Geri D\u00F6n");
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnGeriDn.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGeriDn.setFocusable(false);
		btnGeriDn.setBounds(257, 29, 184, 46);
		getContentPane().add(btnGeriDn);
		
		JLabel lblDoktorSeiniz = new JLabel("Doktor Se\u00E7iniz.");
		lblDoktorSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDoktorSeiniz.setBounds(31, 195, 139, 30);
		getContentPane().add(lblDoktorSeiniz);

		comboBox_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get selected doctor ID
		       
		        // Remove existing appointment times for the selected doctor
		    	int selectedDoktorId = getSelectedDoktorId(comboBox_1);
		        

		        // Populate the comboBox with available appointment times
		        comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"}));
		        removeExistingAppointmentTimes(selectedDoktorId, comboBox, formattedDate);
		    }
		});

		comboBox_1.setToolTipText("");
		comboBox_1.setFocusable(false);
		comboBox_1.setBounds(31, 227, 184, 35);

		getContentPane().add(comboBox_1);
		
		
		
		comboBox_2.setToolTipText("");
		comboBox_2.setFocusable(false);
		comboBox_2.setBounds(31, 149, 184, 35);
        comboBox_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateDoktorComboBox(comboBox_1,comboBox_2,conn);
            }
        });
		getContentPane().add(comboBox_2);
		
		JLabel lblKlinikSeiniz = new JLabel("Klinik Se\u00E7iniz.");
		lblKlinikSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKlinikSeiniz.setBounds(31, 109, 139, 30);
		getContentPane().add(lblKlinikSeiniz);
		
		populateComboBoxWithKlinik(comboBox_2, conn);

	}
	private void randevuOlustur(Connection conn2, String hastaTC, String formattedDate, String saati,
	        int selectedDoktorId, int selectedKlinikId) {
	    try {
	        // Create the SQL INSERT statement
	        String sql = "INSERT INTO RANDEVU (randevu_hasta_tc, randevu_doktor_id, randevu_klinik_id, randevu_tarihi, randevu_saati) " +
	                     "VALUES (?, ?, ?, ?, ?)";

	        // Prepare the statement
	        try (PreparedStatement statement = conn2.prepareStatement(sql)) {
	            // Set the parameters for the statement
	        	long tc_no = Long.parseLong(hastaTC);
	            statement.setLong(1,tc_no);
	            statement.setInt(2, selectedDoktorId);
	            statement.setInt(3, selectedKlinikId);
	            statement.setString(4, formattedDate);
	            statement.setString(5, saati);

	            // Execute the INSERT statement
	            int rowsInserted = statement.executeUpdate();
	            
	            if (rowsInserted > 0) {
	                // Show success message
	                JOptionPane.showMessageDialog(null, "Randevu başarıyla eklendi.", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                // Show failure message
	                JOptionPane.showMessageDialog(null, "Randevu eklenemedi.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle the exception according to your application's needs
	    }
	}


	private static void populateComboBoxWithKlinik(JComboBox<String> comboBox,Connection conn) {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT klinik_isim FROM klinik")) {

            while (resultSet.next()) {
                String klinikName = resultSet.getString("klinik_isim");
                comboBox.addItem(klinikName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void populateDoktorComboBox(JComboBox<String> comboBox_1, JComboBox<String> comboBox_2, Connection conn) {
        comboBox_1.removeAllItems(); // Clear previous items

        // Get selected klinik
        String selectedKlinik = (String) comboBox_2.getSelectedItem();

        if (selectedKlinik != null) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT doktor_id, doktor_isim, doktor_soyisim FROM doktor WHERE doktor_klinik_id IN (SELECT klinik_id FROM klinik WHERE klinik_isim = ?)")) {
                statement.setString(1, selectedKlinik);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int doktorId = resultSet.getInt("doktor_id");
                    String doktorName = resultSet.getString("doktor_isim");
                    String doktorSurname = resultSet.getString("doktor_soyisim");
                    
                    // Concatenate doktor_isim, doktor_soyisim, and doktor_id into a single string
                    String doktorFullName = doktorName + " " + doktorSurname + " (ID: " + doktorId + ")";
                    
                    comboBox_1.addItem(doktorFullName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public long checkHastaTC(String hastaTC, Connection conn) {
        if (hastaTC.isEmpty()) {
            return -1; // Indicate empty TC
        }

        try {
            // Parse hastaTC string to integer
            long hastaTCInt = Long.parseLong(hastaTC);

            try (PreparedStatement statement = conn.prepareStatement("SELECT hasta_tc FROM hasta WHERE hasta_tc = ?")) {
                statement.setLong(1, hastaTCInt);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return 0; // TC exists
                } else {
                    return -1; // TC does not exist
                }
            }
        } catch (NumberFormatException ex) {
            // Invalid TC format
            return -1; // TC does not exist
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -2; // Database error
        }
    }

    private void removeExistingAppointmentTimes(int doktorId, JComboBox<String> comboBox,String tarih) {
        try (PreparedStatement statement = conn.prepareStatement(
                "SELECT randevu_saati FROM randevu WHERE randevu_doktor_id = ? and randevu_tarihi = ?;")) {
            statement.setInt(1, doktorId);
            statement.setString(2, tarih);
            ResultSet resultSet = statement.executeQuery();

            // Create a list to store existing appointment times
            List<String> existingAppointmentTimes = new ArrayList<>();

            // Add existing appointment times to the list
            while (resultSet.next()) {
                String appointmentTime = resultSet.getString("randevu_saati");
                existingAppointmentTimes.add(appointmentTime);
            }

            // Remove existing appointment times from the comboBox
            for (String appointmentTime : existingAppointmentTimes) {
                comboBox.removeItem(appointmentTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int getSelectedDoktorId(JComboBox<String> comboBox) {
        String selectedDoctor = (String) comboBox.getSelectedItem();
        if (selectedDoctor == null) {
            return -1;
        }

        int startIndex = selectedDoctor.lastIndexOf("(ID: ");
        if (startIndex == -1) {
            JOptionPane.showMessageDialog(null, "Doktor bulunamadı.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }

        startIndex += 5;


        int endIndex = selectedDoctor.indexOf(")", startIndex);
        if (endIndex == -1) {
        	JOptionPane.showMessageDialog(null, "Hata.", "Error", JOptionPane.ERROR_MESSAGE);       
            return -1;
        }
        
        String doctorIdStr = selectedDoctor.substring(startIndex, endIndex);

        int doctorId = Integer.parseInt(doctorIdStr);

        return doctorId;
    }

    private int getKlinikId(JComboBox<String> comboBox, Connection conn) {
        int klinikId = -1; 
        String klinikName = (String) comboBox.getSelectedItem();
        try (PreparedStatement statement = conn.prepareStatement("SELECT klinik_id FROM klinik WHERE klinik_isim = ?")) {
            statement.setString(1, klinikName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                klinikId = resultSet.getInt("klinik_id");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return klinikId;
    }


    
}
