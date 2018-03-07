package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.Font.NORMAL;
import com.itextpdf.text.Image;
import static com.itextpdf.text.PageSize.A6;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import static java.awt.Desktop.getDesktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Double.parseDouble;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.table.DefaultTableModel;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class NotaFiscalDAO {
    private static final Logger LOG = getLogger(NotaFiscalDAO.class.getName());
    
    /**
     *
     * @param vModuloCliente
     */
    public void cleanNotaFiscal(view.ModuloCliente vModuloCliente){ 
    vModuloCliente.NOTFIS_CPF_CLI.setText(null);
    vModuloCliente.NOTFIS_NOME_VEN.setSelectedItem(null);
    vModuloCliente.DATEMI_NOTFIS.setText(null);
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    public void getNotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor){
    mCliente.setCPF_CLI(vModuloCliente.NOTFIS_CPF_CLI.getText().replace(".", "").replace("-", ""));
    mVendedor.setNOME_VEN((String) vModuloCliente.NOTFIS_NOME_VEN.getSelectedItem());
    mNotaFiscal.setDTEMI_NOTFIS(vModuloCliente.DATEMI_NOTFIS.getText());
    }
 
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    public void adicionarNotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor) {  
    if(mNotaFiscal.getDTEMI_NOTFIS().equalsIgnoreCase(null)){
    showMessageDialog(vModuloCliente, "Os principais campos estão vazios.", "Atenção", 2);
    }
    else{
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlVendedor = ("SELECT COD_VEN FROM VENDEDOR WHERE NOME_VEN = ?");
    
    String sql = ("INSERT INTO NOTAFISCAL (CLIENTE_COD_CLI, VENDEDOR_COD_VEN, DATEMI_NOTFIS, VLR_NOTFIS) VALUES (?, ?, STR_TO_DATE(?, '%d/%m/%Y'), 0.00)");
    
    PreparedStatement pSVendedor = connectionFactory.Connection.prepareStatement(sqlVendedor);
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
                
    pSVendedor.setString(1, mVendedor.getNOME_VEN());
    
    ResultSet rSvendedor = pSVendedor.executeQuery();
    
    while(rSvendedor.next()){
    mVendedor.setCOD_VEN(rSvendedor.getLong("COD_VEN"));
    }
    
    preparedStatement.setString(1, mCliente.getCPF_CLI());
    preparedStatement.setLong(2, mVendedor.getCOD_VEN());
    preparedStatement.setString(3, mNotaFiscal.getDTEMI_NOTFIS());
                
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Nota Fiscal inclusa com sucesso.");
    
    this.ComboBoxNotaFiscal(vModuloCliente.PES_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.CONCLUIR_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.gerarNota);
    this.cleanNotaFiscal(vModuloCliente);
    this.cleanNotaFiscal(vModuloCliente);
    this.pesquisarTabela_NotaFiscal(vModuloCliente, mNotaFiscal, mCliente, mVendedor);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }}
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     * @param getRow
     */
    public void pesquisarNotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor, String getRow){
    this.cleanNotaFiscal(vModuloCliente);
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT NOTFIS.COD_NOTFIS, NOTFIS.CLIENTE_COD_CLI, VEN.NOME_VEN, DATE_FORMAT(NOTFIS.DATEMI_NOTFIS, '%d%m%Y') AS DATEMI_NOTFIS\n" +
                    "FROM NOTAFISCAL NOTFIS\n" +
                    "INNER JOIN CLIENTE CLI\n" +
                    "INNER JOIN VENDEDOR VEN\n" +
                    "WHERE NOTFIS.CLIENTE_COD_CLI = CLI.CPF_CLI\n" +
                    "AND VEN.COD_VEN = NOTFIS.VENDEDOR_COD_VEN\n" +
                    "AND NOTFIS.COD_NOTFIS = ?");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    if(getRow.equalsIgnoreCase("sim")){
        mNotaFiscal.setCOD_NOTFIS((long) vModuloCliente.tableNotaFiscal.getValueAt(vModuloCliente.tableNotaFiscal.getSelectedRow(), 0));
    } else{
       mNotaFiscal.setCOD_NOTFIS((long) vModuloCliente.PES_COD_NOTFIS.getSelectedItem());
    }
    preparedStatement.setLong(1, mNotaFiscal.getCOD_NOTFIS());
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){
    //vModuloCliente.COD_NOTFIS.setText(resultSet.getString("NOTFIS.COD_NOTFIS"));   
    vModuloCliente.NOTFIS_CPF_CLI.setText(resultSet.getString("NOTFIS.CLIENTE_COD_CLI"));
    vModuloCliente.NOTFIS_NOME_VEN.setSelectedItem(resultSet.getString("VEN.NOME_VEN"));
    vModuloCliente.DATEMI_NOTFIS.setText(resultSet.getString("DATEMI_NOTFIS"));
    }
    else{
    showMessageDialog(null, "Nota Fiscal não encontrado.");
    }

    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }}

    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    
    public void excluirNotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("DELETE FROM NOTAFISCAL WHERE COD_NOTFIS = ?");
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setString(1,vModuloCliente.PES_COD_NOTFIS.getSelectedItem().toString());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Nota Fiscal excluída da Base de Dados com sucesso.");
    }
    this.ComboBoxNotaFiscal(vModuloCliente.PES_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.CONCLUIR_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.gerarNota);
    this.cleanNotaFiscal(vModuloCliente);
    this.pesquisarTabela_NotaFiscal(vModuloCliente, mNotaFiscal, mCliente, mVendedor);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    public void atualizarNotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor){
    try{
    getNotaFiscal(vModuloCliente, mNotaFiscal, mCliente, mVendedor);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlVendedor = ("SELECT COD_VEN FROM VENDEDOR WHERE NOME_VEN = ?");
    
    String sql = ("UPDATE NOTAFISCAL SET COD_NOTFIS = ?, CLIENTE_CPF_CLI = ?, VENDEDOR_COD_VEN = ?, DATEMI_NOTFIS = STR_TO_DATE(?, '%d/%m/%Y') WHERE COD_NOTFIS = ?");
    
    PreparedStatement pSVendedor = connectionFactory.Connection.prepareStatement(sqlVendedor);
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    pSVendedor.setString(1, mVendedor.getNOME_VEN());
    
    ResultSet rSvendedor = pSVendedor.executeQuery();
    
    while(rSvendedor.next()){
    mVendedor.setCOD_VEN(rSvendedor.getLong("COD_VEN"));
    }
    
    preparedStatement.setLong(1, mNotaFiscal.getCOD_NOTFIS());
    preparedStatement.setString(2, mCliente.getCPF_CLI());
    preparedStatement.setLong(3, mVendedor.getCOD_VEN());
    preparedStatement.setString(4, mNotaFiscal.getDTEMI_NOTFIS());
    preparedStatement.setString(5, vModuloCliente.PES_COD_NOTFIS.getSelectedItem().toString());
    
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Nota Fiscal atualizada com sucesso.");
    
    this.ComboBoxNotaFiscal(vModuloCliente.PES_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.PES_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMPROD_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.FIL_ITEMSER_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.CONCLUIR_COD_NOTFIS);
    this.ComboBoxNotaFiscal(vModuloCliente.gerarNota);
    this.cleanNotaFiscal(vModuloCliente);
    this.cleanNotaFiscal(vModuloCliente);
    this.pesquisarTabela_NotaFiscal(vModuloCliente, mNotaFiscal, mCliente, mVendedor);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    public void pesquisarTabela_NotaFiscal(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    String sql = ("SELECT NOTFIS.COD_NOTFIS, CLI.NOME_CLI, VEN.NOME_VEN, DATE_FORMAT( NOTFIS.DATEMI_NOTFIS,  '%d/%m/%Y' ) AS DATEMI_NOTFIS\n" +
                    "FROM NOTAFISCAL NOTFIS\n" +
                    "INNER JOIN CLIENTE CLI\n" +
                    "INNER JOIN VENDEDOR VEN\n" +
                    "WHERE NOTFIS.CLIENTE_COD_CLI = CLI.CPF_CLI\n" +
                    "AND NOTFIS.VENDEDOR_COD_VEN = VEN.COD_VEN");
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    ArrayList <model.Cliente> cliente = new ArrayList <> ();
    ArrayList <model.Vendedor> vendedor = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.NotaFiscal nf = new model.NotaFiscal();
    model.Cliente c = new model.Cliente();
    model.Vendedor v = new model.Vendedor();
    
    nf.setCOD_NOTFIS(resultSet.getLong("NOTFIS.COD_NOTFIS"));
    c.setNOME_CLI(resultSet.getString("CLI.NOME_CLI"));
    v.setNOME_VEN(resultSet.getString("VEN.NOME_VEN"));
    nf.setDTEMI_NOTFIS(resultSet.getString("DATEMI_NOTFIS"));
    notafiscal.add(nf);
    cliente.add(c);
    vendedor.add(v);
    }
    Iterator it1 = notafiscal.iterator();
    Iterator it2 = cliente.iterator();
    Iterator it3 = vendedor.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloCliente.tableNotaFiscal.getModel();
    m.setNumRows(0);
    while(it1.hasNext()&&it2.hasNext()&&it3.hasNext()){
    model.NotaFiscal nf = (model.NotaFiscal)it1.next();
    model.Cliente c = (model.Cliente)it2.next();
    model.Vendedor v = (model.Vendedor)it3.next();
    m.addRow(new Object[]{nf.getCOD_NOTFIS(), c.getNOME_CLI(), v.getNOME_VEN(), nf.getDTEMI_NOTFIS()});
    }
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    /**
     *
     * @param vModuloCliente
     */
    public void ConcluirNotaFiscal(view.ModuloCliente vModuloCliente){
     try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("UPDATE NOTAFISCAL SET VLR_NOTFIS = ? WHERE COD_NOTFIS = ?"); 
            try (PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, parseDouble(vModuloCliente.VLR_NOTFIS.getText()));
                preparedStatement.setString(2, vModuloCliente.CONCLUIR_COD_NOTFIS.getSelectedItem().toString());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Nota Fiscal inclusa e concluida com sucesso.");
            }
    vModuloCliente.CONCLUIR_COD_NOTFIS.setSelectedItem(null);
    vModuloCliente.VLR_NOTFIS.setText(null);
    }
    catch(SQLException | NumberFormatException sQLException){
            showMessageDialog(null, sQLException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     * @param mNotaFiscal
     * @param mCliente
     * @param mVendedor
     */
    public void gerarPDF(view.ModuloCliente vModuloCliente, model.NotaFiscal mNotaFiscal, model.Cliente mCliente, model.Vendedor mVendedor){
    String diretorio = "../nfe/"+vModuloCliente.gerarNota.getSelectedItem()+".pdf";   
    java.io.File file = new File(diretorio);    
    if(file.exists()){
    showMessageDialog(null, "Nota Fiscal já existe.");
    }
    else{
    
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String updateproduto = ("UPDATE PRODUTO SET QTD_PROD = QTD_PROD - ?\n"+
                            "WHERE COD_PROD = ?");
    
    String cabeçalhonota = ("SELECT NOTFIS.COD_NOTFIS, CLI.NOME_CLI, VEN.NOME_VEN, DATE_FORMAT( NOTFIS.DATEMI_NOTFIS,  '%d/%m/%Y' ) AS DATEMI_NOTFIS, NOTFIS.VLR_NOTFIS\n" +
                            "FROM NOTAFISCAL NOTFIS\n" +
                            "INNER JOIN CLIENTE CLI\n" +
                            "INNER JOIN VENDEDOR VEN\n" +
                            "WHERE NOTFIS.CLIENTE_CPF_CLI = CLI.CPF_CLI\n" +
                            "AND NOTFIS.VENDEDOR_COD_VEN = VEN.COD_VEN AND NOTFIS.COD_NOTFIS = ?");
    
    String produtonota = ("SELECT ITEMPROD.PRODUTO_COD_PROD, PROD.NOME_PROD, ITEMPROD.QTD_ITEMPROD, ITEMPROD.VLR_ITEMPROD FROM ITEMPRODUTO ITEMPROD INNER JOIN PRODUTO PROD INNER JOIN NOTAFISCAL NOTFIS\n" +
                          "WHERE ITEMPROD.PRODUTO_COD_PROD = PROD.COD_PROD\n" +
                          "AND ITEMPROD.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                          "AND ITEMPROD.NOTAFISCAL_COD_NOTFIS = ?");
    
    String serviconota = ("SELECT ITEMSER.SERVICO_COD_SER, SER.DES_SER, ITEMSER.QTD_ITEMSER, ITEMSER.VLR_ITEMSER FROM ITEMSERVICO ITEMSER INNER JOIN SERVICO SER INNER JOIN NOTAFISCAL NOTFIS\n" +
                          "WHERE ITEMSER.SERVICO_COD_SER = SER.COD_SER\n" +
                          "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                          "AND ITEMSER.NOTAFISCAL_COD_NOTFIS = ?");
    
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    ArrayList <model.ItemProduto> itemProduto = new ArrayList <> ();
    ArrayList <model.Produto> produto = new ArrayList <> ();
    ArrayList <model.ItemServico> itemServico = new ArrayList <> ();
    ArrayList <model.Servico> servico = new ArrayList <> ();
    ArrayList <model.Cliente> cliente = new ArrayList <> ();
    ArrayList <model.Vendedor> vendedor = new ArrayList <> ();
    
    PreparedStatement pSupdateproduto = connectionFactory.Connection.prepareStatement(updateproduto);
    PreparedStatement pScabeçalhonota = connectionFactory.Connection.prepareStatement(cabeçalhonota);
    PreparedStatement pSprodutonota = connectionFactory.Connection.prepareStatement(produtonota);
    PreparedStatement pSserviconota = connectionFactory.Connection.prepareStatement(serviconota);
    
    pScabeçalhonota.setString(1, vModuloCliente.gerarNota.getSelectedItem().toString());
    pSprodutonota.setString(1, vModuloCliente.gerarNota.getSelectedItem().toString());
    pSserviconota.setString(1, vModuloCliente.gerarNota.getSelectedItem().toString());
    
    ResultSet rScabeçalhonota = pScabeçalhonota.executeQuery();
    ResultSet rSprodutonota = pSprodutonota.executeQuery();
    ResultSet rSserviconota = pSserviconota.executeQuery();
     
    while(rScabeçalhonota.next()){   
      
    model.NotaFiscal nf = new model.NotaFiscal();
    model.Cliente c = new model.Cliente();
    model.Vendedor v = new model.Vendedor();

    nf.setCOD_NOTFIS(rScabeçalhonota.getLong("NOTFIS.COD_NOTFIS"));
    c.setNOME_CLI(rScabeçalhonota.getString("CLI.NOME_CLI"));
    v.setNOME_VEN(rScabeçalhonota.getString("VEN.NOME_VEN"));
    nf.setDTEMI_NOTFIS(rScabeçalhonota.getString("DATEMI_NOTFIS"));
    nf.setVLR_NOTFIS(parseDouble(rScabeçalhonota.getString("NOTFIS.VLR_NOTFIS")));
    
    notafiscal.add(nf);
    cliente.add(c);
    vendedor.add(v);
    }
    
    if(rSprodutonota == null){
    model.ItemProduto ip = new model.ItemProduto();
    model.Produto p = new model.Produto();
    p.setCOD_PROD(0);
    p.setNOME_PROD(null);
    ip.setQTD_ITEMPROD(0);
    ip.setVLR_ITEMPROD(0.00);
    }
    else{
    while(rSprodutonota.next()){   
    model.ItemProduto ip = new model.ItemProduto();
    model.Produto p = new model.Produto();
    p.setCOD_PROD(rSprodutonota.getLong("ITEMPROD.PRODUTO_COD_PROD"));
    p.setNOME_PROD(rSprodutonota.getString("PROD.NOME_PROD"));
    ip.setQTD_ITEMPROD(parseInt(rSprodutonota.getString("ITEMPROD.QTD_ITEMPROD")));
    ip.setVLR_ITEMPROD(parseDouble(rSprodutonota.getString("ITEMPROD.VLR_ITEMPROD")));  
    itemProduto.add(ip);
    produto.add(p);
    pSupdateproduto.setInt(1, parseInt(rSprodutonota.getString("ITEMPROD.QTD_ITEMPROD")));
    pSupdateproduto.setString(2, rSprodutonota.getString("ITEMPROD.PRODUTO_COD_PROD"));  
    pSupdateproduto.executeUpdate();
    }
    }
    if(rSserviconota == null){
    model.ItemServico is = new model.ItemServico();
    model.Servico s = new model.Servico();
    s.setCOD_SER(null);
    s.setDES_SER(null);
    is.setQTD_ITEMSER(0);
    is.setVLR_ITEMSER(0.00);
    }
    else{
    while(rSserviconota.next()){   
    model.ItemServico is = new model.ItemServico();
    model.Servico s = new model.Servico();
    s.setCOD_SER(rSserviconota.getLong("ITEMSER.SERVICO_COD_SER"));
    s.setDES_SER(rSserviconota.getString("SER.DES_SER"));
    is.setQTD_ITEMSER(parseInt(rSserviconota.getString("ITEMSER.QTD_ITEMSER")));
    is.setVLR_ITEMSER(parseDouble(rSserviconota.getString("ITEMSER.VLR_ITEMSER")));
    itemServico.add(is);
    servico.add(s);
    }
    }
    Iterator it1 = notafiscal.iterator();
    Iterator it2 = itemProduto.iterator();
    Iterator it3 = produto.iterator();
    Iterator it4 = itemServico.iterator();
    Iterator it5 = servico.iterator();
    Iterator it6 = cliente.iterator();
    Iterator it7 = vendedor.iterator();
    
    model.NotaFiscal nf = (model.NotaFiscal)it1.next();
    model.Cliente c = (model.Cliente)it6.next();
    model.Vendedor v = (model.Vendedor)it7.next();
       
    Document doc = new Document(A6, 20, 20, 20, 20);
    OutputStream File = new FileOutputStream("../nfe/"+nf.getCOD_NOTFIS()+".pdf");
    
    Font f = new Font(HELVETICA, 6, NORMAL);

    getInstance(doc, File);
    doc.open();
    Image banner = getInstance("../logo.png");
    banner.scaleAbsolute(20, 20);
    doc.add(banner);
    doc.add(new Paragraph("\n",f));
    doc.add(new Paragraph("Nota Fiscal Eletrônica - Papelaria Papelândia\n",f));
    doc.add(new Paragraph("Identificador: "+nf.getCOD_NOTFIS(),f));
    doc.add(new Paragraph("Cliente: "+c.getNOME_CLI(),f));
    doc.add(new Paragraph("Vendedor: "+v.getNOME_VEN(),f));
    doc.add(new Paragraph("Data de Emissão: "+nf.getDTEMI_NOTFIS(),f));
    doc.add(new Paragraph("\n",f));
    
    boolean i2 = it2.hasNext();
    boolean i3 = it3.hasNext();
    
    if(i2 == false && i3 == false){
   
    }
    else{
    PdfPTable cabproduto = new PdfPTable(1);
    cabproduto.addCell(new Phrase("Produtos", f));
    doc.add(cabproduto);
    PdfPTable itensproduto = new PdfPTable(4);
    itensproduto.addCell(new Phrase("Código", f));
    itensproduto.addCell(new Phrase("Produto", f));
    itensproduto.addCell(new Phrase("Quantidade", f));
    itensproduto.addCell(new Phrase("Valor", f));
    while(it2.hasNext()&&it3.hasNext()){
    model.ItemProduto ip = (model.ItemProduto)it2.next();
    model.Produto p = (model.Produto)it3.next();
    itensproduto.addCell(new Phrase("", f));
    itensproduto.addCell(new Phrase(p.getNOME_PROD(), f));
    itensproduto.addCell(new Phrase(valueOf(ip.getQTD_ITEMPROD()), f));
    itensproduto.addCell(new Phrase(valueOf(ip.getVLR_ITEMPROD()), f));
    }
    doc.add(itensproduto);
    doc.add(new Paragraph("\n",f));
    }
    
    boolean i4 = it4.hasNext();
    boolean i5 = it5.hasNext();
    
    if(i4 == false && i5 == false){
        
    }
    else{
    PdfPTable cabservico = new PdfPTable(1);
    cabservico.addCell(new Phrase("Serviços", f));
    doc.add(cabservico);
    PdfPTable itensservico = new PdfPTable(4);
    itensservico.addCell(new Phrase("Código", f));
    itensservico.addCell(new Phrase("Serviço", f));
    itensservico.addCell(new Phrase("Quantidade", f));
    itensservico.addCell(new Phrase("Valor", f));
    while(it4.hasNext()&&it5.hasNext()){
    model.ItemServico is = (model.ItemServico)it4.next();
    model.Servico s = (model.Servico)it5.next();
    itensservico.addCell(new Phrase(String.valueOf(s.getCOD_SER()), f));
    itensservico.addCell(new Phrase(s.getDES_SER(), f));
    itensservico.addCell(new Phrase(valueOf(is.getQTD_ITEMSER()), f));
    itensservico.addCell(new Phrase(valueOf(is.getVLR_ITEMSER()), f));
    }
    doc.add(itensservico);
    doc.add(new Paragraph("\n",f));
    }
    
    PdfPTable valor = new PdfPTable(1);
    valor.addCell(new Phrase("Valor Total: "+nf.getVLR_NOTFIS(), f));
    doc.add(valor);
   
    doc.close();
    
    java.awt.Desktop desktop = getDesktop();  
    desktop.open(new File("../nfe/"+nf.getCOD_NOTFIS()+".pdf"));
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }   catch (FileNotFoundException ex) {
            getLogger(NotaFiscalDAO.class.getName()).log(SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            getLogger(NotaFiscalDAO.class.getName()).log(SEVERE, null, ex);
        }  
    }
    }
    /**
     *
     * @param vModuloCliente
     */
    public void abrirPDF(view.ModuloCliente vModuloCliente){
    try {
            java.awt.Desktop desktop = getDesktop();  
            desktop.open(new File("../nfe/"+vModuloCliente.gerarNota.getSelectedItem()+".pdf"));
        } catch (IOException ex) {
            getLogger(NotaFiscalDAO.class.getName()).log(SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param vModuloCliente
     */
    public void calcularNotaFiscal(view.ModuloCliente vModuloCliente) {
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlproduto = ("SELECT SUM(ITEMPROD.VLR_ITEMPROD) AS VALOR\n" +
                            "FROM ITEMPRODUTO ITEMPROD\n" +
                            "INNER JOIN NOTAFISCAL NOTFIS\n" +
                            "WHERE ITEMPROD.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                            "AND NOTFIS.COD_NOTFIS = ?");
    
    String sqlservico = ("SELECT SUM( ITEMSER.VLR_ITEMSER ) AS VALOR\n" +
                            "FROM ITEMSERVICO ITEMSER\n" +
                            "INNER JOIN NOTAFISCAL NOTFIS\n" +
                            "WHERE ITEMSER.NOTAFISCAL_COD_NOTFIS = NOTFIS.COD_NOTFIS\n" +
                            "AND NOTFIS.COD_NOTFIS = ?");
    

    PreparedStatement pSproduto = connectionFactory.Connection.prepareStatement(sqlproduto);
    PreparedStatement pSservico = connectionFactory.Connection.prepareStatement(sqlservico);

    pSproduto.setString(1, vModuloCliente.CONCLUIR_COD_NOTFIS.getSelectedItem().toString());
    pSservico.setString(1, vModuloCliente.CONCLUIR_COD_NOTFIS.getSelectedItem().toString());
                
    pSproduto.executeQuery();
    pSservico.executeQuery();
                
    ResultSet rSproduto = pSproduto.executeQuery();
    ResultSet rSservico = pSservico.executeQuery();
    
    rSproduto.first();
    rSservico.first();
    
    Double produto;
    Double servico;
    
    
    if(rSproduto == null){
        produto = 0.00;
        servico = rSservico.getDouble("VALOR");  
    }
    else if(rSservico == null){
        produto = rSproduto.getDouble("VALOR");
        servico = 0.00;
    }
    else {
        produto = rSproduto.getDouble("VALOR");
        servico = rSservico.getDouble("VALOR");
        }
    Double Soma = produto + servico;
    vModuloCliente.VLR_NOTFIS.setText(valueOf(Soma));
    }
    catch(SQLException sQLException){
    showMessageDialog(vModuloCliente, sQLException.getMessage());
    }
    }
    
    
    /**
     *
     * @param vModuloCliente
     */
    public void getHoraAtual(view.ModuloCliente vModuloCliente){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sqlproduto = ("SELECT DATE_FORMAT( CURRENT_DATE,  '%d%m%Y' ) AS ATUAL ");
    
    try (
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sqlproduto);  
    ){
                
    preparedStatement.executeQuery();
                
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if(resultSet.next()){ 
    vModuloCliente.DATEMI_NOTFIS.setText(resultSet.getString("ATUAL"));
    }
    else{
    showMessageDialog(null, "Data não encontrada.");
    }
    }
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloCliente
     */
    public void deletarPDF(view.ModuloCliente vModuloCliente){
    int mDeletar = YES_NO_OPTION;
        showConfirmDialog (null, "Deseja realmente excluir a Nota Fiscal?","Atenção",mDeletar);
    if (mDeletar == YES_OPTION){
    File file = new File("../nfe/"+vModuloCliente.gerarNota.getSelectedItem()+".pdf");  
    file.delete();  
    }
    else{
    
    }
    }
    
    /**
     *
     * @param x
     */
    public void ComboBoxNotaFiscal(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_NOTFIS FROM NOTAFISCAL");
    
    ArrayList <model.NotaFiscal> notafiscal = new ArrayList <> ();
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){   
    model.NotaFiscal nf = new model.NotaFiscal();
    nf.setCOD_NOTFIS(resultSet.getLong("COD_NOTFIS"));
    notafiscal.add(nf);
    }
    Iterator it = notafiscal.iterator();
    x.removeAllItems();
    while(it.hasNext()){
    model.NotaFiscal nf = (model.NotaFiscal)it.next();  
    x.addItem((nf.getCOD_NOTFIS()));   
    }
        
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
}
