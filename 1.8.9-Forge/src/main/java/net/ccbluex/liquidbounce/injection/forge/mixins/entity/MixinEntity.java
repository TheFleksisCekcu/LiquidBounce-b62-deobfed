
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.features.module.modules.exploit.NoPitchLimit;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.UUID;
import net.minecraft.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.AxisAlignedBB;
import java.util.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float rotationPitch;
    @Shadow
    public float rotationYaw;
    @Shadow
    public Entity ridingEntity;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public boolean onGround;
    @Shadow
    public boolean isAirBorne;
    @Shadow
    public boolean noClip;
    @Shadow
    public World worldObj;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    public float stepHeight;
    @Shadow
    public boolean isCollidedHorizontally;
    @Shadow
    public boolean isCollidedVertically;
    @Shadow
    public boolean isCollided;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    protected Random rand;
    @Shadow
    public int fireResistance;
    @Shadow
    protected boolean inPortal;
    @Shadow
    public int timeUntilPortal;
    @Shadow
    public float width;
    @Shadow
    private int nextStepDistance;
    @Shadow
    private int fire;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public float prevRotationYaw;
    
    @Shadow
    public abstract boolean isSprinting();
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Shadow
    public void moveEntity(final double x, final double y, final double z) {
    }
    
    @Shadow
    public abstract boolean isInWater();
    
    @Shadow
    public abstract boolean isRiding();
    
    @Shadow
    public abstract void setFire(final int p0);
    
    @Shadow
    protected abstract void dealFireDamage(final int p0);
    
    @Shadow
    public abstract boolean isWet();
    
    @Shadow
    public abstract void addEntityCrashInfo(final CrashReportCategory p0);
    
    @Shadow
    protected abstract void doBlockCollisions();
    
    @Shadow
    protected abstract void playStepSound(final BlockPos p0, final Block p1);
    
    @Shadow
    public abstract void setEntityBoundingBox(final AxisAlignedBB p0);
    
    @Shadow
    protected abstract Vec3 getVectorForRotation(final float p0, final float p1);
    
    @Shadow
    public abstract UUID getUniqueID();
    
    public int getNextStepDistance() {
        return this.nextStepDistance;
    }
    
    public void setNextStepDistance(final int nextStepDistance) {
        this.nextStepDistance = nextStepDistance;
    }
    
    public int getFire() {
        return this.fire;
    }
    
    @Inject(method = { "getCollisionBorderSize" }, at = { @At("HEAD") }, cancellable = true)
    private void getCollisionBorderSize(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        final HitBox hitBox = (HitBox)ModuleManager.getModule(HitBox.class);
        if (hitBox.getState()) {
            callbackInfoReturnable.setReturnValue(0.1f + hitBox.sizeValue.asFloat());
        }
    }
    
    @Inject(method = { "setAngles" }, at = { @At("HEAD") }, cancellable = true)
    private void setAngles(final float yaw, final float pitch, final CallbackInfo callbackInfo) {
        if (ModuleManager.getModule(NoPitchLimit.class).getState()) {
            callbackInfo.cancel();
            final float f = this.rotationPitch;
            final float f2 = this.rotationYaw;
            this.rotationYaw += (float)(yaw * 0.15);
            this.rotationPitch -= (float)(pitch * 0.15);
            this.prevRotationPitch += this.rotationPitch - f;
            this.prevRotationYaw += this.rotationYaw - f2;
        }
    }
}
