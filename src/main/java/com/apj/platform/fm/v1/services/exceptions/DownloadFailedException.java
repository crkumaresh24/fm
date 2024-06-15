package com.apj.platform.fm.v1.services.exceptions;

import com.apj.platform.fm.v1.commons.constants.FmErrorCodes;
import com.apj.platform.fm.v1.commons.vo.SystemException;

public class DownloadFailedException extends SystemException {

    public DownloadFailedException(String... message) {
        super(message.toString());
        addToParams(message);
    }

    @Override
    public String getErrorcode() {
        return FmErrorCodes.ERR_UPLOAD_FAILED;
    }

}
