package com.codex.ecam.dao.asset;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.asset.AssetCustomer;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AssetCustomerDao extends FocusDataTableRepository<AssetCustomer, Integer>{

}
