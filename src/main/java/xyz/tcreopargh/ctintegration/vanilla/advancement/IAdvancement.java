package xyz.tcreopargh.ctintegration.vanilla.advancement;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.text.ITextComponent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

import java.util.List;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "advancement.IAdvancement")
@SuppressWarnings("unused")
public interface IAdvancement {

    Object getInternal();

    @ZenGetter("id")
    @ZenMethod
    String getId();

    @ZenGetter("displayText")
    @ZenMethod
    ITextComponent getDisplayText();

    @ZenGetter("title")
    @ZenMethod
    ITextComponent getTitle();

    @ZenGetter("description")
    @ZenMethod
    ITextComponent getDescription();

    @ZenGetter("requirements")
    @ZenMethod
    String[][] getRequirements();

    @ZenGetter("requirementCount")
    @ZenMethod
    int getRequirementCount();

    @ZenGetter("children")
    @ZenMethod
    List<IAdvancement> getChildren();

    @ZenGetter("criterion")
    @ZenMethod
    List<String> getCriterion();

    @ZenGetter("parent")
    @ZenMethod
    IAdvancement getParent();

    @ZenMethod
    void applyRewards(IPlayer player);
}
