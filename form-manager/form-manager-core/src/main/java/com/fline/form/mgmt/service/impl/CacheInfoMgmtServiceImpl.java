package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CacheInfo;
import com.fline.form.access.service.CacheInfoAccessService;
import com.fline.form.mgmt.service.CacheInfoMgmtService;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.util.SpringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("cacheInfoMgmtService")
public class CacheInfoMgmtServiceImpl implements CacheInfoMgmtService {

	@Resource
	private CacheInfoAccessService cacheInfoAccessService;

	private final String suffix = "MgmtService";

	private final int threadSize = 10;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(threadSize);

	@Override
	public Pagination<CacheInfo> findPagination(Map<String, Object> param,
			Ordering order, Pagination<CacheInfo> page) {
		return cacheInfoAccessService.findPagination(param, order, page);
	}

    @Override
    public void refreshCache(String code) {
        threadPool.execute(new RefreshCacheRunnable(code));
    }

    @Override
    public void refreshAll() {
        List<CacheInfo> cacheInfos = cacheInfoAccessService.findAll();
        for (CacheInfo cacheInfo : cacheInfos) {
            refreshCache(cacheInfo.getCode());
        }
    }

    private Cacheable getCacheMgmtService(String code) {
        return (Cacheable) SpringUtil.getBean(code + suffix);
    }

    private class RefreshCacheRunnable implements Runnable {

	    private String code;

        public RefreshCacheRunnable(String code) {
            this.code = code;
        }

        @Override
        public void run() {
        	//更改状态 ：正在缓存中
            cacheInfoAccessService.autoUpdateStatus(code, CacheInfo.IN_CACHE);
            try {
            	//刷新缓存
				getCacheMgmtService(code).refreshCache();
				//改变状态 ：缓存成功
				cacheInfoAccessService.updateRefreshTime(code, new Date());
			} catch (Exception e) {
				cacheInfoAccessService.autoUpdateStatus(code, CacheInfo.ERROR);
				e.printStackTrace();
			}
        }
    }

}
