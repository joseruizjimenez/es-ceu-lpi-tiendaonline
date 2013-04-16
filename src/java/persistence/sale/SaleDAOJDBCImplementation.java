package persistence.sale;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;
import model.Sale;
import org.apache.log4j.Logger;

/**
 * Implementacion de SaleDAO para persistir la informacion con JDBC en MySQL
 * 
 * @param lockOfConnection objeto para controlar los accesos no concurrentes
 * @param connection conexion con la base de datos
 * @param salePersistenceManager SaleDAO de jdbc
 * @param logger para generar las trazas
 */
public class SaleDAOJDBCImplementation implements SaleDAO{
    private final Object lockOfConnection = new Object();
    private Connection connection = null;
    private static SaleDAOJDBCImplementation salePersistenceManager = null;
    private static final Logger logger = Logger.getLogger(SaleDAOJDBCImplementation.class.getName());
    
    //Visibilidad de paquete para usarlo con SaleDAOPoolImplementation
    SaleDAOJDBCImplementation() {
    }
    
    public static SaleDAO getSaleDAOJDBCImplementation() {
        if(salePersistenceManager == null)
            salePersistenceManager = new SaleDAOJDBCImplementation();
        
        return salePersistenceManager;
    }
    
    @Override
    public boolean createSale(Sale sale) {
        String query = "insert into SALES values(?,?,?,?,?,?,?,?)";
        PreparedStatement statement;
        try{
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,sale.getIdAsString());
            statement.setString(2,sale.getCustomerIdAsString());
            statement.setString(3,sale.getCustomerName());
            statement.setString(4,sale.getAddress());
            statement.setString(5,sale.getPaymentForm());
            Timestamp timestamp = new Timestamp(sale.getTransactionDateInMillis());
            statement.setTimestamp(6,timestamp);
            statement.setString(7,sale.getTotalAsString());
            statement.setString(8,sale.getItems().getFootprint());
            statement.execute();
            logger.trace("Insertada la factura en BD: "+sale.getIdAsString());
            return true;
        } catch (SQLException ex) {
            logger.error("Error al crear la factura", ex);
            return false;
        }
    }

    @Override
    public Sale readSale(String id) {
        String query = "select * from SALES where ID =?";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Sale sale = null;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sale = new Sale(id);
                sale.setCustomerId(UUID.fromString(resultSet.getString("CUSTOMERID")));
                sale.setCustomerName(resultSet.getString("CUSTOMERNAME"));
                sale.setAddress(resultSet.getString("ADDRESS"));
                sale.setPaymentForm(resultSet.getString("PAYMENTFORM"));
                sale.setTransactionDate(resultSet.getTimestamp("TRANSACTIONDATE").toString());
                sale.setTotal(new BigDecimal(resultSet.getString("TOTAL")));
                sale.setItems(resultSet.getString("ITEMS"));
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar la factura", ex);
            sale = null;
        } catch (ParseException ex) {
            logger.warn("Error parseando transactionDate",ex);
            sale = null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    logger.error("Error al cerrar la conexon a la base de datos", ex);
                }
            }
        }
        return sale;
    }

    @Override
    public ArrayList<Sale> listSale(String customerId, String customerName,
            String address, String paymentForm, String cartFootprint, String recordId) {
        ArrayList<Sale> list = new ArrayList();
        String query = "select * from SALES";
        PreparedStatement statement;
        ResultSet resultSet = null;
        Sale sale = null;
        int customerIdPosition=1, customerNamePosition=2, addressPosition=3, paymentFormPosition=4,
                cartFootprintPosition=5;
        boolean isWhereWritten = false;
        
        try {
            if (customerId != null) {
                query = query.concat(" where");
                isWhereWritten = true;
                query = query.concat(" CUSTOMERID =?");
            } else {
                customerIdPosition = 0;
                customerNamePosition--;
                addressPosition--;
                paymentFormPosition--;
                cartFootprintPosition--;
            }
            if (customerName != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" CUSTOMERNAME =?");
            } else {
                customerNamePosition = 0;
                addressPosition--;
                paymentFormPosition--;
                cartFootprintPosition--;
            }
            if (address != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" ADDRESS =?");
            } else {
                addressPosition = 0;
                paymentFormPosition--;
                cartFootprintPosition--;
            }
            if(paymentForm != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" PAYMENTFORM =?");
            } else {
                paymentFormPosition = 0;
                cartFootprintPosition--;
            }
            if(cartFootprint != null) {
                if (!isWhereWritten) {
                    query = query.concat(" where");
                    isWhereWritten = true;
                } else {
                    query = query.concat(" AND");
                }
                query = query.concat(" ITEMS =?");
            } else {
                cartFootprintPosition = 0;
            }

            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            if (customerIdPosition != 0) {
                statement.setString(customerIdPosition, customerId);
            }
            if (customerNamePosition != 0) {
                statement.setString(customerNamePosition, customerName);
            }
            if (addressPosition != 0) {
                statement.setString(addressPosition, address);
            }
            if(paymentFormPosition != 0) {
                statement.setString(paymentFormPosition, paymentForm);
            } 
            if(cartFootprintPosition != 0) {
                statement.setString(cartFootprintPosition, cartFootprint);
            }
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sale = new Sale(resultSet.getString("ID"));
                sale.setCustomerId(UUID.fromString(resultSet.getString("CUSTOMERID")));
                sale.setCustomerName(resultSet.getString("CUSTOMERNAME"));
                sale.setAddress(resultSet.getString("ADDRESS"));
                sale.setPaymentForm(resultSet.getString("PAYMENTFORM"));
                sale.setTransactionDate(resultSet.getTimestamp("TRANSACTIONDATE").toString());
                sale.setTotal(new BigDecimal(resultSet.getString("TOTAL")));
                sale.setItems(resultSet.getString("ITEMS"));
                if(recordId == null || resultSet.getString("ITEMS").contains(recordId)) {
                    list.add(sale);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error al recuperar una factura", ex);
            list.clear();
        } catch (ParseException ex) {
            logger.warn("Error parseando transactionDate",ex);
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
    public boolean updateSale(String id, Sale sale) {
        String query = null;
        query = "update SALES SET CUSTOMERID =?,CUSTOMERNAME = ?,ADDRESS = ?,"
                + "PAYMENTFORM = ?, TRANSACTIONDATE = ?, TOTAL = ?, ITEMS = ? where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1,sale.getCustomerIdAsString());
            statement.setString(2,sale.getCustomerName());
            statement.setString(3,sale.getAddress());
            statement.setString(4,sale.getPaymentForm());
            Timestamp timestamp = new Timestamp(sale.getTransactionDateInMillis());
            statement.setTimestamp(5,timestamp);
            statement.setString(6,sale.getTotalAsString());
            statement.setString(7,sale.getItems().getFootprint());
            statement.setString(8, id);
            statement.execute();
            logger.trace("Actualizada la factura en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al actualizar una factura", ex);
            return false;
        }
    }

    @Override
    public boolean deleteSale(String id) {
        String query = "delete from SALES where ID = ?";
        PreparedStatement statement;
        try {
            synchronized (lockOfConnection) {
                statement = connection.prepareStatement(query);
            }
            statement.setString(1, id);
            statement.execute();
            logger.trace("Borrada la factura en BD: "+id);
            return true;
        } catch (SQLException ex) {
            logger.error("Error al borrar una factura", ex);
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
     * Para establecer conexiones en el SaleDAOPoolImplementation mediante esta clase
     * @param connection
     */
    public void setConnection(Connection connection) {
        synchronized (lockOfConnection) {
            this.connection = connection;
        }
    }

}