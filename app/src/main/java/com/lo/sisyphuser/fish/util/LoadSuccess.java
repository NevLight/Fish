package com.lo.sisyphuser.fish.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xx on 2016/9/14.
 */
public class LoadSuccess {
    public static void onSuccess(String sJson,LoadSuccessCallBack callBack){
        L.i("netInfo",sJson);
        try {
            JSONObject jsonObject=new JSONObject(sJson);
            String code=jsonObject.getString("resultcode");
            if("200".equals(code)) {
                callBack.code200(jsonObject);
            }else{
                String error=jsonObject.getString("reason");
                callBack.codeOther(error);
            }
        } catch (JSONException e) {
            L.e("JSON",e.getMessage());
            callBack.codeOther("数据异常");
        }
    }

    public interface LoadSuccessCallBack{
        public void code200(JSONObject json)throws JSONException;
        public void codeOther(String errorMessage);
    }
}
