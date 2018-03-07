package controller;

import static java.lang.Double.parseDouble;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class ServicoDAO {
    private static final Logger LOG = getLogger(ServicoDAO.class.getName());
    
    /**
     *
     * @param vModuloAdministrativo
     */
    public void cleanServico(view.ModuloAdministrativo vModuloAdministrativo){
    vModuloAdministrativo.DES_SER.setText(null);
    vModuloAdministrativo.VLR_SER.setText(null);
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     */
    public void getServico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico){
    mServico.setDES_SER(vModuloAdministrativo.DES_SER.getText());
    mServico.setVLR_SER(parseDouble(vModuloAdministrativo.VLR_SER.getText()));
    }
 
    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     */
    public void adicionarServico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico) {  
    if(mServico.getDES_SER().equalsIgnoreCase("")){
    showMessageDialog(vModuloAdministrativo, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{  
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("INSERT INTO SERVICO (DES_SER, VLR_SER) VALUES (?, ?)"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mServico.getDES_SER());
                preparedStatement.setDouble(2, mServico.getVLR_SER());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Serviço incluso com sucesso.");
            }
    this.cleanServico(vModuloAdministrativo);
    this.pesquisarTabela_Servico(vModuloAdministrativo, mServico);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     * @param getRow
     */
    public void pesquisarServico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico, String getRow){
    this.cleanServico(vModuloAdministrativo);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_SER, DES_SER, VLR_SER FROM SERVICO WHERE COD_SER = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    if(getRow.equalsIgnoreCase("sim")){
        mServico.setCOD_SER((long) vModuloAdministrativo.tableServico.getValueAt(vModuloAdministrativo.tableServico.getSelectedRow(), 0));
    } else{
        //mProduto = (Produto)vModuloAdministrativo..getSelectedItem();
    }

    preparedStatement.setLong(1, mServico.getCOD_SER());
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    mServico.setCOD_SER(resultSet.getLong("COD_SER"));
    mServico.setDES_SER(resultSet.getString("DES_SER"));
    mServico.setVLR_SER(resultSet.getDouble("VLR_SER"));
     
    vModuloAdministrativo.DES_SER.setText(mServico.getDES_SER());
    vModuloAdministrativo.VLR_SER.setText(String.valueOf(mServico.getVLR_SER()));
    }
    else{
        showMessageDialog(null, "Serviço não encontrado.");
    }

    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }}
    /*Onde Parei*/

    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     */
    
    public void excluirServico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("DELETE FROM SERVICO WHERE COD_SER = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
//                preparedStatement.setString(1,vModuloAdministrativo.PES_COD_SER.getSelectedItem().toString());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Serviço excluído da Base de Dados com sucesso.");
            }
//    this.ComboBoxServico(vModuloAdministrativo.PES_COD_SER);        
    this.cleanServico(vModuloAdministrativo);
    this.pesquisarTabela_Servico(vModuloAdministrativo, mServico);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     */
    public void atualizarServico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico){
    try{
    getServico(vModuloAdministrativo, mServico);
    mServico.setCOD_SER((long) vModuloAdministrativo.tableServico.getValueAt(vModuloAdministrativo.tableServico.getSelectedRow(), 0));
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("UPDATE SERVICO SET DES_SER = ?, VLR_SER = ? WHERE COD_SER = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mServico.getDES_SER());
                preparedStatement.setDouble(2, mServico.getVLR_SER());
                preparedStatement.setLong(3, mServico.getCOD_SER());
//                preparedStatement.setString(4, vModuloAdministrativo.PES_COD_SER.getSelectedItem().toString());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Serviço atualizado com sucesso.");
            }
 //   this.ComboBoxServico(vModuloAdministrativo.PES_COD_SER);        
    this.cleanServico(vModuloAdministrativo);
    this.pesquisarTabela_Servico(vModuloAdministrativo, mServico);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mServico
     * @return
     */
    public ArrayList pesquisarTabela_Servico(view.ModuloAdministrativo vModuloAdministrativo, model.Servico mServico){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT COD_SER, DES_SER, VLR_SER FROM SERVICO");
    ArrayList <model.Servico> servico = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Servico s = new model.Servico();
    s.setCOD_SER(resultSet.getLong("COD_SER"));
    s.setDES_SER(resultSet.getString("DES_SER"));
    s.setVLR_SER(parseDouble(resultSet.getString("VLR_SER")));
    servico.add(s);
    }
    Iterator it = servico.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloAdministrativo.tableServico.getModel();
    m.setNumRows(0);
    while(it.hasNext()){
    model.Servico s = (model.Servico)it.next();
    m.addRow(new Object[]{s.getCOD_SER(), s.getDES_SER(), s.getVLR_SER()});
    }
    return servico;
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    return null;
    }
    
    /**
     *
     * @param x
     */
    public void ComboBoxServico(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_SER FROM SERVICO");
    
    ArrayList <model.Servico> servico = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Servico s = new model.Servico();
    s.setCOD_SER(resultSet.getLong("COD_SER"));
    servico.add(s);
    }
    Iterator it = servico.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.Servico s = (model.Servico)it.next();  
    x.addItem((s.getCOD_SER()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
}
