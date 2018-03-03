package pl.examples;

import java.util.ArrayList;

import pl.core.Biconditional;
import pl.core.Conjunction;
import pl.core.Disjunction;
import pl.core.Implication;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.core.Symbol;
import pl.sln.PLProver;
import pl.sln.TTEnum;

public class DoorEnlightKB extends KB {
	
	public DoorEnlightKB () {
		Symbol x = intern("X is a good door");
		Symbol y = intern("Y is a good door");
		Symbol z = intern("Z is a good door");
		Symbol w = intern("W is a good door");
		
		Symbol a = intern("A is a knight");
		Symbol b = intern("B is a knight");
		Symbol c = intern("C is a knight");
		Symbol d = intern("D is a knight");
		Symbol e = intern("E is a knight");
		Symbol f = intern("F is a knight");
		Symbol g = intern("G is a knight");
		Symbol h = intern("H is a knight");
		
		add(new Biconditional(a, x));
		add(new Biconditional(b, new Disjunction(y, z)));
		add(new Biconditional(c, new Conjunction(a, b)));
		add(new Biconditional(d, new Conjunction(x, y)));
		add(new Biconditional(e, new Conjunction(x, z)));
		add(new Biconditional(f, new Disjunction(new Conjunction(d, new Negation(e)), new Conjunction(e, new Negation(d)))));
		add(new Biconditional(g, new Implication(c, f)));
		add(new Biconditional(h, new Implication(new Conjunction(g, h), a)));
	}

	public static void main(String[] args) {
		new DoorEnlightKB().dump();
		
		DoorEnlightKB dfkb = new DoorEnlightKB();
		ArrayList<Sentence> queries = new ArrayList<Sentence>();
		queries.add(dfkb.intern("X is a good door."));
		queries.add(dfkb.intern("Y is a good door."));
		queries.add(dfkb.intern("Z is a good door."));
		queries.add(dfkb.intern("W is a good door."));
		
		for (Sentence s : queries) {
			TTEnum ttenum = new TTEnum();
			System.out.println(ttenum.TT_Entails(dfkb, s));
		}
		
		System.out.println("\n");
		
		for (Sentence s : queries) {
			PLProver plprover = new PLProver();
			System.out.println(plprover.entails(dfkb, s));
		}

	}

}