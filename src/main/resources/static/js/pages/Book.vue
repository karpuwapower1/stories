

<template>
  <div>
    <h1></h1>
  </div>
</template>

<script>
export default {

	data() {
	return {
		chapters: []
		}
	},
mounted() {
this.$router.get().then(response => response.json().then(
                     this.chapters = response))
},
}
</script>

<style></style>
