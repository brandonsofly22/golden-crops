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

Golden Apple Sapling recovery uses the separate tree system. Golden Carrots use **vanilla crafting**, rather than a rare seed discovery drop. Golden Mushroom Spores use a special **red/brown mushroom break** trigger (mushrooms have no age or maturity property); exact Fortune 0–II rates remain OPEN.

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


### Golden Mushroom and Golden Mushroom Stew — PLANNED; discovery trigger recovered

- One combined **Golden Mushroom Spores** item for both red and brown mushrooms, rather than separate spore types.
- **Natural discovery:** breaking a **vanilla red or brown mushroom** has an independent chance to drop Golden Mushroom Spores. Neither mushroom type has crop maturity stages; do not require a fictional “mature mushroom.”
- Target chance at Fortune III: **1/200**. Fortune 0, I and II rates and interaction with Silk Touch remain **OPEN**; determine whether harvested huge-mushroom blocks are included (not yet approved).
- Spores recipe: **4 gold ingots in corners, red mushrooms left/right, brown mushrooms top/bottom, mushroom stew center**.
- Golden Mushroom Stew recipe: shapeless **2 Golden Mushrooms + 1 Bowl**, not stackable.
- **LOCKED nutrition target:** **10 hunger points (5 drumsticks)** from the Golden Mushroom Stew, superseding the earlier inferred 9. Planned base food saturation remains **10.8**. Vanilla Mushroom Stew restores 6 hunger and 7.2 saturation. Ensure Saturation effect does not accidentally push actual hunger restoration beyond the intended target.
- Status effects: **Jump Boost for 45 seconds** and **Saturation for 0.35 seconds**, modeled on the Saturation variant of vanilla *Suspicious Stew*, not ordinary Mushroom Stew.
- **Important implementation/test note:** the Saturation status effect itself replenishes hunger and saturation, so “10 hunger” may be either the base item nutrition or a target total; currently record **10 base hunger as the working specification** and test the total effect before calling the balance final.
- **Saturation I** is the implementation starting point (amplifier not important to the user); preserve **0.35 seconds / 7 ticks** and tune behavior if needed to deliver the intended 10 hunger points. **OPEN:** precise combined saturation result, Golden Mushroom farming/yields, and exact drop rates below Fortune III.


### Golden Sweet Berries — PLANNED; four-stage growth LOCKED

- Planted directly (no separate seeds); the 20% no-seed mechanic does not apply.
- Recipe JSON: 4 gold ingots corners, 5 sweet berries sides and center.
- Golden Sweet Berries food: **3 hunger, 1.8 saturation**, **Fire Resistance for 15 seconds**.
- Rare Golden Sweet Berries discovery: **1/50 Fortune III** from the **fully mature vanilla Sweet Berry Bush** (vanilla age 3).
- **LOCKED Golden Sweet Berry harvest stages (vanilla-style ages 0–3):** age **0: 0 berries**; age **1: 0 berries**; age **2: 2–3 berries**; age **3: 3–4 berries**. This supersedes the previously discussed age 0–4 proposal.
- **Fortune increases harvest yields and may raise the maximum**; exact per-level bonuses remain **OPEN**.
- **LOCKED Golden Bone Meal behavior:** after a Golden Sweet Berry Bush has been harvested, the player can apply Golden Bone Meal to the still-planted bush. It instantly grows to maturity and marks **only its next right-click berry harvest** for the **1.75× quantity bonus** (after Fortune, with probabilistic rounding). That right-click harvest **consumes and clears the bonus**. The bush remains planted and later harvests are normal unless Golden Bone Meal is applied again. No permanent yield buff or repeated free bonus.
- **OPEN:** Fortune 0–II rare discovery rates and exact growth/harvest implementation.

### Golden Carrot — PLANNED; planting and yields recovered

- Use Minecraft's **existing Golden Carrot item and crafting recipe** (no second custom Golden Carrot item and no Golden Carrot seeds).
- Add the ability to **plant the existing Golden Carrot directly** on farmland. Vanilla Golden Carrots are normally food/brewing ingredients, not plantable crops.
- Proposed/approved Golden Carrot crop harvest ranges (Golden Carrots per mature crop), **with Fortune increasing both minimum and maximum**:

| Tool enchantment | Golden Carrots per mature crop |
|---|---:|
| No Fortune | **1–5** |
| Fortune I | **2–6** |
| Fortune II | **3–7** |
| Fortune III | **4–8** |

