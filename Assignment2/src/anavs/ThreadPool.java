package anavs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	MyThread[] pool;
	int anzahl;
	int A[][];
	int B[][];
	int C[][];
	ThreadPool(int anzahl, int A[][], int B[][], int C[][]){
		this.anzahl=anzahl;
		this.A=A;
		this.B=B;
		this.C=C;
		pool=new MyThread[anzahl];
	}
	int [][]  multiplizieren(){
		for(int i=0;i<anzahl;i++){
			pool[i]=new MyThread(i,anzahl,A,B,C);
			pool[i].start();	
		}
		for(int i=0;i<anzahl;i++){
			try {
				pool[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		
		return C;
      
    }

}
