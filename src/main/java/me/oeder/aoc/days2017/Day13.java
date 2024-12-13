package me.oeder.aoc.days2017;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day13 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			return getCaughtLayers(lines, 0, part).stream().map(layer -> layer.getIndex() * layer.getSize()).reduce(0, (a, b) -> a + b);
		}

		int delay = 1;
		while (true) {
			if (getCaughtLayers(lines, delay, part).isEmpty()) {
				return delay;
			}
			delay++;
		}
	}

	private int prevDelay = 0;
	private List<Layer> prevLayers = new ArrayList<>();

	private List<Layer> getCaughtLayers(List<String> lines, int delay, Part part) {
		List<Layer> layers = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(": ");
			layers.add(new Layer(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), 0, true));
		}

		if (part == Part.TWO) {
			if (prevDelay > 0) {
				layers = getDeepCopyOfLayers(prevLayers);
			}
			moveLayers(layers);

			prevDelay = delay;
			prevLayers = getDeepCopyOfLayers(layers);
		}

		List<Layer> caughtLayers = new ArrayList<>();

		int curPos = 0;
		int maxPos = layers.get(layers.size() - 1).getIndex();

		while (curPos <= maxPos) {
			final int targetPos = curPos;

			Optional<Layer> layerOpt = layers.stream().filter(layer -> layer.getIndex() == targetPos).findFirst();
			if (layerOpt.isPresent() && layerOpt.get().getPos() == 0) {
				caughtLayers.add(layerOpt.get());
				if (part == Part.TWO) {
					return caughtLayers;
				}
			}

			moveLayers(layers);
			curPos++;
		}

		return caughtLayers;
	}

	private List<Layer> getDeepCopyOfLayers(List<Layer> layers) {
		List<Layer> deepCopyOfLayers = new ArrayList<>();
		for (Layer layer : layers) {
			deepCopyOfLayers.add(new Layer(layer.getIndex(), layer.getSize(), layer.getPos(), layer.isForward()));
		}
		return deepCopyOfLayers;
	}

	private void moveLayers(List<Layer> layers) {
		for (Layer layer : layers) {
			layer.setPos(layer.getPos() + (layer.isForward() ? 1 : -1));
			if (layer.getPos() == layer.getSize() - 1) {
				layer.setForward(false);
			}
			if (layer.getPos() == 0) {
				layer.setForward(true);
			}
		}
	}

	@Data
	@AllArgsConstructor
	private class Layer {
		private int index;
		private int size;
		private int pos;
		private boolean forward;
	}
}