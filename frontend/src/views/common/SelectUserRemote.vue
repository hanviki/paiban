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
            :key="d2"
            :value="`${d2.userAccount}`"
          >
            {{ d2.userAccountName + " " + d2.userAccount + " " + d2.birthday }}
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
        console.log(r)
        this.userData = r.data;
      });
    },
    userChange(value,option) {
        this.defaultValue= value;
        this.$emit("userChang",value,option)
    },
    setFiledValue(value){
       this.defaultValue= value;
       this.fetchUser(value)
    }
  }

}
</script>

<style>

</style>