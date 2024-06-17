package nw.rifthelper.utils;

import net.runelite.api.Client;

import javax.inject.Inject;

public class Strategy
{
    /*
    has a strat name
    has a function called process last action which tells the current req to process the action
    has a list of requirement objects
        reqs have a list of step objects
            steps are a single action
            steps have a method isStepFinished
            steps have a toString method
        reqs have a method isReqFinished which is true if all steps inside are finished

     */

    @Inject
    private Client client;

    public Strategy(Client client)
    {
        this.client = client;
    }

    @Override
    public String toString()
    {
       return "test";
    }
}
