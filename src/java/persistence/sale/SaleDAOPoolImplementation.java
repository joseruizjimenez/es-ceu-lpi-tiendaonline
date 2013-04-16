package persistence.sale;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.Sale;
import org.apache.log4j.Logger;

/**
 * Implementacion de SaleDAO para persistir la informacion con un pool de conexiones
 * 
 * @param DataSource el pool de conexiones
 * @param salePersistenceManager SaleDAO de pool
 * @param logger para generar las trazas
 */
public class SaleDAOPoolImplementation implements SaleDAO {
    private static SaleDAOPoolImplementation salePersistenceManager = null;
    private DataSource pool;
    private static final Logger logger = Logger.getLogger(SaleDAOPoolImplementation.class.getName());
    
    private SaleDAOPoolImplementation() {
    }

    public static SaleDAO getSaleDAOPoolImplementation() {
        if(salePersistenceManager == null)
            salePersistenceManager = new SaleDAOPoolImplementation();
        
        return salePersistenceManager;
    }
    
    @Override
    public boolean createSale(Sale sale) {
        SaleDAO jDBCSaleDAO = prepareForExecutingQuery();
        if(jDBCSaleDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCSaleDAO.createSale(sale);
        releaseQueryResources(jDBCSaleDAO);
        return isExecutedOK;
    }

    @Override
    public Sale readSale(String id) {
        SaleDAO jDBCSaleDAO = prepareForExecutingQuery();
        if(jDBCSaleDAO == null){
            return null;
        }
        Sale sale = jDBCSaleDAO.readSale(id);
        releaseQueryResources(jDBCSaleDAO);
        return sale;
    }

    @Override
    public ArrayList<Sale> listSale(String customerId, String customerName,
            String address, String paymentForm, String cartFootprint, String recordId) {
        SaleDAO jDBCSaleDAO = prepareForExecutingQuery();
        if(jDBCSaleDAO == null){
            return (new ArrayList<Sale>());
        }
        ArrayList<Sale> list = jDBCSaleDAO.listSale(customerId,customerName,address,
                paymentForm,cartFootprint,recordId);
        releaseQueryResources(jDBCSaleDAO);
        return list;
    }

    @Override
    public boolean updateSale(String id, Sale sale) {
        SaleDAO jDBCSaleDAO = prepareForExecutingQuery();
        if(jDBCSaleDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCSaleDAO.updateSale(id, sale);
        releaseQueryResources(jDBCSaleDAO);
        return isExecutedOK;
    }

    @Override
    public boolean deleteSale(String id) {
        SaleDAO jDBCSaleDAO = prepareForExecutingQuery();
        if(jDBCSaleDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCSaleDAO.deleteSale(id);
        releaseQueryResources(jDBCSaleDAO);
        return isExecutedOK;
    }

    @Override
    public boolean setUp(String url, String driver, String user, String password) {
        Context env = null;
        try {
            env = (Context) new InitialContext().lookup("java:comp/env");
            pool = (DataSource) env.lookup("jdbc/tiendaonline");
            if(pool == null){
                logger.error("No se encontro el DataSource");
                return false;
            }
        } catch (NamingException ex) {
            logger.error("No se pudo abrir la conexión contra base de datos", ex);
            return false;
        }
        return true; 
    }

    @Override
    public boolean disconnect() {
        return true;
    }
    
    /**
     * Las consultas individuales se hace creando un SaleDAOJDBCImplementation
     * @return SaleDAO
     */
    private SaleDAO prepareForExecutingQuery() {
        SaleDAOJDBCImplementation jDBCpersistenceManager = new SaleDAOJDBCImplementation();
        Connection connection;
        try {
            connection = pool.getConnection();
        } catch (SQLException ex) {
            logger.error("No se pudo abrir la conexion contra la base de datos", ex);
            return null;
        }
        jDBCpersistenceManager.setConnection(connection);
        return jDBCpersistenceManager;
    }

    private void releaseQueryResources(SaleDAO  saleDAO) {
        saleDAO.disconnect();
    }    

}