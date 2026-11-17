package net.got.client.armor;

import net.got.GotMod;
import net.got.brigandine.BrigandineSlotData;
import net.got.coif.CoifSlotData;
import net.got.gambeson.GambesonSlotData;
import net.got.hauberk.HauberkSlotData;
import java.util.ArrayList;
import java.util.List;
import net.got.network.CustomArmorSlotsPayload;
import net.got.network.PlateSlotsPayload;
import net.got.plate.PlatePiece;
import net.got.plate.PlateSlotData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

/**
 * The creative inventory screen is client-authoritative and can only report vanilla slots (1..45) to the server,
 * so anything put in the gambeson / brigandine / coif slot there never reached the server (and was wiped by the next
 * full inventory resync). While the creative screen is open, send those slots to the server when they change.
 */
@EventBusSubscriber(modid = GotMod.MODID, value = Dist.CLIENT)
public final class CreativeArmorSlotSync {

    private static CreativeModeInventoryScreen lastScreen;
    private static ItemStack lastGambeson = ItemStack.EMPTY;
    private static ItemStack lastBrigandine = ItemStack.EMPTY;
    private static ItemStack lastCoif = ItemStack.EMPTY;
    private static ItemStack lastHauberk = ItemStack.EMPTY;
    private static List<ItemStack> lastPlate = new ArrayList<>();

    private static List<ItemStack> readPlate(net.minecraft.world.entity.LivingEntity entity) {
        List<ItemStack> list = new ArrayList<>();
        for (PlatePiece piece : PlatePiece.values()) list.add(PlateSlotData.get(entity, piece).copy());
        return list;
    }

    private static boolean samePlate(List<ItemStack> a, List<ItemStack> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) if (!ItemStack.matches(a.get(i), b.get(i))) return false;
        return true;
    }

    @SubscribeEvent
    public static void onRender(ScreenEvent.Render.Background event) {
        if (!(event.getScreen() instanceof CreativeModeInventoryScreen screen)) {
            lastScreen = null;
            return;
        }
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack gambeson = GambesonSlotData.get(player);
        ItemStack brigandine = BrigandineSlotData.get(player);
        ItemStack coif = CoifSlotData.get(player);
        ItemStack hauberk = HauberkSlotData.get(player);
        List<ItemStack> plate = readPlate(player);

        if (screen != lastScreen) {            // screen just opened: remember what the server already has
            lastScreen = screen;
            lastGambeson = gambeson.copy();
            lastBrigandine = brigandine.copy();
            lastCoif = coif.copy();
            lastHauberk = hauberk.copy();
            lastPlate = plate;
            return;
        }

        if (!samePlate(plate, lastPlate)) {
            lastPlate = plate;
            ClientPacketDistributor.sendToServer(new PlateSlotsPayload(readPlate(player)));
        }

        if (!ItemStack.matches(gambeson, lastGambeson) || !ItemStack.matches(brigandine, lastBrigandine)
                || !ItemStack.matches(coif, lastCoif) || !ItemStack.matches(hauberk, lastHauberk)) {
            lastGambeson = gambeson.copy();
            lastBrigandine = brigandine.copy();
            lastCoif = coif.copy();
            lastHauberk = hauberk.copy();
            ClientPacketDistributor.sendToServer(new CustomArmorSlotsPayload(gambeson.copy(), brigandine.copy(), coif.copy(), hauberk.copy()));
        }
    }

    private CreativeArmorSlotSync() {}
}
