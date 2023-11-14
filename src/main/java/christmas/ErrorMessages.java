package christmas;

public enum ErrorMessages {
    NOT_NUMBER("[ERROR] 메뉴수량과 날짜는 숫자만 입력 가능합니다. 다시 입력해 주세요.\n"),
    INCORRECT_DATE_RANGE("[ERROR] 유효하지 않는 날짜입니다. 다시 입력해 주세요.\n"),
    INCORRECT_MENU_ORDER("[ERROR] 유효하지 않은 주문입니다. 다시 입력해주세요.\n"),
    NOT_INCLUDE_ORDER_RANGE("[ERROR] 총 주문갯수를 확인하고 다시 입력해주세요.(최소 1개, 최대 20개)\n"),
    NOT_ALLOWED_ONLY_DRINK("[ERROR] 음료만 선택 할 수는 없습니다. 다시 입력해주세요.\n");
    private final String errorMessage;

    ErrorMessages(String description) {
        this.errorMessage = description;
    }

    public String getErrorMsg() {
        return errorMessage;
    }
}
