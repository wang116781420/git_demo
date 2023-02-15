"use strict";

import Vue from 'vue';
import axios from "axios";

let config = {
	baseURL: "http://localhost:8080",
	timeout: 60 * 1000,
	withCredentials: true,
};

const _axios = axios.create(config);

//请求前拦截
_axios.interceptors.request.use(
	function(config) {
		let t = localStorage.getItem("admin_token");
		if(t!=null && t!='' && t!=undefined)
		config.headers["admin_token"]=t;
		
		return config;
	},
	function(error) {
		return Promise.reject(error);
	}
);

//请求后拦截
_axios.interceptors.response.use(
	function(response) {
		let headers = response.headers;
		if(Object.keys(headers).indexOf("admin_token")>=0){
			let t = headers["admin_token"];
			localStorage.setItem("admin_token",t);
		}
		let data = response.data;//{"code":,"msg":""}
		let keys = Object.keys(data);
		if(keys.indexOf("code")>=0){
			let code= data['code'];
			if(code==50003){
				location.href="/login";
			}
		}
		return data;
	},
	function(error) {
		return Promise.reject(error);
	}
);

Plugin.install = function(Vue, options) {
	Vue.axios = _axios;
	window.axios = _axios;
	Object.defineProperties(Vue.prototype, {
		axios: {
			get() {
				return _axios;
			}
		},
		$axios: {
			get() {
				return _axios;
			}
		},
	});
};

Vue.use(Plugin)

export default Plugin;
