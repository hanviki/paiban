<template>
   <a-select
      :value="defaultValue"
      @search="handleSearch"
      :filter-option="false"
      show-search
      @change="userChange"
    >
      <a-select-option
        v-for="d2 in userData"
        :key="d2.userAccount"
      >
        <!-- {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday }} -->
         {{ d2.userAccountName + " " + d2.userAccount + " " + d2.yggh}}
      </a-select-option>
    </a-select>
</template>

<script>
export default {
   data() {
    return {
      loading: false,
      userData: [],
      defaultValue: ''
    };
  },
  props: {
  },
  methods: {
    reset(){
      this.defaultValue= ''
    },
    handleSearch(value) {
      this.fetchUser(value);
    },
    fetchUser(userAccount) {
      this.$get("sdlBUserMg/search", {
        userAccount,
        state: 0
      }).then((r) => {
        this.userData = r.data;
      });
    },
    userChange(value) {
      this.defaultValue= value;
      let target = this.userData.filter(item => value === item.userAccount)[0]
      this.$emit("userChang",value,target)
    },
    setFiledValue(value){
       this.defaultValue= value;
    }
  }

}
</script>

<style>

</style>