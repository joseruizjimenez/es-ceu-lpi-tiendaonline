package model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Stock (Existencias disponibles) de un producto que se vende en al tienda
 * 
 * @param recordId id del disco asociado al stock
 * @param stock existencias disponibles del producto
 */
public class Stock implements Serializable {
    private UUID recordId = null;
    private int stock = 0;
    
    public Stock() {
    }
    
    public Stock(UUID recordId, int stock) {
        this.recordId = recordId;
        this.stock = stock;
    }
    
    public Stock(String recordId, int stock) {
        this(UUID.fromString(recordId),stock);
    }

    public UUID getRecordId() {
        return recordId;
    }
    
    public String getRecordIdAsString() {
        return recordId.toString();
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
