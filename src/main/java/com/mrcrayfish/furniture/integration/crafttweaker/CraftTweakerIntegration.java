package com.mrcrayfish.furniture.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.LinkedList;
import java.util.List;

public class CraftTweakerIntegration {
    public static final String CLASS_PREFIX = "mods.cfm.";

    private static final List<IAction> ACTIONS = new LinkedList<>();

    public static void defer(String description, Runnable action) {
        ACTIONS.add(new DeferredAction(description, action));
    }

    public static void apply() {
        CraftTweakerAPI.logInfo("Applying MrCrayfish's Furniture Mod changes...");
        ACTIONS.forEach((action) -> {
            try {
                CraftTweakerAPI.apply(action);
            } catch (Exception ex) {
                CraftTweakerAPI.logError("Exception whilst applying \"" + action.describe() + "\"", ex);
            }
        });
    }

    private static class DeferredAction implements IAction {
        private final String description;
        private final Runnable func;

        public DeferredAction(String description, Runnable func) {
            this.description = description;
            this.func = func;
        }

        @Override
        public void apply() {
            func.run();
        }

        @Override
        public String describe() {
            return description;
        }
    }
}
