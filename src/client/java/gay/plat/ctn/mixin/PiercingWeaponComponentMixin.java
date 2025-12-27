package gay.plat.ctn.mixin;

import gay.plat.ctn.CooldownTrickNotifier;
import gay.plat.ctn.access.PlayerEntityAccessor;
import gay.plat.ctn.config.CtnConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.PiercingWeaponComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiercingWeaponComponent.class)
public abstract class PiercingWeaponComponentMixin {
    @Inject(at = @At("HEAD"), method = "stab")
    private void onStab(LivingEntity attacker, EquipmentSlot slot, CallbackInfo ci) {
        if (attacker instanceof PlayerEntity player) {
            final CtnConfig config = CooldownTrickNotifier.configManager.getConfig();
            if (config.shouldPlaySound(player) && !ItemStack.areEqual(((PlayerEntityAccessor)player).ctn$getPrevMainHandStack(), player.getMainHandStack())) {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.playSound(Registries.SOUND_EVENT.get(Identifier.tryParse(config.sound)), config.volume, config.pitch);
            }
        }
    }
}