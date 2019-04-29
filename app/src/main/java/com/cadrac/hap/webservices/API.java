package com.cadrac.hap.webservices;


import com.cadrac.hap.activites.AddOn;
import com.cadrac.hap.responses.AddOnRideInResponse;
import com.cadrac.hap.responses.AgentIdResponse;
import com.cadrac.hap.responses.Agent_Assigned_Ride_Response;
import com.cadrac.hap.responses.Agent_Direct_Response;
import com.cadrac.hap.responses.Agent_DriverFeedBack_Response;
import com.cadrac.hap.responses.Agent_Status_For_RideOut_Response;
import com.cadrac.hap.responses.Agent_status_response;
import com.cadrac.hap.responses.Agent_status_values_response;
import com.cadrac.hap.responses.DigCashResponse;
import com.cadrac.hap.responses.DriverHistoryResponse;
import com.cadrac.hap.responses.Driver_Registration;
import com.cadrac.hap.responses.Driver_StatusResponse;
import com.cadrac.hap.responses.Drivers_Availabilty_Response;
import com.cadrac.hap.responses.HapResponse;
import com.cadrac.hap.responses.Pass_Driver_Availability_List;
import com.cadrac.hap.responses.PlacesResponse;
import com.cadrac.hap.responses.RideInCostDetailsResponse;
import com.cadrac.hap.responses.RideInDriverDetailsResponse;
import com.cadrac.hap.responses.RideInHistoryResponse;
import com.cadrac.hap.responses.Ridein_Response;
import com.cadrac.hap.responses.Rideout_HistoryResponse;
import com.cadrac.hap.responses.Rideout_Response;
import com.cadrac.hap.responses.SeatsResponse;
import com.cadrac.hap.responses.SettlementResponse;
import com.cadrac.hap.responses.Supervisor_Collected_Response;
import com.cadrac.hap.responses.Supervisor_Status_Response;
import com.cadrac.hap.responses.agent_Login_Response;
import com.cadrac.hap.responses.driver_Login_Response;
import com.cadrac.hap.responses.request_response;
import com.cadrac.hap.responses.statusResponse;
import com.cadrac.hap.responses.supervisior_Login_Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("users_login.php")//supervisor login
    Call<supervisior_Login_Response> supervisorlogin(
            @Query("username") String username,
            @Query("password") String password,
            @Query("utype1") String utype1
            );


    @GET("users_login.php")//driver login
    Call<driver_Login_Response> driverlogin(
            @Query("username") String username,
            @Query("password") String password,
            @Query("utype1") String utype1,
            @Query("from") String from);

    @GET("hapride_places.php")//places
    Call<PlacesResponse> source(
    );

    @GET("hapride_places.php")//places
    Call<PlacesResponse> destination(
            @Query("from") String from
   );
    @GET("users_login.php")// agent login
    Call<agent_Login_Response> agentlogin(
            @Query("username") String username,
            @Query("password") String password,
            @Query("utype1") String utype1,
            @Query("from") String from);


    @GET("Driver_Registration.php")//driver registration
    Call<Driver_Registration> driverregistration(
            @Query("firstName") String firstName,
            @Query("lastName") String lastName,
            @Query("mobileNumber") String mobileNumber,
            @Query("vehicleType") String vehicleType,
            @Query("seats") String seats,
            @Query("vehicleNumber") String vehicleNumber,
//            @Query("rcNumber") String rcNumber,
            @Query("licenseNumber") String licenseNumber,
            @Query("pollutionCheck") String pollutionCheck,
            @Query("p_expiryDate") String p_expiryDate,
            @Query("insurance") String insurance,
            @Query("i_expiryDate") String i_expiryDate,
//            @Query("voterId") String voterId,
            /* @Query("createddate") String createddate,*/
            @Query("createdBy") String createdBy
    );

    @GET("hap_ride_Driver_List.php")//driver aviablabilty list/*hap_ride_Driver_List.php*/
    Call<Drivers_Availabilty_Response> DriverSpinner(@Query("from") String from);


    @GET("hap_ride_driver.php")//driver aviablabilty list
    Call<Drivers_Availabilty_Response> source_driver_availabilty(
            @Query("from") String from

            );


    @GET("hap_getIn.php")//rideinresponse
    Call<Ridein_Response> rideIn(@Query("from") String from,
                                @Query("to") String to,
                                @Query("driver_name") String driver_name,
                                @Query("driver_no") String driver_no,
                                @Query("vech_no") String vech_no,
                                @Query("vech_type") String vech_type,
                                @Query("no_of_passengers") String no_of_passengers,
                                @Query("total_fare") String total_fare,
                                //  @Query("driver_name") String driver_name,
//                              @Query("trans_id") String trans_id,,
                                @Query("username") String username,
                                @Query("g_out_id") String g_out_id,
                                @Query("driver_id") String driver_id,
                                 @Query("supervisor_id") String supervisor_id,
                                 @Query("estimatedTime") String estimatedTime


    );

    @GET("hap_ride_agentid.php")//agentsid_inrideinspinner
    Call<AgentIdResponse> agentIdSpinner(@Query("destination") String destination);

    @GET("hap_ride_agentid.php")//agentsid_inrideinspinner
    Call<AgentIdResponse> agentId(@Query("agent_name") String name);


    @GET("hap_getInDriverDetails.php")//rideindriverdetails_aviableforparticularlocation
    Call<RideInDriverDetailsResponse>  rideInDriverdetails(@Query("driver_fname") String d_fName,
                                                           @Query("driver_lname") String d_lName);

    @GET("hap_getInCostDetails.php")
    Call<RideInCostDetailsResponse> rideInCostDetails(@Query("source") String from,
                                                     @Query("destination") String destination);


    //to get  & update the driver status

    @GET("hapride_driverstatus.php")
    Call<Driver_StatusResponse> driverStatus(
            @Query("username") String username,
            @Query("driver_status") String driver_status,
            @Query("action") String action);

    @GET("getin_history.php")
    Call<RideInHistoryResponse> hap_ridein_hist(@Query("gid") String gid);

    @GET("getout_history.php")
    Call<Rideout_HistoryResponse> hap_rideout_history(@Query("goid") String goid);


    @GET("hapride_getOut.php")
    Call<Rideout_Response> hap_rideout(@Query("username1") String username);


    @GET("hapride_assign.php")
    Call<Rideout_Response> hap_assign(@Query("agent_id") String agent_id,
                                    @Query("agents") String username);

    @GET("hapride_collect.php")
    Call<Ridein_Response> getCollect( @Query("ride_id") String ride_id,
                                    @Query("d_id") String driver_id,
                                    @Query("p_count") String passengerCount,
                                    @Query("amount") String amount,
                                    @Query("digital") String digital,
                                    @Query("cash") String cash,
                                      @Query("s_id") String sid,

                                      @Query("getin_agid") String getin_agid,
                                    @Query("getout_agid") String goid);


    @GET("hapride_assign1.php")
    Call<Ridein_Response> hap_assign1(
            @Query("rideid") String rideid,
            @Query("latestid") String latestid,
            @Query("oldid") String oldid);


    @GET("agent_send_msg.php")//srikanth status task
    Call<Agent_Status_For_RideOut_Response> hap_super(@Query("s_id") String s_id,
                                                      @Query("message") String message,
                                                      @Query("queryStatus") String queryStatus);


    @GET("agent_update_msg.php")//srikantha status task
    Call<Agent_Status_For_RideOut_Response> hap_delete(@Query("s_id") String s_id,
                                                       @Query("queryStatus") String queryStatus);


    @GET("hap_ride_places.php")//places_from_agent_rideout
    Call<Agent_status_values_response> sources();


    @GET("agent_status_value.php")//agent_amith
    Call<Agent_status_response> sourcing(@Query("source") String agent_status,
                                         @Query("agent_id") String agent_id,
                                         @Query("action") String action);



    @GET("swr_ret_msg.php")//srikanth
    Call<Agent_Status_For_RideOut_Response> hap_msg(@Query("s_id") String s_id);

    @GET("agent_mobile.php")//srikanth
    Call<Agent_Status_For_RideOut_Response> hap_mobile(@Query("s_id") String s_id);

 @GET("get_s_details1.php")//srikanth
 Call<Agent_Status_For_RideOut_Response> getSupervisordetails(@Query("sid") String sid);



    @GET("hapride_password.php")//chnage password
    Call<supervisior_Login_Response> supervisor_change_pwd(
            @Query("u_id") String u_id,
            @Query("u_num") String u_num,
            @Query("u_pwd") String u_pwd,
            @Query("utype1") String utype1);

    @GET("ratingbar.php")//srnivas
    Call<Agent_DriverFeedBack_Response> feedbackform(
            @Query("r_id") String rid,
            @Query("go_id") String go_id,
            @Query("d_id") String d_id,
            @Query("rating") String rating,
            @Query("comments") String comments);

    @GET("hap_status.php")//agent_amith_issueresolve
    Call<Agent_Direct_Response> sourcing1(@Query("agent_id") String agent_id);

    @GET("supervisor_collected.php")
    Call<Supervisor_Collected_Response> hap_rideout1(@Query("username1") String username);


