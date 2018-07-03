package com.rjzd.baby.model;

import android.text.TextUtils;
import com.orhanobut.logger.Logger;
import com.rjzd.baby.BabyApplication;
import com.rjzd.baby.BabyConstants;
import com.rjzd.baby.tools.SerializeTools;


/**
 * 用于序列化用户信息
 *
 * Created by Hition on 2017/10/17.
 */

public final class UserInfoCenter {

    private static UserInfoCenter instance;
    private LoginModel loginBean;

    private UserInfoCenter(){
        loadLoginModel();
    }

    public static UserInfoCenter getInstance() {
        if(null == instance){
            synchronized (UserInfoCenter.class){
                if (null == instance){
                    instance = new UserInfoCenter();
                }
            }
        }
        return instance;
    }

    /**
     * 快捷方法获取用户id
     * @return userid
     */
    public String getUserId() {
        String id = "";
        if(loginBean!=null) {
            id = loginBean.getUserId();
        }
        return id;
    }

    /**
     * 获取登陆成功后的用户信息,用于判断登录状态
     *
     * @return null 未登录, else 已登录
     */
    public LoginModel getLoginModel() {
        return loginBean;
    }

    public void setLoginBean(LoginModel loginModel) {
        this.loginBean = loginModel;
        if(loginModel != null) {
            saveLoginModel(loginModel);
        }
    }

    /**
     * 登录成功后调用，将用户信息序列化到本地
     *
     * @param model 登录模型
     */
    private void saveLoginModel(LoginModel model){
        String fileName = getFileDir();
        if(!TextUtils.isEmpty(fileName)) {
            SerializeTools.serialization(fileName, model);
        }else{
            Logger.d("userinfo", "登录初始化数据失败");
        }
    }

    /**
     * 加载 文件下的LoginModel
     */
    private void loadLoginModel() {
        String fileName = getFileDir();
        if(!TextUtils.isEmpty(fileName)) {
            try {
                Object obj = SerializeTools.deserialization(fileName);
                if (obj instanceof LoginModel) {
                    loginBean = (LoginModel) obj;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 退出登录时清空用户信息
     */
    public void clearUserInfo(){
        String fileName = getFileDir();
        SerializeTools.deletePath(fileName);
    }

    /**
     * 恢复重置
     */
    public void reset() {
        loginBean = null;
    }

    private String getFileDir() {
        return BabyApplication.getApplication().getFilesDir().toString()+ BabyConstants.LOGIN_MODEL_FILENAME;
    }

}
