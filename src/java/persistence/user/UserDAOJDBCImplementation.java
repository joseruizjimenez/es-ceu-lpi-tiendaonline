package persistence.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import model.User;
import org.apache.log4j.Logger;

/**
 * Implementacion de UserDAO para persistir la informacion con JDBC en MySQL
 * 
 * @param lockOfConnection objeto para controlar los accesos no concurrentes
 * @param connection conexion con la base de datos
 * @param userPersistenceManager UserDAO de jdbc
 * @param logger para generar las trazas
 */
public class UserDAOJDBCImplementation implements UserDAO{
    private final Object lockOfConnection = new Object();
    private Connection connection = null;
    private static UserDAOJDBCImplementation userPersistenceManager = null;
    private static final Logger logger = Logger.getLogger(UserDAOJDBCImplementation.class.getName());
    
    //Visibilidad de paquete para usarlo con UserDAOPoolImplementation
    UserDAOJDBCImplementation() {
    }
    
    public static UserDAO getUserDAOJDBCImplementation() {
        if(userPersistenceManager == null)
            userPersistenceManager = new UserDAOJDBCImplementation();
        
        return userPersistenceManager;
    }
    
    @Override
    public boolean createUser(User user) {
        String query = "insert into USERS values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement;
        try{
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,user.getIdAsString());
            statement.setString(2,user.getNick());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getFirstName());
            statement.setString(5,user.getLastName());
            Date birthDate = new Date(user.getBirthDateInMillis());
            statement.setDate(6,birthDate);
            statement.setString(7,user.getAddress());
            statement.setString(8,user.getEmail());
            statement.setString(9,user.getPaymentForm());
            statement.execute();
            logger.trace("Insertado el usuario en BD: "+user.getIdAsString());
            return true;
        } catch (SQLException ex) {
            logger.error("Error al crear el usuario", ex);
            return false;
        }
    }

    @Override
    public User readUser(String id) {
        String query = "select * from USERS where ID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        User user = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(id);
                user.setNick(resultSet.getString("NICK"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setFirstName(resultSet.getString("FIRSTNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setBirthDate(resultSet.getDate("BIRTHDATE").toString());
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setEmail(resultSet.getString("EMAIL"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un usuario", ex);
            user = null;
        } catch (ParseException ex) {
            logger.warn("Error parseando birthDate",ex);
            user = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return user;
    }
    
    @Override
    public User readUserByNick(String nick) {
        String query = "select * from USERS where NICK =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        User user = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, nick);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getString("ID"));
                user.setNick(nick);
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setFirstName(resultSet.getString("FIRSTNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setBirthDate(resultSet.getDate("BIRTHDATE").toString());
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setEmail(resultSet.getString("EMAIL"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un usuario", ex);
            user = null;
        } catch (ParseException ex) {
            logger.warn("Error parseando birthDate",ex);
            user = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return user;
    }

    @Override
    public ArrayList<User> listUser(String nick, String firstName,
            String lastName, String address, String email, int day, int month, int year) {
        ArrayList<User> list = new ArrayList();
        String query = "select * from USERS";
        PreparedStatement statement;
        ResultSet resultSet = null;
        User user = null;
        int nickPosition=1, firstNamePosition=2, lastNamePosition=3, addressPosition=4,
                emailPosition = 5, birthDatePosition = 6;
        boolean isWhereWritten = false;
        
        try {
            if (nick != null) {
                query = query.concat(" where");
                isWhereWritten = true;
                query = query.concat(" NICK =?");
            } else {
                nickPosition = 0;
                firstNamePosition--;
                lastNamePosition--;
                addressPosition--;
                emailPosition--;
                birthDatePosition--;
            }
            if (firstName != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" FIRSTNAME =?");
            } else {
                firstNamePosition = 0;
                lastNamePosition--;
                addressPosition--;
                emailPosition--;
                birthDatePosition--;
            }
            if (lastName != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" LASTNAME =?");
            } else {
                lastNamePosition = 0;
                addressPosition--;
                emailPosition--;
                birthDatePosition--;
            }
            if(address != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" ADDRESS =?");
            } else {
                addressPosition = 0;
                emailPosition--;
                birthDatePosition--;
            }
            if(email != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" EMAIL =?");
            } else {
                emailPosition = 0;
                birthDatePosition--;
            }
            if(day!=0 && month!=0 && year!=0) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" BIRTHDATE =?");
            } else {
                birthDatePosition = 0;
            }

            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            if (nickPosition != 0) {
                statement.setString(nickPosition, nick);
            }
            if (firstNamePosition != 0) {
                statement.setString(firstNamePosition, firstName);
            }
            if (lastNamePosition != 0) {
                statement.setString(lastNamePosition, lastName);
            }
            if(addressPosition != 0) {
                statement.setString(addressPosition, address);
            }
            if(emailPosition != 0) {
                statement.setString(emailPosition, email);
            }
            if(birthDatePosition != 0) {
                Calendar birthDate = Calendar.getInstance();
                birthDate.clear();
                birthDate.set(year, month-1, day);
                statement.setDate(birthDatePosition,new Date(birthDate.getTimeInMillis()));
            } 
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getString("ID"));
                user.setNick(resultSet.getString("NICK"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setFirstName(resultSet.getString("FIRSTNAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setBirthDate(resultSet.getDate("BIRTHDATE").toString());
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setEmail(resultSet.getString("EMAIL"));
                list.add(user);
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un usuario", ex);
            list.clear();
        } catch (ParseException ex) {
            logger.warn("Error parseando birthDate",ex);
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
    public boolean updateUser(String id, User user) {
        String query = null;
        query = "update USERS SET NICK =?,PASSWORD = ?,FIRSTNAME = ?,"
                + "LASTNAME = ?, BIRTHDATE = ?, ADDRESS = ?, EMAIL = ?,"
                + "PAYMENTFORM = ? where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, user.getNick());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            Date birthDate = new Date(user.getBirthDateInMillis());
            statement.setDate(5,birthDate);
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getEmail());
            statement.setString(8, user.getPaymentForm());
            statement.setString(9, id);
            statement.execute();
            logger.trace("Actualizado el usuario en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al actualizar un usuario", ex);
            return false;
        }
    }

    @Override
    public boolean deleteUser(String id) {
        String query = "delete from USERS where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            statement.execute();
            logger.trace("Borrado el usuario en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al borrar un usuario", ex);
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
     * Para establecer conexiones en el UserDAOPoolImplementation mediante esta clase
     * @param connection
     */
    public void setConnection(Connection connection) {
        synchronized (lockOfConnection) {
            this.connection = connection;
        }
    }
    
}