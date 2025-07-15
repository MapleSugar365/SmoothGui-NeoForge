package ezzenix.smoothgui.mixin;

import ezzenix.smoothgui.SmoothGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    // 容器屏幕
    // 修复AbstractContainerScreen透明背景会被平移影响的BUG
    // 在渲染开始时应用平移
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    // 在渲染结束时取消平移
    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }

    // 在渲染半透明背景前取消平移
    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE))
    private void beforeRenderTransparentBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta,
            CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }

    // 在渲染半透明背景后恢复平移
    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.AFTER))
    private void afterRenderTransparentBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta,
            CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    // 在渲染背景前应用平移（确保打开的GUI有平滑效果）
    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", shift = At.Shift.BEFORE))
    private void beforeRenderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    // 在渲染容器背景后恢复平移
    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", shift = At.Shift.AFTER))
    private void afterRenderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        guiGraphics.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }
}