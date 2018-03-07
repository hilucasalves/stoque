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
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class ClienteDAO {
    private static final Logger LOG = getLogger(ClienteDAO.class.getName());
    
    /**
     *
     * @param vModuloCliente
     */
    public void cleanCliente(view.ModuloCliente vModuloCliente){
    vModuloCliente.NOME_CLI.setText(null);
    vModuloCliente.CPF_CLI.setText(null);    
    vModuloCliente.SEX_CLI.setSelectedItem(null);
    vModuloCliente.DTNASC_CLI.setText(null);
    vModuloCliente.END_CLI.setText(null);
    vModuloCliente.NUMEND_CLI.setText(null);
    vModuloCliente.COMPEND_CLI.setText(null);
    vModuloCliente.BAI_CLI.setText(null);
    vModuloCliente.CID_CLI.setText(null);
    vModuloCliente.CEP_CLI.setText(null);
    vModuloCliente.UF_CLI.setSelectedItem(null);
    vModuloCliente.DDDTEL_CLI.setText(null);
    vModuloCliente.TEL_CLI.setText(null);
    vModuloCliente.EMAIL_CLI.setText(null);
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     */
    public void getCliente(view.ModuloCliente vModuloCliente, model.Cliente mCliente){
    mCliente.setCPF_CLI(vModuloCliente.CPF_CLI.getText().replace(".", "").replace("-", ""));
    mCliente.setNOME_CLI(vModuloCliente.NOME_CLI.getText());
    
    if (vModuloCliente.SEX_CLI.getSelectedIndex() == 0){
        mCliente.setSEX_CLI("M");    
    }
    else{
        mCliente.setSEX_CLI("F");
    }
    
    mCliente.setDTNASC_CLI(vModuloCliente.DTNASC_CLI.getText());
    mCliente.setEND_CLI(vModuloCliente.END_CLI.getText());
    mCliente.setNUMEND_CLI(vModuloCliente.NUMEND_CLI.getText());
    mCliente.setCOMPEND_CLI(vModuloCliente.COMPEND_CLI.getText());
    mCliente.setBAI_CLI(vModuloCliente.BAI_CLI.getText());
    mCliente.setCEP_CLI(vModuloCliente.CEP_CLI.getText().replace("-", ""));
    mCliente.setCID_CLI(vModuloCliente.CID_CLI.getText());
    mCliente.setUF_CLI((String) vModuloCliente.UF_CLI.getSelectedItem());
    mCliente.setDDDTEL_CLI(vModuloCliente.DDDTEL_CLI.getText());
    mCliente.setTEL_CLI(vModuloCliente.TEL_CLI.getText().replace("-", ""));
    mCliente.setEMAIL_CLI(vModuloCliente.EMAIL_CLI.getText());
    }
 
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     */
    public void adicionarCliente(view.ModuloCliente vModuloCliente, model.Cliente mCliente) {  
    if(mCliente.getCPF_CLI().equalsIgnoreCase("")||mCliente.getNOME_CLI().equalsIgnoreCase("")||mCliente.getDTNASC_CLI().equalsIgnoreCase("")){
    showMessageDialog(vModuloCliente, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("INSERT INTO CLIENTE (CPF_CLI, NOME_CLI, SEX_CLI, DATNASC_CLI, END_CLI, NUMEND_CLI, COMPEND_CLI, BAI_CLI, "
            + "CEP_CLI, CID_CLI, UF_CLI, DDDTEL_CLI, TEL_CLI, EMAIL_CLI, STATUS_CLI) "
            + "VALUES (?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mCliente.getCPF_CLI());
                preparedStatement.setString(2, mCliente.getNOME_CLI());
                preparedStatement.setString(3, mCliente.getSEX_CLI());
                preparedStatement.setString(4, mCliente.getDTNASC_CLI());
                preparedStatement.setString(5, mCliente.getEND_CLI());
                preparedStatement.setString(6, mCliente.getNUMEND_CLI());
                preparedStatement.setString(7, mCliente.getCOMPEND_CLI());
                preparedStatement.setString(8, mCliente.getBAI_CLI());
                preparedStatement.setString(9, mCliente.getCEP_CLI());
                preparedStatement.setString(10, mCliente.getCID_CLI());
                preparedStatement.setString(11, mCliente.getUF_CLI());
                preparedStatement.setString(12, mCliente.getDDDTEL_CLI());
                preparedStatement.setString(13, mCliente.getTEL_CLI());
                preparedStatement.setString(14, mCliente.getEMAIL_CLI());
                preparedStatement.setString(15, "A");
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Cliente incluso com sucesso.");
            }
    //this.ComboBoxCliente(vModuloCliente.PES_CPF_CLI);
    this.cleanCliente(vModuloCliente);
    this.pesquisarTabela(vModuloCliente, mCliente);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }
    }}
    
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     * @param getRow
     */
    public void pesquisarCliente(view.ModuloCliente vModuloCliente, model.Cliente mCliente, String getRow){
    this.cleanCliente(vModuloCliente);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT CPF_CLI, NOME_CLI, SEX_CLI, DATE_FORMAT(DATNASC_CLI, '%d%m%Y') AS DATNASC_CLI, END_CLI, NUMEND_CLI, COMPEND_CLI, BAI_CLI, CEP_CLI, CID_CLI, UF_CLI, DDDTEL_CLI, TEL_CLI, EMAIL_CLI FROM CLIENTE WHERE COD_CLI = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    if(getRow.equalsIgnoreCase("sim")){
        mCliente.setCOD_CLI((long) vModuloCliente.tableCliente.getValueAt(vModuloCliente.tableCliente.getSelectedRow(), 0));
    } else{
        //mCliente = (Cliente)vModuloCliente.PES_CPF_CLI.getSelectedItem();
    }
    
    preparedStatement.setLong(1, mCliente.getCOD_CLI());
    ResultSet resultSet = preparedStatement.executeQuery();
    if(resultSet.next()){
    vModuloCliente.CPF_CLI.setText(resultSet.getString("CPF_CLI"));
    vModuloCliente.NOME_CLI.setText(resultSet.getString("NOME_CLI"));
    if(resultSet.getString("SEX_CLI").equalsIgnoreCase("M")){
    vModuloCliente.SEX_CLI.setSelectedIndex(0);
    }
    else{
    vModuloCliente.SEX_CLI.setSelectedIndex(1);
    }
    vModuloCliente.DTNASC_CLI.setText(resultSet.getString("DATNASC_CLI"));
    vModuloCliente.END_CLI.setText(resultSet.getString("END_CLI"));
    vModuloCliente.NUMEND_CLI.setText(resultSet.getString("NUMEND_CLI"));
    vModuloCliente.COMPEND_CLI.setText(resultSet.getString("COMPEND_CLI"));
    vModuloCliente.BAI_CLI.setText(resultSet.getString("BAI_CLI"));
    vModuloCliente.CEP_CLI.setText(resultSet.getString("CEP_CLI"));
    vModuloCliente.CID_CLI.setText(resultSet.getString("CID_CLI"));
    vModuloCliente.UF_CLI.setSelectedItem(resultSet.getString("UF_CLI"));
    vModuloCliente.DDDTEL_CLI.setText(resultSet.getString("DDDTEL_CLI"));
    vModuloCliente.TEL_CLI.setText(resultSet.getString("TEL_CLI"));
    vModuloCliente.EMAIL_CLI.setText(resultSet.getString("EMAIL_CLI"));
    }
    else{
        showMessageDialog(null, "Cliente não encontrado.");
    }

    }
    catch(SQLException sqlException){
        showMessageDialog(null, sqlException.getMessage());
    }}
    
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     */
    public void excluirCliente(view.ModuloCliente vModuloCliente, model.Cliente mCliente){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("DELETE FROM CLIENTE WHERE COD_CLI = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, (long) vModuloCliente.tableCliente.getValueAt(vModuloCliente.tableCliente.getSelectedRow(), 0));
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Cliente excluído da Base de Dados com sucesso.");
            }
    //this.ComboBoxCliente(vModuloCliente.PES_CPF_CLI);
    this.cleanCliente(vModuloCliente);
    this.pesquisarTabela(vModuloCliente, mCliente);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     */
    public void atualizarCliente(view.ModuloCliente vModuloCliente, model.Cliente mCliente){
    try{
    this.getCliente(vModuloCliente, mCliente);
    mCliente.setCOD_CLI((long) vModuloCliente.tableCliente.getValueAt(vModuloCliente.tableCliente.getSelectedRow(), 0));
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("UPDATE CLIENTE SET CPF_CLI = ?, NOME_CLI = ?, SEX_CLI = ?, DATNASC_CLI = STR_TO_DATE(?, '%d/%m/%Y'), END_CLI = ?, NUMEND_CLI = ?, COMPEND_CLI = ?, BAI_CLI = ?, CEP_CLI = ?, CID_CLI = ?, UF_CLI = ?, DDDTEL_CLI = ?, TEL_CLI = ?, EMAIL_CLI = ?, STATUS_CLI = ? WHERE COD_CLI = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mCliente.getCPF_CLI());
                preparedStatement.setString(2, mCliente.getNOME_CLI());
                preparedStatement.setString(3, mCliente.getSEX_CLI());
                preparedStatement.setString(4, mCliente.getDTNASC_CLI());
                preparedStatement.setString(5, mCliente.getEND_CLI());
                preparedStatement.setString(6, mCliente.getNUMEND_CLI());
                preparedStatement.setString(7, mCliente.getCOMPEND_CLI());
                preparedStatement.setString(8, mCliente.getBAI_CLI());
                preparedStatement.setString(9, mCliente.getCEP_CLI());
                preparedStatement.setString(10, mCliente.getCID_CLI());
                preparedStatement.setString(11, mCliente.getUF_CLI());
                preparedStatement.setString(12, mCliente.getDDDTEL_CLI());
                preparedStatement.setString(13, mCliente.getTEL_CLI());
                preparedStatement.setString(14, mCliente.getEMAIL_CLI());
                preparedStatement.setString(15, "A");
                preparedStatement.setLong(16, mCliente.getCOD_CLI());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Cliente atualizado com sucesso.");
            }
    //this.ComboBoxCliente(vModuloCliente.PES_CPF_CLI);
    this.cleanCliente(vModuloCliente);
    this.pesquisarTabela(vModuloCliente, mCliente);
    }
    catch(SQLException sqlException){
        showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mCliente
     * @return
     */
    public ArrayList pesquisarTabela(view.ModuloCliente vModuloCliente, model.Cliente mCliente){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT COD_CLI, NOME_CLI, DDDTEL_CLI, TEL_CLI, EMAIL_CLI FROM CLIENTE");
    ArrayList <model.Cliente> cliente = new ArrayList <> ();
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.Cliente c = new model.Cliente();
    c.setCOD_CLI(resultSet.getLong("COD_CLI"));
    c.setNOME_CLI(resultSet.getString("NOME_CLI"));
    c.setDDDTEL_CLI(resultSet.getString("DDDTEL_CLI"));
    c.setTEL_CLI(resultSet.getString("TEL_CLI"));
    c.setEMAIL_CLI(resultSet.getString("EMAIL_CLI"));
    cliente.add(c);
    }
    Iterator it = cliente.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloCliente.tableCliente.getModel();
    m.setNumRows(0);
    while(it.hasNext()){
    model.Cliente c = (model.Cliente)it.next();
    m.addRow(new Object[]{c.getCOD_CLI(), c.getNOME_CLI(), c.getDDDTEL_CLI(), c.getTEL_CLI(),c.getEMAIL_CLI()});
    }
    return cliente;
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
    public void ComboBoxCliente(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT CPF_CLI, NOME_CLI FROM CLIENTE");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    x.removeAllItems();
    while(resultSet.next()){
    model.Cliente mCliente = new model.Cliente();
    mCliente.setCPF_CLI(resultSet.getString("CPF_CLI"));
    mCliente.setNOME_CLI(resultSet.getString("NOME_CLI"));
    x.addItem(new model.Cliente(mCliente.getCPF_CLI(), mCliente.getNOME_CLI()));
    }  
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    public void getClienteRow(view.ModuloCliente vModuloCliente, model.Cliente mCliente){
    this.pesquisarCliente(vModuloCliente, mCliente, "sim");
    }
}