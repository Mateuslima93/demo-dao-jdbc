package demo.dao.jdbc;

import modelo.dao.DaoFactory;
import modelo.dao.VendedorDao;
import modelo.entidades.Vendedor;


public class DemoDaoJdbc {

    public static void main(String[] args) {
        VendedorDao vendedorDao = DaoFactory.createdSellerDao();
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);
    }
    
}
