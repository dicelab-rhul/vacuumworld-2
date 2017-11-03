package uk.ac.rhul.cs.dice.vacuumworld.actions;

public enum ActionEnum {
    SENSING_ACTION(0), MOVE_ACTION(1), TURN_LEFT_ACTION(2), TURN_RIGHT_ACTION(3), CLEAN_ACTION(4), SPEECH_ACTION(5), DROP_DIRT_ACTION(6);
    
    private Integer value;
    
    private ActionEnum(Integer value) {
	this.value = value;
    }
    
    public Integer getValue() {
	return this.value;
    }
    
    public static ActionEnum fromCode(int code) {
	return ActionEnum.values()[code];
    }
}