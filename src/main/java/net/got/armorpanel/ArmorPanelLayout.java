package net.got.armorpanel;

import net.got.plate.PlatePiece;

/**
 * Where every custom armor slot lives. All slots sit in one panel attached to the LEFT side of the
 * inventory GUI (survival and the creative inventory tab). Coordinates are relative to the GUI's top-left
 * corner, so they are negative (outside the normal 176-wide inventory).
 *
 * Two columns:  "under" layers (coif, gambeson, hauberk, brigandine, cloak)  |  plate (gorget ... cuisses).
 */
public final class ArmorPanelLayout {

    // Panel rectangle (relative to the GUI's top-left).
    public static final int PANEL_LEFT = -54;
    public static final int PANEL_RIGHT = -4;
    public static final int PANEL_TOP = 6;
    public static final int PANEL_BOTTOM = 124;

    // Slot columns (x of the 16x16 item; the 18x18 frame starts 1px left of this).
    public static final int COL_UNDER = -47;
    public static final int COL_PLATE = -27;

    // Slot rows (y), 20px apart.
    public static final int ROW_0 = 21;
    public static final int ROW_1 = 41;
    public static final int ROW_2 = 61;
    public static final int ROW_3 = 81;
    public static final int ROW_4 = 101;

    // Under column, head to torso (inner layer to outer layer).
    public static final int COIF_X = COL_UNDER,       COIF_Y = ROW_0;
    public static final int GAMBESON_X = COL_UNDER,   GAMBESON_Y = ROW_1;
    public static final int HAUBERK_X = COL_UNDER,    HAUBERK_Y = ROW_2;
    public static final int BRIGANDINE_X = COL_UNDER, BRIGANDINE_Y = ROW_3;
    public static final int CLOAK_X = COL_UNDER,      CLOAK_Y = ROW_4;

    /**
     * Client-side flags set by the panel's render code. {@code hidden}: the panel is tucked away (the
     * recipe book is open), so its slots are inactive. {@code shown}: the panel is currently on screen.
     */
    public static volatile boolean hidden = false;
    public static volatile boolean shown = false;

    public static int plateX(PlatePiece piece) {
        return COL_PLATE;
    }

    public static int plateY(PlatePiece piece) {
        return switch (piece) {
            case GORGET -> ROW_0;
            case SPAULDERS -> ROW_1;
            case VAMBRACES -> ROW_2;
            case BREASTPLATE -> ROW_3;
            case CUISSES -> ROW_4;
        };
    }

    private ArmorPanelLayout() {}
}
