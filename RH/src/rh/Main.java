package rh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import rh.modelo.BancoDados;
import rh.modelo.CargoDAO;
import rh.modelo.ClienteDAO;
import rh.negocio.Funcionario;
import rh.negocio.Venda;
import rh.negocio.VendaItem;
import rh.negocio.Cargo;
import rh.negocio.Cliente;
import rh.negocio.Endereco;
import rh.negocio.EnderecoCliente;
import rh.visao.TelaCargo;


public class Main {

    public static void main(String[] args) throws SQLException  {
        
    	Cliente cliente = new Cliente(11, "luiz", "05415751127");
    	System.out.println(cliente);
    	
    	Venda venda = new Venda(10, 10, 10, 10, new Date());
    	System.out.println(venda);
    	
    	VendaItem vendaItem = new VendaItem(10, 10, 10, 50, 12.80);
    	System.out.println(vendaItem);

    	
    	EnderecoCliente enderecoCliente = new EnderecoCliente("Rua 14", "Jardim Goiás", "Morrinhos", "Goiás");
    	System.out.println(enderecoCliente);
    	    	
    	
    	
    	
    }
    
}
