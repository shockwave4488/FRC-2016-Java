package JavaRoboticsLib.FlowControl;

/**
 * can detect a change in data for any equatable type
 *
 */
public class ChangeTrigger {
	private Object m_state;
	
	public ChangeTrigger(){
		m_state = new Object();
	}
	
	public ChangeTrigger(Object initialState){
		m_state = initialState;
	}
	
	/**
	 * returns true if there is a change in value, does not update the internal state
	 * @param toCompare
	 * @return
	 */
	public boolean getChange(Object toCompare){
		return !m_state.equals(toCompare);
	}
	
	/**
	 * returns true if there is a change in value and updates the internal state
	 * @param toCompare
	 * @return
	 */
	public boolean getChangeUpdate(Object toCompare){
		boolean toReturn = !m_state.equals(toCompare);
		m_state = toCompare;
		return toReturn;
	}
	
	/**
	 * Manually updates the internal state
	 * @param toCompare
	 */
	public void update(Object toCompare){
		m_state = toCompare;
	}
}
