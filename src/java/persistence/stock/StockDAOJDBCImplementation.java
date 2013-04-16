package persistence.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Stock;
import org.apache.log4j.Logger;

/**
 * Implementacion de StockDAO para persistir la informacion con JDBC en MySQL
 * 
 * @param lockOfConnection objeto para controlar los accesos no concurrentes
 * @param connection conexion con la base de datos
 * @param stockPersistenceManager StockDAO de jdbc
 * @param logger para generar las trazas
 */
public class StockDAOJDBCImplementation implements StockDAO{
    private final Object lockOfConnection = new Object();
    private Connection connection = null;
    private static StockDAOJDBCImplementation stockPersistenceManager = null;
    private static final Logger logger = Logger.getLogger(StockDAOJDBCImplementation.class.getName());
    
    //Visibilidad de paquete para usarlo con StockDAOPoolImplementation
    StockDAOJDBCImplementation() {
    }
    
    public static StockDAO getStockDAOJDBCImplementation() {
        if(stockPersistenceManager == null)
            stockPersistenceManager = new StockDAOJDBCImplementation();
        
        return stockPersistenceManager;
    }
    
    @Override
    public boolean createStock(Stock stock) {
        String query = "insert into STOCKS values(?,?)";
        PreparedStatement statement;
        try{
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,stock.getRecordIdAsString());
            statement.setInt(2,stock.getStock());
            statement.execute();
            logger.trace("Insertado el stock en BD: "+stock.getRecordIdAsString());
            return true;
        } catch (SQLException ex) {
            logger.error("Error al crear el stock", ex);
            return false;
        }
    }

    @Override
    public Stock readStock(String recordId) {
        String query = "select * from STOCKS where RECORDID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Stock stock = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, recordId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = new Stock(recordId,resultSet.getInt("STOCK"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar el stock", ex);
            stock = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return stock;
    }

    @Override
    public boolean updateStock(String recordId, int stock) {
        String query = null;
        query = "update STOCKS SET STOCK =? where RECORDID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setInt(1, stock);
            statement.setString(2, recordId);
            statement.execute();
            logger.trace("Actualizado el stock en BD: "+recordId+" a: "+stock);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al actualizar el stock", ex);
            return false;
        }
    }

    @Override
    public boolean deleteStock(String recordId) {
        String query = "delete from STOCKS where RECORDID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, recordId);
            statement.execute();
            logger.trace("Borrado el stock en BD: "+recordId);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al borrar el stock", ex);
            return false;
        }
    }

    @Override
    public boolean setUp(String url, String driver, String user, String password) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            logger.error("No se encontro el driver para la base de datos", ex);
            return false;
        } catch (SQLException ex) {
            logger.error("No se pudo establecer la conexion con la base de datos", ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean disconnect() {
        try {
            connection.close();
        } catch (SQLException ex) {
            logger.error("Conexión a la base de datos no cerrada", ex);
            return false;
        }
        return true;
    }
    
    /**
     * Para establecer conexiones en el StockDAOPoolImplementation mediante esta clase
     * @param connection
     */
    public void setConnection(Connection connection) {
        synchronized (lockOfConnection) {
            this.connection = connection;
        }
    }
}