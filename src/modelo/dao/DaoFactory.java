
package modelo.dao;

import demo.dao.jdbc.Conecao;
import modelo.dao.impl.DepartamentoDaoJdbc;
import modelo.dao.impl.VendedorDaoJdbc;

public class DaoFactory {
    public static VendedorDao createdSellerDao(){
        return new VendedorDaoJdbc(Conecao.getConnection());
    }
    public static DepartamentoDao createdDepartmentDao(){
        return new DepartamentoDaoJdbc(Conecao.getConnection());
    }
}
