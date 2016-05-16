package com.xmmaker.vrmarket.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class NewsListBean implements Serializable{


    /**
     * postid : BKBTULAT000915BF
     * hasCover : false
     * hasHead : 1
     * replyCount : 4827
     * ltitle : 海淘新政没法买买买？朋友圈那些都在胡说
     * hasImg : 1
     * digest : 事情没那么严重，甚至对普通人买买买更友好了。
     * hasIcon : true
     * docid : BKBTULAT000915BF
     * title : 海淘新政没法买买买?朋友圈都在胡说
     * order : 1
     * priority : 200
     * lmodify : 2016-04-11 07:54:30
     * boardid : tech_bbs
     * ads : [{"docid":"BH7H123N00094P0U","title":"VR和物联网的核心还是手机？","tag":"doc","imgsrc":"http://img3.cache.netease.com/3g/2016/3/3/2016030311275428fbb.jpg","subtitle":"","url":"BH7H123N00094P0U"}]
     * url_3w : http://tech.163.com/16/0411/07/BKBTULAT000915BF.html
     * template : manual
     * votecount : 4583
     * alias : Tech
     * cid : C1348649554552
     * url : http://3g.163.com/tech/16/0411/07/BKBTULAT000915BF.html
     * hasAD : 1
     * source : PingWest品玩
     * ename : keji
     * tname : 科技
     * subtitle :
     * imgsrc : http://img2.cache.netease.com/3g/2016/4/11/201604110759203c2c5.jpg
     * ptime : 2016-04-11 07:54:30
     */

    private List<T1348649580692Bean> T1348649580692;

    public List<T1348649580692Bean> getT1348649580692() {
        return T1348649580692;
    }

    public void setT1348649580692(List<T1348649580692Bean> T1348649580692) {
        this.T1348649580692 = T1348649580692;
    }

    public static class T1348649580692Bean implements Serializable{
        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private String ltitle;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String url_3w;
        private String template;
        private int votecount;
        private String alias;
        private String cid;
        private String url;
        private int hasAD;
        private String source;
        private String ename;
        private String tname;
        private String subtitle;
        private String imgsrc;
        private String ptime;
        /**
         * docid : BH7H123N00094P0U
         * title : VR和物联网的核心还是手机？
         * tag : doc
         * imgsrc : http://img3.cache.netease.com/3g/2016/3/3/2016030311275428fbb.jpg
         * subtitle :
         * url : BH7H123N00094P0U
         */

        private List<AdsBean> ads;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getLtitle() {
            return ltitle;
        }

        public void setLtitle(String ltitle) {
            this.ltitle = ltitle;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getUrl_3w() {
            return url_3w;
        }

        public void setUrl_3w(String url_3w) {
            this.url_3w = url_3w;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class AdsBean implements Serializable {
            private String docid;
            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public String getDocid() {
                return docid;
            }

            public void setDocid(String docid) {
                this.docid = docid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
