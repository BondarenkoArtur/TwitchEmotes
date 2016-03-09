package com.uabart.twitchemotes;

import java.util.HashMap;
import java.util.Map;

public class TwitchEmotesGlobal {

    public static final String JSON_URL = "https://twitchemotes.com/api_cache/v2/global.json";

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