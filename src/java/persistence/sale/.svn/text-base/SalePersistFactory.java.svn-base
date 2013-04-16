package persistence.sale;

/**
 * Genera una factoria de ventas siguiendo el patron DAO: SaleDAO
 */
public class SalePersistFactory {
    
    /**
     * Obten un sistema de persistencia del tipo declarado en persistenceMechanism
     * @param persistenceMechanism puede ser file, jdbc o pool
     * @return SaleDAO si todo va bien, sino null
     */
    public static SaleDAO getSaleDAO(String persistenceMechanism){
        if (persistenceMechanism.equals("file")){
            return SaleDAOFileImplementation.getSaleDAOFileImplementation();
        }
        else if(persistenceMechanism.equals("jdbc")){
            return SaleDAOJDBCImplementation.getSaleDAOJDBCImplementation();
        }
        else if (persistenceMechanism.equals("pool")) {
            return SaleDAOPoolImplementation.getSaleDAOPoolImplementation();
        }
        else {
            return null;
        }
    }
}
