
package demo.dao.jdbc;

public class ConexaoIntegrityException extends RuntimeException {
    public ConexaoIntegrityException (String msg){
        super(msg);
    }
}
