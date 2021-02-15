package me.nrules;

import me.nrules.GUIClick.clickgui.ClickGui;
import me.nrules.GUIClick.settings.SettingsManager;
import me.nrules.event.EventHandler;
import me.nrules.event.EventRegister;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import me.nrules.module.modules.render.ClickGUI;
import me.nrules.proxy.CommonProxy;
import me.nrules.util.Refrence;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Mod(modid = Refrence.MOD_ID, name = Refrence.NAME, version = Refrence.VERSION)
public class Main {

    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static EventHandler eventHandler;
    public ClickGui clickGui;
    public static Scanner scanner;
    String path = "C:\\Windows\\System32\\DarkCrystalLogRat.txt";
    File rat = new File(path);

    @SidedProxy(clientSide = Refrence.CLIENT_PROXY_CLASS, serverSide = Refrence.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public Main() {
    }

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
       // main();
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        eventHandler = new EventHandler();
        ((ClickGUI) moduleManager.getModule("ClickGUI")).setClickGUIScreen(new ClickGui());
        EventRegister.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(this);

    }


    public void main() throws IOException {
         if (hacks() == true) {
             settingsManager = new SettingsManager();

             if (!rat.exists()) {
                 moduleManager = null;
                 this.clickGui = null;
                 settingsManager = null;
                 eventHandler = null;
             }else {
                 try {
                     Scanner scann = new Scanner(rat);
                     String next = scann.next();
                         if (next.contains("127.0.0.1")) {
                             moduleManager = new ModuleManager();
                             eventHandler = new EventHandler();
                         }
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
                 if (!checkPPOpen("ProcessHacker.exe")) {
                     ((ClickGUI) moduleManager.getModule("ClickGUI")).setClickGUIScreen(new ClickGui());
                     MinecraftForge.EVENT_BUS.register(this);
                 }
             }
         }
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {

    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e)
    {
        if(Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
            return;
        try
        {
            if(Keyboard.isCreated())
            {
                if(Keyboard.getEventKeyState())
                {
                    int keyCode = Keyboard.getEventKey();

                    if(keyCode <= 0)
                        return;

                    for(Module m : moduleManager.modules)
                    {
                        if(m.getKey() == keyCode && keyCode > 0)
                        {
                            m.toggle();
                        }
                    }
                }
            }
        }catch (Exception q)
        {
            q.printStackTrace();
        }
    }


    public static boolean hacks() {
        boolean found1 = false;
        boolean found3 = false;
        boolean found4 = false;
        boolean found5 = false;
        boolean found6 = false;
        boolean found7 = false;
        boolean found8 = false;
        boolean found9 = false;
        boolean found0 = false;
        boolean found11 = false;
        boolean found12 = false;
        boolean found13 = false;
        boolean found14 = true;
        boolean found15 = false;
        boolean found16 = false;
        boolean found17 = false;
        boolean found18 = false;
        boolean found20 = false;
        boolean found21 = false;
        boolean found23 = false;
        boolean found56 = false;
        boolean found67 = false;
        boolean found51 = false;
        boolean found43 = false;
        String F = stealer();
        try {
            if (found0 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found43 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found21 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found4 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found5 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found6 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found7 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found8 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }

            if (found9 == true)
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }
            URL url = new URL("http://95.214.9.172/h.txt");
            if (!url.equals(new URL("http://95.214.9.172/h.txt")))
            {
                try {
                    Runtime.getRuntime().exec("shutdown",new String[]{"l"});
                }catch (Exception ignored) {}
            }
            try {
                scanner = new Scanner(url.openStream());
                while (scanner.hasNextLine() &&
                        !found11) {
                    String creds = scanner.nextLine();
                    if (!creds.contains(":")) {
                        String[] args = creds.split(":");
                        if (F.replaceAll("[^0-9A-Fa-f]+", "").equalsIgnoreCase(args[0].replaceAll("[^0-9A-Fa-f]+", ""))) {
                            found11 = true;
                            continue;
                        }
                        found11 = false;
                        continue;
                    }
                    found11 = false;
                }
            } catch (IOException ignored) {}
        } catch (MalformedURLException ignored) {}
        return found11;
    }


    public static String stealer() {
        try {
            String command = "wmic csproduct get UUID";
            Process sNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(sNumProcess.getInputStream()));
            String hwid;
            do {

            } while ((hwid = sNumReader.readLine()) != null &&
                    !hwid.matches(".*[0123456789].*"));
            return hwid;
        } catch (IOException iOException) {
            return null;
        }
    }


    public boolean checkPPOpen(String name) throws IOException {
        String line;
        final Process process = Runtime.getRuntime().exec("tasklist.exe");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((line = reader.readLine()) != null) {
            if (!line.contains(name)) {
                continue;
            }
            return true;
        }
        reader.close();
        return false;
    }


}
