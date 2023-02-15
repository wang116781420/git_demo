package com.javasm.commons.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private static Cors cors;
    private static String root;

    private static LoginToken loginToken;

    private static PageInfo page;

    public static class PageInfo{
        private Integer pageNum;
        private Integer pageSize;

        public Integer getPageNum() {
            return pageNum;
        }

        public void setPageNum(Integer pageNum) {
            this.pageNum = pageNum;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class LoginToken {
        private String key;
        private Integer expMinutes;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getExpMinutes() {
            return expMinutes;
        }

        public void setExpMinutes(Integer expMinutes) {
            this.expMinutes = expMinutes;
        }

    }

    public static LoginToken getLoginToken() {
        return loginToken;
    }

    public  void setLoginToken(LoginToken loginToken) {
        AppProperties.loginToken = loginToken;
    }

    public static class Cors{
        private String path="/**";
        private List<String> methods=new ArrayList<>();
        private List<String> headers=new ArrayList<>();
        private List<String> origins=new ArrayList<>();
        private Boolean credentials=true;
        private List<String> exposedHeaders=new ArrayList<>();
        private Long maxAge=1800L;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public List<String> getOrigins() {
            return origins;
        }

        public void setOrigins(List<String> origins) {
            this.origins = origins;
        }

        public Boolean getCredentials() {
            return credentials;
        }

        public void setCredentials(Boolean credentials) {
            this.credentials = credentials;
        }

        public List<String> getExposedHeaders() {
            return exposedHeaders;
        }

        public void setExposedHeaders(List<String> exposedHeaders) {
            this.exposedHeaders = exposedHeaders;
        }

        public Long getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Long maxAge) {
            this.maxAge = maxAge;
        }
    }
        public static String getRoot() {
          return root;
    }
        public  void setRoot(String root) {
            AppProperties.root = root;
        }

        public static Cors getCors() {
            return cors;
        }

        public  void setCors(Cors cors) {
            AppProperties.cors = cors;
        }

    public static PageInfo getPage() {
        return page;
    }

    public  void setPage(PageInfo page) {
        AppProperties.page = page;
    }
}
