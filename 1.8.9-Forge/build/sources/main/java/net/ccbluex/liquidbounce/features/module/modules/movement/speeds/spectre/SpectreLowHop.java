
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.events.MoveEvent;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

public class SpectreLowHop extends SpeedMode
{
    public SpectreLowHop() {
        super("SpectreLowHop");
    }
    
    @Override
    public void onMotion() {
        if (!MovementUtils.isMoving() || SpectreLowHop.mc.thePlayer.movementInput.jump) {
            return;
        }
        if (SpectreLowHop.mc.thePlayer.onGround) {
            MovementUtils.strafe(1.1f);
            SpectreLowHop.mc.thePlayer.motionY = 0.15;
            return;
        }
        MovementUtils.strafe();
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void onMove(final MoveEvent event) {
    }
}
