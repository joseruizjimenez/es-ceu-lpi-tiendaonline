package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Carro de la compra. Contiene un mapa de discos y cantidades
 * 
 * @param footprint firma correspondiente al contenido del carro
 * @param cart mapa de discos y cantidades
 */
public class Cart implements Serializable {
    private String footprint = null;
    private HashMap<UUID,Integer> cart = null;
    
    /**
     * Genera un carro de la compra vacio
     */
    public Cart() {
        footprint = "";
        cart = new HashMap<UUID,Integer>();
    }
    
    /**
     * Genera un carro de la compra a partir de un mapa que contiene ids de
     * productos y sus cantidades
     * @param cart 
     */
    public Cart(HashMap<UUID,Integer> cart) {
        this.cart = new HashMap<UUID,Integer>(cart);
        this.setFootprint();
    }
    
    /**
     * Genera un carro de la compra a partir de una firma que contiene ids de 
     * productos y sus cantidads
     * @param footprint firma del carro
     */
    public Cart(String footprint) {
        this();
        if(!footprint.equals("")) {
            String[] pieces = footprint.split("\\(");
            int i = 0;
            boolean hasNext = true;
            String recordId = pieces[i];
            String nextRecordId = "";
            String recordQuantity;
            do {
                String recordTail = pieces[i+1];
                if(i+1 == pieces.length-1) {
                    hasNext = false;
                    recordQuantity = recordTail.split("\\)")[0];
                } else{
                    String[] subpieces = recordTail.split("\\)");
                    recordQuantity = subpieces[0];
                    nextRecordId = subpieces[1];
                    i++;
                }
                this.cart.put(UUID.fromString(recordId),Integer.parseInt(recordQuantity));
                recordId = nextRecordId;
            } while (hasNext);
            this.footprint = footprint;
        }
    }
    
    /**
     * Devuelve el numero total del disco dado
     * @param id identificador del disco
     * @return cantidad de ese disco
     */
    public String getQuantity(UUID recordId) {
        if(!this.cart.isEmpty() && this.cart.containsKey(recordId)) {
            return String.valueOf(this.cart.get(recordId));
        }
        return "0";
    }
    
    /**
     * Devuelve el numero total de elementos que contiene el carro
     * @return productos que contiene el carro
     */
    public String getNumberOfItems() {
        if(!this.cart.isEmpty()) {
            int quantity = 0;
            for(UUID id : this.cart.keySet()) {
                quantity = quantity + this.cart.get(id);
            }
            return String.valueOf(quantity);
        }
        return "0";
    }
    
    /**
     * Devuelve la firma del contenido del carro: articulos y cantidades
     * @return footprint formada concatenando los IDs del articulo (36 caracteres)
     *         seguido de la cantidad entre parentesis. ej: id(n)id(n)id(n)
     */
    public String getFootprint() {
        return footprint;
    }

    /**
     * Genera la firma del contenido del carro: articulos y cantidades,
     * formada concatenando los IDs del articulo (36 caracteres)
     * seguido de la cantidad entre parentesis. ej: id(n)id(n)id(n)
     */
    private void setFootprint() {
        StringBuilder footprintBuilder = new StringBuilder("");
        if(!this.cart.isEmpty()) {
            for(UUID id : this.cart.keySet()){
                footprintBuilder.append(id.toString());
                footprintBuilder.append("(");
                footprintBuilder.append(this.cart.get(id));
                footprintBuilder.append(")");
            }
        }
        this.footprint = footprintBuilder.toString();
    }

    public HashMap<UUID,Integer> getCart() {
        return new HashMap<UUID,Integer>(cart);
    }

    public void setCart(HashMap<UUID,Integer> cart) {
        this.cart = new HashMap<UUID,Integer>(cart);
        this.setFootprint();
    }
    
    public boolean isEmpty() {
        return cart.isEmpty();
    }
    
    /**
     * Agrega una determinada cantidad de un disco al carro.
     * Si este ya existia en el carro, incrementara su cantidad,
     * eliminando el producto si esta resulta 0 o negativa
     * @param recordId disco
     * @param quantity cantidad a incrementar
     */
    public void addRecord(UUID recordId,Integer quantity) {
        if(cart.containsKey(recordId)) {
            int oldQuantity = cart.get(recordId);
            quantity = quantity + oldQuantity;
        }
        if(quantity <= 0) {
            cart.remove(recordId);
        } else {
            cart.put(recordId, quantity);
        }
        this.setFootprint();
    }
    
    /**
     * Agrega una unidad de un disco al carro.
     * Si este ya existia en el carro, incrementara su cantidad
     * @param recordId disco
     * @param quantity cantidad a incrementar
     */
    public void addRecord(UUID recordId) {
        this.addRecord(recordId,1);
    }
    
    /**
     * Elimina una determinada cantidad de un disco al carro.
     * Si este ya existia en el carro, decrementara su cantidad
     * @param recordId disco
     * @param quantity cantidad a decrementar
     */
    public void removeRecord(UUID recordId, Integer quantity) {
        this.addRecord(recordId,(quantity*-1));            
    }
    
    /**
     * Elimina una unidad de un disco al carro.
     * Si este ya existia en el carro, decrementara su cantidad
     * @param recordId disco
     * @param quantity cantidad a decrementar
     */
    public void removeRecord(UUID recordId) {
        this.removeRecord(recordId,1);        
    }
    
    /**
     * Elimina el disco del carro, todas sus cantidades
     * @param id disco a eliminar
     */
    public void deleteRecord(UUID recordId) {
        cart.remove(recordId);
    }
    
    /**
     * Elimina todo el contenido del carro
     */
    public void clear() {
        cart.clear();
    }

}