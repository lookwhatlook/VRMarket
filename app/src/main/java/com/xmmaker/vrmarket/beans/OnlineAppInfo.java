package com.xmmaker.vrmarket.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/18.
 */
public class OnlineAppInfo implements Serializable {


    /**
     * id : 23
     * name : 3D过山车VR
     * version : 3.0
     * size : 31.44
     * iconlink : 测试
     * link : 测试
     * info : 3D过山车VR》是一款虚拟现实类的过山车游戏，同其它vr游戏一样，需要带上虚拟现实眼镜才能体会到身临其境的感觉。
     */

    private String id;
    private String name;
    private String version;
    private String size;
    private String iconlink;
    private String link;
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIconlink() {
        return iconlink;
    }

    public void setIconlink(String iconlink) {
        this.iconlink = iconlink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
