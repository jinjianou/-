<!--  -->
<template>
  <div class="tac">
    <!-- open	index: 打开的 sub-menu 的 index， indexPath: 打开的 sub-menu 的 index path(数组，含之前的index)
    close index: 收起的 sub-menu 的 index， indexPath: 收起的 sub-menu 的 index path
    select index: 选中菜单项的 index, indexPath: 选中菜单项的 index path -->
    <el-menu :default-active="activeIndex"
             class="el-menu-vertical-demo"
             @select="handleSelect">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">导航二</span>
      </el-menu-item>
      <el-menu-item index="3"
                    disabled>
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeIndex: '2'
    }
  },
  methods: {
    handleSelect (index, indexPath) {
      this.activeIndex = index;
    }
  }
}
</script>

<style  scoped>
</style>
