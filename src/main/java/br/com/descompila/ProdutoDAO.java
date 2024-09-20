package br.com.descompila;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    
    public void salvar(Produto produto) throws Exception {

        var sql = "insert into produto (nome, quantidade, valor) values (?, ?, ?)";
        
        try (var conexao = Conexao.obterConexao();
            var stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, produto.nome());
                stmt.setInt(2, produto.quantidade());
                stmt.setDouble(3, produto.valor());
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<Produto> buscarTodos() throws Exception{

        var sql = "select * from produto";

        List<Produto> produtos = new ArrayList<>();

        try (var conexao = Conexao.obterConexao();
            var stmt = conexao.prepareStatement(sql)) {

                try(ResultSet rs = stmt.executeQuery()){
                    while (rs.next()) {
                        
                        Produto produto = new Produto(rs.getLong("id"), 
                        rs.getString("nome"), rs.getInt("quantidade"), 
                        rs.getDouble("valor"));

                        produtos.add(produto);
                    }
                }

        } catch (SQLException e) {
            throw new Exception(e);
        }

        return produtos;
    }

    public Produto buscarPorId(Long id) throws Exception{

        var sql = "select * from produto where id = ?";

        Produto produto = null;

        try (var conexao = Conexao.obterConexao();
            var stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try(ResultSet rs = stmt.executeQuery()){
                    while (rs.next()) {
                        produto = new Produto(rs.getLong("id"), 
                        rs.getString("nome"), rs.getInt("quantidade"), 
                        rs.getDouble("valor"));
                    }
                }

        } catch (SQLException e) {
            throw new Exception(e);
        }

        return produto;
    }

    public void atualizar(Produto produto) throws Exception {

        var sql = "update produto set nome = ?, quantidade = ?, valor = ? where id = ?";
        
        try (var conexao = Conexao.obterConexao();
            var stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, produto.nome());
                stmt.setInt(2, produto.quantidade());
                stmt.setDouble(3, produto.valor());
                stmt.setLong(4, produto.id());
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void excluir(Long id) throws Exception {

        var sql = "delete from produto where id = ?";
        
        try (var conexao = Conexao.obterConexao();
            var stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
