package demo.dao.jdbc;

import java.util.List;
import modelo.dao.DaoFactory;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;


public class DemoDaoJdbc {

    public static void main(String[] args) {
        VendedorDao vendedorDao = DaoFactory.createdSellerDao();
        System.out.println("=== Test 1: seller findById");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);
        System.out.println("=== Test 2: seller findByDepartment");
        List<Vendedor> list = vendedorDao.findByDepartament(new Departamento(2,null));
        for (Vendedor obj: list) {
            System.out.println(obj);
        }
    }
    
}
