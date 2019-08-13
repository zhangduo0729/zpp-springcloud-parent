package org.zpp.user.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.api.dto.UserDTO;
import org.zpp.common.constant.MQConstant;
import org.zpp.common.template.MobileCodeTemplate;
import org.zpp.user.service.SysUserService;

/**
 * @author zpp
 * @date 2018/12/10 15:42
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SysUserService userService;

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * 发送短信验证码
     * @return
     */
    @PostMapping("/sms/mobile/{phone}")
    public String send(){
        rabbitTemplate.convertAndSend(MQConstant.QUEUE_MOBILE_CODE,
                new MobileCodeTemplate("",MQConstant.MOBILE_CODE_CHANNEL_ALI,"" ));
        return "";
    }
}