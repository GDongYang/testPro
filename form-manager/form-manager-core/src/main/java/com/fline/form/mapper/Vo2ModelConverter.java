package com.fline.form.mapper;

import com.fline.yztb.access.model.FormProperty;
import com.fline.yztb.vo.FormPropertyVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Vo2ModelConverter {

    Vo2ModelConverter INSTANCE = Mappers.getMapper(Vo2ModelConverter.class);

    FormProperty toModel(FormPropertyVo formPropertyVo);
}
