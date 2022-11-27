package org.example.behaviours;


import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Result;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.example.agents.MigratingAgent;

import java.util.ArrayList;
import java.util.List;


public class ReceiveContainersLisBehaviour extends SimpleBehaviour {
    private boolean done = false;
    private String conversationId;
    private MessageTemplate mt;
    protected final MigratingAgent myAgent;
    public ReceiveContainersLisBehaviour(MigratingAgent agent, String conversationID) {
        super(agent);
        myAgent = agent;
        conversationId = conversationID;
    }
    @Override
    public void onStart() {
        super.onStart();
        mt = MessageTemplate.MatchConversationId(conversationId);
    }
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            done = true;
            try {
                ContentElement ce =
                        myAgent.getContentManager().extractContent(msg);
                jade.util.leap.List items = ((Result) ce).getItems();
                List<Location> locations = new ArrayList<>();
                items.iterator().forEachRemaining(i -> {
                        locations.add((Location) i);
                });
                locations.remove(myAgent.here());
                myAgent.setLocations(locations);
                myAgent.addBehaviour(new MigratingBehaviour(myAgent));
            } catch (Codec.CodecException | OntologyException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    @Override
    public boolean done() {
        return done;
    }
}