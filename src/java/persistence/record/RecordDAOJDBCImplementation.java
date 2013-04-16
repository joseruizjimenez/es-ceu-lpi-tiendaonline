package persistence.record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.Record;
import org.apache.log4j.Logger;

/**
 * Implementacion de RecordDAO para persistir la informacion con JDBC en MySQL
 * 
 * @param lockOfConnection objeto para controlar los accesos no concurrentes
 * @param connection conexion con la base de datos
 * @param recordPersistenceManager RecordDAO de jdbc
 * @param logger para generar las trazas
 */
public class RecordDAOJDBCImplementation implements RecordDAO{
    private final Object lockOfConnection = new Object();
    private Connection connection = null;
    private static RecordDAOJDBCImplementation recordPersistenceManager = null;
    private static final Logger logger = Logger.getLogger(RecordDAOJDBCImplementation.class.getName());
    
    //Visibilidad de paquete para usarlo con RecordDAOPoolImplementation
    RecordDAOJDBCImplementation() {
    }
    
    public static RecordDAO getRecordDAOJDBCImplementation() {
        if(recordPersistenceManager == null)
            recordPersistenceManager = new RecordDAOJDBCImplementation();
        
        return recordPersistenceManager;
    }
    
    @Override
    public boolean createRecord(Record record) {
        String query = "insert into RECORDS values(?,?,?,?,?,?,?,?)";
        PreparedStatement statement;
        try{
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,record.getIdAsString());
            statement.setString(2,record.getName());
            statement.setString(3,record.getArtist());
            statement.setString(4,record.getRecordLabel());
            statement.setString(5,record.getShortComment());
            statement.setString(6,record.getFullComment());
            statement.setString(7,record.getType());
            statement.setString(8,record.getPriceAsString());
            statement.execute();
            logger.trace("Insertado el disco en BD: "+record.getIdAsString());
            return true;
        } catch (SQLException ex) {
            logger.error("Error al crear el disco", ex);
            return false;
        }
    }

    @Override
    public Record readRecord(String id) {
        String query = "select * from RECORDS where ID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Record record = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                record = new Record(id);
                record.setName(resultSet.getString("NAME"));
                record.setArtist(resultSet.getString("ARTIST"));
                record.setRecordLabel(resultSet.getString("RECORDLABEL"));
                record.setShortComment(resultSet.getString("SHORTCOMMENT"));
                record.setFullComment(resultSet.getString("FULLCOMMENT"));
                record.setType(resultSet.getString("TYPE"));
                record.setPrice(resultSet.getString("PRICE"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un disco", ex);
            record = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return record;
    }

    @Override
    public ArrayList<Record> listRecord(String name, String artist,
            String recordLabel, String type) {
        ArrayList<Record> list = new ArrayList();
        String query = "select * from RECORDS";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Record record = null;
        int namePosition=1, artistPosition=2, recordLabelPosition=3, typePosition=4;
        boolean isWhereWritten = false;
        
        try {
            if (name != null) {
                query = query.concat(" where");
                isWhereWritten = true;
                query = query.concat(" NAME =?");
            } else {
                namePosition = 0;
                artistPosition--;
                recordLabelPosition--;
                typePosition--;
            }
            if (artist != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" ARTIST =?");
            } else {
                artistPosition = 0;
                recordLabelPosition--;
                typePosition--;
            }
            if (recordLabel != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" RECORDLABEL =?");
            } else {
                recordLabelPosition = 0;
                typePosition--;
            }
            if(type != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" TYPE =?");
            } else {
                typePosition = 0;
            }

            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            if (namePosition != 0) {
                statement.setString(namePosition, name);
            }
            if (artistPosition != 0) {
                statement.setString(artistPosition, artist);
            }
            if (recordLabelPosition != 0) {
                statement.setString(recordLabelPosition, recordLabel);
            }
            if(typePosition != 0) {
                statement.setString(typePosition, type);
            } 
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                record = new Record(resultSet.getString("ID"));
                record.setName(resultSet.getString("NAME"));
                record.setArtist(resultSet.getString("ARTIST"));
                record.setRecordLabel(resultSet.getString("RECORDLABEL"));
                record.setShortComment(resultSet.getString("SHORTCOMMENT"));
                record.setFullComment(resultSet.getString("FULLCOMMENT"));
                record.setType(resultSet.getString("TYPE"));
                record.setPrice(resultSet.getString("PRICE"));
                list.add(record);
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un disco", ex);
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
    public boolean updateRecord(String id, Record record) {
        String query = null;
        query = "update RECORDS SET NAME =?,ARTIST = ?,RECORDLABEL = ?,"
                + "SHORTCOMMENT = ?, FULLCOMMENT = ?, TYPE = ?, PRICE = ? where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, record.getName());
            statement.setString(2, record.getArtist());
            statement.setString(3, record.getRecordLabel());
            statement.setString(4, record.getShortComment());
            statement.setString(5, record.getFullComment());
            statement.setString(6, record.getType());
            statement.setString(7, record.getPriceAsString());
            statement.setString(8, id);
            statement.execute();
            logger.trace("Actualizado el disco en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al actualizar un disco", ex);
            return false;
        }
    }

    @Override
    public boolean deleteRecord(String id) {
        String query = "delete from RECORDS where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            statement.execute();
            logger.trace("Borrado el disco en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al borrar un disco", ex);
            return false;
        }
    }
    
    @Override
    public Map<UUID,Record> getRecordMap() {
        HashMap<UUID,Record> recordMap = new HashMap();
        String query = "select * from RECORDS";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Record record = null;
        
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            } 
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String recordId = resultSet.getString("ID");
                record = new Record(recordId);
                record.setName(resultSet.getString("NAME"));
                record.setArtist(resultSet.getString("ARTIST"));
                record.setRecordLabel(resultSet.getString("RECORDLABEL"));
                record.setShortComment(resultSet.getString("SHORTCOMMENT"));
                record.setFullComment(resultSet.getString("FULLCOMMENT"));
                record.setType(resultSet.getString("TYPE"));
                record.setPrice(resultSet.getString("PRICE"));
                recordMap.put(UUID.fromString(recordId), record);
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar un disco", ex);
            recordMap.clear();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }        
        return recordMap;
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
     * Para establecer conexiones en el RecordDAOPoolImplementation mediante esta clase
     * @param connection
     */
    public void setConnection(Connection connection) {
        synchronized (lockOfConnection) {
            this.connection = connection;
        }
    }

}