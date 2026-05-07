package org.example.Enums;

public enum SnakePartCode {


    HEAD(0, "Head"),
    BODY(1, "Body"),
    TAIL(2, "Tail");



    private int code;
    private String snakePart;

    SnakePartCode(int code, String snakePart) {
        this.code = code;
        this.snakePart = snakePart;
    }

    public int getCode() {
        return code;
    }

    public String getSnakePart() {
        return snakePart;
    }

    public static SnakePartCode fromCode(int code) {
        for (SnakePartCode c : values()) {
            if (c.code == code) return c;
        }
        throw new IllegalArgumentException("Invalid code");
    }
}
