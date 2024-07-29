package dev.fire.firemod.screen.utils;

import dev.fire.firemod.Firemod;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class FunctionEntry {
    public String functionName;
    public String functionType;
    public String rawJsonString;
    public ArrayList<String> code;
    public int longestline;

    public static Map<String, Integer> typeColors = Map.ofEntries(
            entry("event",0x7ad9ff),
            entry("process",0x6eff54),
            entry("entity_event",0xffe942),
            entry("func",0x758eff)
    );

    public static class Location {
        double x;
        double y;
        double z;
        double pitch;
        double yaw;
    }
    public static class DataItem {
        int Count;
        int DF_NBT;
        String id;
    }

    public static class Cluster {
        int amount;
        double horizontal;
        double vertical;
    }
    public static class SubMetaData {
        String rgb;
        String material;
        double size;
        int colorVariation;
        int sizeVariation;
    }

    public static class ItemMetaData {
        String option;
        String tag;
        String action;
        String block;
        String name;
        String scope;
        String x;
        String y;
        String z;
        Location loc;
        String type;
        String target;
        double vol;
        double pitch;
        String sound;
        String particle;
        Cluster cluster;
        SubMetaData data;
        String pot;
        int amp;
        int dur;
        //DataItem item;

    }
    public static class ItemData {
        String id;
        ItemMetaData data;
    }

    public static class Item {
        ItemData item;
        Integer slot;

    }

    public static class Args {
        ArrayList<Item> items;
    }

    public static class CodeBlock {
        String id;
        String block;
        Args args;
        String data;
        String action;
        String direct;
        String type;

    }

    public static class Template {
        String name;
        String type;
        ArrayList<CodeBlock> blocks;

    }
    public static Template getTemplateFromJson(String rawJson) {
        Template response = Firemod.gson.fromJson(rawJson, Template.class);
        Firemod.LOGGER.info(response.blocks.toString());

        return response;

    }

    public static ArrayList<String> getCodeListFromTemplate(Template template) {
        ArrayList<String> codeList = new ArrayList<>();

        int indent = 0;
        int changeIndent = 0;
        boolean has_colon;
        boolean has_args;
        boolean forceNoBrackets;
        for (CodeBlock block : template.blocks) {
            changeIndent = 0;
            has_colon = false;
            String appendText = "";
            String endText = "";

            // indents

            if (block.action != null) {
                if (block.action.isEmpty()) {
                    block.action = "null";
                }
            }


            String appendIndent = "";
            for (int ai = 0; ai < indent; ai++) {
                appendIndent = appendIndent + " ";
            }
            codeList.add(appendIndent + appendText);
            indent += changeIndent;
        }

        return codeList;
    }

    public static FunctionEntry getFunctionEntryFromJson(String rawJsonString) {
        Template template = getTemplateFromJson(rawJsonString);
        ArrayList<String> codeList = getCodeListFromTemplate(template);
        return new FunctionEntry(template.name, template.type, codeList);
    }

    private FunctionEntry(String functionName, String functionType, ArrayList<String> code) {
        this.functionName = functionName;
        this.functionType = functionType;
        this.code = code;

    }

    public int getFuncColor(String type) {
        if (this.typeColors.containsKey(type)) { return this.typeColors.get(type); }
        return 0;
    }
}
