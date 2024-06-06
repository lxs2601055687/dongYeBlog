<template>
  <div class="height100">
    <a-row :gutter="16" class="height100">
      <a-col :span="6">

      </a-col>
      <a-col :span="18" class="height100">
        <div class="help-doc-box height100">

        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script setup>

</script>
<style scoped lang="less">
  .height100 {
    height: 100%;
  }
  .help-doc-box {
    display: flex;
    flex-direction: column;
    .employee {
      flex-grow: 2;
      margin-top: 10px;
    }
  }
</style>
