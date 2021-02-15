package me.nrules.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MotionUtils
{

    public static Minecraft mc = Minecraft.getMinecraft();

    public static float getDirection() {
        float var1 = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0F) {
            var1 += 180.0F;
        }
        float forward = 1.0F;
        if (mc.player.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.player.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (mc.player.moveStrafing > 0.0F) {
            var1 -= 90.0F * forward;
        }
        if (mc.player.moveStrafing < 0.0F) {
            var1 += 90.0F * forward;
        }
        var1 *= 0.017453292F;
        return var1;
    }

    public static void targetSpeed(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.player.motionX = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
        mc.player.motionZ = (forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    private float getDirection1() {
        float forward, direction = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0F)
            direction += 180.0F;
        if (mc.player.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.player.moveForward > 0.0F) {
            forward = 0.5F;
        } else {
            forward = 1.0F;
        }
        if (mc.player.moveStrafing > 0.0F) {
            direction -= 90.0F * forward;
        } else if (mc.player.moveStrafing < 0.0F) {
            direction += 90.0F * forward;
        }
        direction *= 0.017453292F;
        return direction;
    }

    public static void setMotion(double speed) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static void setMotion(double speed, float directionInYaw) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = directionInYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public double getSpeed() {
        return Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ);
    }

    public static float getSpeed1() {
        return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    private void setSpeed(double speed) {
        mc.player.motionX = -(Math.sin(getDirection()) * speed);
        mc.player.motionZ = Math.cos(getDirection()) * speed;
    }


}
