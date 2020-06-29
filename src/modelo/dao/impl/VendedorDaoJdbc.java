
package modelo.dao.impl;

import com.mysql.jdbc.Statement;
import demo.dao.jdbc.Conecao;
import demo.dao.jdbc.ConexaoIntegrityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class VendedorDaoJdbc implements VendedorDao {
    private Connection conn; 
    
    public VendedorDaoJdbc(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Vendedor obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
        st = conn.prepareStatement(
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS); 
        st.setString(1, obj.getName());
        st.setString(2, obj.getEmail());
        st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
        st.setDouble(4, obj.getBaseSalary());
        st.setInt(5,obj.getDepartamento().getId());
        int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
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
            Conecao.closeResultSet(rs);
            }
    }

    @Override
    public void update(Vendedor obj) {
        PreparedStatement st = null;
        try {
            conn.prepareStatement(
                    "UPDATE seller "
                    + "SET Name = ?, Email = ?, BirthDate = ?, "
                    + "BaseSalary = ?, DepartmentId = ? "
                    + "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartamento().getId());
            st.setInt(6, obj.getId());
            
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
        }

    @Override
    public Vendedor findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Departamento dep = instantiateDepartment(rs); 
                Vendedor obj = instantiateSeller(rs,dep);
                return obj;
            }
            else{
                return null;
            }
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
    public List<Vendedor> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " 
                    + "FROM seller INNER JOIN department " 
                    + "ON seller.DepartmentId = department.Id " 
                    + "ORDER BY Name");

            rs = st.executeQuery();

            List<Vendedor> listVend = new ArrayList<>();
            Map<Integer,Departamento> map = new HashMap<>();

            while (rs.next()){
                Departamento dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                listVend.add(instantiateSeller(rs,dep));
            }
        return listVend;
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeStatement(st);
            Conecao.closeResultSet(rs);
            
        }
    }

    private Departamento instantiateDepartment(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento(rs.getInt("DepartmentId"),rs.getString("DepName"));
        return dep;  
    }

    private Vendedor instantiateSeller(ResultSet rs, Departamento dep) throws SQLException {
        Vendedor vendedor = new Vendedor (rs.getInt("Id"),rs.getString("Name"),rs.getString("Email"),rs.getDate("BirthDate"),rs.getDouble("BaseSalary"),dep);
        return vendedor;
    }

    @Override
    public List<Vendedor> findByDepartament(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " 
                    + "FROM seller INNER JOIN department " 
                    + "ON seller.DepartmentId = department.Id " 
                    + "WHERE DepartmentId = ? " 
                    + "ORDER BY Name");
            st.setInt(1, departamento.getId());
            rs = st.executeQuery();
            List<Vendedor> listVend = new ArrayList<>();
            Map<Integer,Departamento> map = new HashMap<>();
            while (rs.next()){
                Departamento dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                listVend.add(instantiateSeller(rs,dep));
            }
        return listVend;
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
