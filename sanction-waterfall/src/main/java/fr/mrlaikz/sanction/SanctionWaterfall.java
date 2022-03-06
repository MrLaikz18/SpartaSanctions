package fr.mrlaikz.sanction;

import net.md_5.bungee.api.plugin.Plugin;

public class SanctionWaterfall extends Plugin {

    //TODO:
    //PLUGIN SPIGOT:
    //  ENVOIE DE LA COMMANDE
    //PLUGIN WATERFALL:
    //  RECEPTION COMMANDE
    //  EXECUTION PAR PROXYPLAYER

    public static SanctionWaterfall INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getProxy().registerChannel("spartachannel:command");
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener());

    }
}
