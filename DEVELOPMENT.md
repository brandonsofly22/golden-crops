# Golden Crops — Development Plan and Decision Log

> **Project status:** Active development. This file is the durable source of truth for design decisions, implementation status, testing, and publication notes.  
> **Last planning update:** 2026-10-21  
> **Important:** “Locked” means approved design, **not** necessarily implemented. Mark changes here when implemented and verified in-game.

## 1. Project identity and technical baseline

- **Project name:** Golden Crops (formerly considered “Golden Agriculture”); description may refer to golden agriculture.
- **Game:** Minecraft Java Edition; primary target **26.2**. Other versions/ports are not promised.
- **Platform:** Fabric; Java 25; IntelliJ IDEA; Gradle.
- **Package:** `com.brandon.goldencrops`; **mod ID:** `golden-crops`.
- **Repository:** https://github.com/brandonsofly22/golden-crops
- **Code license:** Apache-2.0. **Do not assume this license covers third-party textures.**
- **Known development environment:** Fabric Loader 0.19.5, Fabric API 0.161.0+26.2, Loom 1.18-SNAPSHOT, Gradle 9.7.1.
- **Release channels planned:** Modrinth (primary), CurseForge (secondary), GitHub (source, issues, wiki/releases).
- **General design:** Golden variants of crops, foods, farming tools, and special trees; preserve vanilla farming expectations unless a change is explicitly approved.

## 2. Status legend and process

- **LOCKED / PLANNED:** Agreed specification; may not exist in code.
- **IMPLEMENTED:** Code or resources added; does not imply successful gameplay test.
- **TESTED:** Observed functioning in-game.
- **OPEN:** Needs decision, verification, or revision.
- **PROVISIONAL:** Previously discussed but not firmly confirmed.
- Whenever code, recipes, odds, textures, or behavior change, update this file and record test results. Do not claim untested features in public release descriptions.

## 3. Shared farming and food rules — LOCKED

1. Rare golden discoveries from ordinary crops require **fully mature vanilla crops**. **Melons are the special case:** eligible vanilla **melon blocks** (farm-grown or naturally generated), not stems or individual slices.
2. Fortune improves the chance of rare golden discoveries; exact odds are crop-specific.
3. Golden crops that actually produce seed drops have a **20% independent chance to drop no seeds** from mature harvests, even with Fortune. **This does not alter rare discovery chances from vanilla crops.** It does not apply to potatoes, carrots, berries, or harvested melon blocks merely because they can be replanted; saplings are separately designed.
4. Golden foods generally restore **1.5× the vanilla food's hunger (rounded up to a whole point)** and **1.5× its vanilla saturation**. Exceptions may be explicitly approved; existing Golden Bread stays unchanged.
5. Distinguish “saturation restored” from saturation modifiers in code; validate actual food component values in-game.
6. Food effects and crop yields are individual specifications, not automatically inferred from the multiplier.

### Rare discovery rates — Fortune III targets LOCKED

| Discovery | Fortune III chance | Eligible source |
|---|---:|---|
| Golden Wheat Seeds | 1/250 | Mature vanilla wheat |
| Golden Mushroom Spores | 1/200 | Mushroom discovery mechanic **OPEN** (mushrooms do not have crop maturity stages) |
| Golden Melon Seeds | 1/175 | Vanilla melon block, natural or farm-grown |
| Golden Potato | 1/150 | Mature vanilla potato crop |
| Golden Beetroot Seeds | 1/125 | Mature vanilla beetroot crop |
| Golden Sweet Berries | 1/50 | Mature vanilla sweet berry bush |

Other previously proposed discovery rates, including Golden Apple Saplings and Golden Carrot, are separate systems. Mushroom source/eligibility needs explicit design before implementation; do not invent a maturity state.

### Fortune level discovery odds — LOCKED where explicitly approved

| Item | No Fortune | Fortune I | Fortune II | Fortune III |
|---|---:|---:|---:|---:|
| Golden Wheat Seeds (implemented) | 1/450 | 1/375 | 1/300 | 1/250 |
| Golden Beetroot Seeds | 1/200 | 1/167 approx. (0.60%) | 1/143 approx. (0.70%) | 1/125 |
| Golden Melon Seeds | 1/280 | 1/245 | 1/210 | 1/175 |
| Golden Potato | 1/240 | 1/210 | 1/180 | 1/150 |

