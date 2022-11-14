package tp02;

import java.io.PrintStream;
import java.util.ArrayList;

public class Promotion {
	
	private ArrayList<Student> studentList ;

	public Promotion() {
		studentList = new ArrayList<Student>();
	}
	public int add(String firstName, String lastName) {
		studentList.add(new Student(studentList.size(),firstName,lastName));
		return studentList.size()-1;
	}
	public void printToConsole() {
		
		PrintStream out = System.out;
		for (int i = 0; i < studentList.size();i=i+1) {
			out.println(studentList.get(i));
		}
	}
	private void swap(int i, int j) {
		Student a = studentList.get(i);
		Student b = studentList.get(j);
		studentList.add(i,b);
		studentList.remove(i+1);
		studentList.add(j,a);
		studentList.remove(j+1);
	}
	private int min(int k) {
		int n = studentList.size();
		int m = k;
		for (int i = k;i<=n-1;i=i+1)
		{
			if (studentList.get(i).compareTo(studentList.get(m)) < 0)
				m = i;
		}
		return m;
	}
	public void selectionSort() {
		int n = studentList.size();
		for (int i=0;i<n-1;i=i+1) {
			int m = min(i);
			swap(i,m);
		}
	}
	
	
	
	
	private int partition(int g, int d) {
		Student key = studentList.get(g);
		int i = g+1;
		int j = d;
		while (i<=j) {
			while (i<=j && studentList.get(i).compareTo(key) <= 0) {
				i = i+1;
			}
			while (j>=i && studentList.get(j).compareTo(key) > 0) {
				j = j-1;
			}
			if (i<j) {
				swap(i,j);
				i = i+1;
				j = j-1;
			}	}
			swap(g,j);
			return j;
		}
	
	
	private void quickSort(int g, int d) {
		if (g<d) {
			int j = partition(g,d);
			quickSort(g,j-1);
			quickSort(j+1,d);
		}
	}
		
	public void quickSort() {
		int n = studentList.size();
		quickSort(0,n-1);
	}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

