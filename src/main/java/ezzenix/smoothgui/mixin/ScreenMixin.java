package ezzenix.smoothgui.mixin;

import ezzenix.smoothgui.SmoothGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(Screen.class)
public class ScreenMixin {

    // 被排除平滑效果的屏幕
    private boolean shouldExcludeScreen() {
        Object self = (Object) this;
        return self instanceof LevelLoadingScreen
                || self instanceof ReceivingLevelScreen
                || self instanceof GenericMessageScreen;
    }

    // 通用屏幕平滑效果
    // Offset screen rendering
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu() || shouldExcludeScreen())
            return;
        gh.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu() || shouldExcludeScreen())
            return;
        gh.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }

    // Make the menu background not affected
    @Inject(method = "renderBackground", at = @At("HEAD"))
    private void onRenderBackground(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu() || shouldExcludeScreen())
            return;
        gh.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }

    @Inject(method = "renderBackground", at = @At("TAIL"))
    private void onRenderBackgroundEnd(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu() || shouldExcludeScreen())
            return;
        gh.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    // Track when new screens are opened
    @Inject(method = "added", at = @At("HEAD"))
    private void added(CallbackInfo ci) {
        SmoothGui.lastGuiOpenedTime = System.currentTimeMillis();
    }
}