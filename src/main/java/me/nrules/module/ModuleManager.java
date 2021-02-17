package me.nrules.module;

import me.nrules.Main;
import me.nrules.module.modules.combat.*;
import me.nrules.module.modules.fun.AntiAim;
import me.nrules.module.modules.fun.ChatSpammer;
import me.nrules.module.modules.fun.FakeHack;
import me.nrules.module.modules.ghost.AimAssist;
import me.nrules.module.modules.ghost.Hitbox;
import me.nrules.module.modules.ghost.SelfDelete;
import me.nrules.module.modules.ghost.SelfDestruct;
import me.nrules.module.modules.misc.*;
import me.nrules.module.modules.movement.*;
import me.nrules.module.modules.player.*;
import me.nrules.module.modules.render.*;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager
{

    public static ArrayList<Module> modules;

    public static Module getModule(Class moduleClass) {
        for (Module module : modules) {
            if (module.getClass() != moduleClass) {
                continue;
            }
            return module;
        }
        return null;
    }

    public ModuleManager()
    {


        (modules = new ArrayList<Module>()).clear();
        
        //COMBAT
        if (Main.hacks()) {
            modules.add(new AutoCrystal());
            modules.add(new AutoTotems());
            modules.add(new Killaura());
            modules.add(new Criticals());
            modules.add(new Velocity());
            modules.add(new FastBow());
            modules.add(new AntiBot());
            modules.add(new AimBot());

            //MOVEMENT
            modules.add(new WaterJump());
            modules.add(new ElytraFly());
            modules.add(new NoFall());
            modules.add(new NoClip());
            modules.add(new InvMove());
            modules.add(new NoSlow());
            modules.add(new Speed());
            modules.add(new Jesus());
            modules.add(new Step());
            modules.add(new Fly());

            //PLAYER
            modules.add(new TargetStrafe());
            modules.add(new AutoGapple());
            modules.add(new AutoShift());
            modules.add(new AutoMine());
            modules.add(new Freecam());
            modules.add(new AntiAFK());
            modules.add(new NoPush());
            modules.add(new Timer());
            modules.add(new Sprint());
            modules.add(new VClip());


            //RENDER
            modules.add(new FullBright());
            modules.add(new ClickGUI());
            modules.add(new WallHack());
            modules.add(new PotionUI());
            modules.add(new NameTags());
            modules.add(new PlayerUI());
            modules.add(new ChestUI());
            modules.add(new ArmorUI());
            modules.add(new ItemUI());
            modules.add(new NoRain());
            //modules.add(new HUD2());
            modules.add(new HUD());

            //FUN
            modules.add(new ChatSpammer());
            modules.add(new AntiAim());
            modules.add(new FakeHack());

            //MISC
            modules.add(new MClickFriend());
            modules.add(new SelfDestruct());
            modules.add(new PingSpoof());
            modules.add(new NoHurtCam());

            //GHOST
            modules.add(new SelfDelete());
            modules.add(new AimAssist());
            modules.add(new Hitbox());
        }
    }

    public Module getModule(String name)
    {
        for (Module m : this.modules)
        {
            if (m.getName().equalsIgnoreCase(name))
            {
                return m;
            }
        }
        return null;
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Module> getModulesInCategory(Category categoryIn)
    {
        ArrayList<Module> mods = new ArrayList<Module>();
            for(Module m : this.modules)
            {
                if(m.getCategory() == categoryIn)
                    mods.add(m);
            }
            return mods;
    }

    public ArrayList<Module> getModuleList()
    {
        return this.modules;
    }

    public static List<Module> getModulesByCategory(Category c)
    {
        List<Module> modules = new ArrayList<Module>();

        for (Module m : Main.moduleManager.modules)
        {
            if (m.getCategory() == c)
                modules.add(m);
        }
        return modules;
    }


}
