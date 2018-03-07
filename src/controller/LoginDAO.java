/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static java.lang.String.valueOf;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class LoginDAO {
    
    private static final Logger LOG = getLogger(LoginDAO.class.getName());
    
    /**
     *
     * @param vLogin
     */
    public void clean(view.Login vLogin){
        vLogin.LOGIN_USU.setText(null);
        vLogin.SENHA_USU.setText(null);        
    }
    
    /**
     *
     * @param vLogin
     * @param mLogin
     */
    public void logar(view.Login vLogin, model.Login mLogin){
        mLogin.setLOGIN_USU(vLogin.LOGIN_USU.getText());
        mLogin.setSENHA_USU(valueOf(vLogin.SENHA_USU.getPassword()));
        if(mLogin.getLOGIN_USU().isEmpty()&&mLogin.getSENHA_USU().isEmpty()){
            showMessageDialog(null, "Necessário preencher email e senha.");
        }
        else if(mLogin.getLOGIN_USU().isEmpty()){
            showMessageDialog(null, "Necessário preencher email.");
        }
        else if(mLogin.getSENHA_USU().isEmpty()){
            showMessageDialog(null, "Necessário preencher senha.");
        }
        else{
            try{
                ConnectionFactory connectionFactory = new ConnectionFactory();
                
                connectionFactory.openConnection();
                String sql = ("SELECT COUNT(LOGIN_USU) AS C FROM LOGIN WHERE LOGIN_USU = ? && SENHA_USU = PASSWORD(?)");
                PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
                
                preparedStatement.setString(1, mLogin.getLOGIN_USU());
                preparedStatement.setString(2, mLogin.getSENHA_USU());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                if(resultSet.getString("C").equalsIgnoreCase("1")){
                    //JOptionPane.showMessageDialog(null, "Usuário Conectado.");
                    resultSet.close();
                    preparedStatement.close();
                    vLogin.dispose();
                    view.MenuPrincipal vMenuPrincipal = new view.MenuPrincipal();
                    vMenuPrincipal.setVisible(true);
                }
                else{
                    showMessageDialog(null, "Usuário Inexistente.");
                    this.clean(vLogin);
                }
            }
            catch(SQLException sQLException){
                showMessageDialog(null, "Usuário Inexistente.\n"+sQLException.getMessage());
            }}
    }
    
    /**
     *
     * @param vModuloAdministrativo
     */
    public void cleanLogin(view.ModuloAdministrativo vModuloAdministrativo) {
        vModuloAdministrativo.LOGIN_USU.setText(null);
        vModuloAdministrativo.NOME_USU.setText(null);
        vModuloAdministrativo.SENHA_USU.setText(null);
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mLogin
     */
    public void getLogin(view.ModuloAdministrativo vModuloAdministrativo, model.Login mLogin){
        mLogin.setLOGIN_USU(vModuloAdministrativo.LOGIN_USU.getText());
        mLogin.setNOME_USU(vModuloAdministrativo.NOME_USU.getText());
        mLogin.setSENHA_USU(valueOf(vModuloAdministrativo.SENHA_USU.getPassword()));
}
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mLogin
     */
    public void adicionarLogin(view.ModuloAdministrativo vModuloAdministrativo, model.Login mLogin){  
    this.getLogin(vModuloAdministrativo, mLogin);
    if(mLogin.getLOGIN_USU().equalsIgnoreCase("")||mLogin.getSENHA_USU().equalsIgnoreCase("")){
    showMessageDialog(vModuloAdministrativo, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{    
    try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.openConnection();
            
            String sql = ("INSERT INTO LOGIN VALUES (?, ?, PASSWORD(?))"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mLogin.getLOGIN_USU());
                preparedStatement.setString(2, mLogin.getNOME_USU());
                preparedStatement.setString(3, mLogin.getSENHA_USU());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Login incluso com sucesso.");
            }
            this.cleanLogin(vModuloAdministrativo);
            this.pesquisarTabela_Login(vModuloAdministrativo);
        }
        catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
        }}
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mLogin
     */
    public void excluirLogin(view.ModuloAdministrativo vModuloAdministrativo, model.Login mLogin){
        try{
            mLogin.setLOGIN_USU((String) vModuloAdministrativo.tableLogin.getValueAt(vModuloAdministrativo.tableLogin.getSelectedRow(), 0));
            controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
            connectionFactory.openConnection();
            String sql = ("DELETE FROM LOGIN WHERE LOGIN_USU = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mLogin.getLOGIN_USU());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Login excluído da Base de Dados com sucesso.");
            }
            this.cleanLogin(vModuloAdministrativo);
            this.pesquisarTabela_Login(vModuloAdministrativo);
        }
        catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
        }
    }   

    /**
     *
     * @param vModuloAdministrativo
     * @param mLogin
     */
    public void atualizarLogin(view.ModuloAdministrativo vModuloAdministrativo, model.Login mLogin) {
        try{
            this.getLogin(vModuloAdministrativo, mLogin);
            controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
            connectionFactory.openConnection();
            String sql = ("UPDATE LOGIN SET LOGIN_USU = ?, NOME_USU = ?, SENHA_USU = PASSWORD(?) WHERE LOGIN_USU = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mLogin.getLOGIN_USU());
                preparedStatement.setString(2, mLogin.getNOME_USU());
                preparedStatement.setString(3, mLogin.getSENHA_USU());
                preparedStatement.setString(4, (String) vModuloAdministrativo.tableLogin.getValueAt(vModuloAdministrativo.tableLogin.getSelectedRow(), 0));
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Login atualizado com sucesso.");
            }
            this.cleanLogin(vModuloAdministrativo);
            this.pesquisarTabela_Login(vModuloAdministrativo);
        }
        catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
        }
    }

    /**
     *
     * @param vModuloAdministrativo
     * @return
     */
    public ArrayList pesquisarTabela_Login(view.ModuloAdministrativo vModuloAdministrativo) {
        try{
            controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
            connectionFactory.openConnection();
            String sql = ("SELECT LOGIN_USU, NOME_USU FROM LOGIN");
            ArrayList <model.Login> login = new ArrayList <> ();
            
            PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                model.Login l = new model.Login();
                l.setLOGIN_USU(resultSet.getString("LOGIN_USU"));
                l.setNOME_USU(resultSet.getString("NOME_USU"));
                login.add(l);
            }
            Iterator it = login.iterator();
            DefaultTableModel m = (DefaultTableModel) vModuloAdministrativo.tableLogin.getModel();
            m.setNumRows(0);
            while(it.hasNext()){
                model.Login l = (model.Login)it.next();
                m.addRow(new Object[]{l.getLOGIN_USU(), l.getNOME_USU()});
                
            }
            return login;
        }
        catch(SQLException sqlException){
            sqlException.getMessage();
        }
        return null;
    }
    
    public void getLoginrow(view.ModuloAdministrativo vModuloAdministrativo, model.Login mLogin){
        mLogin.setLOGIN_USU((String) vModuloAdministrativo.tableLogin.getModel().getValueAt(vModuloAdministrativo.tableLogin.getSelectedRow(), 0));
        mLogin.setNOME_USU((String) vModuloAdministrativo.tableLogin.getModel().getValueAt(vModuloAdministrativo.tableLogin.getSelectedRow(), 1));
        vModuloAdministrativo.LOGIN_USU.setText(mLogin.getLOGIN_USU());
        vModuloAdministrativo.NOME_USU.setText(mLogin.getNOME_USU());
    }

}