/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rh.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import rh.negocio.Cliente;
import rh.negocio.EnderecoCliente;
import rh.modelo.ClienteEnderecoDAO;

/**
 * Realiza as resposabilidades comportamentais necessárias para a persistencia
 * de um funcionário no banco de dados
 *
 * @author L
 */
public class ClienteDAO {

    public static int create(Cliente c) throws SQLException {
        Connection conn = BancoDados.createConnection();

        String sql = "INSERT INTO clientes(nome, cpf) VALUES (?, ?)";

        PreparedStatement stm = conn.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS);        
        stm.setString(1, c.getNome());
        stm.setString(2, c.getCpf());

        stm.execute();

        ResultSet rs = stm.getGeneratedKeys();

        rs.next();

        int pkc = rs.getInt("pk_cliente");

        c.setPk_cliente(pkc);

        for (EnderecoCliente aux : c.getEndereco()) {
            aux.setFk_cliente(pkc);

            ClienteEnderecoDAO.create(aux);
        }

        return pkc;
    }

    public static Cliente retrieve(int pk_cliente) throws SQLException {
        Connection conn = BancoDados.createConnection();

        String sql = "select * from clientes where pk_cliente = ?";

        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, pk_cliente);

        stm.execute();

        ResultSet rs = stm.getResultSet();

        rs.next();

        Cliente c = new Cliente(
                pk_cliente,
                rs.getString("nome"),
                rs.getString("cpf"));
                
                c.setEndereco(ClienteEnderecoDAO.retrieveAll(pk_cliente));
        return c;
    }

    public static ArrayList<Cliente> retrieveAll() throws SQLException {
        Connection conn = BancoDados.createConnection();

        String sql = "select * from clientes";

        PreparedStatement stm = conn.prepareStatement(sql);

        stm.execute();

        ResultSet rs = stm.getResultSet();

        ArrayList<Cliente> aux = new ArrayList<>();

        while (rs.next()) {
            Cliente caux = new Cliente(
                    rs.getInt("pk_cliente"),
                    rs.getString("nome"),
                    rs.getString("cpf"));
            caux.setEndereco(ClienteEnderecoDAO.retrieveAll(caux.getPk_cliente()));

            aux.add(caux);
        }
        return aux;
    }
    
    public static void update(Funcionario f) throws SQLException{
        Connection conn = BancoDados.createConnection();
        
        String sql = "UPDATE funcionarios SET fk_cargo=?, nome=?, cpf=? WHERE pk_funcionario=?";
        
        PreparedStatement stm = conn.prepareCall(sql);
        
        stm.setInt(1, f.getCargo().getPk());
        stm.setString(2, f.getNome());
        stm.setString(3, f.getCpf());
        stm.setInt(4, f.getPk_funcionario());
        
        stm.execute();
        
        for (EnderecoFuncionario aux: f.getEndereco()){
            if (!aux.isSync()){
               FuncionarioEnderecoDAO.update(aux);
            }
        }
    }
    
    public static void delete(Funcionario f) throws SQLException{
        if (f.getPk_funcionario()==0){
            throw new SQLException("Objeto não persistido ainda ou com a chave primária não configurada");
        }

        String sql = "delete from funcionarios where pk_funcionario =?";
        
        Connection conn = BancoDados.createConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        
        stm.setInt(1, f.getPk_funcionario());       
        stm.execute();
        stm.close();        
    }

}
