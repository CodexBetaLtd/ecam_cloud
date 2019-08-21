package com.codex.ecam.model.inventory.receiptOrder;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_receipt_order_item_serial_number")
public class ReceiptOrderItemSerialNumber extends BaseModel {

    private static final long serialVersionUID = 321321321321321321L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "tbl_receipt_order_item_serial_number_s")
    @SequenceGenerator(name = "tbl_receipt_order_item_serial_number_s", sequenceName = "tbl_receipt_order_item_serial_number_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "receipt_order_item_id")
    @ManyToOne(targetEntity = ReceiptOrderItem.class, fetch = FetchType.LAZY)
    private ReceiptOrderItem receiptOrderItem;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "active")
    private Boolean active;


    /*==========================*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReceiptOrderItem getReceiptOrderItem() {
        return receiptOrderItem;
    }

    public void setReceiptOrderItem(ReceiptOrderItem receiptOrderItem) {
        this.receiptOrderItem = receiptOrderItem;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
