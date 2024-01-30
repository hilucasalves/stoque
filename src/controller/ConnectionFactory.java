package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import static java.sql.DriverManager.registerDriver;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class ConnectionFactory {

    private static final Logger LOG = getLogger(ConnectionFactory.class.getName());

    /**
     *
     */
    public Connection Connection;

    /**
     *
     */
    public void openConnection() {

        try {
            Properties props = new Properties();
            FileInputStream input = new FileInputStream("database.properties");
            props.load(input);

            String url = "jdbc:mysql://" + props.getProperty("DB_SERVER") + ":" + props.getProperty("DB_PORT") + "/" + props.getProperty("DB_NAME");//caminho
            registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection = getConnection(url, props.getProperty("DB_USER"), props.getProperty("DB_PASS"));
        } catch (SQLException | IOException ex) {
            showMessageDialog(null, "Erro: " + ex.getMessage());
        }
    }

    /**
     *
     */
    public void closeConnection() {
        try {
            Connection.close();
        } catch (SQLException SQLException) {
            showMessageDialog(null, "Erro: " + SQLException.getMessage());
        }
    }

}
