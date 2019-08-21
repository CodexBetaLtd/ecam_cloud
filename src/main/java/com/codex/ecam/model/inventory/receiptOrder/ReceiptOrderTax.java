package com.codex.ecam.model.inventory.receiptOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.biz.tax.Tax;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_receipt_order_tax")
public class ReceiptOrderTax extends BaseModel {

    private static final long serialVersionUID = 123321123321123321L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "receipt_order_tax_s")
    @SequenceGenerator(name = "receipt_order_tax_s", sequenceName = "receipt_order_tax_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "receipt_order_id")
    @ManyToOne(targetEntity = ReceiptOrder.class, fetch = FetchType.LAZY)
    private ReceiptOrder receiptOrder;

    @JoinColumn(name = "tax_id")
    @ManyToOne(targetEntity = Tax.class, fetch = FetchType.LAZY)
    private Tax tax;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;


    /*==========================*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReceiptOrder getReceiptOrder() {
        return receiptOrder;
    }

    public void setReceiptOrder(ReceiptOrder receiptOrder) {
        this.receiptOrder = receiptOrder;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
}
