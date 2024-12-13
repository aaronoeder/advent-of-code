package me.oeder.aoc.days2017;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {

		List<Particle> particles = new ArrayList<>();

		Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				particles.add(
						new Particle(
								new Coord3D(getInt(matcher, 1), getInt(matcher, 2), getInt(matcher, 3)),
								new Coord3D(getInt(matcher, 4), getInt(matcher, 5), getInt(matcher, 6)),
								new Coord3D(getInt(matcher, 7), getInt(matcher, 8), getInt(matcher, 9))
						)
				);
			}
		}

		for (Particle p : particles) {
			log(p);
		}

		Map<Integer, Integer> closest = new HashMap<>();
		while (closest.size() < 10000000) {
			int minDistance = Integer.MAX_VALUE;
			int minParticleId = -1;
			for (int i = 0; i < particles.size(); i++) {
				Particle particle = particles.get(i);
				particle.getVelocity().add(particle.getAcceleration());
				particle.getPosition().add(particle.getVelocity());

				int dist = particle.getPosition().getMagnitude();
				if (dist < minDistance) {
					minDistance = dist;
					minParticleId = i + 1;
				}
			}

			closest.put(closest.size(), minParticleId);
		}

		List<Integer> ids = new ArrayList<>(closest.values());
		Collections.sort(ids);

		int maxId = -1;
		int maxLength = Integer.MIN_VALUE;
		int currentId = -1;
		int currentRun = 0;
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			if (currentId == -1) {
				currentId = id;
				currentRun = 1;
			} else if (currentId != id) {
				if (currentRun > maxLength) {
					maxLength = currentRun;
					maxId = id;
				}
				currentId = id;
				currentRun = 1;
			} else {
				currentRun++;
			}
		}
		return maxId;
	}

	private int getInt(Matcher matcher, int groupIndex) {
		return Integer.parseInt(matcher.group(groupIndex));
	}

	@Data
	@AllArgsConstructor
	private static class Particle {
		private Coord3D position;
		private Coord3D velocity;
		private Coord3D acceleration;
	}

	@Data
	@AllArgsConstructor
	private static class Coord3D {
		private int x;
		private int y;
		private int z;

		public void add(Coord3D coord) {
			x += coord.getX();
			y += coord.getY();
			z += coord.getZ();
		}

		public int getMagnitude() {
			return Math.abs(x) + Math.abs(y) + Math.abs(z);
		}
	}
}