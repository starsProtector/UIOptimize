package com.sn.synthesize.bean;

import java.util.List;

/**
 * date:2017/5/13
 * author:易宸锋(dell)
 * function:
 */

public class Newsnew {

    private NewslistBean newslist;

    public NewslistBean getNewslist() {
        return newslist;
    }

    public void setNewslist(NewslistBean newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * title : 易宸锋是个大帅哥
         * detail : 奥巴马和卡拉希希望一睹其风采
         * comment : 15687
         * image : http://192.168.253.1:8080/images/6.jpg
         */

        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            private String title;
            private String detail;
            private String comment;
            private String image;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
