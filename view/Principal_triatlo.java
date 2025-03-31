package view;

import java.util.concurrent.Semaphore;

import Controller.Trialo;

public class Principal_triatlo {

	public static void main(String[] args) {
		Semaphore se = new Semaphore(5);
		
		for (int i = 0; i<25;i++) {
			
			Trialo t = new Trialo(i, se);
			t.start();
			
		}

	}

}
