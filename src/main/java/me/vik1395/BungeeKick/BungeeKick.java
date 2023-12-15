package me.vik1395.BungeeKick;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*

Author: Vik1395
Project: BungeeKick

Copyright 2014

Licensed under Creative CommonsAttribution-ShareAlike 4.0 International Public License (the "License");
You may not use this file except in compliance with the License.

You may obtain a copy of the License at http://creativecommons.org/licenses/by-sa/4.0/legalcode

You may find an abridged version of the License at http://creativecommons.org/licenses/by-sa/4.0/
 */

public class BungeeKick extends Plugin {
    public Configuration config;
    public ConfigurationProvider cProvider;
    public File cFile;

    public void onEnable() {
        File cFolder = new File(this.getDataFolder(), "");

        if (!cFolder.exists()) {
            cFolder.mkdir();
        }

        cFile = new File(this.getDataFolder(), "/config.yml");

        if (!cFile.exists()) {
            try (FileWriter fw = new FileWriter(cFile)) {
                try (BufferedWriter out = new BufferedWriter(fw)) {
                    out.write("# This is where the player is kicked to. This is usually the lobby/hub server\n"
                            + "ServerName: \'lobby\'\n"
                            + "# Message to be sent to the player who has been kicked. This message is followed by the kick reason"
                            + "KickMessage: \'&6You have been kicked! Reason:&4 \'\n"
                            + "# Set this to True if you want the kicked player to be able to see the kick reason."
                            + "ShowKickMessage: true\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
        try {
            config = cProvider.load(cFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    public void onDisable() {
        config = null;
    }

}
