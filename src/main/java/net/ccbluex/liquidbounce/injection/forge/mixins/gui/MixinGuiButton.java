/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import me.zywl.fdpclient.FDPClient;
import net.ccbluex.liquidbounce.features.module.modules.client.ClientSpoof;
import net.ccbluex.liquidbounce.features.module.modules.client.button.AbstractButtonRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton extends Gui {

   @Shadow
   public int xPosition;

   @Shadow
   public int yPosition;

   @Shadow
   public int width;

   @Shadow
   public int height;

   @Shadow
   public boolean hovered;

   @Shadow
   public boolean enabled;

   @Shadow
   public boolean visible;

   @Final
   @Shadow
   protected static ResourceLocation buttonTextures;

   @Shadow
   protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

   @Shadow
   protected abstract int getHoverState(boolean p_getHoverState_1_);

   @Shadow
   public String displayString;

   @Unique
   protected final AbstractButtonRenderer fDPClient$buttonRenderer = Objects.requireNonNull(FDPClient.moduleManager.getModule(ClientSpoof.class)).getButtonRenderer((GuiButton)(Object)this);

   /**
    * @author liuli
    */
   @Inject(method = "drawButton", at = @At("HEAD"), cancellable = true)
   public void drawButton(Minecraft mc, int mouseX, int mouseY, CallbackInfo ci) {
      if(this.fDPClient$buttonRenderer != null) {
         if(!visible) {
            return;
         }
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         this.mouseDragged(mc, mouseX, mouseY);
         fDPClient$buttonRenderer.render(mouseX, mouseY, mc);
         fDPClient$buttonRenderer.drawButtonText(mc);
         ci.cancel();
      }
   }

}