For beetroot, the intended intermediate rates are **0.60% and 0.70%**, not exactly 1/167 and 1/143. Other crops' Fortune 0–II odds remain **OPEN**.

## 4. Crops and food: detailed specifications

### Golden Wheat — IMPLEMENTED and TESTED

- Golden Wheat Crop: 8 growth stages (age 0–7), farmland, bonemealable; existing crop class extends `CropBlock`.
- Mature harvest: **1–3 Golden Wheat**; immature harvest: **1 seed**.
- Mature seed quantity on a successful seed drop: Fortune 0 **2–4**, I **3–6**, II **4–7**, III **5–8**; **20% chance of no seeds**.
- Rare Golden Wheat Seeds from mature vanilla wheat; Fortune odds in table above.
- Golden Bread recipe: **3 Golden Wheat in a horizontal row**.
- Golden Bread: **8 hunger, 9.6 saturation, Resistance I for 30 seconds**. This existing tested balance overrides general 1.5× rule.
- Test reference: 100 mature Golden Wheat with Fortune III yielded **187 wheat / 523 seeds**; vanilla comparison **100 wheat / 439 seeds**.
- Source files include `GoldenWheatCropBlock.java`, `ModBlocks.java`, `ModItems.java`, `ModFoods.java`, `ModLootTables.java`, resources.
- Rare wheat discovery implemented with Fabric `LootTableEvents.MODIFY`; temporary test odds were restored to production odds.

### Golden Beetroot — PLANNED; crop class created, registration prepared

- `GoldenBeetrootCropBlock` extends vanilla `BeetrootBlock`; **4 growth stages (0–3)**, farmland, bonemealable.
- Mature harvest: **1–2 Golden Beetroots**, independent of Fortune.
- Mature seed quantity **if seeds drop**: Fortune 0 **1–3**, I **2–4**, II **3–5**, III **4–6**; **20% chance of no seeds**.
- Immature harvest: **1 Golden Beetroot Seed**, no Golden Beetroot.
- Rare seed discovery: only fully mature vanilla beetroot; odds in table above.
- Golden Beetroot food: **2 hunger, 1.8 saturation, Strength I for 15 seconds**, stack 64.
- Golden Beetroot Soup: **9 hunger, 10.8 saturation, Strength II for 30 seconds**, stack 1.
- Soup recipe: shapeless **6 Golden Beetroots + 1 Bowl**.
- Seed recipe: 3×3, **4 gold ingots in corners, 4 beetroot on sides, beetroot seeds in center**.
- **Next implementation:** finish `ModBlocks.java` registration (confirm local file), `ModItems.java`, `ModFoods.java`, models, textures, loot tables, rare discovery and tests.
- Crop class was created during conversation; **do not mark registration tested**.

### Golden Melon — PLANNED

- Golden Melon Seeds recipe: **4 gold ingots corners, 4 glistering melon slices sides, vanilla melon seeds center**.
- Golden Melon stems produce separate Golden Melon blocks and remain planted when melon blocks are harvested.
- Breaking Golden Melon block without Silk Touch: **4–9 Golden Melon Slices**.
- Fortune **shifts probability toward higher quantities** but **never exceeds 9 slices**; exact probability distribution **OPEN**.
- Silk Touch: **1 Golden Melon Block** instead of slices.
- **Golden Melon blocks do not drop seeds**, just slices (or the block with Silk Touch); do not add an ordinary seed-drop mechanic to them.
- Vanilla melon blocks **never gain ordinary vanilla melon seed drops** from this mod. They retain their normal vanilla loot and have **one independent rare Golden Melon Seed roll per block**, including naturally generated jungle melons and farmed melons. No roll per slice or from stems.
- Vanilla melon rare discovery odds: no Fortune 1/280, I 1/245, II 1/210, III 1/175; Silk Touch eligible, using applicable Fortune level if present.
- Golden Melon Slice food: **3 hunger, 1.8 saturation, Speed I for 45 seconds**, stack 64.
- No need to create seed drops from Golden Melon stems as an additional farming system. **OPEN:** whether breaking a golden stem should retain standard stem seed drops, if any, when implemented; do not conflate with melon-block harvesting.

### Golden Potato and Golden Baked Potato — PLANNED

