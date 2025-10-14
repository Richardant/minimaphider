package com.minimaphider;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
        name = "Minimap Hider",
        description = "Hides minimap",
        tags = {"minimap", "hide"}
)
public class MinimapHiderPlugin extends Plugin
{
    @Inject
    private Client client;

    @Override
    protected void startUp()
    {
        setMapHidden(true);
    }

    @Override
    protected void shutDown()
    {
        setMapHidden(false);
    }

    @Subscribe
    public void onScriptPostFired(ScriptPostFired s)
    {
        if (s.getScriptId() == ScriptID.TOPLEVEL_REDRAW || s.getScriptId() == 903 || s.getScriptId() == 2396)
        {
            setMapHidden(true);
        }
    }

    private void setMapHidden(boolean hidden)
    {
        Widget classic = client.getWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, 22);
        if (classic != null)
        {
            classic.setHidden(hidden);
        }
        for (int i = 23; i <= 32; i++)
        {
            Widget modern = client.getWidget(InterfaceID.TOPLEVEL_PRE_EOC, i);
            if (modern != null)
            {
                modern.setHidden(hidden);
            }
        }
    }
}
