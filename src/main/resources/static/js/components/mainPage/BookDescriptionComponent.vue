<template>
<div>
 <b-card-text :style=styles > Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempor arcu a elit tincidunt pulvinar. Proin non diam maximus, fermentum arcu eu, porta dolor. Suspendisse potenti. Suspendisse potenti. Mauris ac faucibus turpis. Nullam feugiat risus nunc, id convallis metus scelerisque in. Etiam sapien velit, scelerisque nec tortor non, suscipit viverra nibh. Quisque quis turpis ac ante tristique hendrerit. Suspendisse commodo tincidunt arcu ut aliquam. Donec consectetur arcu ultrices tincidunt commodo. Morbi iaculis velit laoreet viverra laoreet. Ut commodo justo eu justo viverra, in pharetra metus finibus. Phasellus vel lobortis est, eget commodo magna. Nunc vel orci sed dolor rhoncus aliquam.
Curabitur rutrum, neque in cursus efficitur, magna purus viverra ipsum, commodo tempus augue velit vitae turpis. Praesent eget metus quam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In leo dolor, vulputate sit amet ornare quis, luctus vitae lectus. Duis aliquet iaculis elementum. Aenean quis pretium lectus, at malesuada mi. Morbi diam lorem, eleifend vel cursus quis, rutrum vel nulla.
Mauris at justo mollis, sodales metus sed, cursus justo. Etiam id pretium ante, a eleifend augue. Aliquam eleifend tellus fermentum, bibendum enim eu, ullamcorper magna. Quisque tempus justo libero, in suscipit quam facilisis sed. Suspendisse mauris dolor, auctor ac mauris id, sollicitudin eleifend massa. Suspendisse volutpat mi sit amet ipsum tristique consequat. Praesent porta eu lectus id varius. Curabitur pulvinar ligula eget placerat egestas.
Phasellus gravida mi sapien, eu pulvinar mauris consequat a. Sed commodo diam nisl, non rutrum justo finibus a. Vestibulum accumsan interdum diam at iaculis. In non luctus magna. Vivamus ut turpis id odio scelerisque ultricies. Aenean eu nisi sed purus malesuada viverra. Integer eget sem vel sapien rhoncus lacinia. Morbi in sollicitudin dolor. Nam molestie lacus id condimentum ullamcorper. In hac habitasse platea dictumst. Aliquam porttitor interdum neque, vitae congue tortor ultrices vel. Curabitur laoreet elit eleifend lacus cursus tincidunt. Aenean ultrices porttitor neque a consectetur. Fusce vel egestas nisl. Etiam venenatis sollicitudin eros, sit amet fermentum arcu tincidunt a.
          </b-card-text>
<b-card-text align="right">  
        <b-button @click="changeStyle()" variant="link">Show more</b-button>
        </b-card-text>
          </div>
</template>

<script>
export default {
    data() {
        return {
            styles: {
                overflow: hidden,
                height: 100px;
            }
        }
    },
    methods: {
       changeStyle : function() {
    console.log(isActive)
    isActive = isActive == '100px' ? '' : '100px';
    return isActive
}
},
}
</script>

<style>

</style>