- Golden Potato is **planted directly**; no separate seed item and no 20% seed-loss rule.
- Mature harvest: **2–6 Golden Potatoes before Fortune** (this replaces earlier 2–5 plan); Fortune increases yields using **vanilla-style bonus behavior** and may exceed six.
- Immature harvest: **1 Golden Potato**.
- **No poisonous potato drops** from Golden Potato crops.
- Rare Golden Potato is an additional drop from **fully mature vanilla potato crops**; Fortune odds: 1/240, 1/210, 1/180, 1/150.
- Golden Potato food: **2 hunger, 0.9 saturation, Regeneration I for 3 seconds**.
- Golden Baked Potato: **8 hunger, 9 saturation, Regeneration II for 8 seconds** (latest decision; supersedes earlier Regeneration I 5–10 seconds).
- Golden Potato recipe: 3×3, **4 gold ingots corners, 5 potatoes sides and center**; earlier plan allowed baked potato as center **OPEN whether alternate recipe was added**.
- Cooking Golden Potato into Golden Baked Potato: smelting (200 ticks), smoking (100), campfire (600), **0.35 XP** where applicable; three JSON recipe files committed.
- **OPEN:** exact Fortune bonus distribution and in-game healing test for Regeneration II.

### Golden Mushroom and Golden Mushroom Stew — PLANNED

- One combined **Golden Mushroom Spores** item for red/brown mushrooms, not separate species.
- Spores recipe: 4 gold ingots corners, red mushrooms left/right, brown mushrooms top/bottom, mushroom stew center.
- Golden Mushroom Stew: shapeless **2 Golden Mushrooms + 1 Bowl**; **Jump Boost for 45 seconds plus short Saturation effect**.
- Under general food multiplier, mushroom stew target **9 hunger and 10.8 saturation** (vanilla 6/7.2), pending final effect and implementation check.
- Fortune III rare spores target 1/200; **OPEN** eligible vanilla mushroom discovery trigger, Fortune 0–II odds, harvest yields, precise Saturation effect level/duration.

### Golden Sweet Berries — PLANNED

- Planted directly (no separate seeds); 20% seed-loss mechanic does not apply.
- Recipe JSON: 4 gold ingots corners, 5 sweet berries sides and center.
- Golden Sweet Berries food: **3 hunger, 1.8 saturation** under 1.5× rules, **Fire Resistance for 15 seconds**.
- Fortune III discovery target **1/50** from mature vanilla berry bush.
- **OPEN:** berry harvest quantities, Fortune 0–II discovery rates, exact maturity handling and whether the previously discussed bone meal center variant is desired.

### Golden Carrot — PLANNED

- Vanilla Golden Carrot crafting remains available; plant Golden Carrot directly.
- Intended as **best overall saturation food** in ranking; **do not automatically apply 1.5× to it without checking existing vanilla Golden Carrot stats and deciding an exception**.
- **OPEN:** growth/harvest, whether new edible stats are needed (the vanilla Golden Carrot already exists), rare discovery mechanic.

## 5. Food ranking — LOCKED intended order

1. Golden Carrot — highest saturation priority.
2. Golden Bread — strong hunger restoration and Resistance.
3. Golden Mushroom Stew — Jump Boost and brief Saturation effect.
4. Golden Beetroot Soup — Strength II.
5. Golden Melon Slice — Speed.
6. Golden Baked Potato — Regeneration II (updated to 8 seconds).
7. Golden Beetroot — Strength I.
8. Golden Potato — Regeneration I.
9. Golden Sweet Berries — Fire Resistance.

Golden Wheat and Golden Mushroom are ingredients, not separately ranked edible foods. Re-evaluate balance during testing without silently changing the agreed order.

## 6. Golden Apple Trees — PLANNED, separate sapling system

- Ordinary Golden Apple Saplings craft from either oak or dark oak saplings (two JSON recipes).
- Recipe: **4 gold blocks corners, 4 golden apples sides, oak or dark oak sapling center**.
- Golden apple harvest by **breaking special leaves**, not right-clicking.
- **4–8 apples per harvest** without Fortune; Fortune I **4–10**, II **4–12**, III **4–14**; intended as **per tree harvest**, not per individual leaf.
- After first harvest, golden leaves **regrow after 10 minutes / 12,000 loaded ticks**. Timer pauses when chunks unload.
- **Second harvest is final:** tree exhausts with 100% certainty; golden leaves disappear permanently; vanilla oak/dark oak logs remain. This replaces earlier random exhaustion idea.
- Golden Apple Sapling chance **only on final harvest**: no Fortune **5%**, I **10%**, II **15%**, III **20%**.
- Vanilla bone meal does not work on Golden Apple Sapling; planned **Golden Bone Meal does**.
- Natural tree growth roughly twice as long as vanilla was discussed **PROVISIONALLY**.
- **OPEN:** exact sapling variants/texture implementation, harvest bookkeeping, loaded-chunk performance, and growth speed.

