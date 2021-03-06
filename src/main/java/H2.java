import java.util.*;
import java.util.logging.*;
import java.sql.*;

public class H2 {
	private double[] ranNumbers;
	public static void main(String[] args) {
		//testGenRandomNumbers();
		String url = "jdbc:h2:mem:";

        try (Connection con = DriverManager.getConnection(url);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT 1+1")) {

            if (rs.next()) {
                System.out.println(rs.getInt(1));
            }

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
	}
	public void genRandomNumbers(int n) {
		//generate n number of random numbers 
		this.ranNumbers  = new double[n];
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			this.ranNumbers[i] = ran.nextGaussian();
		}
	}
	public static void testGenRandomNumbers() {
		H2 h2 = new H2();
		h2.genRandomNumbers(10);
		for (int i = 0; i < 10; i++) {
			System.out.println(h2.ranNumbers[i]);
		}
	}
}