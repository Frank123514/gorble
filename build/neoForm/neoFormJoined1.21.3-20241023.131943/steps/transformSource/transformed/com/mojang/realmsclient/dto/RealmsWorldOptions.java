package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsWorldOptions extends ValueObject {
    public final boolean pvp;
    public final boolean spawnMonsters;
    public final int spawnProtection;
    public final boolean commandBlocks;
    public final boolean forceGameMode;
    public final int difficulty;
    public final int gameMode;
    public final boolean hardcore;
    private final String slotName;
    public final String version;
    public final RealmsServer.Compatibility compatibility;
    public long templateId;
    @Nullable
    public String templateImage;
    public boolean empty;
    private static final boolean DEFAULT_FORCE_GAME_MODE = false;
    private static final boolean DEFAULT_PVP = true;
    private static final boolean DEFAULT_SPAWN_MONSTERS = true;
    private static final int DEFAULT_SPAWN_PROTECTION = 0;
    private static final boolean DEFAULT_COMMAND_BLOCKS = false;
    private static final int DEFAULT_DIFFICULTY = 2;
    private static final int DEFAULT_GAME_MODE = 0;
    private static final boolean DEFAULT_HARDCORE_MODE = false;
    private static final String DEFAULT_SLOT_NAME = "";
    private static final String DEFAULT_VERSION = "";
    private static final RealmsServer.Compatibility DEFAULT_COMPATIBILITY = RealmsServer.Compatibility.UNVERIFIABLE;
    private static final long DEFAULT_TEMPLATE_ID = -1L;
    private static final String DEFAULT_TEMPLATE_IMAGE = null;

    public RealmsWorldOptions(
        boolean pvp,
        boolean spawnMonsters,
        int spawnProtection,
        boolean commandBlocks,
        int difficulty,
        int gameMode,
        boolean hardcore,
        boolean forceGameMode,
        String slotName,
        String version,
        RealmsServer.Compatibility compatibility
    ) {
        this.pvp = pvp;
        this.spawnMonsters = spawnMonsters;
        this.spawnProtection = spawnProtection;
        this.commandBlocks = commandBlocks;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.hardcore = hardcore;
        this.forceGameMode = forceGameMode;
        this.slotName = slotName;
        this.version = version;
        this.compatibility = compatibility;
    }

    public static RealmsWorldOptions createDefaults() {
        return new RealmsWorldOptions(true, true, 0, false, 2, 0, false, false, "", "", DEFAULT_COMPATIBILITY);
    }

    public static RealmsWorldOptions createDefaultsWith(GameType gameMode, Difficulty difficulty, boolean hardcore, String version, String slotName) {
        return new RealmsWorldOptions(true, true, 0, false, difficulty.getId(), gameMode.getId(), hardcore, false, slotName, version, DEFAULT_COMPATIBILITY);
    }

    public static RealmsWorldOptions createFromSettings(LevelSettings settings, String version) {
        return createDefaultsWith(settings.gameType(), settings.difficulty(), settings.hardcore(), version, settings.levelName());
    }

    public static RealmsWorldOptions createEmptyDefaults() {
        RealmsWorldOptions realmsworldoptions = createDefaults();
        realmsworldoptions.setEmpty(true);
        return realmsworldoptions;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public static RealmsWorldOptions parse(JsonObject json, RealmsSettings realmsSettings) {
        RealmsWorldOptions realmsworldoptions = new RealmsWorldOptions(
            JsonUtils.getBooleanOr("pvp", json, true),
            JsonUtils.getBooleanOr("spawnMonsters", json, true),
            JsonUtils.getIntOr("spawnProtection", json, 0),
            JsonUtils.getBooleanOr("commandBlocks", json, false),
            JsonUtils.getIntOr("difficulty", json, 2),
            JsonUtils.getIntOr("gameMode", json, 0),
            realmsSettings.hardcore(),
            JsonUtils.getBooleanOr("forceGameMode", json, false),
            JsonUtils.getRequiredStringOr("slotName", json, ""),
            JsonUtils.getRequiredStringOr("version", json, ""),
            RealmsServer.getCompatibility(JsonUtils.getRequiredStringOr("compatibility", json, RealmsServer.Compatibility.UNVERIFIABLE.name()))
        );
        realmsworldoptions.templateId = JsonUtils.getLongOr("worldTemplateId", json, -1L);
        realmsworldoptions.templateImage = JsonUtils.getStringOr("worldTemplateImage", json, DEFAULT_TEMPLATE_IMAGE);
        return realmsworldoptions;
    }

    public String getSlotName(int slotIndex) {
        if (StringUtil.isBlank(this.slotName)) {
            return this.empty ? I18n.get("mco.configure.world.slot.empty") : this.getDefaultSlotName(slotIndex);
        } else {
            return this.slotName;
        }
    }

    public String getDefaultSlotName(int slotIndex) {
        return I18n.get("mco.configure.world.slot", slotIndex);
    }

    public String toJson() {
        JsonObject jsonobject = new JsonObject();
        if (!this.pvp) {
            jsonobject.addProperty("pvp", this.pvp);
        }

        if (!this.spawnMonsters) {
            jsonobject.addProperty("spawnMonsters", this.spawnMonsters);
        }

        if (this.spawnProtection != 0) {
            jsonobject.addProperty("spawnProtection", this.spawnProtection);
        }

        if (this.commandBlocks) {
            jsonobject.addProperty("commandBlocks", this.commandBlocks);
        }

        if (this.difficulty != 2) {
            jsonobject.addProperty("difficulty", this.difficulty);
        }

        if (this.gameMode != 0) {
            jsonobject.addProperty("gameMode", this.gameMode);
        }

        if (this.hardcore) {
            jsonobject.addProperty("hardcore", this.hardcore);
        }

        if (this.forceGameMode) {
            jsonobject.addProperty("forceGameMode", this.forceGameMode);
        }

        if (!Objects.equals(this.slotName, "")) {
            jsonobject.addProperty("slotName", this.slotName);
        }

        if (!Objects.equals(this.version, "")) {
            jsonobject.addProperty("version", this.version);
        }

        if (this.compatibility != DEFAULT_COMPATIBILITY) {
            jsonobject.addProperty("compatibility", this.compatibility.name());
        }

        return jsonobject.toString();
    }

    public RealmsWorldOptions clone() {
        return new RealmsWorldOptions(
            this.pvp,
            this.spawnMonsters,
            this.spawnProtection,
            this.commandBlocks,
            this.difficulty,
            this.gameMode,
            this.hardcore,
            this.forceGameMode,
            this.slotName,
            this.version,
            this.compatibility
        );
    }
}
