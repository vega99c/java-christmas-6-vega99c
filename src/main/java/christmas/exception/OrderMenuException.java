package christmas.exception;

public class OrderMenuException extends IllegalArgumentException {
    public enum ErrorMessage {
        INVALID_MENU_ORDER("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.\n");
        private final String message;

        ErrorMessage(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public OrderMenuException() {
        super(ErrorMessage.INVALID_MENU_ORDER.getMessage());
    }
}