- Preserve the existing vanilla Golden Carrot's place as the **best saturation food**. Vanilla stats: **6 hunger, 14.4 saturation**. A nutrition change was **not** explicitly approved, so do **not** blindly apply the Golden Crops 1.5× food rule to this already-vanilla item.
- No rare Golden Carrot discovery from ordinary carrots has been approved; acquisition is through existing vanilla Golden Carrot crafting/trading/etc.
- **OPEN:** immature harvest behavior, exact Fortune yield distribution within listed bounds, growth rate, effects of Golden Bone Meal and Grow Lights, and whether to alter vanilla food values (default is **no**).

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
- **Updated natural growth design:** approximately **1.5× comparable vanilla tree growth time** for Golden Apple Trees; target **at least 5 minutes** in ordinary conditions. Vanilla oak/dark oak growth is random, so these are **mod-design timing targets**, not guaranteed vanilla timings.
- Golden Grow Lights may reduce the above baseline growth duration in their effective area. Specify exact multiplier and minimum under a light during implementation. Growth timing may depend on loaded ticks.
- Golden Apple Trees use **single-tree forms**; **do not generate 2×2 dark-oak mega trees** as the golden variant.
- **OPEN:** exact growth algorithm, sapling variants/texture implementation, harvest bookkeeping, and loaded-chunk performance.

### Enchanted Golden Apple Sapling — PLANNED

Recipe grid:

| | | |
|---|---|---|
| Nether Star | Ghast Tear | Diamond Block |
| Shield | Golden Apple Sapling | Totem of Undying |
| Netherite Ingot | Magma Cream | Enchanted Golden Apple |

- Vanilla bone meal **and Golden Bone Meal** do **not** work on the enchanted sapling.
- **Enchanted Golden Apple Tree growth target:** approximately **2× the comparable vanilla tree growth time**, with a baseline **minimum of 10 minutes**; Golden Grow Lights may reduce that baseline according to their growth bonus. Exact algorithm and illuminated minimum **OPEN**.
- Enchanted Golden Apple Tree uses **one harvest only**, **no fruit regeneration**, and **fewer apples than ordinary Golden Apple Trees**. Fruit-bearing leaves are intended to be rarer; **no exact apple count was recovered**.
- Enchanted Golden Apple Sapling recovery is **chance-based**, never guaranteed to sustain itself indefinitely. **Exact percentage remains OPEN**.
- **OPEN:** enchanted tree shape, exact growth/drop probabilities, yield, sapling-recovery source, and implementation details. Do not invent numbers.
- Existing advancement concept: **Forbidden Orchard** (Challenge).


## 7. Golden Grow Lights — LOCKED core design; other bonuses need specification

- **Current approved design uses three variants:** **Torch, Lantern, Glowstone**, each crafted by surrounding its vanilla light with **8 gold ingots**. An early single-Lantern-only design was a **proposal**, superseded by the later three-variant decision.
- Apply to **Golden Crops and designated Golden Apple Tree growth**; not ordinary vanilla crops.
- Area for crop bonuses: **11×11** (vertical range and how tree saplings count **OPEN**).
- Crop growth speed: approximately **2×** with the light alone.
- Light + Golden Fertilizer: **2.5× maximum combined crop-growth multiplier**.
- Early design additionally envisioned **modest yield improvements** and better chances of sustainable golden planting-material recovery near the light. **Exact values and whether all crop types receive these bonuses remain OPEN**. Do not silently assume a numeric yield bonus.
- **No infinite stacking:** overlapping lights must not multiply the bonus without limit; choose exact overlap rules in implementation.
- Golden Apple Tree and Enchanted Golden Apple Tree natural growth may be accelerated by the light, but exact tree growth rate/minimum under illumination is **OPEN**.
- **OPEN:** technical tick implementation, vertical range, stacking rules, grow-light interaction with Golden Bone Meal bonuses, and exact yield/sustainability effects.


## 8. Golden Fertilizer and irrigation — LOCKED design, not implemented

- Apply Golden Fertilizer to **water** for irrigation effect; **Shift-remove**.
- Planned coverage: **8×8**; precise shape/center **OPEN**.
- Charged farmland makes crops **grow faster across harvests**, not instantly; fertilizer consumption is linked to harvesting.
- Charge cap: **64 per farmland position** (preserve water/farmland data-storage design decision for implementation).
- Obtain fertilizer by using the **Smoker** with eligible raw Golden harvest items. **No prepared stews, baked foods, Golden Apples, or saplings** in this fertilizer conversion system.
- **Recovered Smoker input/output mapping (latest user correction: all prior 3 outputs become 4):**

