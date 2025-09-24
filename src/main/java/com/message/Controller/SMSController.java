//package com.message.Controller;
//
//import com.message.Payload.APIResponse;
//import com.message.Service.SMSService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/apt/v1/sms/")
//public class SMSController {
//
//    @Autowired
//    private SMSService smsService;
//
//    @PostMapping("sendsms")
//
//    public ResponseEntity<APIResponse<String>> sendSMS(@RequestBody String fromNumber, @RequestBody String toNumber,@RequestBody String message){
//        APIResponse<String> sms = smsService.sendSMS(fromNumber,toNumber,message);
//
//    }
//
//}
