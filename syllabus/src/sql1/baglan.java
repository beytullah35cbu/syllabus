package sql1;

import java.sql.*;

    public class baglan {
        static Connection myCon;
        static Statement myStat;

        public static ResultSet sqlConnection(String sql_sorgu){
            ResultSet myRs=null;
            try {
                String url=("jdbc:mysql://localhost:3306/test");
                String using=("root");
                String psddword=("");

                myCon = DriverManager.getConnection(url,using,psddword);
                myStat =(Statement) myCon.createStatement();
                myRs = myStat.executeQuery("select * from "+sql_sorgu);//sql değrlerini girmek için kullanılır

            } catch (Exception e) {
                e.printStackTrace();
            }
            return myRs;

        }
        private static final String URL = "jdbc:mysql://localhost:3306/test";
        private static final String KULLANICI_ADI = "root";
        private static final String SIFRE = "";

        // Bağlantıyı kapatmak için yardımcı method
        public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void ekle(String sql_sorgu){
            try {

                myStat.executeUpdate(sql_sorgu);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void update(String sql_sorgu){
            try {
                myStat.executeUpdate(sql_sorgu);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void delete(String sql_sorgu){
            try {
                myStat.executeUpdate(sql_sorgu);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static  ResultSet bul(String sql_sorgu,String tablo){
            ResultSet myRs=null;
            if (myStat == null) {
                // Eğer myStat başlatılmamışsa başlat
                sqlConnection(tablo); // myStat başlatılır
            }

            try {
                myRs=myStat.executeQuery(sql_sorgu);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return  myRs;
        }
    }

