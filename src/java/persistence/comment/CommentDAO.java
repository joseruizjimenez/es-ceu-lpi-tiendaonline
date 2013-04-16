package persistence.comment;

import java.util.ArrayList;
import model.Comment;

/**
 * Esta interfaz define un patron de persistencia DAO para los comentarios
 */
public interface CommentDAO {
    /**
     * Inserta un comentario en el sistema de persistencia
     * @param comment disco a insertar
     * @return true si hay exito, false en caso contrario
     */
    public boolean createComment(Comment comment);
    
    /**
     * Lee un comentario del sistema de persistencia
     * @param id string identificando el comentario a leer
     * @return comentario solicitado
     */
    public Comment readComment(String id);
    
    /**
     * Lista los comentarios asociados a un recordId
     * @param recordId id del disco que comentan
     * @return ArrayList de Comments. Vacio si no se encontraron coincidencias
     */
    public ArrayList<Comment> listComment(String recordId);
    
    /**
     * Actualiza la informacion de un comentario dado su id
     * @param id del comentario a actualizar
     * @param comment nuevo comentario
     * @return true si hay exito, false en caso contrario
     */
    public boolean updateComment(String id, Comment comment);
    
    /**
     * Borra un comentario del sistema de persistencia
     * @param id del comentario a borrar
     * @return true si hay exito, false en caso contrario
     */
    public boolean deleteComment(String id);
    
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