| Smoker input | Golden Fertilizer output |
|---|---:|
| Golden Sweet Berries | **1** |
| Golden Potato | **2** |
| Golden Beetroot | **2** |
| Golden Melon Slice | **2** |
| Golden Wheat | **4** |
| Golden Carrot | **4** |
| Golden Mushroom | **4** |

- These are outputs **per eligible item cooked**; verify recipe feasibility for existing vanilla Golden Carrot and avoid unintended competing recipes.
- Combined Golden Grow Light + Golden Fertilizer caps crop growth acceleration at **2.5×**.
- **OPEN:** whether the fertilizer is applied/stored on the water block, farmland, or saved data; how water coverage maps to farmland charges; exact consumption per harvest; feedback/visuals, save/load persistence, and conflicts with Smoker food recipes.


## 9. Golden Composter and Golden Bone Meal — LOCKED design, not implemented

### Golden Composter

- **Dedicated custom Golden Composter block**, not a modified vanilla Composter and not a “last Golden item wins” mechanic.
- **Crafting grid:** left/right columns each contain **two slabs** (top and middle); **bottom row has 3 Gold Blocks**; top/middle center slots are empty:

```text
Slab   [empty]  Slab
Slab   [empty]  Slab
Gold Block  Gold Block  Gold Block
```

- **Eligible inputs:** Golden planting materials, seeds/spores, and raw Golden foods/harvests: Golden Wheat Seeds/Wheat, Golden Beetroot Seeds/Beetroot, Golden Melon Seeds/Slices, Golden Potato, Golden Sweet Berries, Golden Mushroom Spores/Mushroom, and Golden Carrot. Extend this list only after checking balance.
- **Excluded:** Golden Apples, Enchanted Golden Apples, both ordinary/enchanted Golden Apple Saplings, stews and other prepared dishes. Exact handling of Golden Baked Potato and Golden Bread follows the prepared-food exclusion; they should **not** count as raw harvests.
- **Deterministic, no chance rolls:** each eligible item adds **exactly one of eight internal contributions**. No random chance for whether the compost increases.
- **Eight eligible items** -> full composter -> collect **one Golden Bone Meal**. Consumes those eight items and resets the composter.
- **Five visual fill stages** including empty: **0, 1, 2, 3, 4 (full)**. Internally track **eight item inputs** and map them to the five appearances; no need for eight separate visual models.
- The earlier concepts of “3–4 successful items,” chance-based filling, vanilla composter mixing, and a possible 1.20× composter yield were **superseded**. The locked product is exactly one Golden Bone Meal per eight valid Golden inputs.
- **OPEN implementation details:** precise internal-count-to-visual-stage thresholds and model, block UI/sounds, handling automation/hoppers, when full compost becomes collectible.

### Golden Bone Meal

- **Single-use instant-growth upgrade:** applying **one Golden Bone Meal to a vanilla or Golden Crop instantly matures that crop**.
- It grants **+75% yield (1.75×)** on **that one crop's next harvest only**. No permanent boost to the underlying farmland.
- **Fortune first, then 1.75× bonus** for applicable harvested-item quantities.
- Use **probabilistic rounding** to preserve fractional expected yields: e.g., a 5.25 calculated amount becomes **5 items, with a 25% chance of a sixth**.
- **LOCKED (Option A):** the **1.75× bonus applies to both harvested crops/foods and their planting-material drops (seeds, etc.)**, for eligible vanilla and Golden Crops. Calculate ordinary drops including Fortune first, then apply 1.75× with probabilistic rounding to each eligible drop quantity.
- Preserve the Golden seed-producing crop's independent **20% no-seed chance**: if the seed roll fails, no seeds are produced and the multiplier does not create any. **Rare golden discovery probabilities are not multiplied** by Golden Bone Meal; only quantities of ordinary eligible harvest drops are boosted.
- **LOCKED renewable bush exception:** Golden Sweet Berry Bushes can receive Golden Bone Meal **after the previous right-click harvest** while the bush remains planted; it instantly matures the bush, and **the next right-click berry harvest receives the one-time 1.75× bonus**. Clear the bonus flag as soon as that harvest happens; no subsequent berry harvest receives it without another application. The bonus is tied to the bush, not the farmland.
- Also usable on an **ordinary Golden Apple Sapling** to accelerate or trigger its growth. **Neither vanilla bone meal nor Golden Bone Meal works on the Enchanted Golden Apple Sapling**; vanilla bone meal does not work on ordinary Golden Apple Saplings either.
- **OPEN:** whether Golden Bone Meal instantly grows the ordinary Golden Apple Sapling or instead makes significant growth progress; interactions with trees, Grow Lights and fertilizer; exact technical implementation of the now-locked one-harvest persistent flag for right-click berry harvesting.
- The earlier 1.20× value was associated with a tentative **composter** output idea, **not** the locked 1.75× Golden Bone Meal harvest bonus.

