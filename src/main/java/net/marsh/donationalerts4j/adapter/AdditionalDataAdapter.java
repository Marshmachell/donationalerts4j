package net.marsh.donationalerts4j.adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.marsh.donationalerts4j.data.AdditionalData;

import java.io.IOException;

public class AdditionalDataAdapter extends TypeAdapter<AdditionalData> {
    private static final Gson GSON = new Gson();

    @Override
    public AdditionalData read(JsonReader in) throws IOException {
        JsonToken token = in.peek();

        if (token == JsonToken.NULL) {in.nextNull(); return null;}
        if (token == JsonToken.BEGIN_OBJECT) return GSON.fromJson(in, AdditionalData.class);
        if (token == JsonToken.STRING) {String rawJson = in.nextString(); return GSON.fromJson(rawJson, AdditionalData.class);}

        throw new JsonParseException("Unsupported additional_data token: " + token);
    }

    @Override
    public void write(JsonWriter out, AdditionalData value) {GSON.toJson(value, AdditionalData.class, out);}
}
