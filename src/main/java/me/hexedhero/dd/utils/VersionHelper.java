package me.hexedhero.dd.utils;

import org.bukkit.Bukkit;

public class VersionHelper {
    private final String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private final int majorVersionNumber;
    private boolean isPaper;

    public VersionHelper() {
        this.majorVersionNumber = Integer.parseInt(this.nmsVersion.split("_")[1]);

        try {
            this.isPaper = Class.forName("com.destroystokyo.paper.PaperConfig") != null;
        } catch (ClassNotFoundException ignored) {
        }

    }

    public String getNMSVersion() {
        return this.nmsVersion;
    }

    public int getMajorVersionNumber() {
        return this.majorVersionNumber;
    }

    public boolean isPaper() {
        return this.isPaper;
    }
}