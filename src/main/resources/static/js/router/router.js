import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from 'pages/Main.vue'
import Book from 'pages/Book.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Main },
    { path: '/user/:id', component: Main },
    { path: '/book/:id', component: Book }
    // { path: '/book/:id', component: Book }
  ]
  
  export default new VueRouter({
    mode: 'history',
    routes 
  })