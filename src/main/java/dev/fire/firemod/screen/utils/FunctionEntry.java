package dev.fire.firemod.screen.utils;

import com.google.gson.JsonObject;
import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.CodeScreen;
import dev.fire.firemod.screen.utils.templateUtils.CodeLine;
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
    public ArrayList<CodeLine> formattedCodeList;

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

    public static ArrayList<CodeLine> getCodeListFromTemplate(Template template) {
        ArrayList<CodeLine> codeList = new ArrayList<>();

        int indent = 0;
        int changeIndent = 0;
        boolean has_colon;
        boolean has_args;
        boolean forceNoBrackets;
        for (CodeBlock block : template.blocks) {
            changeIndent = 0;
            has_colon = false;
            forceNoBrackets = false;
            MutableText appendText = Text.empty();
            MutableText endText = Text.empty();

            // indents

            if (block.action != null) {
                if (block.action.isEmpty()) {
                    block.action = "null";
                }
            }
            if (Objects.equals(block.id, "block")) {
                if (Objects.equals(block.block, "process")) {
                    appendText.append(Text.literal(block.block));
                    appendText.append(Text.literal(" "));
                    appendText.append(Text.literal(block.data).withColor(typeColors.get("process")));
                    template.name = block.data;
                    template.type = block.block;
                    changeIndent++;
                    has_colon = true;

                } else if (Objects.equals(block.block, "event")) {
                    appendText.append(Text.literal(block.block));
                    appendText.append(Text.literal(" "));
                    appendText.append(Text.literal(block.action).withColor(typeColors.get("event")));
                    template.name = block.action;
                    template.type = block.block;
                    changeIndent++;
                    has_colon = true;

                } else if (Objects.equals(block.block, "func")) {
                    appendText.append(Text.literal(block.block));
                    appendText.append(Text.literal(" "));
                    appendText.append(Text.literal(block.data).withColor(typeColors.get("func")));
                    template.name = block.data;
                    template.type = block.block;
                    changeIndent++;
                    has_colon = true;

                } else if (Objects.equals(block.block, "player_action")) {
                    appendText.append(Text.literal("player"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xbaa182));

                } else if (Objects.equals(block.block, "select_obj")) {
                    appendText.append(Text.literal(block.block));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xc781de));

                } else if (Objects.equals(block.block, "control")) {
                    appendText.append(Text.literal((block.block)));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0x969696));

                }  else if (Objects.equals(block.block, "entity_action")) {
                    appendText.append(Text.literal("entity"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xbad19b));
                }  else if (Objects.equals(block.block, "set_var")) {
                    appendText.append(Text.literal(block.block));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xfffbc9));
                } else if (Objects.equals(block.block, "game_action")) {
                    appendText.append(Text.literal("game"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xde6459));
                } else if (Objects.equals(block.block, "call_func")) {
                    appendText.append(Text.literal("call"));
                    appendText.append(Text.literal(" "));
                    appendText.append(Text.literal(block.data).withColor(typeColors.get("func")));
                } else if (Objects.equals(block.block, "start_process")) {
                    appendText.append(Text.literal("call_process"));
                    appendText.append(Text.literal(" "));
                    appendText.append(Text.literal(block.data).withColor(typeColors.get("process")));
                }



                else if (Objects.equals(block.block, "repeat")) {
                    appendText.append(Text.literal("repeat"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0x79d4cb));
                    has_colon = true;

                }  else if (Objects.equals(block.block, "if_var")) {
                    appendText.append(Text.literal("if"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xd3cfff));
                    has_colon = true;

                } else if (Objects.equals(block.block, "if_game")) {
                    appendText.append(Text.literal("if_game"));
                    appendText.append(Text.literal("."));
                    appendText.append(Text.literal(block.action).withColor(0xf07c59));
                    has_colon = true;
                } else if (Objects.equals(block.block, "else")) {
                    appendText.append(Text.literal("else").withColor(0xd3cfff));
                    has_colon = true;
                    forceNoBrackets = true;
                } else {
                    if (block.action != null){
                        appendText.append(Text.literal(block.block));
                        appendText.append(Text.literal("."));
                        appendText.append(Text.literal(block.action));
                    }
                }

            } else {
                if (block.id.equals("bracket")) {
                    if (Objects.equals(block.direct, "open")) {
                        changeIndent++;
                    } else if (Objects.equals(block.direct, "close")) {
                        changeIndent--;
                    }
                    indent += changeIndent;
                    continue;
                } else {
                    if (block.action != null) {
                        appendText.append(Text.literal(block.block));
                        appendText.append(Text.literal("."));
                        appendText.append(Text.literal(block.action));
                    }
                }
            }

            has_args = false;
            // add args
            if (block.args != null) {
                if (block.args.items != null) {
                    if (!block.args.items.isEmpty()) {
                        has_args = true;
                        appendText.append(Text.literal("("));
                        endText.append(Text.literal(")"));
                        int i = 0;
                        for (Item arg : block.args.items) {
                            MutableText argText = Text.empty();

                            ItemData itemData = arg.item;

                            if (Objects.equals(itemData.id, "hint")) {
                                i++;
                                continue;
                            }

                            if (Objects.equals(itemData.id, "var")) {
                                int color = 0xfffff;
                                if (itemData.data.scope.equals("local")) { color = 0x8eff8a; }
                                if (itemData.data.scope.equals("saved")) { color = 0xfff766; }
                                if (itemData.data.scope.equals("unsaved")) { color = 0xa8a8a8; }
                                if (itemData.data.scope.equals("line")) { color = 0x5cc0ff; }

                                argText.append(Text.literal(itemData.data.name).withColor(color));
                            } else if (Objects.equals(itemData.id, "num")) {
                                argText.append(Text.literal(itemData.data.name).withColor(Formatting.RED.getColorValue()));
                            } else if (Objects.equals(itemData.id, "txt") || Objects.equals(itemData.id, "comp")) {
                                argText.append(Text.literal("\""+itemData.data.name.replace("Â§", "&")+"\"").withColor(Formatting.AQUA.getColorValue()));
                            } else if (Objects.equals(itemData.id, "vec")) {
                                argText.append(Text.literal("v(" + itemData.data.x  + ", " + itemData.data.y + ", " + itemData.data.z + ")").withColor(0x90e0d8));
                            } else if (Objects.equals(itemData.id, "loc")) {
                                String locText = "[" + itemData.data.loc.x  + ", " + itemData.data.loc.y + ", " + itemData.data.loc.z;
                                if (itemData.data.loc.pitch != 0 || itemData.data.loc.yaw != 0) {
                                    locText = locText + ", " + itemData.data.loc.pitch + ", " + itemData.data.loc.yaw;
                                }
                                locText = locText + "]";
                                argText.append(Text.literal(locText).withColor(0xa6e048));
                            } else if (Objects.equals(itemData.id, "item")) {
                                argText.append(Text.literal("item").withColor(0xdb9d40));
                                //argText.append(Text.literal(itemData.data.item.Count + "x " + itemData.data.item.id).withColor(0xdb9d40));
                            } else if (Objects.equals(itemData.id, "g_val")) {
                                argText.append(Text.literal("gv(" + itemData.data.type + ", " + itemData.data.target + ")").withColor(0xdec8a6));

                            } else if (Objects.equals(itemData.id, "snd")) {
                                argText.append(Text.literal("s(" + itemData.data.pitch + ", "  + itemData.data.vol + ", " + itemData.data.sound + ")").withColor(0xffedde));
                            } else if (Objects.equals(itemData.id, "part")) {
                                String partText = "part(";

                                partText = partText + itemData.data.particle;

                                partText = partText + ")";
                                argText.append(Text.literal(partText).withColor(0xd4e3ff));
                            } else if (Objects.equals(itemData.id, "pot")) {
                                String potText = "pot(";

                                potText = potText + itemData.data.pot;
                                potText = potText + ", ";
                                potText = potText + itemData.data.amp;
                                potText = potText + ", ";
                                potText = potText + itemData.data.dur;

                                potText = potText + ")";
                                argText.append(Text.literal(potText).withColor(0xd296ff));
                            }
                            else if (Objects.equals(itemData.id, "bl_tag")) {
                                MutableText blText = Text.empty();
                                blText.append(Text.literal(itemData.data.tag).withColor(Formatting.AQUA.getColorValue()));
                                blText.append(Text.literal( "=").withColor(Formatting.WHITE.getColorValue()));
                                blText.append(Text.literal(itemData.data.option).withColor(Formatting.AQUA.getColorValue()));
                                argText.append(blText);
                            }

                            appendText.append(argText);

                            if (i < (block.args.items.size() - 1)) {
                                //hints are bad smh
                                if (!block.args.items.get(i+1).item.id.equals("hint")) {
                                    appendText.append(Text.literal(", "));
                                }
                            }
                            i++;
                        }
                    }
                }
            }
            if (!has_args && !Objects.equals(block.id, "bracket") && !forceNoBrackets) {
                appendText.append(Text.literal("("));
                endText.append(Text.literal(")"));
            }

            appendText.append(endText);
            if (has_colon) {
                appendText.append(Text.literal(":"));
            }
            codeList.add(new CodeLine(appendText, indent));
            indent += changeIndent;
        }

        return codeList;
    }

    public static FunctionEntry getFunctionEntryFromJson(String rawJsonString) {
        Template template = getTemplateFromJson(rawJsonString);
        ArrayList<CodeLine> codeList = getCodeListFromTemplate(template);
        return new FunctionEntry(template.name, template.type, codeList);
    }

    private FunctionEntry(String functionName, String functionType, ArrayList<CodeLine> formattedCodeList) {
        this.functionName = functionName;
        this.functionType = functionType;
        this.formattedCodeList = formattedCodeList;

    }

    public int getFuncColor(String type) {
        if (this.typeColors.containsKey(type)) { return this.typeColors.get(type); }
        return 0;
    }
}
