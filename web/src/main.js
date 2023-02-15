import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import {getItems,getItemLabel} from './api/dict.js'

Vue.config.productionTip = false

Vue.prototype.suc=function(msg){
	this.$message({
	         message:msg,
	         type: 'success'
	       });
}

Vue.prototype.error=function(msg){
	this.$message.error(msg);
}

Vue.prototype.getDictItems=getItems
Vue.prototype.getItemLabel=getItemLabel

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
