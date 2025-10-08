## Unreleased
**Added :** 
- UniDict support
- IItemStack[] as IIngredient ZenCaster (or)

## 1.2.0
**Added :**
- MineralMixTweaker
- MineralMixTweaker support for Immersive Petroleum

**Changed :**
- IWorld.getEntitiesWithinAABB `IEntityDefinition entity` parameter is non-optional because already in CraftTweaker

**Removed :**
- Villager.customProfessionSetter : never really worked, caused crashes only in prod

**Fixed :**
- Can now parse invalid JSON text (JsonReader is lenient) - #5
- Crash on startup when customProfessionSetter is enabled - #6

## 1.1.2
- **Fixed** error on startup with just baubles
- **Added** IWorld.getEntitiesWithinAABB

## 1.1.1
Fixed crash on startup when using original baubles - #2

## 1.1.0

**Added :**
- Config to toggle event listeners
- IData file storage
- Expanded Baubles Support
- Bubbles Support
- ArrayUtil.toString
- IWorld.getTopBlock
- IWorld.getTopSolidBlock
- MutableString.indexOf
- MutableString.getLength

**Changed :**
- GardenClocheMultiplierFunction now returns float instead of IData
- Event listeners are off by default

## 1.0.1 : 
Fix mod not loading

## 1.0.0 :
First release