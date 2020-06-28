import Vue from 'vue'
import VueResource from 'vue-resource'
import Main from 'pages/Main.vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

Vue.config.productionTip = false

new Vue({
	el: '#app',
	render: a => a(Main)
})


//var userApi = Vue.resource('/user{/id}');

//Vue.component('add-book', {
//	props : ['books', 'book'], 
//	data : function() {
//		return {
//			name : '',
//			id : ''
//		}
//	},
//	template : '<div>' 
//		 '<div>' +
//         '<input type="text" placeholder="Book name" v-model="text" />' +
//         '<input type="button" value="Add" @click="save" />' +
//     '</div>',
//     
//     me
//});

//Vue.component('som', {
//	props : [ 'user'],
//	data : function() {
//		return {
//			book : null
//		}
//	},
//	template : '<div>' 
//		+ '<h2>{{ user.id }}</h2>'
//					+ '<div v-for="book in user.books">'
//					+ '<h2>{{ book.name }}</h2>'
//					+ '<p> {{ book.id }}</p>' 
//					+ '</div>' 
//					+ '<add-book :books="user.books" :book="book">'
//				+ '</div>',
//				
//
//});
//
//var app = new Vue({
//	
//	template : '<som :user="user"/>',
//	data : {
//		user : userParameters.user
//	},
//});