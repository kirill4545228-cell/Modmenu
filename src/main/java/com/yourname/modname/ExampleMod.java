package com.yourname.modname;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "utilitymenu";

    public static final Identifier WEATHER_PACKET_ID = new Identifier(MOD_ID, "set_weather");
    public static final Identifier TIME_PACKET_ID = new Identifier(MOD_ID, "set_time");
    public static final Identifier GIVE_PACKET_ID = new Identifier(MOD_ID, "give_item");

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(WEATHER_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String type = buf.readString();
            server.execute(() -> {
                if (player.hasPermissionLevel(2)) {
                    if (type.equals("clear")) player.getServerWorld().setWeather(0, 6000, false, false);
                    if (type.equals("rain")) player.getServerWorld().setWeather(6000, 0, true, false);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(TIME_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String timeType = buf.readString();
            server.execute(() -> {
                if (player.hasPermissionLevel(2)) {
                    if (timeType.equals("day")) player.getServerWorld().setTimeOfDay(1000);
                    if (timeType.equals("night")) player.getServerWorld().setTimeOfDay(13000);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(GIVE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Identifier itemId = buf.readIdentifier();
            server.execute(() -> {
                if (player.hasPermissionLevel(2)) {
                    Item item = Registries.ITEM.get(itemId);
                    player.giveItemStack(new ItemStack(item));
                }
            });
        });
    }
}
