<!--  -->
<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8"
              v-for="ebook in ebookList"
              :key="ebook.id">
        <el-card class="card">
          <div class="card-top">
            <el-image style="width: 70px; height: 70px"
                      :src="ebook.cover"
                      fit="cover"
                      @load="loadsuc"
                      @error="loaderror"
                      class="img">
              <div slot="error"
                   class="image-slot">
                <div class="image-when-empty"></div>
              </div>
            </el-image>

            <div class="card-top-right">
              <div class="title">{{ebook.title}}</div>
              <div class="description">{{ebook.description}}</div>
            </div>

          </div>
          <div class="card-bottom">
            <i class="el-icon-position"></i>
            <span>{{ebook.contentCount}}</span>
            <el-divider direction="vertical"></el-divider>
            <i class="el-icon-view"></i>
            <span>{{ebook.viewCount}}</span>
            <el-divider direction="vertical"></el-divider>
            <i class="el-icon-star-on"></i>
            <span>{{ebook.voteCount}}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
	data () {
		return {
			ebookList:[]
		}
	},
	methods:{
		loadsuc(){
			console.log("loadsuc");
		},
			loaderror(){
			console.log("loaderror");
		},
	},
	created(){
		this.$http.get('ebook/list')
							.then(res=>this.ebookList=res)
							.catch(err=>err);
	}
}
</script>


<style>
</style>

<style  scoped>
.el-card {
  margin-bottom: 30px;
  height: 200px;
  display: flex;
  flex-direction: column;
}

>>> .el-card__body {
  width: 100%;
  height: 100%;
  padding: 15px;
}

.card-top {
  height: 75%;
  display: flex;
  /* justify-content: flex-start; */
  /* align-items: center; */
}
.card-top-right {
  width: 70%;
}
.card-bottom {
  height: 25%;
  /* margin-bottom: 10px; */
  font-size: 18px;
  padding-top: 20px;
  padding-right: 10px;
}
.img {
  /* float: left; */

  /* margin-top: 20px; */
  border-radius: 5px;
}
.image-when-empty {
  width: 70px;
  height: 70px;
  background-color: rgb(153, 151, 151);
}
.title {
  width: 2;
  font-size: 25px;
  font-weight: bold;
  white-space: nowrap;
  text-overflow: ellipsis;
  /* line-height: 2; */
  text-align: center;
  margin-bottom: 15px;
}
.description {
  font-size: 15px;
  margin-top: 5px;
}
</style>
