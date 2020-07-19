package com.fline.form.mgmt.service;

public interface TempInfoService {

    String createFormByPersonal(String serviceCode, String userId, String sfId, String sfName, String mobile, String applyForm);

    String createFormByLegalMan(String serviceCode, String userId, String sfId, String sfName
            , String mobile, String applyForm, String legalMan, String companyType, String companyAddress
            , String attnName, String attnIDNo, String companyRegNumber);
}
