package com.tisawesomeness.notoasts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoToasts extends JavaPlugin {

    @Override
    public void onEnable() {

        if (getServer().shouldSendChatPreviews()) {
            getLogger().info("Chat preview is enabled and will show players a warning on join.\nIf you do not use a chat preview plugin, set \"previews-chat\" to false in server.properties to disable the warning.");
        }

        if (is1_19_1()) {
            injectVanilla();
        } else if (Bukkit.getPluginManager().getPlugin("ViaVersion") != null) {
            injectViaVersion();
        } else {
            getLogger().warning("1.19.0 servers without ViaVersion cannot show the warning toast, this plugin will do nothing");
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    private void injectVanilla() {
        if (Bukkit.getPluginManager().getPlugin("FreedomChat") == null) {
            getLogger().info("Version is 1.19.1 or higher, using vanilla strategy...");
            try {
                new ServerDataProtocol(this); // Registers the protocol on construction
            } catch (Exception e) {
                getLogger().severe("Failed to initialize TinyProtocol injector!");
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
            }
        } else {
            getLogger().info("Found FreedomChat, not duplicating packet injection...");
        }
    }

    private void injectViaVersion() {
        getLogger().info("Found ViaVersion, injecting...");
        try {
            var clazz = Class.forName("com.tisawesomeness.notoasts.ViaVersionInit");
            clazz.getConstructor().newInstance(); // Constructor does ViaVersion injection
        } catch (Exception e) {
            getLogger().severe("Failed to initialize ViaVersion injector!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean is1_19_1() {
        return getServer().getBukkitVersion().startsWith("1.19.");
    }

}
