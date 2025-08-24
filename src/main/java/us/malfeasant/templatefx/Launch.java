package us.malfeasant.templatefx;

/**
 * This is a hack- rather than starting the JavaFX app directly,
 * we launch this class that then calls main on the App class.
 * Otherwise, JavaFX subsystem doesn't get invoked properly.
 * Only needed because we're packaging into a fat jar.
 */
public class Launch {
    public static void main(String[] args) {
        App.main(args);
    }
}
