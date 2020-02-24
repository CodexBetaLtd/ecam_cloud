package com.codex.ecam.model.asset;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.ApplicationEvent;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.event.RootAwareEvent;
import com.codex.ecam.listeners.asset.AssetLogListener;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.model.api.RootApplicationEventAware;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.model.biz.part.PartNotification;
import com.codex.ecam.model.inventory.bom.BOMGroupPart;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.model.inventory.rfq.RFQItem;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenanceAsset;

@Entity
@Table(name="tbl_asset")
@EntityListeners({AssetLogListener.class})
public class Asset extends BaseModel implements RootApplicationEventAware{

	private static final long serialVersionUID = 3466843752790052309L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="asset_s")
	@SequenceGenerator(name="asset_s", sequenceName="asset_s", allocationSize=1)
	@Column(name = "id")
	private Integer id;

	@JoinColumn( name = "asset_category_id" )
	@ManyToOne( targetEntity = AssetCategory.class, fetch = FetchType.LAZY)
	private AssetCategory assetCategory;

	@JoinColumn( name = "parent_asset_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset parentAsset;

	@JoinColumn( name = "site_id" )
	@ManyToOne( targetEntity = Asset.class, fetch = FetchType.LAZY)
	private Asset site;

	@JoinColumn( name = "asset_status_id" )
	@ManyToOne( targetEntity = AssetStatus.class, fetch = FetchType.LAZY)
	private AssetStatus assetStatus;

	@JoinColumn( name = "asset_offline_tracker_id" )
	@ManyToOne( targetEntity = AssetOfflineTracker.class, fetch = FetchType.LAZY)
	private AssetOfflineTracker offLineTracker;

	@JoinColumn( name = "current_customer_id" )
	@ManyToOne(targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business customer;

	@JoinColumn(name = "current_asset_event_id")
	@OneToOne(targetEntity = AssetEvent.class, fetch = FetchType.LAZY)
	private AssetEvent currentAssetEvent;

	@JoinColumn( name = "country_id" )
	@ManyToOne( targetEntity = Country.class, fetch = FetchType.LAZY)
	private Country country;

	@JoinColumn( name="business_id" )
	@ManyToOne( targetEntity = Business.class, fetch = FetchType.LAZY)
	private Business business;

	@JoinColumn(name="brand_id")
	@ManyToOne(targetEntity = AssetBrand.class, fetch = FetchType.LAZY)
	private AssetBrand brand;

	@JoinColumn(name="model_id")
	@ManyToOne(targetEntity = AssetModel.class, fetch = FetchType.LAZY)
	private AssetModel model;

	@Column(name="name")
	private String name;

	@Column(name="description")
	private String description;
	
	@Column(name="code")
	private String code;

	@Column(name="image_location")
	private String imageLocation;

	@Column(name="serial_no")
	private String serialNo;

	@Column(name="notes")
	private String notes;

	@Column(name="is_online")
	private Boolean isOnline;

	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="province")
	private String province;

	@Column(name="postal_code")
	private String postalcode;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="latitude")
	private String latitude;

	@Column(name="department_id")
	private Integer departmentId;
	
	@Column(name="inventory_code")
	private String inventoryCode;
	
	@Column(name="unspc_code")
	private String unspcCode;
	
	@Column(name="barcode")
	private String barcode;
	
	@Column(name="last_price")
	private Integer lastPrice;
	
	@Column(name="part_type_id")
	private PartType partType;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetMeterReading> assetMeterReadings;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetEventTypeAsset> assetEventTypeAssets;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<ScheduledMaintenanceAsset> assetScheduledMaintenances;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetUser> assetUsers;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Warranty> warranties;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetConsumingReference> partConsumingReferences;

	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetConsumingReference> assetConsumingReferences;

	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetBusiness> assetBusinesses;

	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<Stock> stocks;
	
	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<AssetFile> assetFiles;
	
	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<PartNotification> partNotifications;
	
	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private Set<AssetLog> assetLogs;
	
	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private Set<BOMGroupPart> bomGroupParts;
	
	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private Set<ReceiptOrderItem> receiptOrderItems;
	
	@OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	private Set<RFQItem> rfqItems;
	
	@Transient
	private Integer childCount;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssetCategory getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public AssetBrand getBrand() {
		return brand;
	}

	public void setBrand(AssetBrand brand) {
		this.brand = brand;
	}

	public AssetModel getModel() {
		return model;
	}

	public void setModel(AssetModel model) {
		this.model = model;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Asset getParentAsset() {
		return parentAsset;
	}

	public void setParentAsset(Asset parentAsset) {
		this.parentAsset = parentAsset;
	}

	public Asset getSite() {
		return site;
	}

	public void setSite(Asset site) {
		this.site = site;
	}

	public Set<AssetMeterReading> getAssetMeterReadings() {
		return assetMeterReadings;
	}

	public void setAssetMeterReadings(Set<AssetMeterReading> assetMeterReadings) {
		updateCollection("assetMeterReadings", assetMeterReadings);
	}

	public AssetStatus getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(AssetStatus assetStatus) {
		this.assetStatus = assetStatus;
	}

	public AssetOfflineTracker getOffLineTracker() {
		return offLineTracker;
	}

	public void setOffLineTracker(AssetOfflineTracker offLineTracker) {
		this.offLineTracker = offLineTracker;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Set<ScheduledMaintenanceAsset> getAssetScheduledMaintenances() {
		return assetScheduledMaintenances;
	}

	public void setAssetScheduledMaintenances(Set<ScheduledMaintenanceAsset> assetScheduledMaintenances) {
		updateCollection("assetScheduledMaintenances", assetScheduledMaintenances);
	}

	public Set<AssetEventTypeAsset> getAssetEventTypeAssets() {
		return assetEventTypeAssets;
	}

	public void setAssetEventTypeAssets(Set<AssetEventTypeAsset> assetEventTypeAssets) {
		updateCollection("assetEventTypeAssets", assetEventTypeAssets);
	}

	public AssetEvent getCurrentAssetEvent() {
		return currentAssetEvent;
	}

	public void setCurrentAssetEvent(AssetEvent currentAssetEvent) {
		this.currentAssetEvent = currentAssetEvent;
	}

	public Set<AssetUser> getAssetUsers() {
		return assetUsers;
	}

	public void setAssetUsers(Set<AssetUser> assetUsers) {
		updateCollection("assetUsers", assetUsers);
	}

	public Set<AssetConsumingReference> getAssetConsumingReferences() {
		return assetConsumingReferences;
	}

	public void setAssetConsumingReferences(Set<AssetConsumingReference> assetConsumingReferences) {
		updateCollection("assetConsumingReferences", assetConsumingReferences);
	}

	public Set<Warranty> getWarranties() {
		return warranties;
	}

	public void setWarranties(Set<Warranty> warranties) {
		updateCollection("warranties", warranties);
	}

	public Set<AssetConsumingReference> getPartConsumingReferences() {
		return partConsumingReferences;
	}

	public void setPartConsumingReferences(Set<AssetConsumingReference> partConsumingReferences) {
		this.partConsumingReferences = partConsumingReferences;
	}

	public Set<AssetBusiness> getAssetBusinesses() {
		return assetBusinesses;
	}

	public void setAssetBusinesses(Set<AssetBusiness> assetBusinesses) {
		this.assetBusinesses = assetBusinesses;
	}

	public void setAssetCustomers(Set<AssetCustomer> assetCustomers) {
		updateCollection("assetCustomers", assetCustomers);
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}


	public Business getCustomer() {
		return customer;
	}

	public void setCustomer(Business customer) {
		this.customer = customer;
	}

	public Set<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(Set<Stock> stocks) {
		updateCollection("stocks", stocks);
	}

	public Set<AssetFile> getAssetFiles() {
		return assetFiles;
	}

	public void setAssetFiles(Set<AssetFile> assetFiles) {
		updateCollection("assetFiles", assetFiles);
	}
	
	public String getInventoryCode() {
		return inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	public String getUnspcCode() {
		return unspcCode;
	}

	public void setUnspcCode(String unspcCode) {
		this.unspcCode = unspcCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Integer lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public Set<PartNotification> getPartNotifications() {
		return partNotifications;
	}

	public void setPartNotifications(Set<PartNotification> partNotifications) {
		updateCollection("partNotifications", partNotifications);
	}
	public Set<AssetLog> getAssetLogs() {
		return assetLogs;
	}

	public void setAssetLogs(Set<AssetLog> assetLogs) {
		updateCollection("assetLogs", assetLogs);
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public Set<BOMGroupPart> getBomGroupParts() {
		return bomGroupParts;
	}

	public void setBomGroupParts(Set<BOMGroupPart> bomGroupParts) {
		updateCollection("bomGroupParts", bomGroupParts);
	}

	public Set<ReceiptOrderItem> getReceiptOrderItems() {
		return receiptOrderItems;
	}

	public void setReceiptOrderItems(Set<ReceiptOrderItem> receiptOrderItems) {
		updateCollection("receiptOrderItems", receiptOrderItems);
	}

	public Set<RFQItem> getRfqItems() {
		return rfqItems;
	}

	public void setRfqItems(Set<RFQItem> rfqItems) {
		updateCollection("rfqItems", rfqItems);
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof Asset ) {
			return getId().equals(((Asset)obj).getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = (31 * hash) + getId();
		return hash;
	}

	@Override
	public ApplicationEvent getEvent() {
		return new RootAwareEvent(this);
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public PartType getPartType() {
		return partType;
	}

	public void setPartType(PartType partType) {
		this.partType = partType;
	}
	
	
}
