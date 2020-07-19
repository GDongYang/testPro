package com.fline.form.constant;

public class KeyConstant {

    /**
     * reids key
     */

    //事项列表
	public static final String YZTB_ITEM = "yztbItemList";
    //事项编码列表
    public static final String YZTB_ITEM_CODE = "yztbItemCodeList";
	//模板列表
	public static final String YZTB_CERT_TEMP = "yztbCertTempList";
	//部门列表
	public static final String YZTB_DEPARTMENT = "yztbDepartmentList";
	//电子签章
	public static final String YZTB_SEAL = "yztbSealList";
	//业务账号
	public static final String YZTB_SERVICE_ACCOUNT = "yztbServiceAccountList";
	//业务岗位
	public static final String YZTB_POSITION = "yztbPositionList";
    //证件数据资源
    public static final String YZTB_CERT_RESOURCE = "yztbCertResourceList";
    
    public static final String YZTB_VICHEL_DISTRICT = "yztbVichelDistrictList";
    
    public static final String YZTB_PCS_DISTRICT = "yztbPcsDistrictList";
    
    public static final String YZTB_DICTIONARIES = "yztbdictionariesList";
    
    public static final String YZTB_DICTIONARY = "yztbdictionaryList";
    //表单
    public static final String YZTB_FORM_PAGE = "yztbFormPageList";
    //area-org
    public static final String YZTB_AREA_ORG = "yztbAreaOrg";

    //密钥列表
    public static final String YZTB_SECRET = "yztbSecretList";
    //事项主题分类
    public static final String YZTB_ITEM_THEME = "yztbItemThemeList";
    //事项基本码
    public static final String YZTB_BASE_ITEM = "yztbBaseItemList";
    //事项主题分类keys
    public static final String YZTB_ITEM_THEME_KEYS = "yztbItemThemeKeys";
    //token
    public static final String YZTB_TOKEN = "yztbToken";
    //token锁
    public static final String YZTB_TOKEN_LOCK = "yztbTokenLock";
    public static final String YZTB_TOKEN_READ_COUNT = "yztbTokenReadCount";

    public static final String SECURITY_CODE = "securityCode";
    //数据统计
    public static final String YZTB_STATISTICS  = "yztbStatistics";					//统计的最外层Key
    public static final String DEPT_ITEM_COUNT_BYDAY = "deptItemCountByDay";		//饼图：统计本周内事项办件的分布图
    public static final String WEEK_ITEM_COUNT_CHANGE = "weekItemCountChange";		//折线图：统计本周的日期的办件个数
    public static final String MONTH_ITEM_COUNT_CHANGE = "monthItemCountChange";	//折线图：统计本月的日期的办件个数
    public static final String MONTH_DEPT_ITEM_COUNT = "monthDeptItemCount";		//本月部门排名图
    public static final String ALL_DEPT_ITEM_COUNT = "allDeptItemCount";			//全量部门排名图
    public static final String MONTH_CITY_ITEM_COUNT = "monthCityItemCount";		//本月地区办件
    public static final String ALL_CITY_ITEM_COUNT = "allCityItemCount";			//全量地区办件
    public static final String CERNO_COUNT = "cerNoCount";							//办理事项总数
    public static final String TEMP_COUNT = "tempCount";							//办理获取证件总数
    public static final String STATISTIC_STATE = "statisticState";					//统计完成状态
    public static final String STATISTIC_LOCK = "statisticLock";					//统计锁						
    
    /**
     * solr
     */

	//一证通办log库
	public static final String YZTB_SOLR_DB = "yztb_log_db";
	//一证通办log库
	public static final String YZTB_SOLR_TABLE_BUSINESS = "yztbbusinesstable";
	public static final String YZTB_SOLR_TABLE_BUSINESS_ITEM = "yztbbusinessitemtable";
	public static final String YZTB_SOLR_TABLE_SEALLOG = "yztbSealLogTable";
	// 支付缴费单信息
	public static final String PAY_INFO = "pay_info";
	// 支付结果信息
	public static final String PAY_RESULT_INFO = "pay_result_info";
	// 公安 反馈结果信息表
	public static final String QUESTION_FEEDBACK = "question_feedback";
	//表单数据暂存表
    public static final String TEMP_FORM_DATA = "temp_form_data";
	
	//表单中心库
	public static final String FORM_SOLR_DB = "formcenter";
	//表单业务表
	public static final String FORM_INFO = "form_info";
	//表单数据表
	public static final String FORM_DATA = "form_data" ;
	//办件信息
    public static final String PROJECT_INFO = "project_info";
    //缴费信息表
	public static final String PAYMENT = "c_payment";
    //中台回调信息表
    public static final String ALI_CALLBACK_INFO = "ali_callback_info";
    //办件生命周期
    public static final String PROJECT_LIFECYCLE = "project_lifecycle";
    //公安请求钧信平台日志
    public static final String REQUEST_LOG = "request_log";
    //公安请求钧信平台日志
    public static final String PROJECT_CONFIRM = "project_confirm";
    // 办件回调错误信息日志
    public static final String PROJECT_ERRORINFO = "project_error_info";
    // 支付生命周期
    public static final String PAY_LIFECYCLE = "pay_lifecycle";
    //消息推送
    public static final String SEND_MSG_INFO = "send_msg_info";
}
