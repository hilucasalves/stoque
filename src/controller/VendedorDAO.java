package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class VendedorDAO {
    private static final Logger LOG = getLogger(VendedorDAO.class.getName());
    
    /**
     *
     * @param vModuloVendedor
     */
    public void cleanVendedor(view.ModuloVendedor vModuloVendedor){
    vModuloVendedor.CPF_VEN.setText(null);
    vModuloVendedor.NOME_VEN.setText(null);
    vModuloVendedor.SEX_VEN.setSelectedItem(null);
    vModuloVendedor.DATNASC_VEN.setText(null);
    vModuloVendedor.END_VEN.setText(null);
    vModuloVendedor.NUMEND_VEN.setText(null);
    vModuloVendedor.COMPEND_VEN.setText(null);
    vModuloVendedor.BAI_VEN.setText(null);
    vModuloVendedor.CID_VEN.setText(null);
    vModuloVendedor.CEP_VEN.setText(null);
    vModuloVendedor.UF_VEN.setSelectedItem(null);
    vModuloVendedor.DDDTEL_VEN.setText(null);
    vModuloVendedor.TEL_VEN.setText(null);
    vModuloVendedor.EMAIL_VEN.setText(null);
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     */
    public void getVendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor){
    mVendedor.setCPF_VEN(vModuloVendedor.CPF_VEN.getText().replace(".", "").replace("-", ""));
    mVendedor.setNOME_VEN(vModuloVendedor.NOME_VEN.getText());
    //mVendedor.setSEX_VEN((String) vModuloVendedor.SEX_VEN.getSelectedItem());
    
    if (vModuloVendedor.SEX_VEN.getSelectedIndex() == 0){
        mVendedor.setSEX_VEN("M");    
    }
    else{
        mVendedor.setSEX_VEN("F");
    }
    
    mVendedor.setDATNASC_VEN(vModuloVendedor.DATNASC_VEN.getText());
    mVendedor.setEND_VEN(vModuloVendedor.END_VEN.getText());
    mVendedor.setNUMEND_VEN(vModuloVendedor.NUMEND_VEN.getText());
    mVendedor.setCOMPEND_VEN(vModuloVendedor.COMPEND_VEN.getText());
    mVendedor.setBAI_VEN(vModuloVendedor.BAI_VEN.getText());
    mVendedor.setCEP_VEN(vModuloVendedor.CEP_VEN.getText().replace("-", ""));
    mVendedor.setCID_VEN(vModuloVendedor.CID_VEN.getText());
    mVendedor.setUF_VEN((String) vModuloVendedor.UF_VEN.getSelectedItem());
    mVendedor.setDDDTEL_VEN(vModuloVendedor.DDDTEL_VEN.getText());
    mVendedor.setTEL_VEN(vModuloVendedor.TEL_VEN.getText().replace("-", ""));
    mVendedor.setEMAIL_VEN(vModuloVendedor.EMAIL_VEN.getText());
    mVendedor.setSTATUS_VEN("A");
    }
 
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     */
    public void adicionarVendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor) {
    if(mVendedor.getNOME_VEN().equalsIgnoreCase("")||mVendedor.getCPF_VEN().equalsIgnoreCase("")){
    showMessageDialog(vModuloVendedor, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{
    try{
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("INSERT INTO VENDEDOR (CPF_VEN, NOME_VEN, SEX_VEN, DATNASC_VEN, END_VEN, "
            + "NUMEND_VEN, COMPEND_VEN, BAI_VEN, CEP_VEN, CID_VEN, "
            + "UF_VEN, DDDTEL_VEN, TEL_VEN, EMAIL_VEN, STATUS_VEN) "
            + "VALUES (?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mVendedor.getCPF_VEN());
                preparedStatement.setString(2, mVendedor.getNOME_VEN());
                preparedStatement.setString(3, mVendedor.getSEX_VEN());
                preparedStatement.setString(4, mVendedor.getDATNASC_VEN());
                preparedStatement.setString(5, mVendedor.getEND_VEN());
                preparedStatement.setString(6, mVendedor.getNUMEND_VEN());
                preparedStatement.setString(7, mVendedor.getCOMPEND_VEN());
                preparedStatement.setString(8, mVendedor.getBAI_VEN());
                preparedStatement.setString(9, mVendedor.getCEP_VEN());
                preparedStatement.setString(10, mVendedor.getCID_VEN());
                preparedStatement.setString(11, mVendedor.getUF_VEN());
                preparedStatement.setString(12, mVendedor.getDDDTEL_VEN());
                preparedStatement.setString(13, mVendedor.getTEL_VEN());
                preparedStatement.setString(14, mVendedor.getEMAIL_VEN());
                preparedStatement.setString(15, "A");
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Vendedor incluso com sucesso.");
            }
    this.cleanVendedor(vModuloVendedor);
    this.pesquisarTabela_Vendedor(vModuloVendedor, mVendedor);
    }
    catch(SQLException | NumberFormatException sQLException){
    showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     * @param getRow
     */
    public void pesquisarVendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor, String getRow){
    this.cleanVendedor(vModuloVendedor);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_VEN, CPF_VEN, NOME_VEN, SEX_VEN, DATE_FORMAT(DATNASC_VEN, '%d%m%Y') AS DATNASC_VEN, "
            + "END_VEN, NUMEND_VEN, COMPEND_VEN, BAI_VEN, CEP_VEN, CID_VEN, UF_VEN, DDDTEL_VEN, "
            + "TEL_VEN, EMAIL_VEN, STATUS_VEN FROM VENDEDOR WHERE COD_VEN = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    if(getRow.equalsIgnoreCase("sim")){
        mVendedor.setCOD_VEN((long) vModuloVendedor.tableVendedor.getValueAt(vModuloVendedor.tableVendedor.getSelectedRow(), 0));
    } else{
        //mProduto = (Produto)vModuloAdministrativo..getSelectedItem();
    }
    preparedStatement.setLong(1, mVendedor.getCOD_VEN());
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    mVendedor.setCOD_VEN(resultSet.getLong("COD_VEN"));  
    vModuloVendedor.CPF_VEN.setText(resultSet.getString("CPF_VEN"));
    vModuloVendedor.NOME_VEN.setText(resultSet.getString("NOME_VEN"));
    /*vModuloVendedor.SEX_VEN.setSelectedItem(resultSet.getString("SEX_VEN"));*/
    if(resultSet.getString("SEX_VEN").equalsIgnoreCase("M")){
    vModuloVendedor.SEX_VEN.setSelectedIndex(0);
    }
    else{
    vModuloVendedor.SEX_VEN.setSelectedIndex(1);
    }
    vModuloVendedor.DATNASC_VEN.setText(resultSet.getString("DATNASC_VEN"));
    vModuloVendedor.END_VEN.setText(resultSet.getString("END_VEN"));
    vModuloVendedor.NUMEND_VEN.setText(resultSet.getString("NUMEND_VEN"));
    vModuloVendedor.COMPEND_VEN.setText(resultSet.getString("COMPEND_VEN"));
    vModuloVendedor.BAI_VEN.setText(resultSet.getString("BAI_VEN"));
    vModuloVendedor.CEP_VEN.setText(resultSet.getString("CEP_VEN"));
    vModuloVendedor.CID_VEN.setText(resultSet.getString("CID_VEN"));
    vModuloVendedor.UF_VEN.setSelectedItem(resultSet.getString("UF_VEN"));
    vModuloVendedor.DDDTEL_VEN.setText(resultSet.getString("DDDTEL_VEN"));
    vModuloVendedor.TEL_VEN.setText(resultSet.getString("TEL_VEN"));
    vModuloVendedor.EMAIL_VEN.setText(resultSet.getString("EMAIL_VEN"));
    mVendedor.setSTATUS_VEN(resultSet.getString("STATUS_VEN"));
    }
    else{
    showMessageDialog(null, "Vendedor não encontrado.");
    }

    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }}
    
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     */
    public void excluirVendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
     mVendedor.setCOD_VEN((long) vModuloVendedor.tableVendedor.getValueAt(vModuloVendedor.tableVendedor.getSelectedRow(), 0));
    String sql = ("DELETE FROM VENDEDOR WHERE COD_VEN = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, mVendedor.getCOD_VEN());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Vendedor excluído da Base de Dados com sucesso.");
            }
    this.cleanVendedor(vModuloVendedor);
    this.pesquisarTabela_Vendedor(vModuloVendedor, mVendedor);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     */
    public void atualizarVendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor){
    try{
    getVendedor(vModuloVendedor, mVendedor);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    mVendedor.setCOD_VEN((long) vModuloVendedor.tableVendedor.getValueAt(vModuloVendedor.tableVendedor.getSelectedRow(), 0));
    String sql = ("UPDATE VENDEDOR SET CPF_VEN = ?, NOME_VEN = ?, SEX_VEN = ?, DATNASC_VEN = STR_TO_DATE(?, '%d/%m/%Y'), END_VEN = ?, NUMEND_VEN = ?, COMPEND_VEN = ?, BAI_VEN = ?, CEP_VEN = ?, CID_VEN = ?, UF_VEN = ?, DDDTEL_VEN = ?, TEL_VEN = ?, EMAIL_VEN = ?, STATUS_VEN = ? WHERE COD_VEN = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mVendedor.getCPF_VEN());
                preparedStatement.setString(2, mVendedor.getNOME_VEN());
                preparedStatement.setString(3, mVendedor.getSEX_VEN());
                preparedStatement.setString(4, mVendedor.getDATNASC_VEN());
                preparedStatement.setString(5, mVendedor.getEND_VEN());
                preparedStatement.setString(6, mVendedor.getNUMEND_VEN());
                preparedStatement.setString(7, mVendedor.getCOMPEND_VEN());
                preparedStatement.setString(8, mVendedor.getBAI_VEN());
                preparedStatement.setString(9, mVendedor.getCEP_VEN());
                preparedStatement.setString(10, mVendedor.getCID_VEN());
                preparedStatement.setString(11, mVendedor.getUF_VEN());
                preparedStatement.setString(12, mVendedor.getDDDTEL_VEN());
                preparedStatement.setString(13, mVendedor.getTEL_VEN());
                preparedStatement.setString(14, mVendedor.getEMAIL_VEN());
                preparedStatement.setString(15, mVendedor.getSTATUS_VEN());
                preparedStatement.setLong(16, mVendedor.getCOD_VEN());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Vendedor atualizado com sucesso.");
            }
    this.cleanVendedor(vModuloVendedor);
    this.pesquisarTabela_Vendedor(vModuloVendedor, mVendedor);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloVendedor
     * @param mVendedor
     * @return
     */
    public ArrayList pesquisarTabela_Vendedor(view.ModuloVendedor vModuloVendedor, model.Vendedor mVendedor){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT COD_VEN, NOME_VEN, EMAIL_VEN FROM VENDEDOR");
    ArrayList <model.Vendedor> vendedor = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Vendedor v = new model.Vendedor();
    v.setCOD_VEN(resultSet.getLong("COD_VEN"));
    v.setNOME_VEN(resultSet.getString("NOME_VEN"));
    v.setEMAIL_VEN(resultSet.getString("EMAIL_VEN"));
    vendedor.add(v);
    }
    Iterator it = vendedor.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloVendedor.tableVendedor.getModel();
    m.setNumRows(0);
    while(it.hasNext()){
    model.Vendedor v = (model.Vendedor)it.next();
    m.addRow(new Object[]{v.getCOD_VEN(), v.getNOME_VEN(), v.getEMAIL_VEN()});
    }
    return vendedor;
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
    public void ComboBoxVendedor(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT NOME_VEN FROM VENDEDOR");
    
    ArrayList <model.Vendedor> vendedor = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Vendedor v = new model.Vendedor();
    v.setNOME_VEN(resultSet.getString("NOME_VEN"));
    vendedor.add(v);
    }
    Iterator it = vendedor.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.Vendedor v = (model.Vendedor)it.next();  
    x.addItem((v.getNOME_VEN()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
}
