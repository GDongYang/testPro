//package com.fline.yztb.mgmt.service;
//
//import com.fline.yztb.access.model.Business;
//import com.fline.yztb.access.model.BusinessItem;
//import com.fline.yztb.access.service.BusinessAccessService;
//import com.fline.yztb.util.BusinessCollections;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//public class InsertDataFromlogGX {
//    @Autowired
//    private BusinessMgmtService businessMgmtService;
//
//    @Autowired
//    private BusinessItemMgmtService businessItemMgmtService;
//
//    @Test
//    public void dataInsert()  {
//        List<String> stringData=readStringFromtxt("D:\\workspace\\TZTBCOME\\com.fline.yztb.tz.runtime\\10.48.133.12.out");
//        List<BusinessCollections>businessCollections=new ArrayList<>();
//        //将属于同一组的businessCollections中的数据进行拼接起来
//        businessCollections=formDatas(stringData);
//        for (int i = 0; i < businessCollections.size(); i++) {
//            BusinessCollections businessCollectionsEntity=businessCollections.get(i);
//            Business business=businessCollectionsEntity.getBusiness();
//            businessMgmtService.saveforTest(business);
//            businessCollectionsEntity.updateBusinessId();
//            List<BusinessItem> businessItems=businessCollectionsEntity.getBusinessItems();
//            for (int i1 = 0; i1 < businessItems.size(); i1++) {
//                BusinessItem businessItem=businessItems.get(i1);
//                businessItemMgmtService.create(businessItem);
//            }
//        }
//        System.out.println("aa");
//    }
//
//    private List<BusinessCollections> formDatas(List<String> stringData)  {
//        List<BusinessCollections>businessCollections=new ArrayList<>();
//        BusinessCollections businessCollectionsEntity=null;
//        for (int i = 0; i < stringData.size(); i++) {
//
//            if(businessCollectionsEntity==null||businessCollectionsEntity.isEmpty())
//                businessCollectionsEntity=new BusinessCollections();
//
//            //第一个遇到的数据为c_business_item,随后遇到的数据到c_business为止算一个BusinessCollections对象
//            if (stringData.get(i).contains("INSERT INTO C_BUSINESS_ITEM")){
//                String businessItemStr="";
//                BusinessItem businessItem=null;
//                while (true){
//                    if(i<stringData.size())
//                        i++;
//                    if(stringData.get(i).contains("Parameters: [")) {
//                        businessItemStr = stringData.get(i);//找到最近的包含Parameters: [的值
//                        try {
//                            businessItem = StrToObj(businessItemStr);//拼接BusinessItem
//                            break;
//                        }catch (Exception e){
//                            //TODO 输出错误日志，忽略这个值并寻找下一个值
//                            System.out.println(e.getMessage()+"\n");
//                            System.out.println("the error string of BusinessItem is\n "+businessItemStr);
//                        }
//                    }
//                }
//
//                businessCollectionsEntity.getBusinessItems().add(businessItem);
//                i++;
//
//
//                while (!stringData.get(i).contains("INSERT INTO C_BUSINESS (     NAME,CODE,MEMO,STATUS")){
//                    if (stringData.get(i).contains("INSERT INTO C_BUSINESS_ITEM")) {
//
//                        while (true){
//                            if(i<stringData.size())
//                                i++;
//                            if(stringData.get(i).contains("Parameters: [")) {
//                                businessItemStr = stringData.get(i);//找到最近的包含Parameters: [的值
//                                try {
//                                    businessItem = StrToObj(businessItemStr);//拼接BusinessItem
//                                    break;
//                                }catch (Exception e){
//                                    //TODO 输出错误日志，忽略这个值并寻找下一个值
//                                    System.out.println(e.getMessage()+"\n");
//                                    System.out.println("the error string of BusinessItem is\n "+businessItemStr);
//                                }
//
//                            }
//                        }
//
//
//
//                        businessCollectionsEntity.getBusinessItems().add(businessItem);
//                    }
//                    i++;
//                }
//                if(stringData.get(i).contains("INSERT INTO C_BUSINESS (     NAME,CODE,MEMO,STATUS")) {
//
//                    String businessStr="";
//                    Business business=null;
//                    while (true){
//                        if(i<stringData.size())
//                            i++;
//                        if(stringData.get(i).contains("Parameters: [")) {
//                            businessStr = stringData.get(i);//找到最近的包含Parameters: [的值
//                            try {
//                                business = StrToObj2(businessStr);//拼接BusinessItem
//                                break;
//                            }catch (Exception e){
//                                //TODO 输出错误日志，忽略这个值并寻找下一个值
//                                System.out.println(e.getMessage()+"\n");
//                                System.out.println("the error string of Business is\n "+businessItemStr);
//                            }
//
//                        }
//                    }
//
//
//                    businessCollectionsEntity.setBusiness(business);
//                    businessCollections.add(businessCollectionsEntity);
//                    businessCollectionsEntity = null;
//                }
//            }
//        }
//
//        return businessCollections;
//    }
//
//    private Business StrToObj2(String businessStr) throws ParseException {
//
//        try {
//        String result1=businessStr.substring(businessStr.indexOf("Parameters: [")+13,businessStr.length()-1);
//        String[] resarr=result1.split(",");
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:S");
//        String NAME=resarr[0].trim();
//        String CODE=resarr[1].trim();
//        String MEMO=resarr[2].trim();
//        //memo get start
//               int i_index_min=2;
//               int i_offset=0;
//               while (true) {
//                   try {
//                       Date date = sdf.parse(resarr[i_index_min+6+i_offset]);
//                       break;
//                   } catch (ParseException e) {
//                       System.out.println("having offset adjust "+(++i_offset));
//                       MEMO=MEMO+resarr[i_index_min+i_offset];
//                   }
//               }
//        //memo get end
//
//        String STATUS=resarr[3+i_offset].trim();
//        String CERNO=resarr[4+i_offset].trim();
//        String ACCOUNT_ID=resarr[5+i_offset].trim();
//        String DEPARTMENT_ID=resarr[6+i_offset].trim();
//        String ITEM_CODE=resarr[7+i_offset].trim();
//        String CREATE_DATE=resarr[8+i_offset].trim();
//        String APPLICANT_UNIT=resarr[9+i_offset].trim();
//        String APPLICANT_USER=resarr[10+i_offset].trim();
//        String PERMISSION_CODE=resarr[11+i_offset].trim();
//        String CER_NAME=resarr[12+i_offset].trim();
//        String type=resarr[13+i_offset].trim();
//        String accessIP=resarr[14+i_offset].trim();
//        String USER_ID=resarr[15+i_offset].trim();
//        String ITEM_INNER_CODE=resarr[16+i_offset].trim();
//        String zhId=resarr[17+i_offset].trim();
//        Business business=new Business();
//        business.setName(NAME);
//        business.setCode(CODE);
//        business.setMemo(MEMO);
//        business.setStatus(Long.parseLong(STATUS.trim()));
//        business.setCerno(CERNO);
//        business.setAccountId(Long.parseLong(ACCOUNT_ID.trim()));
//        business.setDepartmentId(Long.parseLong(DEPARTMENT_ID.trim()));
//        business.setItemCode(ITEM_CODE);
//        business.setCreateDate(CREATE_DATE.trim().equals("null")==true?null:sdf.parse(CREATE_DATE));
//        business.setApplicantUnit(APPLICANT_UNIT);
//        business.setApplicantUser(APPLICANT_USER);
//        business.setPermissionCode(PERMISSION_CODE);
//        business.setCerName(CER_NAME);
//        business.setType(Integer.parseInt(type.trim()));
//        business.setAccessIP(accessIP);
//        business.setUserId(Long.parseLong(USER_ID.trim()));
//        business.setItemInnerCode(ITEM_INNER_CODE);
//        business.setZhId(zhId);
//            return business;
//        }catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }
//
//    private BusinessItem StrToObj(String businessItemStr) throws Exception {
//        try {
//            String result1 = businessItemStr.substring(businessItemStr.indexOf("Parameters: [") + 13, businessItemStr.length() - 1);
//            String[] resarr = result1.split(",");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:S");
//            String NAME = resarr[0].trim();
//            String CODE = resarr[1].trim();
//            String MEMO = resarr[2].trim();
//            //memo get start
//            int i_index_min = 2;
//            int i_offset = 0;
//            while (true) {
//                try {
//                    Date date = sdf.parse(resarr[i_index_min + 5 + i_offset]);
//                    break;
//
//                } catch (ParseException e) {
//                    System.out.println("having offset adjust " + (++i_offset));
//                    MEMO = MEMO + resarr[i_index_min + i_offset];
//                }
//            }
//            //memo get end
//
//            String STATUS = resarr[3 + i_offset].trim();
//            String CERNO = resarr[4 + i_offset].trim();
//            String BUSINESS_ID = resarr[5 + i_offset].trim();
//            String CERT_TEMP_CODE = resarr[6 + i_offset].trim();
//            String CREATE_DATE = resarr[7 + i_offset].trim();
//            String TIME_CONSUMING = resarr[8 + i_offset].trim();
//            BusinessItem businessItem = new BusinessItem();
//            businessItem.setName(NAME);
//            businessItem.setCode(CODE);
//            businessItem.setMemo(MEMO);
//            businessItem.setStatus(Long.parseLong(STATUS.trim()));
//            businessItem.setCerno(CERNO);
//            businessItem.setBusinessId(Long.parseLong(BUSINESS_ID.trim()));
//            businessItem.setCertCode(CERT_TEMP_CODE);
//            businessItem.setCreateDate(CREATE_DATE.trim().equals("null") == true ? null : sdf.parse(CREATE_DATE));
//            businessItem.setTimeConsuming(Long.parseLong(TIME_CONSUMING.trim()));
//            return businessItem;
//        }catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    public static List<String> readStringFromtxt(String txtpath) {
//        File file = new File(txtpath);
//        List<String> data=new ArrayList<>();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String s = null;
//            while ((s = br.readLine()) != null) {
//                data.add(s);
//            }
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//}
