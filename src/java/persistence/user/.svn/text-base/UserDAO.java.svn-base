package persistence.user;

import java.util.ArrayList;
import model.User;

/**
 * Esta interfaz define un patron de persistencia DAO para los usuarios
 */
public interface UserDAO {
    /**
     * Inserta un usuario en el sistema de persistencia.
     * El nick del usuario no puede estar repetido
     * @param user a insertar
     * @return true si hay exito, false en caso contrario
     */
    public boolean createUser(User user);
    
    /**
     * Lee un usuario del sistema de persistencia por su id
     * @param id string identificando el usuario a leer
     * @return usuario solicitado, null si no existe
     */
    public User readUser(String id);
    
    /**
     * Lee un usuario del sistema de persistencia por su nick
     * @param nick alias de login del usuario
     * @return usuario solicitado, null si no existe
     */
    public User readUserByNick(String nick);
    
    /**
     * Lista los usuarios que cumplen las caracteristicas solicitadas.
     * Dejando los campos en null, estos no se tendran en cuenta en la busqueda
     * @param nick del usuario
     * @param firstName nombre del usuario
     * @param lastName apellidos del usuario
     * @param address direccion del usuario
     * @param email email del usuario
     * @param day dia de nacimiento. 0 para no tener en cuenta
     * @param month mes de nacimiento. 0 para no tener en cuenta
     * @param year anyo de nacimiento. 0 para no tener en cuenta
     * @return ArrayList de usuarios. Vacio si no se encontraron coincidencias
     */
    public ArrayList<User> listUser(String nick, String firstName, String lastName,
            String address, String email, int day, int month, int year);
    
    /**
     * Actualiza la informacion de un usuario dado su id
     * @param id del usuario a actualizar
     * @param user nuevo usuario
     * @return true si hay exito, false en caso contrario
     */
    public boolean updateUser(String id, User user);
    
    /**
     * Borra un usuario del sistema de persistencia
     * @param id del usuario a borrar
     * @return true si hay exito, false en caso contrario
     */
    public boolean deleteUser(String id);
    
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
