package com.fline.form.action;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feixian.aip.platform.common.action.AbstractAction;
import com.feixian.aip.platform.model.type.IdentityUser;
import com.feixian.tp.common.encrypt.PasswordEncoder;
import com.feixian.tp.common.util.Detect;
import com.feixian.tp.common.vo.Ordering;
import com.feixian.tp.common.vo.Pagination;
import com.fline.form.access.model.Role;
import com.fline.form.access.model.User;
import com.fline.form.mgmt.service.RoleMgmtService;
import com.fline.form.mgmt.service.UserMgmtService;
import com.fline.form.mgmt.service.UserSessionManagementService;
import com.fline.form.util.FileUtil;
import com.fline.form.util.HttpClientUtil;
import com.fline.form.util.HttpRequest;
import com.fline.form.util.MD5Util;
import com.itextpdf.text.pdf.codec.Base64;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends AbstractAction implements ModelDriven<User> {

	private static final long serialVersionUID = -2109275587732602187L;

	private UserMgmtService userMgmtService;
	
	private PasswordEncoder passwordEncoder;

	private UserSessionManagementService userSessionManagementService;

	private RoleMgmtService roleMgmtService;

	private Map<String, Object> dataMap = new HashMap<String, Object>();

	private List<User> userList;

	private int pageNum;

	private int pageSize;

	private User user;

	private String oldPass;

	private String newPass;

	private String department;

	private String position;

	private String positionOr;

	private long roleId;

	private long[] ids;
	// add cert by smj
	private String authCode;
	
	private String certInfo;
	
	private String signature;

	private String formBusiCode;
	private String situations;
	private String formInfo;
	private String attrInfo;
	private String postInfo;

	public void setFormBusiCode(String formBusiCode) {
		this.formBusiCode = formBusiCode;
	}

	public void setSituations(String situations) {
		this.situations = situations;
	}

	public void setFormInfo(String formInfo) {
		this.formInfo = formInfo;
	}

	public void setAttrInfo(String attrInfo) {
		this.attrInfo = attrInfo;
	}

	public void setPostInfo(String postInfo) {
		this.postInfo = postInfo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getCertInfo() {
		return certInfo;
	}

	public void setCertInfo(String certInfo) {
		this.certInfo = certInfo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionOr() {
		return positionOr;
	}

	public void setPositionOr(String positionOr) {
		this.positionOr = positionOr;
	}

	public void setUserMgmtService(UserMgmtService userMgmtService) {
		this.userMgmtService = userMgmtService;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserSessionManagementService(
			UserSessionManagementService userSessionManagementService) {
		this.userSessionManagementService = userSessionManagementService;
	}

	public void setRoleMgmtService(RoleMgmtService roleMgmtService) {
		this.roleMgmtService = roleMgmtService;
	}

	public RoleMgmtService getRoleMgmtService() {
		return roleMgmtService;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/** 新增 */
	public String save() {
		try {
			IdentityUser operator = userSessionManagementService.findByContext();
			user.setPassword(passwordEncoder.encodePassword("123456", null));
			user.setCreator(String.valueOf(operator.getId()));
			user.setActive(true);
			userMgmtService.create(user,roleId);
			dataMap.put("msg", "新增成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "新增失败!");
		}
		return SUCCESS;
	}

	/** 修改 */
	public String update() {
		try {
			IdentityUser operator = userSessionManagementService.findByContext();
			user.setUpdater(String.valueOf(operator.getId()));
			userMgmtService.update(user);
			dataMap.put("msg", "修改成功!");
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("msg", "修改失败!");
		}
		return SUCCESS;
	}

	/** 删除 */
	public String remove() {
		userMgmtService.remove(user);
		return SUCCESS;
	}

	/** checkUserName */
	public String checkUserName() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", user.getUsername());
		userList = userMgmtService.find(param);
		if (userList == null) {
			dataMap.put("valid", true);
		} else {
			if (user.getId() == userList.get(0).getId()) {
				dataMap.put("valid", true);
			} else {
				dataMap.put("valid", false);
			}
		}
		return SUCCESS;
	}

	/** 用户激活 */
	public String active() {
		userMgmtService.authorize(user);
		return SUCCESS;
	}

	/** 密码重置 */
	public String passWordReset() {
		String password = passwordEncoder.encodePassword("123456", null);
		userMgmtService.passWordReset(String.valueOf(user.getId()), password);
		return SUCCESS;
	}

	/** 修改密码 */
	public String updatePass() {
		try {
			String password = passwordEncoder.encodePassword(newPass, null);
			userMgmtService.passWordReset(String.valueOf(user.getId()), password);
			dataMap.put("msg", "修改成功!");
		} catch (Exception e) {
			dataMap.put("msg", "修改失败请重试!");
		}
		return SUCCESS;
	}

	/** 修改密码 */
	public String checkOldPass() {
		User u = userMgmtService.findById(user.getId());
		if (u.getPassword().equals(oldPass)
				|| u.getPassword().equals(
						passwordEncoder.encodePassword(oldPass, null))) {
			dataMap.put("valid", true);
		} else {
			dataMap.put("valid", false);
		}
		return SUCCESS;
	}

	/** 分配角色 */
	public String saveUserRole() {
		userMgmtService.assignRole(user.getId(), ids);
		return SUCCESS;
	}


	/**
	 * 解绑用户
	 * */
	public String unBind() {
		userMgmtService.updateCertNo(user.getId(), "");
		return SUCCESS;
	}

	public String findById() {
		User user1 = userMgmtService.findById(user.getId());
		dataMap.put("user", user1);
		return SUCCESS;
	}

	public String findPage() {
		Pagination<User> page = new Pagination<User>();
		page.setCounted(true);
		page.setIndex(pageNum);
		page.setSize(pageSize);

		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(user.getUsername()))
			param.put("usernameLike", user.getUsername());
		if (StringUtils.isNotBlank(user.getName()))
			param.put("nameLike", user.getName());
		if (StringUtils.isNotBlank(user.getCertNo()))
			param.put("certNo", user.getCertNo());
		if (StringUtils.isNotBlank(department))
			param.put("department", department);
		Ordering order = new Ordering();
		order.addDesc("ID");
		Pagination<User> pageData = userMgmtService.findPagination(param,
				order, page);
		dataMap.put("total",pageData.getCount());
		dataMap.put("rows",pageData.getItems());
		return SUCCESS;
	}
	

	public String findUserRole() {
		List<Role> rolelist = roleMgmtService.findByUserRole(user.getId());
		dataMap.put("roles", rolelist);
		return SUCCESS;
	}

	public String findByRoleId() {
		List<User> users = userMgmtService.findByRoleId(roleId);
		dataMap.put("datas", users);
		return SUCCESS;
	}

	public String findAll() {
		dataMap.put("datas", userMgmtService.findAll());
		return SUCCESS;
	}

	public String findList() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(user.getName())) {
			param.put("nameLike", user.getName());
		}
		if (StringUtils.isNotBlank(position)) {
			param.put("position", position);
		}
		if (StringUtils.isNotBlank(positionOr)) {
			param.put("positionOr", positionOr);
		}
		dataMap.put("datas", userMgmtService.findList(param));
		return SUCCESS;
	}

	/**
	 * 根据部门分组
	 * 
	 * @return
	 */
/*	public String getTree() {
		IdentityUser iuser = userSessionManagementService.findByContext();
		List<User> users = userMgmtService.findAllByUser(iuser.getId());
		Map<Long, TreeNode> map = new HashMap<Long, TreeNode>();
		for (User user : users) {
			TreeNode node = new TreeNode();
			node.setName(user.getName());
			node.setId(user.getId());
			long depId = user.getOrgId();
			if (map.containsKey(depId)) {
				map.get(depId).getChildren().add(node);
			} else {
				TreeNode depNode = new TreeNode();
				depNode.setName(depId == 0 ? "尚未分配部门" : user.getOrgName());
				depNode.setChildren(new ArrayList<TreeNode>());
				depNode.getChildren().add(node);
				map.put(depId, depNode);
			}
		}
		dataMap.put("nodes", map.values());
		return SUCCESS;
	}*/

	/** 分配岗位 */
	public String assignPosition() {
		if (Detect.notEmpty(position)) {
			userMgmtService.removePosition(Long.valueOf(position));
			if (Detect.notEmpty(ids)) {
				userMgmtService.assignPosition(Long.valueOf(position), ids);
			}
		}
		return SUCCESS;
	}

	public String findUserPower() {
		IdentityUser curUser = userSessionManagementService.findByContext();//获取当前的用户
		//获取当前用户的角色
		List<Role> rolelist = roleMgmtService.findByUserRole(curUser.getId());
		dataMap.put("power", "read");
		for(Role role:rolelist) {
			if(role.getLevel() == 0) {
				dataMap.put("power", "edit");
				break;
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String findByBusiCode() {
		String result = HttpRequest.sendGet(
				"http://172.31.84.188:18087/form-yinchuan/rest/item/situation/"
						+ formBusiCode, "");
		System.out.println(result);
		Map<String, Object> returnMap = (Map<String, Object>) JSONObject.parse(result);
		Map<String, Object> datasMap = (Map<String, Object>) returnMap.get("data");
		List<Map<String, Object>> situations = (List<Map<String, Object>>) datasMap
				.get("situations");
		result = HttpRequest.sendGet(
				"http://172.31.84.188:18087/form-yinchuan/rest/form/loadData/"
						+ formBusiCode, "");
		returnMap = (Map<String, Object>) JSONObject.parse(result);
		datasMap = (Map<String, Object>) returnMap.get("data");
		Map<String, Object> personMap = (Map<String, Object>) datasMap.get("personInfo");
		String itemCode = personMap.get("ITEM_CODE") + "";
		//调用事项库 取事项信息
		String itemInfo = HttpRequest.sendGet("http://192.168.0.110:9090/item/api/info/getItemInfo", formBusiCode);
		Map<String, Object> returnItem = (Map<String, Object>) JSONObject.parse(itemInfo);
		Map<String, Object> datasItem = (Map<String, Object>) returnItem.get("data");
		String id = datasItem.get("id").toString();
		Map<String, Object> item = new HashMap<>();
		item.put("sxmc", datasItem.get("taskName").toString());
		item.put("lx", datasItem.get("projectType").toString());
		item.put("bljg", datasItem.get("deptName").toString());
		item.put("bjsx", datasItem.get("anticipateDay").toString()+"个"+datasItem.get("anticipateType").toString());
		item.put("sfsf", datasItem.get("isFee").toString());
		item.put("zxdh", datasItem.get("linkTel").toString());
		item.put("sxid", itemCode);
		String idnum = personMap.get("APPLY_CARD_NUMBER") + "";
		String bcardNO = idnum;
		String bType = "1";
		String apId = "yztb";
		String secretKey = "fgdWrkYZJeFFbmMn";
		String mac = MD5Util.MD5(bcardNO + bType + apId + secretKey).toLowerCase();
		Map<String, Object> param = new HashMap<>();
		param.put("bcardNO", bcardNO);
		param.put("bType", bType);
		param.put("apId", apId);
		param.put("mac", mac);
		String json = JSON.toJSONString(param);
		JSONObject jsonObject = JSON.parseObject(json);
		Map<String, Object> userIf = new HashMap<>();
		userIf.put("idnum", idnum);
		try {
			//地址要改为内网地址
//			result = HttpRequest.sendPost("http://smdt.yinchuan.gov.cn/citizen/user/getUserByUniqueCode", jsonObject);
			result = HttpRequest.sendPost("http://172.31.84.188:83/smdt-yinchuan/citizen/user/getUserByUniqueCode", jsonObject);//映射的ip
			returnMap = (Map<String, Object>) JSONObject.parse(result);
			String base64 = returnMap.get("data") + "";
			result = new String(Base64.decode(base64));
			returnMap = (Map<String, Object>) JSONObject.parse(result);
			userIf.put("trueName", returnMap.get("trueName"));
			userIf.put("phone", returnMap.get("phone"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String noice = null;
		String appSecret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPzEOJnzqSkFPwKb/lM8uTm6bVty33XOg+vG77zXFh2a3bOo560Yo6FjwaNEjz5yXZ7Y3iaiL2H+E6R+ui/lm9jtC+zcciZ/eMVjb63obR128rXa0dG29QuKyvYogg0gN02wIwUJ+LVGd0WHbP6aQHjcvxp9z6Utan5cIjJweu0QIDAQAB";
		String sign = MD5Util.MD5(idnum + "_" + noice + "_" + appSecret);
		Map<String, String> map = new HashMap<>(8);
		map.put("appId", "test_app_1");
		map.put("idnum", idnum);
		map.put("noice", noice);
		map.put("sign", sign);
		map.put("areaCode", "330683");
		dataMap.put("userIf", userIf);
		dataMap.put("item", item);
		dataMap.put("situations", situations);
		dataMap.put("form", "zj_sx_sz_cer_1068");
		dataMap.put("ybtx", map);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String findTempInfoAll() {
		System.out.println(formBusiCode);
		System.out.println(situations);
		String result = HttpRequest.sendGet(
				"http://172.31.84.188:18087/form-yinchuan/rest/form/loadData/"
						+ formBusiCode, "");
		System.out.println(result);
		Map<String, Object> returnMap = (Map<String, Object>) JSONObject.parse(result);
		Map<String, Object> datasMap = (Map<String, Object>) returnMap.get("data");
		Map<String, Object> personMap = (Map<String, Object>) datasMap.get("personInfo");
		String idnum = personMap.get("APPLY_CARD_NUMBER") + "";
		System.out.println(idnum);
		result = HttpRequest.sendPostRequest(
				"http://172.31.84.188:18087/form-yinchuan/rest/data/certificateData/"
						+ formBusiCode, "situationCode=" + situations);
		System.out.println(result);
		returnMap.clear();
		returnMap = (Map<String, Object>) JSONObject.parse(result);
		if ("0".equals(returnMap.get("returnCode") + "")) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) returnMap
					.get("data");
			if (list != null) {
				for (Map<String, Object> map : list) {
					map.put("certName", map.get("materialName"));
					if ("c2869970-cdbd-4cf0-a91b-558fafcf472f".equals(map
							.get("materialCode"))) {
						result = HttpClientUtil.getYztb(idnum);
						Map<String, Object> resultMap = (Map<String, Object>) JSONObject
								.parse(result);
						//returnMap.get("certFile");
						//returnMap.get("certPic");
						if (resultMap.get("certPic") != null
								&& !"".equals(resultMap.get("certPic"))) {
							map.put("certFile", resultMap.get("certPic"));
							map.put("certName", "身份证");
							map.put("code", 1);
							map.put("msg", "成功");
						}
					} else if ("96664f80-696f-4261-bbc8-582839ef71fb".equals(map
							.get("materialCode"))) {
						String filePath = System.getProperty("user.dir") + File.separator
								+ "download" + File.separator + idnum + ".jpg";
						System.out.println(filePath);
						try {
							String base64 = FileUtil.encodeBase64File(filePath);
							if (base64 != null) {
								map.put("certFile", base64);
								map.put("certName", "申请表");
								map.put("code", 1);
								map.put("msg", "成功");
								map.put("certCode", "zj_sx_sz_cer_1068");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
//						String base64 = HttpClientUtil.sendGetYbtx("zj_sx_sz_cer_1068", idnum);
//						if (base64 != null) {
//							map.put("certFile", base64);
//							map.put("code", 1);
//							map.put("msg", "成功");
//							map.put("certCode", "zj_sx_sz_cer_1068");
//						}
					}
				}
				Collections.sort(list, new Comparator<Map<String, Object>>() {
					@Override
					public int compare(Map<String, Object> result1,
							Map<String, Object> result2) {
						return -Integer.compare(
								Integer.parseInt(result1.get("code") + ""),
								Integer.parseInt(result2.get("code") + ""));
					}
				});
				dataMap.put("returnCode", 0);
				dataMap.put("data", list);
			}
		}
		return SUCCESS;
	}

	public String saveFormData() {
		Map<String, Object> dataParam = new HashMap<>();
		dataParam.put("formInfo", formInfo);
		dataParam.put("attrInfo", attrInfo);
		dataParam.put("postInfo", postInfo);
		String json = JSON.toJSONString(dataParam);
		JSONObject jsonObject = JSON.parseObject(json);
		String result = HttpRequest.sendPost(
				"http://172.31.84.188:18087/form-yinchuan/rest/form/submitFormData/"
						+ formBusiCode, jsonObject);
		System.out.println(result);
		dataMap.put("data", result);
		return SUCCESS;
	}

	@Override
	public User getModel() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

}
