package persistence.stock;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.Stock;
import org.apache.log4j.Logger;

/**
 * Implementacion de StockDAO para persistir la informacion con un pool de conexiones
 * 
 * @param DataSource el pool de conexiones
 * @param stockPersistenceManager StockDAO de pool
 * @param logger para generar las trazas
 */
public class StockDAOPoolImplementation implements StockDAO {
    private static StockDAOPoolImplementation stockPersistenceManager = null;
    private DataSource pool;
    private static final Logger logger = Logger.getLogger(StockDAOPoolImplementation.class.getName());
    
    private StockDAOPoolImplementation() {
    }

    public static StockDAO getStockDAOPoolImplementation() {
        if(stockPersistenceManager == null)
            stockPersistenceManager = new StockDAOPoolImplementation();
        
        return stockPersistenceManager;
    }
    
    @Override
    public boolean createStock(Stock stock) {
        StockDAO jDBCStockDAO = prepareForExecutingQuery();
        if(jDBCStockDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCStockDAO.createStock(stock);
        releaseQueryResources(jDBCStockDAO);
        return isExecutedOK;
    }

    @Override
    public Stock readStock(String recordId) {
        StockDAO jDBCStockDAO = prepareForExecutingQuery();
        if(jDBCStockDAO == null){
            return null;
        }
        Stock stock = jDBCStockDAO.readStock(recordId);
        releaseQueryResources(jDBCStockDAO);
        return stock;
    }

    @Override
    public boolean updateStock(String recordId, int stock) {
        StockDAO jDBCStockDAO = prepareForExecutingQuery();
        if(jDBCStockDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCStockDAO.updateStock(recordId, stock);
        releaseQueryResources(jDBCStockDAO);
        return isExecutedOK;
    }

    @Override
    public boolean deleteStock(String recordId) {
        StockDAO jDBCStockDAO = prepareForExecutingQuery();
        if(jDBCStockDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCStockDAO.deleteStock(recordId);
        releaseQueryResources(jDBCStockDAO);
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
     * Las consultas individuales se hace creando un StockDAOJDBCImplementation
     * @return StockDAO
     */
    private StockDAO prepareForExecutingQuery() {
        StockDAOJDBCImplementation jDBCpersistenceManager = new StockDAOJDBCImplementation();
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

    private void releaseQueryResources(StockDAO  stockDAO) {
        stockDAO.disconnect();
    }
}
