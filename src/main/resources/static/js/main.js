var userApi = Vue.resource('/user{/id}');

Vue.component('som', {
	props : [ 'books', 'user' ],
	data : function() {
		return {
			book : null
		}
	},
	template : '<div>' 
		+ '<h2>{{ user.id }}</h2>'
					+ '<div v-for="book in user.books">'
					+ '<h2>{{ book.name }}</h2>'
					+ '<p> {{ book.id }}</p>' 
					+ '</div>' 
				+ '</div>',

});

var app = new Vue({
	el : '#app',
	template : '<som :user="user"/>',
	data : {
		user : userParameters.user
	},
});