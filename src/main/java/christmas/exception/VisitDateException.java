package christmas.exception;

public class VisitDateException extends IllegalArgumentException {
    public enum ErrorMessage {
        INCORRECT_DATE_RANGE("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.\n");
        private final String message;

        ErrorMessage(final String message) {
            this.message = message;
        }

        public String getMessages() {
            return message;
        }
    }

    public VisitDateException() {
        super(ErrorMessage.INCORRECT_DATE_RANGE.getMessages());
    }
}
