package com.neolith.focus.model.sale.quotation;

import com.neolith.focus.constants.user.Salutation;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.admin.AssetBrand;
import com.neolith.focus.model.admin.Currency;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.model.maintenance.workorder.WorkOrder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_quotation")
public class Quotation extends BaseModel {

    private static final long serialVersionUID = 99662638695352589L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "estimate_s")
    @SequenceGenerator(name = "estimate_s", sequenceName = "estimate_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "estimate_ref")
    private String estimateRef;

    @Column(name = "estimate_date")
    private Date estimateDate;

    @Column(name = "revision_no")
    private String revisionNo;

    @JoinColumn(name = "estimate_by_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User estimateBy;

    @JoinColumn(name = "estimate_prepared_by_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User estimatePreparedBy;

    @JoinColumn(name = "inquiry_taken_by_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User inquiryTakenBy;

    @JoinColumn(name = "inquiry_handle_by_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User inquiryHandleBy;

    @Column(name = "response_time")
    private Double responseTime;

    @Column(name = "delivered_by")
    private String deliveredBy;

    @Column(name = "short_time_to_prepare")
    private String shortTimeToPrepare;

    @Column(name = "realized")
    private String realized;

    @Column(name = "overseas_payment_method")
    private String overseasPaymentMethod;

    @Column(name = "imported")
    private String imported;

    @Column(name = "commissioned")
    private String commissioned;

    @Column(name = "air_network_drawing")
    private String airNetworkDrawing;

    @Column(name = "equipment_layout_drawing")
    private String equipmentLayoutDrawing;

    @Column(name = "competitor")
    private String competitor;

    @Column(name = "validity")
    private Boolean validity;

    @Column(name = "chairman_decision")
    private String chairmanDecision;

    @Column(name = "factory_manager_decision")
    private String factoryManagerDecision;

    @Column(name = "factory_engineer_decision")
    private String factoryEngineerDecision;

    @JoinColumn(name = "sign_by_user_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User signBy;

    @JoinColumn(name = "currency_id")
    @ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
    private Currency currency;

    @JoinColumn(name = "wo_id")
    @ManyToOne(targetEntity = WorkOrder.class, fetch = FetchType.LAZY)
    private WorkOrder workOrder;

    @JoinColumn(name = "estimate_brand_id")
    @ManyToOne(targetEntity = AssetBrand.class, fetch = FetchType.LAZY)
    private AssetBrand brand;

    @Column(name = "estimate_header")
    private String estimateHeader;

    @Column(name = "estimate_body")
    private String estimateBody;

    @Column(name = "estimate_footer")
    private String estimateFooter;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_address_01")
    private String customerAddress01;

    @Column(name = "customer_address_02")
    private String customerAddress02;

    @Column(name = "customer_address_03")
    private String customerAddress03;

    @Column(name = "customer_address_04")
    private String customerAddress04;

    @Column(name = "customer_city")
    private String customerCity;

    @Column(name = "customer_postal_code")
    private String customerPostalCode;

    @Column(name = "customer_province")
    private String customerProvince;

    @Column(name = "customer_land_no")
    private String customerLandNo;

    @Column(name = "customer_attention_person")
    private String customerAttentionPerson;

    @Column(name = "customer_mobile_no")
    private String customerMobileNo;

    @Column(name = "salutation")
    private Salutation salutation;

    @Column(name = "wo_no")
    private String woNo;

    @Column(name = "wo_brand")
    private String woBrand;

    @Column(name = "wo_model_no")
    private String woModelNo;

    @Column(name = "wo_serial_no")
    private String woSerialNo;

    @Column(name = "wo_complain")
    private String woComplain;

    @Column(name = "wo_condition")
    private String woCondition;

    @Column(name = "tax_include")
    private Boolean taxInclude;

    @Column(name = "tax_breakdown")
    private Boolean taxBreakdown;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_breakdown")
    private Boolean discountBreakdown;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstimateRef() {
        return estimateRef;
    }

    public void setEstimateRef(String estimateRef) {
        this.estimateRef = estimateRef;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getRevisionNo() {
        return revisionNo;
    }

    public void setRevisionNo(String revisionNo) {
        this.revisionNo = revisionNo;
    }

    public User getEstimateBy() {
        return estimateBy;
    }

    public void setEstimateBy(User estimateBy) {
        this.estimateBy = estimateBy;
    }

    public User getEstimatePreparedBy() {
        return estimatePreparedBy;
    }

    public void setEstimatePreparedBy(User estimatePreparedBy) {
        this.estimatePreparedBy = estimatePreparedBy;
    }

    public User getInquiryTakenBy() {
        return inquiryTakenBy;
    }

    public void setInquiryTakenBy(User inquiryTakenBy) {
        this.inquiryTakenBy = inquiryTakenBy;
    }

    public User getInquiryHandleBy() {
        return inquiryHandleBy;
    }

    public void setInquiryHandleBy(User inquiryHandleBy) {
        this.inquiryHandleBy = inquiryHandleBy;
    }

    public Double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Double responseTime) {
        this.responseTime = responseTime;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getShortTimeToPrepare() {
        return shortTimeToPrepare;
    }

    public void setShortTimeToPrepare(String shortTimeToPrepare) {
        this.shortTimeToPrepare = shortTimeToPrepare;
    }

    public String getRealized() {
        return realized;
    }

    public void setRealized(String realized) {
        this.realized = realized;
    }

    public String getOverseasPaymentMethod() {
        return overseasPaymentMethod;
    }

    public void setOverseasPaymentMethod(String overseasPaymentMethod) {
        this.overseasPaymentMethod = overseasPaymentMethod;
    }

    public String getImported() {
        return imported;
    }

    public void setImported(String imported) {
        this.imported = imported;
    }

    public String getCommissioned() {
        return commissioned;
    }

    public void setCommissioned(String commissioned) {
        this.commissioned = commissioned;
    }

    public String getAirNetworkDrawing() {
        return airNetworkDrawing;
    }

    public void setAirNetworkDrawing(String airNetworkDrawing) {
        this.airNetworkDrawing = airNetworkDrawing;
    }

    public String getEquipmentLayoutDrawing() {
        return equipmentLayoutDrawing;
    }

    public void setEquipmentLayoutDrawing(String equipmentLayoutDrawing) {
        this.equipmentLayoutDrawing = equipmentLayoutDrawing;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    public String getChairmanDecision() {
        return chairmanDecision;
    }

    public void setChairmanDecision(String chairmanDecision) {
        this.chairmanDecision = chairmanDecision;
    }

    public String getFactoryManagerDecision() {
        return factoryManagerDecision;
    }

    public void setFactoryManagerDecision(String factoryManagerDecision) {
        this.factoryManagerDecision = factoryManagerDecision;
    }

    public String getFactoryEngineerDecision() {
        return factoryEngineerDecision;
    }

    public void setFactoryEngineerDecision(String factoryEngineerDecision) {
        this.factoryEngineerDecision = factoryEngineerDecision;
    }

    public User getSignBy() {
        return signBy;
    }

    public void setSignBy(User signBy) {
        this.signBy = signBy;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public AssetBrand getBrand() {
        return brand;
    }

    public void setBrand(AssetBrand brand) {
        this.brand = brand;
    }

    public String getEstimateHeader() {
        return estimateHeader;
    }

    public void setEstimateHeader(String estimateHeader) {
        this.estimateHeader = estimateHeader;
    }

    public String getEstimateBody() {
        return estimateBody;
    }

    public void setEstimateBody(String estimateBody) {
        this.estimateBody = estimateBody;
    }

    public String getEstimateFooter() {
        return estimateFooter;
    }

    public void setEstimateFooter(String estimateFooter) {
        this.estimateFooter = estimateFooter;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAddress01() {
        return customerAddress01;
    }

    public void setCustomerAddress01(String customerAddress01) {
        this.customerAddress01 = customerAddress01;
    }

    public String getCustomerAddress02() {
        return customerAddress02;
    }

    public void setCustomerAddress02(String customerAddress02) {
        this.customerAddress02 = customerAddress02;
    }

    public String getCustomerAddress03() {
        return customerAddress03;
    }

    public void setCustomerAddress03(String customerAddress03) {
        this.customerAddress03 = customerAddress03;
    }

    public String getCustomerAddress04() {
        return customerAddress04;
    }

    public void setCustomerAddress04(String customerAddress04) {
        this.customerAddress04 = customerAddress04;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getCustomerLandNo() {
        return customerLandNo;
    }

    public void setCustomerLandNo(String customerLandNo) {
        this.customerLandNo = customerLandNo;
    }

    public String getCustomerAttentionPerson() {
        return customerAttentionPerson;
    }

    public void setCustomerAttentionPerson(String customerAttentionPerson) {
        this.customerAttentionPerson = customerAttentionPerson;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getWoNo() {
        return woNo;
    }

    public void setWoNo(String woNo) {
        this.woNo = woNo;
    }

    public String getWoBrand() {
        return woBrand;
    }

    public void setWoBrand(String woBrand) {
        this.woBrand = woBrand;
    }

    public String getWoModelNo() {
        return woModelNo;
    }

    public void setWoModelNo(String woModelNo) {
        this.woModelNo = woModelNo;
    }

    public String getWoSerialNo() {
        return woSerialNo;
    }

    public void setWoSerialNo(String woSerialNo) {
        this.woSerialNo = woSerialNo;
    }

    public String getWoComplain() {
        return woComplain;
    }

    public void setWoComplain(String woComplain) {
        this.woComplain = woComplain;
    }

    public String getWoCondition() {
        return woCondition;
    }

    public void setWoCondition(String woCondition) {
        this.woCondition = woCondition;
    }

    public Boolean getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Boolean taxInclude) {
        this.taxInclude = taxInclude;
    }

    public Boolean getTaxBreakdown() {
        return taxBreakdown;
    }

    public void setTaxBreakdown(Boolean taxBreakdown) {
        this.taxBreakdown = taxBreakdown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDiscountBreakdown() {
        return discountBreakdown;
    }

    public void setDiscountBreakdown(Boolean discountBreakdown) {
        this.discountBreakdown = discountBreakdown;
    }
}
