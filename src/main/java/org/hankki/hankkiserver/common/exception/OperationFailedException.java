package org.hankki.hankkiserver.common.exception;

import org.hankki.hankkiserver.common.code.ErrorCode;

public class OperationFailedException extends BusinessException {
  public OperationFailedException() {
    super(ErrorCode.INTERNAL_SERVER_ERROR);
  }

  public OperationFailedException(ErrorCode errorCode) {
    super(errorCode);
  }
}

