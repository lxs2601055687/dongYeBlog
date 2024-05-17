<!--
  * 奖品表
  *
  * @Author:    李祥生
  * @Date:      2024-05-16 16:35:02
  * @Copyright  niit-阿升
-->
<template>
    <!---------- 查询表单form begin ----------->
    <a-form class="smart-query-form">
        <a-row class="smart-query-form-row">
            <a-form-item label="奖品名称" class="smart-query-form-item">
                <a-input style="width: 150px" v-model:value="queryForm.title" placeholder="奖品名称" />
            </a-form-item>
            <a-form-item label="奖品有效周期：开始时间" class="smart-query-form-item">
                <a-range-picker v-model:value="queryForm.beginTime" :presets="defaultTimeRanges" style="width: 150px" @change="onChangeBeginTime" />
            </a-form-item>
            <a-form-item label="奖品有效周期：结束时间" class="smart-query-form-item">
                <a-range-picker v-model:value="queryForm.endTime" :presets="defaultTimeRanges" style="width: 150px" @change="onChangeEndTime" />
            </a-form-item>
            <a-form-item class="smart-query-form-item">
                <a-button type="primary" @click="queryData">
                    <template #icon>
                        <SearchOutlined />
                    </template>
                    查询
                </a-button>
                <a-button @click="resetQuery" class="smart-margin-left10">
                    <template #icon>
                        <ReloadOutlined />
                    </template>
                    重置
                </a-button>
            </a-form-item>
        </a-row>
    </a-form>
    <!---------- 查询表单form end ----------->

    <a-card size="small" :bordered="false" :hoverable="true">
        <!---------- 表格操作行 begin ----------->
        <a-row class="smart-table-btn-block">
            <div class="smart-table-operate-block">
                <a-button @click="showForm" type="primary" size="small">
                    <template #icon>
                        <PlusOutlined />
                    </template>
                    新建
                </a-button>
                <a-button @click="confirmBatchDelete" type="danger" size="small" :disabled="selectedRowKeyList.length == 0">
                    <template #icon>
                        <DeleteOutlined />
                    </template>
                    批量删除
                </a-button>
            </div>
            <div class="smart-table-setting-block">
                <TableOperator v-model="columns" :tableId="null" :refresh="queryData" />
            </div>
        </a-row>
        <!---------- 表格操作行 end ----------->

      <a-table
          size="small"
          :dataSource="tableData"
          :columns="columns"
          rowKey="id"
          bordered
          :loading="tableLoading"
          :pagination="false"
          :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
      >
        <template #bodyCell="{ text, record, column }">
          <template v-if="column.dataIndex === 'img'">
            <!-- 使用插槽将图片链接显示为图片 -->
            <img :src="record.img" alt="图片" style="max-width: 100%; max-height: 100px;" />
          </template>
          <template v-if="column.dataIndex === 'prizeType'">
            <!-- 使用 getPrizeTypeName 方法获取对应的奖品类型文本 -->
            {{ getPrizeTypeName(record.prizeType) }}
          </template>
          <template v-else-if="column.dataIndex === 'action'">
            <div class="smart-table-operate">
              <a-button @click="showForm(record)" type="link">编辑</a-button>
              <a-button @click="onDelete(record)" danger type="link">删除</a-button>
            </div>
          </template>
        </template>
      </a-table>

        <!---------- 表格 end ----------->

        <div class="smart-query-table-page">
            <a-pagination
                    showSizeChanger
                    showQuickJumper
                    show-less-items
                    :pageSizeOptions="PAGE_SIZE_OPTIONS"
                    :defaultPageSize="queryForm.pageSize"
                    v-model:current="queryForm.pageNum"
                    v-model:pageSize="queryForm.pageSize"
                    :total="total"
                    @change="queryData"
                    @showSizeChange="queryData"
                    :show-total="(total) => `共${total}条`"
            />
        </div>

        <PrizeForm  ref="formRef" @reloadList="queryData"/>

    </a-card>
