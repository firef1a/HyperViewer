package dev.fire.firemod.screen.utils;

import com.google.gson.JsonObject;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.CodeScreen;
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
    public ArrayList<Text> formattedCodeList;

    public static Map<String, Integer> typeColors = Map.ofEntries(
            entry("player_event",0x7ad9ff),
            entry("process",0x6eff54),
            entry("entity_event",0xffe942),
            entry("function",0x365aff)
    );

    public static class ItemMetaData {
        String option;
        String tag;
        String action;
        String block;
        String name;
        String scope;

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
        ArrayList<CodeBlock> blocks;

    }
    public static Template getTemplateFromJson(String rawJson) {
        Template response = Firemod.gson.fromJson(rawJson, Template.class);
        Firemod.LOGGER.info(response.blocks.toString());

        return response;

    }

    public static ArrayList<Text> getCodeListFromTemplate(Template template) {
        ArrayList<Text> codeList = new ArrayList<>();

        int indent_level = 0;
        for (CodeBlock block : template.blocks) {
            MutableText appendText = Text.empty();
            MutableText endText = Text.empty();

            // indents
            for (int level = 0; level < indent_level; level++) {
                appendText.append("    ");
            }

            switch (block.id) {
                case ("block"):
                    switch (block.block) {
                        case ("process"):
                            appendText.append(Text.literal(block.block));
                            appendText.append(Text.literal(" "));
                            appendText.append(Text.literal(block.data).withColor(0x6eff54));
                            indent_level++;
                        case ("event"):
                            appendText.append(Text.literal(block.block));
                            appendText.append(Text.literal(" "));
                            appendText.append(Text.literal(block.data).withColor(0x6eff54));
                            appendText.append(Text.literal("("));
                            endText.append(Text.literal(")"));
                            indent_level++;
                        case ("func"):
                            appendText.append(Text.literal(block.block));
                            appendText.append(Text.literal(" "));
                            appendText.append(Text.literal(block.data).withColor(0x365aff));
                            indent_level++;
                        default:
                            if (block.action != null){
                                appendText.append(Text.literal(block.block));
                                appendText.append(Text.literal("."));
                                appendText.append(Text.literal(block.action));
                            }
                    }
                case ("bracket"):
                    if (Objects.equals(block.direct, "open")) {
                        indent_level++;
                        continue;
                    } else if (Objects.equals(block.direct, "close")) {
                        indent_level--;
                        continue;
                    }
                default:
                    if (block.action != null){
                        appendText.append(Text.literal(block.block));
                        appendText.append(Text.literal("."));
                        appendText.append(Text.literal(block.action));
                    }


            }


            // add args
            if (block.args != null) {
                if (block.args.items != null) {
                    if (!block.args.items.isEmpty()) {
                        appendText.append(Text.literal("("));
                        endText.append(Text.literal(")"));
                        int i = 0;
                        for (Item arg : block.args.items) {
                            MutableText argText = Text.empty();

                            ItemData itemData = arg.item;
                            String itemID = itemData.id;

                            switch (itemID) {
                                case ("var"):
                                    argText.append(Text.literal(itemData.data.name).withColor(Formatting.YELLOW.getColorValue()));
                                case ("num"):
                                    argText.append(Text.literal(itemData.data.name).withColor(Formatting.RED.getColorValue()));
                                case ("txt"):
                                    argText.append(Text.literal("\""+itemData.data.name.replace("Â§", "&")+"\"").withColor(Formatting.WHITE.getColorValue()));
                                case ("str"):
                                    argText.append(Text.literal("\""+itemData.data.name+"\"").withColor(Formatting.WHITE.getColorValue()));
                                case ("bl_tag"):
                                    argText.append(Text.literal(itemData.data.option).withColor(Formatting.AQUA.getColorValue()));
                                default:
                                    if (itemData.data.name != null) {
                                        argText.append(Text.literal(itemData.data.name).withColor(Formatting.WHITE.getColorValue()));
                                    }

                            }

                            appendText.append(argText);

                            if (i < block.args.items.size() - 1) {
                                appendText.append(Text.literal(", "));
                            }
                            i++;
                        }
                    }
                }
            }

            appendText.append(endText);
            codeList.add(appendText);
        }

        return codeList;
    }

    public static FunctionEntry getFunctionEntryFromJson(String rawJsonString) {
        Template template = getTemplateFromJson(rawJsonString);
        ArrayList<Text> codeList = getCodeListFromTemplate(template);
        return new FunctionEntry("test", "process", codeList);
    }

    private FunctionEntry(String functionName, String functionType, ArrayList<Text> formattedCodeList) {
        this.functionName = functionName;
        this.functionType = functionType;
        this.formattedCodeList = formattedCodeList;

    }

    public int getFuncColor(String type) {
        if (this.typeColors.containsKey(type)) { return this.typeColors.get(type); }
        return 0;
    }
}
