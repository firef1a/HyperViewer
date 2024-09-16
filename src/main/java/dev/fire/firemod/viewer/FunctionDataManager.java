package dev.fire.firemod.viewer;

import dev.fire.firemod.Firemod;
import dev.fire.firemod.screen.utils.FunctionEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class FunctionDataManager {
    public ArrayList<FunctionEntry> functionEntryArrayList;
    public int focusedFunctionEntry;
    public boolean enableCodeViewer;

    public FunctionDataManager() {
        enableCodeViewer = false;
        focusedFunctionEntry = 0;
        functionEntryArrayList = new ArrayList<>();
        /*
        // test data
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

         */
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
    public void remove(FunctionEntry entry) {
        ArrayList<FunctionEntry> newFunctionList = new ArrayList<>();
        for (FunctionEntry functionEntry : functionEntryArrayList) {
            if (!(functionEntry.functionName.equals(entry.functionName) && functionEntry.functionType.equals(entry.functionType))) {
                newFunctionList.add(functionEntry);
            }
        }
        this.functionEntryArrayList = newFunctionList;
    }
    public void remove(int i) {
        functionEntryArrayList.remove(i);
    }

    public void clear() {
        functionEntryArrayList.clear();
        focusedFunctionEntry = 0;
        Firemod.functionFinder.checkedBlocks.clear();
        FunctionFinder.outstandingRequests = 0;
    }
}
