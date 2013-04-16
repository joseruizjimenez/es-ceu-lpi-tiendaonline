package model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Commentarios de un usuario sobre un producto
 * 
 * @param recordId ID del disco que se comenta
 * @param nickname nombre del usuario que creo el comentario
 * @param comment comentario
 */
public class Comment implements Serializable {
    private UUID id = null;
    private UUID recordId = null;
    private String nickname = null;
    private String comment = null;
    
    public Comment() {
        this.id = UUID.randomUUID();
    }
    
    public Comment(UUID id) {
        this.id = id;
    }
    
    public Comment(String id) {
        this(UUID.fromString(id));
    }
    
    /**
     * Constructor de un nuevo comentario
     * @param recordId UUID del disco que comenta
     * @param nickname nombre del creador
     * @param comment comentario
     */
    public Comment(UUID recordId, String nickname, String comment) {
        this();
        this.recordId = recordId;
        this.nickname = nickname;
        this.comment = comment;
    }
    
    /**
     * Constructor de un nuevo comentario
     * @param recordId Identificador del disco que comenta
     * @param nickname nombre del creador
     * @param comment comentario
     */
    public Comment(String recordId, String nickname, String comment) {
        this(UUID.fromString(recordId),nickname,comment);
    }
    
    /**
     * Constructor para recrear comentarios ya existentes
     * @param id identificador del comentario original
     * @param recordId Identificador del disco que comenta
     * @param nickname nombre del creador
     * @param comment comentario
     */
    public Comment(String id, String recordId, String nickname, String comment) {
        this(id);
        this.recordId = UUID.fromString(recordId);
        this.nickname = nickname;
        this.comment = comment;
    }
    
    @Override
    public boolean equals(Object object) {
        if(object instanceof Comment) {
            Comment com = (Comment) object;
            return (com != null) && (this.getId().equals(com.getId()));
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

    public UUID getRecordId() {
        return recordId;
    }
    
    public String getRecordIdAsString() {
        return recordId.toString();
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
