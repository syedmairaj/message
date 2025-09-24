package com.message.Service;

import com.message.Repository.SMSEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Autowired
    private SMSEntityRepository smsRepo;
}
