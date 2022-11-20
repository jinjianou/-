<template>
  <div>
    <el-container class="container">
      <el-header class="header-container">
        <search-nav></search-nav>
      </el-header>
      <el-container>
        <el-aside>
          <keep-alive>
            <side-nav></side-nav>
          </keep-alive>
        </el-aside>
        <el-main class="main-container">
          <display-area></display-area>
        </el-main>
      </el-container>
      <el-footer class="footer-container">
        <footer-nav></footer-nav>
      </el-footer>
    </el-container>
    <!-- <router-view v-if="$route.meta.keepAlive"></router-view> -->
  </div>
</template>

<script>
import Header from '@/components/common/Header.vue'
import Nav from '@/components/common/Nav.vue'
import Footer from '@/components/common/Footer.vue'
import Main from '@/components/Main.vue'
export default {
  name: 'Index',
  data () {
    return {


    }
  },
  created () {
  },
  components: {
    searchNav: Header,
    sideNav: Nav,
    footerNav: Footer,
    displayArea: Main
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.container {
  padding-left: 20px;
  height: 750px;
}
.header-container {
  margin-bottom: 30px;
}
.el-aside {
  margin-bottom: 30px;
  min-height: 80%;
  border-right: #f4edf1 solid 1px !important;
  width: 180px !important;
}
.main-container {
  min-height: 80%;
  /* padding-bottom: 20px; */
}
.footer-container {
  background-color: rgb(204, 194, 194);
  font-size: 25px;
  position: fixed;
  bottom: 30px;
  width: 100%;
}
</style>
