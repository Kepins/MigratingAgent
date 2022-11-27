package org.example.behaviours;

import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import org.example.agents.MigratingAgent;

import java.util.Optional;

public class MigratingBehaviour extends SimpleBehaviour {
    private boolean done = false;
    protected final MigratingAgent myAgent;
    public MigratingBehaviour(MigratingAgent agent){
        myAgent = agent;
    }

    @Override
    public void action(){
        Optional<Location> nextLocation = myAgent.getNextLocation();
        if(nextLocation.isPresent()){
            myAgent.doWait(750);
            myAgent.doMove(nextLocation.get());
        }
        else {
            done = true;
        }
    }

    @Override
    public boolean done() {
        return done;
    }

}
