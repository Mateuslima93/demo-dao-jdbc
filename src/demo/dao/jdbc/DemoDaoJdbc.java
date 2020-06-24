package demo.dao.jdbc;

import java.util.Date;
import modelo.entidades.Departamento;
import modelo.entidades.Vendedor;

public class DemoDaoJdbc {

    public static void main(String[] args) {
        Departamento derp = new Departamento(1, "books");
        System.out.println(derp);
        Vendedor vend = new Vendedor(21,"Bob","bob@gmail.com",new Date(),3000.00,derp);
        System.out.println(vend);
    }
    
}
