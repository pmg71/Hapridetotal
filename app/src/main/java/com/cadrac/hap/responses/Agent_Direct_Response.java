package com.cadrac.hap.responses;



public class Agent_Direct_Response {
    String message,status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Agent_Direct_Response.data[] getData() {
        return data;
    }

    public void setData(Agent_Direct_Response.data[] data) {
        this.data = data;
    }

    Agent_Direct_Response.data[] data;



    public class data
    {

        String agentAvailability;

        public String getAgentAvailability() {
            return agentAvailability;
        }

        public void setAgentAvailability(String agentAvailability) {
            this.agentAvailability = agentAvailability;
        }
    }
}
