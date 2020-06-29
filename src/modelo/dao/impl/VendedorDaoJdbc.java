
package modelo.dao.impl;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Vendedor obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            return null;
        }
        catch(SQLException e){
            throw new ConexaoIntegrityException(e.getMessage());
        }
        finally{
            Conecao.closeResultSet(rs);
            Conecao.closeStatement(st);
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
            Conecao.closeResultSet(rs);
            Conecao.closeStatement(st);
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
            Conecao.closeResultSet(rs);
            Conecao.closeStatement(st);
        }
    }
}
