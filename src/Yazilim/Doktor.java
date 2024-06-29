package Yazilim;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;


public class Doktor extends JFrame {
	private static Connection dummy;
	@SuppressWarnings("unused")
	private int doktorId;
	@SuppressWarnings("unused")
	private Connection conn;
	

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
					Doktor frame = new Doktor(dummy,"",1000);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Doktor(Connection conn,String kullanici,int doktorId) {
		if(kullanici == "") {
			kullanici = "Yavuz";
		}
		this.conn= conn;
		this.doktorId=doktorId;
		setTitle("Doktor "+ kullanici);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 683, 613);
		getContentPane().setLayout(null);
		
		JLabel lblTarihiSeiniz = new JLabel("Tarihi Se\u00E7iniz.");
		lblTarihiSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTarihiSeiniz.setBounds(51, 120, 139, 30);
		getContentPane().add(lblTarihiSeiniz);

		JComboBox comboBox_1 = new JComboBox();
		
		comboBox_1.setToolTipText("");
		comboBox_1.setFocusable(false);
		comboBox_1.setBounds(51, 238, 184, 35);
		getContentPane().add(comboBox_1);

		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedDate = (String) comboBox_2.getSelectedItem();
				populateComboBoxWithHours(comboBox_1, selectedDate, doktorId, conn);
			}
		});
		comboBox_2.setToolTipText("");
		comboBox_2.setFocusable(false);
		comboBox_2.setBounds(51, 160, 184, 35);
		getContentPane().add(comboBox_2);
		
		JLabel lblRandevuSaatiniSeiniz = new JLabel("Randevu Saatini Se\u00E7iniz.");
		lblRandevuSaatiniSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRandevuSaatiniSeiniz.setBounds(51, 206, 216, 30);
		getContentPane().add(lblRandevuSaatiniSeiniz);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(455, 51, 133, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("");
		lblNewLabel_4_1.setBounds(455, 76, 133, 14);
		getContentPane().add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("");
		lblNewLabel_4_2.setBounds(455, 99, 133, 14);
		getContentPane().add(lblNewLabel_4_2);
		textField = new JTextField();
		
		JLabel lblNewLabel = new JLabel("Hasta Bilgileri");
		lblNewLabel.setVisible(false);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(445, 11, 155, 21);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u0130sim:");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(397, 74, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Soyisim:");
		lblNewLabel_1_1.setVisible(false);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(371, 99, 64, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Hasta Kayd\u0131:");
		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(341, 120, 100, 55);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("TC Kimlik:");
		lblNewLabel_3.setVisible(false);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(360, 50, 94, 14);
		getContentPane().add(lblNewLabel_3);
		

		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setVisible(false);
		comboBox_1_1.setToolTipText("");
		comboBox_1_1.setFocusable(false);
		comboBox_1_1.setBounds(439, 132, 184, 35);
		getContentPane().add(comboBox_1_1);
		
		JButton btnHastaKaydA = new JButton("Hasta Kayd\u0131 A\u00E7");
		btnHastaKaydA.setVisible(false);
		btnHastaKaydA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String muayene_id = (String) comboBox_1_1.getSelectedItem();
				int muayene = Integer.parseInt(muayene_id);
				if(muayene_id == null) {
					JOptionPane.showMessageDialog(null, "Hasta kaydı seçmediniz.", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					HastaKaydi hk = new HastaKaydi(conn,muayene);
					hk.setVisible(true);
				}
			}
		});
		

		JComboBox<String> comboBox_1_2 = new JComboBox();
		comboBox_1_2.setVisible(false);
		comboBox_1_2.setModel(new DefaultComboBoxModel(new String[] {"", "Rapor", "Re\u00E7ete", "Sevk Ka\u011F\u0131d\u0131"}));
		comboBox_1_2.setToolTipText("");
		comboBox_1_2.setFocusable(false);
		comboBox_1_2.setBounds(439, 314, 184, 35);
		getContentPane().add(comboBox_1_2);
		
		JLabel lblBelgeTipiSeiniz = new JLabel("Belge Tipi Se\u00E7iniz.");
		lblBelgeTipiSeiniz.setVisible(false);
		lblBelgeTipiSeiniz.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBelgeTipiSeiniz.setBounds(439, 269, 171, 30);
		getContentPane().add(lblBelgeTipiSeiniz);
		
		JButton btnBelgeVer = new JButton("Belge Ver");
		btnBelgeVer.setVisible(false);
		btnBelgeVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String belgeTipi = (String) comboBox_1_2.getSelectedItem();
				if(belgeTipi == null||belgeTipi == "") {
					JOptionPane.showMessageDialog(null, "Belge Tipi seçiniz.", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(belgeTipi.equals("Rapor")){
					String randevu_tarihi = (String) comboBox_2.getSelectedItem();
					String randevu_saati= (String) comboBox_1.getSelectedItem();
					int randevuId = -1;
					try {
						randevuId = getRandevuId(conn, randevu_tarihi,randevu_saati, doktorId);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Rapor newRapor = new Rapor(conn,randevuId);
					newRapor.setVisible(true);
				}else if(belgeTipi.equals("Sevk Kağıdı")) {
					String randevu_tarihi = (String) comboBox_2.getSelectedItem();
					String randevu_saati= (String) comboBox_1.getSelectedItem();
					int randevuId = -1;
					try {
						randevuId = getRandevuId(conn, randevu_tarihi,randevu_saati, doktorId);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					SevkKagidi newRapor = new SevkKagidi(conn,randevuId);
					newRapor.setVisible(true);
					
				}else if(belgeTipi.equals("Reçete")) {
					String randevu_tarihi = (String) comboBox_2.getSelectedItem();
					String randevu_saati= (String) comboBox_1.getSelectedItem();
					int randevuId = -1;
					try {
						randevuId = getRandevuId(conn, randevu_tarihi,randevu_saati, doktorId);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Recete newRecete = new Recete(conn,randevuId);
					newRecete.setVisible(true);
				}
			}
		});
		btnBelgeVer.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBelgeVer.setFocusable(false);
		btnBelgeVer.setBounds(439, 375, 184, 30);
		getContentPane().add(btnBelgeVer);
		JLabel lblMuayenecreti = new JLabel("Muayene \u00DCcreti");
		JButton btnMuayeneBitir = new JButton("Muayene Bitir");
		JButton btnHastaBilgisiGrntle = new JButton("Hasta Bilgisi G\u00F6r\u00FCnt\u00FCle");
		btnHastaBilgisiGrntle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String randevu_tarihi = (String) comboBox_2.getSelectedItem();
				String randevu_saati= (String) comboBox_1.getSelectedItem();
				//JOptionPane.showMessageDialog(null, randevu_tarihi+" "+randevu_saati, "Error", JOptionPane.ERROR_MESSAGE);
				
				  String[] hastaInfo = getHastaInfo(conn, randevu_tarihi, randevu_saati);

		            // Display the retrieved information in a pop-up dialog
		            if (hastaInfo != null) {
		            	lblNewLabel_4.setText(hastaInfo[0]);
		            	lblNewLabel_4_1.setText(hastaInfo[1]);
		            	lblNewLabel_4_2.setText(hastaInfo[2]);
		            	lblNewLabel.setVisible(true);
		            	lblNewLabel_1.setVisible(true);
		            	lblNewLabel_1_1.setVisible(true);
		            	lblNewLabel_2.setVisible(true);
		            	lblNewLabel_3.setVisible(true);
		            	btnHastaKaydA.setVisible(true);
		            	comboBox_1_1.setVisible(true);
		            	btnBelgeVer.setVisible(true);
		            	lblMuayenecreti.setVisible(true);
		            	textField.setVisible(true);
		            	lblBelgeTipiSeiniz.setVisible(true);
		            	comboBox_1_2.setVisible(true);
		            	btnMuayeneBitir.setVisible(true);
		            	populateComboBoxWithMuayeneId(hastaInfo[0],comboBox_1_1,conn);
			                //JOptionPane.showMessageDialog(null, message.toString(), "Hasta Information", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                JOptionPane.showMessageDialog(null, "Hasta bulunamadı.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
			}
		});
		btnHastaBilgisiGrntle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnHastaBilgisiGrntle.setFocusable(false);
		btnHastaBilgisiGrntle.setBounds(26, 314, 246, 55);
		getContentPane().add(btnHastaBilgisiGrntle);
		populateComboBoxWithDates(comboBox_2);
		
		JButton btnGeriDn = new JButton("Geri D\u00F6n");
		btnGeriDn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartPage stp = new StartPage(conn);
				stp.showFrame();
				dispose();
			}
		});
		btnGeriDn.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnGeriDn.setFocusable(false);
		btnGeriDn.setBounds(51, 11, 165, 63);
		getContentPane().add(btnGeriDn);

		

		

		

		btnHastaKaydA.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnHastaKaydA.setFocusable(false);
		btnHastaKaydA.setBounds(439, 178, 184, 44);
		getContentPane().add(btnHastaKaydA);
		
		
		
		
		btnMuayeneBitir.setVisible(false);
		btnMuayeneBitir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || textField.getText() == null) {
				    JOptionPane.showMessageDialog(null, "Muayene ücretini boş bırakmayınız.", "Error", JOptionPane.ERROR_MESSAGE);   
				}else {
				int randevuId = -1;
				String randevu_tarihi = (String) comboBox_2.getSelectedItem();
				String randevu_saati= (String) comboBox_1.getSelectedItem();
				try {
					randevuId = getRandevuId(conn, randevu_tarihi,randevu_saati, doktorId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					int muayene_ucreti = Integer.parseInt(textField.getText());
					setMuayeneUcreti(muayene_ucreti,randevuId,conn);
					updateMuayeneDurum(randevuId,conn);
					JOptionPane.showMessageDialog(null, "Muayene başarıyla bitirildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
					comboBox_2.setSelectedItem((String) comboBox_2.getSelectedItem());
				}catch(NumberFormatException eb) {
					JOptionPane.showMessageDialog(null, "Muayene ücreti sayısal bir değer olmalıdır.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}}
		});
		btnMuayeneBitir.setForeground(Color.RED);
		btnMuayeneBitir.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnMuayeneBitir.setFocusable(false);
		btnMuayeneBitir.setBounds(372, 508, 269, 55);
		getContentPane().add(btnMuayeneBitir);
		
		
		lblMuayenecreti.setVisible(false);
		lblMuayenecreti.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMuayenecreti.setBounds(439, 416, 216, 30);
		getContentPane().add(lblMuayenecreti);
		
		
		textField.setVisible(false);
		textField.setBounds(439, 446, 94, 25);
		getContentPane().add(textField);
		textField.setColumns(10);
		

	}
	protected void setMuayeneUcreti(int muayene_ucreti, int randevuId, Connection conn) {
		// TODO Auto-generated method stub
	    try {
	        // Create the SQL query to retrieve hour strings
	        String query = "update muayene set muayene_fiyati = ? where muayene_id = ?";
	        
	        // Create a PreparedStatement
	        PreparedStatement statement = conn.prepareStatement(query);
	        
	        // Set the parameters
	        statement.setInt(1, muayene_ucreti);
	        statement.setInt(2, randevuId);
	        
	        // Execute the query
	        statement.execute();

	      
	        
	        // Close the statement and ResultSet
	        statement.close();

	    } catch (SQLException e) {
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
	    for (int i = 1; i <= 8; i++) {
	        LocalDate nextDate = currentDate.plusDays(i);
	        String formattedNextDate = nextDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        comboBox.addItem(formattedNextDate);
	    }
	}
	
	private void populateComboBoxWithHours(JComboBox<String> comboBox, String date, int doctorId, Connection conn) {
	    comboBox.removeAllItems(); // Clear previous items
	    
	    try {
	        // Create the SQL query to retrieve hour strings
	        String query = "SELECT randevu_saati FROM randevu,muayene WHERE randevu_id =muayene_id and muayene_durum = 0 and randevu_tarihi = ? AND randevu_doktor_id = ?";
	        
	        // Create a PreparedStatement
	        PreparedStatement statement = conn.prepareStatement(query);
	        
	        // Set the parameters
	        statement.setString(1, date);
	        statement.setInt(2, doctorId);
	        
	        // Execute the query
	        ResultSet resultSet = statement.executeQuery();
	        
	        // Create a set to store hour strings in sorted order
	        TreeSet<String> hourSet = new TreeSet<>();
	        
	        // Iterate through the ResultSet and add hour strings to the set
	        while (resultSet.next()) {
	            String hour = resultSet.getString("randevu_saati");
	            hourSet.add(hour);
	        }
	        
	        // Add hour strings from the set to the comboBox
	        for (String hour : hourSet) {
	            comboBox.addItem(hour);
	        }
	        
	        // Close the statement and ResultSet
	        statement.close();
	        resultSet.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
    public static String[] getHastaInfo(Connection conn, String randevu_tarihi, String randevu_saati) {
        // SQL query to retrieve HASTA information based on RANDVU date and time
        String query = "SELECT h.hasta_tc, h.hasta_isim, h.hasta_soyisim " +
                "FROM HASTA h " +
                "JOIN RANDEVU r ON h.hasta_tc = r.randevu_hasta_tc " +
                "WHERE r.randevu_tarihi = ? AND r.randevu_saati = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            stmt.setString(1, randevu_tarihi);
            stmt.setString(2, randevu_saati);

            // Execute the query and retrieve the results
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extract HASTA information from the result set
                String hasta_tc = rs.getString("hasta_tc");
                String hasta_isim = rs.getString("hasta_isim");
                String hasta_soyisim = rs.getString("hasta_soyisim");

                // Return the information as an array
                return new String[]{hasta_tc, hasta_isim, hasta_soyisim};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no matching HASTA found, return null
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public void populateComboBoxWithMuayeneId(String hasta_tc, @SuppressWarnings("rawtypes") JComboBox comboBox,Connection conn) {      
        @SuppressWarnings("unused")
		Statement stmt = null;
        PreparedStatement pstmt = null;
        long tc = Long.parseLong(hasta_tc);
        try {
            // Execute a query to retrieve muayene_ids
            stmt = conn.createStatement();
            String sql = "SELECT muayene_id FROM muayene WHERE muayene_hasta_tc = ? and muayene_durum = 1;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, tc);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Clear comboBox
            comboBox.removeAllItems();

            // Populate comboBox with muayene_ids
            while (rs.next()) {
                String muayene_id = rs.getString("muayene_id");
                comboBox.addItem(muayene_id);
            }

            // Clean-up environment
            rs.close();
            pstmt.close();
            
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
 // end finally try
        } // end try
    }
    
    public static int getRandevuId(Connection conn, String randevu_tarihi, String randevu_saati, int randevu_doktor_id) throws SQLException {
        int randevuId = -1; // Default value if no match found
        
        String sql = "SELECT randevu_id FROM randevu WHERE randevu_tarihi = ? AND randevu_saati = ? AND randevu_doktor_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, randevu_tarihi);
            pstmt.setString(2, randevu_saati);
            pstmt.setInt(3, randevu_doktor_id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    randevuId = rs.getInt("randevu_id");
                }
            }
        }
        
        return randevuId;
    }
    
    protected void updateMuayeneDurum(int muayene_id,Connection conn) {
        try {
            // SQL update statement to set muayene_durum to 1 for the given muayene_id
            String sql = "UPDATE MUAYENE SET muayene_durum = 1 WHERE muayene_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, muayene_id);

                // Execute the update statement
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {

                } else {
                	JOptionPane.showMessageDialog(null, "Hata.", "Error", JOptionPane.ERROR_MESSAGE);                
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("Error updating muayene_durum for muayene_id: " + muayene_id);
        }
    }
}
