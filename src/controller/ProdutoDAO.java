package controller;

import static java.lang.Double.parseDouble;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static java.lang.Integer.parseInt;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class ProdutoDAO {
    
    private static final Logger LOG = getLogger(ProdutoDAO.class.getName());
    
    /**
     *
     * @param vModuloAdministrativo
     */
    public void cleanProduto(view.ModuloAdministrativo vModuloAdministrativo){
    vModuloAdministrativo.PROD_DES_CAT.setSelectedIndex(0);
    vModuloAdministrativo.PROD_NOME_FOR.setSelectedIndex(0);
    vModuloAdministrativo.NOME_PROD.setText(null);
    vModuloAdministrativo.MARCA_PROD.setText(null);
    vModuloAdministrativo.QTD_PROD.setText(null);
    vModuloAdministrativo.VLRUNID_PROD.setText(null);
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mProduto
     * @param mCategoria
     * @param mFornecedor
     */
    public void getProduto(view.ModuloAdministrativo vModuloAdministrativo, model.Produto mProduto, model.Categoria mCategoria, model.Fornecedor mFornecedor){
    mFornecedor.setCOD_FOR(((model.Fornecedor)vModuloAdministrativo.PROD_NOME_FOR.getSelectedItem()).getCOD_FOR());
    mCategoria.setCOD_CAT(((model.Categoria)vModuloAdministrativo.PROD_DES_CAT.getSelectedItem()).getCOD_CAT());
    mProduto.setNOME_PROD(vModuloAdministrativo.NOME_PROD.getText());
    mProduto.setMARCA_PROD(vModuloAdministrativo.MARCA_PROD.getText());
    try{
        mProduto.setQTD_PROD(parseInt(vModuloAdministrativo.QTD_PROD.getText()));
        mProduto.setVLRUNID_PROD(parseDouble(vModuloAdministrativo.VLRUNID_PROD.getText()));
    }catch(NumberFormatException e){
        mProduto.setQTD_PROD(0);
        mProduto.setVLRUNID_PROD(0.00);
        //showMessageDialog(null, e.getMessage());
    }
    }
 
    /**
     *
     * @param vModuloAdministrativo
     * @param mProduto
     * @param mCategoria
     * @param mFornecedor
     */
    public void adicionarProduto(view.ModuloAdministrativo vModuloAdministrativo, model.Produto mProduto, model.Categoria mCategoria, model.Fornecedor mFornecedor) {
    if(mProduto.getNOME_PROD().equalsIgnoreCase("") || mProduto.getQTD_PROD() == 0 || mProduto.getVLRUNID_PROD() == 0.00){
    showMessageDialog(vModuloAdministrativo, "Os principais campos estão vazios.", "Atenção", 2);
    } else{  
    try{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.openConnection();
    
        String sql = ("INSERT INTO PRODUTO (CATEGORIA_COD_CAT, FORNECEDOR_COD_FOR, NOME_PROD, MARCA_PROD, QTD_PROD, VLRUNID_PROD) VALUES (?, ?, ?, ?, ?, ?)"); 
        PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
                preparedStatement.setLong(1, mCategoria.getCOD_CAT());
                preparedStatement.setLong(2, mFornecedor.getCOD_FOR());
                preparedStatement.setString(3, mProduto.getNOME_PROD());
                preparedStatement.setString(4, mProduto.getMARCA_PROD());
                preparedStatement.setInt(5, mProduto.getQTD_PROD());
                preparedStatement.setDouble(6, mProduto.getVLRUNID_PROD());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Produto incluso com sucesso.");
    this.cleanProduto(vModuloAdministrativo);
    this.pesquisarTabela_Produto(vModuloAdministrativo, mProduto);
    }
    catch(SQLException | NumberFormatException sQLException){
    showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mProduto
     * @param mCategoria
     * @param mFornecedor
     */
    
    public void excluirProduto(view.ModuloAdministrativo vModuloAdministrativo, model.Produto mProduto, model.Categoria mCategoria, model.Fornecedor mFornecedor){
    try{
    mProduto.setCOD_PROD((long) vModuloAdministrativo.tableProduto.getValueAt(vModuloAdministrativo.tableProduto.getSelectedRow(), 0));    
        
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("DELETE FROM PRODUTO WHERE COD_PROD = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    preparedStatement.setLong(1, mProduto.getCOD_PROD());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Produto excluído da Base de Dados com sucesso.");
           
    this.cleanProduto(vModuloAdministrativo);
    this.pesquisarTabela_Produto(vModuloAdministrativo, mProduto);
    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mProduto
     * @param mCategoria
     * @param mFornecedor
     */
    public void atualizarProduto(view.ModuloAdministrativo vModuloAdministrativo, model.Produto mProduto, model.Categoria mCategoria, model.Fornecedor mFornecedor){
    try{
    mProduto.setCOD_PROD((long) vModuloAdministrativo.tableProduto.getValueAt(vModuloAdministrativo.tableProduto.getSelectedRow(), 0));
    getProduto(vModuloAdministrativo, mProduto, mCategoria, mFornecedor);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("UPDATE PRODUTO SET CATEGORIA_COD_CAT = ?, FORNECEDOR_COD_FOR = ?, NOME_PROD = ?, MARCA_PROD = ?, QTD_PROD = ?, VLRUNID_PROD = ? WHERE COD_PROD = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
                preparedStatement.setLong(1, mCategoria.getCOD_CAT());
                preparedStatement.setLong(2, mFornecedor.getCOD_FOR());
                preparedStatement.setString(3, mProduto.getNOME_PROD());
                preparedStatement.setString(4, mProduto.getMARCA_PROD());
                preparedStatement.setInt(5, mProduto.getQTD_PROD());
                preparedStatement.setDouble(6, mProduto.getVLRUNID_PROD());
                preparedStatement.setLong(7, mProduto.getCOD_PROD());
                
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Produto atualizado com sucesso.");
            
    this.cleanProduto(vModuloAdministrativo);
    this.pesquisarTabela_Produto(vModuloAdministrativo, mProduto);
    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }
    }
    
        /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     * @param mCategoria
     * @param mProduto
     * @param getRow
     */
    public void pesquisarProduto(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor, model.Categoria mCategoria, model.Produto mProduto, String getRow){
    this.cleanProduto(vModuloAdministrativo);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT PROD.COD_PROD, PROD.NOME_PROD, CAT.COD_CAT, CAT.DES_CAT, FORN.COD_FOR, FORN.NOME_FOR, PROD.MARCA_PROD, PROD.QTD_PROD, PROD.VLRUNID_PROD "
            + "FROM PRODUTO PROD INNER JOIN CATEGORIA CAT INNER JOIN FORNECEDOR FORN "
            + "WHERE PROD.CATEGORIA_COD_CAT = CAT.COD_CAT "
            + "AND PROD.FORNECEDOR_COD_FOR = FORN.COD_FOR AND PROD.COD_PROD = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    if(getRow.equalsIgnoreCase("sim")){
        mProduto.setCOD_PROD((long) vModuloAdministrativo.tableProduto.getValueAt(vModuloAdministrativo.tableProduto.getSelectedRow(), 0));
    } else{
        //mProduto = (Produto)vModuloAdministrativo..getSelectedItem();
    }
    
    preparedStatement.setLong(1, mProduto.getCOD_PROD());
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
        
    mProduto.setCOD_PROD(resultSet.getLong("PROD.COD_PROD"));
    mProduto.setNOME_PROD(resultSet.getString("PROD.NOME_PROD"));
    mFornecedor.setCOD_FOR(resultSet.getLong("FORN.COD_FOR"));
    mFornecedor.setNOME_FOR(resultSet.getString("FORN.NOME_FOR"));
    mCategoria.setCOD_CAT(resultSet.getLong("CAT.COD_CAT"));
    mCategoria.setDES_CAT(resultSet.getString("CAT.DES_CAT"));
    mProduto.setMARCA_PROD(resultSet.getString("PROD.MARCA_PROD"));
    mProduto.setQTD_PROD(parseInt(resultSet.getString("PROD.QTD_PROD")));
    mProduto.setVLRUNID_PROD(parseDouble(resultSet.getString("PROD.VLRUNID_PROD")));    
    
    vModuloAdministrativo.NOME_PROD.setText(mProduto.getNOME_PROD());
    vModuloAdministrativo.PROD_NOME_FOR.getModel().setSelectedItem(new model.Fornecedor(mFornecedor.getCOD_FOR(), mFornecedor.getNOME_FOR()));
    vModuloAdministrativo.PROD_DES_CAT.getModel().setSelectedItem(new model.Categoria(mCategoria.getCOD_CAT(), mCategoria.getDES_CAT()));
    vModuloAdministrativo.MARCA_PROD.setText(mProduto.getMARCA_PROD());
    vModuloAdministrativo.QTD_PROD.setText(String.valueOf(mProduto.getQTD_PROD()));
    vModuloAdministrativo.VLRUNID_PROD.setText(String.valueOf(mProduto.getVLRUNID_PROD()));
    }
    else{
        showMessageDialog(null, "Produto não encontrado.");
    }

    }
    catch(SQLException sqlException){
        showMessageDialog(null, sqlException.getMessage());
    }}
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mProduto
     */
    public void pesquisarTabela_Produto(view.ModuloAdministrativo vModuloAdministrativo, model.Produto mProduto){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT PROD.COD_PROD, PROD.NOME_PROD, CAT.DES_CAT, PROD.QTD_PROD, PROD.VLRUNID_PROD FROM PRODUTO PROD INNER JOIN CATEGORIA CAT\n" +
                  "WHERE PROD.CATEGORIA_COD_CAT = CAT.COD_CAT");
    ArrayList <model.Produto> produto = new ArrayList <> ();
    ArrayList <model.Categoria> categoria = new ArrayList <> ();
    ArrayList <model.Fornecedor> fornecedor = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Produto p = new model.Produto();
    model.Categoria c = new model.Categoria();
    p.setCOD_PROD(resultSet.getLong("PROD.COD_PROD"));
    p.setNOME_PROD(resultSet.getString("PROD.NOME_PROD"));
    c.setDES_CAT(resultSet.getString("CAT.DES_CAT"));
    p.setQTD_PROD(parseInt(resultSet.getString("PROD.QTD_PROD")));
    p.setVLRUNID_PROD(parseDouble(resultSet.getString("PROD.VLRUNID_PROD")));
    produto.add(p);
    categoria.add(c);
    }
    Iterator it1 = produto.iterator();
    Iterator it2 = categoria.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloAdministrativo.tableProduto.getModel();
    m.setNumRows(0);
    while(it1.hasNext()&&it2.hasNext()){
    model.Produto p = (model.Produto)it1.next();
    model.Categoria c = (model.Categoria)it2.next();
    m.addRow(new Object[]{p.getCOD_PROD(), p.getNOME_PROD(), c.getDES_CAT(), p.getQTD_PROD(), p.getVLRUNID_PROD()});
    }
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    /**
     *
     * @param x
     */
    public void ComboBoxProduto(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_PROD FROM PRODUTO");
    
    ArrayList <model.Produto> produto = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Produto p = new model.Produto();
   // p.setCOD_PROD(resultSet.getString("COD_PROD"));
    produto.add(p);
    }
    Iterator it = produto.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.Produto p = (model.Produto)it.next();  
    x.addItem((p.getCOD_PROD()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    public void comboProduto(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_PROD, NOME_PROD FROM PRODUTO");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    x.removeAllItems();
    while(resultSet.next()){
    model.Produto mProduto = new model.Produto();
    mProduto.setCOD_PROD(resultSet.getLong("COD_PROD"));
    mProduto.setNOME_PROD(resultSet.getString("NOME_PROD"));
    x.addItem(new model.Produto(mProduto.getCOD_PROD(), mProduto.getNOME_PROD()));
    }  
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    public void getProdutorow(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor, model.Categoria mCategoria, model.Produto mProduto){
    this.pesquisarProduto(vModuloAdministrativo, mFornecedor, mCategoria, mProduto, "sim");
    }
}