//    @GET("hap_digcash_settlement.php")
         @GET("naveen2.php")//php/elibrary/get_books.php
    Call<DigCashResponse> digcash(
            @Query("cash") String cash,
            @Query("digital") String digital,
            @Query("s_id") String s_id,
            @Query("gi_id") String gi_id,
            @Query("set_id") String  set_id,
            @Query("totalAmount") String totalAmount,
            @Query("totalRides") String totalRides,
            @Query("paidDate") String paidDate
            );


    @GET("hap_transaction_details.php")
    Call<SettlementResponse> settlement(
            @Query("gi_id") String gi_id,
            @Query("s_id") String s_id
    );

    @GET("hap_supervisor_getout_list.php")
    Call<Supervisor_Status_Response> hap_getout(@Query("s_id") String s_id);


    @GET("naveen.php")
    Call<Supervisor_Status_Response> hap_supervisor_collect(
            @Query("s_id") String s_id,
            @Query("agent_id") String agent_id,
            @Query("cash") String cash,
            @Query("digital") String digital,

//            @Query("pending") String pending,
            @Query("paidDate") String paidDate,
            @Query("set_id")String set_id,
            @Query("total_rides")String total_rides,
            @Query("totalPaid")String totalPaid,
            @Query("totalUnpaid")String totalUnpaid,
            @Query("cash2") String cash2,
            @Query("digital2") String digital2,
            @Query("totalFare") String totalFare
    );

    @GET("agentmsg.php")//srikanth
    Call<Agent_Status_For_RideOut_Response> getAgentMsg(@Query("sid") String sid,
                                                        @Query("aid") String aid);


    @GET("VehicleType.php")
    Call<HapResponse> vehicleType();

    @GET("SeatsCount.php")
    Call<HapResponse> seatsCount();

    @GET("hap_getSeats.php")
    Call<SeatsResponse> getSeats(@Query("driv_id") String d_id);

    @GET("addOnRideIn.php")
    Call<AddOnRideInResponse> addOn_rideIn(@Query("from") String from,
                                           @Query("to") String to,
                                           @Query("gi_id") String gi_id,
                                           @Query("d_id") String d_id,
                                           @Query("hap_pass_count") String pass_count1,
                                           @Query("hap_fare") String fare1,
                                           @Query("pass_count") String pass_count2,
                                           @Query("fare") String fare2,
                                           @Query("go_id") String go_id,
                                            @Query("supervisor_id") String sup_id);

    @GET("agentrequest.php")       //rideinresponse
    Call<request_response> request(@Query("agent_id") String un);


    @GET("hap_Pass_Driver_List.php")//driver aviablabilty list/*hap_ride_Driver_List.php*/
    Call<Pass_Driver_Availability_List> driver_availability(@Query("from") String from,
                                                            @Query("veh_type") String veh_type);

    @GET("statusChange.php")       //rideinresponse
    Call<statusResponse> status(@Query("id") String id,
                                @Query("status") String status);


    @GET("PassStatusChange.php")       //rideinresponse
    Call<statusResponse> status1(@Query("id") String id,
                                @Query("d_id") String d_id,
                                @Query("status") String status);

    @GET("countvalidation.php")//srikanth
    Call<request_response> countvalidation(@Query("agent_id") String agent_id);

    @GET("driver_history.php")
    Call<DriverHistoryResponse>driver_history(
            @Query("d_id") String d_id
    );

    @GET("assignedrides.php")
    Call<Agent_Assigned_Ride_Response>ridesassign(

            @Query("agentid") String agentid

    );

    @GET("agentCancel.php")       //rideinresponse
    Call<statusResponse> status3(@Query("id") String id,
                                @Query("status") String status);



}