</template>
<script setup>
    import { reactive, ref, onMounted } from 'vue';
    import { message, Modal } from 'ant-design-vue';
    import { SmartLoading } from '/@/components/framework/smart-loading';
    import { prizeApi } from '/@/api/business/lottery/prize-api.js';
    import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
    import { smartSentry } from '/@/lib/smart-sentry';
    import TableOperator from '/@/components/support/table-operator/index.vue';
    import { defaultTimeRanges } from '/@/lib/default-time-ranges';
    import PrizeForm from './prize-form.vue';
    // ---------------------------- 表格列 ----------------------------

    const columns = ref([
        {
            title: 'id',
            dataIndex: 'id',
            ellipsis: true,
        },
        {
            title: '奖品名称',
            dataIndex: 'title',
            ellipsis: true,
        },
        {
            title: '奖品数量',
            dataIndex: 'prizeNum',
            ellipsis: true,
        },
        {
            title: '剩余数量',
            dataIndex: 'leftNum',
            ellipsis: true,
        },
        {
            title: '中奖概率',
            dataIndex: 'prizeCode',
            ellipsis: true,
        },
        {
            title: '发奖周期',
            dataIndex: 'prizeTime',
            ellipsis: true,
        },
        {
            title: '位置序号',
            dataIndex: 'displayOrder',
            ellipsis: true,
        },
        {
            title: '奖品类型',
            dataIndex: 'prizeType',
            ellipsis: true,
        },
        {
            title: '开始时间',
            dataIndex: 'beginTime',
            ellipsis: true,
        },
        {
            title: '结束时间',
            dataIndex: 'endTime',
            ellipsis: true,
        },
        {
            title: '发奖计划',
            dataIndex: 'prizePlan',
            ellipsis: true,
        },
      /*  {
            title: '状态',
            dataIndex: 'sysStatus',
            ellipsis: true,
        },*/
        {
            title: '创建时间',
            dataIndex: 'sysCreated',
            ellipsis: true,
        },
        {
            title: '修改时间',
            dataIndex: 'sysUpdated',
            ellipsis: true,
        },
        {
            title: '奖品图片',
            dataIndex: 'img',
            ellipsis: true,
        },
        {
            title: '操作',
            dataIndex: 'action',
            fixed: 'right',
            width: 90,
        },
    ]);

    // ---------------------------- 查询数据表单和方法 ----------------------------

    const queryFormState = {
        title: undefined, //奖品名称
        beginTime: [], //奖品有效周期：开始时间
        beginTimeBegin: undefined, //奖品有效周期：开始时间 开始
        beginTimeEnd: undefined, //奖品有效周期：开始时间 结束
        endTime: [], //奖品有效周期：结束时间
        endTimeBegin: undefined, //奖品有效周期：结束时间 开始
        endTimeEnd: undefined, //奖品有效周期：结束时间 结束
        pageNum: 1,
        pageSize: 10,
    };
    // 查询表单form
    const queryForm = reactive({ ...queryFormState });
    // 表格加载loading
    const tableLoading = ref(false);
    // 表格数据
    const tableData = ref([]);
    // 总数
    const total = ref(0);

    // 重置查询条件
    function resetQuery() {
        let pageSize = queryForm.pageSize;
        Object.assign(queryForm, queryFormState);
        queryForm.pageSize = pageSize;
        queryData();
    }
    function   getPrizeTypeName(prizeType) {
      switch (prizeType) {
        case 1:
          return '虚拟币';
        case 2:
          return '虚拟券';
        case 3:
          return '实物小奖';
        case 4:
          return '实物大奖';
        default:
          return '';
      }
    }
    // 查询数据
    async function queryData() {
        tableLoading.value = true;
        try {
            let queryResult = await prizeApi.queryPage(queryForm);
            tableData.value = queryResult.data.list;
            total.value = queryResult.data.total;
        } catch (e) {
            smartSentry.captureError(e);
        } finally {
            tableLoading.value = false;
        }
    }

    function onChangeBeginTime(dates, dateStrings){
        queryForm.beginTimeBegin = dateStrings[0];
        queryForm.beginTimeEnd = dateStrings[1];
    }

    function onChangeEndTime(dates, dateStrings){
        queryForm.endTimeBegin = dateStrings[0];
        queryForm.endTimeEnd = dateStrings[1];
    }


    onMounted(queryData);

    // ---------------------------- 添加/修改 ----------------------------
    const formRef = ref();

    function showForm(data) {
        formRef.value.show(data);
    }

    // ---------------------------- 单个删除 ----------------------------
    //确认删除
    function onDelete(data){
        Modal.confirm({
            title: '提示',
            content: '确定要删除选吗?',
            okText: '删除',
            okType: 'danger',
            onOk() {
                requestDelete(data);
            },
            cancelText: '取消',
            onCancel() {},
        });
    }

    //请求删除
    async function requestDelete(data){
        SmartLoading.show();
        try {
            let deleteForm = {
                goodsIdList: selectedRowKeyList.value,
            };
            await prizeApi.delete(data.id);
            message.success('删除成功');
            queryData();
        } catch (e) {
            smartSentry.captureError(e);
        } finally {
            SmartLoading.hide();
        }
    }

    // ---------------------------- 批量删除 ----------------------------

    // 选择表格行
    const selectedRowKeyList = ref([]);

    function onSelectChange(selectedRowKeys) {
        selectedRowKeyList.value = selectedRowKeys;
    }

    // 批量删除
    function confirmBatchDelete() {
        Modal.confirm({
            title: '提示',
            content: '确定要批量删除这些数据吗?',
            okText: '删除',
            okType: 'danger',
            onOk() {
                requestBatchDelete();
            },
            cancelText: '取消',
            onCancel() {},
        });
    }

    //请求批量删除
    async function requestBatchDelete() {
        try {
            SmartLoading.show();
            await prizeApi.batchDelete(selectedRowKeyList.value);
            message.success('删除成功');
            queryData();
        } catch (e) {
            smartSentry.captureError(e);
        } finally {
            SmartLoading.hide();
        }
    }
</script>
