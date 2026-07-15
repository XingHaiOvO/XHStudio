package cn.xh_net.studio.service;


import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

/**
 * 重置密码服务接口
 * @author XingHai
 * @date 2026/7/15 19:54
 * @description 重置密码服务接口
 */
public interface IPasswordService {

    /**
     * 重置密码请求
     * @param email 邮箱
     */
    void resetPasswordRequest(String email) throws MessagingException, UnsupportedEncodingException;
}
