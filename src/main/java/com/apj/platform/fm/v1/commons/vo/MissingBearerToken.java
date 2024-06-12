package com.apj.platform.fm.v1.commons.vo;

import com.apj.platform.fm.v1.commons.constants.FmErrorCodes;

public class MissingBearerToken extends SystemRuntimeException {

    public MissingBearerToken(String message) {
        super(message);
    }

    @Override
    public String getErrorcode() {
        return FmErrorCodes.ERR_BEARER_TOKEN_MISSING;
    }

}
