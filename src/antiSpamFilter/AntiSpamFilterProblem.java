package antiSpamFilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	  public AntiSpamFilterProblem() {
	    // 10 variables (anti-spam filter rules) by default 
	    this(335);
	  }

	  public AntiSpamFilterProblem(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  public void evaluate(DoubleSolution solution){
	    double aux, xi, xj;
	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
	    
        ArrayList<Rule> rules = AntiSpamFilterManager.getInstance().getRulesList();
        for(int i = 0; i < x.length; i++) {
        	rules.get(i).setWeight(x[i]);
        }
        
        Results r = AntiSpamFilterManager.getInstance().evaluate(rules);
        
        fx[0] = r.getFp();
        fx[1] = r.getFn();
	    
	    solution.setObjective(0, fx[0]);
	    solution.setObjective(1, fx[1]);
	  }
	}
