package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Directory root = new Directory(null);
		Directory cur = root;
		
		for (String line : lines) {
			if (line.startsWith("$ cd")) {
				String arg = line.replace("$ cd ", "");
				if (arg.equals("/")) {
					cur = root;
				} else if (arg.equals("..")) {
					cur = cur.getParent();
				} else {
					for (Item item : cur.getChildren()) {
						if (item.getName().equals(arg)) {
							cur = (Directory)item;
						}
					}
				}
			} else if (line.startsWith("$ ls")) {
				// ignore
			} else {
				// add item
				String[] parts = line.split(" ");
				if (parts[0].startsWith("dir")) {
					cur.addChild(new Directory(parts[1]));
				} else {
					cur.addChild(new File(parts[1], Integer.parseInt(parts[0])));
				}
			}
		}
		
		if (part == Part.ONE) {
			return getSum(root);
		} else {
			int size = Integer.MAX_VALUE;
			for (Directory dir : getDirectories(root, new ArrayList<>())) {
				if ((70000000 - root.getSize()) + dir.getSize() >= 30000000 && dir.getSize() < size) {
					size = dir.getSize();
				}
			}
			return size;
		}
	}
	
	private abstract class Item {
		private Directory parent;
		private String name;
		
		public Item(String name) {
			this.name = name;
		}
		
		public void setParent(Directory parent) {
			this.parent = parent;
		}
		
		public Directory getParent() {
			return parent;
		}
		
		public String getName() {
			return name;
		}
		
		public abstract int getSize();
	}
	
	private class Directory extends Item {
		private List<Item> children;
		
		public Directory(String name) {
			super(name);
			children = new ArrayList<>();
		}
		
		public void addChild(Item item) {
			item.setParent(this);
			children.add(item);
		}
		
		public List<Item> getChildren() {
			return children;
		}
		
		@Override
		public int getSize() {
			int size = 0;
			for (Item child : children) {
				size += child.getSize();
			}
			return size;
		}
	}
	
	private class File extends Item {
		private int size;
		
		public File(String name, int size) {
			super(name);
			this.size = size;
		}
		
		@Override
		public int getSize() {
			return size;
		}
	}
	
	private int getSum(Directory cur) {
		int sum = 0;
		if (cur.getSize() <= 100000) {
			sum += cur.getSize();
		}
		for (Item item : cur.getChildren()) {
			if (item instanceof Directory) {
				sum += getSum((Directory)item);
			}
		}
		return sum;
	}
	
	private List<Directory> getDirectories(Directory cur, List<Directory> dirs) {
		dirs.add(cur);
		for (Item item : cur.getChildren()) {
			if (item instanceof Directory) {
				getDirectories((Directory)item, dirs);
			}
		}
		return dirs;
	}
}