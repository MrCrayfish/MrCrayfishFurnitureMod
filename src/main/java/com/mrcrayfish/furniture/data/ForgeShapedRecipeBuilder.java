package com.mrcrayfish.furniture.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Since Forge allows NBT tag on the output of the crafting item, this builder allows you to set
 * that tag. This is used only for data generators.
 *
 * Author: MrCrayfish
 */
public class ForgeShapedRecipeBuilder
{
    private final String key;
    private final ItemStack result;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> ingredientMap = Maps.newLinkedHashMap();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private String group;

    private ForgeShapedRecipeBuilder(String key, ItemStack resultIn)
    {
        this.key = key;
        this.result = resultIn.copy();
    }

    public static ForgeShapedRecipeBuilder shapedRecipe(String key, ItemStack resultIn)
    {
        return new ForgeShapedRecipeBuilder(key, resultIn);
    }

    public ForgeShapedRecipeBuilder key(Character symbol, TagKey<Item> tagIn)
    {
        return this.key(symbol, Ingredient.of(tagIn));
    }

    public ForgeShapedRecipeBuilder key(Character symbol, ItemLike itemIn)
    {
        return this.key(symbol, Ingredient.of(itemIn));
    }

    public ForgeShapedRecipeBuilder key(Character symbol, Ingredient ingredientIn)
    {
        if(this.ingredientMap.containsKey(symbol))
        {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        }
        else if(symbol == ' ')
        {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        }
        else
        {
            this.ingredientMap.put(symbol, ingredientIn);
            return this;
        }
    }

    public ForgeShapedRecipeBuilder patternLine(String patternIn)
    {
        if(!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length())
        {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        }
        else
        {
            this.pattern.add(patternIn);
            return this;
        }
    }

    public ForgeShapedRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterion)
    {
        this.advancementBuilder.addCriterion(name, criterion);
        return this;
    }

    public ForgeShapedRecipeBuilder setGroup(String groupIn)
    {
        this.group = groupIn;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn)
    {
        this.build(consumerIn, Registry.ITEM.getKey(this.result.getItem()));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save)
    {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result.getItem());
        if((new ResourceLocation(save)).equals(resourcelocation))
        {
            throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        }
        else
        {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id)
    {
        this.validate(id);
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumerIn.accept(new Result(this.key, id, this.result, this.group == null ? "" : this.group, this.pattern, this.ingredientMap, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItem().getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
    }

    /**
     * Makes sure that this recipe is valid and obtainable.
     */
    private void validate(ResourceLocation id)
    {
        if(this.pattern.isEmpty())
        {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        }
        else
        {
            Set<Character> set = Sets.newHashSet(this.ingredientMap.keySet());
            set.remove(' ');

            for(String s : this.pattern)
            {
                for(int i = 0; i < s.length(); ++i)
                {
                    char c0 = s.charAt(i);
                    if(!this.ingredientMap.containsKey(c0) && c0 != ' ')
                    {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if(!set.isEmpty())
            {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            }
            else if(this.pattern.size() == 1 && this.pattern.get(0).length() == 1)
            {
                throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            }
            else if(this.advancementBuilder.getCriteria().isEmpty())
            {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }

    public static class Result implements FinishedRecipe
    {
        private final String key;
        private final ResourceLocation id;
        private final ItemStack result;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> ingredientMap;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(String key, ResourceLocation idIn, ItemStack resultIn, String groupIn, List<String> patternIn, Map<Character, Ingredient> keyIn, Advancement.Builder advancementBuilderIn, ResourceLocation advancementIdIn)
        {
            this.key = key;
            this.id = idIn;
            this.result = resultIn;
            this.group = groupIn;
            this.pattern = patternIn;
            this.ingredientMap = keyIn;
            this.advancementBuilder = advancementBuilderIn;
            this.advancementId = advancementIdIn;
        }

        public void serializeRecipeData(JsonObject json)
        {
            if(!this.group.isEmpty())
            {
                json.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for(String s : this.pattern)
            {
                jsonarray.add(s);
            }

            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();

            for(Map.Entry<Character, Ingredient> entry : this.ingredientMap.entrySet())
            {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }

            json.add("key", jsonobject);
            JsonObject result = new JsonObject();
            result.addProperty("item", Registry.ITEM.getKey(this.result.getItem()).toString());
            if(this.result.getCount() > 1)
            {
                result.addProperty("count", this.result.getCount());
            }
            if(this.result.getTag() != null)
            {
                result.addProperty("nbt", this.result.getTag().toString());
            }
            json.add("result", result);
        }

        //TODO figure out what happened to serializers
        public RecipeSerializer<?> getType()
        {
            return RecipeSerializer.SHAPED_RECIPE;
        }

        public ResourceLocation getId()
        {
            return new ResourceLocation(this.id.getNamespace(), this.key);
        }

        @Nullable
        public JsonObject serializeAdvancement()
        {
            return this.advancementBuilder.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
