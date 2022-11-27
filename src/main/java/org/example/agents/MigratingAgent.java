package org.example.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;
import jade.wrapper.ControllerException;
import org.example.behaviours.RequestContainersListBehaviour;

import java.util.List;
import java.util.Optional;

public class MigratingAgent extends Agent {
    private Location initialLocation;
    int locationId;
    private List<Location> locations;
    @Override
    protected void setup() {
        super.setup();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());

        initialLocation = this.here();

        this.addBehaviour(new RequestContainersListBehaviour(this));
    }
    @Override
    protected void afterMove() {
        super.afterMove();
        //restore state
        //resume threads
    }
    @Override
    protected void beforeMove() {
        //stop threads
        //save state
        super.beforeMove();
    }

    public void setLocations(List<Location> locations){
        locationId = 0;
        this.locations = locations;
        this.locations.add(initialLocation);
    }
    public Optional<Location> getNextLocation(){
        if(locationId >= locations.size()){
            locationId = 0;
        }
        return Optional.of(locations.get(locationId++));
    }
}