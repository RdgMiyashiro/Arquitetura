package br.edu.utfpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dto.PaisDTO;
import lombok.extern.java.Log;

@Log
public class ClienteDAO {

    // Responsável por criar a tabela Cliente no banco.
    public ClienteDAO() {

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela cliente ...");
            conn.createStatement().executeUpdate(
            "CREATE TABLE cliente (" +
						"id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_cliente_pk PRIMARY KEY," +
						"nome varchar(255)," +
						"telefone varchar(30)," + 
						"idade int," + 
                        "limiteCredito double," +
                        "id_pais int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(ClienteDTO cli){

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            String sql = "INSERT INTO cliente (nome, telefone, idade, limiteCredito, id_pais) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cli.getNome());
            ps.setString(2, cli.getTelefone());
            ps.setString(3, String.valueOf(cli.getIdade()));
            ps.setString(4, String.valueOf(cli.getLimiteCredito()));
            ps.setString(5, String.valueOf(cli.getPais()));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Novo cliente cadastrado!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClienteDTO> sel(ClienteDTO cli){

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            List<ClienteDTO> clientList = new ArrayList<ClienteDTO>();

            PreparedStatement ps = conn.prepareStatement("SELECT id, nome, telefone, idade, limiteCredito, id_pais from cliente");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaisDTO p = new PaisDTO().setId(rs.getInt("id_pais");
                ClienteDTO client = new ClienteDTO();
                client.setId(rs.getInt("id"));
                client.setNome(rs.getString("nome"));
                client.setTelefone(rs.getString("telefone"));
                client.setIdade(rs.getInt("idade"));
                client.setLimiteCredito(rs.getDouble("limiteCredito"));
                client.setPais(p);

                clientList.add(client);
            }

            ps.close();

            return clientList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void upd(ClienteDTO cli){
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            String sql = "UPDATE cliente SET nome=?, telefone=?, idade=?, limiteCredito=?, id_pais=? WHERE id=?\"";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cli.getNome());
            ps.setString(2, cli.getTelefone());
            ps.setString(3, String.valueOf(cli.getIdade()));
            ps.setString(4, String.valueOf(cli.getLimiteCredito()));
            ps.setString(5, String.valueOf(cli.getPais()));
            ps.setString(6, String.valueOf(cli.getId()));

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cliente atualizado!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del(ClienteDTO cli){
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            String sql = "DELETE FROM cliente WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(cli.getId()));

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cliente excluido!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
