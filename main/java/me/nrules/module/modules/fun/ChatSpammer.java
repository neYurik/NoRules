package me.nrules.module.modules.fun;

import me.nrules.GUIClick.settings.Setting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChatSpammer extends Module {

    ArrayList<String> message;
    TimerHelper timerHelper = new TimerHelper();

    public ChatSpammer() {
        super("ChatSpammer", Category.FUN);
        message = new ArrayList<String>();
        message.add("!NoRules users the best! " + "NR_CL1ENT -> " + " [" + getRandomString(15) + "]");
        message.add("!NoRules perfect! " + "NR_CL1ENT -> " + " [" + getRandomString(15) + "]");
        message.add("!I'm using NoRules because of I've parents! " + "NR_CL1ENT -> " + " [" + getRandomString(15) + "]");
        message.add("!NoRules the best! " + "NR_CL1ENT -> " + " [" + getRandomString(15) + "]");
        message.add("!NoRules better than all modifications! " + "NR_CL1ENT -> " + " [" + getRandomString(15) + "]");
        message.add("!NoRules NoRules NoRules.. Wonderful! " + "NR_CL1ENT -> " +" [" + getRandomString(15) + "]");
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event)
    {
        if (mc.player == null && mc.world == null)
            return;

        if (timerHelper.hasReached(5000))
        {
            Random r = new Random();
            int index = r.nextInt(message.size());
            String messages = message.get(index);

            mc.player.sendChatMessage(messages);
            timerHelper.reset();
        }

    }

    public static String getRandomString(double d)
    {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d; i++)
        {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
