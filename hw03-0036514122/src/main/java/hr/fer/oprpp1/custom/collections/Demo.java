package hr.fer.oprpp1.custom.collections;

import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class Demo {

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(3);
		
		map.put("Ivana", 2);
		map.put("Ante", 2);
		map.put("Jasna", 2);
		map.put("Kristina", 5);
		map.put("Ivana", 5);
		map.remove("Ante");
		map.remove("Petar");
		map.get("Petar");
		map.remove("Jasna");
		
//		Integer kristinaGrade = map.get("Kristina");
//		System.out.println("Kristina's grade is: " + kristinaGrade);
//		System.out.println(map.size());
//		System.out.println(map.toString());
//		System.out.println(map.containsKey("Ivana"));
//		System.out.println(map.containsValue(4));

		
//		TableEntry<String, Integer>[] array = map.toArray();
//		System.out.println(array.length);
//		for(int i = 0; i < array.length; i++) {
//			System.out.println(array[i].getKey() + "=" + array[i].getValue());
//		}
		
		SimpleHashtable<String, Integer> map2 = new SimpleHashtable<String, Integer>(1);
		map2.put("Ivana", 2);
		//System.out.println(map2.getTable().length);
		map2.put("Ante", 2);
		//System.out.println(map2.getTable().length);
		map2.put("Jasna", 2);
		//System.out.println(map2.getTable().length);
		//map2.put("Kristina", 5);
		//System.out.println(map2.getTable().length);
		map2.put("Jozo", 5);
		//System.out.println(map2.getTable().length);
		
		//System.out.println(map2.toString());
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = map2.iterator();
//		while(iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.println(pair.getKey() + " " + pair.getValue());
//		}
//		while(true) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.println(pair.getKey() + " " + pair.getValue());
//		}
//		for(SimpleHashtable.TableEntry<String, Integer> pair1 : map2) {
//			for(SimpleHashtable.TableEntry<String, Integer> pair2 : map2) {
//				System.out.printf("(%s => %d) - (%s => %d)\n", 
//						pair1.getKey(), pair1.getValue(),
//						pair2.getKey(), pair2.getValue());
//			}
//		}
		
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.println(map2);
			iter.remove();
		}
		System.out.println(map2);
		System.out.println(map2.size());
		
		
		
		
		
	}
}
