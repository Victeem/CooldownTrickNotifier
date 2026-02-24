package gay.plat.ctn.mixin;

import gay.plat.ctn.CooldownTrickNotifier;
import gay.plat.ctn.config.CtnConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;piercingAttack(Lnet/minecraft/world/item/component/PiercingWeapon;)V"))
    private void onStabAttack(CallbackInfoReturnable<Boolean> cir) {
        final CtnConfig config = CooldownTrickNotifier.configManager.getConfig();
        if (config.shouldPlaySound(player)) {
            assert player != null;
            if (!ItemStack.matches(player.lastItemInMainHand, player.getMainHandItem())) {
                SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.tryParse(config.sound));
                if (sound != null)
                    player.playSound(sound, config.volume, config.pitch);
            }
        }
    }
}