### Enchanted Golden Apple Sapling — PLANNED

Recipe grid:

| | | |
|---|---|---|
| Nether Star | Ghast Tear | Diamond Block |
| Shield | Golden Apple Sapling | Totem of Undying |
| Netherite Ingot | Magma Cream | Enchanted Golden Apple |

- Vanilla bone meal **and Golden Bone Meal** do **not** work on the enchanted sapling.
- Special tree behavior, growth, drops, and balance **OPEN**; do not invent.
- Existing advancement concept: **Forbidden Orchard** (Challenge).

## 7. Golden Grow Lights — LOCKED design, not implemented

- Three variants: **Torch, Lantern, Glowstone**.
- Recipe concept: **8 gold ingots surrounding the corresponding vanilla light**.
- Affect **Golden Crops only**.
- Area: **11×11** around the light (exact vertical reach/stacking rules **OPEN**).
- Approximately **2× growth** from light alone.
- Combined light + Golden Fertilizer cap: **2.5×** growth (not multiplicative beyond cap).
- **OPEN:** technical tick implementation, whether light effects stack, exact placement/radius behavior.

## 8. Golden Fertilizer and irrigation — LOCKED design, not implemented

- Apply Golden Fertilizer to **water** for irrigation effect; **Shift-remove**.
- Planned coverage: **8×8**; exact shape/center **OPEN**.
- Fertilizer consumed on harvest; per-farmland charges cap **64**.
- Obtain via **smelting raw golden harvests**, excluding prepared foods and apples; output **1/2/4** depending on input category.
- **OPEN:** precise ingredient-to-output mapping, visual feedback, whether water or farmland holds charges, save/load persistence, interactions with Golden Grow Lights.

## 9. Golden Composter and Golden Bone Meal — PLANNED

- Golden Composter and Golden Bone Meal are planned.
- Golden Bone Meal works on ordinary Golden Apple Sapling, not enchanted sapling.
- **OPEN:** recipes, compost acceptance/output, crop interaction, exact growth acceleration, balance. No unapproved specifics.

## 10. Advancements — LOCKED names and tiers

**Tasks:** Golden Agriculture; Worth Its Weight in Gold; Money Does Grow on Trees.  
**Goals:** Golden Fields; Golden Harvest; Golden Diet; Liquid Gold; Let There Be Gold.  
**Challenge:** Forbidden Orchard.

- Exact trigger conditions **OPEN** where not documented.
- Earlier guiding rule: first-item discoveries are Tasks, all/every collection achievements are Goals, enchanted tree is Challenge.
- Validate each advancement against the actual shipped items and mechanics.

## 11. Recipe inventory and current source status

The following recipe JSON files were added on GitHub and fast-forward pulled to local `main` (14 files in the documented sync):

- `golden_wheat_seeds.json`
- `golden_beetroot_seeds.json`
- `golden_melon_seeds.json`
- `golden_potato.json`
- `golden_sweet_berries.json`
- `golden_mushroom_spores.json`
- `golden_apple_sapling_oak.json`
- `golden_apple_sapling_dark_oak.json`
- `enchanted_golden_apple_sapling.json`
- `golden_mushroom_stew.json`
- `golden_beetroot_soup.json`
- `golden_baked_potato_smelting.json`
- `golden_baked_potato_smoking.json`
- `golden_baked_potato_campfire_cooking.json`

Golden Bread recipe already existed and was tested. Recipes for unregistered items can fail at runtime; **do not infer readiness from Gradle build alone**. Verify names and recipes against actual code before launch.

## 12. Implementation roadmap

