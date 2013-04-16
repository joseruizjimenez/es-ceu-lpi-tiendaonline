package persistence.comment;

/**
 * Genera una factoria de comentarios siguiendo el patron DAO: CommentsDAO
 */
public class CommentPersistFactory {
    
    /**
     * Obten un sistema de persistencia del tipo declarado en persistenceMechanism
     * @param persistenceMechanism puede ser file, jdbc o pool
     * @return CommentDAO si todo va bien, sino null
     */
    public static CommentDAO getCommentDAO(String persistenceMechanism){
        if (persistenceMechanism.equals("file")){
            return CommentDAOFileImplementation.getCommentDAOFileImplementation();
        }
        else if(persistenceMechanism.equals("jdbc")){
            return CommentDAOJDBCImplementation.getCommentDAOJDBCImplementation();
        }
        else if (persistenceMechanism.equals("pool")) {
            return CommentDAOPoolImplementation.getCommentDAOPoolImplementation();
        }
        else {
            return null;
        }
    }
}
