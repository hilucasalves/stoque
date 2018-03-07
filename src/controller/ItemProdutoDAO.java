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
public class ItemProdutoDAO {
    private static final Logger LOG = getLogger(ItemProdutoDAO.class.getName());
    
    /**
     *
     * @param vModuloCliente
     */
    public void cleanItemProduto(view.ModuloCliente vModuloCliente){ 
    vModuloCliente.ITEMPROD_NOME_PROD.setSelectedItem(null);
    vModuloCliente.ITEMPROD_COD_NOTFIS.setSelectedItem(null);
    vModuloCliente.QTD_ITEMPROD.setText(null);
    vModuloCliente.VLR_ITEMPROD.setText(null);
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void getItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal){
    mProduto.setNOME_PROD(vModuloCliente.ITEMPROD_NOME_PROD.getSelectedItem().toString());
    mNotaFiscal.setCOD_NOTFIS((Long) vModuloCliente.ITEMPROD_COD_NOTFIS.getSelectedItem());
    mItemProduto.setQTD_ITEMPROD(parseInt(vModuloCliente.QTD_ITEMPROD.getText()));
    mItemProduto.setVLR_ITEMPROD(parseDouble(vModuloCliente.VLR_ITEMPROD.getText()));
    }
 
    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void adicionarItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal) {  
    if(mProduto.getNOME_PROD().equalsIgnoreCase("")||mNotaFiscal.getCOD_NOTFIS().equals("")){
    showMessageDialog(vModuloCliente, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{   
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlProduto = ("SELECT COD_PROD FROM PRODUTO WHERE NOME_PROD = ?");
    
    String sql = ("INSERT INTO ITEMPRODUTO VALUES (?, ?, ?, ?)");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlProduto);
    
    pSproduto.setString(1, mProduto.getNOME_PROD());
    
    ResultSet rSproduto = pSproduto.executeQuery();
    
    while(rSproduto.next()){
    mProduto.setCOD_PROD(rSproduto.getLong("COD_PROD"));
    }
    
    preparedStatement.setLong(1, mProduto.getCOD_PROD());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setInt(3, mItemProduto.getQTD_ITEMPROD());
    preparedStatement.setDouble(4, mItemProduto.getVLR_ITEMPROD());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Produto incluso à Nota Fiscal com sucesso.");
    
    this.cleanItemProduto(vModuloCliente);
    this.pesquisarTabela_ItemProduto(vModuloCliente, mItemProduto, mProduto, mNotaFiscal);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void pesquisarItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal){
    this.cleanItemProduto(vModuloCliente);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT PROD.NOME_PROD, ITEMPROD.NOTAFISCAL_COD_NOTFIS, ITEMPROD.QTD_ITEMPROD, ITEMPROD.VLR_ITEMPROD\n" +
                    "FROM ITEMPRODUTO ITEMPROD\n" +
                    "INNER JOIN PRODUTO PROD\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "WHERE ITEMPROD.PRODUTO_COD_PROD = PROD.COD_PROD \n" +
                    "AND ITEMPROD.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                    "AND PROD.NOME_PROD = ?\n" +
                    "AND ITEMPROD.NOTAFISCAL_COD_NOTFIS = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    preparedStatement.setString(1,vModuloCliente.PES_ITEMPROD_NOME_PROD.getSelectedItem().toString());
    preparedStatement.setString(2,vModuloCliente.PES_ITEMPROD_COD_NOTFIS.getSelectedItem().toString());
    
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    vModuloCliente.ITEMPROD_NOME_PROD.setSelectedItem(resultSet.getString("PROD.NOME_PROD"));   
    vModuloCliente.ITEMPROD_COD_NOTFIS.setSelectedItem(resultSet.getString("ITEMPROD.NOTAFISCAL_COD_NOTFIS"));
    vModuloCliente.QTD_ITEMPROD.setText(resultSet.getString("ITEMPROD.QTD_ITEMPROD"));
    vModuloCliente.VLR_ITEMPROD.setText(resultSet.getString("ITEMPROD.VLR_ITEMPROD"));
    }
    else{
    showMessageDialog(null, "Item de Produto não encontrado.");
    }

    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }}

    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void excluirItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlProduto = ("SELECT COD_PROD FROM PRODUTO WHERE NOME_PROD = ?");
    
    String sql = ("DELETE FROM ITEMPRODUTO WHERE PRODUTO_COD_PROD = ? AND NOTAFISCAL_COD_NOTFIS = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlProduto);
    
    pSproduto.setString(1, mProduto.getNOME_PROD());
    
    ResultSet rSproduto = pSproduto.executeQuery();
    
    while(rSproduto.next()){
    mProduto.setCOD_PROD(rSproduto.getLong("COD_PROD"));
    }
    
    preparedStatement.setLong(1, mProduto.getCOD_PROD());
    preparedStatement.setString(2,vModuloCliente.PES_ITEMPROD_COD_NOTFIS.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Item de Produto excluído da Base de Dados com sucesso.");
    
    this.cleanItemProduto(vModuloCliente);
    this.pesquisarTabela_ItemProduto(vModuloCliente, mItemProduto, mProduto, mNotaFiscal);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void atualizarItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal){
    try{
    getItemProduto(vModuloCliente, mItemProduto, mProduto, mNotaFiscal);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlProduto = ("SELECT COD_PROD FROM PRODUTO WHERE NOME_PROD = ?");
    
    String sql = ("UPDATE ITEMPRODUTO SET PRODUTO_COD_PROD = ?, NOTAFISCAL_COD_NOTFIS = ?, QTD_ITEMPROD = ?, VLR_ITEMPROD = ? WHERE PRODUTO_COD_PROD = ? AND NOTAFISCAL_COD_NOTFIS = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlProduto);
    
    pSproduto.setString(1, mProduto.getNOME_PROD());
    
    ResultSet rSproduto = pSproduto.executeQuery();
    
    while(rSproduto.next()){
    mProduto.setCOD_PROD(rSproduto.getLong("COD_PROD"));
    }
    
    preparedStatement.setLong(1, mProduto.getCOD_PROD());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setInt(3, mItemProduto.getQTD_ITEMPROD());
    preparedStatement.setDouble(4, mItemProduto.getVLR_ITEMPROD());
    preparedStatement.setLong(5,mProduto.getCOD_PROD());
    preparedStatement.setString(6,vModuloCliente.PES_ITEMPROD_COD_NOTFIS.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Item de Produto atualizado com sucesso.");
    
    this.cleanItemProduto(vModuloCliente);
    this.pesquisarTabela_ItemProduto(vModuloCliente, mItemProduto, mProduto, mNotaFiscal);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemProduto
     * @param mProduto
     * @param mNotaFiscal
     */
    public void pesquisarTabela_ItemProduto(view.ModuloCliente vModuloCliente, model.ItemProduto mItemProduto, model.Produto mProduto, model.NotaFiscal mNotaFiscal){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT ITEMPROD.NOTAFISCAL_COD_NOTFIS, PROD.NOME_PROD, ITEMPROD.QTD_ITEMPROD, ITEMPROD.VLR_ITEMPROD\n" +
                    "FROM ITEMPRODUTO ITEMPROD\n" +
                    "INNER JOIN PRODUTO PROD\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "WHERE ITEMPROD.NOTAFISCAL_COD_NOTFIS = ?\n" +
                    "AND ITEMPROD.PRODUTO_COD_PROD = PROD.COD_PROD\n" +
                    "AND ITEMPROD.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS");
    
    ArrayList <model.Produto> produto = new ArrayList <> ();
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    ArrayList <model.ItemProduto> itemProduto = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    preparedStatement.setString(1, vModuloCliente.FIL_ITEMPROD_COD_NOTFIS.getSelectedItem().toString());
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){
    model.Produto p = new model.Produto();
    model.NotaFiscal nf = new model.NotaFiscal();
    model.ItemProduto ip = new model.ItemProduto();
    nf.setCOD_NOTFIS(resultSet.getLong("ITEMPROD.NOTAFISCAL_COD_NOTFIS"));
    p.setNOME_PROD(resultSet.getString("PROD.NOME_PROD"));
    ip.setQTD_ITEMPROD(parseInt(resultSet.getString("ITEMPROD.QTD_ITEMPROD")));
    ip.setVLR_ITEMPROD(parseDouble(resultSet.getString("ITEMPROD.VLR_ITEMPROD")));
    notafiscal.add(nf);
    produto.add(p);
    itemProduto.add(ip);
    }
    Iterator it1 = notafiscal.iterator();
    Iterator it2 = produto.iterator();
    Iterator it3 = itemProduto.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloCliente.tableItemProduto.getModel();
    m.setNumRows(0);
    while(it1.hasNext()&&it2.hasNext()&&it3.hasNext()){
    model.NotaFiscal nf = (model.NotaFiscal)it1.next();
    model.Produto p = (model.Produto)it2.next();
    model.ItemProduto ip = (model.ItemProduto)it3.next();
    m.addRow(new Object[]{nf.getCOD_NOTFIS(), p.getNOME_PROD(), ip.getQTD_ITEMPROD(), ip.getVLR_ITEMPROD()});
    }
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    } 
    
    /**
     *
     * @param vModuloCliente
     * @param mProduto
     */
    public void calcularItemProduto(view.ModuloCliente vModuloCliente, model.Produto mProduto){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlProduto = ("SELECT COD_PROD FROM PRODUTO WHERE NOME_PROD = ?");
    
    String sql = ("SELECT ROUND( PROD.VLRUNID_PROD * ?, 2 ) AS VALOR\n" +
                    "FROM PRODUTO PROD\n"+
                    "WHERE PROD.COD_PROD = ?");
   
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlProduto);
    
    pSproduto.setString(1, vModuloCliente.ITEMPROD_NOME_PROD.getSelectedItem().toString());
    
    ResultSet rSproduto = pSproduto.executeQuery();
    
    while(rSproduto.next()){
    mProduto.setCOD_PROD(rSproduto.getLong("COD_PROD"));
    }
                
    preparedStatement.setString(1,vModuloCliente.QTD_ITEMPROD.getText());
    preparedStatement.setLong(2,mProduto.getCOD_PROD());
    preparedStatement.executeQuery();
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
    vModuloCliente.VLR_ITEMPROD.setText(resultSet.getString("VALOR"));   
    }
    else{
    showMessageDialog(null, "Valor não calculado.");
    }
    }
    
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }

    /**
     *
     * @param vModuloCliente
     * @param mProduto
     */
    public void verificarProduto(view.ModuloCliente vModuloCliente, model.Produto mProduto){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlProduto = ("SELECT COD_PROD FROM PRODUTO WHERE NOME_PROD = ?");
    
    String sql = ("SELECT QTD_PROD FROM PRODUTO\n" +
                    "WHERE COD_PROD = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlProduto);
    
    pSproduto.setString(1, vModuloCliente.ITEMPROD_NOME_PROD.getSelectedItem().toString());
    
    ResultSet rSproduto = pSproduto.executeQuery();
    
    while(rSproduto.next()){
    mProduto.setCOD_PROD(rSproduto.getLong("COD_PROD"));
    }
                
    preparedStatement.setLong(1,mProduto.getCOD_PROD());
    preparedStatement.executeQuery();
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
        if(resultSet.getInt("QTD_PROD") < parseInt(vModuloCliente.QTD_ITEMPROD.getText())){
        showMessageDialog(vModuloCliente, "Quantidade Comprada maior que quantidade em estoque.\n"
                +"Quantidade Comprada: "+vModuloCliente.QTD_ITEMPROD.getText()+"\n"
                +"Quantidade em Estoque: "+resultSet.getString("QTD_PROD"), "Verificação", 1);
        }else{
        showMessageDialog(vModuloCliente, "Quantidade em Estoque:"+resultSet.getString("QTD_PROD"), "Verificação", 1);
        }    
    }
    else{
    showMessageDialog(null, "Quantidade não encontrada.");
    }
    }
    
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
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
    
    String sql = ("SELECT NOME_PROD FROM PRODUTO");
    
    ArrayList <model.Produto> produto = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Produto p = new model.Produto();
    p.setNOME_PROD(resultSet.getString("NOME_PROD"));
    produto.add(p);
    }
    Iterator it = produto.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.Produto p = (model.Produto)it.next();  
    x.addItem((p.getNOME_PROD()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
}
