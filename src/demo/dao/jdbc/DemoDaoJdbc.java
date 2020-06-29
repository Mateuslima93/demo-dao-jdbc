package demo.dao.jdbc;

import java.util.Date;
import java.util.List;
import modelo.dao.DaoFactory;
import modelo.dao.VendedorDao;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;


public class DemoDaoJdbc {

    public static void main(String[] args) {
        VendedorDao vendedorDao = DaoFactory.createdSellerDao();
        /*System.out.println("=== Test 1: seller findById");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);
        System.out.println("=== Test 2: seller findByDepartment");
        List<Vendedor> list = vendedorDao.findByDepartament(new Departamento(2,null));
        for (Vendedor obj: list) {
            System.out.println(obj);
        }
        System.out.println("=== Test 3: seller findAll");
        list = vendedorDao.findAll();
        list.forEach(System.out::println);
        
        System.out.println("=== Test 4: seller insert");
        Vendedor newVendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(),4000.00, new Departamento(2,null));
        vendedorDao.insert(newVendedor);
        System.out.println("Inserted! New id = " + newVendedor.getId());
        */
        System.out.println("=== Test 6: seller delete");
        vendedorDao.deleteById(25);
        
        /*  System.out.println("=== Test 5: seller update");
        Vendedor vendedor1 = vendedorDao.findById(1);
        vendedor1.setName("Lucas Camargo");
        vendedorDao.update(vendedor1);
        System.out.println("Update completo");*/
    }
    
}
