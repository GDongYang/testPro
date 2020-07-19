package com.fline.form.mgmt.service;

import java.io.File;
import java.util.Map;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.ItemConfirm;

public interface ItemConfirmMgmtService {
	Pagination<ItemConfirm> findPagination(Map<String, Object> param,
			Ordering order, Pagination<ItemConfirm> page);

	void update(ItemConfirm itemConfirm);

	void remove(ItemConfirm itemConfirm);

	ItemConfirm create(ItemConfirm itemConfirm);

	ItemConfirm findById(long id);

    void confirmSingle(ItemConfirm itemConfirm);

    int confirmList(String memo, Integer status, long[] itemIds, String[] itemNames, String[] itemInnerCodes);

    void uploadImg(long itemId, File file);

    String previewImg(long itemId);
}
