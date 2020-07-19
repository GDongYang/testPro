package com.fline.form.mgmt.service.impl;

import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.CertTemp;
import com.fline.form.access.model.SealInfo;
import com.fline.form.access.model.TempAttachment;
import com.fline.form.access.service.*;
import com.fline.form.constant.KeyConstant;
import com.fline.form.mgmt.service.Cacheable;
import com.fline.form.mgmt.service.DataCacheService;
import com.fline.form.mgmt.service.SealInfoMgmtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("sealInfoMgmtService")
public class SealInfoMgmtServiceImpl implements SealInfoMgmtService, Cacheable {
	@Resource
	private SealInfoAccessService sealInfoAccessService;
	@Resource
	private CertTempAccessService certTempAccessService;
	@Resource
	private QuestionAccessService questionAccessService;
	@Resource
	private DepartmentAccessService departmentAccessService;
	@Resource
	private DataCacheService dataCacheService;
	@Resource
	private TempAttachmentAccessService tempAttachmentAccessService;
	
	@Override
	public Pagination<SealInfo> findPagination(Map<String, Object> param, Ordering order, Pagination<SealInfo> page) {
		return sealInfoAccessService.findPagination(param, order, page);
	}
	
	@Override
	public Map<String,Object> findByidkeyword(Long id) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int i = 0;
		List<Map<String,Object>> certseal =  sealInfoAccessService.findCertSeal("id");
		if(certseal != null){
			String[] ids = new String[certseal.size()];
			for(Map<String,Object> map : certseal){
				ids[i]= (String)map.get("certId");
				i++;
			}
			List<CertTemp> certTemp = certTempAccessService.findByIds(ids);
			
			dataMap.put("keyword", certseal.get(0).get("keyword"));
			dataMap.put("certTemp", certTemp);
		}
		
		return dataMap;
	}


	@Override
	public void update(SealInfo sealInfo,String[] certTempIdS,String[] area,String keyword) {
		SealInfo findSealInfo = sealInfoAccessService.findById(sealInfo.getId());
		
		sealInfoAccessService.createSealHistory(findSealInfo);
		sealInfo.setVersion(findSealInfo.getVersion()+1);
		sealInfoAccessService.update(sealInfo);								//更新印章信息
		//sealInfoAccessService.removeCertSeal(sealInfo.getId());
		tempAttachmentAccessService.removeByCode(findSealInfo.getCode());	//删除关联表数据
		
		if(!Detect.notEmpty(certTempIdS)) {
			certTempIdS = new String[]{""};
		}	
		if(!Detect.notEmpty(area)) {
			area = new String[]{""};
		}	
		Map<String,Object> map ;
		for(String areaCode : area) {
			for(String certId : certTempIdS) {
				/*map = new HashMap<String,Object>();
				map.put("areaCode", areaCode);
				map.put("certId", certId);
				map.put("sealId", sealInfo.getId());
				map.put("keyword", keyword);
				sealInfoAccessService.createCertSeal(map);*/
				TempAttachment ta = new TempAttachment();
				ta.setCode(sealInfo.getCode());
				ta.setKeyWord(keyword);
				ta.setType(1);
				if(Detect.notEmpty(certId)) {
					ta.setTempId(Long.valueOf(certId));
				}
				tempAttachmentAccessService.create(ta);
			}
		}
		//将印章信息添加到redis缓存中
		//dataCacheService.setSealInfo(sealInfo.getCode(),sealInfo);
	}

	@Override
	public void remove(SealInfo sealInfo) {
		SealInfo sealInfoOld = sealInfoAccessService.findById(sealInfo.getId());
		sealInfoAccessService.createSealHistory(sealInfoOld);
		//sealInfoAccessService.removeCertSeal(sealInfo.getId());
		tempAttachmentAccessService.removeByCode(sealInfo.getCode());//删除关联的模板
		sealInfoAccessService.remove(sealInfo);
		//删除存放在redis中的值
		dataCacheService.removeRedis(KeyConstant.YZTB_SEAL,sealInfoOld.getCode());

	}
	
	@Override
	public void updateActive(SealInfo sealInfo) {
		sealInfoAccessService.updateActive(sealInfo);
		//将印章信息添加到redis缓存中
		dataCacheService.setSealInfo(sealInfo.getCode(),sealInfo);
	}

	

	@Override
	public void create(SealInfo sealInfo,String[] certTempIdS,String[] area,String keyword) {
		sealInfoAccessService.create(sealInfo);
		/*if(!Detect.notEmpty(certTempIdS)) {
			certTempIdS = new String[]{""};
		}*/	
		if(!Detect.notEmpty(area)) {
			area = new String[]{""};
		}	
		Map<String,Object> map ;
		/*
		 * 创建与模板的关联数据
		 */
		if(Detect.notEmpty(area)) {
			for(String areaCode : area) {
				if(Detect.notEmpty(certTempIdS)) {
					for(String certId : certTempIdS) {
						/*map = new HashMap<String,Object>();
						map.put("areaCode", areaCode);
						map.put("certId", certId);
						map.put("sealId", sealInfo.getId());
						map.put("keyword", keyword);
						sealInfoAccessService.createCertSeal(map);*/
						if(!certId.equals("")) {
							TempAttachment ta = new TempAttachment();
							ta.setCode(sealInfo.getCode());
							ta.setKeyWord(keyword);
							ta.setType(1);
							ta.setTempId(Long.valueOf(certId));
							tempAttachmentAccessService.create(ta);
						}
					}
				}
			}
		}

		//将印章信息添加到redis缓存中
		// dataCacheService.setSealInfo(sealInfo.getCode(),sealInfo);
	}

	@Override
	public SealInfo findById(long id) {
		return sealInfoAccessService.findById(id);
	}

	@Override
	public List<SealInfo> findAll() {
		return sealInfoAccessService.findAll();
	}

	@Override
	public SealInfo findByName(String name) {
		return sealInfoAccessService.findByName(name);
	}

	@Override
	public void analysisExcel(File file) throws Exception{
//		byte[] byt = toByteArray(file);
//		InputStream is = new ByteArrayInputStream(byt);
//		List<SealInfo> sealInfoList= readXlsx(is);
//		sealInfoAccessService.create(sealInfoList);
	}
	
	private byte[] toByteArray(File f) throws IOException {
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
	

	@Override
	public List<TempAttachment> findCertSeal(String code) {
		return tempAttachmentAccessService.findByCode(code);
	}

	@Override
	public void refreshCache() {
	    dataCacheService.clearSealInfo();
//	    dataCacheService.setSealInfos(sealInfoAccessService.findAllVo());
    }

	/* (non-Javadoc)
	 * @see com.fline.yztb.mgmt.service.SealInfoMgmtService#findByDepartmentId(long)
	 */
	@Override
	public List<SealInfo> findByDepartmentId(long deptId) {
		return sealInfoAccessService.findByDepartmentId(deptId);
	}

	@Override
	public void updateImage(long id, byte[] image) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("image", image);
		params.put("id", id);
		sealInfoAccessService.updateImage(params);
	}

}
