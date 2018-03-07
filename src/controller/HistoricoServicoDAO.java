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
public class HistoricoServicoDAO {
    private static final Logger LOG = getLogger(HistoricoServicoDAO.class.getName());
    
    /**
     *
     * @param vModuloVendedor
     */
    public void cleanHistoricoServico(view.ModuloVendedor vModuloVendedor){ 
    //vModuloVendedor.COD_HISSER.setText(null);
    vModuloVendedor.HISSER_COD_NOTFIS.setSelectedItem(null);
    vModuloVendedor.HISSER_DES_SER.setSelectedItem(null);
    vModuloVendedor.DES_HISSER.setText(null);
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    public void getHistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico){
    //mHistoricoServico.setCOD_HISSER(vModuloVendedor.COD_HISSER.getText());
    mNotaFiscal.setCOD_NOTFIS((Long) vModuloVendedor.HISSER_COD_NOTFIS.getSelectedItem());
    mServico.setDES_SER(vModuloVendedor.HISSER_DES_SER.getSelectedItem().toString());
    mHistoricoServico.setDES_HISSER(vModuloVendedor.DES_HISSER.getText());
    }
 
    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    public void adicionarHistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico) {  
    if(mHistoricoServico.getCOD_HISSER().equalsIgnoreCase("")||mNotaFiscal.getCOD_NOTFIS().equals("")){
    showMessageDialog(vModuloVendedor, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{   
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("INSERT INTO HISTORICOSERVICO VALUES (?, ?, ?, ?)"); 
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    
    preparedStatement.setString(1, mHistoricoServico.getCOD_HISSER());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setLong(3, mServico.getCOD_SER());
    preparedStatement.setString(4, mHistoricoServico.getDES_HISSER());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Histórico de Serviço incluso com sucesso.");
    
    this.ComboBoxHistoricoServico(vModuloVendedor.PES_COD_HISSER);
    this.cleanHistoricoServico(vModuloVendedor);
    this.pesquisarTabela_HistoricoServico(vModuloVendedor, mHistoricoServico, mNotaFiscal, mServico);
    }
    catch(SQLException | NumberFormatException sQLException){
    showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    public void pesquisarHistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico){
    this.cleanHistoricoServico(vModuloVendedor);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT HISSER.COD_HISSER, HISSER.ITEMSERVICO_NOTAFISCAL_COD_NOTFIS, HISSER.ITEMSERVICO_SERVICO_COD_SER, HISSER.DES_HISSER\n" +
                    "FROM HISTORICOSERVICO HISSER\n" +
                    "INNER JOIN ITEMSERVICO ITEMSER\n" +
                    "INNER JOIN SERVICO SER\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "WHERE HISSER.ITEMSERVICO_NOTAFISCAL_COD_NOTFIS = ITEMSER.NOTAFISCAL_COD_NOTFIS\n" +
                    "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                    "AND HISSER.ITEMSERVICO_SERVICO_COD_SER = ITEMSER.SERVICO_COD_SER\n" +
                    "AND ITEMSER.SERVICO_COD_SER = SER.COD_SER\n" +
                    "AND HISSER.COD_HISSER = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    preparedStatement.setString(1,vModuloVendedor.PES_COD_HISSER.getSelectedItem().toString());
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    //vModuloVendedor.COD_HISSER.setText(resultSet.getString("HISSER.COD_HISSER"));
    vModuloVendedor.HISSER_COD_NOTFIS.setSelectedItem(resultSet.getString("HISSER.ITEMSERVICO_NOTAFISCAL_COD_NOTFIS"));
    vModuloVendedor.HISSER_DES_SER.setSelectedItem(resultSet.getString("HISSER.ITEMSERVICO_SERVICO_COD_SER"));
    vModuloVendedor.DES_HISSER.setText(resultSet.getString("HISSER.DES_HISSER"));
    }
    else{
    showMessageDialog(null, "Histórico de Serviço não encontrado.");
    }

    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }}
    /*Onde Parei*/

    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    
    public void excluirHistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("DELETE FROM HISTORICOSERVICO WHERE COD_HISSER = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    
    preparedStatement.setString(1, vModuloVendedor.PES_COD_HISSER.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Histórico de Serviço excluído da Base de Dados com sucesso.");
    
    this.ComboBoxHistoricoServico(vModuloVendedor.PES_COD_HISSER);
    this.cleanHistoricoServico(vModuloVendedor);
    this.pesquisarTabela_HistoricoServico(vModuloVendedor, mHistoricoServico, mNotaFiscal, mServico);
    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    public void atualizarHistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico){
    try{
    getHistoricoServico(vModuloVendedor, mHistoricoServico, mNotaFiscal, mServico);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlServico = ("SELECT COD_SER FROM SERVICO WHERE DES_SER = ?");
    
    String sql = ("UPDATE HISTORICOSERVICO SET COD_HISSER = ?, ITEMSERVICO_NOTAFISCAL_COD_NOTFIS = ?, ITEMSERVICO_SERVICO_COD_SER = ?, DES_HISSER = ? WHERE COD_HISSER = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlServico);
    
    pSservico.setString(1, mServico.getDES_SER());
    
    ResultSet rSservico = pSservico.executeQuery();
    
    while(rSservico.next()){
    mServico.setCOD_SER(rSservico.getLong("COD_SER"));
    }
    
    preparedStatement.setString(1, mHistoricoServico.getCOD_HISSER());
    preparedStatement.setLong(2, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setLong(3, mServico.getCOD_SER());
    preparedStatement.setString(4, mHistoricoServico.getDES_HISSER());
    preparedStatement.setString(5, vModuloVendedor.PES_COD_HISSER.getSelectedItem().toString());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Histórico de Serviço atualizado com sucesso.");
    
    this.ComboBoxHistoricoServico(vModuloVendedor.PES_COD_HISSER);
    this.cleanHistoricoServico(vModuloVendedor);
    this.pesquisarTabela_HistoricoServico(vModuloVendedor, mHistoricoServico, mNotaFiscal, mServico);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mHistoricoServico
     * @param mNotaFiscal
     * @param mServico
     */
    public void pesquisarTabela_HistoricoServico(view.ModuloVendedor vModuloVendedor, model.HistoricoServico mHistoricoServico, model.NotaFiscal mNotaFiscal, model.Servico mServico){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT HISSER.COD_HISSER, SER.DES_SER, ITEMSER.QTD_ITEMSER, ITEMSER.VLR_ITEMSER, DATE_FORMAT( NOTFIS.DATEMI_NOTFIS,  '%d/%m/%Y' ) AS DATEMI_NOTFIS, VEN.NOME_VEN, HISSER.DES_HISSER\n" +
                    "FROM HISTORICOSERVICO HISSER\n" +
                    "INNER JOIN ITEMSERVICO ITEMSER\n" +
                    "INNER JOIN SERVICO SER\n" +
                    "INNER JOIN NOTAFISCAL NOTFIS\n" +
                    "INNER JOIN VENDEDOR VEN\n" +
                    "INNER JOIN CLIENTE CLI\n" +
                    "WHERE HISSER.ITEMSERVICO_NOTAFISCAL_COD_NOTFIS = ITEMSER.NOTAFISCAL_COD_NOTFIS\n" +
                    "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                    "AND HISSER.ITEMSERVICO_SERVICO_COD_SER = ITEMSER.SERVICO_COD_SER\n" +
                    "AND ITEMSER.SERVICO_COD_SER = SER.COD_SER\n" +
                    "AND NOTFIS.VENDEDOR_COD_VEN = VEN.COD_VEN\n" +
                    "AND NOTFIS.CLIENTE_CPF_CLI = CLI.CPF_CLI");
    
    ArrayList <model.HistoricoServico> historicoservico = new ArrayList <> ();
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    ArrayList <model.Servico> servico = new ArrayList <> ();
    ArrayList <model.ItemServico> itemServico = new ArrayList <> ();
    ArrayList <model.Vendedor> vendedor = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){
    model.HistoricoServico hs = new model.HistoricoServico(); 
    model.NotaFiscal nf = new model.NotaFiscal();
    model.Servico s = new model.Servico();
    model.ItemServico is = new model.ItemServico();
    model.Vendedor v = new model.Vendedor();
    hs.setCOD_HISSER(resultSet.getString("HISSER.COD_HISSER"));
    s.setDES_SER(resultSet.getString("SER.DES_SER"));
    is.setQTD_ITEMSER(parseInt(resultSet.getString("ITEMSER.QTD_ITEMSER")));
    is.setVLR_ITEMSER(parseDouble(resultSet.getString("ITEMSER.VLR_ITEMSER")));
    nf.setDTEMI_NOTFIS(resultSet.getString("DATEMI_NOTFIS"));
    v.setNOME_VEN(resultSet.getString("VEN.NOME_VEN"));
    hs.setDES_HISSER(resultSet.getString("HISSER.DES_HISSER"));
    historicoservico.add(hs);
    notafiscal.add(nf);
    servico.add(s);
    itemServico.add(is);
    vendedor.add(v);
    }
    Iterator it1 = historicoservico.iterator();
    Iterator it2 = notafiscal.iterator();
    Iterator it3 = servico.iterator();
    Iterator it4 = itemServico.iterator();
    Iterator it5 = vendedor.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloVendedor.tableHistoricoServico.getModel();
    m.setNumRows(0);
    while(it1.hasNext()&&it2.hasNext()&&it3.hasNext()&&it4.hasNext()&&it5.hasNext()){
    model.HistoricoServico hs = (model.HistoricoServico)it1.next();
    model.NotaFiscal nf = (model.NotaFiscal)it2.next();
    model.Servico s = (model.Servico)it3.next();
    model.ItemServico is = (model.ItemServico)it4.next();
    model.Vendedor v = (model.Vendedor)it5.next();
   
    m.addRow(new Object[]{hs.getCOD_HISSER(), s.getDES_SER(), is.getQTD_ITEMSER(), is.getVLR_ITEMSER(), nf.getDTEMI_NOTFIS(), v.getNOME_VEN(), hs.getDES_HISSER()});
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
    public void ComboBoxHistoricoServico(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_HISSER FROM HISTORICOSERVICO");
    
    ArrayList <model.HistoricoServico> historicoservico = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.HistoricoServico hs = new model.HistoricoServico();
    hs.setCOD_HISSER(resultSet.getString("COD_HISSER"));
    historicoservico.add(hs);
    }
    Iterator it = historicoservico.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.HistoricoServico hs = (model.HistoricoServico)it.next();  
    x.addItem((hs.getCOD_HISSER()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    } 
}
