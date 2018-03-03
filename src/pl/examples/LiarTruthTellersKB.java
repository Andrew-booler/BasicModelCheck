package pl.examples;

import pl.core.Biconditional;
import pl.core.Conjunction;
import pl.core.Disjunction;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class LiarTruthTellersKB extends KB {


	public LiarTruthTellersKB(int version) {
		super();
		Symbol a = intern("Amy is truthful");
		Symbol b = intern("Bob is truthful");
		Symbol c = intern("Cal is truthful");
		if (version<=1) 
		{
			add(new Biconditional( a, new Conjunction(a, c)));
			add(new Biconditional( b, new Negation(c)));
			add(new Biconditional( c, new Disjunction(b, new Negation(a))));
		}else {
			add(new Biconditional( a, new Negation(c)));
			add(new Biconditional( b, new Conjunction(a,c)));
			add(new Biconditional( c, b));
		}
		
	}

	public static void main(String[] args) {
		
		
		LiarTruthTellersKB lttkb = new LiarTruthTellersKB(1);
		lttkb.dump();
		Sentence q1 = lttkb.intern("Amy is truthful");
		Sentence q2 = lttkb.intern("Bob is truthful");
		Sentence q3 = lttkb.intern("Cal is truthful");
		
		TTEnum ttenum = new TTEnum();
		System.out.println(ttenum.TT_Entails(lttkb, q1));
		System.out.println(ttenum.TT_Entails(lttkb, q2));
		System.out.println(ttenum.TT_Entails(lttkb, q3));
		
		PLProver plprover = new PLProver();
		System.out.println(plprover.entails(lttkb, q1));
		System.out.println(plprover.entails(lttkb, q2));
		System.out.println(plprover.entails(lttkb, q3));
		
		lttkb = new LiarTruthTellersKB(2);
		lttkb.dump();
		System.out.println(ttenum.TT_Entails(lttkb, q1));
		System.out.println(ttenum.TT_Entails(lttkb, q2));
		System.out.println(ttenum.TT_Entails(lttkb, q3));
		

		System.out.println(plprover.entails(lttkb, q1));
		System.out.println(plprover.entails(lttkb, q2));
		System.out.println(plprover.entails(lttkb, q3));

	}

}
