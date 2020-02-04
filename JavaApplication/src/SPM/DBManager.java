package SPM;
import java.sql.*;
public class DBManager {




    public static int GetTableElementsNumber(String DataBaseName, String TableName) {

        int cnt = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + TableName + ";");
            while (rs.next()) {
                cnt++;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnt;


    }


    public static void OpenDataBase(String DataBaseName) {


        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void CreateTableInDataBase(String DataBaseName, String TableName, String Commands) {


        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);

            stmt = c.createStatement();
            String sql = "CREATE TABLE " + TableName + " " +

                    Commands;

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void InsertIntoTable(String DataBaseName, String Commands) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            stmt.executeUpdate(Commands);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String GetFromTable(String DataBaseName, String TableName, String IdName, String IdValue, String Wanted) {
        String Got = "";
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + TableName + ";");

            while (rs.next()) {

                String ident = rs.getString(IdName);

                if (ident.equals(IdValue)) {
                    Got = rs.getString(Wanted);
                }


            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return Got;
    }


    public static void UpdateInTable(String DataBaseName, String TableName, String IdName, String IdValue, String DataName, String NewData) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "UPDATE " + TableName + " set " + DataName + " = '" + NewData + "' where " + IdName + "=" + IdValue + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void RemoveFromTable(String DataBaseName, String TableName, String IdName, String IdValue) {

        int en = GetTableElementsNumber(DataBaseName, TableName);


        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DataBaseName);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE from " + TableName + " where " + IdName + "=" + IdValue + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = Integer.parseInt(IdValue) + 1; i <= en; i++) {
            UpdateInTable(DataBaseName, TableName, IdName, String.valueOf(i), IdName, String.valueOf(i - 1));


        }


    }



}