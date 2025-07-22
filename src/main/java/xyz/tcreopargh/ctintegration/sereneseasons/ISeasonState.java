package xyz.tcreopargh.ctintegration.sereneseasons;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;

@ModOnly("sereneseasons")
@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "sereneseasons.ISeasonState")
@SuppressWarnings("unused")
public interface ISeasonState {

    @ZenGetter("dayDuration")
    @ZenMethod
    int getDayDuration();

    @ZenGetter("subSeasonDuration")
    @ZenMethod
    int getSubSeasonDuration();

    @ZenGetter("seasonDuration")
    @ZenMethod
    int getSeasonDuration();

    @ZenGetter("cycleDuration")
    @ZenMethod
    int getCycleDuration();

    @ZenGetter("seasonCycleTicks")
    @ZenMethod
    int getSeasonCycleTicks();

    @ZenGetter("day")
    @ZenMethod
    int getDay();

    @ZenGetter("season")
    @ZenMethod
    int getSeason();

    @ZenGetter("subSeason")
    @ZenMethod
    int getSubSeason();

    @ZenGetter("subSeasonName")
    @ZenMethod
    String getSubSeasonName();

    @ZenGetter("seasonName")
    @ZenMethod
    String getSeasonName();

    @ZenGetter("tropicalSeason")
    @ZenMethod
    int getTropicalSeason();

    @ZenGetter("tropicalSeasonName")
    @ZenMethod
    String getTropicalSeasonName();

    Object getInternal();
}

