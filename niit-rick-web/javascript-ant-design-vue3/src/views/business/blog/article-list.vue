<!--
  * 文章管理
  *
  * @Author:    李祥生
  * @Date:      2024-05-22 15:28:58
  * @Copyright  niit-阿升
-->
<template>
    <!---------- 查询表单form begin ----------->
    <a-form class="smart-query-form">
        <a-row class="smart-query-form-row">
            <a-form-item label="标题名" class="smart-query-form-item">
                <a-input style="width: 150px" v-model:value="queryForm.articleTitle" placeholder="标题名" />
            </a-form-item>
            <a-form-item label="创建时间" class="smart-query-form-item">
                <a-range-picker v-model:value="queryForm.createdTime" :presets="defaultTimeRanges" style="width: 150px" @change="onChangeCreatedTime" />
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

        <!---------- 表格 begin ----------->
        <a-table
                size="small"
                :dataSource="tableData"
                :columns="columns"
                rowKey="articleId"
                bordered
                :loading="tableLoading"
                :pagination="false"
                :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
        >
            <template #bodyCell="{ text, record, column }">
                <template v-if="column.dataIndex === 'action'">
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

        <ArticleForm  ref="formRef" @reloadList="queryData"/>

    </a-card>
</template>
<script setup>
    import { reactive, ref, onMounted } from 'vue';
    import { message, Modal } from 'ant-design-vue';
    import { SmartLoading } from '/@/components/framework/smart-loading';
    import { articleApi } from '/@/api/business/blog/article-api.js';
    import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
    import { smartSentry } from '/@/lib/smart-sentry';
    import TableOperator from '/@/components/support/table-operator/index.vue';
    import ArticleForm from './article-form.vue';
    import { defaultTimeRanges } from '/@/lib/default-time-ranges';
    // ---------------------------- 表格列 ----------------------------

    const columns = ref([
        {
            title: '文章id',
            dataIndex: 'articleId',
            ellipsis: true,
        },
        {
            title: '文章标题',
            dataIndex: 'articleTitle',
            ellipsis: true,
        },
        {
            title: '文章摘要',
            dataIndex: 'articleSummary',
            ellipsis: true,
        },
        {
            title: '文章内容',
            dataIndex: 'articleContent',
            ellipsis: true,
        },
        {
            title: '文章浏览量',
            dataIndex: 'articleViewCount',
            ellipsis: true,
        },
        {
            title: '文章点赞量',
            dataIndex: 'articleLikeCount',
            ellipsis: true,
        },
        {
            title: '文章评论数量',
            dataIndex: 'articleCommentCount',
            ellipsis: true,
        },
        {
            title: '创建时间',
            dataIndex: 'createdTime',
            ellipsis: true,
        },
        {
            title: '修改时间',
            dataIndex: 'updateTime',
            ellipsis: true,
        },
        {
            title: '外键，对应user_id',
            dataIndex: 'articleUserId',
            ellipsis: true,
        },
        {
            title: '分类表中对应category_name',
            dataIndex: 'articleCategoryName',
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
        articleTitle: undefined, //标题名
        createdTime: [], //创建时间
        createdTimeBegin: undefined, //创建时间 开始
        createdTimeEnd: undefined, //创建时间 结束
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

    // 查询数据
    async function queryData() {
        tableLoading.value = true;
        try {
            let queryResult = await articleApi.queryPage(queryForm);
            tableData.value = queryResult.data.list;
            total.value = queryResult.data.total;
        } catch (e) {
            smartSentry.captureError(e);
        } finally {
            tableLoading.value = false;
        }
    }

    function onChangeCreatedTime(dates, dateStrings){
        queryForm.createdTimeBegin = dateStrings[0];
        queryForm.createdTimeEnd = dateStrings[1];
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
            await articleApi.delete(data.articleId);
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
            await articleApi.batchDelete(selectedRowKeyList.value);
            message.success('删除成功');
            queryData();
        } catch (e) {
            smartSentry.captureError(e);
        } finally {
            SmartLoading.hide();
        }
    }
</script>
