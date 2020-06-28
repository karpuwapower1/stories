<template>
<div> 
<navbar-component :user="user" />
<book-component :user="user"/>
</div>					
</template>

<script>
import navbarComponent from 'components/navbarComponent.vue'
import BookComponent from 'components/mainPage/BookComponent.vue'
	export default {
		components: {
            navbarComponent,
			BookComponent
        },
	data() {
	return {
		user : userParameters.user
		}
	},
}
</script>

<style>

</style>