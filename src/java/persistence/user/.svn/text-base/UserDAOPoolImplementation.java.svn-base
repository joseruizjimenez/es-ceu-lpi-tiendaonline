package persistence.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.User;
import org.apache.log4j.Logger;

/**
 * Implementacion de UserDAO para persistir la informacion con un pool de conexiones
 * 
 * @param DataSource el pool de conexiones
 * @param userPersistenceManager UserDAO de pool
 * @param logger para generar las trazas
 */
public class UserDAOPoolImplementation implements UserDAO {
    private static UserDAOPoolImplementation userPersistenceManager = null;
    private DataSource pool;
    private static final Logger logger = Logger.getLogger(UserDAOPoolImplementation.class.getName());
    
    private UserDAOPoolImplementation() {
    }

    public static UserDAO getUserDAOPoolImplementation() {
        if(userPersistenceManager == null)
            userPersistenceManager = new UserDAOPoolImplementation();
        
        return userPersistenceManager;
    }
    
    @Override
    public boolean createUser(User user) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCUserDAO.createUser(user);
        releaseQueryResources(jDBCUserDAO);
        return isExecutedOK;
    }

    @Override
    public User readUser(String id) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return null;
        }
        User user = jDBCUserDAO.readUser(id);
        releaseQueryResources(jDBCUserDAO);
        return user;
    }
    
    @Override
    public User readUserByNick(String nick) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return null;
        }
        User user = jDBCUserDAO.readUserByNick(nick);
        releaseQueryResources(jDBCUserDAO);
        return user;
    }

    @Override
    public ArrayList<User> listUser(String nick, String firstName,
            String lastName, String address, String email, int day, int month, int year) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return (new ArrayList<User>());
        }
        ArrayList<User> list = jDBCUserDAO.listUser(nick,firstName,lastName,address,
                email,day,month,year);
        releaseQueryResources(jDBCUserDAO);
        return list;
    }

    @Override
    public boolean updateUser(String id, User user) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCUserDAO.updateUser(id, user);
        releaseQueryResources(jDBCUserDAO);
        return isExecutedOK;
    }

    @Override
    public boolean deleteUser(String id) {
        UserDAO jDBCUserDAO = prepareForExecutingQuery();
        if(jDBCUserDAO == null){
            return false;
        }
        boolean isExecutedOK = jDBCUserDAO.deleteUser(id);
        releaseQueryResources(jDBCUserDAO);
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
     * Las consultas individuales se hace creando un UserDAOJDBCImplementation
     * @return UserDAO
     */
    private UserDAO prepareForExecutingQuery() {
        UserDAOJDBCImplementation jDBCpersistenceManager = new UserDAOJDBCImplementation();
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

    private void releaseQueryResources(UserDAO userDAO) {
        userDAO.disconnect();
    }
    
}