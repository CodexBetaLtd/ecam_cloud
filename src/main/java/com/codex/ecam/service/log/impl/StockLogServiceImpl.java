package com.codex.ecam.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.LogType;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.inventory.StockLogDao;
import com.codex.ecam.dto.inventory.stock.StockLedgerDTO;
import com.codex.ecam.mappers.inventory.stock.StockLedgerMapper;
import com.codex.ecam.model.BaseModel;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockLog;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.log.LogSupport;
import com.codex.ecam.service.log.api.StockLogService;

import javax.persistence.criteria.Join;
import java.math.BigDecimal;
import java.util.List;

@Service
@Qualifier("stockLogService")
public class StockLogServiceImpl implements LogSupport, StockLogService {

	@Autowired
	private StockLogDao stockLogDao;

	@Autowired
	private StockDao stockDao;


	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof Stock) {
			addAssetLog((Stock) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof Stock) {
			addAssetLog((Stock) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof Stock) {
			addAssetLog((Stock) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addAssetLog(Stock stock, String notes, LogType type) {
		StockLog log = createLogRecord(stock, notes, type);
        if (log != null) {
            stockLogDao.save(log);
		}
	}

	private StockLog createLogRecord(Stock stock, String notes, LogType type) {
        if ((stock != null) && (stock.getId() != null) && (stock.getId() > 0)) {
            StockLog stockLog = new StockLog();
            Stock currentStock = stockDao.findOne(stock.getId());
            if (currentStock != null) {
                stockLog.setStock(currentStock); 
                stockLog.setAfterQuantity(stock.getCurrentQuantity());
                if(currentStock.getLastQuantity() !=null){
                    stockLog.setBeforeQuantity(currentStock.getLastQuantity());             
                }else{
                	stockLog.setBeforeQuantity(BigDecimal.ZERO); 
                }
                stockLog.setQuantity(stock.getCurrentQuantity().subtract(stockLog.getBeforeQuantity()));
                stockLog.setNotes(notes);
                stockLog.setLogType(type);
                stockLog.setIsDeleted(false);
                return stockLog;
            }
        }
        return null;
	}

	@Override
	public DataTablesOutput<StockLedgerDTO> findAllByPartId(FocusDataTablesInput input) throws Exception {
		final DataTablesOutput<StockLog> domainOut = stockLogDao.findAll(input);
		final DataTablesOutput<StockLedgerDTO> out = StockLedgerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

    public List<StockLedgerDTO> findAllStockByPartId(Integer partId) throws Exception {
        Specification<StockLog> specification = (root, query, cb) -> {
            Join<StockLog, Stock> joinStock = root.join("stock");
            return cb.equal(joinStock.get("part").get("id"), partId);
        };
        List<StockLedgerDTO> domainOut = StockLedgerMapper.getInstance().domainToDTOList(stockLogDao.findAll(specification));
        return domainOut;
    }

    @Override
    public DataTablesOutput<StockLedgerDTO> findAllByStockId(FocusDataTablesInput input, Integer stockId) throws Exception {
        final Specification<StockLog> specification = (root, query, cb) -> cb.equal(root.get("stock").get("id"), stockId);
        final DataTablesOutput<StockLog> domainOut = stockLogDao.findAll(input, specification);
        final DataTablesOutput<StockLedgerDTO> out = StockLedgerMapper.getInstance().domainToDTODataTablesOutput(domainOut);
        return out;
    }

}
