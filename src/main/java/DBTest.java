import java.sql.*;

public class DBTest {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://10.0.1.13:3306/ws_lottery";

    private static final String USER_NAME = "lottery";

    private static final String PASSWORD = "weisaishidai";


    public String sqlquery() throws ClassNotFoundException, SQLException {
        String ver_code = null;
        Connection connection = null;
        Class.forName(DRIVER_NAME);
        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String sql = "SELECT message from ws_lottery.sms_record where phone = 15882066239";
        PreparedStatement prst = connection.prepareStatement(sql);
        //结果集
        ResultSet rs = prst.executeQuery();
        while (rs.last()) {
            String message = rs.getString("message");
            String idCode = message.substring(7, 13);
            ver_code = idCode;
            System.out.println(ver_code);
            break;
        }
        rs.close();
        prst.close();

        return ver_code;
        
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBTest dbTest = new DBTest();
        dbTest.sqlquery();
    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        Connection connection = null;
//        Class.forName(DRIVER_NAME);
//        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//        String sql = "SELECT message from ws_lottery.sms_record where phone = 15882066239";
//        PreparedStatement prst = connection.prepareStatement(sql);
//        //结果集
//        ResultSet rs = prst.executeQuery();
//        while (rs.last()) {
//            String message = rs.getString("message");
//            String idCode = message.substring(7, 13);
//            System.out.println(idCode);
//            break;
//        }
//        rs.close();
//        prst.close();
//
//        return sql;
//
////        DBTest dbTest = new DBTest();
////        dbTest.sqlquery();
//
//    }
}