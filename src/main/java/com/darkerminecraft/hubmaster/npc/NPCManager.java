package com.darkerminecraft.hubmaster.npc;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.services.Service;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NPCManager extends Service {

    @Getter
    private List<NPC> activeNPCs;

    public NPCManager(HubMaster plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void onStart() {
        activeNPCs = new ArrayList<>();
    }

    @Override
    public void onStop() {
        activeNPCs.clear();
    }

    public void addActiveNPC(NPC npc) {
        if(!activeNPCs.contains(npc))
            activeNPCs.add(npc);
    }

    public void removeActiveNPC(NPC npc) {
        activeNPCs.remove(npc);
    }
}
