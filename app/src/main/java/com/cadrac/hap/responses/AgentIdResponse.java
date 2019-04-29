package com.cadrac.hap.responses;

public class AgentIdResponse {
    Data[] data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }



    public class Data{

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        String agent_name;


        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        String agentid;
        String supervisor_id;

        public String getSupervisor_id() {
            return supervisor_id;
        }

        public void setSupervisor_id(String supervisor_id) {
            this.supervisor_id = supervisor_id;
        }
    }
}
