package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day21 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Food> foods = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(" \\(contains ");
			String[] ingredientParts = parts[0].split(" ");
			String[] allergenParts = parts[1].replace(")", "").split(", ");
			
			foods.add(new Food(Arrays.asList(ingredientParts), Arrays.asList(allergenParts)));
		}
		
		Set<String> invalidIngredients = new HashSet<>();
		for (int i = 0; i < foods.size(); i++) {
			Food food = foods.get(i);
			
			Set<String> foundIngredients = new HashSet<>();
			for (String allergen : food.getAllergens()) {
				for (int j = 0; j < foods.size(); j++) {
					if (i == j) {
						continue;
					}
					Food otherFood = foods.get(j);
					if (otherFood.getAllergens().contains(allergen)) {
						foundIngredients.addAll(otherFood.getIngredients());
					}
				}
			}
			
			if (!foundIngredients.isEmpty()) {
				for (String ingredient : food.getIngredients()) {
					if (!foundIngredients.contains(ingredient)) {
						invalidIngredients.add(ingredient);
					}
				}
			} else {
				invalidIngredients.removeAll(food.getIngredients());
			}
		}
		// 103 too low
		log(invalidIngredients);
		
		int sum = 0;
		for (Food food : foods) {
			for (String ingredient : food.getIngredients()) {
				if (invalidIngredients.contains(ingredient)) {
					sum++;
				}
			}
		}
		
		return sum;
	}
	
	@Data
	@AllArgsConstructor
	private class Food {
		private List<String> ingredients;
		private List<String> allergens;
	}
}