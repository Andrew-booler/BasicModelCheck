package pl.sln;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.cnf.Literal;
import pl.core.KB;
import pl.core.Negation;
import pl.core.Sentence;
import pl.prover.Prover;
import pl.util.ArraySet;

public class PLProver implements Prover {

	@Override
	public boolean entails(KB kb, Sentence alpha) {
		// TODO Auto-generated method stub
		Set<Clause> clauses= new HashSet<Clause>();
		for (Sentence s : kb.sentences()) {
			Set<Clause> c = CNFConverter.convert(s);
			clauses.addAll(c);
		}
		
		clauses.addAll(CNFConverter.convert(new Negation(alpha)));
		
		Set<Clause> new_clauses = new HashSet<Clause>();
		
		while (true) {
			List<Clause> obj = new ArrayList<Clause>(clauses);
			for (int i = 0; i < obj.size(); i++) {
				for (int j = i; j < obj.size(); j++) {
					if (hasComplementary(obj.get(i), obj.get(j))) {
						ArraySet<Literal> resolvents = PLResolve(obj.get(i), obj.get(j));
						if (resolvents.size() == 0) {
							return true;
						}
						new_clauses.add((Clause)resolvents);
					}
				}
			}
			if (clauses.containsAll(new_clauses)) {
				return false;
			}
			clauses.addAll(new_clauses);
		}
	}
	
	protected boolean hasComplementary(Clause ci, Clause cj) {
		for (Iterator<Literal> ci_iter = ci.iterator(); ci_iter.hasNext(); ) {
			Literal li = ci_iter.next();
			for (Iterator<Literal> cj_iter = cj.iterator(); cj_iter.hasNext(); ) {
				Literal lj = cj_iter.next();
				if (li.getContent() == lj.getContent() && li.getPolarity() != lj.getPolarity()) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected ArraySet<Literal> PLResolve(Clause ci, Clause cj) {
		
		ArraySet<Literal> ci_copy = new ArraySet<Literal>(0);
		for (Iterator<Literal> ci_iter = ci_copy.iterator(); ci_iter.hasNext(); ) {
			ci_copy.add(ci_iter.next());
		}
		ArraySet<Literal> cj_copy = new ArraySet<Literal>(0);
		for (Iterator<Literal> cj_iter = cj_copy.iterator(); cj_iter.hasNext(); ) {
			cj_copy.add(cj_iter.next());
		}
		
		for (Iterator<Literal> ci_iter = ci_copy.iterator(); ci_iter.hasNext(); ) {
			Literal li = ci_iter.next();
			for (Iterator<Literal> cj_iter = cj_copy.iterator(); cj_iter.hasNext(); ) {
				Literal lj = cj_iter.next();
				if (li.getContent() == lj.getContent() && li.getPolarity() != lj.getPolarity()) {
					ci.remove(li);
					cj.remove(lj);
					ci_copy.addAll(cj_copy);
					
					return ci_copy;
				}
			}
		}
		
		return ci_copy;	// actually unreachable
	}

}
