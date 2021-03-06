package asim.agents;

import asim.Framework;
import asim.Log;
import asim.ScoringAgent;
import asim.StateNode;
import asim.Util;

/** farther back we have to go the lower the score */

public class SameLocationThisTimeOfDayWeekAgo extends ScoringAgent
{
	private static final String TAG = SameLocationThisTimeOfDayWeekAgo.class.getSimpleName();
	
	private static final double K = 2.0;
	
	public SameLocationThisTimeOfDayWeekAgo(Framework f)
	{
		super(f);
		Log.d(TAG, getID(true));
	}

	@Override
	public double score(StateNode node)
	{
		StateNode n = node;
		while (true)
		{
			n = n.nextThisTimeOfDay(true, 7);
			
			if (n == null)
				return 0.5;	// no more history, 
			
			if (Util.metersBetween(node.getLocation(), n.getLocation()) < Util.SameLocationThreshold)
			{
				// preserve direction of time
				double howFar = n.getTime() - (node.getTime() - Util.OneWeek);
				return Math.exp(howFar + K);
			}
		}
	}

}
