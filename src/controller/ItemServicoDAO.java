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
public class ItemServicoDAO {
    private static final Logger LOG = getLogger(ItemServicoDAO.class.getName());
    
    /**
     *
     * @param vModuloCliente
     */
    public void cleanItemServico(view.ModuloCliente vModuloCliente){ 
    vModuloCliente.ITEMSER_DES_SER.setSelectedItem(null);
    vModuloCliente.ITEMSER_COD_NOTFIS.setSelectedItem(null);
    vModuloCliente.QTD_ITEMSER.setText(null);
    vModuloCliente.VLR_ITEMSER.setText(null);
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    public void getItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal){
    mServico.setDES_SER(vModuloCliente.ITEMSER_DES_SER.getSelectedItem().toString());
    mNotaFiscal.setCOD_NOTFIS((Long) vModuloCliente.ITEMSER_COD_NOTFIS.getSelectedItem());
    mItemServico.setQTD_ITEMSER(parseInt(vModuloCliente.QTD_ITEMSER.getText()));
    mItemServico.setVLR_ITEMSER(parseDouble(vModuloCliente.VLR_ITEMSER.getText()));
    }
 
    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    public void adicionarItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal) { 
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("INSERT INTO ITEMSERVICO VALUES (?, ?, ?, ?)"); 
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    
    preparedStatement.setLong(1, mServico.getCOD_SER());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setInt(3, mItemServico.getQTD_ITEMSER());
    preparedStatement.setDouble(4, mItemServico.getVLR_ITEMSER());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Serviço incluso à Nota Fiscal com sucesso.");
            
    this.cleanItemServico(vModuloCliente);
    this.pesquisarTabela_ItemServico(vModuloCliente, mItemServico, mServico, mNotaFiscal);
    }
    catch(SQLException | NumberFormatException | NullPointerException sQLException){
    showMessageDialog(null, sQLException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    public void pesquisarItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal){
    this.cleanItemServico(vModuloCliente);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT SER.DES_SER, ITEMSER.NOTAFISCAL_COD_NOTFIS, ITEMSER.QTD_ITEMSER, ITEMSER.VLR_ITEMSER\n" +
                    "FROM ITEMSERVICO ITEMSER\n" +
                    "INNER JOIN SERVICO SER\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "WHERE ITEMSER.SERVICO_COD_SER = SER.COD_SER \n" +
                    "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                    "AND SER.DES_SER = ?\n" +
                    "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);

    preparedStatement.setString(1, vModuloCliente.PES_ITEMSER_DES_SER.getSelectedItem().toString());
    preparedStatement.setString(2,vModuloCliente.PES_ITEMSER_COD_NOTFIS.getSelectedItem().toString());
    
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    vModuloCliente.ITEMSER_DES_SER.setSelectedItem(resultSet.getString("SER.DES_SER"));   
    vModuloCliente.ITEMSER_COD_NOTFIS.setSelectedItem(resultSet.getString("ITEMSER.NOTAFISCAL_COD_NOTFIS"));
    vModuloCliente.QTD_ITEMSER.setText(resultSet.getString("ITEMSER.QTD_ITEMSER"));
    vModuloCliente.VLR_ITEMSER.setText(resultSet.getString("ITEMSER.VLR_ITEMSER"));
    }
    else{
    showMessageDialog(null, "Item de Serviço não encontrado.");
    }

    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }}

    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    
    public void excluirItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("DELETE FROM ITEMSERVICO WHERE SERVICO_COD_SER = ? AND NOTAFISCAL_COD_NOTFIS = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    preparedStatement.setLong(1,mServico.getCOD_SER());
    preparedStatement.setString(2,vModuloCliente.PES_ITEMSER_COD_NOTFIS.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Item de Serviço excluído da Base de Dados com sucesso.");
    
    this.cleanItemServico(vModuloCliente);
    this.pesquisarTabela_ItemServico(vModuloCliente, mItemServico, mServico, mNotaFiscal);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    public void atualizarItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal){
    try{
    getItemServico(vModuloCliente, mItemServico, mServico, mNotaFiscal);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("UPDATE ITEMSERVICO SET SERVICO_COD_SER = ?, NOTAFISCAL_COD_NOTFIS = ?, QTD_ITEMSER = ?, VLR_ITEMSER = ? WHERE SERVICO_COD_SER = ? AND NOTAFISCAL_COD_NOTFIS = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    
    preparedStatement.setLong(1, mServico.getCOD_SER());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setInt(3, mItemServico.getQTD_ITEMSER());
    preparedStatement.setDouble(4, mItemServico.getVLR_ITEMSER());
    
    preparedStatement.setLong(5, mServico.getCOD_SER());
    preparedStatement.setString(6,vModuloCliente.PES_ITEMSER_COD_NOTFIS.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Item de Serviço atualizado com sucesso.");
    
    this.cleanItemServico(vModuloCliente);
    this.pesquisarTabela_ItemServico(vModuloCliente, mItemServico, mServico, mNotaFiscal);
    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mItemServico
     * @param mServico
     * @param mNotaFiscal
     */
    public void pesquisarTabela_ItemServico(view.ModuloCliente vModuloCliente, model.ItemServico mItemServico, model.Servico mServico, model.NotaFiscal mNotaFiscal){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT ITEMSER.NOTAFISCAL_COD_NOTFIS, SER.DES_SER, ITEMSER.QTD_ITEMSER, ITEMSER.VLR_ITEMSER\n" +
                    "FROM ITEMSERVICO ITEMSER\n" +
                    "INNER JOIN SERVICO SER\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "WHERE ITEMSER.NOTAFISCAL_COD_NOTFIS = ?\n" +
                    "AND ITEMSER.SERVICO_COD_SER = SER.COD_SER\n" +
                    "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS");
    
    ArrayList <model.Servico> servico = new ArrayList <> ();
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    ArrayList <model.ItemServico> itemServico = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    preparedStatement.setString(1, vModuloCliente.FIL_ITEMSER_COD_NOTFIS.getSelectedItem().toString());
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){
    model.Servico s = new model.Servico();
    model.NotaFiscal nf = new model.NotaFiscal();
    model.ItemServico is = new model.ItemServico();
    nf.setCOD_NOTFIS(resultSet.getLong("ITEMSER.NOTAFISCAL_COD_NOTFIS"));
    s.setDES_SER(resultSet.getString("SER.DES_SER"));
    is.setQTD_ITEMSER(parseInt(resultSet.getString("ITEMSER.QTD_ITEMSER")));
    is.setVLR_ITEMSER(parseDouble(resultSet.getString("ITEMSER.VLR_ITEMSER")));
    notafiscal.add(nf);
    servico.add(s);
    itemServico.add(is);
    }
    Iterator it1 = notafiscal.iterator();
    Iterator it2 = servico.iterator();
    Iterator it3 = itemServico.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloCliente.tableItemServico.getModel();
    m.setNumRows(0);
    while(it1.hasNext()&&it2.hasNext()&&it3.hasNext()){
    model.NotaFiscal nf = (model.NotaFiscal)it1.next();
    model.Servico s = (model.Servico)it2.next();
    model.ItemServico is = (model.ItemServico)it3.next();
    m.addRow(new Object[]{nf.getCOD_NOTFIS(), s.getDES_SER(), is.getQTD_ITEMSER(), is.getVLR_ITEMSER()});
    }
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mServico
     */
    public void calcularItemServico(view.ModuloCliente vModuloCliente, model.Servico mServico){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("SELECT ROUND( SER.VLR_SER * ?, 2 ) AS VALOR\n" +
                    "FROM SERVICO SER\n"+
                    "WHERE SER.COD_SER = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, vModuloCliente.ITEMSER_DES_SER.getSelectedItem().toString());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
     
    preparedStatement.setString(1,vModuloCliente.QTD_ITEMSER.getText());
    preparedStatement.setLong(2, mServico.getCOD_SER());
    preparedStatement.executeQuery();
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
    vModuloCliente.VLR_ITEMSER.setText(resultSet.getString("VALOR"));   
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
     * @param x
     */
    public void ComboBoxServiço(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT DES_SER FROM SERVICO");
    
    ArrayList <model.Servico> servico = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Servico s = new model.Servico();
    s.setDES_SER(resultSet.getString("DES_SER"));
    servico.add(s);
    }
    Iterator it = servico.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.Servico s = (model.Servico)it.next();  
    x.addItem((s.getDES_SER()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
}
