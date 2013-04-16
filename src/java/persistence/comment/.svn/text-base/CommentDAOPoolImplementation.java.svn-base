package persistence.comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.Comment;
import org.apache.log4j.Logger;

/**
 * Implementacion de CommentDAO para persistir la informacion con un pool de conexiones
 * 
 * @param DataSource el pool de conexiones
 * @param commentPersistenceManager CommentDAO de pool
 * @param logger para generar las trazas
 */
public class CommentDAOPoolImplementation implements CommentDAO {
    private static CommentDAOPoolImplementation commentPersistenceManager = null;
    private DataSource pool;
    private static final Logger logger = Logger.getLogger(CommentDAOPoolImplementation.class.getName());
    
    private CommentDAOPoolImplementation() {
    }

    public static CommentDAO getCommentDAOPoolImplementation() {
        if(commentPersistenceManager == null)
            commentPersistenceManager = new CommentDAOPoolImplementation();
        
        return commentPersistenceManager;
    }
    
    @Override
    public boolean createComment(Comment comment) {
        CommentDAO jDBCCommentDAO = prepareForExecutingQuery();
        if(jDBCCommentDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCCommentDAO.createComment(comment);
        releaseQueryResources(jDBCCommentDAO);
        return isExecutedOK;
    }

    @Override
    public Comment readComment(String id) {
        CommentDAO jDBCCommentDAO = prepareForExecutingQuery();
        if(jDBCCommentDAO == null){
            return null;
        }
        Comment comment = jDBCCommentDAO.readComment(id);
        releaseQueryResources(jDBCCommentDAO);
        return comment;
    }

    @Override
    public ArrayList<Comment> listComment(String recordId) {
        CommentDAO jDBCCommentDAO = prepareForExecutingQuery();
        if(jDBCCommentDAO == null){
            return (new ArrayList<Comment>());
        }
        ArrayList<Comment> list = jDBCCommentDAO.listComment(recordId);
        releaseQueryResources(jDBCCommentDAO);
        return list;
    }

    @Override
    public boolean updateComment(String id, Comment comment) {
        CommentDAO jDBCCommentDAO = prepareForExecutingQuery();
        if(jDBCCommentDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCCommentDAO.updateComment(id, comment);
        releaseQueryResources(jDBCCommentDAO);
        return isExecutedOK;
    }

    @Override
    public boolean deleteComment(String id) {
        CommentDAO jDBCCommentDAO = prepareForExecutingQuery();
        if(jDBCCommentDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCCommentDAO.deleteComment(id);
        releaseQueryResources(jDBCCommentDAO);
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
     * Las consultas individuales se hace creando un CommentDAOJDBCImplementation
     * @return CommentDAO
     */
    private CommentDAO prepareForExecutingQuery() {
        CommentDAOJDBCImplementation jDBCpersistenceManager = new CommentDAOJDBCImplementation();
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

    private void releaseQueryResources(CommentDAO commentDAO) {
        commentDAO.disconnect();
    }

}