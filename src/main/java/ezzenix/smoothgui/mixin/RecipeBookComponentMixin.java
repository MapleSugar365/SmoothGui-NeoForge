package ezzenix.smoothgui.mixin;

import ezzenix.smoothgui.SmoothGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {

    // 配方窗口
    // Offset screen rendering
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        gh.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderEnd(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        gh.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
    }
}