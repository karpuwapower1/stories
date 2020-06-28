<template>
  <div>
    <div v-for="userBook in user.books">
      <b-card no-body class="overflow-hidden">
        <b-row no-gutters>
          <b-col md="4">
            <b-card-img src="" alt="Image" class="rounded-0"></b-card-img>
          </b-col>
          <b-col>
            <b-card-body :title="userBook.name">
              <b-card-text> {{ userBook.description }} </b-card-text>
              <b-card-text align="right">
                <b-button variant="link" >Show more</b-button>
              </b-card-text>
              <div>
              <b-button variant="link" @click="jump(userBook)">Read</b-button>
              <router-view></router-view>
              </div>
            </b-card-body>
          </b-col>
        </b-row>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  props: ["user"],
  data() {
    return {
      book: null,
      visible: false,
    };
  },
  methods: {
    jump(book) {
       console.info('App currentRoute:', this.$router.currentRoute)
      this.$router.push({path: `/book/${book.id}`})
    }
  }
}
</script>

<style>
a {
  color: black;
}
a:hover {
  color: #120cba;
}
a:visited {
  color: #cecece;
}
a:active {
  color: #ffff00;
}
</style>