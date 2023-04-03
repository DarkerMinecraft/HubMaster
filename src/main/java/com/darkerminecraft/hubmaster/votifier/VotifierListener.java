package com.darkerminecraft.hubmaster.votifier;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    @EventHandler
    public void onVotifierEvent(VotifierEvent e) {
        Vote vote = e.getVote();
    }

}
