package com.lalaalal.coffee.misc;

public interface TextHider {
    TextHider SHOW_FIRST_CHARACTER = original -> original.charAt(0) + "**";
    TextHider HIDE_ALL = original -> "****";

    String hide(String original);
}