1. **Golden Beetroot**: finish block/item/food registration, crop visuals, loot tables, vanilla rare discovery; test maturity, Fortune, food and soup.
2. Golden Melon: stem/block behavior, 4–9 slice loot, Fortune weighting cap, Silk Touch, vanilla block discovery; test jungle and farm melons.
3. Golden Potato: plant item, 2–6 yield, Fortune, no poisonous potatoes, rare discovery, baked food Regeneration II.
4. Golden Sweet Berries, Golden Mushroom, Golden Carrot.
5. Golden Apple Trees and Enchanted Golden Apple Tree.
6. Golden Grow Lights, Golden Composter/Bone Meal, Golden Fertilizer/irrigation.
7. Advancements, compatibility, balance, regression tests, releases.

### Testing checklist for each crop

- Registries and resource loading; no missing models/textures or recipe parse errors.
- Planting and maturity, bone meal behavior, farmland/stem/bush interactions.
- Immature and mature loot, 20% seed-loss where applicable.
- Fortune 0/I/II/III and Silk Touch where applicable.
- Rare discovery only from eligible vanilla harvests; verify no accidental duplicate rolls.
- Food hunger, saturation, effect amplifier, duration, stacking, and bowls.
- Multiplayer/server compatibility, reload/persistence, performance.
- Record tested Minecraft/Fabric/Java versions and date.

## 13. Publication, permissions, and AI disclosure notes

**Platforms:** Modrinth, CurseForge, GitHub. Prepare honest project descriptions, feature list, installation requirements, compatibility, changelog, issues/wiki, screenshots and attribution.

- The creator manually makes/edits **all actual shipping project textures/assets** (Paint.NET). AI-generated preview/reference images are for private evaluation only and **must not be shipped as mod assets or uploaded as project imagery**.
- AI assistance has been used for **planning, design discussions, code drafting/debugging, and documentation**. Assess each platform's current disclosure requirements at publication; disclose substantial AI contributions accurately. **Do not claim zero AI involvement.**
- Permission was discussed/obtained from **Lupin**, creator of “Golden Foods,” for **crop-food textures, not meats**. Confirm exact permission scope, attribution text, redistribution conditions, and which assets actually ship before release. The source textures may be **All Rights Reserved**; permission and attribution matter independently of Apache-2.0 code licensing.
- Do not claim every texture is original if a permitted third-party texture is used or modified. Record asset-by-asset provenance.
- Do not use AI-generated images for Modrinth project pages.
- Consider compatibility with **Raixu27's 3×3 farm datapack**, but test before making a compatibility claim.
- Track dependencies, supported Minecraft versions, Fabric API and Java requirements, screenshots, known limitations, and performance caveats.
- Keep changelogs distinct from design proposals: **planned**, **implemented**, **tested**, **released**.

## 14. Known ambiguities and corrections to preserve

- **Golden Potato:** initial mature yield 2–5 was revised to **2–6**. Golden Baked Potato changed from Regeneration I for 5–10 seconds to **Regeneration II for 8 seconds**.
- **Golden Apple Trees:** old possible 10–25% sapling/return and random exhaustion discussions were superseded by **final-harvest 5/10/15/20% sapling chances** and **guaranteed exhaustion after second harvest**.
- **Golden Melon:** normal melon blocks do not drop ordinary seeds. Only **vanilla melon blocks** get the additional rare golden-seed roll. Golden melon blocks drop slices or Silk Touch block only.
- **Golden Beetroot:** intended 0.60%/0.70% mid-Fortune rates are approximated by 1/167 and 1/143, not identical.
- **Golden Carrot:** vanilla already has a Golden Carrot food; do not accidentally overwrite vanilla food properties without a conscious design decision.
- **Golden Mushroom:** “only mature vanilla crops” cannot literally apply to mushrooms; specify a special eligible source.
- **Golden Sweet Berries:** bushes have growth stages; rare discovery only from mature bushes, not each berry item.
- **Golden Melon 20% seed-loss:** does not apply to melon block harvesting because golden melon blocks drop no seeds.
- Original planning also mentioned a Golden Apple Sapling recipe corner decision; current committed recipes use **gold blocks**, so use those unless explicitly revised.
- Distinguish historical concepts from locked designs; do not silently reconstruct forgotten numbers.

## 15. Git and update workflow

- Repository `main` tracks `origin/main`.
- GitHub-web recipe edits were brought to the PC with `git pull --ff-only`.
- After changes on GitHub, **pull before editing/pushing local files** to avoid conflicts.
- On PC, update this document alongside implementation and use descriptive commits.
- Avoid rewriting published commit history just to rename an old commit.
- This document is a snapshot of decisions through 2026-10-21; update it when new decisions are made.
