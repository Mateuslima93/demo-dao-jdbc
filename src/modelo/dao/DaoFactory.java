
package modelo.dao;

import modelo.dao.impl.VendedorDaoJdbc;

public class DaoFactory {
    public static VendedorDao createdSellerDao(){
        return new VendedorDaoJdbc();
    }
    
}
