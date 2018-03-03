package pl.examples;

import pl.core.*;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class ModusPonensKB extends KB {
	
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		Symbol q = intern("Q");
		add(q);
		add(new Implication(p, q));
	}
	
	public static void main(String[] argv) {
		new ModusPonensKB().dump();
		
		ModusPonensKB mpkb = new ModusPonensKB();
		Sentence alpha = new Symbol("P");
		
		TTEnum ttenum = new TTEnum();
		System.out.println(ttenum.TT_Entails(mpkb, alpha));
		
		PLProver plprover = new PLProver();
		System.out.println(plprover.entails(mpkb, alpha));
	}

}
