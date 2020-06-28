<template>
<div>
<div v-for="userBook in user.books">
<a :href="/book/'userBook.id'">
  <b-card no-body class="overflow-hidden" >
    <b-row no-gutters>
      <b-col md="4">
        <b-card-img src="" alt="Image" class="rounded-0"></b-card-img>
      </b-col>
      <b-col>
        <b-card-body :title="userBook.name">
         <b-card-text> {{ userBook.description }} </b-card-text>
        <b-card-text align="right">  
        <b-button variant="link">Show more</b-button>
        </b-card-text>
        </b-card-body>
      </b-col>
    </b-row>
  </b-card>
  </a>
</div>
</div>
</template>

<script>
export default {
    props: ['user'],
    data() {
        return {
            book: null,
            visible: false
        } 
    },
}
</script>

<style>

a {
	color: black;
}

a:hover {
	color: #120CBA;
}

a:visited {
  color: #cecece; /* Цвет посещенных ссылок */
}
 a:active {
  color: #ffff00; /* Цвет активных ссылок */
}

</style>