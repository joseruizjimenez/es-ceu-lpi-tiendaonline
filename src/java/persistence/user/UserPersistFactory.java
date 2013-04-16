package persistence.user;

/**
 * Genera una factoria de usuarios siguiendo el patron DAO: UserDAO
 */
public class UserPersistFactory {
    
    /**
     * Obten un sistema de persistencia del tipo declarado en persistenceMechanism
     * @param persistenceMechanism puede ser file, jdbc o pool
     * @return UserDAO si todo va bien, sino null
     */
    public static UserDAO getUserDAO(String persistenceMechanism){
        if (persistenceMechanism.equals("file")){
            return UserDAOFileImplementation.getUserDAOFileImplementation();
        }
        else if(persistenceMechanism.equals("jdbc")){
            return UserDAOJDBCImplementation.getUserDAOJDBCImplementation();
        }
        else if (persistenceMechanism.equals("pool")) {
            return UserDAOPoolImplementation.getUserDAOPoolImplementation();
        }
        else {
            return null;
        }
    }
}
