package controller;

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
public class CategoriaDAO {
    private static final Logger LOG = getLogger(CategoriaDAO.class.getName());
    
    /**
     *
     *  @param vModuloAdministrativo
     */
    public void cleanCategoria(view.ModuloAdministrativo vModuloAdministrativo){ 
    vModuloAdministrativo.DES_CAT.setText(null);
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mCategoria
     */
    public void getCategoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
    mCategoria.setDES_CAT(vModuloAdministrativo.DES_CAT.getText());
    }
 
    /**
     *
     * @param vModuloAdministrativo
     * @param mCategoria
     */
    public void adicionarCategoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria) {  
    if(mCategoria.getDES_CAT().equalsIgnoreCase("")){
    showMessageDialog(vModuloAdministrativo, "Os campos estão estão vazios.", "Atenção", 2);
    }
    else{
    try{
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("INSERT INTO CATEGORIA (DES_CAT) VALUES (?)"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mCategoria.getDES_CAT());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Categoria inclusa com sucesso.");
            }
    this.comboCategoria(vModuloAdministrativo.PROD_DES_CAT);
    this.cleanCategoria(vModuloAdministrativo);
    this.pesquisarTabela_Categoria(vModuloAdministrativo, mCategoria);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mCategoria
     */
    
    public void excluirCategoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
    try{
    mCategoria.setCOD_CAT((long) vModuloAdministrativo.tableCategoria.getValueAt(vModuloAdministrativo.tableCategoria.getSelectedRow(), 0));
    
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("DELETE FROM CATEGORIA WHERE COD_CAT = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    preparedStatement.setLong(1, mCategoria.getCOD_CAT());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Categoria excluída da Base de Dados com sucesso.");
    
    this.comboCategoria(vModuloAdministrativo.PROD_DES_CAT);
    this.cleanCategoria(vModuloAdministrativo);
    this.pesquisarTabela_Categoria(vModuloAdministrativo, mCategoria);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mCategoria
     */
    public void atualizarCategoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
    try{
    mCategoria.setCOD_CAT((long)vModuloAdministrativo.tableCategoria.getModel().getValueAt(vModuloAdministrativo.tableCategoria.getSelectedRow(), 0));
    
    getCategoria(vModuloAdministrativo, mCategoria);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("UPDATE CATEGORIA SET DES_CAT = ? WHERE COD_CAT = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mCategoria.getDES_CAT());
                preparedStatement.setLong(2, mCategoria.getCOD_CAT());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Categoria atualizada com sucesso.");
            }
    this.comboCategoria(vModuloAdministrativo.PROD_DES_CAT);
    this.cleanCategoria(vModuloAdministrativo);
    this.pesquisarTabela_Categoria(vModuloAdministrativo, mCategoria);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mCategoria
     * @return
     */
    public ArrayList pesquisarTabela_Categoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT COD_CAT, DES_CAT FROM CATEGORIA");
    ArrayList <model.Categoria> categoria = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Categoria c = new model.Categoria();
    c.setCOD_CAT(resultSet.getLong("COD_CAT"));
    c.setDES_CAT(resultSet.getString("DES_CAT"));
    categoria.add(c);
    }
    Iterator it = categoria.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloAdministrativo.tableCategoria.getModel();
    m.setNumRows(0);
    while(it.hasNext()){
    model.Categoria c = (model.Categoria)it.next();
    m.addRow(new Object[]{c.getCOD_CAT(), c.getDES_CAT()});
    }
    return categoria;
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    return null;
    }
    
    public void comboCategoria(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_CAT, DES_CAT FROM CATEGORIA ORDER BY DES_CAT");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    x.removeAllItems();
    while(resultSet.next()){
    model.Categoria mCategoria = new model.Categoria();
    mCategoria.setCOD_CAT(resultSet.getLong("COD_CAT"));
    mCategoria.setDES_CAT(resultSet.getString("DES_CAT"));
    x.addItem(new model.Categoria(mCategoria.getCOD_CAT(), mCategoria.getDES_CAT()));
    }
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    public void getCategoriarow(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
        mCategoria.setCOD_CAT((long) vModuloAdministrativo.tableCategoria.getValueAt(vModuloAdministrativo.tableCategoria.getSelectedRow(), 0));
        mCategoria.setDES_CAT((String) vModuloAdministrativo.tableCategoria.getValueAt(vModuloAdministrativo.tableCategoria.getSelectedRow(), 1));
        
        vModuloAdministrativo.DES_CAT.setText(mCategoria.getDES_CAT());
    }
    
    /*public void getNomeCategoria(view.ModuloAdministrativo vModuloAdministrativo, model.Categoria mCategoria){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT DES_CAT FROM CATEGORIA WHERE COD_CAT = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    preparedStatement.setLong(1, (long)vModuloAdministrativo.tableProduto.getValueAt(vModuloAdministrativo.tableProduto.getSelectedRow(), 2));
    ResultSet resultSet = preparedStatement.executeQuery();
   
            
    while(resultSet.next()){
    model.Categoria mCategoria = new model.Categoria();
    mCategoria.setCOD_CAT(resultSet.getLong("COD_CAT"));
    mCategoria.setDES_CAT(resultSet.getString("DES_CAT"));
    x.addItem(new model.Categoria(mCategoria.getCOD_CAT(), mCategoria.getDES_CAT()));
    }  
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }    
    }*/
}