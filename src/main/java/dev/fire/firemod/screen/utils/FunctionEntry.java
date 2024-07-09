package dev.fire.firemod.screen.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class FunctionEntry {
    public String name;
    public String functionType;
    public ArrayList<String> data;

    public static Map<String, Integer> typeColors = Map.ofEntries(
            entry("event",0x7ad9ff),
            entry("process",0x6eff54),
            entry("entity_event",0xffe942),
            entry("func",0x365aff)
    );

    public FunctionEntry(String name, String functionType) {
        this.name = name;
        this.functionType = functionType;
        this.data = new ArrayList<String>();
    }

    public FunctionEntry(String name, String functionType, ArrayList<String> data) {
        this.name = name;
        this.functionType = functionType;
        this.data = data;
    }

    public int getFuncColor(String type) {
        if (this.typeColors.containsKey(type)) { return this.typeColors.get(type); }
        return 0;
    }
}
