package dev.fire.firemod.viewer;

import dev.fire.firemod.screen.utils.FunctionEntry;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionDataManager {
    public ArrayList<FunctionEntry> functionEntryArrayList;

    public FunctionDataManager() {
        functionEntryArrayList = new ArrayList<>();
    }
    public void addFunction(String rawJson) {
        FunctionEntry newEntry = FunctionEntry.getFunctionEntryFromJson(rawJson);
        boolean contains = false;
        for (FunctionEntry functionEntry : functionEntryArrayList) {
            if (functionEntry.functionName == newEntry.functionName && functionEntry.functionType == newEntry.functionType) {
                contains = true;
                break;
            }
        }
        if (!contains) {

            functionEntryArrayList.add(newEntry);
        }
    }
}
