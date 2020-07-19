package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Department;
import com.fline.form.access.model.Position;
import com.fline.form.access.service.PositionAccessService;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.DepartmentMgmtService;
import com.fline.form.mgmt.service.PositionMgmtService;
import com.fline.form.vo.PositionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("positionMgmtService")
public class PositionMgmtServiceImpl implements PositionMgmtService, Cacheable {
	@Resource
	private PositionAccessService positionAccessService;
	@Resource
	private DepartmentMgmtService departmentMgmtService;
	@Resource
 	private DataCacheService dataCacheService;

	@Override
	public Pagination<Position> findPagination(Map<String, Object> param,
			Ordering order, Pagination<Position> page) {
		return positionAccessService.findPagination(param,order,page);
	}

	@Override
	public void update(Position position) {
		Position findPosition = positionAccessService.findById(position.getId());
		positionAccessService.update(position);
		//若更新后的部门层级比原先低则删除原先的事项
		Department updateDepartment = departmentMgmtService.findById(position.getDepartmentId());
		Department findDepartment = departmentMgmtService.findById(findPosition.getDepartmentId());
		if(!updateDepartment.getCode().equals(findDepartment.getCode()) && updateDepartment.getCode().indexOf(findDepartment.getCode()) != -1) {
			positionAccessService.deletePositionItem(position.getId());
		}
		//assignItems(position.getId(), itemCodes);
	}

	@Override
	public void remove(Position position) {
		positionAccessService.remove(position);
		//删除redis缓存
		dataCacheService.removeRedis(KeyConstant.YZTB_POSITION,String.valueOf(position.getId()));
	}

	@Override
	public Position create(Position position,String[] itemCodes) {
		Department department = departmentMgmtService.findById(position.getDepartmentId());
		position.setCode(department.getCode());
		position = positionAccessService.create(position);
		assignItems(position.getId(), itemCodes);
		return position;
	}
	
	private void assignItems(long positionId,String[] itemCodes) {
		positionAccessService.deletePositionItem(positionId);
		if(itemCodes != null && itemCodes.length > 0){
			Map<String, Object> params = new HashMap<>();
			params.put("positionId", positionId);
			for(int i=0; i < itemCodes.length; i++){
				if(Detect.notEmpty(itemCodes[i])) {
					if(!Detect.notEmpty(itemCodes[i])) {
						continue;
					}
					params.put("itemCode", itemCodes[i]);
					positionAccessService.createPositionItem(params);
				}
			}
		}
	}

	@Override
	public Position findById(long id) {
		return positionAccessService.findById(id);
	}

	@Override
	public List<Position> findList(Map<String, Object> parameter) {
		return positionAccessService.find(parameter);
	}

	@Override
	public List<Position> findAll(Map<String, Object> parameter) {
		return positionAccessService.find(parameter);
	}
	
	@Override
	public List<String> findItems(long positionId) {
		return positionAccessService.findItems(positionId);
	}

	@Override
	public void refreshCache() {
	    dataCacheService.clearPosition();
	    //获取所有岗位事项关联关系
        List<Map<String, Object>> positionItemList = positionAccessService.findPositionItem();
        //根据岗位id分组
        Map<Long, List<String>> itemCodeMap = new HashMap<>();
        for (Map<String, Object> pi : positionItemList) {
            long positionId = (long) pi.get("positionId");
            List<String> itemCodes = itemCodeMap.get(positionId);
            if(itemCodes == null) {
                itemCodes = new ArrayList<>();
                itemCodeMap.put(positionId, itemCodes);
            }
            itemCodes.add(pi.get("itemCode") + "");
        }
        //获取所有岗位
        List<PositionVo> positionVos = positionAccessService.findAllVo();
        for (PositionVo vo : positionVos) {
            vo.setItemCodeList(itemCodeMap.get(vo.getId()));
        }
        dataCacheService.setPositions(positionVos);
    }

	@Override
	public void createToCache(Position position) {
		PositionVo positionVo = position.toVo();
		//获取相关联的事项code
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("positionId", position.getId());
		List<Map<String, Object>> positionItems = positionAccessService.findPositionItemByMap(params);
		List<String> itemCodes = positionItems != null? new ArrayList<String>():null; 
		for(Map<String,Object> map : positionItems) {
			itemCodes.add("" + map.get("itemCode"));
		}
		positionVo.setItemCodeList(itemCodes);
		dataCacheService.setPosition(positionVo);
	}

	@Override
	public void bindItems(Map<String, Object> params) {
		long positionId = (long) params.get("positionId");
		String[] itemCodes = (String[]) params.get("itemCodes");
		this.assignItems(positionId, itemCodes);
	}
}
