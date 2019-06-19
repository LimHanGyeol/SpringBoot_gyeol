package com.example.gyeol_web1.domain;

/**
 * 강의 5-6 내용
 * 예외처리의 중복제거를 위해 만들어진 클래스이다.
 * 예외상황이 발생할경우 이 클래스에 상태를 전달해서 처리한다.
 */
public class Result {
    private boolean valid;

    private String errorMessage;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
