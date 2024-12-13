package me.oeder.aoc.days2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day15 extends AdventDay {
	
	@Data
	@AllArgsConstructor
	private class Ingredient {
		private String name;
		private int capacity;
		private int durability;
		private int flavor;
		private int texture;
		private int calories;
	}

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Ingredient> ingredients = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(": ");
			String[] properties = parts[1].split(", ");
			ingredients.add(new Ingredient(parts[0], 
					Integer.parseInt(properties[0].split(" ")[1]),
					Integer.parseInt(properties[1].split(" ")[1]),
					Integer.parseInt(properties[2].split(" ")[1]),
					Integer.parseInt(properties[3].split(" ")[1]),
					Integer.parseInt(properties[4].split(" ")[1])));
		}
		
		List<Map<Ingredient, Integer>> recipes = new ArrayList<>();
		populateRecipes(100, ingredients, new HashMap<>(), recipes);
		
		int max = Integer.MIN_VALUE;
		for (Map<Ingredient, Integer> recipe : recipes) {
			int score = getScore(recipe);
			if (score > max) {
				if (part == Part.ONE) { 
					max = score;
				} else {
					int calories = getCalories(recipe);
					if (calories == 500) {
						max = score;
					}
				}
			}
		}
		
		return max;
		
	}
	
	private void populateRecipes(int remaining, List<Ingredient> ingredients, Map<Ingredient, Integer> recipe, List<Map<Ingredient, Integer>> recipes) {
		if (remaining == 0) {
			recipes.add(recipe);
		} else {
			for (Ingredient ingredient : ingredients) {
				if (recipe.keySet().contains(ingredient)) {
					continue;
				}
				for (int i = 1; i <= remaining; i++) {
					Map<Ingredient, Integer> newRecipe = new HashMap<>();
					newRecipe.putAll(recipe);
					newRecipe.put(ingredient, i);
					populateRecipes(remaining - i, ingredients, newRecipe, recipes);
				}
			}
		}
	}
	
	private int getScore(Map<Ingredient, Integer> recipe) {
		int capacity = 0;
		int durability = 0;
		int flavor = 0;
		int texture = 0;
		for (Map.Entry<Ingredient, Integer> entry : recipe.entrySet()) {
			capacity += entry.getKey().getCapacity() * entry.getValue();
			durability += entry.getKey().getDurability() * entry.getValue();
			flavor += entry.getKey().getFlavor() * entry.getValue();
			texture += entry.getKey().getTexture() * entry.getValue();
		}
		if (capacity < 0 || durability < 0 || flavor < 0 || texture < 0) {
			return 0;
		}
		return capacity * durability * flavor * texture;
	}
	
	private int getCalories(Map<Ingredient, Integer> recipe) {
		int calories = 0;
		for (Map.Entry<Ingredient, Integer> entry : recipe.entrySet()) {
			calories += entry.getKey().getCalories() * entry.getValue();
		}
		return Math.max(0, calories);
	}
}