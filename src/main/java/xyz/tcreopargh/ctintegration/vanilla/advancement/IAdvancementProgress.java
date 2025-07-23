package xyz.tcreopargh.ctintegration.vanilla.advancement;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.tcreopargh.ctintegration.CTIntegration;
import xyz.tcreopargh.ctintegration.date.IDate;

import java.util.List;

@ZenRegister
@ZenClass(CTIntegration.CT_PACKAGE + "advancement.IAdvancementProgress")
@SuppressWarnings("unused")
public interface IAdvancementProgress {

    Object getInternal();

    @ZenMethod
    boolean isDone();

    @ZenMethod
    boolean hasProgress();

    @ZenMethod
    boolean grantCriterion(String criterion);

    @ZenMethod
    boolean revokeCriterion(String criterion);

    @ZenMethod
    @ZenGetter("completedCriteria")
    List<String> getCompletedCriteria();

    @ZenMethod
    @ZenGetter("remainingCriteria")
    List<String> getRemainingCriteria();

    @ZenMethod
    @ZenGetter("percent")
    float getPercent();

    @ZenMethod
    @ZenGetter("progressText")
    String getProgressText();

    @ZenMethod
    boolean isCriterionObtained(String criterion);

    @ZenMethod
    IDate getCriterionObtainedDate(String criterion);

    @ZenMethod
    IDate getFirstProgressDate(String criterion);

    @ZenMethod
    void obtainCriterion(String criterion);

    @ZenMethod
    void resetCriterion(String criterion);

    @ZenMethod
    boolean setCompleted();

    @ZenMethod
    void resetAll();

}
