package persistence.comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import model.Comment;
import org.apache.log4j.Logger;

/**
 * Implementacion de CommentDAO para persistir la informacion con JDBC en MySQL
 * 
 * @param lockOfConnection objeto para controlar los accesos no concurrentes
 * @param connection conexion con la base de datos
 * @param commentPersistenceManager CommentDAO de jdbc
 * @param logger para generar las trazas
 */
public class CommentDAOJDBCImplementation implements CommentDAO{
    private final Object lockOfConnection = new Object();
    private Connection connection = null;
    private static CommentDAOJDBCImplementation commentPersistenceManager = null;
    private static final Logger logger = Logger.getLogger(CommentDAOJDBCImplementation.class.getName());
    
    //Visibilidad de paquete para usarlo con CommentDAOPoolImplementation
    CommentDAOJDBCImplementation() {
    }
    
    public static CommentDAO getCommentDAOJDBCImplementation() {
        if(commentPersistenceManager == null)
            commentPersistenceManager = new CommentDAOJDBCImplementation();
        
        return commentPersistenceManager;
    }
    
    @Override
    public boolean createComment(Comment comment) {
        String query = "insert into COMMENTS values(?,?,?,?)";
        PreparedStatement statement;
        try{
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,comment.getIdAsString());
            statement.setString(2,comment.getRecordIdAsString());
            statement.setString(3,comment.getNickname());
            statement.setString(4,comment.getComment());
            statement.execute();
            logger.trace("Insertado el comentario en BD: "+comment.getIdAsString());
            return true;
        } catch (SQLException ex) {
            logger.error("Error al insertar el commentario", ex);
            return false;
        }
    }

    @Override
    public Comment readComment(String id) {
        String query = "select * from COMMENTS where ID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Comment comment = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                comment = new Comment(resultSet.getString("ID"));
                comment.setRecordId(UUID.fromString(resultSet.getString("RECORDID")));
                comment.setNickname(resultSet.getString("NICK"));
                comment.setComment(resultSet.getString("COMMENT"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un comentario", ex);
            comment = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return comment;
    }

    @Override
    public ArrayList<Comment> listComment(String recordId) {
        ArrayList<Comment> list = new ArrayList();
        String query = "select * from COMMENTS where RECORDID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Comment comment = null;
        
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, recordId);
            resultSet = statement.executeQuery();
            UUID recordIdUUID = UUID.fromString(recordId);
            while (resultSet.next()) {
                comment = new Comment(resultSet.getString("ID"));
                comment.setRecordId(recordIdUUID);
                comment.setNickname(resultSet.getString("NICK"));
                comment.setComment(resultSet.getString("COMMENT"));
                list.add(comment);
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar los comentarios", ex);
            list.clear();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }        
        return list;
    }

    @Override
    public boolean updateComment(String id, Comment comment) {
        String query = null;
        query = "update COMMENTS SET NICK = ?,COMMENT = ? where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, comment.getNickname());
            statement.setString(2, comment.getComment());
            statement.setString(3, id);
            statement.execute();
            logger.trace("Actualizado el comentario en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al actualizar un comentario", ex);
            return false;
        }
    }

    @Override
    public boolean deleteComment(String id) {
        String query = "delete from COMMENTS where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            statement.execute();
            logger.trace("Borrado el comentario en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al borrar un comentario", ex);
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
     * Para establecer conexiones en el CommentDAOPoolImplementation mediante esta clase
     * @param connection
     */
    public void setConnection(Connection connection) {
        synchronized (lockOfConnection) {
            this.connection = connection;
        }
    }

}