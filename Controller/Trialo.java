package Controller;

import java.util.*;
import java.util.Random;
import java.util.concurrent.Semaphore;
import Controla_ordenadores.Sorts;

public class Trialo extends Thread{
	private static Map<Integer, Integer> rankingMap;
	int c;
	Semaphore s;
	static int chegada = 0;
	static int[] ranking;
	static int pontuacao;
	Random r = new Random();
	public Trialo(int atleta, Semaphore sema) {
		c = atleta;
		s = sema;
	}
	
	public void run() {
		
		corrida();
		try {
			s.acquire();
			System.out.println("Atleta "+c+" iniciou a fase de tiro");

			tiro();
		} catch (InterruptedException e) {e.printStackTrace();}
		finally {
			System.out.println("Atleta "+c+" terminou a fase de tiro"); s.release();
		}
		
		bicicleta();
		classificacao();
	}

	private void corrida() {
		
		int percorrido = 0;
		
		while (percorrido <= 3000) {
			try {
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Atleta "+c+" correu "+percorrido+"m");
			percorrido+= r.nextInt(20, 26);
		}
		System.out.println("Atleta "+c+" terminou a primeira fase");
		
	}

	private void tiro() {
		
		for (int i = 0; i<3;i++) {
			
			try {
				sleep(r.nextInt(500, 3001));
			} catch (InterruptedException e) {e.printStackTrace();}
			
			int ptiro = r.nextInt(11);
			System.out.println("Atleta "+c+" marcou "+ptiro+" pontos com seu "+(i+1)+"° tiro");
			pontuacao += ptiro;		
		}
		
	}

	private void bicicleta() {
		
		int percorrido = 0;
		
		while (percorrido <= 5000) {
			try {
				sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Atleta "+c+" correu "+percorrido+"m");
			percorrido+= r.nextInt(30, 41);
		}
		chegada++;
		System.out.println("Atleta "+c+" terminou o circuito em "+chegada+"° lugar");
		
	}

	private void classificacao() {
	    
	    int pontosChegada = 250 - (chegada - 1) * 10;
	    int pontuacaoTotal = pontosChegada + pontuacao;
	    
	    if (rankingMap == null) {
	        rankingMap = new HashMap<Integer, Integer>();
	    }
	    rankingMap.put(c, pontuacaoTotal);
	    
	    if (chegada == 25) {
	        System.out.println("\n=== RANKING FINAL ===");
	        System.out.println("Posição | Atleta | Pontuação");
	        System.out.println("---------------------------");
	        
	        List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>(rankingMap.entrySet());
	        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
	        
	        int posicao = 1;
	        for (Map.Entry<Integer, Integer> entry : sortedEntries) {
	            System.out.printf("%5dº   | %6d | %5d pts%n", 
	                posicao++, entry.getKey(), entry.getValue());
	        }
	    }
	}

}
