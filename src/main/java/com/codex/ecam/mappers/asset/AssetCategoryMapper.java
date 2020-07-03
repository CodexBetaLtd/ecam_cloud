package com.codex.ecam.mappers.asset;


import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.maintenance.task.TaskDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.model.asset.AssetCategoryTask;

public class AssetCategoryMapper extends GenericMapper<AssetCategory, AssetCategoryDTO> {

	private static AssetCategoryMapper instance = null;
	
	private AssetCategoryMapper(){
	}
	
	public static AssetCategoryMapper getInstance() {
		if (instance == null) {
			instance = new AssetCategoryMapper();
		}
		return instance;
	}

	@Override
	public AssetCategoryDTO domainToDto(AssetCategory domain) throws Exception {
		AssetCategoryDTO dto = new AssetCategoryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		dto.setOverideRule(domain.getOverideRule());
		
		if ( domain.getAssetCategoryType() != null ) {
			dto.setType(domain.getAssetCategoryType() );
		}
		
		if ( domain.getParentAssetCategory() != null ) {
			dto.setParentId(domain.getParentAssetCategory().getId());
			dto.setParentName(domain.getParentAssetCategory().getName());
		}
		if ( domain.getBusiness()!= null ) {
			dto.setParentId(domain.getBusiness().getId());
			dto.setParentName(domain.getBusiness().getName());
		}
		setTask(domain,dto);
		dto.setVersion(domain.getVersion());
		return dto;
	}
	
	private void setTask(AssetCategory domain,AssetCategoryDTO dto){
		for(AssetCategoryTask assetCategoryTask:domain.getTasks()){
			TaskDTO taskDTO=new TaskDTO();
			taskDTO.setAssetCatgoryTaskId(assetCategoryTask.getId());
			taskDTO.setAssetCatgoryVersionId(assetCategoryTask.getVersion());
			if(assetCategoryTask.getTask()!=null){
				taskDTO.setId(assetCategoryTask.getTask().getId());
				taskDTO.setVersion(assetCategoryTask.getTask().getVersion());
				taskDTO.setName(assetCategoryTask.getTask().getName());
				taskDTO.setDescription(assetCategoryTask.getTask().getDescription());
				taskDTO.setEstimatedHours(assetCategoryTask.getTask().getEstimatedHours());
				taskDTO.setTaskType(assetCategoryTask.getTask().getTaskType());
			}


			dto.getTasks().add(taskDTO);
		}
	}

	@Override
	public void dtoToDomain(AssetCategoryDTO dto, AssetCategory domain) throws Exception {
		domain.setId(dto.getId());
		domain.setName(dto.getName());
		domain.setDescription(dto.getDescription());
		domain.setOverideRule(dto.getOverideRule());
		domain.setAssetCategoryType(dto.getType());
		domain.setIsDeleted(dto.getIsDeleted());
		domain.setVersion(dto.getVersion());
	}

    @Override
    public AssetCategoryDTO domainToDtoForDataTable(AssetCategory domain) throws Exception {
		AssetCategoryDTO dto = new AssetCategoryDTO();
		dto.setId(domain.getId());
		dto.setName(domain.getName());
		dto.setDescription(domain.getDescription());
		if ( domain.getParentAssetCategory() != null ) {
			dto.setParentName(domain.getParentAssetCategory().getName());
		}
		return dto;
    }
}
