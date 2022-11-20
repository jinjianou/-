<!--  -->
<template>
  <div class="header">
    <div class="header-left">
      <router-link :to="{name:'TestRouteMeta'}"></router-link>
      <router-link :to="{name:'Index'}"></router-link>
    </div>
    <div class="header-right">
      <el-input v-model="searchCourse"
                placeholder="从教程的词条中查询">
        <el-button slot="append"
                   icon="el-icon-search"></el-button>
      </el-input>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchCourse: '',
    }
  }
}
</script>

<style  scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.header-left {
  float: left;
  display: flex;
  align-items: center;
}
.header-left > a:nth-child(1) {
  background-image: url(http://www.imooc.com/wiki/_nuxt/img/423338e.png);
  background-repeat: no-repeat;
  background-size: cover;
  width: 160px;
  height: 54px;
  display: inline-block;
}
.header-left > a:nth-child(2) {
  background-image: url(http://www.imooc.com/wiki/_nuxt/img/8d624c9.png);
  background-repeat: no-repeat;
  background-size: cover;
  width: 110px;
  height: 19px;
  display: inline-block;
  margin-top: 10px;
}
.header-right {
  margin-right: 50px;
}
</style>
