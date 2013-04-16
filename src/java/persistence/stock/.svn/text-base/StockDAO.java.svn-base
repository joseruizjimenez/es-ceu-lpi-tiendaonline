package persistence.stock;

import model.Stock;

/**
 * Esta interfaz define un patron de persistencia DAO para los Stock
 */
public interface StockDAO {
    /**
     * Inserta un stock en el sistema de persistencia
     * @param stock a insertar
     * @return true si hay exito, false en caso contrario
     */
    public boolean createStock(Stock stock);
    
    /**
     * Lee el stock de un articulo del sistema de persistencia
     * @param recordId identificador del producto
     * @return stock del producto. null si no existe el producto
     */
    public Stock readStock(String recordId);
    
    /**
     * Actualiza la informacion de un stock dado el id del producto
     * @param recordId del producto a actualizar
     * @param stock nuevo stock
     * @return true si hay exito, false en caso contrario
     */
    public boolean updateStock(String recordId, int stock);
    
    /**
     * Borra el stock de un producto del sistema de persistencia
     * @param recordId del producto a borrar
     * @return true si hay exito, false en caso contrario
     */
    public boolean deleteStock(String recordId);
    
    /**
     * Metodo para crear la conexion con el sistema de persistencia.
     * En el caso de trabajar contra ficheros la url define parte del nombre
     * @param url
     * @param driver
     * @param user
     * @param password
     * @return true si hay exito, false en caso contrario
     */
    public boolean setUp(String url, String driver, String user, String password);
    
    /**
     * Cierra la conexion con el sistema de persistencia.
     * @return true si hay exito, false en caso contrario
     */
    public boolean disconnect();

}
