package structures;

public class Heap {
		private final String[] heap;

		public Heap(String[] args) {
			heap = args.clone();
		}
		
		private void swap(int i, int j) {
			String s = heap[j];
			heap[j] = heap[i];
			heap[i] = s;
		}
		private void pullUp(int k) {
			int i = k-1;
			int p = (i-1)/2;
			while (i>=1 && heap[p].compareTo(heap[i])<0) {
				swap(p,i);
				i=p;
				p=(i-1)/2;
			}
		}
		public void buildHeap() {
			
		}
		
		
		
		
		
		
		
}
