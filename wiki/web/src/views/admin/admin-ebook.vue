<template>
  <a-layout style="padding: 24px 0; background: #fff">
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-space size="small">
          <a-input v-model:value="name" placeholder="Basic usage"/>
          <a-button type="primary" @click="choose()" size="large">
            查询
          </a-button>
          <a-button type="primary" @click="add()" size="large">
            新增
          </a-button>
        </a-space>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:category="{text, record}">
          <span>
            {{ getCategoryName(record.category1Id) }}/{{ getCategoryName(record.category2Id) }}
          </span>
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
            <router-link to="/admin/doc">
              <a-button type="primary">
                文档管理
              </a-button>
            </router-link>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      title="电子书表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader v-model:value="categoryIds"
                    :field-names="{ label: 'name', value: 'id', children: 'children'}"
                    :options="level1"
        />
      </a-form-item>

      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>

</template>
<script lang="ts">

import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios'
import {message} from 'ant-design-vue';
import {Tool} from '@/util/tool';

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });
    let categorys: any;
    const categoryIds = ref();
    const level1 = ref();
    const loading = ref(false);
    const ebook = ref();
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: {customRender: 'cover'}
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '分类',
        slots: {customRender: 'category'}
      },

      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }];
    /**
     * 数据查询
     **/
    const handleQuery = (params: any) => {
      loading.value = true;

      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size,
          name: params.name,
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          ebooks.value = data.content.list;
          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    };
    const handleQueryCategory = () => {
      loading.value = true;

      axios.get("/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组：", categorys);
          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形结构：", level1);
          //加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };

    /**
     * 表格点击页码时触发
     */
    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    const handleModalOk = () => {
      modalLoading.value = true;
      ebook.value.category1Id = categoryIds.value[0];
      ebook.value.category2Id = categoryIds.value[1];
      axios.post("/ebook/save",
          ebook.value).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          modalVisible.value = false;

          //重新加载列表
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);

        }
      });
    };

    // 新增
    const add = () => {
      modalVisible.value = true;
      ebook.value = {};
    };

    /**
     * 编辑
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      ebook.value = Tool.copy(record);
      categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
    };
    //删除
    //这里id由雪花算法生成，如果后端不转化为string传递过来就会精度丢失
    const handleDelete = (id: string) => {
      axios.delete("/ebook/delete/" +
          id).then((response) => {

        const data = response.data;
        if (data.success) {

          //重新加载列表
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        }
      });
    };
    const name = ref<string>('');
    const choose = (params: any) => {
      loading.value = true;
      handleQuery({
        page: 1,
        size: pagination.value.pageSize,
        name: name.value,
      });
    };
    //会爆精度
    //Js语言这些参数指定都是假的，就算只定了cid:number ，传字符串进来cid就是一个字符串
    const getCategoryName = (cid: string) => {
      let result = "";
      categorys.forEach((item: any) => {
        if (item.id === cid) {
          result = item.name;
        }
      });
      return result;
    };
    onMounted(function () {
      handleQueryCategory();

    });

    return {
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      handleQueryCategory,
      getCategoryName,
      categoryIds,
      level1,
      edit,
      add,
      name,
      handleDelete,
      choose,
      ebook,
      modalVisible,
      modalLoading,
      handleModalOk
    }
  }
});
</script>
<style>
img {
  vertical-align: middle;
  border-style: none;
  width: 50px;
}
</style>
