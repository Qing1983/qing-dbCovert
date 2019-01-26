package tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import global.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultTool {

    public static void success (Object data) {
        Result result = new Result();
        result.setFlag(true);
        result.setData(data);
        List<SerializerFeature> serializerFeatureList = new ArrayList<>();
        System.out.print(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames));

    }

    public static void error (String msg) {
        Result result = new Result();
        result.setFlag(false);
        result.setMsg(msg);
        System.out.print(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames));
    }
}
