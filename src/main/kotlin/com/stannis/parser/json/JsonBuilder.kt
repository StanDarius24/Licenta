package com.stannis.parser.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.complexStatementTypes.ComplexFinalTranslation
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.parser.fileHandler.Reader


object JsonBuilder {
    fun createJson(primaryBlock: PrimaryBlock): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(primaryBlock)
        return jsonString
    }
    fun createJson(list: List<FunctionDefinition>?): String? {
        var jsonString: String? = null
        if (list != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            list.iterator().forEachRemaining { element ->
                run { jsonString += gson.toJson(element) }
            }
        }
        return jsonString
    }

    fun createComplexJson(list: List<ComplexFinalTranslation>?): String? {
        if (list != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(list)
        }
        return null
    }

    fun decodeComplexObject(path: String): List<*>? {
        val gson = GsonBuilder().create()
        val text = Reader.readFileAsLinesUsingBufferedReader(path)
        return gson.fromJson(text, List::class.java)
    }
}

// public final class MYInterfaceAdapter implements JsonDeserializer<MyInterface>,
// JsonSerializer<MyInterface> {
//  private static final String PROP_NAME = "myClass";
//
//  @Override
//  public MyInterface deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
// context) throws JsonParseException {
//    try {
//      String classPath = json.getAsJsonObject().getAsJsonPrimitive(PROP_NAME).getAsString();
//      Class<MyInterface> cls = (Class<MyInterface>) Class.forName(classPath);
//
//      return (MyInterface) context.deserialize(json, cls);
//    } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//
//    return null;
//  }
//
//  @Override
//  public JsonElement serialize(MyInterface src, Type typeOfSrc, JsonSerializationContext context)
// {
//    // note : won't work, you must delegate this
//    JsonObject jo = context.serialize(src).getAsJsonObject();
//
//    String classPath = src.getClass().getName();
//    jo.add(PROP_NAME, new JsonPrimitive(classPath));
//
//    return jo;
//  }
// }
