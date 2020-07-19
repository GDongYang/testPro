package com.fline.form.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fline.form.annotation.IgnoreExpire;
import com.fline.form.exception.BaseException;
import com.fline.form.mgmt.service.FormMgmtService;
import com.fline.form.mgmt.service.TempInfoService;
import com.fline.form.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单创建，保存，加载
 *
 */
@RestController
@RequestMapping("/form")
public class FormController {

	@Autowired
	private FormMgmtService formMgmtService;
	@Autowired
    private TempInfoService tempInfoService;

    /**
     * pc端获取暂存数据
     * @param projId
     * @param formBusiCode
     * @return
     */
    @GetMapping("/loadTempFrom")
	public ResponseResult<Map<String,Object>> loadTempFrom(@RequestParam(value = "projId", defaultValue = "" ,required = false) String projId,@RequestParam(value = "formBusiCode") String formBusiCode ){
        Map<String, Object> stringObjectMap = tempInfoService.loadTempFrom(projId, formBusiCode);
        return ResponseResult.success(stringObjectMap);
    }

    /**
     * 暂存
     */
    @PostMapping("/tempSaveFromData")
    public ResponseResult<String> tempSaveFromData(@RequestParam(value = "projId") String projId,
                                                   @RequestParam(value = "formBusiCode") String formBusiCode,
                                                   @RequestParam(value = "formInfo") String formInfo,
                                                   @RequestParam(value = "attrInfo",required = false) String attrInfo){
        String stringResponseResult = tempInfoService.tempSaveFromData(formBusiCode, projId, formInfo, attrInfo);
        return ResponseResult.success(stringResponseResult);
    }
    /**
     * 测试方法  获取用户信息并创建表单地址
     */
    @PostMapping(value = "/createFormById/item/{serviceCode}/{bType}")
    public ResponseResult<Map<String,Object>> createFormById(@PathVariable(value = "serviceCode")String serviceCode,
                                                             @PathVariable(value = "bType") String bType,
                                                             @RequestParam String idNum){
        System.out.println("请求进来了");
        Map<String,Object> result = formMgmtService.createFormByIdNum(serviceCode,bType,idNum);
        return ResponseResult.success(result);
    }


	/**
	 * 新创建表单地址
	 * @param formReq 事项信息，申请人信息
	 * @return 表单地址+表单流水号
	 */
	@PostMapping(value = "/createForm/item/{serviceCode}")
	public ResponseResult<Map<String, Object>> createFormByItem(
			@PathVariable(value = "serviceCode") String serviceCode,
			@RequestBody String formReq) {
		Map<String, Object> result = formMgmtService.createFormByItem(serviceCode, formReq);
		return ResponseResult.success(result);
	}

    /**
     * 保存表单数据
     * @param formBusiCode
     * @return
     */
    @IgnoreExpire
    @PostMapping(value = "/saveFormData/{formBusiCode}")
    public ResponseResult<String> saveFormData(
            @PathVariable(value = "formBusiCode") String formBusiCode,
            @RequestParam String formInfo, @RequestParam(required = false) String postInfo, @RequestParam(required = false) String attrInfo) {
        String result = formMgmtService.saveFormData(formBusiCode, formInfo, postInfo, attrInfo);
        return ResponseResult.success(result);
    }

    /**
     * 提交表单
     * @param formBusiCode
     * @return
     */
    @PostMapping(value = "/submitFormData/{formBusiCode}")
    public ResponseResult<String> submitFormData(
            @PathVariable(value = "formBusiCode") String formBusiCode,
            //@RequestBody String formReq
            @RequestParam(value = "formInfo", required = false ) String formInfo,
            @RequestParam(value = "attrInfo", required = false ) String attrInfo,
            @RequestParam(value = "postInfo", required = false ) String postInfo
            ) {
        System.out.println("进入提交的方法");
//        JSONObject json = JSON.parseObject(formReq);
//        String formInfo = json.getString("formInfo");
//        String attrInfo = json.getString("attrInfo");
//        String postInfo = json.getString("postInfo");
        String resultMap = formMgmtService.submitFormData(formBusiCode, formInfo, attrInfo, postInfo);
        return ResponseResult.success(resultMap);
    }

	/**
	 *  获取表单自动填充数据
	 * @param formBusiCode 表单流水号
	 * @return 自动带出的数据
	 */
	@GetMapping(value = "/loadData/{formBusiCode}")
	public ResponseResult<Map<String, Object>> loadFormData(
			@PathVariable(value = "formBusiCode") String formBusiCode) {
		Map<String, Object> result = formMgmtService.loadFormData(formBusiCode);
		return ResponseResult.success(result);
	}

    /**
     * 自助终端机跳转表单地址
     * @param itemCode
     * @return
     */
    @GetMapping(value = "/terminal/formUrl/{itemCode}")
    public void getTerminalFormUrl(
            @PathVariable("itemCode") String itemCode,
            @RequestParam("personInfo") String personInfo,
            @RequestParam("areaCode") String areaCode,
            HttpServletResponse response) throws IOException {
        String formUrl = formMgmtService.getTerminalFormUrl(itemCode, personInfo, areaCode);
        response.sendRedirect(formUrl);
    }

    /**
     * 自助终端机提交办件信息
     * @param formBusiCode
     * @param formInfo
     * @param attrInfo
     * @param postInfo
     * @return
     */
    @PostMapping(value = "/terminal/submitFormData/{formBusiCode}")
    public ResponseResult<Map<String, Object>> submitYcsl(
            @PathVariable(value = "formBusiCode") String formBusiCode,
            @RequestParam String formInfo, @RequestParam String attrInfo, @RequestParam String postInfo) {
        Map<String, Object> resultMap = formMgmtService.submitYcsl(formBusiCode, formInfo, attrInfo, postInfo);
        return ResponseResult.success(resultMap);
    }

    /**
     * 一窗获取表单
     * @param itemInnerCode
     * @param cerNo
     * @param cerName
     */
    @GetMapping(value = "/offline/formUrl")
    public ResponseResult<String> getOfflineFormUrl(String itemInnerCode, String cerNo, String cerName, String projectId) {
        String formUrl = formMgmtService.getOfflineFormUrl(itemInnerCode, cerNo, cerName, projectId);
        return ResponseResult.success(formUrl);
    }

    /**
     * 一窗加载表单信息
     * @param formBusiCode
     * @return
     */
    @IgnoreExpire
    @GetMapping(value = "/offline/loadData/{formBusiCode}")
    public ResponseResult<Map<String, Object>> loadOfflineFormData(
            @PathVariable(value = "formBusiCode") String formBusiCode) {
        Map<String, Object> result = formMgmtService.loadOfflineFormData(formBusiCode);
        return ResponseResult.success(result);
    }

    /**
     * 一窗提交
     * @param formBusiCode
     * @param formInfo
     * @return
     */
    @IgnoreExpire
    @PostMapping(value = "/offline/submitFormData/{formBusiCode}")
    public ResponseResult<Map<String, Object>> submitOfflineForm(
            @PathVariable(value = "formBusiCode") String formBusiCode, @RequestParam String formInfo) {
        formMgmtService.submitOfflineForm(formBusiCode, formInfo);
        return ResponseResult.success();
    }

    /**
     *
     * @param base64
     * @param path 值为 ＰＣ/APP
     * @return
     */
    @IgnoreExpire
    @PostMapping(value = "/upload/oss")
    public ResponseResult<String> uploadOss(String base64,String path) {
        String url = formMgmtService.uploadOss(base64,path);
        return ResponseResult.success(url);
    }

}
