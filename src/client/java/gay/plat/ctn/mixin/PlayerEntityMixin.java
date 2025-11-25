package gay.plat.ctn.mixin;

import gay.plat.ctn.CooldownTrickNotifier;
import gay.plat.ctn.config.CtnConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private ItemStack prevMainHandStack = ItemStack.EMPTY;

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        prevMainHandStack = getMainHandStack();
    }

    @Inject(at = @At("HEAD"), method = "attack")
    private void onAttack(Entity target, CallbackInfo info) {
        final CtnConfig config = CooldownTrickNotifier.configManager.getConfig();
        if (config.shouldPlaySound((PlayerEntity)(Object)this) && !ItemStack.areEqual(prevMainHandStack, getMainHandStack())) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.playSound(Registries.SOUND_EVENT.get(Identifier.tryParse(config.sound)), config.volume, config.pitch);
        }
    }
}