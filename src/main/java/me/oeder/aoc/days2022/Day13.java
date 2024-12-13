package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			String left = lines.get(i);
			String right = lines.get(i + 1);
			List<Object> leftList = buildList(left);
			List<Object> rightList = buildList(right);
			Order order = isInOrder(leftList, rightList);
			if (order == Order.IN || order == Order.SUPERIN) {
				sum += (i/3 + 1);
			}
		}
		return sum;
	}
	
	private List<Object> buildList(String line) {
		List<Object> list = new ArrayList<>();
		
		String trimmedLine = line.substring(1, line.length() - 1);
		
		int recurseBracketCount = 0;
		String recursePart = "";
		String digitPart = "";
		for (int i = 0; i < trimmedLine.length(); i++) {
			String s = trimmedLine.substring(i, i + 1);
			if (s.equals("[")) {
				recurseBracketCount++;
				recursePart += s;
			} else if (s.equals("]")) { 
				recurseBracketCount--;
				recursePart += s;
			} else if (recurseBracketCount > 0) {
				recursePart += s;
			}
			
			if (recurseBracketCount == 0) {
				if (!recursePart.equals("")) {
					list.add(buildList(recursePart));
					recursePart = "";
				} else {
					if (s.equals(",")) {
						if (!digitPart.equals("")) {
							list.add(Integer.parseInt(digitPart));
							digitPart = "";
						}
					} else {
						digitPart += s;
					}
				}
			}
		}
		if (!digitPart.equals("")) {
			list.add(Integer.parseInt(digitPart));
		}
		return list;
	}
	
	public enum Order {
		SUPERIN,
		IN,
		OUT,
		SUPEROUT,
		EQUAL;
	}
	
	private Order isInOrder(List<Object> leftList, List<Object> rightList) {
		if (leftList.size() == 0 && rightList.size() > 0) {
			return Order.SUPERIN;
		}
		if (leftList.size() > 0 && rightList.size() == 0) {
			return Order.SUPEROUT;
		}
		Order order = Order.IN;
		for (int i = 0; i < leftList.size(); i++) {
			if (i >= rightList.size()) {
				return Order.SUPEROUT; // Right side ran out of items
			}
			Object leftItem = leftList.get(i);
			Object rightItem = rightList.get(i);
			
			if (leftItem instanceof Integer && rightItem instanceof Integer) {
				if ((Integer)leftItem < (Integer)rightItem) {
					return Order.IN;
				} else if ((Integer)leftItem > (Integer)rightItem) {
					return Order.OUT;
				}
			} else if (leftItem instanceof List && rightItem instanceof List) {
				order = isInOrder((List)leftItem, (List)rightItem);
			} else if (leftItem instanceof List && rightItem instanceof Integer) {
				order = isInOrder((List)leftItem, Arrays.asList((Integer)rightItem));
			} else if (leftItem instanceof Integer && rightItem instanceof List) {
				order = isInOrder(Arrays.asList((Integer)leftItem), (List)rightItem);
			}
			
			if (order == Order.SUPERIN || order == Order.SUPEROUT) {
				return order;
			}
		}
		return order;
	}
}