package com.uabart.twitchemotes;

import java.util.HashMap;
import java.util.Map;

public class TwitchEmotesGlobal {
    Template template = new Template();
    Map<String, Emotes> emotes = new HashMap<>();
}

class Template {
    String small = "";
    String medium = "";
    String large = "";
}

class Emotes {
    String description = "";
    int image_id = -1;
}