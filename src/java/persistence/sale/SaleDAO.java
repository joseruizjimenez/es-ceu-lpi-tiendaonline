package persistence.sale;

import java.util.ArrayList;
import model.Sale;

/**
 * Esta interfaz define un patron de persistencia DAO para las ventas
 */
public interface SaleDAO {
    /**
     * Inserta una venta en el sistema de persistencia
     * @param sale a insertar
     * @return true si hay exito, false en caso contrario
     */
    public boolean createSale(Sale sale);
    
    /**
     * Lee una venta del sistema de persistencia
     * @param id string identificando la venta a leer
     * @return venta solicitada
     */
    public Sale readSale(String id);

    /**
     * Lista las ventas que cumplen las caracteristicas solicitadas.
     * Dejando los campos en null, estos no se tendran en cuenta en la busqueda
     * @param customerId id del cliente
     * @param customerName nombre del cliente
     * @param address direccion de envio
     * @param paymentForm forma de pago
     * @param cartFootprint huella del carro de productos vendido
     * @param recordId producto concreto perteneciente a la venta
     * @return 
     */
    public ArrayList<Sale> listSale(String customerId, String customerName, String address,
            String paymentForm, String cartFootprint, String recordId);
    
    /**
     * Actualiza la informacion de una venta dada su id
     * @param id de la venta a actualizar
     * @param sale nueva venta
     * @return true si hay exito, false en caso contrario
     */
    public boolean updateSale(String id, Sale sale);
    
    /**
     * Borra una venta del sistema de persistencia
     * @param id de la venta a borrar
     * @return true si hay exito, false en caso contrario
     */
    public boolean deleteSale(String id);
    
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
