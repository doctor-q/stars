package cc.doctor.stars.crawler.douyin;

import lombok.Data;

@Data
public class AwemeParams {
    /**
     * device_platform:webapp
     * aid:6383
     * channel:channel_pc_web
     * sec_user_id:MS4wLjABAAAAEtCPQHj7DdEpCo8Oc4RVOhx-HEoluCTFKcBWhkFGf9U
     * max_cursor:0
     * locate_query:false
     * show_live_replay_strategy:1
     * need_time_list:1
     * time_list_query:0
     * whale_cut_token:
     * cut_version:1
     * count:18
     * publish_video_strategy_type:2
     * update_version_code:170400
     * pc_client_type:1
     * version_code:290100
     * version_name:29.1.0
     * cookie_enabled:true
     * screen_width:1920
     * screen_height:1080
     * browser_language:zh-CN
     * browser_platform:Linux+x86_64
     * browser_name:Chrome
     * browser_version:126.0.0.0
     * browser_online:true
     * engine_name:Blink
     * engine_version:126.0.0.0
     * os_name:Linux
     * os_version:x86_64
     * cpu_core_num:8
     * device_memory:8
     * platform:PC
     * downlink:10
     * effective_type:4g
     * round_trip_time:50
     * webid:7384125727966398003
     * msToken:V8dZJZHcfHZr5Ic9-7kWT1i1WUQ_PJdYurEn3JpADO6ErbwbeVY0inYH-dOSXZz1rK_BzvfvT4DsMrCV7uhJ3qgSRMTkpQiV07cIObsUrhtvN38dxd5AyoGbnpntREpC
     * a_bogus:D7WZ%2FmugdE2BhV6D5UdLfY3q66P3Y8VF0trEMD2fexVNj639HMT29exojbUvdWWjNs%2FDIeDjy4hbO3xprQIJ8Zwf7Wsx%2F2CZmL00t-Pg-VSSs1feeLbQrsJx-kJlFeep5JV3EcvhqJKczbuk09xn5vIlO6ZCcHgJEimyb1pngIWw9-Bj
     */
    private String device_platform = "webapp";
    private Integer aid = 6383;
    private String channel = "channel_pc_web";
    // 用户名
    private String sec_user_id = "MS4wLjABAAAAEtCPQHj7DdEpCo8Oc4RVOhx-HEoluCTFKcBWhkFGf9U";
    // 查询游标
    private Long max_cursor = 0L;
    private Boolean locate_query = false;
    private Integer show_live_replay_strategy = 1;
    private Integer need_time_list = 0;
    private Integer time_list_query = 0;
    private String whale_cut_token;
    private Integer cut_version = 1;
    private Integer count = 18;
    private Integer publish_video_strategy_type = 2;
    private Integer update_version_code = 170400;
    private Integer pc_client_type = 1;
    private String version_code = "290100";
    private String version_name = "29.1.0";
    private Boolean cookie_enabled = true;
    private Integer screen_width = 1920;
    private Integer screen_height = 1080;
    private String browser_language = "zh-CN";
    private String browser_platform = "Linux x86_64";
    private String browser_name = "Chrome";
    private String browser_version = "126.0.0.0";
    private Boolean browser_online = true;
    private String engine_name = "Blink";
    private String engine_version = "126.0.0.0";
    private String os_name = "Linux";
    private String os_version = "x86_64";
    private Integer cpu_core_num = 8;
    private Integer device_memory = 8;
    private String platform = "PC";
    private Integer downlink = 10;
    private String effective_type = "4g";
    private Integer round_trip_time = 50;
    private String webid = "7384125727966398003";
    private String msToken = "NvlnUMNrMMjsQQ4lFJnQio5_p2f06tBwoX21f_QhWPKof2CX7u1BT5q4cMt-f4ozSs3D5Y-i8Tnq78H06qQZuOd55rbxm4GcPaxQNYBKQme3A231kHuRC-VxESsneYK0";
    private String a_bogus = //"EfWqQ58gdEVTvf6g5U2LfY3q64-3Y8Vo0trEMD2f0VVN0639HMPk9exojYGve3jjNs/DIeDjy4hbO3xprQ/n8Hwf7Wsx/2CZmL00t-Pg-VSSs1feeLbQrsJx-kJlFeep5JV3EcvhqJKczbuk09cJ5iIlO6ZCcHgJEimyb1pngIWw9-Q-";
            "EfWqQ58gdEVTvf6g5U2LfY3q64-3Y8Vo0trEMD2f0VVN0639HMPk9exojYGve3jjNs%2FDIeDjy4hbO3xprQ%2Fn8Hwf7Wsx%2F2CZmL00t-Pg-VSSs1feeLbQrsJx-kJlFeep5JV3EcvhqJKczbuk09cJ5iIlO6ZCcHgJEimyb1pngIWw9-Q-";
}
