package frc.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Shifter
 */
public enum Shifter {
    LOW(true), HIGH(false);
    
    private final boolean value;
    private static Map<Object, Object> map = new HashMap<>();
    private Shifter(boolean value) {
        this.value = value;
    }
    /**
     * Get the numerical value of Log.
     * @return
     */
    public boolean getValue() {
        return value;
    }

    static
	{
		for(Shifter shifter : Shifter.values())
		{
			map.put(shifter.value, shifter);
		}
	}
	
	public static Shifter valueOf(boolean value)
	{
		return (Shifter) map.get(value);
}
}