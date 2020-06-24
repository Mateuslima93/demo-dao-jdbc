
package modelo.dao;

import demo.dao.jdbc.Conecao;
import modelo.dao.impl.VendedorDaoJdbc;

public class DaoFactory {
    public static VendedorDao createdSellerDao(){
        return new VendedorDaoJdbc(Conecao.getConnection());
    }
    
}
