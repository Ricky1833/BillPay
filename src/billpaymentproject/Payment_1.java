/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Ricky
 */
@Entity
@Table(name = "payment", catalog = "billpay", schema = "")
@NamedQueries({
    @NamedQuery(name = "Payment_1.findAll", query = "SELECT p FROM Payment_1 p")
    , @NamedQuery(name = "Payment_1.findBySerial", query = "SELECT p FROM Payment_1 p WHERE p.serial = :serial")
    , @NamedQuery(name = "Payment_1.findByDescription", query = "SELECT p FROM Payment_1 p WHERE p.description = :description")
    , @NamedQuery(name = "Payment_1.findByPaymentDate", query = "SELECT p FROM Payment_1 p WHERE p.paymentDate = :paymentDate")
    , @NamedQuery(name = "Payment_1.findByStatus", query = "SELECT p FROM Payment_1 p WHERE p.status = :status")
    , @NamedQuery(name = "Payment_1.findByAmount", query = "SELECT p FROM Payment_1 p WHERE p.amount = :amount")
    , @NamedQuery(name = "Payment_1.findByPayeeSerial", query = "SELECT p FROM Payment_1 p WHERE p.payeeSerial = :payeeSerial")})
public class Payment_1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serial")
    private Integer serial;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Column(name = "status")
    private Integer status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "payee_serial")
    private int payeeSerial;

    public Payment_1() {
    }

    public Payment_1(Integer serial) {
        this.serial = serial;
    }

    public Payment_1(Integer serial, Date paymentDate, int payeeSerial) {
        this.serial = serial;
        this.paymentDate = paymentDate;
        this.payeeSerial = payeeSerial;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        Integer oldSerial = this.serial;
        this.serial = serial;
        changeSupport.firePropertyChange("serial", oldSerial, serial);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        Date oldPaymentDate = this.paymentDate;
        this.paymentDate = paymentDate;
        changeSupport.firePropertyChange("paymentDate", oldPaymentDate, paymentDate);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        Integer oldStatus = this.status;
        this.status = status;
        changeSupport.firePropertyChange("status", oldStatus, status);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        BigDecimal oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    public int getPayeeSerial() {
        return payeeSerial;
    }

    public void setPayeeSerial(int payeeSerial) {
        int oldPayeeSerial = this.payeeSerial;
        this.payeeSerial = payeeSerial;
        changeSupport.firePropertyChange("payeeSerial", oldPayeeSerial, payeeSerial);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serial != null ? serial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment_1)) {
            return false;
        }
        Payment_1 other = (Payment_1) object;
        if ((this.serial == null && other.serial != null) || (this.serial != null && !this.serial.equals(other.serial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "billpaymentproject.Payment_1[ serial=" + serial + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
