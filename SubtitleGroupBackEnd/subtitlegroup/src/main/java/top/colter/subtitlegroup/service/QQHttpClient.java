package top.colter.subtitlegroup.service;

import cn.hutool.http.HttpUtil;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QQHttpClient {

    private final String verifyKey = "INITKEYxgVyZKxg";
    private final String host = "http://127.0.0.1:8082";

    private String session = "";
    private final Log log = LogFactory.get();

    private final OrganizationService organizationService;

    public QQHttpClient(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void getSession(){
        JSONObject data = JSONUtil.createObj();
        data.set("verifyKey", verifyKey);
        log.info(data.toString());

        String verifyRes = HttpUtil.post(host+"/verify", data.toString());

        log.info(verifyRes);

        JSONObject verifyResJson = JSONUtil.parseObj(verifyRes);

        if (verifyResJson.getInt("code") == 0){
            session = verifyResJson.getStr("session");

            log.info("验证成功");
            //String body = "{\"sessionKey\": \""+session+"\",\"qq\": 1537133819}";
            //String bindRes = HttpUtil.post(host+"/bind", body);
            //
            //JSONObject bindResJson = JSONUtil.parseObj(bindRes);
            //if (bindResJson.getInt("code") == 0){
            //
            //    log.info("绑定成功");
            //}else{
            //    log.error("绑定失败=>" + bindRes);
            //}
        }else{
            log.error("验证失败=>" + verifyRes);
        }
    }

    public Boolean sendMessage(String msg, Long qq){
        if ("".equals(session)){
            getSession();
        }
        log.info("MSG: {}", msg);
        log.info("QQ: {}", qq);
        Long group = 0L;
        List<Long> groups = organizationService.findAllGroupNumber();
        for (Long v : groups){
            //if (!group.equals(0L)){break;}
            log.info("Test: {}", v);
            String memberList = HttpUtil.get(host+"/memberList?target="+v);
            log.info("List: {}", memberList);
            if (memberList.contains("\"id\":"+qq)){
                log.info("Yes: {}", v);
                group = v;
                break;
            }
            //JSONObject membersResJson = JSONUtil.parseObj(memberList);
            //if (membersResJson.getInt("code") == 0){
            //    JSONArray members = membersResJson.getJSONArray("data");
            //    for (Object d : members){
            //        Long id = ((JSONObject)d).getLong("id");
            //        if (id.equals(qq)){
            //            group = v;
            //            break;
            //        }
            //    }
            //}else{
            //    break;
            //}

        }

        if (group.equals(0L)){
            return false;
        }

        JSONObject body = new JSONObject();
        //body.set("sessionKey", session);
        body.set("qq", qq);
        body.set("group", group);

        JSONArray messageChain = JSONUtil.createArray();

        JSONObject ms = JSONUtil.createObj();
        ms.set("type", "Plain");
        ms.set("text", msg);
        messageChain.add(ms);
        body.set("messageChain", messageChain);

        log.info(body.toString());

        String res = HttpUtil.post(host+"/sendTempMessage", body.toString());
        JSONObject resJson = JSONUtil.parseObj(res);
        if (resJson.getInt("code") == 0){
            return true;
        }else{
            log.error("发送失败=>" + res);
            return false;
        }
    }


}
