package com.darkerminecraft.hubmaster.npc;

import com.darkerminecraft.hubmaster.HubMaster;
import com.darkerminecraft.hubmaster.services.Service;
import com.darkerminecraft.hubmaster.utils.NPCStorageUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NPCManager extends Service {

    @Getter
    private List<NPC> activeNPCs;

    public NPCManager(HubMaster plugin) {
        super(plugin, "NPC Manager");
    }

    @Override
    public void onStart() {
        activeNPCs = NPCStorageUtil.loadNPCs();
        if(activeNPCs == null) activeNPCs = new ArrayList<>();
    }

    @Override
    public void onStop() {
        NPCStorageUtil.saveNPCs();
        activeNPCs.clear();
    }

    public void addActiveNPC(NPC npc) {
        if(!activeNPCs.contains(npc)) {
            activeNPCs.add(npc);
            NPCStorageUtil.createNPC(npc);
        }
    }

    public void removeActiveNPC(NPC npc) {
        activeNPCs.remove(npc);
        NPCStorageUtil.deleteNPC(npc.getNpc().getId());
    }

    public NPC getNPCFromID(int entityID) {
        NPC npc = null;
        for (NPC activeNPC : activeNPCs) {
            if (activeNPC.getNpc().getId() == entityID) {
                npc = activeNPC;
            }
        }
        return npc;
    }
}
