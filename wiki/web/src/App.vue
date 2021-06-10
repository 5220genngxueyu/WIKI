<template>
  <a-layout>
    <the-header></the-header>
<!--    这里表示路由变化的内容，表示各个页面的内容HOME啊，About啊。其头部和尾部都是不变的内容-->
    <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'app',
  components: {
    TheHeader,
    TheFooter,
  },
});
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>