@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/fabric-loom.md

# Easters Delight - `fabric/1.21.11`

**This file describes the `fabric/1.21.11` line**: Minecraft 1.21.11, Fabric.
Built with Fabric Loom.

It belongs to whichever folder has this branch checked out - the main `EasterDelight` folder or a worktree
under `EasterDelight/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

A cute add-on for Farmer's Delight adding egg painting and Easter-themed foods. Mod ID
`eastersdelight`; the repository and folder are `EasterDelight` (singular), which is historical.

Split build: the Fabric lines are `fabric/*` (Loom); the 1.21.1 NeoForge line is `neoforge/1.21`
(ModDevGradle), and `neoforge/1.20` is a Forge line despite its prefix.

## This line

- Java 21, Mojang mappings with Parchment.
- Datagen: `runDatagen`. Output: `src/main/generated/resources`, never hand-edited. Pinned to this mod with `modId = mod_id`.
- No game tests yet. Writing the first one for whatever is ported next is the highest-value test available (`verification.md`).
- No `publishMods` on this line: add the block before publishing it through the plugin (`publishing.md`).
- GitHub Actions: `build.yml`.
