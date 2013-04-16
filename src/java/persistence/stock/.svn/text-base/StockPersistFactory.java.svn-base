package persistence.stock;

/**
 * Genera una factoria de Stocks siguiendo el patron DAO: StockDAO
 */
public class StockPersistFactory {
    
    /**
     * Obten un sistema de persistencia del tipo declarado en persistenceMechanism
     * @param persistenceMechanism puede ser file, jdbc o pool
     * @return StockDAO si todo va bien, sino null
     */
    public static StockDAO getStockDAO(String persistenceMechanism){
        if (persistenceMechanism.equals("file")){
            return StockDAOFileImplementation.getStockDAOFileImplementation();
        }
        else if(persistenceMechanism.equals("jdbc")){
            return StockDAOJDBCImplementation.getStockDAOJDBCImplementation();
        }
        else if (persistenceMechanism.equals("pool")) {
            return StockDAOPoolImplementation.getStockDAOPoolImplementation();
        }
        else {
            return null;
        }
    }
}