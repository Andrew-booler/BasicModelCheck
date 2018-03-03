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

public class PLProver implements Prover {

	@Override
	public boolean entails(KB kb, Sentence alpha) {
		// store all clauses from KB
		Set<Clause> clauses = new HashSet<Clause>();
		for (Sentence s : kb.sentences()) {
			Set<Clause> c = CNFConverter.convert(s);
			clauses.addAll(c);
		}
		// add clauses from alpha
		clauses.addAll(CNFConverter.convert(new Negation(alpha)));
		
		// an empty set
		Set<Clause> new_clauses = new HashSet<Clause>();
		
		while (true) {
			List<Clause> clauses_list = new ArrayList<Clause>(clauses);
			for (int i = 0; i < clauses_list.size(); i++) {
				for (int j = i + 1; j < clauses_list.size(); j++) {
					Clause ci = clauses_list.get(i);
					Clause cj = clauses_list.get(j);
					Set<Clause> resolvents = PLResolve(ci, cj);
					// check whether the resolvents contains empty clause
					for (Clause c : resolvents) {	
						if (c.size() == 0) {
							return true;
						}
					}
					new_clauses.addAll(resolvents);
					}
				}
			if (clauses.containsAll(new_clauses)) {
				return false;
			}
			clauses.addAll(new_clauses);
		}
	}
	
	protected Set<Clause> PLResolve(Clause ci, Clause cj) {
		// an empty set
		Set<Clause> resolvents = new HashSet<Clause>();
		
		for (Iterator<Literal> ci_iter = ci.iterator(); ci_iter.hasNext(); ) {
			Literal li = ci_iter.next();
			for (Iterator<Literal> cj_iter = cj.iterator(); cj_iter.hasNext(); ) {
				Literal lj = cj_iter.next();
				if (li.getContent().equals(lj.getContent()) && li.getPolarity() != lj.getPolarity()) {
					// copy a clause
					Clause ci_copy = new Clause(ci);
					// remove complementary
					ci_copy.remove(li);
					// copy a clause
					Clause cj_copy = new Clause(cj);
					// remove complementary
					cj_copy.remove(lj);
					// combine two clauses
					ci_copy.addAll(cj_copy);
					// add to resolvents
					resolvents.add(ci_copy);
				}
			}
		}
		
		return resolvents;
	}

}
