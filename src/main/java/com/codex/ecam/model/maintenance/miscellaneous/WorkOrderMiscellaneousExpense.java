package com.codex.ecam.model.maintenance.miscellaneous;

import javax.persistence.*;

import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

import java.math.BigDecimal;

/**
 */
@Entity
@Table(name = "tbl_wo_miscellaneous_expense")
public class WorkOrderMiscellaneousExpense extends BaseModel {

    private static final long serialVersionUID = 99856993851212085L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "wo_miscellaneous_expense_s")
    @SequenceGenerator(name = "wo_miscellaneous_expense_s", sequenceName = "wo_miscellaneous_expense_s", allocationSize = 1)
    @Column(name="id")
    private Integer id;

    @JoinColumn(name="work_order_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
    private WorkOrder workOrder;

    @JoinColumn(name="miscellaneous_expense_type_id")
    @ManyToOne(targetEntity = MiscellaneousExpenseType.class, fetch = FetchType.LAZY)
    private MiscellaneousExpenseType miscellaneousExpenseType;

    @Column(name="estimated_unit_cost")
    private BigDecimal estimatedUnitCost;

    @Column(name="estimated_quantity")
    private BigDecimal estimatedQuantity;

    @Column(name="estimated_total_cost")
    private BigDecimal estimatedTotalCost;

    @Column(name="actual_unit_cost")
    private BigDecimal actualUnitCost;

    @Column(name="actual_quantity")
    private BigDecimal actualQuantityCost;

    @Column(name="actual_total_cost")
    private BigDecimal actualTotalCost;

    @Column(name="description")
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public MiscellaneousExpenseType getMiscellaneousExpenseType() {
        return miscellaneousExpenseType;
    }

    public void setMiscellaneousExpenseType(MiscellaneousExpenseType miscellaneousExpenseType) {
        this.miscellaneousExpenseType = miscellaneousExpenseType;
    }

    public BigDecimal getEstimatedUnitCost() {
        return estimatedUnitCost;
    }

    public void setEstimatedUnitCost(BigDecimal estimatedUnitCost) {
        this.estimatedUnitCost = estimatedUnitCost;
    }

    public BigDecimal getEstimatedQuantity() {
        return estimatedQuantity;
    }

    public void setEstimatedQuantity(BigDecimal estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
    }

    public BigDecimal getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(BigDecimal estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public BigDecimal getActualUnitCost() {
        return actualUnitCost;
    }

    public void setActualUnitCost(BigDecimal actualUnitCost) {
        this.actualUnitCost = actualUnitCost;
    }

    public BigDecimal getActualQuantityCost() {
        return actualQuantityCost;
    }

    public void setActualQuantityCost(BigDecimal actualQuantityCost) {
        this.actualQuantityCost = actualQuantityCost;
    }

    public BigDecimal getActualTotalCost() {
        return actualTotalCost;
    }

    public void setActualTotalCost(BigDecimal actualTotalCost) {
        this.actualTotalCost = actualTotalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
