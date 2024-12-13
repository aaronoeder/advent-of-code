package me.oeder.aoc.days2015;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day21 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int bossHp = Integer.parseInt(lines.get(0).split(": ")[1]);
		int bossDamage = Integer.parseInt(lines.get(1).split(": ")[1]);
		int bossArmor = Integer.parseInt(lines.get(2).split(": ")[1]);

		List<GearItem> possibleWeapons = new ArrayList<>();
		possibleWeapons.add(new GearItem("Dagger", 8, 4, 0));
		possibleWeapons.add(new GearItem("Shortsword", 10, 5, 0));
		possibleWeapons.add(new GearItem("Warhammer", 25, 6, 0));
		possibleWeapons.add(new GearItem("Longsword", 40, 7, 0));
		possibleWeapons.add(new GearItem("Greataxe", 74, 8, 0));

		List<GearItem> possibleArmor = new ArrayList<>();
		possibleArmor.add(new GearItem("Leather", 13, 0, 1));
		possibleArmor.add(new GearItem("Chainmail", 31, 0, 2));
		possibleArmor.add(new GearItem("Splintmail", 53, 0, 3));
		possibleArmor.add(new GearItem("Bandedmail", 75, 0, 4));
		possibleArmor.add(new GearItem("Platemail", 102, 0, 5));

		List<GearItem> possibleRings = new ArrayList<>();
		possibleRings.add(new GearItem("Damage +1", 25, 1, 0));
		possibleRings.add(new GearItem("Damage +2", 50, 2, 0));
		possibleRings.add(new GearItem("Damage +3", 100, 3, 0));
		possibleRings.add(new GearItem("Defense +1", 20, 0, 1));
		possibleRings.add(new GearItem("Defense +2", 40, 0, 2));
		possibleRings.add(new GearItem("Defense +3", 80, 0, 3));

		List<Gear> gears = new ArrayList<>();
		populateGearCombinations(gears, new Gear(), possibleWeapons, possibleArmor, possibleRings);

		int minCost = Integer.MAX_VALUE;
		int maxCost = Integer.MIN_VALUE;
		for (Gear gear : gears) {
			int cost = gear.getWeapon().getCost();
			int damage = gear.getWeapon().getDamage();
			int armor = gear.getWeapon().getArmor();
			if (gear.getArmor() != null) {
				cost += gear.getArmor().getCost();
				damage += gear.getArmor().getDamage();
				armor += gear.getArmor().getArmor();
			}
			for (GearItem ring : gear.getRings()) {
				cost += ring.getCost();
				damage += ring.getDamage();
				armor += ring.getArmor();
			}

			Entity player = new Entity(100, damage, armor);
			Entity boss = new Entity(bossHp, bossDamage, bossArmor);
			int playerHealthRemaining = getPlayerHealthRemaining(player, boss);

			if (playerHealthRemaining > 0 && cost < minCost) {
				minCost = cost;
			}
			if (playerHealthRemaining == 0 && cost > maxCost) {
				maxCost = cost;
			}
		}

		if (part == Part.ONE) {
			return minCost;
		}
		return maxCost;
	}

	private void populateGearCombinations(List<Gear> gears, Gear gear, List<GearItem> possibleWeapons, List<GearItem> possibleArmor, List<GearItem> possibleRings) {
		if (gear.getWeapon() != null && gear.isSelectedArmor() && gear.isSelectedRings()) {
			gears.add(gear);
		} else if (gear.getWeapon() == null) {
			for (GearItem weapon : possibleWeapons) {
				Gear deepCopyOfGearWeapon = getDeepCopyOfGear(gear);
				deepCopyOfGearWeapon.setWeapon(weapon);
				populateGearCombinations(gears, deepCopyOfGearWeapon, possibleWeapons, possibleArmor, possibleRings);
			}
		} else if (!gear.isSelectedArmor()) {
			Gear deepCopyOfGearNoArmor = getDeepCopyOfGear(gear);
			deepCopyOfGearNoArmor.setSelectedArmor(true);
			populateGearCombinations(gears, deepCopyOfGearNoArmor, possibleWeapons, possibleArmor, possibleRings);

			for (GearItem armor : possibleArmor) {
				Gear deepCopyOfGearArmor = getDeepCopyOfGear(gear);
				deepCopyOfGearArmor.setArmor(armor);
				deepCopyOfGearArmor.setSelectedArmor(true);
				populateGearCombinations(gears, deepCopyOfGearArmor, possibleWeapons, possibleArmor, possibleRings);
			}
		} else if (!gear.isSelectedRings()) {
			if (gear.getSelectedRingCount() == 0) {
				Gear deepCopyOfGear0Rings = getDeepCopyOfGear(gear);
				deepCopyOfGear0Rings.setSelectedRings(true);
				populateGearCombinations(gears, deepCopyOfGear0Rings, possibleWeapons, possibleArmor, possibleRings);

				Gear deepCopyOfGear1Rings = getDeepCopyOfGear(gear);
				deepCopyOfGear1Rings.setSelectedRingCount(1);
				populateGearCombinations(gears, deepCopyOfGear1Rings, possibleWeapons, possibleArmor, possibleRings);

				Gear deepCopyOfGear2Rings = getDeepCopyOfGear(gear);
				deepCopyOfGear2Rings.setSelectedRingCount(2);
				populateGearCombinations(gears, deepCopyOfGear2Rings, possibleWeapons, possibleArmor, possibleRings);
			} else if (gear.getRings().size() < gear.getSelectedRingCount()) {
				for (GearItem ring : possibleRings) {
					if (!gear.getRings().contains(ring)) {
						Gear deepCopyOfGearAdditionalRing = getDeepCopyOfGear(gear);
						deepCopyOfGearAdditionalRing.getRings().add(ring);
						if (deepCopyOfGearAdditionalRing.getRings().size() == deepCopyOfGearAdditionalRing.getSelectedRingCount()) {
							deepCopyOfGearAdditionalRing.setSelectedRings(true);
						}
						populateGearCombinations(gears, deepCopyOfGearAdditionalRing, possibleWeapons, possibleArmor, possibleRings);
					}
				}
			}
		}
	}

	private Gear getDeepCopyOfGear(Gear gear) {
		List<GearItem> rings = new ArrayList<>(gear.getRings());
		return new Gear(gear.getWeapon(), gear.getArmor(), rings, gear.isSelectedArmor(), gear.isSelectedRings(), gear.getSelectedRingCount());
	}

	private int getPlayerHealthRemaining(Entity player, Entity boss) {
		boolean playerTurn = true;
		while (true) {
			if (playerTurn) {
				boss.setHp(boss.getHp() - Math.max(1, player.getDamage() - boss.getArmor()));
				if (boss.getHp() <= 0) {
					break;
				}
			} else {
				player.setHp(player.getHp() - Math.max(1, boss.getDamage() - player.getArmor()));
				if (player.getHp() <= 0) {
					break;
				}
			}
			playerTurn = !playerTurn;
		}
		return player.getHp();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Gear {
		private GearItem weapon;
		private GearItem armor;
		private List<GearItem> rings = new ArrayList<>();
		private boolean selectedArmor;
		private boolean selectedRings;
		private int selectedRingCount;
	}

	@Data
	@AllArgsConstructor
	private static class GearItem {
		private String name;
		private int cost;
		private int damage;
		private int armor;
	}

	@Data
	@AllArgsConstructor
	private static class Entity {
		private int hp;
		private int damage;
		private int armor;
	}
}