package cn.huanzi.qch.baseadmin;

import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.util.AesUtil;
import cn.huanzi.qch.baseadmin.util.RsaUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

public class AesTest {
    public static void main(String[] args) throws Exception {
        //jackson
        ObjectMapper mapper = new ObjectMapper();
        //每次响应之前随机获取AES的key，加密data数据
        String key = AesUtil.getKey();
        String dataString = mapper.writeValueAsString("123");
        System.out.println("需要加密的data数据：" + dataString);
        String publicKey = RsaUtil.getPublicKey();
        String data = AesUtil.encrypt(dataString, key);

        //用前端的公钥来解密AES的key，并转成Base64
        String aesKey = Base64.encodeBase64String(RsaUtil.encryptByPublicKey(key.getBytes(), publicKey));

        //转json字符串并转成Object对象，设置到Result中并赋值给返回值o
        Object o = Result.of(mapper.readValue("{\"data\":\"" + data + "\",\"aesKey\":\"" + aesKey + "\"}", Object.class));
        System.out.println(o);
    }
}
