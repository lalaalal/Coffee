package com.lalaalal.coffee.misc;

public interface TextHider {
    public static final TextHider SHOW_FIRST_CHARACTER = new TextHider() {
        @Override
        public String hide(String original) {
            return original.charAt(0) + "**";
        }
    };
    public static final TextHider HIDE_ALL = new TextHider() {
        @Override
        public String hide(String original) {
            return "****";
        }
    };

    String hide(String original);
}
