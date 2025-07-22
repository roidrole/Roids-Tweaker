import mods.ctintegration.util.RecipePattern;

RecipePattern.init("dsdafg", <minecraft:wool>, [
    "aaa",
    "b b",
    "ccc"
])
.with('a', <minecraft:string>)
.and('b', <minecraft:stick>)
.and('c', <ore:ingotIron>)
.setMirrored(true)
.build();

RecipePattern.init(<minecraft:wool>, [
    "AAA",
    "S S",
    "GGG"
])
.map({
    A: <minecraft:apple>,
    S: <minecraft:stick>,
    G: <ore:ingotGold>
})
.setMirrored(true)
.setName("blabla")
.setAction(function(out, cInfo, player) {
    player.xp += 1;
})
.build();

//You can also use the RecipePattern to generate an IIngredient[] to use in custom crafting recipes
//Commented out because I don't have avaritia in dev
/*
    mods.avaritia.ExtremeCrafting.addShaped("recipe_0", <minecraft:diamond>, RecipePattern.init([
        "         ",
        " ### ### ",
        "#########",
        "#########",
        " ####### ",
        "  #####  ",
        "   ###   ",
        "    #    ",
        "         "
    ]).map({
        '#': <ore:ingotGold>
    }).ingredients);
*/