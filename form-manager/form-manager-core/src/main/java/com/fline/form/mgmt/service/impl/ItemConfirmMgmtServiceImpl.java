package com.fline.form.mgmt.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import com.fline.form.access.model.Item;
import com.fline.form.access.model.User;
import com.fline.form.access.service.ItemAccessService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ItemConfirm;
import com.fline.form.access.service.ItemConfirmAccessService;
import com.fline.form.mgmt.service.ItemConfirmMgmtService;
import sun.misc.BASE64Encoder;

@Service("itemConfirmMgmtService")
public class ItemConfirmMgmtServiceImpl implements ItemConfirmMgmtService {

	@Resource
	private ItemConfirmAccessService itemConfirmAccessService;
	@Resource
	private UserSessionManagementService userSessionManagementService;
	@Resource
	private ItemAccessService itemAccessService;

	@Override
	public Pagination<ItemConfirm> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ItemConfirm> page) {
		return itemConfirmAccessService.findPagination(param, order, page);
	}

	@Override
	public void update(ItemConfirm itemConfirm) {
		itemConfirmAccessService.update(itemConfirm);
	}

	@Override
	public void remove(ItemConfirm itemConfirm) {
		itemConfirmAccessService.remove(itemConfirm);
	}

	@Override
	public ItemConfirm create(ItemConfirm itemConfirm) {
		return itemConfirmAccessService.create(itemConfirm);
	}

	@Override
	public ItemConfirm findById(long id) {
		return itemConfirmAccessService.findById(id);
	}

	@Override
	public void confirmSingle(ItemConfirm itemConfirm) {
        User user = (User) userSessionManagementService.findByContext();
        itemConfirm.setCreator(user.getName());
        itemConfirm.setDeptName(user.getOrgName());
        itemConfirm.setDeptId(user.getOrgId());
        create(itemConfirm);
    }

    @Override
    public int confirmList(String memo, Integer status, long[] itemIds, String[] itemNames, String[] itemInnerCodes) {
        User user = (User) userSessionManagementService.findByContext();
        String userName = user.getName();
        String deptName = user.getOrgName();
        long deptId = user.getOrgId();
        for (int i = 0; i < itemIds.length; i++) {
            ItemConfirm itemConfirm = new ItemConfirm();
            itemConfirm.setCreator(userName);
            itemConfirm.setDeptName(deptName);
            itemConfirm.setDeptId(deptId);
            itemConfirm.setStatus(status);
            itemConfirm.setMemo(memo);
            itemConfirm.setItemId(itemIds[i]);
            itemConfirm.setItemName(itemNames[i]);
            itemConfirm.setItemInnerCode(itemInnerCodes[i]);
            create(itemConfirm);
            itemAccessService.updateConfirmStatus(itemIds[i], status);
        }
        return itemIds.length;
    }

    @Override
    public void uploadImg(long itemId, File file) {
        try {
            itemAccessService.updateImg(itemId, FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
    }

    @Override
    public String previewImg(long itemId) {
        try {
            Item item = itemAccessService.findImg(itemId);
            return new BASE64Encoder().encode(item.getFormImg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
    }

}
