
package modelo.dao.impl;

import demo.dao.jdbc.Conecao;
import demo.dao.jdbc.ConexaoIntegrityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
                Departamento dep = new Departamento(rs.getInt("DepartmentId"),rs.getString("DepName"));
                Vendedor obj = new Vendedor (rs.getInt("Id"),rs.getString("Name"),rs.getString("Email"),rs.getDate("BirthDate"),rs.getDouble("BaseSalary"),dep);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
