package demo.dao.jdbc;

import java.util.Date;
import java.util.List;
import modelo.dao.DaoFactory;
import modelo.dao.DepartamentoDao;
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
        System.out.println("=== Test 3: seller findAll");
        list = vendedorDao.findAll();
        list.forEach(System.out::println);
        
        System.out.println("=== Test 4: seller insert");
        Vendedor newVendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(),4000.00, new Departamento(2,null));
        vendedorDao.insert(newVendedor);
        System.out.println("Inserted! New id = " + newVendedor.getId());
        
        System.out.println("=== Test 5: seller update");
        vendedor = vendedorDao.findById(1);
        vendedor.setName("Lucas Camargo");
        vendedorDao.update(vendedor);
        System.out.println("Update completo");
        
        System.out.println("=== Test 6: seller delete");
        for (int i = 17; i >= 9; i--) {
           vendedorDao.deleteById(i);
        }
        
        
        DepartamentoDao dep = DaoFactory.createdDepartmentDao();
        System.out.println("TEST 1: department findById");
        Departamento departamento = dep.findById(1);
        System.out.println(departamento);
        System.out.println("TEST 2: department findAll");
        List<Departamento> list1 = dep.findAll();
        list1.forEach(System.out::println);
        System.out.println("TEST 3: department insert");
        Departamento novoDepartamento = new Departamento(null,"Movies");
        dep.insert(novoDepartamento);
        System.out.println("Inserted! New id = " + novoDepartamento.getId());
        System.out.println("TEST 4: department update");
        departamento = dep.findById(5);
        departamento.setName("Tables");
        dep.update(departamento);
        System.out.println("Update Completo");
        System.out.println("TEST 5: department deleteById");
        dep.deleteById(6);
    }
    
}
