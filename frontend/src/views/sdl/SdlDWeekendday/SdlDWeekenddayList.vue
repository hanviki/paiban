<template>
  <a-calendar mode="month" @select="dateSelect">
    <ul slot="dateCellRender" slot-scope="value" class="events">
      <li>
        <a-badge style="text-align:center; font-size:20px;font-weight:bold;" status="success" text="双休日" v-if="getListData(value)" />
      </li>
    </ul>
  </a-calendar>
</template>
<script>
export default {
  data() {
    return {
      dataSource: [],
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    getListData(value) {
      let cureenDate = value.format("YYYY-MM-DD");
      if (this.dataSource.indexOf(cureenDate) >= 0) {
        return true;
      } else {
        return false;
      }
    },
    dateSelect(value) {
      let cureenDate = value.format("YYYY-MM-DD");
      let index = this.dataSource.indexOf(cureenDate);
      var params= {
        id: cureenDate
      }
      if (index >= 0) {
        params.flag = 0
         this.$post("sdlDWeekendday", {
            ...params
          }).then(()=>{
          this.dataSource.splice(index, 1);
        })
       
      } else {
        params.flag = 1
          this.$post("sdlDWeekendday", {
            ...params
          }).then(()=>{
          this.dataSource.push(cureenDate);
        })
      }
    },
    fetch() {
      this.$get("sdlDWeekendday/all", {}).then((r) => {
        this.dataSource = r.data;
      });
    },
    
         
    
  },
};
</script>
<style scoped>
.events {
  list-style: none;
  margin: 0;
  padding: 0;
}
.events .ant-badge-status {
  overflow: hidden;
  white-space: nowrap;
  width: 100%;
  text-overflow: ellipsis;
  font-size: 12px;
}
.notes-month {
  text-align: center;
  font-size: 28px;
}
.notes-month section {
  font-size: 28px;
}
</style>