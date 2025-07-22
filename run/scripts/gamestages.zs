#modloaded gamestages
mods.ctintegration.gamestages.GameStageAddEvent;
mods.ctintegration.gamestages.GameStageAddedEvent;
mods.ctintegration.gamestages.GameStageRemoveEvent;
mods.ctintegration.gamestages.GameStageRemovedEvent;
mods.ctintegration.gamestages.GameStageClearedEvent;

events.onGameStageAdd(function(event as GameStageAddEvent) {
   event.player.sendChat("Gamestage add! " + event.gameStage);
});
events.onGameStageAdded(function(event as GameStageAddedEvent) {
   event.player.sendChat("Gamestage added! " + event.gameStage);
});
events.onGameStageRemove(function(event as GameStageRemoveEvent) {
   event.player.sendChat("Gamestage remove! " + event.gameStage);
});
events.onGameStageRemoved(function(event as GameStageRemovedEvent) {
   event.player.sendChat("Gamestage remove! " + event.gameStage);
});
events.onGameStageCleared(function(event as GameStageClearedEvent) {
   event.player.sendChat("Gamestage Cleared! ");
});