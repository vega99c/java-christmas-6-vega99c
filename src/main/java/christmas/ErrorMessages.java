package christmas;

public enum ErrorMessages {
    NOT_NUMBER("[ERROR] 메뉴수량과 날짜는 숫자만 입력 가능합니다. 다시 입력해 주세요.\n"),
    INCORRECT_RANGE("[ERROR] 유효하지 않는 날짜입니다. 다시 입력해 주세요.\n"),
    NOT_EXIST_MATCHED_MENU("[ERROR] 존재하지 않는 메뉴가 있습니다. 다시 입력해 주세요.\n"),
    INCORRECT_MENU_ORDER("메뉴 주문 양식이 잘못되었습니다. 다시 입력해주세요.\n");
    private final String errorMessage;

    ErrorMessages(String description) {
        this.errorMessage = description;
    }

    public String getErrorMsg() {
        return errorMessage;
    }
}
