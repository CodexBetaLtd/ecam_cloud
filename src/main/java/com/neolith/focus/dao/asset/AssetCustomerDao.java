package com.neolith.focus.dao.asset;

import com.neolith.focus.model.asset.AssetCustomer;
import com.neolith.focus.repository.FocusDataTableRepository;
 
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCustomerDao extends FocusDataTableRepository<AssetCustomer, Integer>{

}
