package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * Producto de la tienda: Disco de musica
 * 
 * @param id identificador del disco
 * @param name nombre del disco
 * @param artist artista del disco
 * @param recordLabel distribuidora
 * @param shortComment descripcion corta
 * @param fullComment descripcion larga
 * @param type estilo musical
 * @param price precio del disco
 * @param creationDate fecha de creacion
 */
public class Record implements Serializable {
    private UUID id = null;
    private String name = null;
    private String artist = null;
    private String recordLabel = null;
    private String shortComment = null;
    private String fullComment = null;
    private String type = null;
    private BigDecimal price = null;
    
    public Record() {
        this.id = UUID.randomUUID();
    }
    
    public Record(String id){
        this.id = UUID.fromString(id);
    }
    
    public Record(UUID id){
        this.id = id;
    }
    
    /**
     * Constructor de un nuevo disco
     * @param name nombre del disco
     * @param artist artista del disco
     * @param recordLabel discografica
     * @param shortComment comentario corto
     * @param fullComment comentario largo
     * @param type tipo de musica
     * @param price precio del disco
     */
    public Record(String name, String artist, String recordLabel, String shortComment,
            String fullComment, String type, BigDecimal price){
        this();
        this.name = name;
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.shortComment = shortComment;
        this.fullComment = fullComment;
        this.type = type;
        this.price = price;        
    }
    
    /**
     * Constructor para recrear discos ya existentes
     * @param id identificador del disco original
     * @param name nombre del disco
     * @param artist artista del disco
     * @param recordLabel discografica
     * @param shortComment comentario corto
     * @param fullComment comentario largo
     * @param type tipo de musica
     * @param price precio del disco
     */
    public Record(String id, String name, String artist, String recordLabel, String shortComment,
            String fullComment, String type, BigDecimal price){
        this(id);
        this.name = name;
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.shortComment = shortComment;
        this.fullComment = fullComment;
        this.type = type;
        this.price = price;        
    }
    
    @Override
    public boolean equals(Object object) {
        if(object instanceof Record) {
            Record record = (Record) object;
            return (record != null) && (this.getId().equals(record.getId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public UUID getId() {
        return id;
    }
    
    public String getIdAsString() {
        return id.toString();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRecordLabel() {
        return recordLabel;
    }

    public void setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public String getFullComment() {
        return fullComment;
    }

    public void setFullComment(String fullComment) {
        this.fullComment = fullComment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public String getPriceAsString() {
        return price.toString();
    }
    
    /**
     * @return string con el precio formateado correctamente,  añadiendo unas
     *         separaciones en los miles, millones... y añadiendo EUR
     */
    public String getPriceAsFormattedString() {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.FRANCE); 
        double doublePrice = price.doubleValue();
        return n.format(doublePrice);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

}