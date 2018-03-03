package pl.examples;
import pl.core.*;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class HornClauseKB extends KB {
			
		public HornClauseKB() {
			super();
			Symbol myth = intern("mythical");
			Symbol mor = intern("mortal");
			Symbol mam = intern("mammal");
			Symbol horn = intern("horned");
			Symbol magic = intern("magical");
			add(new Implication(myth, new Negation(mor)));
			add(new Implication(new Negation(myth), new Conjunction(mor, mam)));
			add(new Implication(new Disjunction(new Negation(mor), mam), horn));
			add(new Implication(horn,magic));
		}
		
		public static void main(String[] argv) {
			new HornClauseKB().dump();
			
			HornClauseKB hckb = new HornClauseKB();
			Sentence q1 = new Symbol("mythical");
			Sentence q2 = new Symbol("magical");
			Sentence q3 = new Symbol("horned");
			
			TTEnum ttenum = new TTEnum();
			System.out.println(ttenum.TT_Entails(hckb, q1));
			System.out.println(ttenum.TT_Entails(hckb, q2));
			System.out.println(ttenum.TT_Entails(hckb, q3));
			
			PLProver plprover = new PLProver();
			System.out.println(plprover.entails(hckb, q1));
			System.out.println(plprover.entails(hckb, q2));
			System.out.println(plprover.entails(hckb, q3));
		}

	}