## 10. Advancements — LOCKED names and tiers

**Tasks:** Golden Agriculture; Worth Its Weight in Gold; Money Does Grow on Trees.  
**Goals:** Golden Fields; Golden Harvest; Golden Diet; Liquid Gold; Let There Be Gold.  
**Challenge:** Forbidden Orchard.

- **The advancement tree, names, frame types and requirements were previously described as locked for 1.0**, but the pasted recovery text only contains names/frame types. **Exact per-advancement triggering rules have not been recovered yet**: search original advancement planning before writing advancement JSONs.
- Recorded overall milestone ideas (not an authoritative mapping to the nine named advancements): **plant first Golden Crop, harvest first Golden Crop, plant every Golden Crop, harvest every Golden Crop, eat every Golden Food**, and obtain/grow an Enchanted Golden Apple Tree as an endgame Challenge.
- Earlier guiding rule: first-item discoveries are Tasks, all/every collection achievements are Goals, enchanted tree is Challenge.
- Validate triggers against actual shipped crops/items, including light and fertilizer prerequisites where appropriate.

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
7. Advancements; verify ordinary crafting recipe visibility in JEI/REI/EMI, with custom integration only if actually needed.
8. Compatibility, balance, regression tests, releases. **Fabric first; Forge/NeoForge is a potential future separate port**. Decide future Minecraft version groups after comparing APIs; do not assume a single JAR can span 1.20–26.3.

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
- **1.0 scope:** Golden Crops' nine-plant roster; farming mechanics in this plan. **Villager interaction/trading changes deferred** to later updates.
- **Fabric first**, with future Forge/NeoForge support requiring a distinct port. Select future version groups based on tested APIs, not arbitrary blanket 1.20–26.3 claims.
- **JEI/REI/EMI:** ordinary Minecraft recipe JSONs should generally appear through recipe viewers; only develop custom integration if a custom crafting/farming mechanic demonstrably needs it.
- Keep changelogs distinct from design proposals: **planned**, **implemented**, **tested**, **released**.

## 14. Known ambiguities and corrections to preserve

- **Golden Potato:** initial mature yield 2–5 was revised to **2–6**. Golden Baked Potato changed from Regeneration I for 5–10 seconds to **Regeneration II for 8 seconds**.
- **Golden Apple Trees:** old possible 10–25% sapling/return and random exhaustion discussions were superseded by **final-harvest 5/10/15/20% sapling chances** and **guaranteed exhaustion after second harvest**.
- **Golden Melon:** normal melon blocks do not drop ordinary seeds. Only **vanilla melon blocks** get the additional rare golden-seed roll. Golden melon blocks drop slices or Silk Touch block only.
- **Golden Beetroot:** intended 0.60%/0.70% mid-Fortune rates are approximated by 1/167 and 1/143, not identical.
- **Golden Carrot:** vanilla already has a Golden Carrot food; do not accidentally overwrite vanilla food properties without a conscious design decision.
- **Golden Mushroom:** “only mature vanilla crops” cannot literally apply to mushrooms; **breaking a vanilla red or brown mushroom** is the recovered discovery trigger. Huge-mushroom blocks remain unapproved.
- **Golden Mushroom Stew:** 10 hunger was explicitly recovered later, superseding the earlier inferred 9; Saturation effect 0.35 seconds follows *Suspicious Stew*, not vanilla ordinary Mushroom Stew.
- **Golden Sweet Berries:** user clarified four stages age **0–3**, with **0, 0, 2–3, 3–4** Golden Berries respectively; this supersedes the accidental five-stage age 0–4 proposal. Rare discovery remains from the mature vanilla bush, not each berry item.
- **Golden Composter:** exactly eight guaranteed golden-item contributions and one Golden Bone Meal; the chance-based/3–4-input proposals are obsolete.
- **Golden Bone Meal:** 1.75× crop next-harvest yield with probabilistic rounding; separate from Golden Fertilizer/Grow Light growth bonuses.
- **Golden Fertilizer:** the user's latest correction changed all three fertilizer-output categories **from 3 to 4**, while 1 and 2 stay unchanged.
- **Golden Apple Trees:** baseline growth ~1.5× vanilla, at least 5 minutes; Enchanted baseline ~2× vanilla, at least 10 minutes; Grow Lights may accelerate, but implementation rates remain open.
- **Villager interactions and special trades:** intentionally **deferred to future updates**, not 1.0.
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
