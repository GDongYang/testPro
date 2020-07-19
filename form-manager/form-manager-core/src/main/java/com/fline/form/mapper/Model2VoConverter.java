package com.fline.form.mapper;

import com.fline.form.access.model.*;
import com.fline.form.vo.*;
import com.fline.yztb.access.model.CertResource;
import com.fline.yztb.access.model.FormPage;
import com.fline.yztb.access.model.FormProperty;
import com.fline.yztb.vo.FormPageVo;
import com.fline.yztb.vo.FormPropertyVo;
import com.fline.yztb.vo.ItemVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Model2VoConverter {

    Model2VoConverter INSTANCE = Mappers.getMapper(Model2VoConverter.class);

    @Mapping(source = "departmentName", target = "deptName")
    ItemVo toVo(Item item);

    DepartmentVo toVo(Department department);

    CertTempVo toVo(CertTemp certTemp);

    MaterialVo toVo(Material material);

    SituationVo toVo(Situation situation);

    FormPageVo toVo(FormPage formPage);

    CertResourceVo toVo(CertResource certResource);

    FormPropertyVo toVo(FormProperty formProperty);

    ServiceAccountVo toVo(ServiceAccount serviceAccount);

    PositionVo toVo(Position position);

    SecretVo toVo(Secret secret);

    SealInfoVo toVo(SealInfo sealInfo);

}
