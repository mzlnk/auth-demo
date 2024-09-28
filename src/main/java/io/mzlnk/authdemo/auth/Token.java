package io.mzlnk.authdemo.auth;

public record Token(String value) {

    @Override
    public String toString() {
        return value;
    }
}
