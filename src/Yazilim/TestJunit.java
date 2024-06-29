package Yazilim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestJunit extends TestCase{

	private Connection conn;
    private DoktorEkle objUnderTest;
    private StartPage doktorIDTest;
    private Veznedar veznedarTest;
    private KartOdeme kartOdemeTest;

    @Before
    public void setUp() throws Exception {
        String databaseName = "klinik"; // Veritabanı adı
        String userName = "postgres"; // Kullanıcı adı
        String password = "1"; // Şifre

        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, userName, password);
        objUnderTest = new DoktorEkle(conn);
        doktorIDTest = new StartPage(conn);
        veznedarTest = new Veznedar(conn,"yavuz");
        kartOdemeTest = new KartOdeme(conn,12345678910L);

    }      
    @Test
    public void testGetClinicIDFromMethod() throws SQLException {
        int clinicID = objUnderTest.getClinicID("göz");       
        assertEquals(1, clinicID);
    }
    
    @Test
    public void testStartPage1() throws SQLException{
    	int doktorID = doktorIDTest.doktorGiris("furkan");
    	Assert.assertNotNull(doktorID);
    }
   
    @Test
    public void testStartPage2() throws SQLException{
    	Assert.assertNotNull(doktorIDTest.gorevliGiris("yavuz", "123", "Veznedar"));
    }
    @Test
    public void testStartPage3() throws SQLException{
    	Assert.assertNotNull(doktorIDTest.adminGiris("yavuz"));
    }

    @Test
    public void testVeznedar() throws SQLException{
    	int price = veznedarTest.getPrice(1);
    	Assert.assertNotNull(price);
    }
    @Test
    public void testVeznedar2() throws SQLException{
    	boolean insurance = veznedarTest.getInsurance(12345678910L);
    	Assert.assertNotNull(insurance);
    }
    @Test
    public void testVeznedar3() throws SQLException{
    	Assert.assertNotNull(veznedarTest.getPrice(12345678910L));
    }

    @Test 
    public void testDoktor2() throws SQLException {
    	Assert.assertNotNull(Doktor.getRandevuId(conn, "2024-05-10", "09:30", 1));
    }
    @Test 
    public void testKartOdeme() throws SQLException {
    	Assert.assertNotNull(kartOdemeTest.authenticate("1111222233334444","01/24", "111", 12345678910L));
    }
    @Test
    public void testDoktor() throws SQLException {
    	Assert.assertNotNull(Doktor.getHastaInfo(conn, "2024-05-10", "09:30"));
    }

  
    


    @After
    public void tearDown() throws Exception {
        conn.close();
    }

}


