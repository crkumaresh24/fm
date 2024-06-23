package com.apj.platform.fm.v1.constants;

public interface FmErrorCodes {
    String ERR_INPUT_VALIDATION = "exception.input.validation";
    String ERR_BAD_CREDENTIALS = "exception.input.credentials.invalid";
    String ERR_MULTIPART_NOTFOUND = "exception.multipart.invalid";
    String ERR_BEARER_TOKEN_MISSING = "exception.token.missing";


    String ERR_FILE_NOT_FOUND = "exception.file.notfound";
    String ERR_UPLOAD_FAILED = "exception.file.uploadFailed";
    String ERR_DELETE_FAILED = "exception.file.deleteFailed";
}
