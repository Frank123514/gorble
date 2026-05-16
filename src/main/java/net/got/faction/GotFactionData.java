package net.got.faction;

/**
 * Immutable data object describing a single playable faction (region).
 *
 * @param id           Unique string key, e.g. {@code "north"}.
 * @param continent    Parent continent key, e.g. {@code "westeros"}.
 * @param displayName  Human-readable region name shown on the tab button.
 * @param lordParamount The ruling Great House name, e.g. {@code "House Stark"}.
 * @param seat         Castle / city seat of power.
 * @param fealtyTo     Who the Lord Paramount swears fealty to.
 * @param lore         Short flavour description shown in the info panel.
 */
public record GotFactionData(
        String id,
        String continent,
        String displayName,
        String lordParamount,
        String seat,
        String fealtyTo,
        String lore
) {}