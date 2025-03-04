package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.crafting.ClearInkRecipe;
import de.dafuqs.spectrum.recipe.crafting.RepairAnythingRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.spawner.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpectrumCustomRecipeSerializers {
	
	private static void register(RecipeSerializer recipeSerializer, String id) {
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(SpectrumCommon.MOD_ID, id), recipeSerializer);
	}
	
	public static void registerRecipeSerializers() {
		// Crafting
		register(RepairAnythingRecipe.SERIALIZER, "repair_anything");
		register(ClearInkRecipe.SERIALIZER, "clear_ink");
		
		// Spirit Instiller
		register(SpawnerCreatureChangeRecipe.SERIALIZER, "spirit_instiller_spawner_creature_change");
		register(SpawnerMaxNearbyEntitiesChangeRecipe.SERIALIZER, "spirit_instiller_spawner_max_nearby_entities_change");
		register(SpawnerRequiredPlayerRangeChangeRecipe.SERIALIZER, "spirit_instiller_spawner_spawner_player_range_change");
		register(SpawnerSpawnCountChangeRecipe.SERIALIZER, "spirit_instiller_spawner_spawn_count_change");
		register(SpawnerSpawnDelayChangeRecipe.SERIALIZER, "spirit_instiller_spawner_spawn_delay_change");
		
	}
	
}
