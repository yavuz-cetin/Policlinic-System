package Yazilim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main{

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String databaseName = "klinik"; //veri tabanı ismi
		String userName = "postgres"; //pgsql kullanıcı adı
		String password = "1"; //pgsql şifresi
		
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, userName,password);
        
		StartPage startPage = new StartPage(conn);
		startPage.showFrame();
	}
}