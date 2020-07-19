package com.fline.form.access.service;

import com.feixian.aip.platform.access.common.service.AbstractNamespaceAccessService;
import com.fline.form.access.model.DataElement;

public interface DataElementAccessService extends AbstractNamespaceAccessService<DataElement> {

    void removeByFormCode(String formCode);
}
