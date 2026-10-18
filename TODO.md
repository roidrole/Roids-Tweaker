# TODO
- Check which mods have built-in CT support and remove duplicate support (Buildcraft and fossils (broken?), at least)
- Figure out what to do with mod-specific CT addons (RailcraftTweaker, GalacticraftTweaker): include or delegate
- Test everything after Betweenlands:
  - Buildcraft support is broken
  - Draconic Evolution support is broken
- Make the JEI module compatible with CT's, ideally merge the package
  - Might not be possible because scheduling. CT's JEI plugin doesn't happen after other mods'...
- Write the wiki once the features stabilize

Improve API:
- Composter remove recipes
- Use IEntityDefinition pretty much everywhere (additional methods to keep backwards-compat)

Make lazy actions:
- Unidict (cleanup API held, too)
- Event handler (instead of action, the handler itself)