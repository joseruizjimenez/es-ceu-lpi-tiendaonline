package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Venta de uno o varios productos a un cliente
 * 
 * @param id identificador de la venta
 * @param customerId identificador del cliente
 * @param customerName nombre del cliente
 * @param address direccion de envio
 * @param paymentForm forma de pago: Transaction, CreditCard, CashOnDelivery
 * @param transactionDate fecha de venta
 * @param total precio total de la venta
 * @param items productos y sus cantidades compradas
 */
public class Sale implements Serializable {
    private UUID id = null;
    private UUID customerId = null;
    private String customerName = null;
    private String address = null;
    private String paymentForm = null;
    private Calendar transactionDate = null;
    private BigDecimal total = null;
    private Cart items = null;
    
    public Sale() {
        this.id = UUID.randomUUID();
        this.transactionDate = Calendar.getInstance();
        this.transactionDate.clear();
        this.transactionDate.setTimeInMillis(System.currentTimeMillis());
    }
    
    public Sale(String id){
        this.id = UUID.fromString(id);
        this.transactionDate = Calendar.getInstance();
    }
    
    public Sale(UUID id){
        this.id = id;
        this.transactionDate = Calendar.getInstance();
    }
    
    /**
     * Constructor de una venta nueva
     * @param customerId Id del cliente
     * @param customerName nombre del cliente
     * @param address direccion de entrega
     * @param paymentForm forma de pago
     * @param total precio total de la venta
     * @param cart productos adquiridos
     */
    public Sale(String customerId, String customerName, String address,
            String paymentForm, BigDecimal total, Cart cart){
        this();
        this.customerId = UUID.fromString(customerId);
        this.customerName = customerName;
        this.address = address;
        this.paymentForm = paymentForm;
        this.total = total;
        this.items = new Cart(cart.getCart());       
    }
    
    /**
     * Constructor para recrear ventas ya existentes
     * @param id id de la transaccion
     * @param customerId id del cliente
     * @param customerName nombre del cliente
     * @param address direccion de envio
     * @param paymentForm forma de pago
     * @param total precio total
     * @param transactionDate fecha de la transaccion
     * @param cart productos adquiridos
     * @throws ParseException error parseando transactionDate
     */
    public Sale(String id, String customerId, String customerName, String address,
            String paymentForm, BigDecimal total, String transactionDate, Cart cart)
            throws ParseException{
        this(id);
        this.customerId = UUID.fromString(customerId);
        this.customerName = customerName;
        this.address = address;
        this.paymentForm = paymentForm;
        this.total = total;
        this.transactionDate.clear();
        DateFormat date = DateFormat.getDateTimeInstance();
        this.transactionDate.setTime(date.parse(transactionDate)); 
        this.items = new Cart(cart.getCart());
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

    public UUID getCustomerId() {
        return customerId;
    }
    
    public String getCustomerIdAsString() {
        return customerId.toString();
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(String paymentForm) {
        this.paymentForm = paymentForm;
    }

    public Calendar getTransactionDate() {
        return transactionDate;
    }
    
    public long getTransactionDateInMillis() {
        return transactionDate.getTimeInMillis();
    }
    
    public String getTransactionDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(transactionDate.getTime());
    }

    public void setTransactionDate(Calendar transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public void setTransactionDate(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.transactionDate.setTime(dateFormat.parse(timestamp));
    }

    public BigDecimal getTotal() {
        return total;
    }
    
    /**
     * @return string con el precio total sin formatear
     */
    public String getTotalAsString() {
        return total.toString();
    }
    
    /**
     * @return string con el precio total formateado correctamente, 
     * añadiendo una separaciones en los miles, millones... y añadiendo EUR
     */
    public String getTotalAsFormattedString() {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.FRANCE); 
        double doublePrice = getTotal().doubleValue();
        return n.format(doublePrice);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cart getItems() {
        return items;
    }

    public void setItems(Cart items) {
        this.items = new Cart(items.getCart());
    }
    
    public void setItems(String cartFootprint) {
        this.items = new Cart(cartFootprint);
    }
}
    