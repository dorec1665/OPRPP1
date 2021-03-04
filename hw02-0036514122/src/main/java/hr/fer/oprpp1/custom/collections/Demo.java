package hr.fer.oprpp1.custom.collections;

public class Demo {

	public static void main(String[] args) {
		
//		Collection col1 = new ArrayIndexedCollection();
//		Collection col2 = new LinkedListIndexedCollection();
//		col1.add("Ivo");
//		col1.add("Ana");
//		col1.add("Jasna");
//		col2.add("Jasmina");
//		col2.add("Štefanija");
//		col2.add("Karmela");
//		
//		IElementsGetter getter1 = col1.createElementsGetter();
//		IElementsGetter getter2 = col1.createElementsGetter();
//		IElementsGetter getter3 = col2.createElementsGetter();
//		
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
		
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		IElementsGetter getter = col.createElementsGetter();
		
//		System.out.println("Jedan element: " + getter.getNextElement());
//		System.out.println("Jedan element: " + getter.getNextElement());
//		
//		col.add("Pero");
//		
//		System.out.println("Jedan element: " + getter.getNextElement());
		
		getter.getNextElement();
		getter.processRemaining(System.out::println);
		
		class EvenIntegerTester implements Tester {

			@Override
			public boolean test(Object obj) {
				if(!(obj instanceof Integer)) {
					return false;
				}
				Integer i = (Integer) obj;
				return i % 2 == 0;
			}
			
		}
		
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
		
//		Collection col3 = new LinkedListIndexedCollection();
//		Collection col4 = new ArrayIndexedCollection();
//		
//		col3.add(2);
//		col3.add(3);
//		col3.add(4);
//		col3.add(5);
//		col3.add(6);
//		
//		col4.add(12);
//		col4.addAllSatisfying(col3, new EvenIntegerTester());
//		
//		col4.forEach(System.out::println);
		
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		
		Collection col3 = col1;
		Collection col4 = col2;
		
		col1.get(0);
		col2.get(0);
//		col3.get(0);
//		col4.get(0);
		
		col1.forEach(System.out::println);
		col2.forEach(System.out::println);
		col3.forEach(System.out::println);
		col4.forEach(System.out::println);
	}
}
