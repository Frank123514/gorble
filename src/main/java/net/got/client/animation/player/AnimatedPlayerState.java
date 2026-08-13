package net.got.client.animation.player;

public interface AnimatedPlayerState {

    float got$getClimbProgress();

    void got$setClimbProgress(float value);

    float got$getAirborneProgress();

    void got$setAirborneProgress(float value);

    float got$getSprintProgress();

    void got$setSprintProgress(float value);

    SwingStyle got$getSwingStyle();

    void got$setSwingStyle(SwingStyle style);

    int got$getComboIndex();

    void got$setComboIndex(int value);

    float got$getPrevSwing();

    void got$setPrevSwing(float value);

    float got$getSwingStartAge();

    void got$setSwingStartAge(float value);

    boolean got$isMiningWithAxe();

    void got$setMiningWithAxe(boolean value);

    boolean got$isRidingHorse();

    void got$setRidingHorse(boolean value);

    float got$getHorseRunBlend();

    void got$setHorseRunBlend(float value);

    boolean got$isLocalFirstPerson();

    void got$setLocalFirstPerson(boolean value);
}