package controller;

import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import static java.sql.DriverManager.registerDriver;
import java.sql.SQLException;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class ConnectionFactory{
    
    private static final Logger LOG = getLogger(ConnectionFactory.class.getName());
    
    /**
     *
     */
    public Connection Connection;
   
    /**
     *
     */
    public void openConnection(){
    String server = "localhost"; //servidor do BD
    String database ="papelaria2"; //nome do seu banco de dados
    String url = "jdbc:mysql://" + server + "/" + database;//caminho
    String user = "root"; //nome de um usuário de seu BD
    String password = "InfoLuck79"; //sua senha de acesso
    try{
            registerDriver(new com.mysql.jdbc.Driver());
    Connection = getConnection(url, user, password);
    //JOptionPane.showMessageDialog(null,"Conexão com Banco de Dados realizada com sucesso.");
    }
    catch(SQLException SQLException){
            showMessageDialog(null,"Erro: "+ SQLException.getMessage()); 
    }}

    /**
     *
     */
    public void closeConnection(){
    try {
    Connection.close();
    //JOptionPane.showMessageDialog(null,"Banco de Dados desconectado.");
    }
    catch (SQLException SQLException){
            showMessageDialog(null,"Erro: "+ SQLException.getMessage()); 
    }}  
        
}
