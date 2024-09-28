package io.mzlnk.authdemo.user;

public record User(User.Id id) {

    public record Id(String value) {

        @Override
        public String toString() {
            return value;
        }
    }

}
