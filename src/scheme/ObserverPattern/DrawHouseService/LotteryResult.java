package scheme.ObserverPattern.DrawHouseService;
import java.util.*;
public class LotteryResult {
    private String uId; // 用户id
    private String msg; // 摇号信息
    private Date dataTime; // 业务时间
    public LotteryResult(String uID, String msg, Date datetime){
        this.uId = uID;
        this.msg = msg;
        this.dataTime = datetime;
    }

    @Override
    public String toString(){
        return uId + " " + msg + " " + dataTime;
    }

    public String getMsg(){
        return this.msg;
    }
    public String getuId(){

        return this.uId;
    }
}
