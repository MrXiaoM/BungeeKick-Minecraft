package me.vik1395.BungeeKick;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

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

        if (!cFile.exists()) saveDefaultConfig();

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

    public void saveDefaultConfig() {
        try (InputStream in = this.getResourceAsStream("config.yml")) {
            try (FileOutputStream os = new FileOutputStream(cFile, false)) {
                try (BufferedOutputStream out = new BufferedOutputStream(os)) {
                    int data;
                    while((data = in.read()) != -1) {
                        out.write(data);
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
