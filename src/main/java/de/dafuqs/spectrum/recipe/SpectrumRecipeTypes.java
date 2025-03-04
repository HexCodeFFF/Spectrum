package de.dafuqs.spectrum.recipe;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipeSerializer;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipeSerializer;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipe;
import de.dafuqs.spectrum.recipe.crystallarieum.CrystallarieumRecipeSerializer;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipeSerializer;
import de.dafuqs.spectrum.recipe.enchantment_upgrade.EnchantmentUpgradeRecipe;
import de.dafuqs.spectrum.recipe.enchantment_upgrade.EnchantmentUpgradeRecipeSerializer;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipeSerializer;
import de.dafuqs.spectrum.recipe.ink_converting.InkConvertingRecipe;
import de.dafuqs.spectrum.recipe.ink_converting.InkConvertingRecipeSerializer;
import de.dafuqs.spectrum.recipe.midnight_solution_converting.MidnightSolutionConvertingRecipe;
import de.dafuqs.spectrum.recipe.midnight_solution_converting.MidnightSolutionConvertingRecipeSerializer;
import de.dafuqs.spectrum.recipe.pedestal.PedestalCraftingRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalCraftingRecipeSerializer;
import de.dafuqs.spectrum.recipe.potion_workshop.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.ISpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipeSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SpectrumRecipeTypes {
	
	public static RecipeSerializer<PedestalCraftingRecipe> PEDESTAL_RECIPE_SERIALIZER;
	public static RecipeType<PedestalCraftingRecipe> PEDESTAL;
	public static RecipeSerializer<AnvilCrushingRecipe> ANVIL_CRUSHING_RECIPE_SERIALIZER;
	public static RecipeType<AnvilCrushingRecipe> ANVIL_CRUSHING;
	public static RecipeSerializer<FusionShrineRecipe> FUSION_SHRINE_RECIPE_SERIALIZER;
	public static RecipeType<FusionShrineRecipe> FUSION_SHRINE;
	public static RecipeSerializer<EnchanterRecipe> ENCHANTER_RECIPE_SERIALIZER;
	public static RecipeType<EnchanterRecipe> ENCHANTER;
	public static RecipeSerializer<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER;
	public static RecipeType<EnchantmentUpgradeRecipe> ENCHANTMENT_UPGRADE;
	public static RecipeSerializer<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING_RECIPE_SERIALIZER;
	public static RecipeType<PotionWorkshopBrewingRecipe> POTION_WORKSHOP_BREWING;
	public static RecipeSerializer<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING_RECIPE_SERIALIZER;
	public static RecipeType<PotionWorkshopCraftingRecipe> POTION_WORKSHOP_CRAFTING;
	public static RecipeSerializer<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING_SERIALIZER;
	public static RecipeType<PotionWorkshopReactingRecipe> POTION_WORKSHOP_REACTING;
	public static RecipeSerializer<MidnightSolutionConvertingRecipe> MIDNIGHT_SOLUTION_CONVERTING_RECIPE_SERIALIZER;
	public static RecipeType<MidnightSolutionConvertingRecipe> MIDNIGHT_SOLUTION_CONVERTING_RECIPE;
	public static RecipeSerializer<SpiritInstillerRecipe> SPIRIT_INSTILLING_SERIALIZER;
	public static RecipeType<ISpiritInstillerRecipe> SPIRIT_INSTILLING;
	public static RecipeSerializer<InkConvertingRecipe> INK_CONVERTING_RECIPE_SERIALIZER;
	public static RecipeType<InkConvertingRecipe> INK_CONVERTING;
	public static RecipeSerializer<CrystallarieumRecipe> CRYSTALLARIEUM_RECIPE_SERIALIZER;
	public static RecipeType<CrystallarieumRecipe> CRYSTALLARIEUM;
	public static RecipeSerializer<CinderhearthRecipe> CINDERHEARTH_RECIPE_SERIALIZER;
	public static RecipeType<CinderhearthRecipe> CINDERHEARTH;
	
	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerSerializer(String id, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(SpectrumCommon.MOD_ID, id), serializer);
	}
	
	static <S extends RecipeType<T>, T extends Recipe<?>> S registerRecipeType(String id, S serializer) {
		return Registry.register(Registry.RECIPE_TYPE, new Identifier(SpectrumCommon.MOD_ID, id), serializer);
	}
	
	public static void registerSerializer() {
		PEDESTAL_RECIPE_SERIALIZER = registerSerializer("pedestal", new PedestalCraftingRecipeSerializer(PedestalCraftingRecipe::new));
		PEDESTAL = registerRecipeType("pedestal", new RecipeType<PedestalCraftingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:pedestal";
			}
		});
		
		ANVIL_CRUSHING_RECIPE_SERIALIZER = registerSerializer("anvil_crushing", new AnvilCrushingRecipeSerializer(AnvilCrushingRecipe::new));
		ANVIL_CRUSHING = registerRecipeType("anvil_crushing", new RecipeType<AnvilCrushingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:anvil_crushing";
			}
		});
		
		FUSION_SHRINE_RECIPE_SERIALIZER = registerSerializer("fusion_shrine", new FusionShrineRecipeSerializer(FusionShrineRecipe::new));
		FUSION_SHRINE = registerRecipeType("fusion_shrine", new RecipeType<FusionShrineRecipe>() {
			@Override
			public String toString() {
				return "spectrum:fusion_shrine";
			}
		});
		
		ENCHANTER_RECIPE_SERIALIZER = registerSerializer("enchanter", new EnchanterRecipeSerializer(EnchanterRecipe::new));
		ENCHANTER = registerRecipeType("enchanter", new RecipeType<EnchanterRecipe>() {
			@Override
			public String toString() {
				return "spectrum:enchanter";
			}
		});
		
		ENCHANTMENT_UPGRADE_RECIPE_SERIALIZER = registerSerializer("enchantment_upgrade", new EnchantmentUpgradeRecipeSerializer(EnchantmentUpgradeRecipe::new));
		ENCHANTMENT_UPGRADE = registerRecipeType("enchantment_upgrade", new RecipeType<EnchantmentUpgradeRecipe>() {
			@Override
			public String toString() {
				return "spectrum:enchantment_upgrade";
			}
		});
		
		POTION_WORKSHOP_BREWING_RECIPE_SERIALIZER = registerSerializer("potion_workshop_brewing", new PotionWorkshopBrewingRecipeSerializer(PotionWorkshopBrewingRecipe::new));
		POTION_WORKSHOP_BREWING = registerRecipeType("potion_workshop_brewing", new RecipeType<PotionWorkshopBrewingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:potion_workshop_brewing";
			}
		});
		
		POTION_WORKSHOP_CRAFTING_RECIPE_SERIALIZER = registerSerializer("potion_workshop_crafting", new PotionWorkshopCraftingRecipeSerializer(PotionWorkshopCraftingRecipe::new));
		POTION_WORKSHOP_CRAFTING = registerRecipeType("potion_workshop_crafting", new RecipeType<PotionWorkshopCraftingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:potion_workshop_crafting";
			}
		});
		
		POTION_WORKSHOP_REACTING_SERIALIZER = registerSerializer("potion_workshop_reacting", new PotionWorkshopReactingRecipeSerializer(PotionWorkshopReactingRecipe::new));
		POTION_WORKSHOP_REACTING = registerRecipeType("potion_workshop_reacting", new RecipeType<PotionWorkshopReactingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:potion_workshop_reacting";
			}
		});
		
		MIDNIGHT_SOLUTION_CONVERTING_RECIPE_SERIALIZER = registerSerializer("midnight_solution_converting", new MidnightSolutionConvertingRecipeSerializer(MidnightSolutionConvertingRecipe::new));
		MIDNIGHT_SOLUTION_CONVERTING_RECIPE = registerRecipeType("midnight_solution_converting", new RecipeType<MidnightSolutionConvertingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:midnight_solution_converting";
			}
		});
		
		SPIRIT_INSTILLING_SERIALIZER = registerSerializer("spirit_instiller", new SpiritInstillerRecipeSerializer(SpiritInstillerRecipe::new));
		SPIRIT_INSTILLING = registerRecipeType("spirit_instiller", new RecipeType<ISpiritInstillerRecipe>() {
			@Override
			public String toString() {
				return "spectrum:spirit_instiller";
			}
		});
		
		INK_CONVERTING_RECIPE_SERIALIZER = registerSerializer("ink_converting", new InkConvertingRecipeSerializer(InkConvertingRecipe::new));
		INK_CONVERTING = registerRecipeType("ink_converting", new RecipeType<InkConvertingRecipe>() {
			@Override
			public String toString() {
				return "spectrum:ink_converting";
			}
		});
		
		CRYSTALLARIEUM_RECIPE_SERIALIZER = registerSerializer("crystallarieum_growing", new CrystallarieumRecipeSerializer(CrystallarieumRecipe::new));
		CRYSTALLARIEUM = registerRecipeType("crystallarieum_growing", new RecipeType<CrystallarieumRecipe>() {
			@Override
			public String toString() {
				return "spectrum:crystallarieum_growing";
			}
		});
		
		CINDERHEARTH_RECIPE_SERIALIZER = registerSerializer("cinderhearth", new CinderhearthRecipeSerializer(CinderhearthRecipe::new));
		CINDERHEARTH = registerRecipeType("cinderhearth", new RecipeType<CinderhearthRecipe>() {
			@Override
			public String toString() {
				return "spectrum:cinderhearth";
			}
		});
		
	}
	
}
