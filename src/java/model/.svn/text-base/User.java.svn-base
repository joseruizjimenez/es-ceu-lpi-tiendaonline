package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Un usuario es un posible cliente de la tienda.
 * Registrarse como tal es opcional.
 * 
 * @param id identificacion del usuario
 * @param nick del usuario
 * @param password clave de acceso
 * @param fistName nombre
 * @param lastName apellidos
 * @param address direccion
 * @param email direccion de correo electronico
 * @param birthDate fecha de nacimiento
 */
public class User implements Serializable {
    private UUID id = null;
    private String nick = null;
    private String password = null;
    private String firstName = null;
    private String lastName = null;
    private String address = null;
    private String email = null;
    private Calendar birthDate = null;
    
    public User(){
        this.id = UUID.randomUUID();
        this.birthDate = Calendar.getInstance();
    }
    
    public User(String id){
        this.id = UUID.fromString(id);
        this.birthDate = Calendar.getInstance();
    }
    
    public User(UUID id){
        this.id = id;
        this.birthDate = Calendar.getInstance();
    }
      
    /**
     * Constructor para reconstruir un usuario ya existente
     * @param id del usuario original
     * @param nick del usuario
     * @param password clave de acceso
     * @param firstName nombre del usuario
     * @param lastName apellidos del usuario
     * @param address direccion del usuario
     * @param email email del usuario
     * @param year anho de nacimiento
     * @param month mes de nacimiento
     * @param date dia de nacimiento
     */
    public User(String id, String nick, String password, String firstName,
            String lastName, String address, String email, int year, int month, int day){
        this(id);
        this.nick = nick;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.birthDate = Calendar.getInstance();
        this.birthDate.clear();
        this.birthDate.set(year, month-1, day);        
    }
    
    /**
     * Constructor de un nuevo usuario
     * @param nick del usuario
     * @param password clave de acceso
     * @param firstName nombre del usuario
     * @param lastName apellidos del usuario
     * @param address direccion del usuario
     * @param email email del usuario
     * @param year anho de nacimiento
     * @param month mes de nacimiento
     * @param date dia de nacimiento
     */
    public User(String nick, String password, String firstName, String lastName,
            String address, String email, int year, int month, int day){
        this(UUID.randomUUID().toString(),nick,password,firstName,lastName,
                address,email,year,month,day);       
    }
    
    @Override
    public boolean equals(Object object) {
        if(object instanceof User) {
            User us = (User) object;
            return (us != null) && (this.getId().equals(us.getId()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }
    
    public String getIdAsString() {
        return id.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }
    
    public long getBirthDateInMillis() {
        return birthDate.getTimeInMillis();
    }
    
    public String getBirthDateAsString() {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(birthDate.getTime());
    }
   
    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setBirthDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.birthDate.setTime(dateFormat.parse(date));
    }
    
    public void setBirthDateFromLong(long birthDate) {
        this.birthDate.clear();
        this.birthDate.setTimeInMillis(birthDate);
    }
    
    public void setBirthDate(int year, int month, int day) {
        this.birthDate.clear();
        this.birthDate.set(year, month-1, day);
    }
    
    public String getPaymentForm() {
        return "Contra reembolso";
    }
}