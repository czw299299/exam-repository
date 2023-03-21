package org.czw.exammanger.service;

import org.czw.exammanger.util.JsonDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImportTemplateService {
    public JsonDto importTemplate(MultipartFile file);
}
