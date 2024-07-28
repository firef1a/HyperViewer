package dev.fire.firemod.viewer;

import dev.fire.firemod.screen.utils.FunctionEntry;
import dev.fire.firemod.screen.utils.templateUtils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FunctionDataManager {
    public ArrayList<FunctionEntry> functionEntryArrayList;

    public FunctionDataManager() {
        functionEntryArrayList = new ArrayList<>(List.of(
                FunctionEntry.getFunctionEntryFromJson(TestData.projectdf),
                FunctionEntry.getFunctionEntryFromJson(TestData.dorkey),
                FunctionEntry.getFunctionEntryFromJson(TestData.disguise),
                FunctionEntry.getFunctionEntryFromJson(TestData.smallfont),
                FunctionEntry.getFunctionEntryFromJson(TestData.nopull),
                FunctionEntry.getFunctionEntryFromJson(TestData.tablist),
                FunctionEntry.getFunctionEntryFromJson(TestData.command),
                FunctionEntry.getFunctionEntryFromJson(TestData.joinevent),
                FunctionEntry.getFunctionEntryFromJson(TestData.teamcommands),
                FunctionEntry.getFunctionEntryFromJson(TestData.webhook),
                FunctionEntry.getFunctionEntryFromJson(TestData.swaphands),
                FunctionEntry.getFunctionEntryFromJson(TestData.itemrc)
        ));
    }
    public void addFunction(String rawJson) {
        FunctionEntry newEntry = FunctionEntry.getFunctionEntryFromJson(rawJson);
        boolean contains = false;
        for (FunctionEntry functionEntry : functionEntryArrayList) {
            if (functionEntry.functionName.equals(newEntry.functionName) && functionEntry.functionType.equals(newEntry.functionType)) {
                contains = true;
                break;
            }
        }
        if (!contains) {

            functionEntryArrayList.add(newEntry);
        }
    }
}
