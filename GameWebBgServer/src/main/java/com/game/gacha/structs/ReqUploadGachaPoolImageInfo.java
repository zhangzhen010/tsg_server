package com.game.gacha.structs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUploadGachaPoolImageInfo {

    /**
     * 上传卡池图片资源类型
     */
    private String type;
    /**
     * 图片Url
     */
    private String imageUrl;
    /**
     * 视频Url
     */
    private String videoUrl;

}
