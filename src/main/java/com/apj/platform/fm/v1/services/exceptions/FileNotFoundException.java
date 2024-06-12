package com.apj.platform.fm.v1.services.exceptions;

import org.springframework.http.HttpStatus;

import com.apj.platform.fm.v1.commons.constants.FmErrorCodes;
import com.apj.platform.fm.v1.commons.vo.SystemException;

public class FileNotFoundException extends SystemException {

    public FileNotFoundException(Long id) {
        super(String.valueOf(id));
        addToParams(String.valueOf(id));
    }

    @Override
    public String getErrorcode() {
        return FmErrorCodes.ERR_FILE_NOT_FOUND;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
