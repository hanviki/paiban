<template>
  <a-cascader
    :fieldNames="{ label: 'name', value: 'name', children: 'children' }"
    :options="areaData"
    :placeholder="placeholder"
    v-model="selectedValues"
    @change="onChange"
  />
</template>

<script>
import areaData from "../../utils/areaData";
export default {
  name: "areaSelect",
  props: {
    placeholder: {
      type: String,
      default: "请选择省市区",
    },
    defaultValue: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    return {
      //地区数据
      areaData,
      //选择的数据
      selectedValues: [],
    };
  },
  created() {
    if (this.defaultValue.length) {
      this.selectedValues = [...this.defaultValue];
    }
  },
  watch: {
    defaultValue(newValue) {
      if (newValue.length) {
        this.selectedValues = newValue;
      } else {
        this.selectedValues = [];
      }
    },
  },
  methods: {
    //选择好之后的回调
    onChange(value) {
      this.$emit("change", value);
    },
  },
};
</script>
