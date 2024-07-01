package cc.doctor.stars.crawler.douyin;

import cc.doctor.stars.crawler.douyin.aweme.response.AwemeResponse;
import cc.doctor.stars.crawler.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.HashMap;
import java.util.Map;

public class AwemeRequests {
    public static AwemeResponse getAwemeList(String secUid) {
        String msToken = "NvlnUMNrMMjsQQ4lFJnQio5_p2f06tBwoX21f_QhWPKof2CX7u1BT5q4cMt-f4ozSs3D5Y-i8Tnq78H06qQZuOd55rbxm4GcPaxQNYBKQme3A231kHuRC-VxESsneYK0";
        String aBogus = "EfWqQ58gdEVTvf6g5U2LfY3q64-3Y8Vo0trEMD2f0VVN0639HMPk9exojYGve3jjNs%2FDIeDjy4hbO3xprQ%2Fn8Hwf7Wsx%2F2CZmL00t-Pg-VSSs1feeLbQrsJx-kJlFeep5JV3EcvhqJKczbuk09cJ5iIlO6ZCcHgJEimyb1pngIWw9-Q-";
        // sec_user_id: MS4wLjABAAAAEtCPQHj7DdEpCo8Oc4RVOhx-HEoluCTFKcBWhkFGf9U
        String url = "https://www.douyin.com/aweme/v1/web/aweme/post?device_platform=webapp&aid=6383&channel=channel_pc_web&sec_user_id=%s&max_cursor=0&locate_query=false&show_live_replay_strategy=1&need_time_list=1&time_list_query=0&whale_cut_token=&cut_version=1&count=18&publish_video_strategy_type=2&update_version_code=170400&pc_client_type=1&version_code=290100&version_name=29.1.0&cookie_enabled=true&screen_width=1920&screen_height=1080&browser_language=zh-CN&browser_platform=Linux+x86_64&browser_name=Chrome&browser_version=126.0.0.0&browser_online=true&engine_name=Blink&engine_version=126.0.0.0&os_name=Linux&os_version=x86_64&cpu_core_num=8&device_memory=8&platform=PC&downlink=10&effective_type=4g&round_trip_time=50&webid=7384125727966398003&msToken=%s&a_bogus=%s";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "ttwid=1%7C2ui9niFiROfAFy2I8GRzE4PfOIIEjm-VUd_fWn8WoYk%7C1719250769%7C09a83ffa176982045bc394168763b54754ab72321e0020bfd198f414d7ba2be2; UIFID_TEMP=9696d8ba36d71ab46fc16af3f06315c78de6f6c90b689997eea7b95fa226a60b804faea0921ed2d7b91d90c44a28fe6ea201d61bf617af71ae40c1365a14add1b1c35ca2d7d0272c7dad188220da1973; dy_swidth=1920; dy_sheight=1080; fpk1=U2FsdGVkX1/Ed5s4XFd2ScdfDPH74vSkN7tDSa8otPb+yXFIyWrbIgMCFckfC6i3JPtlK4SaKvIHAIRR5tVxzQ==; fpk2=855abe33f610755c8e35f3ba0e85948b; s_v_web_id=verify_lxt9ipom_ffxlDNTD_sQk5_45ac_9dBj_yMVi78d9PeF9; passport_csrf_token=7def3c5188af2c2b122fd28e13fd118f; passport_csrf_token_default=7def3c5188af2c2b122fd28e13fd118f; bd_ticket_guard_client_web_domain=2; FORCE_LOGIN=%7B%22videoConsumedRemainSeconds%22%3A180%7D; UIFID=9696d8ba36d71ab46fc16af3f06315c78de6f6c90b689997eea7b95fa226a60b789ec661f4f04dd1d0972c6724a232ff791749943f37dc0f5749ea55f373ba1be5f2611fd657d9e7f10beea2f5aaa1d03c8b7d036a9244e7195abe3761539e05b4aa61268f4fccbdd24c6b00920df8d449d943a875793eebe4b185ba4e7e31b6a62e00cdcfe8d94a92f669cbcbc1504290ad5b1424e1fc62979810fb94808700; odin_tt=eeeea47f515e34c8f4026e747f0c7c9b62ccff245caca6e9add8647fde42d2503dbb112de403649ad6f4fd1800823aaa0989a3d9026060dca4d21efcbb532c32ce3f366796d165bba2433aab6f792636; xgplayer_user_id=875929363599; SEARCH_RESULT_LIST_TYPE=%22single%22; pwa2=%220%7C0%7C3%7C0%22; douyin.com; device_web_cpu_core=8; device_web_memory_size=8; architecture=amd64; csrf_session_id=f194db9a2d72db4473f4aaeb305c88b9; my_rd=2; volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.996%7D; strategyABtestKey=%221719424489.458%22; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1920%2C%5C%22screen_height%5C%22%3A1080%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A8%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A10%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22; xg_device_score=7.338377468011834; stream_player_status_params=%22%7B%5C%22is_auto_play%5C%22%3A0%2C%5C%22is_full_screen%5C%22%3A0%2C%5C%22is_full_webscreen%5C%22%3A0%2C%5C%22is_mute%5C%22%3A1%2C%5C%22is_speed%5C%22%3A1%2C%5C%22is_visible%5C%22%3A0%7D%22; __ac_nonce=0667c5f8a0036f2a39ced; __ac_signature=_02B4Z6wo00f01VDdS5AAAIDBfwOISKP76olQ.U8AADJOSW1FFZ0T0mxBTdqPDNPc6Dln4YvzRyEBsd3wv2HQw.NYaLTfNh8UBVrv9MxGClggyMy.vBvz1RjfDXdc4By.Glz210ujtTg95Zt-e0; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCRy9kWENIYW9OOHl6eXM1eEZuZ05rbDBWNkN0Yk9SS0xLMW1JQ2JSQ3JQbzU4eVJzbHJ2TDNLYVBlSCtkUi9XczVwT0NlZ3N6T1lESzZyeWpTek1nalU9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoxfQ%3D%3D; msToken=4UPJsG-mJqIoD-gxaZeEK-BioM7C-oQJ_g_N02txHVsYNz6ymt7mgwEz5eZAfUb-P8sCR0Sn3J04GziC5bxXKNvR8cRDU_WCGiaLpMwkBGvWMHLzlOTODbohQnUKynsB; IsDouyinActive=true; home_can_add_dy_2_desktop=%220%22");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
        headers.put("Referer", String.format("https://www.douyin.com/user/%s", secUid));
        CloseableHttpResponse response = HttpUtils.get(String.format(url, secUid, msToken, aBogus), null, headers);
        String res = HttpUtils.parseHttpResponse(response);
        return JSON.parseObject(res, AwemeResponse.class);
    }
}
