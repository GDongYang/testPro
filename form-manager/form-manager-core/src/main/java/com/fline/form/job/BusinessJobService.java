package com.fline.form.job;

import com.fline.form.access.service.BusinessAccessService;
import com.fline.form.access.service.BusinessItemAccessService;
import com.fline.form.mgmt.service.BusinessItemMgmtService;
import com.fline.form.mgmt.service.BusinessMgmtService;
import com.fline.form.mgmt.service.DataCacheService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

@Component
public class BusinessJobService {
	
	private Log logger = LogFactory.getLog(BusinessJobService.class);
	@Resource
	private BusinessAccessService businessAccessService;
	@Resource
	private BusinessMgmtService businessMgmtService;
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private BusinessItemAccessService businessItemAccessService;
	@Resource
	private BusinessItemMgmtService businessItemMgmtService;
	@Scheduled(cron = "${businessJobCron}")
	public void interval() {
//		try {
//			String ip= LocalHostUtil.getLocalHostLANAddress().getHostAddress(); //获取本机ip
//			logger.info("businessJob:"+ ip);
//			if(Contants.LOCALHOST.equals(ip)) {
//				logger.info("BusinessJobService start:" + new Date());
//				int count1 = businessAccessService.deleteTest();
//				int count2 = businessItemAccessService.deleteTest();
//				logger.info("BusinessJobService 【end】:" + new Date() +
//						"【business】:" + count1 + "【businessItem】:" + count2);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * @Description: 采用定时任务去获取到最近的统计数据并加入到缓存中   
	 * @return void
	 */
	//定时每6小时
	@Scheduled(cron = "0 0 0/6 * * ?")
	public void refreshBusinessCache() {
		/*
		 * 获取锁
		 */
		String statisticState = dataCacheService.getStatisticState();
		if(statisticState == null || "failed".equals(statisticState)) {
			String requestId = UUID.randomUUID().toString();
			businessMgmtService.lock(requestId);
            try {
                //再次从缓存中读取存储的
                statisticState = dataCacheService.getStatisticState();
                if(statisticState == null || "failed".equals(statisticState)) {
                	long startTime = System.currentTimeMillis();
                    /*
                     	* 执行统计
                     */
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("forCache", true);
                    businessMgmtService.deptItemCountByDay(params);//饼图：统计本周事项办件量分布

                    params.put("days", 7);//折线图统计本周每天的办件量
                    businessMgmtService.dayItemCountChange(params);
                    params.put("days", 30);//折线图统计本月每天的办件量
                    businessMgmtService.dayItemCountChange(params);

                    params.put("type", 0);
                    businessMgmtService.deptItemCount(params);//部门排名图：全量
                    params.put("type", 1);
                    businessMgmtService.deptItemCount(params);//部门排名图：本月

                    businessMgmtService.cityItemCount(params);//地区排名图：本月
                    params.put("type", 0);
                    businessMgmtService.cityItemCount(params);//地区排名图：全量

                    businessMgmtService.serviceItemCount(params);//证明和事项各自总数量
                    dataCacheService.setStatisticState("success", 5 * 60);//设置缓存状态为成功
                    
                    long endTime = System.currentTimeMillis();
                    logger.info("同步完成,用时 " +  (endTime - startTime) + " ms");
                }
            }catch (Exception e) {
                logger.error("同步统计数据出错",e);
                dataCacheService.setStatisticState("failed", 2 * 60);//设置缓存状态为失败其他线程可再次缓存
            }finally {
                //释放锁
                businessMgmtService.unLock(requestId);
            }
		}
	}
}
