package tp02;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Promotion p = new Promotion();
		p.add("f", "c");
		p.add("q","o");
		p.add("d","h");
		p.add("g", "h");
		p.printToConsole();
		
		//System.out.println((new Student(6,"f","c")).compareTo(new Student(5,"d","h")));
	
		System.out.println("-------");
		p.quickSort();
		p.printToConsole();
	
	
	
	}

}
