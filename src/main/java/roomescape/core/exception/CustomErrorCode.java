package roomescape.core.exception;

public enum CustomErrorCode {

  INTERNAL_SERVER_ERROR(500, "내부 서버 오류가 발생했습니다.");

  private final int status;
  private final String message;

  CustomErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}