package uncc2014watsonsim.research;

import uncc2014watsonsim.Answer;
import uncc2014watsonsim.Passage;
import uncc2014watsonsim.Question;
import uncc2014watsonsim.Score;

/** Scorers apply scores in parallel to:
 *  - Answers
 *  - Passages
 *  By default, a score is NaN.
 *  Scorers are expected to run in parallel. Try to avoid side effects.
 *  Otherwise use "synchronized".
 */
public abstract class Scorer { 
	// This is a constructor-less hack to give Researchers a convenient name
	// It is used for assigning scores.
	String name;
	{
		name = this.getClass().getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
		Score.register(name);
	}

	/** Default implementation of research for a question.
	 * Simply calls research_answer for every Answer
	 * Override this if you need more power.
	 * @param question
	 * @throws Exception 
	 */
	public void scoreQuestion(Question q) {
		for (Answer a : q)
			for (Passage p: a.passages)
				p.score(name, scorePassage(q, a, p));		
	}
	
	/** Default implementation for researching a passage.
	 * Does nothing by default. You don't need to override this if you don't
	 * use it.
	 * 
	 * @param answer
	 */
	public double scorePassage(Question q, Answer a, Passage p) {
		return Double.NaN;
	}
	
	/** Default implementation for ending question research.
	 * This might trigger some database inserts or like writing, for example.
	 */
	public void complete() {};
}