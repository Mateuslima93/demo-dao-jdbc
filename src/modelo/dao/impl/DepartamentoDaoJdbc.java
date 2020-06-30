package modelo.dao.impl;

import demo.dao.jdbc.Conecao;
import demo.dao.jdbc.ConexaoIntegrityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.dao.DepartamentoDao;
import modelo.entidades.Departamento;

public class DepartamentoDaoJdbc implements DepartamentoDao {
    private Connection conn;
    
    public DepartamentoDaoJdbc(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Departamento obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO department "
                    + "(Name) "
                    + "VALUES "
                    + "(?)",
                    + Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                Conecao.closeResultSet(rs);
            }
            else{
                throw new ConexaoIntegrityException("Erro inexperado. Nenhuma linha afetada.");
            }
        }
        catch(SQLException e){
             throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
        }
    }

    @Override
    public void update(Departamento obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?");
            st.setString(1,obj.getName());
            st.setInt(2, obj.getId());
            
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM department "
                    + "WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
        }
    }
    
    public Departamento instantiateDepartment(ResultSet rs) throws SQLException{
        Departamento dep = new Departamento();
            dep.setId(rs.getInt("Id"));
            dep.setName(rs.getString("Name"));
        return dep;
    }
    @Override
    public Departamento findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM department " 
                    + "WHERE department.Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()){
                Departamento dep = instantiateDepartment(rs);
                return dep;
            }
            return null;
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
            Conecao.closeResultSet(rs);
        }
    }

    @Override
    public List<Departamento> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT department.* FROM department " 
                    + "ORDER BY Name");

            rs = st.executeQuery();
            
            List<Departamento> list = new ArrayList <>();
            while (rs.next()){
                list.add(instantiateDepartment(rs));
            }
            return list;
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
            Conecao.closeResultSet(rs);
        }
    }
    
}
