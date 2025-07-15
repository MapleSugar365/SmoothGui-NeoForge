package ezzenix.smoothgui.mixin;

import ezzenix.smoothgui.SmoothGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ImageButton.class)
public class ImageButtonMixin {
    @Final
    @Shadow
    protected WidgetSprites sprites;

    // 配方按钮

    @Inject(method = "renderWidget", at = @At("HEAD"))
    private void onRender(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        if (sprites == RecipeBookComponent.RECIPE_BUTTON_SPRITES) {
            gh.pose().translate(0.0, SmoothGui.getOffsetY(), 0.0);
        }
    }

    @Inject(method = "renderWidget", at = @At("TAIL"))
    private void onRenderEnd(GuiGraphics gh, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothGui.isInMenu())
            return;
        if (sprites == RecipeBookComponent.RECIPE_BUTTON_SPRITES) {
            gh.pose().translate(0.0, -SmoothGui.getOffsetY(), 0.0);
        }
    }
}