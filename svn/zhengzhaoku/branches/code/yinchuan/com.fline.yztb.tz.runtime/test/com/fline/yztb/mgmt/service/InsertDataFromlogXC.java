//package com.fline.yztb.mgmt.service;
//
//import com.fline.yztb.access.model.SealUncovered;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.InputStream;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//public class InsertDataFromlogXC {
//
//    @Autowired
//    private SealUncoveredMgmtService sealUncoveredMgmtService;
//
//    @Test
//    public void dataInsert() throws ParseException {
//        List<String>stringData=readStringFromtxt("D:\\workspace\\TZTBCOME\\com.fline.yztb.tz.runtime\\result.txt");
//        //将数据拼接为List<SealUncovered>格式
//        List<SealUncovered>finalDatas=formData(stringData);
//        List<SealUncovered>data_insert=new ArrayList<>();
//        for (int i = 0; i < finalDatas.size(); i++) {
//            //未处理
//            SealUncovered sealUncovered=finalDatas.get(i);
//            if(sealUncovered.flag.equals("INSERT")){
//                for (int i1 = 0; i1 < finalDatas.size(); i1++) {
//                    SealUncovered sealUncovered1=finalDatas.get(i1);
//                    if(sealUncovered1.flag.equals("UPDATE")){
//                    //获取判断依据 CREATE_DATE,slfId,cert,BUSINESS,DEPARTMENTID
//                        Date date1=sealUncovered.getCreateDate();
//                        Date date2=sealUncovered1.getCreateDate();
//                        String slfId1=sealUncovered.getSfid();
//                        String slfId2=sealUncovered1.getSfid();
//                        String cert1=sealUncovered.getCert();
//                        String cert2=sealUncovered1.getCert();
//                        String business1=sealUncovered.getBusiness();
//                        String business2=sealUncovered1.getBusiness();
//                        String depart_id1=sealUncovered.getDepartmentId()+"";
//                        String depart_id2=sealUncovered1.getDepartmentId()+"";
//                        long time1=date1.getTime();
//                        long time2=date2.getTime();
//                        if(time1==time2&&slfId1.equals(slfId2)&&cert1.equals(cert2)&&business1.equals(business2)&&depart_id1.equals(depart_id2)){
//                            sealUncovered.setId(sealUncovered1.getId());
//                            sealUncovered.find=true;
//                            try {
//                                sealUncoveredMgmtService.create(sealUncovered);
//                            }catch (Exception e){
//                            }
//                            break;
//                        }
//
//                    }
//                }
//                if(!sealUncovered.find)
//                    data_insert.add(sealUncovered);
//            }
//
//        }
//        //将数据插入
//        for (int i = 0; i < data_insert.size(); i++) {
//            sealUncoveredMgmtService.create(data_insert.get(i));
//        }
//
//        for (int i = 0; i < finalDatas.size(); i++) {
//            //待盖章   0
//            SealUncovered sealUncovered=finalDatas.get(i);
//            long Status=sealUncovered.getStatus();
//            String flag=sealUncovered.flag;
//            if(flag.equals("UPDATE")&&Status==0){
//                try {
//                sealUncoveredMgmtService.update(sealUncovered);
//                }catch (Exception e){
//                    sealUncoveredMgmtService.create(sealUncovered);
//                }
//            }
//        }
//        for (int i = 0; i < finalDatas.size(); i++) {
//            //已盖章   1
//            SealUncovered sealUncovered=finalDatas.get(i);
//            long Status=sealUncovered.getStatus();
//            String flag=sealUncovered.flag;
//            if(flag.equals("UPDATE")&&Status==1){
//                try {
//                    sealUncoveredMgmtService.update(sealUncovered);
//                }catch (Exception e){
//                    sealUncoveredMgmtService.create(sealUncovered);
//                }
//            }
//        }
//        for (int i = 0; i < finalDatas.size(); i++) {
//            //已办结   5
//            SealUncovered sealUncovered=finalDatas.get(i);
//            long Status=sealUncovered.getStatus();
//            String flag=sealUncovered.flag;
//            if(flag.equals("UPDATE")&&Status==5){
//                try {
//                    sealUncoveredMgmtService.update(sealUncovered);
//                }catch (Exception e){
//                    sealUncoveredMgmtService.create(sealUncovered);
//                }
//            }
//        }
//        System.out.println("aa");
//    }
//
//    private List<SealUncovered> formData(List<String> stringData) throws ParseException {
//        List<SealUncovered>finalDatas=new ArrayList<>();
//        for (int i = 0; i < stringData.size(); i=i+2) {
//           String line=stringData.get(i);
//           if(line.contains("INSERT INTO C_SEAL_UNCOVERED")){
//            String word_toInsert=stringData.get(i+1);
//               String result1=word_toInsert.substring(word_toInsert.indexOf("Parameters: [")+13,word_toInsert.length()-1);
//               String[] resarr=result1.split(",");
//               SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:S");
//               String ID=resarr[0];
//               String DEPARTMENTID=resarr[1];
//               String APPLY_DEPT=resarr[2];
//               String CERT=resarr[3];
//               String BUSINESS=resarr[4];
//               String STATUS=resarr[5];
//               String certCode=resarr[6];
//               String sfId=resarr[7];
//               String busiCode=resarr[8];
//               String CERT_TYPE=resarr[9];
//               String CREATE_DATE=resarr[10];
//               String SIGN_DATE=resarr[11];
//               String NAME=resarr[12];
//               String memo=resarr[13];
//               //memo get start
//               int i_index_min=13;
//               int i_offset=0;
//               while (true) {
//                   try {
//                       String EXCUTE_DATE_str=resarr[i_index_min+2+i_offset];
//                        if(EXCUTE_DATE_str.trim().equals("null")){
//                            break;
//                        }else {
//                            Date date = sdf.parse(EXCUTE_DATE_str);
//                        }
//                       break;
//                   } catch (ParseException e) {
//                       System.out.println("having offset adjust "+(++i_offset));
//                       memo=memo+resarr[i_index_min+i_offset];
//                   }
//               }
//               //memo get end
//               String INQUIRED_NAME=resarr[14+i_offset];
//               String EXCUTE_DATE=resarr[15+i_offset];
//               String KEY=resarr[16+i_offset];
//               String SEAL_PERSON=resarr[17+i_offset];
//               String COMPANY_NAME=resarr[18+i_offset];
//               String COMPANY_CODE=resarr[19+i_offset];
//               String BUSINESS_TYPE=resarr[20+i_offset];
//               String UPLOAD_PERSON=resarr[21+i_offset];
//               String APPLY_PERSON_ID=resarr[22+i_offset];
//               String SEAL_PERSON_ID=resarr[23+i_offset];
//               String is_contain_file_attach=resarr[24+i_offset];
//
//               SealUncovered sealUncovered=new SealUncovered();
//               String flag="INSERT";
//               StrToObj(sdf, ID, DEPARTMENTID, APPLY_DEPT, CERT, BUSINESS, STATUS, certCode, sfId, busiCode, CERT_TYPE, CREATE_DATE, SIGN_DATE, NAME, memo, INQUIRED_NAME, EXCUTE_DATE, KEY, SEAL_PERSON, COMPANY_NAME, COMPANY_CODE, BUSINESS_TYPE, UPLOAD_PERSON, APPLY_PERSON_ID, SEAL_PERSON_ID, is_contain_file_attach, sealUncovered,flag);
//               finalDatas.add(sealUncovered);
//
//           }else if(line.contains("UPDATE C_SEAL_UNCOVERED SET")){
//            String word_toUpdate=stringData.get(i+1);
//            String result1=word_toUpdate.substring(word_toUpdate.indexOf("Parameters: [")+13,word_toUpdate.length()-1);
//            String[] resarr=result1.split(",");
//               SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:S");
//               String DEPARTMENTID=resarr[0];
//               String APPLY_DEPT=resarr[1];
//               String CERT=resarr[2];
//               String BUSINESS=resarr[3];
//               String STATUS=resarr[4];
//               String certCode=resarr[5];
//               String sfId=resarr[6];
//               String busiCode=resarr[7];
//               String CERT_TYPE=resarr[8];
//               String CREATE_DATE=resarr[9];
//               String SIGN_DATE=resarr[10];
//               String NAME=resarr[11];
//               String memo=resarr[12];
//               //memo get start
//               int i_index_min=12;
//               int i_offset=0;
//               while (true) {
//                   try {
//                       Date date = sdf.parse(resarr[i_index_min+2+i_offset]);
//                       break;
//                   } catch (ParseException e) {
//                       System.out.println("having offset adjust "+(++i_offset));
//                       memo=memo+resarr[i_index_min+i_offset];
//                   }
//               }
//               //memo get end
//               String INQUIRED_NAME=resarr[13+i_offset];
//               String EXCUTE_DATE=resarr[14+i_offset];
//               String KEY=resarr[15+i_offset];
//               String SEAL_PERSON=resarr[16+i_offset];
//               String COMPANY_NAME=resarr[17+i_offset];
//               String COMPANY_CODE=resarr[18+i_offset];
//               String BUSINESS_TYPE=resarr[19+i_offset];
//               String UPLOAD_PERSON=resarr[20+i_offset];
//               String APPLY_PERSON_ID=resarr[21+i_offset];
//               String SEAL_PERSON_ID=resarr[22+i_offset];
//               String is_contain_file_attach=resarr[23+i_offset];
//               String ID=resarr[24+i_offset];
//               SealUncovered sealUncovered=new SealUncovered();
//               String flag="UPDATE";
//               StrToObj(sdf, ID, DEPARTMENTID, APPLY_DEPT, CERT, BUSINESS, STATUS, certCode, sfId, busiCode, CERT_TYPE, CREATE_DATE, SIGN_DATE, NAME, memo, INQUIRED_NAME, EXCUTE_DATE, KEY, SEAL_PERSON, COMPANY_NAME, COMPANY_CODE, BUSINESS_TYPE, UPLOAD_PERSON, APPLY_PERSON_ID, SEAL_PERSON_ID, is_contain_file_attach, sealUncovered,flag);
//               finalDatas.add(sealUncovered);
//           }
//        }
//
//        return finalDatas;
//    }
//
//    private void StrToObj(SimpleDateFormat sdf, String ID, String DEPARTMENTID, String APPLY_DEPT, String CERT, String BUSINESS, String STATUS, String certCode, String sfId, String busiCode, String CERT_TYPE, String CREATE_DATE, String SIGN_DATE, String NAME, String memo, String INQUIRED_NAME, String EXCUTE_DATE, String KEY, String SEAL_PERSON, String COMPANY_NAME, String COMPANY_CODE, String BUSINESS_TYPE, String UPLOAD_PERSON, String APPLY_PERSON_ID, String SEAL_PERSON_ID, String is_contain_file_attach, SealUncovered sealUncovered,String flag) throws ParseException {
//        sealUncovered.flag=flag;
//        sealUncovered.setId(Long.parseLong(ID.trim()));
//        sealUncovered.setDepartmentId(Long.parseLong(DEPARTMENTID.trim()));
//        sealUncovered.setApplyDept(APPLY_DEPT);
//        sealUncovered.setCert(CERT);
//        sealUncovered.setBusiness(BUSINESS);
//        sealUncovered.setStatus(Long.parseLong(STATUS.trim()));
//        sealUncovered.setCertcode(certCode);
//        sealUncovered.setSfid(sfId);
//        sealUncovered.setBusicode(busiCode);
//        sealUncovered.setCertType(Long.parseLong(CERT_TYPE.trim()));
//        sealUncovered.setCreateDate(CREATE_DATE.trim().equals("null")==true?null:sdf.parse(CREATE_DATE));
//        sealUncovered.setSignDate(SIGN_DATE.trim().equals("null")==true?null:sdf.parse(SIGN_DATE));
//        sealUncovered.setName(NAME);
//        sealUncovered.setMemo(memo);
//        sealUncovered.setInquiredName(INQUIRED_NAME);
//        sealUncovered.setExcuteDate(EXCUTE_DATE.trim().equals("null")==true?null:sdf.parse(EXCUTE_DATE));
//        sealUncovered.setKey(KEY);
//        sealUncovered.setSealPerson(SEAL_PERSON);
//        sealUncovered.setCompanyName(COMPANY_NAME);
//        sealUncovered.setCompanyCode(COMPANY_CODE);
//        sealUncovered.setBusinessType(Long.parseLong(BUSINESS_TYPE.trim()));
//        sealUncovered.setUploadPerson(UPLOAD_PERSON);
//        sealUncovered.setApplyPersonId(Long.parseLong(APPLY_PERSON_ID.trim()));
//        sealUncovered.setSealPersonId(SEAL_PERSON_ID.trim().equals("null")==true?null:Long.parseLong(SEAL_PERSON_ID.trim()));
//        sealUncovered.setIsContainFileAttach(Integer.parseInt(is_contain_file_attach.trim()));
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
//
//
//}
