package com.yourname.modname.UI;

import com.yourname.modname.ExampleMod;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomMenuScreen extends Screen {
    private static final int BG_WIDTH = 280;
    private static final int BG_HEIGHT = 220;

    public CustomMenuScreen() {
        super(Text.literal("Мой мод"));
    }

    @Override
    protected void init() {
        super.init();
        int centerX = (this.width - BG_WIDTH) / 2;
        int centerY = (this.height - BG_HEIGHT) / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("☀️ Включить солнце"), button -> {
            PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
            packet.writeString("clear");
            ClientPlayNetworking.send(ExampleMod.WEATHER_PACKET_ID, packet);
        }).dimensions(centerX + 20, centerY + 50, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("⏰ Утро / День"), button -> {
            PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
            packet.writeString("day");
            ClientPlayNetworking.send(ExampleMod.TIME_PACKET_ID, packet);
        }).dimensions(centerX + 20, centerY + 80, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("💎 Выдать алмаз"), button -> {
            PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
            packet.writeIdentifier(new Identifier("minecraft", "diamond"));
            ClientPlayNetworking.send(ExampleMod.GIVE_PACKET_ID, packet);
        }).dimensions(centerX + 20, centerY + 110, 240, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = (this.width - BG_WIDTH) / 2;
        int centerY = (this.height - BG_HEIGHT) / 2;

        context.fill(centerX, centerY, centerX + BG_WIDTH, centerY + BG_HEIGHT, 0xFF1E1B29);
        context.fillBorder(centerX, centerY, centerX + BG_WIDTH, centerY + BG_HEIGHT, 0xFF4A3B69);

        context.drawText(this.textRenderer, Text.literal("✨ Мой мод"), centerX + 20, centerY + 15, 0xD4B8FF, true);
        context.drawText(this.textRenderer, Text.literal("Новые возможности в вашем мире"), centerX + 20, centerY + 28, 0x8A79A6, false);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
