package part1;

public class Main {
	public static void main(String[] args) {
		//@mail: benjamin.rouxel@inria.fr
		
		Bowling b = new Bowling();
		
		for(int i = 0 ; i < 4*8 ; i++) {
			
			Client c = new Client(b,"c"+i);
			Thread th = new Thread(c);

			th.start();
		}
		
		//TODO: afficher stats;
	}
}
