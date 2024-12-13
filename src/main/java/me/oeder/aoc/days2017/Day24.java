package me.oeder.aoc.days2017;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day24 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Component> components = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split("/");
			components.add(new Component(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}

		List<List<Component>> arrangements = new ArrayList<>();
		populateArrangements(arrangements, new ArrayList<>(), components);

		int maxStrength = Integer.MIN_VALUE;
		int maxLength = Integer.MIN_VALUE;
		for (List<Component> arrangement : arrangements) {
			if (part == Part.ONE) {
				maxStrength = Math.max(maxStrength, getStrength(arrangement));
			} else {
				if (arrangement.size() > maxLength) {
					maxStrength = getStrength(arrangement);
					maxLength = arrangement.size();
				} else if (arrangement.size() == maxLength) {
					maxStrength = Math.max(maxStrength, getStrength(arrangement));
				}
			}
		}

		return maxStrength;
	}

	private void populateArrangements(List<List<Component>> arrangements, List<Component> partialArrangement, List<Component> remainingComponents) {
		if (remainingComponents.isEmpty() || !hasValidComponents(remainingComponents, partialArrangement)) {
			arrangements.add(partialArrangement);
			return;
		}

		for (int i = 0; i < remainingComponents.size(); i++) {
			Component component = remainingComponents.get(i);

			if (!isComponentValid(component, partialArrangement)) {
				continue;
			}

			if (isComponentReversalNeeded(component, partialArrangement)) {
				component = new Component(component.getRightPort(), component.getLeftPort());
			}

			List<Component> newPartialArrangement = new ArrayList<>();
			newPartialArrangement.addAll(partialArrangement);
			newPartialArrangement.add(component);

			List<Component> newRemainingComponents = new ArrayList<>();
			for (int j = 0; j < remainingComponents.size(); j++) {
				if (i != j) {
					newRemainingComponents.add(remainingComponents.get(j));
				}
			}

			populateArrangements(arrangements, newPartialArrangement, newRemainingComponents);
		}
	}

	private boolean hasValidComponents(List<Component> remainingComponents, List<Component> partialArrangement) {
		if (partialArrangement.isEmpty()) {
			return true;
		}
		for (Component component : remainingComponents) {
			if (isComponentValid(component, partialArrangement)) {
				return true;
			}
		}
		return false;
	}

	private boolean isComponentValid(Component component, List<Component> partialArrangement) {
		if (partialArrangement.isEmpty()) {
			return component.getLeftPort() == 0 || component.getRightPort() == 0;
		} else {
			Component prevComponent = partialArrangement.get(partialArrangement.size() - 1);
			return prevComponent.getRightPort() == component.getLeftPort() || prevComponent.getRightPort() == component.getRightPort();
		}
	}

	private boolean isComponentReversalNeeded(Component component, List<Component> partialArrangement) {
		if (partialArrangement.isEmpty()) {
			return component.getRightPort() == 0;
		} else {
			Component prevComponent = partialArrangement.get(partialArrangement.size() - 1);
			return prevComponent.getRightPort() == component.getRightPort();
		}
	}

	private int getStrength(List<Component> arrangement) {
		int strength = 0;
		for (Component component : arrangement) {
			strength += component.getLeftPort() + component.getRightPort();
		}
		return strength;
	}

	@Data
	@AllArgsConstructor
	private class Component {
		private int leftPort;
		private int rightPort;
	}
}