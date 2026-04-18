package net.got.entity.client.npc.smallfolk;

import net.minecraft.resources.ResourceLocation;
import net.got.entity.npc.NpcGender;

/**
 * Provides randomized NPC skin textures based on culture, gender, and entity ID.
 *
 * Textures are pre-generated variants (12 per culture/gender combo).
 * Each NPC gets a stable skin for its lifetime via seeded selection using entity ID.
 *
 * Usage in your renderer:
 *   ResourceLocation tex = SmallfolkSkinProvider.getSkin(culture, gender, entityId);
 */
public class SmallfolkSkinProvider {

    private static final int VARIANTS_PER_GENDER = 16;

    /**
     * Returns a deterministic but varied skin texture for an NPC.
     *
     * @param culture    e.g. "northman", "dornishman"
     * @param gender     NpcGender.MALE or NpcGender.FEMALE
     * @param entityId   The entity's unique numeric ID (used as seed)
     * @return ResourceLocation pointing to the correct generated texture
     */
    public static ResourceLocation getSkin(String culture, NpcGender gender, long entityId) {
        return buildLocation(culture, gender, entityId, VARIANTS_PER_GENDER, 1);
    }

    /**
     * Age-adjusted variant — elders use slots 09-12 (grey-hair variants),
     * young use slots 01-04, adults use the full range.
     *
     * @param ageCategory "young" | "adult" | "elder"
     */
    public static ResourceLocation getSkinByAge(String culture, NpcGender gender, long entityId, String ageCategory) {
        return switch (ageCategory) {
            case "elder" -> buildLocation(culture, gender, entityId, 4, 9);   // slots 09-12
            case "young" -> buildLocation(culture, gender, entityId, 4, 1);   // slots 01-04
            default      -> buildLocation(culture, gender, entityId, VARIANTS_PER_GENDER, 1);
        };
    }

    // ── Internal ─────────────────────────────────────────────────────────────

    private static ResourceLocation buildLocation(String culture, NpcGender gender,
                                                   long entityId, int poolSize, int offset) {
        String genderStr = (gender == NpcGender.FEMALE) ? "female" : "male";
        long   seed      = entityId ^ (long) culture.hashCode();
        int    variant   = (int)(Math.abs(seed) % poolSize) + offset;
        String path      = "textures/entity/npc/smallfolk/" + culture
                         + "/generated/" + genderStr + "_" + String.format("%02d", variant);
        return ResourceLocation.fromNamespaceAndPath("got", path);
    }
}
